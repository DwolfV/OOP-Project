package nl.tudelft.oopp.demo.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SidebarSceneController implements Initializable {

    @FXML
    public AnchorPane anchorPane;
    @FXML
    public DatePicker dp;
    @FXML
    public Button searchId;
    @FXML
    public TextField capacityId;
    @FXML
    public CheckBox whiteboardId;
    @FXML
    public CheckBox tableId;
    @FXML
    public CheckBox tvId;
    @FXML
    public CheckBox projectorId;
    @FXML
    public CheckBox chairsId;
    @FXML
    public CheckBox deliveryId;
    @FXML
    public CheckBox takeAwayId;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        anchorPane = anchorPane;
    }

    public LocalDate onPickDate() {
        return dp.getValue();
    }
    public int getCapacity(){ return Integer.parseInt(capacityId.getText());}

    @FXML
    public List handleSearchClick(){
        List<String> result = new ArrayList<>();
        result.add(whiteboardSelected());
        result.add(tableSelected());
        result.add(tvSelected());
        result.add(projectorSelected());
        result.add(chairsSelected());
        result.add(deliverySelected());
        result.add(takeAwaySelected());
        return result;
    }

    private String whiteboardSelected(){
        if(whiteboardId.isSelected()){
            return "whiteboard";
        }
        return null;
    }

    private String tableSelected(){
        if(tableId.isSelected()){
            return "table";
        }
        return null;
    }

    private String tvSelected(){
        if(tvId.isSelected()){
            return "tv";
        }
        return null;
    }

    private String projectorSelected(){
        if(projectorId.isSelected()){
            return "projector";
        }
        return null;
    }

    private String chairsSelected(){
        if(chairsId.isSelected()){
            return "chairs";
        }
        return null;
    }

    private String deliverySelected(){
        if(deliveryId.isSelected()){
            return "delivery";
        }
        return null;
    }

    private String takeAwaySelected(){
        if(takeAwayId.isSelected()){
            return "takeAway";
        }
        return null;
    }
}
