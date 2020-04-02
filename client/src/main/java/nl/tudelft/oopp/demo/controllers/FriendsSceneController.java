package nl.tudelft.oopp.demo.controllers;

import com.jayway.jsonpath.internal.function.numeric.Max;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import nl.tudelft.oopp.demo.communication.RoomReservationCommunication;
import nl.tudelft.oopp.demo.communication.UserCommunication;
import nl.tudelft.oopp.demo.helperclasses.RoomReservation;
import nl.tudelft.oopp.demo.helperclasses.User;

public class FriendsSceneController implements Initializable {

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

        HBox hoBoxAddFriend = new HBox();
        Button addButton = new Button("Add a friend");
        TextField newFriendTextField = new TextField();
        newFriendTextField.setPromptText("username");
        hoBoxAddFriend.getChildren().addAll(addButton, newFriendTextField);
        hoBoxAddFriend.setAlignment(Pos.CENTER);
        hoBoxAddFriend.setSpacing(10);

        ObservableList<User> friends = FXCollections.observableList(FriendCommunication.getFriends(Authenticator.USERNAME));
        ObservableList<RoomReservation> reservedRooms = FXCollections.observableList(RoomReservationCommunication.getRoomReservationsByUserId(Authenticator.ID));

        TitledPane[] tps = new TitledPane[friends.size()];
        List<Button> buttonsRemove = new ArrayList<>();
        List<Button> buttonsInvite = new ArrayList<>();

        VBox veBoxTpAndAdd = new VBox();

        int c = 0;

        for (int i = 0; i < friends.size(); i++) {
            tps[c] = new TitledPane();

            Label friendsLabel = new Label(friends.get(i).getUsername());

            Button inviteFriendsButton = new Button("Invite");
            buttonsInvite.add(inviteFriendsButton);

            Button removeFriendsButton = new Button("Remove");
            buttonsRemove.add(removeFriendsButton);

            ComboBox<RoomReservation> comboBoxReservedRooms = new ComboBox<>();
            comboBoxReservedRooms.setItems(reservedRooms);

            String friendId = friends.get(i).getUsername();
            removeFriendsButton.setOnAction(event -> {
                FriendCommunication.removeFriendship(UserCommunication.getByUsername(Authenticator.USERNAME), UserCommunication.getByUsername(friendId));
            });

            HBox.setHgrow(inviteFriendsButton, Priority.ALWAYS);
            HBox.setHgrow(removeFriendsButton, Priority.ALWAYS);
            HBox.setHgrow(friendsLabel, Priority.ALWAYS);
            inviteFriendsButton.setMinWidth(70);
            removeFriendsButton.setMinWidth(70);
            friendsLabel.setMinWidth(100);
            comboBoxReservedRooms.setMinWidth(75);

            HBox hoBoxUsernameAndRemove = new HBox();
            hoBoxUsernameAndRemove.setSpacing(50);
            hoBoxUsernameAndRemove.getChildren().addAll(friendsLabel, comboBoxReservedRooms, inviteFriendsButton, removeFriendsButton);
            hoBoxUsernameAndRemove.setStyle("-fx-padding: 8;" + "-fx-border-style: solid inside;"
                + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
                + "-fx-border-radius: 5;" + "-fx-border-color: lightblue;");
            veBoxTpAndAdd.getChildren().add(hoBoxUsernameAndRemove);
            veBoxTpAndAdd.setPadding(new Insets(20,0,0,0));
        }

        addButton.setOnAction(event -> {
            String newFriendTextFieldText = newFriendTextField.getText();

            System.out.println(FriendCommunication.addFriendship(UserCommunication.getByUsername(Authenticator.USERNAME), UserCommunication.getByUsername(newFriendTextFieldText)));

            newFriendTextField.setText(null);
        });

        borderPane.setCenter(hoBoxAddFriend);
        borderPane.setBottom(veBoxTpAndAdd);
        borderPane.setPadding(new Insets(30, 5, 5, 10));
    }
}
