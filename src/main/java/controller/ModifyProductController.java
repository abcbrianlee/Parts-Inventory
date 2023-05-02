package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static controller.MainMenuController.confirmDialog;
import static controller.MainMenuController.infoDialog;

/**Modify product page that allows users to save modified inputs.*/
public class ModifyProductController implements Initializable {
private Stage stage;
private Object scene;
@FXML
private TextField productName;
@FXML
private TextField productInv;
@FXML
private TextField productPrice;
@FXML
private TextField productMax;
@FXML
private TextField productMin;
@FXML private TextField productID;
@FXML
private TableView<Part>partsTableView;
@FXML
private TableColumn<Part, Integer> PartsIDColumn;
@FXML
private TableColumn<Part, String> PartsNameColumn;
@FXML
private TableColumn<Part, Integer> PartsInventoryColumn;
@FXML
private TableColumn<Part, Double> PartsCostColumn;
@FXML
private TextField partsSearchBar;
@FXML
private TableView<Part>associatedPartsTableView;
@FXML
private TableColumn<Product, Integer> ppID;
@FXML
private TableColumn<Product, String> ppName;
@FXML
private TableColumn<Product, Integer> ppInv;
@FXML
private TableColumn<Product, Double> ppPrice;
    /** Observabile list for ppParts. */
    private Product selectedParts;
@FXML
private ObservableList<Part>associatedParts = FXCollections.observableArrayList();
    /**Sends products to Modify Products.
     This class is utilized in mainmenu and transfers data to modify products page where textfields are set to previous input.
     @param selectedParts */
@FXML
public void sendProducts(Product selectedParts) {
    this.selectedParts = selectedParts;
    productID.setText(String.valueOf(selectedParts.getId()));
    productName.setText(String.valueOf(selectedParts.getName()));
    productInv.setText(String.valueOf(selectedParts.getStock()));
    productMin.setText(String.valueOf(selectedParts.getMin()));
    productMax.setText(String.valueOf(selectedParts.getMax()));
    productPrice.setText(String.valueOf(selectedParts.getPrice()));
    associatedParts.addAll(selectedParts.getAllAssociatedParts());
}
    /** Deletes from ppTableView.
     @param event ppDelete button.*/
    @FXML
    void onActionPPDelete(ActionEvent event) throws IOException {

        if(associatedPartsTableView.getSelectionModel().isEmpty()){
            infoDialog("Warning!", "No Product Selected", "Please Choose product from list");
            return;
        }
        if (confirmDialog("Warning!", "Would you like to delete this part?")) {
            int selectedPart = associatedPartsTableView.getSelectionModel().getSelectedIndex();
            associatedPartsTableView.getItems().remove(selectedPart);
        }
        }
    /** Adds to Observable list.
     @param event PPadd button. */
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
    /** Searches input in observable list and returns word in partsTableview.
     @param event partsSearch.*/
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
    /** Cancels and returns user to main menu page.
     On cancellation confirmation dialogue pops up.
     @param event  ActionCancel butotn. */
    @FXML
    public void onActionCancel(ActionEvent event)throws IOException {
        if (MainMenuController.confirmDialog("Cancel", "Are you Sure you want to cancel")) {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/com/example/software/MainMenu.fxml"));
            stage.setTitle("Inventory Management System");
            stage.setScene(new Scene((Parent) scene));
            stage.show();

        }
    }
    /** Saves product and updates observable list and returns user to main menu.
     Upon saving, confirmation dialogue pops up.  input is validated and warning dialogue pops up if error occurs.
     @param event Save button. */
    @FXML
    public void onActionSave(ActionEvent event) throws IOException{
        int id = Integer.parseInt(productID.getText());
        String name = productName.getText();
        int stock = Integer.parseInt(productInv.getText());
        double price = Double.parseDouble(productPrice.getText());
        int max = Integer.parseInt(productMax.getText());
        int min = Integer.parseInt(productMin.getText());
        if(MainMenuController.confirmDialog("Save","Would you like to save this product?")) {
            if (max < min) {
                MainMenuController.infoDialog("Input Error", "Error in min-max range", "Please check Min and Max values.");
                return;
            } else if (stock < min || max < stock) {
                MainMenuController.infoDialog("Input Error", "Error in inventory field", "Inventory must be between min and max values.");
                return;
            }

            Product updatedProduct = new Product(id, name, price, stock, min, max);
            if (updatedProduct != associatedParts) {
                Inventory.updateProduct(id - 1, updatedProduct);
            }
            for (Part part : associatedParts) {
                if (part != associatedParts) {
                    updatedProduct.addAssociatedPart(part);
                }
            }

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/com/example/software/MainMenu.fxml"));
            stage.setTitle("Inventory Management System");
            stage.setScene(new Scene((Parent) scene));
            stage.show();
        }
    }
    private void updateAssociatedPartTable() {
        associatedPartsTableView.setItems(associatedParts);
    }
    private void updatePartTable(){partsTableView.setItems(Inventory.getAllParts());}

    /** Sets Columns to set property value of incoming data.
     @param resourceBundle */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partsTableView.setItems(Inventory.getAllParts());
        PartsIDColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        PartsNameColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        PartsInventoryColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        PartsCostColumn.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));

        associatedPartsTableView.setItems(associatedParts);
        ppID.setCellValueFactory(new PropertyValueFactory<>("id"));
        ppName.setCellValueFactory(new PropertyValueFactory<>("name"));
        ppInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ppPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        updateAssociatedPartTable();;
        updatePartTable();
    }
}
