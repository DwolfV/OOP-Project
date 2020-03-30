package nl.tudelft.oopp.demo.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import nl.tudelft.oopp.demo.helperclasses.*;

public class AdminSceneController implements Initializable {

    private static Rectangle2D screenBounds;

    public MainSceneController mainSceneController;

    @FXML private Accordion ac;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        screenBounds = Screen.getPrimary().getBounds();

        // Building TitledPane
        BorderPane borderPaneInfo = AdminBuildingPane.getBuildingInfoBP();
        BorderPane borderPaneTime = AdminBuildingPane.getBuildingTimesBP();
        VBox vboxBuilding = new VBox();
        vboxBuilding.getChildren().setAll(borderPaneInfo, borderPaneTime);

        ScrollPane scrollPaneBuilding = getScrollPane(vboxBuilding);

        TitledPane buildingTP = new TitledPane();
        buildingTP.setText("Buildings");
        buildingTP.setContent(scrollPaneBuilding);

        // Room TitledPane
        BorderPane borderPaneRoom = AdminRoomPane.getRoomBP();
        VBox vboxRoom = new VBox();
        vboxRoom.getChildren().setAll(borderPaneRoom);

        ScrollPane scrollPaneRoom = getScrollPane(vboxRoom);

        TitledPane roomTP = new TitledPane();
        roomTP.setText("Rooms");
        roomTP.setContent(scrollPaneRoom);

        // Restaurant TitledPane
        BorderPane borderPaneRestaurant = AdminRestaurantPane.getRestaurantBP();
        VBox vboxRestaurant = new VBox();
        vboxRestaurant.getChildren().setAll(borderPaneRestaurant);

        ScrollPane scrollPaneRestaurant = getScrollPane(vboxRestaurant);

        TitledPane restaurantTP = new TitledPane();
        restaurantTP.setText("Restaurants");
        restaurantTP.setContent(scrollPaneRestaurant);

        ac.setPrefWidth(screenBounds.getWidth() - 400);
        ac.getPanes().addAll(buildingTP, roomTP, restaurantTP);
    }

    public void setControllers(MainSceneController mainSceneController) {
        this.mainSceneController = mainSceneController;
    }

    public static BorderPane getBorderPane(Pane centerPane, HBox bottomHBox, VBox rightVBox) {

        bottomHBox.getStyleClass().add("admin-bottom");
        rightVBox.getStyleClass().add("admin-right");

        // All elements in BorderPane
        BorderPane borderPane = new BorderPane();
        borderPane.getStyleClass().add("admin-border-pane");
        borderPane.setCenter(centerPane);
        borderPane.setRight(rightVBox);
        borderPane.setBottom(bottomHBox);

        return borderPane;
    }

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
        gridPane.getStyleClass().add("admin-grid");
        gridPane.setPrefWidth(screenBounds.getWidth() - 420);

        // GridPane inside ScrollPane
        ScrollPane scroll = new ScrollPane();
        scroll.setMaxHeight(screenBounds.getHeight() - 300);
        scroll.setMaxWidth(screenBounds.getWidth());
        scroll.setContent(gridPane);
        scroll.getStyleClass().setAll("scroll-pane-admin");

        // Set scroll speed
        scroll.getContent().setOnScroll(scrollEvent -> {
            double deltaY = scrollEvent.getDeltaY() * 0.01;
            scroll.setVvalue(scroll.getVvalue() - deltaY);
        });

        return scroll;
    }
}
