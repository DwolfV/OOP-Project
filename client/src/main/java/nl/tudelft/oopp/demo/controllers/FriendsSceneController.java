package nl.tudelft.oopp.demo.controllers;

import com.jayway.jsonpath.internal.function.numeric.Max;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import nl.tudelft.oopp.demo.communication.*;
import nl.tudelft.oopp.demo.helperclasses.Building;
import nl.tudelft.oopp.demo.helperclasses.Friend;
import nl.tudelft.oopp.demo.helperclasses.Room;
import nl.tudelft.oopp.demo.helperclasses.User;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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

        HBox hoBoxAddFriend = new HBox();
        Button addButton = new Button("Add a friend");
        TextField newFriendTextField = new TextField();
        newFriendTextField.setPromptText("username");
        hoBoxAddFriend.getChildren().addAll(addButton, newFriendTextField);
        hoBoxAddFriend.setAlignment(Pos.CENTER);
        hoBoxAddFriend.setSpacing(10);

        ObservableList<User> friends = FXCollections.observableList(FriendCommunication.getFriends(Authenticator.USERNAME));

        TitledPane[] tps = new TitledPane[friends.size()];
        List<Button> buttons = new ArrayList<>();

        VBox veBoxTpAndAdd = new VBox();

        int c = 0;

        for (int i = 0; i < friends.size(); i++) {
            tps[c] = new TitledPane();
            HBox hoBoxUsernameAndRemove = new HBox();

            Label friendsLabel = new Label(friends.get(i).getUsername());

            Button removeFriendsButton = new Button("Remove");
            buttons.add(removeFriendsButton);

            removeFriendsButton.setOnAction(event -> {
//            FriendCommunication.removeFriendship(UserCommunication.getByUsername(Authenticator.USERNAME), UserCommunication.getByUsername());
            });

            hoBoxUsernameAndRemove.getChildren().addAll(friendsLabel, removeFriendsButton);
            hoBoxUsernameAndRemove.getChildren().get(1).setTranslateX(780);
            hoBoxUsernameAndRemove.setSpacing(150);
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
