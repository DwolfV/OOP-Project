package nl.tudelft.oopp.demo.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import nl.tudelft.oopp.demo.communication.Authenticator;
import nl.tudelft.oopp.demo.communication.FriendCommunication;
import nl.tudelft.oopp.demo.communication.RoomCommunication;
import nl.tudelft.oopp.demo.communication.UserCommunication;
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
        Button deleteButton = new Button();
        Button addButton = new Button();
        hoBox.getChildren().addAll(deleteButton, addButton);

        ListView<User> listView = new ListView<>();
        ObservableList<User> friends = FXCollections.observableList(FriendCommunication.getFriends(Authenticator.USERNAME));
        listView.setItems(friends);

        VBox veBox = new VBox();
        veBox.getChildren().addAll(listView, hoBox);

        borderPane.setCenter(veBox);
        borderPane.setPadding(new Insets(30, 5, 5, 10));
    }
}
