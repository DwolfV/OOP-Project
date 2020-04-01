package nl.tudelft.oopp.demo.controllers;

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
        //borderPane.setCenter(text);
        borderPane.setTop(text);
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
                //create time for all the rooms of building i
                ArrayList<LocalTime> timeFrom = new ArrayList<>();
                ArrayList<LocalTime> timeTo = new ArrayList<>();

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

                System.out.println(st + " " + et);

                //Start time
                for (int b = st; b < et; b++) {
                    if (b < 10) {
                        timeFrom.add(LocalTime.parse("0" + b + ":00"));
                        timeFrom.add(LocalTime.parse("0" + b + ":30"));
                    } else {
                        timeFrom.add(LocalTime.parse(b + ":00"));
                        timeFrom.add(LocalTime.parse(b + ":30"));
                    }

                }

                //End time
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


                ObservableList<LocalTime> from = FXCollections.observableArrayList(timeFrom);
                ObservableList<LocalTime> to = FXCollections.observableArrayList(timeTo);

                VBox vertBox = new VBox();
                tps[c] = new TitledPane();

                for (int j = 0; j < showRooms.size(); j++) {
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


                    long roomId = showRooms.get(j).getId();
                    button1.setOnAction(e -> {
                        LocalDate date = ctrl.onPickDate();
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
            VBox box = new VBox();
            box.getChildren().addAll(ac);
            borderPane.setTop(box);
            borderPane.setPadding(new Insets(20, 0, 0, 50));
        }

    }

    public void setControllers(HamburgerMenuSceneController hamburgerMenuSceneController) {
        this.hamburgerMenuSceneController = hamburgerMenuSceneController;
    }
}
