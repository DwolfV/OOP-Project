package nl.tudelft.oopp.demo.controllers;

import com.sun.javafx.fxml.expression.Expression;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import nl.tudelft.oopp.demo.helperclasses.Dish;
import nl.tudelft.oopp.demo.helperclasses.Restaurant;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class OrderSceneController implements Initializable {

    public ListView basketListView;
    private MainSceneController mainSceneController;
    private  HamburgerMenuSceneController hamburgerMenuSceneController;
    private static ObservableList<String> stringBasketList = FXCollections.observableArrayList();
    private static ArrayList<Dish> basketList = new ArrayList();
    private Restaurant restaurant;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        basketListView.setItems(stringBasketList);
    }

    public static void addToBasket(Dish dish) {
        basketList.add(dish);
        stringBasketList.add(dish.getName());
    }

    public static void removeFromBasket(Dish dish){
        if(basketList.contains(dish)){
            basketList.remove(dish);
            stringBasketList.remove(dish.getName());
        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("The dish you selected is not currently in your basket");
            alert.show();
        }
    }

    public static void clearBasket(){
        basketList.clear();
        stringBasketList.clear();
    }

    public void setRestaurant(Restaurant restaurant){
        this.restaurant = restaurant;
    }

}
