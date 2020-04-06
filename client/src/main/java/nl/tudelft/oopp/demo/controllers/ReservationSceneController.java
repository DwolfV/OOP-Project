package nl.tudelft.oopp.demo.controllers;

import static java.util.function.Predicate.not;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import nl.tudelft.oopp.demo.communication.Authenticator;
import nl.tudelft.oopp.demo.communication.BuildingCommunication;
import nl.tudelft.oopp.demo.communication.OccasionCommunication;
import nl.tudelft.oopp.demo.communication.RoomCommunication;
import nl.tudelft.oopp.demo.communication.RoomReservationCommunication;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Occasion;
import nl.tudelft.oopp.demo.entities.Room;

public class ReservationSceneController implements Initializable {

    public HamburgerMenuSceneController hamburgerMenuSceneController;

    @FXML
    private BorderPane borderPane;

    @FXML
    private Accordion ac = new Accordion();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        borderPane.setPrefWidth(screenBounds.getWidth()-400);
    }

    /**
     * The method is used to set up the reservation scene when a user clicks on the "Reservations" button.
     */
    public void init() {
        Label text = new Label("Pick a date, capacity and filters to see the available rooms");
        borderPane.setCenter(text);
        text.setStyle("-fx-padding: 50;" + "-fx-font-weight: bold");
        SidebarSceneController ctrl = hamburgerMenuSceneController.sidebarFilterLoader.getController();
        Button search = ctrl.searchId;

        search.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                List<String> filters = ctrl.handleSearchClick();
                int capacity = ctrl.getCapacity();
                filteredSearch(filters, capacity);
            }
        });
    }


    /**
     * The method is used to show the filtered rooms when a user clicks on the "Search" button.
     */
    public void filteredSearch(List<String> filters, int capacity) {
        // clear the previous result
        ac.getPanes().clear();
        int notFound = 0;

        ObservableList<Building> buildingData = FXCollections.observableList(BuildingCommunication.getBuildings());

        TitledPane[] tps = new TitledPane[buildingData.size()];
        List<Button> buttons = new ArrayList<>();

        SidebarSceneController ctrl = hamburgerMenuSceneController.sidebarFilterLoader.getController();

        int c = 0; // c - for tps

        // fill the accordion
        for (int i = 0; i < buildingData.size(); i++) {
            LocalDate date = ctrl.onPickDate();
            System.out.println(date);
            //Look for the rooms of the building i with the applied filters
            ObservableList<Room> showRooms = FXCollections.observableList(RoomCommunication.getFilteredRoomsByBuilding(buildingData.get(i).getId(), date, Authenticator.ID, capacity, filters));

            if (showRooms.size() == 0) {
                notFound++;
            }

            //if there are rooms for the building i - show them
            if (showRooms.size() != 0) {
                List<LocalTime> buildingTime = BuildingCommunication.getTimebyBuildingId(buildingData.get(i).getId());

                List<Occasion> occasions = OccasionCommunication.getOccasionsByBuilding(buildingData.get(i).getId());
                LocalTime startTime = buildingTime.get(0);
                LocalTime endTime = buildingTime.get(1);
                // Check the occasions for the building
                if (occasions.size() != 0) {
                    for (int oc = 0; oc < occasions.size(); oc++) {
                        if (occasions.get(oc).getDate().isEqual(date)) {
                            startTime = occasions.get(oc).getOpenTime();
                            endTime = occasions.get(oc).getCloseTime();
                        }
                    }
                }

                VBox vertBox = new VBox();
                tps[c] = new TitledPane();
                for (int j = 0; j < showRooms.size(); j++) {
                    Long roomId = showRooms.get(j).getId();

                    // Check if the room is already reserved for any time
                    Map<LocalTime, LocalTime> reservedTime = RoomReservationCommunication.getAllRoomReservationTimesPerRoomAndDate(roomId, date);
                    // A list for already reserved time
                    ArrayList<LocalTime> reservedT = new ArrayList<>();
                    if (!reservedTime.isEmpty()) {
                        reservedT = reservedTimeToDelete(reservedTime);
                    }

                    ArrayList<LocalTime> timeFrom = setStartTime(startTime, endTime, reservedT);
                    ArrayList<LocalTime> timeTo = setEndTime(startTime, endTime, reservedT);
                    ObservableList<LocalTime> from = FXCollections.observableArrayList(timeFrom);
                    ObservableList<LocalTime> to = FXCollections.observableArrayList(timeTo);

                    HBox horizBox = new HBox();
                    Label label1 = new Label(showRooms.get(j).getName());
                    label1.setStyle("-fx-font-weight: bold");

                    Label label2 = new Label("Capacity: " + showRooms.get(j).getCapacity() + " persons");
                    Button button1 = new Button("Reserve");
                    button1.getStyleClass().setAll("restaurant-menu-button");
                    buttons.add(button1);

                    // A combobox for start time
                    ComboBox<LocalTime> cb = new ComboBox<>();
                    cb.setItems(from);
                    // A combobox for end time
                    ComboBox<LocalTime> cbb = new ComboBox<>();
                    final LocalTime[] stt = new LocalTime[1];
                    final LocalTime[] ett = new LocalTime[1];

                    // A list with times to delete from end times
                    List<LocalTime> timeToDelete = new ArrayList<>();

                    //EventHandler for the start time combobox
                    EventHandler<ActionEvent> event =
                        new EventHandler<ActionEvent>() {
                            public void handle(ActionEvent e) {
                                stt[0] = cb.getValue();

                                timeToDelete.clear();
                                for (LocalTime time : to) {
                                    if (time.isBefore(stt[0])) {
                                        timeToDelete.add(time);
                                    }
                                }
                                timeToDelete.add(stt[0]);

                                //filtering end time
                                Set<LocalTime> result = to.stream()
                                        .distinct()
                                        .filter(not(timeToDelete::contains))
                                        .collect(Collectors.toSet());
                                List<LocalTime> filteredTimeList = new ArrayList<>(result);
                                Collections.sort(filteredTimeList);
                                //A list with the correct end time (contains only times after the start time)
                                ObservableList<LocalTime> readableCloseTime = FXCollections.observableArrayList(filteredTimeList);
                                //set combobox for end time
                                cbb.setItems(readableCloseTime);
                            }
                        };
                    // Set on action
                    cb.setOnAction(event);

                    EventHandler<ActionEvent> event1 =
                        new EventHandler<ActionEvent>() {
                            public void handle(ActionEvent e) {
                                ett[0] = cbb.getValue();
                            }
                        };
                    // Set on action
                    cbb.setOnAction(event1);

                    // An action for a reserve button
                    button1.setOnAction(e -> {
                        RoomReservationCommunication.addRoomReservation(date, stt[0], ett[0], roomId);
                        System.out.println(date + " " + stt[0] + " " + ett[0] + " " + roomId);
                    });

                    GridPane grid = new GridPane();
                    ColumnConstraints constraint1 = new ColumnConstraints();
                    constraint1.setPercentWidth(100 / 5);
                    ColumnConstraints constraint2 = new ColumnConstraints();
                    constraint2.setPercentWidth(100 / 5);
                    ColumnConstraints constraint3 = new ColumnConstraints();
                    constraint3.setPercentWidth(100 / 5);
                    ColumnConstraints constraint4 = new ColumnConstraints();
                    constraint4.setPercentWidth(100 / 5);
                    ColumnConstraints constraint5 = new ColumnConstraints();
                    constraint4.setPercentWidth(100 / 5);
                    grid.getColumnConstraints().setAll(
                            constraint1,
                            constraint2,
                            constraint3,
                            constraint4,
                            constraint5
                    );
                    grid.add(label1, 0, j);
                    grid.add(label2, 1, j);
                    grid.add(cb, 2, j);
                    grid.add(cbb, 3, j);
                    grid.add(button1, 4, j);

//                    horizBox.setHgrow(label1, Priority.ALWAYS);
//                    horizBox.setHgrow(label2, Priority.ALWAYS);
//                    horizBox.setHgrow(cb, Priority.ALWAYS);
//                    horizBox.setHgrow(cbb, Priority.ALWAYS);
//                    horizBox.setHgrow(button1, Priority.ALWAYS);
//                    label1.setMinWidth(70);
//                    label2.setMinWidth(100);
//                    cb.setMinWidth(70);
//                    cbb.setMinWidth(70);
//                    button1.setMinWidth(70);
//
//                    horizBox.getChildren().addAll(label1, label2, cb, cbb, button1);
//                    horizBox.setSpacing(150);
//                    horizBox.setStyle("-fx-padding: 8;" + "-fx-border-style: solid inside;"
//                        + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
//                        + "-fx-border-radius: 5;" + "-fx-border-color: lightgrey;");

                    SplitPane splitPane = new SplitPane();
                    splitPane.getStyleClass().add("restaurant-split-pane");

                    vertBox.getChildren().addAll(grid, splitPane);
                }
                vertBox.setSpacing(5);
                tps[c].setText(buildingData.get(i).getName());
                tps[c].setContent(vertBox);
                ac.getPanes().add(tps[c]);
                c++;
            }

            // if there is no available rooms
            if (notFound == buildingData.size()) {
                Label text = new Label("There are no available rooms");
                borderPane.setCenter(text);
                text.setStyle("-fx-padding: 50;" + "-fx-font-weight: bold");
            } else {
                // load the accordion into the scene if there are available rooms
                VBox box = new VBox(ac);
                borderPane.setCenter(box);
            }

        }
    }

    /**
     * The method is used to create a list of start time for a room.
     * @return a list of start time.
     */
    public ArrayList<LocalTime> setStartTime(LocalTime st, LocalTime et, ArrayList<LocalTime> tt) {
        ArrayList<LocalTime> timeFrom = new ArrayList<>();

        for (LocalTime tm = st; tm.isBefore(et); tm = tm.plusMinutes(30)) {
            timeFrom.add(tm);
        }

        if (tt.size() != 0) {
            for (int h = 0; h < tt.size() - 1;h++) {
                timeFrom.remove(tt.get(h));
            }
        }

        return timeFrom;
    }


    /**
     * The method is used to create a list of end time for a room.
     * @return a list of end time.
     */
    public ArrayList<LocalTime> setEndTime(LocalTime st, LocalTime et, ArrayList<LocalTime> tt) {
        ArrayList<LocalTime> timeTo = new ArrayList<>();

        for (LocalTime tm = st.plusMinutes(30); tm.isBefore(et.plusMinutes(30)); tm = tm.plusMinutes(30)) {
            timeTo.add(tm);
        }

        if (tt.size() != 0) {
            for (int h = 1; h < tt.size();h++) {
                timeTo.remove(tt.get(h));
            }
        }

        return timeTo;
    }

    /**
     * The method is used to create a list of times to delete from a list of end times.
     * @return a list of times.
     */
    public ArrayList<LocalTime> reservedTimeToDelete(Map<LocalTime, LocalTime> reservedTime) {
        ArrayList<LocalTime> result = new ArrayList<>();

        String str = reservedTime.toString();
        str  = str.replace("}", "");
        str = str.replace("{", "");
        String[] timeR;
        if (str.contains(",")) {
            timeR = str.split(", ");
        } else {
            timeR = new String[1];
            timeR[0] = str;
        }
        for (int r = 0; r < timeR.length;r++) {
            String[] temp = timeR[r].split("=");
            String one = temp[0];
            String two = temp[1];
            for (LocalTime tm = LocalTime.parse(one); tm.isBefore(LocalTime.parse(two)); tm = tm.plusMinutes(30)) {
                result.add(tm);
            }
            result.add(LocalTime.parse(two));
        }
        return result;
    }

    public void setControllers(HamburgerMenuSceneController hamburgerMenuSceneController) {
        this.hamburgerMenuSceneController = hamburgerMenuSceneController;
    }
}
