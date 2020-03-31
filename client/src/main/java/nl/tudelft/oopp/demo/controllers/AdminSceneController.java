package nl.tudelft.oopp.demo.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Accordion;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;

import nl.tudelft.oopp.demo.helperclasses.AdminBuildingPane;
import nl.tudelft.oopp.demo.helperclasses.AdminRestaurantPane;
import nl.tudelft.oopp.demo.helperclasses.AdminRoomPane;

public class AdminSceneController implements Initializable {

    public static TitledPane buildingTP;
    public static TitledPane roomTP;
    public static TitledPane restaurantTP;
    private static Rectangle2D screenBounds;
    public static BorderPane adminBorderPane;
    public MainSceneController mainSceneController;

    @FXML private Accordion ac;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadAdminScene(ac);
        ac.setExpandedPane(buildingTP);
    }

    /**
     * Load the admin scene.
     * @param ac Accordion
     */
    public static void loadAdminScene(Accordion ac) {
        screenBounds = Screen.getPrimary().getBounds();

        // Building TitledPane
        BorderPane borderPaneInfo = AdminBuildingPane.getBuildingInfoBP(ac);
        BorderPane borderPaneTime = AdminBuildingPane.getBuildingTimesBP(ac);

        VBox vboxBuilding = new VBox();
        vboxBuilding.getChildren().setAll(borderPaneInfo, borderPaneTime);

        ScrollPane scrollPaneBuilding = getScrollPane(vboxBuilding);

        buildingTP = new TitledPane();
        buildingTP.setText("Buildings");
        buildingTP.setContent(scrollPaneBuilding);

        // Room TitledPane
        BorderPane borderPaneRoom = AdminRoomPane.getRoomBP(ac);
        VBox vboxRoom = new VBox();
        vboxRoom.getChildren().setAll(borderPaneRoom);

        ScrollPane scrollPaneRoom = getScrollPane(vboxRoom);

        roomTP = new TitledPane();
        roomTP.setText("Rooms");
        roomTP.setContent(scrollPaneRoom);

        // Restaurant TitledPane
        BorderPane borderPaneRestaurant = AdminRestaurantPane.getRestaurantBP(ac);
        VBox vboxRestaurant = new VBox();
        vboxRestaurant.getChildren().setAll(borderPaneRestaurant);

        ScrollPane scrollPaneRestaurant = getScrollPane(vboxRestaurant);

        restaurantTP = new TitledPane();
        restaurantTP.setText("Restaurants");
        restaurantTP.setContent(scrollPaneRestaurant);

        ac.setPrefWidth(screenBounds.getWidth() - 400);
        ac.getPanes().setAll(buildingTP, roomTP, restaurantTP);
    }

    public void setControllers(MainSceneController mainSceneController) {
        this.mainSceneController = mainSceneController;
    }

    /**
     * Get the border pane.
     * @param centerPane center of border pane
     * @param bottomHBox bottom of border pane
     * @param rightVBox right of border pane
     * @return
     */
    public static BorderPane getBorderPane(TableView<Object> centerPane, HBox bottomHBox, VBox rightVBox) {

        centerPane.getStyleClass().add("center");
        bottomHBox.getStyleClass().add("bottom");
        rightVBox.getStyleClass().add("right");

        // All elements in BorderPane
        BorderPane borderPane = new BorderPane();
        borderPane.getStyleClass().add("border-pane");
        borderPane.setCenter(centerPane);
        borderPane.setRight(rightVBox);
        borderPane.setBottom(bottomHBox);
        adminBorderPane = borderPane;

        return borderPane;
    }

    /**
     * Get the scrollPane of the borderPanes.
     * @param vbox VBox of the borderPanes
     * @return ScrollPane
     */
    public static ScrollPane getScrollPane(VBox vbox) {

        // VBox inside GridPane
        GridPane gridPane = new GridPane();
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(10);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(80);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(10);
        gridPane.getColumnConstraints().addAll(col1, col2, col3);

        gridPane.add(vbox, 1,0);
        gridPane.getStyleClass().add("grid");
        gridPane.setPrefWidth(screenBounds.getWidth() - 420);

        // GridPane inside ScrollPane
        ScrollPane scroll = new ScrollPane();
        scroll.setMaxHeight(screenBounds.getHeight() - 300);
        scroll.setMaxWidth(screenBounds.getWidth());
        scroll.setContent(gridPane);
        scroll.getStyleClass().setAll("scroll-pane");

        // Set scroll speed
        scroll.getContent().setOnScroll(scrollEvent -> {
            double deltaY = scrollEvent.getDeltaY() * 0.01;
            scroll.setVvalue(scroll.getVvalue() - deltaY);
        });

        return scroll;
    }
}
