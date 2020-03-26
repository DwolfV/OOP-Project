package nl.tudelft.oopp.demo.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import nl.tudelft.oopp.demo.communication.BuildingCommunication;
import nl.tudelft.oopp.demo.communication.RoomCommunication;
import nl.tudelft.oopp.demo.helperclasses.Building;
import nl.tudelft.oopp.demo.helperclasses.Room;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ReservationSceneController implements Initializable {

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

        //Create time
        ArrayList<String> timeFrom = new ArrayList<>();
        ArrayList<String> timeTo = new ArrayList<>();

        for(int p = 9; p < 20; p++){
            timeFrom.add(p+".00");
            timeFrom.add(p+".30");
            if(p>9){
                timeTo.add(p+".00");
                timeTo.add(p+".30");
            }
        }

        // fill the accordion
        for (int i = 0; i < buildingData.size(); i++) {

            //Look for rooms for the building i;
            ObservableList<Room> showRooms = FXCollections.observableArrayList();
            for (int k = 0; k < rooms.size(); k++) {
                if (rooms.get(k).getBuilding().getName().equals(buildingData.get(i).getName())) {
                    showRooms.add(rooms.get(k));
                }
            }

            ObservableList<String> from = FXCollections.observableArrayList(timeFrom);
            ObservableList<String> to = FXCollections.observableArrayList(timeTo);

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
                    String bname = buildingData.get(i).getName();
                    String rname = showRooms.get(j).getName();
                    buttons.add(button1);

                    ComboBox<String> cb = new ComboBox<>();
                    cb.setItems(from);
                    ComboBox<String> cbb = new ComboBox<>();
                    cbb.setItems(to);

                    button1.setOnAction(e -> {System.out.println("building: " + bname + " room " + rname);});


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
}
