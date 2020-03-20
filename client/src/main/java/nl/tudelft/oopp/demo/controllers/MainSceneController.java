package nl.tudelft.oopp.demo.controllers;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;
import nl.tudelft.oopp.demo.communication.BuildingCommunication;
import nl.tudelft.oopp.demo.communication.RestaurantCommunication;
import nl.tudelft.oopp.demo.communication.RoomCommunication;
import nl.tudelft.oopp.demo.communication.UserCommunication;
import nl.tudelft.oopp.demo.helperclasses.Building;
import nl.tudelft.oopp.demo.helperclasses.Restaurant;
import nl.tudelft.oopp.demo.helperclasses.Room;
import nl.tudelft.oopp.demo.views.MainDisplay;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainSceneController implements Initializable {

    public static final TitledPane buildingTP = new TitledPane("Buildings", new Button("View"));
    public static final TitledPane roomsTP = new TitledPane("Rooms", new Button("View"));
    public static final TitledPane restaurantsTP = new TitledPane("Restaurants", new Button("View"));
    private static final ArrayList<String> timeFrom = new ArrayList<>();
    private static final ArrayList<String> timeTo = new ArrayList<>();
    private static BorderPane rootScene;
    final Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();
    @FXML
    private final Accordion ac = new Accordion();
    @FXML
    private final BorderPane bPane = new BorderPane();
    @FXML
    private Label closeButton;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private DatePicker dp;
    @FXML
    private Button searchId;
    @FXML
    private JFXDrawer drawer;
    @FXML
    private JFXHamburger hamburger;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void closeSecondaryStage() {
        MainDisplay.secondaryStage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });
    }

    @FXML
    public void handleCloseButtonAction() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    public void hamburgerMenu() throws IOException {
        VBox vBox = FXMLLoader.load(getClass().getResource("/drawerMenuContent.fxml"));
        drawer.setSidePane(vBox);
        HamburgerBasicCloseTransition transition = new HamburgerBasicCloseTransition(hamburger);
        transition.setRate(-1);
        hamburger.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
            transition.setRate(transition.getRate() * -1);
            transition.play();
            if (drawer.isOpened()) {
                drawer.close();
            } else {
                drawer.open();
                drawer.setTranslateX(140);
            }
        });
    }

    @FXML
    public void handleLoginButton (javafx.event.ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (!UserCommunication.authenticate(username, password)) {
            return;
        }

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/calendarScene.fxml"));
            Parent loginParent = (Parent) fxmlLoader.load();
            MainDisplay.secondaryStage = new Stage();

            MainDisplay.secondaryStage.setScene(new Scene(loginParent, screenSize.getWidth(), screenSize.getHeight()));
            MainDisplay.secondaryStage.setTitle("Home");
            MainDisplay.secondaryStage.show();
            MainDisplay.primaryStage.close();

            drawer = (JFXDrawer) loginParent.lookup("#drawer");
            hamburger = (JFXHamburger) loginParent.lookup("#hamburger");
            hamburgerMenu();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleSignUpClick() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/registerScene.fxml"));
            Parent registerParent = fxmlLoader.load();
            MainDisplay.registerStage = new Stage();

            MainDisplay.registerStage.setResizable(true);
            MainDisplay.registerStage.setScene(new Scene(registerParent));
            MainDisplay.registerStage.setTitle("Register");
            MainDisplay.registerStage.show();

            drawer = (JFXDrawer) registerParent.lookup("#drawer");
            hamburger = (JFXHamburger) registerParent.lookup("#hamburger");
            hamburgerMenu();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleHomeButton() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/reservationsScene.fxml"));
            Parent calendarParent = fxmlLoader.load();

            MainDisplay.secondaryStage.setScene(new Scene(calendarParent, screenSize.getWidth(), screenSize.getHeight()));
            MainDisplay.secondaryStage.setTitle("Home");
            MainDisplay.secondaryStage.show();
            closeSecondaryStage();

            drawer = (JFXDrawer) calendarParent.lookup("#drawer");
            hamburger = (JFXHamburger) calendarParent.lookup("#hamburger");
            hamburgerMenu();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void handleReservationButton() {
        try {
            ObservableList<Room> rooms = FXCollections.observableList(RoomCommunication.getRooms());
            ObservableList<Building> buildingData = FXCollections.observableList(BuildingCommunication.getBuildings());

            TitledPane[] tps = new TitledPane[buildingData.size()];
            List<Button> buttons = new ArrayList<>();

            int c = 0; // count - for lists, c - for tps
            rootScene = FXMLLoader.load(getClass().getResource("/reservationsScene.fxml"));    // load the scene

            // fill the accordion
            for (int i = 0; i < buildingData.size(); i++) {

                //Look for rooms for the building i;
                ObservableList<Room> showRooms = FXCollections.observableArrayList();
                for (int k = 0; k < rooms.size(); k++) {
                    if (rooms.get(k).getBuilding().getName().equals(buildingData.get(i).getName())) {
                        showRooms.add(rooms.get(k));
                    }
                }


                //if there are rooms for the building i - show them;
                if (showRooms.size() != 0) {
                    VBox vBox = new VBox();
                    tps[c] = new TitledPane();

                    for (int j = 0; j < showRooms.size(); j++) {
                        HBox hBox = new HBox();

                        Label label1 = new Label(showRooms.get(j).getName());
                        label1.setStyle("-fx-font-weight: bold");

                        Label label2 = new Label("Capacity: " + showRooms.get(j).getCapacity() + " persons");
                        Button button1 = new Button("Reserve");
                        buttons.add(button1);


                        ObservableList<String> from = FXCollections.observableArrayList(timeFrom);
                        ObservableList<String> to = FXCollections.observableArrayList(timeTo);

                        ChoiceBox<String> cb = new ChoiceBox<>();
                        cb.setItems(from);
                        ChoiceBox<String> cbb = new ChoiceBox<>();
                        cbb.setItems(to);


                        hBox.getChildren().addAll(label1, label2, cb, cbb, button1);
                        hBox.setSpacing(150);
                        hBox.setStyle("-fx-padding: 8;" + "-fx-border-style: solid inside;"
                                    + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
                                    + "-fx-border-radius: 5;" + "-fx-border-color: lightgrey;");
                        vBox.getChildren().add(hBox);
                    }
                    tps[c].setText(buildingData.get(i).getName());
                    tps[c].setContent(vBox);
                    ac.getPanes().add(tps[c]);
                    c++;
                }

            }

            // load the accordion into the scene
            VBox vBox = new VBox(ac);
            bPane.setCenter(vBox);
            bPane.setPadding(new Insets(30, 5, 5, 10));
            rootScene.setCenter(bPane);


            // show the scene
            MainDisplay.secondaryStage.setScene(new Scene(rootScene, screenSize.getWidth(), screenSize.getHeight()));
            MainDisplay.secondaryStage.setTitle("Reservations");
            MainDisplay.secondaryStage.show();

            drawer = (JFXDrawer) rootScene.lookup("#drawer");
            hamburger = (JFXHamburger) rootScene.lookup("#hamburger");
            hamburgerMenu();

        } catch (Exception e) {
            e.printStackTrace();
        }
        closeSecondaryStage();
    }

    @FXML
    public void handleRestaurantsButton() {
        try {
            ObservableList<Building> buildingData = FXCollections.observableList(BuildingCommunication.getBuildings());
            ObservableList<Restaurant> restaurants = FXCollections.observableList(RestaurantCommunication.getRestaurants());

            TitledPane[] tps = new TitledPane[buildingData.size()];
            List<Button> buttons = new ArrayList<>();
            List<Label> labels = new ArrayList<>();

            // count - for lists, c - for tps
            int count = 0;
            int c = 0;

            // load the scene
            BorderPane rootScene = FXMLLoader.load(getClass().getResource("/restaurantsScene.fxml"));

            // fill the accordion
            for (int i = 0; i < buildingData.size(); i++) {

                //Look for restaurants for the building i;
                ObservableList<Restaurant> showRestaurants = FXCollections.observableArrayList();
                for (int k = 0; k < restaurants.size(); k++) {
                    if (restaurants.get(k).getBuilding().getName().equals(buildingData.get(i).getName())) {
                        showRestaurants.add(restaurants.get(k));
                    }
                }

                //if there are restaurants for the building i - show them;
                if (showRestaurants.size() != 0) {
                    tps[c] = new TitledPane();
                    GridPane grid = new GridPane();
                    ColumnConstraints colConst = new ColumnConstraints();
                    colConst.setPercentWidth(100 / 2);
                    grid.getColumnConstraints().add(colConst);
                    grid.setVgap(4);
                    grid.setPadding(new Insets(5, 5, 5, 5));

                    for (Restaurant restaurant : restaurants) {
                        System.out.println(buildingData.get(i).getName() + " " + restaurant.getName());
                    }

                    for (int j = 0; j < showRestaurants.size(); j++) {
                        Label label1 = new Label(showRestaurants.get(j).getName());
                        labels.add(label1);
                        Button button1 = new Button("Menu");
                        buttons.add(button1);

                        grid.add(labels.get(count), 0, j);
                        grid.add(buttons.get(count), 1, j);
                        count = count + 1;
                    }
                    tps[c].setText(buildingData.get(i).getName());
                    tps[c].setContent(grid);
                    ac.getPanes().add(tps[c]);
                    c++;
                }

            }

            // load the accordion into the scene
            VBox vBox = new VBox(ac);
            bPane.setCenter(vBox);
            bPane.setPadding(new Insets(30, 5, 5, 10));
            rootScene.setCenter(bPane);

            MainDisplay.secondaryStage.setScene(new Scene(rootScene, screenSize.getWidth(), screenSize.getHeight()));
            MainDisplay.secondaryStage.setTitle("Restaurants");
            MainDisplay.secondaryStage.show();
            closeSecondaryStage();

            drawer = (JFXDrawer) rootScene.lookup("#drawer");
            hamburger = (JFXHamburger) rootScene.lookup("#hamburger");
            hamburgerMenu();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleFriendsButton() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/friendsScene.fxml"));
            Parent friendsParent = fxmlLoader.load();

            MainDisplay.secondaryStage.setScene(new Scene(friendsParent, screenSize.getWidth(), screenSize.getHeight()));
            MainDisplay.secondaryStage.setTitle("Friends");
            MainDisplay.secondaryStage.show();
            closeSecondaryStage();

            drawer = (JFXDrawer) friendsParent.lookup("#drawer");
            hamburger = (JFXHamburger) friendsParent.lookup("#hamburger");
            hamburgerMenu();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleSettingsButton() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/settingsScene.fxml"));
            Parent settingsParent = (Parent) fxmlLoader.load();

            MainDisplay.secondaryStage.setScene(new Scene(settingsParent, screenSize.getWidth(), screenSize.getHeight()));
            MainDisplay.secondaryStage.setTitle("Settings");
            MainDisplay.secondaryStage.show();
            closeSecondaryStage();

            drawer = (JFXDrawer) settingsParent.lookup("#drawer");
            hamburger = (JFXHamburger) settingsParent.lookup("#hamburger");
            hamburgerMenu();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleAdminButton() throws IOException {
        // load main admin scene
        BorderPane rootScene = FXMLLoader.load(getClass().getResource("/adminScene.fxml"));

        ac.getPanes().addAll(buildingTP, roomsTP, restaurantsTP);

        AdminSceneController.BuildingView();
        AdminSceneController.RoomView();
//        AdminSceneController.RestaurantView();

        // load everything
        VBox mainVerticalBox = new VBox(ac);
        bPane.setCenter(mainVerticalBox);
//        bPane.setPadding(new Insets(10, 50, 10, 50));
        rootScene.setCenter(bPane);

        // show the scene
        MainDisplay.secondaryStage.setScene(new Scene(rootScene, screenSize.getWidth(), screenSize.getHeight()));
        MainDisplay.secondaryStage.setTitle("Admin");
        MainDisplay.secondaryStage.show();
        closeSecondaryStage();

        drawer = (JFXDrawer) rootScene.lookup("#drawer");
        hamburger = (JFXHamburger) rootScene.lookup("#hamburger");
        hamburgerMenu();

    }
}
