package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import model.*;
import model.Part;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.Inventory;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static controller.MainMenuController.confirmDialog;
import static controller.MainMenuController.infoDialog;
import static model.Inventory.getAllProducts;


/** This section of the application adds products to the Observable list. */
public class AddProductController implements Initializable {
    private Stage stage;
    private Object scene;
    /** Product name Textfield.*/
    @FXML
    private TextField productName;
    /** Product inventory Textfield. */
    @FXML
    private TextField productInv;
    /** Product Price Textfield. */
    @FXML
    private TextField productPrice;
    /** Product Max Textfield. */
    @FXML
    private TextField productMax;
    /** Product Min Textfield. */
    @FXML
    private TextField productMin;
    /** Parts Tableview. */
    @FXML
    private TableView<Part> partsTableView;
    /** Parts ID Column. */
    @FXML
    private TableColumn<Part, Integer> PartsIDColumn;
    /** Parts Name Column. */
    @FXML
    private TableColumn<Part, String> PartsNameColumn;
    /** Parts Inventory Column. */
    @FXML
    private TableColumn<Part, Integer> PartsInventoryColumn;
    /** Parts Cost Column. */
    @FXML
    private TableColumn<Part, Double> PartsCostColumn;
    /** Parts Search bar Textfield. */
    @FXML
    private TextField partsSearchBar;
    /** parts associated product Table view. */
    @FXML
    private TableView<Part> associatedPartTable;
    /** parts product ID column. */
    @FXML
    private TableColumn<Product, Integer> ppID;
    /** Parts prouducts Name Column. */
    @FXML
    private TableColumn<Product, String> ppName;
    /** Parts products Inventory Column. */
    @FXML
    private TableColumn<Product, Integer> ppInv;
    /** Parts products Price Column. */
    @FXML
    private TableColumn<Product, Double> ppPrice;

    /** Returns size of Product's Observable list. */
    public int currentIndexProducts = getAllProducts().size();
    /** Creates Observable list of Product's Parts. */

    @FXML private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /** Deletes selected input of Product's parts table.
      Gives a warning when nothing is selected. Also ask confirmation if user wants to delete part.
     @param event pp Delete button. */
    @FXML
    void onActionPPDelete(ActionEvent event) throws IOException {
        if(associatedPartTable.getSelectionModel().isEmpty()){
            infoDialog("Warning!", "No Product Selected", "Please Choose product from list");
            return;
        }
        if (confirmDialog("Warning!", "Would you like to delete this part?")) {
            int selectedPart = associatedPartTable.getSelectionModel().getSelectedIndex();
            associatedPartTable.getItems().remove(selectedPart);
            updatePartTable();
            updateAssociatedPartTable();
        }


    }
    /** Adds selected input into the Product's Parts associated observable list.
     @param event pp Add button. */
    @FXML
    void onActionPPAdd(ActionEvent event) throws IOException {
        Part selectedPart = partsTableView.getSelectionModel().getSelectedItem();
        if (selectedPart != null) {
            associatedParts.add(selectedPart);
            updateAssociatedPartTable();
        }
        else{
            MainMenuController.infoDialog("Selected part","Select part!", "select a part to add to product.");
        }
    }
    /** Searches input through Observable list and returns word.
     Alerts User if no match is found.
     @param event  parts Search button. */
    @FXML
    void onActionPartsSearch(ActionEvent event) throws  IOException {
        String searchField = partsSearchBar.getText();
        try {
            ObservableList<Part> tempParts = Inventory.lookupPart(searchField);
            if (tempParts.size() == 0) {
                int searchID = Integer.parseInt(searchField);
                Part searchP = Inventory.lookupPart(searchID);
                tempParts.add(searchP);
                if (searchP == null) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("No Match Found. Please Try another Search.");
                    alert.setTitle("No Match");
                    alert.showAndWait();
                }
            }
            partsTableView.setItems(tempParts);
        }
        catch(NumberFormatException e){
            partsTableView.setItems(null);
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("No parts found");
            alert.showAndWait();
        }
    }
    /** Exits Product application and returns user to Main Menu.
     Asks for confirmation if user wants to exit.
     @param event cancel button. */
    @FXML
    public void onActionCancel(ActionEvent event) throws IOException {
        if (confirmDialog("Exit","Are you sure you want to exit?"))
        {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/com/example/software/MainMenu.fxml"));
            stage.setTitle("Inventory Management System");
            stage.setScene(new Scene((Parent) scene));
            stage.show();
        }

    }
    /** Saves Product to Observable list and returns user to main Menu.
     Checks to make sure all input is valid and creates an error message and also asks user if they want to save.
     @param event Save button. */
    @FXML
    public void onActionSave(ActionEvent event) throws IOException {
        try {

        int id = getNewID();
        String name = productName.getText();
        int stock = Integer.parseInt(productInv.getText());
        double price = Double.parseDouble(productPrice.getText());
        int max = Integer.parseInt(productMax.getText());
        int min = Integer.parseInt(productMin.getText());
        if (min > stock || stock > max) {
            Alert alert = new Alert(Alert.AlertType.ERROR,"Inventory must be within min and max");
            alert.showAndWait();
            return;
        } else if (min >= max) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Maximum must be greater than minimum");
            alert.showAndWait();
            return;
        }
        if (confirmDialog("Warning!", "Would you like to save this part?")) {
            Product product = new Product(id, name, price, stock, min, max);
            for (Part part : associatedParts) {
                product.addAssociatedPart(part);
            }
            Inventory.addProduct(product);

            currentIndexProducts++;
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/com/example/software/MainMenu.fxml"));
            stage.setTitle("Inventory Management System");
            stage.setScene(new Scene((Parent) scene));
            stage.show();
        }
    } catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Error");
            alert.setContentText("Incorrect Values");
            alert.showAndWait();
            return;
        }

}
/** Generates new ID according to Observable list size. */
    @FXML
    public static int getNewID(){
        int newID = 1;
        for(int i = 0; i < getAllProducts().size(); i++)
        {
            newID++;

        }
        return newID;
    }

    public void updatePartTable() {
        partsTableView.setItems(Inventory.getAllParts());
    }
    private void updateAssociatedPartTable() {
        associatedPartTable.setItems(associatedParts);
    }
    /** Sets parameters of input and lists them in the Tableview */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partsTableView.setItems(Inventory.getAllParts());
        PartsIDColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        PartsNameColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        PartsInventoryColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        PartsCostColumn.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));

        associatedPartTable.setItems(associatedParts);
        ppID.setCellValueFactory(new PropertyValueFactory<>("id"));
        ppName.setCellValueFactory(new PropertyValueFactory<>("name"));
        ppInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ppPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

    }
}
