package nl.tudelft.oopp.demo.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
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
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static java.time.LocalDate.now;


public class ReservationSceneController implements Initializable {

    public MainSceneController mainSceneController;

    @FXML
    private BorderPane bPane;
    @FXML
    private Accordion ac = new Accordion();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Room> rooms = FXCollections.observableList(RoomCommunication.getRooms());
        ObservableList<Building> buildingData = FXCollections.observableList(BuildingCommunication.getBuildings());

        TitledPane[] tps = new TitledPane[buildingData.size()];
        List<Button> buttons = new ArrayList<>();

        int c = 0; // count - for lists, c - for tps

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
                //create time for all the rooms of building i
                ArrayList<LocalTime> timeFrom = new ArrayList<>();
                ArrayList<LocalTime> timeTo = new ArrayList<>();

                List<LocalTime> bTime = BuildingCommunication.getTimebyBuildingId(buildingData.get(i).getId());
                String startTime = String.valueOf(bTime.get(0));
                String endTime = String.valueOf(bTime.get(1));
                System.out.println(startTime + " " + endTime);
                int st, et;

                String[] time = startTime.split(":",2);
                String check = time[0];
                st = Integer.valueOf(check);
                time = endTime.split(":",2);
                check = time[0];
                et = Integer.valueOf(check);

                System.out.println(st + " " + et);

                //Start time
                for (int b = st; b < et; b++) {
                    if(b<10){
                        timeFrom.add(LocalTime.parse("0"+b + ":00"));
                        timeFrom.add(LocalTime.parse("0"+b + ":30") );
                    }else{
                        timeFrom.add(LocalTime.parse(b + ":00"));
                        timeFrom.add(LocalTime.parse(b + ":30") );
                    }

                }

                //End time
                for (int b = st; b <= et; b++) {
                    if(b<10){
                        if (b != st && b != et) {
                            timeTo.add(LocalTime.parse("0"+b + ":00"));
                            timeTo.add(LocalTime.parse("0"+b + ":30"));
                        } else if (b == st) {
                            timeTo.add(LocalTime.parse("0"+b + ":30"));
                        }
                    }else{
                        if (b != st && b != et) {
                            timeTo.add(LocalTime.parse(b + ":00"));
                            timeTo.add(LocalTime.parse(b + ":30"));
                        } else if (b == st) {
                            timeTo.add(LocalTime.parse(b + ":30"));
                        }
                        else if (b == et) {
                            timeTo.add(LocalTime.parse(b + ":00"));
                        }
                    }
                }


                ObservableList<LocalTime> from = FXCollections.observableArrayList(timeFrom);
                ObservableList<LocalTime> to = FXCollections.observableArrayList(timeTo);

                VBox vBox = new VBox();
                tps[c] = new TitledPane();

                for (int j = 0; j < showRooms.size(); j++) {
                    HBox hBox = new HBox();

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


                    long rId = showRooms.get(j).getId();

                    button1.setOnAction(e -> {
                        RoomReservationCommunication.addRoomReservation(now(), stt[0], ett[0], rId);
                        System.out.println(now() + " " + stt[0] + " " + ett[0] + " " + rId);
                    });


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

            // load the accordion into the scene
            VBox vBox = new VBox(ac);
            bPane.setCenter(vBox);
            bPane.setPadding(new Insets(30, 5, 5, 10));
            //rootScene.setCenter(bPane);

        }

    }

    public void setControllers(MainSceneController mainSceneController) {
        this.mainSceneController = mainSceneController;
    }
}
