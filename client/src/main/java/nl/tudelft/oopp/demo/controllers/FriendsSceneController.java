package nl.tudelft.oopp.demo.controllers;

import static java.util.function.Predicate.not;

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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import nl.tudelft.oopp.demo.communication.Authenticator;
import nl.tudelft.oopp.demo.communication.FriendCommunication;
import nl.tudelft.oopp.demo.communication.InvitationCommunication;
import nl.tudelft.oopp.demo.communication.RoomReservationCommunication;
import nl.tudelft.oopp.demo.communication.UserCommunication;
import nl.tudelft.oopp.demo.entities.RoomReservation;
import nl.tudelft.oopp.demo.entities.User;

public class FriendsSceneController implements Initializable {

    private static HBox hoBoxAddFriend;
    @FXML
    private BorderPane borderPane;

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
        addFriends();
        friendList();
    }

    /**
     * The method is used to set to add friends to the list.
     */
    public void addFriends() {
        Button addButton = new Button("Add a friend");
        TextField newFriendTextField = new TextField();
        newFriendTextField.setPromptText("username");

        hoBoxAddFriend = new HBox();
        hoBoxAddFriend.getChildren().addAll(addButton, newFriendTextField);
        hoBoxAddFriend.setAlignment(Pos.CENTER);
        hoBoxAddFriend.setSpacing(10);

        // add a friend
        addButton.setOnAction(event -> {
            String newFriendTextFieldText = newFriendTextField.getText();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText(FriendCommunication.addFriendship(UserCommunication.getByUsername(Authenticator.USERNAME),
                UserCommunication.getByUsername(newFriendTextFieldText)));

            alert.showAndWait();

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

            HBox.setHgrow(inviteFriendsButton, Priority.ALWAYS);
            HBox.setHgrow(removeFriendsButton, Priority.ALWAYS);
            HBox.setHgrow(friendsLabel, Priority.ALWAYS);
            inviteFriendsButton.setMinWidth(70);
            removeFriendsButton.setMinWidth(70);
            friendsLabel.setMinWidth(100);
            comboBoxReservedRooms.setMinWidth(75);

            HBox hoBoxUsernameAndRemove = new HBox();
            hoBoxUsernameAndRemove.setSpacing(150);
            hoBoxUsernameAndRemove.getChildren().addAll(friendsLabel, comboBoxReservedRooms, inviteFriendsButton, removeFriendsButton);
            hoBoxUsernameAndRemove.setStyle("-fx-padding: 8;" + "-fx-border-style: solid inside;"
                + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
                + "-fx-border-radius: 5;" + "-fx-border-color: lightblue;");

            veBoxTpAndAdd.getChildren().add(hoBoxUsernameAndRemove);
            veBoxTpAndAdd.setPadding(new Insets(20, 0, 0, 0));
        }
        borderPane.setCenter(hoBoxAddFriend);
        borderPane.setBottom(veBoxTpAndAdd);
        borderPane.setPadding(new Insets(30, 5, 5, 10));
    }
}
