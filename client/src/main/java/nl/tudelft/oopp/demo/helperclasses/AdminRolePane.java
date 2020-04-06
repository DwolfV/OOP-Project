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

            String roles = users.get(i).getRole();
            String tempWord = "ROLE_";
            roles = roles.replaceAll(tempWord, "");

            Label usernameLabel = new Label(users.get(i).getUsername());
            Label userFirstNameLabel = new Label(users.get(i).getFirstName());
            Label userLastNameLabel = new Label(users.get(i).getLastName());
            Label userRoleLabel = new Label(roles);

            Button makeAdminButton = new Button("Make Admin");
            makeAdminButtons.add(makeAdminButton);
            makeAdminButton.getStyleClass().setAll("restaurant-menu-button");

            Button makeEmployeeButton = new Button("Make Employee");
            makeEmployeeButtons.add(makeEmployeeButton);
            makeEmployeeButton.getStyleClass().setAll("restaurant-menu-button");

            Button makeNormalButton = new Button("Make User");
            makeNormalButtons.add(makeNormalButton);
            makeNormalButton.getStyleClass().setAll("restaurant-menu-button");


            if (users.get(i).getRole().equals("ROLE_USER")) {
                makeNormalButton.setDisable(true);
            }
            if (users.get(i).getRole().equals("ROLE_EMPLOYEE")) {
                makeEmployeeButton.setDisable(true);
            }
            if (users.get(i).getRole().equals("ROLE_ADMIN")) {
                makeAdminButton.setDisable(true);
            }

            int index = i;

            // make a user Admin
            makeAdminButton.setOnAction(event -> {
                UserCommunication.changeRole(users.get(index).getUsername(), "admin");
                AdminSceneController.loadRolesTP(ac);
                ac.setExpandedPane(AdminSceneController.rolesTP);
            });

            // make a user Employee
            makeEmployeeButton.setOnAction(event -> {
                UserCommunication.changeRole(users.get(index).getUsername(), "employee");
                AdminSceneController.loadRolesTP(ac);
                ac.setExpandedPane(AdminSceneController.rolesTP);
            });

            // make a user "Normal"
            makeNormalButton.setOnAction(event -> {
                UserCommunication.changeRole(users.get(index).getUsername(), "user");
                AdminSceneController.loadRolesTP(ac);
                ac.setExpandedPane(AdminSceneController.rolesTP);
            });

            HBox.setHgrow(usernameLabel, Priority.ALWAYS);
            HBox.setHgrow(userFirstNameLabel, Priority.ALWAYS);
            HBox.setHgrow(userLastNameLabel, Priority.ALWAYS);
            HBox.setHgrow(userRoleLabel, Priority.ALWAYS);
            HBox.setHgrow(makeNormalButton, Priority.ALWAYS);
            HBox.setHgrow(makeEmployeeButton, Priority.ALWAYS);
            HBox.setHgrow(makeAdminButton, Priority.ALWAYS);

            usernameLabel.setMinWidth(100);
            userFirstNameLabel.setMinWidth(100);
            userLastNameLabel.setMinWidth(100);
            userRoleLabel.setMinWidth(40);
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
