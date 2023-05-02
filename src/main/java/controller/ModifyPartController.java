package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.OutSourced;
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static model.Inventory.getAllParts;
/**Modify Part that allows user to save modifications*/
public class ModifyPartController implements Initializable {
    @FXML private TextField partID;
    @FXML private TextField partName;
    @FXML private TextField partInv;
    @FXML private TextField partPrice;
    @FXML private TextField partMax;
    @FXML private TextField partMin;
    @FXML private TextField partCompanyOrMachineId;
    @FXML private RadioButton partOutsourced;
    @FXML private RadioButton partInHouse;
    @FXML private Label machineOrCompany;
    @FXML private Label nameLb;
    @FXML private Label invLb;
    @FXML private Label priceLb;
    @FXML private Label maxLb;
    @FXML private Label minLb;
    public int tempid;
    private Stage stage;
    private Object scene;
    public Part parts;
    /** Sets Label to Machine ID or company Name.
     When toggle radio button is selected, sets label to COmpany name if outsource is selected or machine ID if inhouse.
     @param event Radiobutton. */
    @FXML
    public void radioSelection(ActionEvent event){
        if(partOutsourced.isSelected())
            this.machineOrCompany.setText("Company Name");
        else
            this.machineOrCompany.setText(("Machine ID"));
    }
    /** Cancels and returns user to main menu.
     Displays confirmation dialogue to see if user wants to cancel.
     @param event Cancel button.*/
@FXML
    public void onActionCancel(ActionEvent event)throws IOException {
        if(MainMenuController.confirmDialog("Cancel","Are you Sure you want to cancel")) {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/com/example/software/MainMenu.fxml"));
            stage.setTitle("Inventory Management System");
            stage.setScene(new Scene((Parent) scene));
            stage.show();
        }
}
    /** Saves Information on modify part page.
     Displays confirmation if user wants to save information displayed in textfield.
     ALso verifies information to see if its valid and generates error message
     @param event  Save button.*/
@FXML
    public void onActionSave(ActionEvent event) throws IOException {
    int id = Integer.parseInt(partID.getText());
    String name = partName.getText();
    int stock = Integer.parseInt(partInv.getText());
    double price = Double.parseDouble(partPrice.getText());
    int max = Integer.parseInt(partMax.getText());
    int min = Integer.parseInt(partMin.getText());
    if(MainMenuController.confirmDialog("Save","Would you like to save this part?")) {
        if (max < min) {
            MainMenuController.infoDialog("Input Error", "Error in min-max range", "Please check Min and Max values.");
            return;
        } else if (stock < min || max < stock) {
            MainMenuController.infoDialog("Input Error", "Error in inventory field", "Inventory must be between min and max values.");
            return;
        }
        if (partOutsourced.isSelected()) {
            String companyName = partCompanyOrMachineId.getText();
            OutSourced updatedPart = new OutSourced(id, name, price, stock, min, max, companyName);
            Inventory.updatePart(id - 1, updatedPart);
        } else {
            int machineID = Integer.parseInt(partCompanyOrMachineId.getText());
            InHouse updatedPart = new InHouse(id, name, price, stock, min, max, machineID);
            Inventory.updatePart(id - 1, updatedPart);
        }
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/com/example/software/MainMenu.fxml"));
        stage.setTitle("Inventory Management System");
        stage.setScene(new Scene((Parent) scene));
        stage.show();
    }
}
    /** Sets the textfield to the information held inside variables at the modify part page.
     Class is utizilied in the mainmenu page when user wants to modify a part. Information is parsed accordingly to data type.
     Sets Textfield to respective radiobutton selection.
     @param parts */
    public void sendParts(Part parts){
        this.parts = parts;
        tempid = Inventory.getAllParts().indexOf(parts);
        partID.setText(Integer.toString(parts.getId()));
        partName.setText(String.valueOf(parts.getName()));
        partInv.setText(String.valueOf(parts.getStock()));
        partMin.setText(String.valueOf(parts.getMin()));
        partMax.setText(String.valueOf(parts.getMax()));
        partPrice.setText(String.valueOf(parts.getPrice()));

        if(parts instanceof InHouse){
            machineOrCompany.setText("Machine ID");
            partCompanyOrMachineId.setText(String.valueOf(((InHouse) parts).getMachineID()));
        }
        else {
            if(parts instanceof OutSourced){
                partOutsourced.setSelected(true);
            machineOrCompany.setText("Company Name");
            partCompanyOrMachineId.setText(((OutSourced) parts).getCompanyName());

        }
    }

}
    /** Initialize
     * @param resourceBundle */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
