package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import model.Inventory;
import model.InHouse;
import model.OutSourced;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static controller.MainMenuController.confirmDialog;
import static model.Inventory.getAllParts;
/**
 This controller class provides control for the add part of the application.
 */
public class AddPartController implements Initializable {
    /** part name textfield. */
    @FXML private TextField partName;
    /** part inventory Textfield. */
    @FXML private TextField partInv;
    /** part price Textfield. */
    @FXML private TextField partPrice;
    /** Max part price Textfield. */
    @FXML private TextField partMax;
    /**Min part Textfield. */
    @FXML private TextField partMin;
    /**company/machine ID textfield. */
    @FXML private TextField partCompanyOrMachineId;
    /** toggle radiobutton for outsourced. */
    @FXML private RadioButton partOutsourced;
    /** toggle radiobutton for inhouse. */
    @FXML private RadioButton partInHouse;
    /** Label of machineID/company. */
    @FXML private Label machineOrCompany;
    private Stage stage;
    private Object scene;
    static int currentIndexParts = getAllParts().size();
    /** Sets label to machineID or inhouse
      machineID is selected or Company Name if outsource is selected.
     @param event radio button.
      */
@FXML
    public void radioSelection(ActionEvent event){
        if(partOutsourced.isSelected())
            this.machineOrCompany.setText("Company Name");
        else
            this.machineOrCompany.setText(("Machine ID"));
}
    /** Returns application to MainMenu.
     Confirmation box asks for confirmation if user wants to return back to main menu.
     @param event Cancel button.*/
@FXML
    public void onActionCancel(ActionEvent event)throws IOException {
        if (confirmDialog("Exit","Are you sure you want to exit?"))
    {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/com/example/software/MainMenu.fxml"));
        stage.setTitle("Inventory Management System");
        stage.setScene(new Scene((Parent) scene));
        stage.show();
    }


}
    /** Saves textfield information into observable list and returns user back to main menu.
     Checks to make sure that inputed data in textfield is a valid response. It not a valid response, returns user to the same page where changes can be made.
     Also textfield data changes type according to what radiobutton is selected.
     @param event save part button. */
@FXML
    public void onActionSave(ActionEvent event) throws IOException {
    try {
        int id = getNewID();
        String name = partName.getText();
        int stock = Integer.parseInt(partInv.getText());
        double price = Double.parseDouble(partPrice.getText());
        int max = Integer.parseInt(partMax.getText());
        int min = Integer.parseInt(partMin.getText());
        if (MainMenuController.confirmDialog("Save?", "Would you like to save?")) {

            if (max < min) {
                MainMenuController.infoDialog("Input error", "Error in min-max range", "Check min and max value");
                return;
            } else if (stock < min || max < stock) {
                MainMenuController.infoDialog("Input error", "Error in inventory field", "Inventory must be between min and max values");
                return;
            }
            if (partOutsourced.isSelected()) {
                String companyName = partCompanyOrMachineId.getText();
                Inventory.addPart(new OutSourced(id, name, price, stock, min, max, companyName));
            } else {
                int machineID = Integer.parseInt(partCompanyOrMachineId.getText());
                Inventory.addPart(new InHouse(id, name, price, stock, min, max, machineID));
            }
            currentIndexParts++;
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/com/example/software/MainMenu.fxml"));
            stage.setTitle("Inventory Management System");
            stage.setScene(new Scene((Parent) scene));
            stage.show();
        }
    }
    catch(Exception e)
    {
        MainMenuController.infoDialog("Input Error","Error","Check fields for correct input");
    }


}/** This function generates new ID.
     Generates new ID in congruence of list size of observable list. */
@FXML
    public static int getNewID(){
        int newID = 1;
        for(int i = 0; i < getAllParts().size(); i++)
    {
        newID++;

    }
    return newID;
}
    /** Initialize first part in program (empty)*/
@Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
