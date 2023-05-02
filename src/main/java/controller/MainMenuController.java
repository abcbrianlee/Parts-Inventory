package controller;

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
import java.util.Optional;
import java.util.ResourceBundle;
/**Main Menu Page
 Displays methods and classes that enable user to search,add,delete and modify product and parts.*/
public class MainMenuController implements Initializable {
    /** Parts Table View. */
    @FXML private TableView <Part> partsTableView;
    /** Part ID Column. */
    @FXML private TableColumn<Part, Integer> partIDColumn;
    /** Part Name Colmn. */
    @FXML private TableColumn<Part, String> partNameColumn;
    /** Part Inventory Column. */
    @FXML private TableColumn<Part, Integer> partInvLevelColumn;
    /** Part Cost Column. */
    @FXML private TableColumn<Part, Double> partCostColumn;
    /** Products Table View. */
    @FXML private TableView <Product> productsTableView;
    /** Products ID column. */
    @FXML private TableColumn<Product, Integer> productIDColumn;
    /** Products Name Column. */
    @FXML private TableColumn<Product, String> productNameColumn;
    /** Products Inventory Column. */
    @FXML private TableColumn<Product, Integer> productInvLevelColumn;
    /** Products Cost Column. */
    @FXML private TableColumn<Product, Double> productCostColumn;
    /** Parts Search Textfield. */
    @FXML private TextField partsSearchBar;
    /**Product Search Textfield. */
    @FXML private TextField productSearchBar;
    Stage stage;
    Parent scene;
    /** Takes selected input and then transfers data to Modify Parts Page.
     If nothing is selected, returns user to same page to avoid generating program errors.
     @param event Parts modify button. */
    @FXML
    void onActionPartsModify(ActionEvent event) throws  IOException {

            Part selectedPart = partsTableView.getSelectionModel().getSelectedItem();
            if (selectedPart == null) {
                return;
            }

            Part parts = partsTableView.getSelectionModel().getSelectedItem();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/example/software/ModifyPart.fxml"));
            loader.load();

            ModifyPartController MPController = loader.getController();
            MPController.sendParts(parts);

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setTitle("Inventory Management System");
            stage.setScene(new Scene((Parent) scene));
            stage.show();
        }

    /**Takes selected input and transfers data to modify product page.
     If nothing is selected, returns user to same page to avoid generating errors.
     @param event Products modify button. */
    @FXML
    void onActionProductsModify(ActionEvent event) throws  IOException {
        Product selectedPart = productsTableView.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            return;
        }

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/example/software/ModifyProduct.fxml"));
        loader.load();

        ModifyProductController MPController = loader.getController();
        MPController.sendProducts(productsTableView.getSelectionModel().getSelectedItem());

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setTitle("Inventory Management System");
        stage.setScene(new Scene((Parent) scene));
        stage.show();
    }
    /** Deletes Parts from Observable List.
     Created a confirmation warning, also creates error if nothing is selected.
     @param event Parts delete button*/
    @FXML
    void onActionPartsDelete(ActionEvent event) throws IOException {
        if(partsTableView.getSelectionModel().isEmpty()){
            infoDialog("Warning!", "No Part Selected", "Please Choose part from list");
            return;
        }
        if (confirmDialog("Warning!", "Would you like to delete this part?")) {
             Part selectedPart = partsTableView.getSelectionModel().getSelectedItem();
             Inventory.deletePart(selectedPart);
        }

}
    /** Deletes products from Observable List.
     Creates confirmation warning and also displays error if nothing is selected.
     @param event products delete button. */
    @FXML
    void onActionProductsDelete(ActionEvent event) throws IOException {
        if(productsTableView.getSelectionModel().isEmpty()){
            infoDialog("Warning!", "No Product Selected", "Please Choose product from list");
            return;
        }
        if (confirmDialog("Warning!", "Would you like to delete this product?")) {
            Product selectedProduct = productsTableView.getSelectionModel().getSelectedItem();
            if(selectedProduct.getAllAssociatedParts().size() > 0) {
                infoDialog("Error!", "Remove associated parts before you delete this product", "Please delete associated part.");
                return;
            }
            Inventory.deleteProduct(selectedProduct);

        }
    }
    /**RUNTIME ERROR: After submitting application, I realized the search function wasn't able to return searches via ID numbers. Previously used were string's in the search textfield. After some thinking, i decided to make an IF statement that would take the results from the string search.  IF the results had a ssize of 0 (meaning no response) then I made the assumption that it was either an invalid search or an integer (as the rubric asks for either a string or integer search) 
     * Takes input from search textfield and searches for response while also selecting it.
      If search input is empty, full observable list is displayed and if search is not found, displays error.
     @param event   Parts Search button. */
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
    /** Takes user input from search textfield and searches product's observable list.
      If input is empty, full list is returned and if search does not exist, generates an error.
     @param event Search button*/
    @FXML
    void onActionProductsSearch(ActionEvent event) throws  IOException {
        String searchField = productSearchBar.getText();
        try {
            ObservableList<Product> tempProducts = Inventory.lookupProduct(searchField);
            if (tempProducts.size() == 0) {
                int searchID = Integer.parseInt(searchField);
                Product searchP = Inventory.lookupProduct(searchID);
                tempProducts.add(searchP);
                if (searchP == null) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("No Match Found. Please Try another Search.");
                    alert.setTitle("No Match");
                    alert.showAndWait();
                }
            }
            productsTableView.setItems(tempProducts);
        }
        catch(NumberFormatException e){
            productsTableView.setItems(null);
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("No parts found");
            alert.showAndWait();
        }
    }
    /** Takes user to Add Part
     @param event add part button*/
    @FXML
    void onActionAddParts(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/com/example/software/AddPart.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /** Takes user to add product page.
     @param event  acc product button. */
    @FXML
    void onActionAddProducts(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/com/example/software/AddProduct.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /** Exit's the application and returns to stop class
     @param event exit button*/
    @FXML
    void onActionExit(ActionEvent event){
        System.exit(0);
    }
    /** Confirmation dialog used throughout program.
     @param content  title and string content. */
    static boolean confirmDialog(String title, String content){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText("Confirm");
        alert.setContentText(content);
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK){
            System.out.println("yes");
            return true;
        }
        else{
            System.out.println("no");
            return false;
        }
    }
    /** Information Dialogue used throughout program that sets message.
     @param content The first content.  */
    static void infoDialog(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partsTableView.setItems(Inventory.getAllParts());
        partIDColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        partInvLevelColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        partCostColumn.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));

        productsTableView.setItems((Inventory.getAllProducts()));
        productIDColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        productInvLevelColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("stock"));
        productCostColumn.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));


    }
}