package nl.tudelft.oopp.demo.controllers;

import static java.util.function.Predicate.not;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import nl.tudelft.oopp.demo.communication.Authenticator;
import nl.tudelft.oopp.demo.communication.FriendCommunication;
import nl.tudelft.oopp.demo.communication.InvitationCommunication;
import nl.tudelft.oopp.demo.communication.RoomReservationCommunication;
import nl.tudelft.oopp.demo.communication.UserCommunication;
import nl.tudelft.oopp.demo.entities.RoomReservation;
import nl.tudelft.oopp.demo.entities.SupplyReservation;
import nl.tudelft.oopp.demo.entities.User;

public class FriendsSceneController implements Initializable {

    private static TableView<SupplyReservation> tableFriends;

    private static HBox hoBoxAddFriend;

    @FXML private VBox vbox;

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        addFriends();
        friendList();
        vbox.setPrefWidth(screenSize.getWidth() - 400);
    }

    /**
     * The method is used to set to add friends to the list.
     */
    public void addFriends() {
        Button addButton = new Button("Add a friend");
        addButton.getStyleClass().setAll("restaurant-menu-button");
        TextField newFriendTextField = new TextField();
        newFriendTextField.setPromptText("username");

        hoBoxAddFriend = new HBox();
        hoBoxAddFriend.getChildren().addAll(newFriendTextField, addButton);
        hoBoxAddFriend.setAlignment(Pos.CENTER);
        hoBoxAddFriend.setSpacing(10);

        // add a friend
        addButton.setOnAction(event -> {
            String newFriendTextFieldText = newFriendTextField.getText();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            String success = FriendCommunication.addFriendship(UserCommunication.getByUsername(Authenticator.USERNAME),
                UserCommunication.getByUsername(newFriendTextFieldText));
            if (success.equals("Successful")) {
                alert.hide();
            } else {
                alert.setContentText(success);
                alert.showAndWait();
            }

            newFriendTextField.setText(null);

            friendList();
        });
    }

    /**
     * The method is used to set up the friends list for the user.
     */
    public void friendList() {
        VBox veBoxTpAndAdd = new VBox();

        ObservableList<User> friends = FXCollections.observableList(FriendCommunication.getFriends(Authenticator.USERNAME));
        ObservableList<RoomReservation> reservedRooms = FXCollections.observableList(RoomReservationCommunication.getRoomReservationsByUserId(Authenticator.ID));

        // This changes the observable list to a more human readable list
        List<String> reservedRoomsList = new ArrayList<>();
        for (RoomReservation reservedRoom : reservedRooms) {
            reservedRoomsList.add(reservedRoom.invitationString());
        }

        TitledPane[] tps = new TitledPane[friends.size()];
        List<Button> buttonsRemove = new ArrayList<>();
        List<Button> buttonsInvite = new ArrayList<>();

        int c = 0;

        for (User friend : friends) {
            tps[c] = new TitledPane();

            Label friendsLabel = new Label(friend.getUsername());
            String friendId = friend.getUsername();

            Button inviteFriendsButton = new Button("Invite");
            buttonsInvite.add(inviteFriendsButton);

            Button removeFriendsButton = new Button("Remove");
            buttonsRemove.add(removeFriendsButton);

            ComboBox<String> comboBoxReservedRooms = new ComboBox<>();

            // add all the reserved invitations by the friend's ID
            ObservableList<RoomReservation> reservedRoomsInvitations = FXCollections.observableList(InvitationCommunication.getInvitations(friendId));
            List<String> invitedRoomsList = new ArrayList<>();
            for (RoomReservation reservedRoomsInvitation : reservedRoomsInvitations) {
                invitedRoomsList.add(reservedRoomsInvitation.invitationString());
            }

            // filtering the reserved room list to show reserved rooms that weren't invited to by the user
            Set<String> result = reservedRoomsList.stream()
                .distinct()
                .filter(not(invitedRoomsList::contains))
                .collect(Collectors.toSet());
            List<String> filteredRoomsList = new ArrayList<>(result);

            ObservableList<String> readableReservedRoomsList = FXCollections.observableArrayList(filteredRoomsList);

            comboBoxReservedRooms.setItems(readableReservedRoomsList);

            final String[] temp = new String[3];
            final String[] buildingAndRoom = new String[3];
            String delimiter = ", ";
            String secondDelimiter = ": ";
            comboBoxReservedRooms.setOnAction(e -> {
                temp[0] = (comboBoxReservedRooms.getValue().trim().split(delimiter)[0]);
                temp[1] = (comboBoxReservedRooms.getValue().trim().split(delimiter)[1]);
                temp[2] = (comboBoxReservedRooms.getValue().trim().split(delimiter)[2]);

                buildingAndRoom[0] = (temp[0].trim().split(secondDelimiter)[1]);
                buildingAndRoom[1] = (temp[1].trim().split(secondDelimiter)[1]);
                buildingAndRoom[2] = (temp[2].trim().split(secondDelimiter)[1]);
            });

            // remove a friend
            removeFriendsButton.setOnAction(event -> {
                FriendCommunication.removeFriendship(UserCommunication.getByUsername(Authenticator.USERNAME),
                    UserCommunication.getByUsername(friendId));
                friendList();
            });

            // invite a friend to a reserved room
            inviteFriendsButton.setOnAction(event -> {
                List<RoomReservation> reservedList = RoomReservationCommunication.getRoomReservationsByUserId(Authenticator.ID);
                for (RoomReservation reservedRooms1 : reservedList) {
                    if (reservedRooms1.getRoom().getBuilding().getName().equals(buildingAndRoom[0])
                        && reservedRooms1.getRoom().getName().equals(buildingAndRoom[1])
                        && reservedRooms1.getDate().toString().equals(buildingAndRoom[2])) {
                        
                        InvitationCommunication.addInvitation(reservedRooms1, UserCommunication.getByUsername(friendId));
                        break;
                    }
                }
                friendList();
            });

            // Grid inside list
            GridPane grid = new GridPane();
            ColumnConstraints constraint1 = new ColumnConstraints();
            constraint1.setPercentWidth(100 / 5);
            ColumnConstraints constraint2 = new ColumnConstraints();
            constraint2.setPercentWidth(100 / 2.5);
            ColumnConstraints constraint3 = new ColumnConstraints();
            constraint3.setPercentWidth(100 / 5);
            ColumnConstraints constraint4 = new ColumnConstraints();
            constraint4.setPercentWidth(100 / 5);
            grid.getColumnConstraints().setAll(
                    constraint1,
                    constraint2,
                    constraint3,
                    constraint4
            );

            grid.setVgap(15);
            grid.setHgap(15);
            grid.add(friendsLabel, 0, c);
            grid.add(comboBoxReservedRooms, 1, c);
            grid.add(inviteFriendsButton, 2, c);
            grid.add(removeFriendsButton, 3, c);
            inviteFriendsButton.getStyleClass().setAll("restaurant-menu-button");
            inviteFriendsButton.setAlignment(Pos.BOTTOM_CENTER);
            removeFriendsButton.getStyleClass().setAll("restaurant-menu-button");
            removeFriendsButton.setAlignment(Pos.CENTER);
            grid.getStyleClass().setAll("friends-list");

            veBoxTpAndAdd.getChildren().add(grid);
            veBoxTpAndAdd.setSpacing(20);
        }
        SplitPane splitPane1 = new SplitPane();
        SplitPane splitPane2 = new SplitPane();
        hoBoxAddFriend.setPadding(new Insets(20, 20, 20, 20));
        veBoxTpAndAdd.setPadding(new Insets(20, 20, 20, 20));

        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        veBoxTpAndAdd.setPrefWidth(screenBounds.getWidth() - 460);
        ScrollPane scrollPane = new ScrollPane(veBoxTpAndAdd);
        scrollPane.setMaxHeight(600);
        vbox.getChildren().setAll(splitPane1, hoBoxAddFriend, splitPane2, scrollPane);
    }
}
