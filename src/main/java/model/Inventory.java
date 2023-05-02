package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**Models an inventory of parts and products*/
public class Inventory {
    /**Observable list used for Parts. */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    /**Observable list used for products.*/
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    /** Adds parts to observable list.
     * @param newPart */
    public static void addPart(Part newPart){
        allParts.add(newPart);
    }
    /** Adds products to observable list.
     @param newProduct */
    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }
    /** Allparts Getter*/
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }
    /** allProducts Getter*/
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    /**Deletes product from observable list.
     @param selectedProduct */
    public static void deleteProduct(Product selectedProduct) {
        allProducts.remove(selectedProduct);
    }
    public static void deletePart(Part selectedPart) {
        allParts.remove(selectedPart);
    }

    /** Updates products
     @param product
     @param index */
    public static void updateProduct(int index, Product product) {
        allProducts.set(index, product);
    }
    /**Updates parts
     @param parts
     @param index */
    public static void updatePart(int index, Part parts) {allParts.set(index, parts);}
    /**Looks up part in observable list.
     If searching input is empty, returns whole list.
     @param searchPartName */
    public static ObservableList lookupPart(String searchPartName){
        ObservableList<Part> foundParts = FXCollections.observableArrayList();

        if(searchPartName.length() == 0) {
            foundParts = allParts;
        }
        else {
            for (int i = 0; i < allParts.size(); i++) {
                if (allParts.get(i).getName().toLowerCase().contains(searchPartName.toLowerCase())) {
                    foundParts.add(allParts.get(i));
                }
            }
        }

        return foundParts;
    }
    /**Looks up part through parts list and returns it
     @param partId */
    public static Part lookupPart(int partId) {
        Part tempSearchPartID = null;
        for (Part part : getAllParts()) {
            if (part.getId() == partId) {
                tempSearchPartID = part;
            }
        }
        return tempSearchPartID;
    }
/** Looks up product through products list and return it
 @param productID */
    public static Product lookupProduct(int productID) {
        Product tempSearchProductID = null;
        for (Product product : getAllProducts()) {
            if (product.getId() == productID) {
                tempSearchProductID = product;
            }
        }
        return tempSearchProductID;
    }

    /** Looks up products through observable list.
     if products input search is empty, returns whole list.
     @param searchProductName */
    public static ObservableList lookupProduct(String searchProductName){
        ObservableList<Product> foundProducts = FXCollections.observableArrayList();

        if(searchProductName.length() == 0) {
            foundProducts = allProducts;
        }
        else {
            for (int i = 0; i < allProducts.size(); i++) {
                if (allProducts.get(i).getName().toLowerCase().contains(searchProductName.toLowerCase())) {
                    foundProducts.add(allProducts.get(i));
                }
            }
        }

        return foundProducts;
    }

}
