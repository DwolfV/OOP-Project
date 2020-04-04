package nl.tudelft.oopp.demo.helperclasses;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Accordion;
import javafx.scene.layout.BorderPane;
import nl.tudelft.oopp.demo.communication.Authenticator;
import nl.tudelft.oopp.demo.communication.FriendCommunication;
import nl.tudelft.oopp.demo.communication.UserCommunication;
import nl.tudelft.oopp.demo.entities.User;

public class AdminRolePane {

    /**
     * Get the BorderPane of the Roles info list.
     * @return BorderPane of Roles Info
     */
    public static BorderPane getRolesBP(Accordion ac) {

        for (int i = 0; i < user)
        String usersUsername = ;
        ObservableList<User> users = FXCollections.observableList(UserCommunication.getByUsername(usersUsername));



        // All elements in BorderPane
        BorderPane borderPane = new BorderPane();
        borderPane.getStyleClass().add("border-pane");
        borderPane.setCenter(listUsers);

        return borderPane;
    }
}
