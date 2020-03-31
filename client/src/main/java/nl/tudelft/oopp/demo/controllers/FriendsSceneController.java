package nl.tudelft.oopp.demo.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import nl.tudelft.oopp.demo.communication.*;
import nl.tudelft.oopp.demo.helperclasses.Building;
import nl.tudelft.oopp.demo.helperclasses.Friend;
import nl.tudelft.oopp.demo.helperclasses.Room;
import nl.tudelft.oopp.demo.helperclasses.User;

import java.net.URL;
import java.util.ResourceBundle;

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
    public void initialize (URL location, ResourceBundle resources) {

        HBox hoBox = new HBox();
        Button deleteButton = new Button("Delete");
        Button addButton = new Button("Add");
        TextField newFriendTextField = new TextField();
        newFriendTextField.setPromptText("username");

        hoBox.getChildren().addAll(deleteButton, addButton, newFriendTextField);

        ListView<User> friendList = new ListView<>();
        ObservableList<User> friends = FXCollections.observableList(FriendCommunication.getFriends(Authenticator.USERNAME));
        friendList.setItems(friends);

        deleteButton.setOnAction(event -> {
            final int selectedIdx = friendList.getSelectionModel().getSelectedIndex();
            if (selectedIdx != -1) {

                final int newSelectedIdx =
                    (selectedIdx == friendList.getItems().size() - 1)
                        ? selectedIdx - 1
                        : selectedIdx;

                friendList.getSelectionModel().select(newSelectedIdx);
                friends.remove(selectedIdx);
            }
        });

        addButton.setOnAction(event -> {
            String newFriendTextFieldText = newFriendTextField.getText();

            FriendCommunication.addFriendship(UserCommunication.getByUsername(Authenticator.USERNAME), UserCommunication.getByUsername(newFriendTextFieldText));

            newFriendTextField.setText(null);
        });

        VBox veBox = new VBox();
        veBox.getChildren().addAll(friendList, hoBox);

        borderPane.setCenter(veBox);
        borderPane.setPadding(new Insets(30, 5, 5, 10));
    }
}
