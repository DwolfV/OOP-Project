package nl.tudelft.oopp.demo.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
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
import nl.tudelft.oopp.demo.helperclasses.AdminRolePane;
import nl.tudelft.oopp.demo.helperclasses.AdminRoomPane;
import nl.tudelft.oopp.demo.helperclasses.AdminSupplyPane;

public class AdminSceneController implements Initializable {

    public static TitledPane buildingTP;
    public static TitledPane roomTP;
    public static TitledPane restaurantTP;
    public static TitledPane supplyTP;
    public static TitledPane rolesTP;
    public static BorderPane adminBorderPane;
    private static Rectangle2D screenBounds;

    private static VBox vboxBuilding;
    private static Label buildTitle;
    private static Label occTitle;
    private static VBox vboxRoom;
    private static Label roomTitle;
    private static VBox vboxRestaurant;
    private static Label resTitle;
    private static VBox vboxSupply;
    private static Label supplyTitle;
    private static VBox vboxRoles;
    private static Label rolesTitle;

    public MainSceneController mainSceneController;

    @FXML
    private Accordion ac;

    /**
     * Load the buildingTP scene.
     *
     * @param ac Accordion
     */
    public static void loadBuildingTP(Accordion ac) {
        vboxBuilding = new VBox();
        buildTitle = new Label("Add A Building:");
        SplitPane splitPane1 = new SplitPane();
        BorderPane borderPaneInfo = AdminBuildingPane.getBuildingInfoBP(ac);
        occTitle = new Label("Add A Free Day:");
        SplitPane splitPane2 = new SplitPane();
        BorderPane borderPaneTime = AdminBuildingPane.getBuildingTimesBP(ac);
        vboxBuilding.getChildren().setAll(buildTitle, splitPane1, borderPaneInfo, occTitle, splitPane2, borderPaneTime);

        ScrollPane scrollPaneBuilding = getScrollPane(vboxBuilding);

        buildingTP = new TitledPane();
        buildingTP.setText("Buildings");
        buildingTP.setContent(scrollPaneBuilding);

        vboxBuilding.getStyleClass().add("v-boxes");
        buildTitle.getStyleClass().setAll("titles");
        occTitle.getStyleClass().setAll("titles");

        ac.getPanes().set(0, buildingTP);
    }

    /**
     * Load the roomTP scene.
     *
     * @param ac Accordion
     */
    public static void loadRoomTP(Accordion ac) {
        vboxRoom = new VBox();
        roomTitle = new Label("Add A Room:");
        SplitPane splitPane3 = new SplitPane();
        BorderPane borderPaneRoom = AdminRoomPane.getRoomBP(ac);
        vboxRoom.getChildren().setAll(roomTitle, splitPane3, borderPaneRoom);

        ScrollPane scrollPaneRoom = getScrollPane(vboxRoom);

        roomTP = new TitledPane();
        roomTP.setText("Rooms");
        roomTP.setContent(scrollPaneRoom);

        vboxRoom.getStyleClass().add("v-boxes");
        roomTitle.getStyleClass().setAll("titles");

        ac.getPanes().set(1, roomTP);
    }

    /**
     * Load the restaurantTP scene.
     *
     * @param ac Accordion
     */
    public static void loadRestaurantTP(Accordion ac) {
        vboxRestaurant = new VBox();
        resTitle = new Label("Add A Restaurant:");
        SplitPane splitPane4 = new SplitPane();
        BorderPane borderPaneRestaurant = AdminRestaurantPane.getRestaurantBP(ac);
        vboxRestaurant.getChildren().setAll(resTitle, splitPane4, borderPaneRestaurant);

        ScrollPane scrollPaneRestaurant = getScrollPane(vboxRestaurant);

        restaurantTP = new TitledPane();
        restaurantTP.setText("Restaurants");
        restaurantTP.setContent(scrollPaneRestaurant);

        vboxRestaurant.getStyleClass().add("v-boxes");
        resTitle.getStyleClass().setAll("titles");

        ac.getPanes().set(2, restaurantTP);
    }

    /**
     * Load the supplyTP scene.
     *
     * @param ac Accordion
     */
    public static void loadSupplyTP(Accordion ac) {
        vboxSupply = new VBox();
        supplyTitle = new Label("Add A Supply:");
        SplitPane splitPane5 = new SplitPane();
        BorderPane borderPaneSupply = AdminSupplyPane.getSupplyBP(ac);
        vboxSupply.getChildren().setAll(supplyTitle, splitPane5, borderPaneSupply);

        ScrollPane scrollPaneSupply = getScrollPane(vboxSupply);

        supplyTP = new TitledPane();
        supplyTP.setText("Supplies");
        supplyTP.setContent(scrollPaneSupply);

        vboxSupply.getStyleClass().add("v-boxes");
        supplyTitle.getStyleClass().setAll("titles");

        ac.getPanes().set(3, supplyTP);
    }

    /**
     * Load the rolesTP scene.
     *
     * @param ac Accordion
     */
    public static void loadRolesTP(Accordion ac) {
        vboxRoles = new VBox();
        rolesTitle = new Label("Modify User Roles:");
        SplitPane splitPane6 = new SplitPane();
        BorderPane borderPaneRoles = AdminRolePane.getRolesBP(ac);
        vboxRoles.getChildren().setAll(rolesTitle, splitPane6, borderPaneRoles);

        ScrollPane scrollPaneRoles = getScrollPane(vboxRoles);

        rolesTP = new TitledPane();
        rolesTP.setText("Roles");
        rolesTP.setContent(scrollPaneRoles);

        ac.getPanes().set(4, rolesTP);
    }

    /**
     * Load the admin scene.
     *
     * @param ac Accordion
     */
    public static void loadAdminScene(Accordion ac) {
        screenBounds = Screen.getPrimary().getBounds();

        ac.getPanes().setAll(null, null, null, null, null);


        loadBuildingTP(ac);
        loadRoomTP(ac);
        loadRestaurantTP(ac);
        loadSupplyTP(ac);
        loadRolesTP(ac);

        // Set Panes
        ac.setPrefWidth(screenBounds.getWidth() - 400);
    }

    /**
     * Get the border pane.
     *
     * @param centerPane center of border pane
     * @param bottomHBox bottom of border pane
     * @param rightVBox  right of border pane
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
     *
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

        gridPane.add(vbox, 1, 0);
        gridPane.getStyleClass().add("grid");
        gridPane.setPrefWidth(screenBounds.getWidth() - 420);

        // GridPane inside ScrollPane
        ScrollPane scroll = new ScrollPane();
        scroll.setMaxHeight(screenBounds.getHeight() - 400);
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadAdminScene(ac);

        ac.setExpandedPane(buildingTP);
    }

    public void setControllers(MainSceneController mainSceneController) {
        this.mainSceneController = mainSceneController;
    }
}
