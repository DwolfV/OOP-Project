package nl.tudelft.oopp.demo.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import nl.tudelft.oopp.demo.communication.BuildingCommunication;
import nl.tudelft.oopp.demo.communication.RoomCommunication;
import nl.tudelft.oopp.demo.communication.RoomReservationCommunication;
import nl.tudelft.oopp.demo.helperclasses.Building;
import nl.tudelft.oopp.demo.helperclasses.Room;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;


public class ReservationSceneController implements Initializable {

    public HamburgerMenuSceneController hamburgerMenuSceneController;

    @FXML
    private BorderPane borderPane;

    @FXML
    private Accordion ac = new Accordion();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

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
        /*long roomId = 71;
        LocalDate dt = LocalDate.parse("2020-04-02");
        Map<LocalTime, LocalTime> date = RoomReservationCommunication.getAllRoomReservationTimesPerRoomAndDate(roomId, dt);

        ArrayList<LocalTime> begin = new ArrayList<>();
        ArrayList<LocalTime> end = new ArrayList<>();

        if(date!=null){
            String str = date.toString();
            str  = str.replace("}", "");
            str = str.replace("{", "");
            String[] timeR = str.split(",");
            for(int r=0; r<timeR.length;r++){
                String[] temp= timeR[r].split("=");
                begin.add(LocalTime.parse(temp[0]));
                end.add(LocalTime.parse(temp[1]));
            }
        }
        System.out.println(begin + " " + end);*/

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

        ObservableList<Building> buildingData = FXCollections.observableList(BuildingCommunication.getBuildings());

        TitledPane[] tps = new TitledPane[buildingData.size()];
        List<Button> buttons = new ArrayList<>();

        SidebarSceneController ctrl = hamburgerMenuSceneController.sidebarFilterLoader.getController();

        int c = 0; // c - for tps

