package nl.tudelft.oopp.demo.helperclasses;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import nl.tudelft.oopp.demo.communication.UserCommunication;
import nl.tudelft.oopp.demo.controllers.AdminSceneController;
import nl.tudelft.oopp.demo.entities.User;

public class AdminRolePane {

    /**
     * Get the BorderPane of the Roles info list.
     * @return BorderPane of Roles Info
     */
    public static BorderPane getRolesBP(Accordion ac) {
        VBox veBoxTp = new VBox();

        ObservableList<User> users = FXCollections.observableList(UserCommunication.getUsers());

        TitledPane[] tps = new TitledPane[users.size()];
        List<Button> makeAdminButtons = new ArrayList<>();
        List<Button> makeEmployeeButtons = new ArrayList<>();
        List<Button> makeNormalButtons = new ArrayList<>();

        int c = 0; //c - for tps

        for (int i = 0; i < users.size(); i++) {
            tps[c] = new TitledPane();

            Label usernameLabel = new Label(users.get(i).getUsername());
            Label userFirstNameLabel = new Label(users.get(i).getFirstName());
            Label userLastNameLabel = new Label(users.get(i).getLastName());
            Label userRoleLabel = new Label(users.get(i).getRole());

            Button makeAdminButton = new Button("Make Admin");
            makeAdminButtons.add(makeAdminButton);

            Button makeEmployeeButton = new Button("Make Employee");
            makeEmployeeButtons.add(makeEmployeeButton);

            Button makeNormalButton = new Button("Make Normal");
            makeNormalButtons.add(makeEmployeeButton);

            int Index = i;

            // make a user Admin
            makeAdminButton.setOnAction(event -> {
                UserCommunication.changeRole(users.get(Index).getUsername(), "admin");
                AdminSceneController.loadAdminScene(ac);
                ac.setExpandedPane(AdminSceneController.rolesTP);
            });

            // make a user Employee
            makeEmployeeButton.setOnAction(event -> {
                UserCommunication.changeRole(users.get(Index).getUsername(), "employee");
                AdminSceneController.loadAdminScene(ac);
                ac.setExpandedPane(AdminSceneController.rolesTP);
            });

            // make a user "Normal"
            makeEmployeeButton.setOnAction(event -> {
                UserCommunication.changeRole(users.get(Index).getUsername(), "user");
                AdminSceneController.loadAdminScene(ac);
                ac.setExpandedPane(AdminSceneController.rolesTP);
            });

            HBox.setHgrow(usernameLabel, Priority.ALWAYS);
            HBox.setHgrow(userFirstNameLabel, Priority.ALWAYS);
            HBox.setHgrow(userLastNameLabel, Priority.ALWAYS);
            HBox.setHgrow(userRoleLabel, Priority.ALWAYS);
            HBox.setHgrow(makeNormalButton, Priority.ALWAYS);
            HBox.setHgrow(makeEmployeeButton, Priority.ALWAYS);
            HBox.setHgrow(makeAdminButton, Priority.ALWAYS);

            usernameLabel.setMinWidth(70);
            userFirstNameLabel.setMinWidth(70);
            userLastNameLabel.setMinWidth(70);
            userRoleLabel.setMinWidth(100);
            makeNormalButton.setMinWidth(40);
            makeEmployeeButton.setMinWidth(40);
            makeAdminButton.setMinWidth(40);

            HBox hoBoxUsernameAndRemove = new HBox();
            hoBoxUsernameAndRemove.setSpacing(25);
            hoBoxUsernameAndRemove.getChildren().addAll(usernameLabel, userFirstNameLabel, userLastNameLabel, userRoleLabel,
                makeNormalButton, makeEmployeeButton, makeAdminButton);
            hoBoxUsernameAndRemove.setStyle("-fx-padding: 8;" + "-fx-border-style: solid inside;"
                + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
                + "-fx-border-radius: 5;" + "-fx-border-color: lightblue;");

            veBoxTp.getChildren().add(hoBoxUsernameAndRemove);
            veBoxTp.setPadding(new Insets(10, 0, 0, 0));
        }

        // All elements in BorderPane
        BorderPane borderPane = new BorderPane();
        borderPane.getStyleClass().add("border-pane");
        borderPane.setCenter(veBoxTp);

        return borderPane;
    }
}