        // fill the accordion
        for (int i = 0; i < buildingData.size(); i++) {

            //Look for the rooms of the building i with the filters
            ObservableList<Room> showRooms = FXCollections.observableList(RoomCommunication.getFilteredRoomsByBuilding(buildingData.get(i).getId(), capacity, filters));

            //if there are rooms for the building i - show them;
            if (showRooms.size() != 0) {
                //get time for all the rooms of building i
                List<LocalTime> buildingTime = BuildingCommunication.getTimebyBuildingId(buildingData.get(i).getId());
                String startTime = String.valueOf(buildingTime.get(0));
                String endTime = String.valueOf(buildingTime.get(1));
                System.out.println(startTime + " " + endTime);
                int st;
                int et;

                String[] time = startTime.split(":", 2);
                String check = time[0];
                st = Integer.valueOf(check);
                time = endTime.split(":", 2);
                check = time[0];
                et = Integer.valueOf(check);

                ArrayList<LocalTime> timeFrom;
                ArrayList<LocalTime> timeTo;

                VBox vertBox = new VBox();
                tps[c] = new TitledPane();

                for (int j = 0; j < showRooms.size(); j++) {
                    Long roomId = showRooms.get(j).getId();
                    LocalDate date = ctrl.onPickDate();
                    Map<LocalTime, LocalTime> reservedTime = RoomReservationCommunication.getAllRoomReservationTimesPerRoomAndDate(roomId, date);

                    ArrayList<LocalTime> tt = new ArrayList<>();

                    if (!reservedTime.isEmpty()) {
                        String str = reservedTime.toString();
                        str  = str.replace("}", "");
                        str = str.replace("{", "");
                        String[] timeR;
                        if (str.contains(",")) {
                            timeR = str.split(",");
                        } else {
                            timeR = new String[1];
                            timeR[0] = str;
                        }
                        for (int r = 0; r < timeR.length;r++) {
                            String[] temp = timeR[r].split("=");
                            String one = temp[0];
                            String two = temp[1];
                            for (LocalTime tm = LocalTime.parse(one); tm.isBefore(LocalTime.parse(two)); tm = tm.plusMinutes(30)) {
                                tt.add(tm);
                            }
                            tt.add(LocalTime.parse(two));
                        }
                    }

                    timeFrom = setStartTime(st, et, tt);
                    timeTo = setEndTime(st, et, tt);

                    ObservableList<LocalTime> from = FXCollections.observableArrayList(timeFrom);
                    ObservableList<LocalTime> to = FXCollections.observableArrayList(timeTo);


                    HBox horizBox = new HBox();
                    Label label1 = new Label(showRooms.get(j).getName());
                    label1.setStyle("-fx-font-weight: bold");

                    Label label2 = new Label("Capacity: " + showRooms.get(j).getCapacity() + " persons");
                    Button button1 = new Button("Reserve");
                    buttons.add(button1);


                    ComboBox<LocalTime> cb = new ComboBox<>();
                    cb.setItems(from);
                    ComboBox<LocalTime> cbb = new ComboBox<>();
                    cbb.setItems(to);
                    final LocalTime[] stt = new LocalTime[1];
                    final LocalTime[] ett = new LocalTime[1];

                    EventHandler<ActionEvent> event =
                        new EventHandler<ActionEvent>() {
                            public void handle(ActionEvent e) {
                                stt[0] = cb.getValue();
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


                    button1.setOnAction(e -> {
                        RoomReservationCommunication.addRoomReservation(date, stt[0], ett[0], roomId);
                        System.out.println(date + " " + stt[0] + " " + ett[0] + " " + roomId);
                    });


                    horizBox.getChildren().addAll(label1, label2, cb, cbb, button1);
                    horizBox.setSpacing(150);
                    horizBox.setStyle("-fx-padding: 8;" + "-fx-border-style: solid inside;"
                        + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
                        + "-fx-border-radius: 5;" + "-fx-border-color: lightgrey;");
                    vertBox.getChildren().add(horizBox);
                }
                tps[c].setText(buildingData.get(i).getName());
                tps[c].setContent(vertBox);
                ac.getPanes().add(tps[c]);
                c++;
            }

            // load the accordion into the scene
            VBox box = new VBox(ac);
            borderPane.setCenter(box);
            borderPane.setPadding(new Insets(20, 0, 0, 50));
        }

    }

    /**
     * The method is used to create a list of start time for a room.
     * @return a list of start time.
     */
    public ArrayList<LocalTime> setStartTime(int st, int et, ArrayList<LocalTime> tt) {
        ArrayList<LocalTime> timeFrom = new ArrayList<>();
        for (int b = st; b < et; b++) {
            if (b < 10) {
                timeFrom.add(LocalTime.parse("0" + b + ":00"));
                timeFrom.add(LocalTime.parse("0" + b + ":30"));
            } else {
                timeFrom.add(LocalTime.parse(b + ":00"));
                timeFrom.add(LocalTime.parse(b + ":30"));
            }
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
    public ArrayList<LocalTime> setEndTime(int st, int et, ArrayList<LocalTime> tt) {
        ArrayList<LocalTime> timeTo = new ArrayList<>();
        for (int b = st; b <= et; b++) {
            if (b < 10) {
                if (b != st && b != et) {
                    timeTo.add(LocalTime.parse("0" + b + ":00"));
                    timeTo.add(LocalTime.parse("0" + b + ":30"));
                } else if (b == st) {
                    timeTo.add(LocalTime.parse("0" + b + ":30"));
                }
            } else {
                if (b != st && b != et) {
                    timeTo.add(LocalTime.parse(b + ":00"));
                    timeTo.add(LocalTime.parse(b + ":30"));
                } else if (b == st) {
                    timeTo.add(LocalTime.parse(b + ":30"));
                } else if (b == et) {
                    timeTo.add(LocalTime.parse(b + ":00"));
                }
            }
        }
        if (tt.size() != 0) {
            for (int h = 1; h < tt.size();h++) {
                timeTo.remove(tt.get(h));
            }
        }

        return timeTo;
    }

    public void setControllers(HamburgerMenuSceneController hamburgerMenuSceneController) {
        this.hamburgerMenuSceneController = hamburgerMenuSceneController;
    }
}
