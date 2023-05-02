package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**Sub Products instance of Parts*/
public class Product {
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    /** Associated part Observable List. */
    private ObservableList<Part>associatedParts = FXCollections.observableArrayList();
    /** Products Constructor
     @param name for name product
     @param id for id product
     @param stock for stock product
     @param max for max product
     @param min for min product
     @param price for price product*/
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;

    }

    /**ID Getter*/
    public int getId() {return id;}
    /**ID Setter*/
    public void setId(int id) {this.id = id;}
    /**Name Getter*/
    public String getName() {return name;}
    /**Name Setter*/
    public void setName(String name) {this.name = name;}
    /**Price Getter*/
    public double getPrice() {return price;}
    /**Price Setter*/
    public void setPrice(double price) {this.price = price;}
    /**Stock Getter */
    public int getStock() {return stock;}
    /**Stock Setter*/
    public void setStock(int stock) {this.stock = stock;}
    /** Min Getter*/
    public int getMin() {return min;}
    /**Min Setter*/
    public void setMin(int min) {this.min = min;}
    /** Max getter*/
    public int getMax() {return max;}
    /**Max Setter*/
    public void setMax(int max) {this.max = max;}
    /**Getter for associated parts
     @param associatedParts*/
    public void setAssociatedParts(ObservableList<Part> associatedParts) {
        this.associatedParts = associatedParts;
    }
    /**Add associated parts
     @param part */
    public void addAssociatedPart(Part part) {
        this.associatedParts.add(part);
    }
    /**Deleted associated part
     @param selectedAssociatedPart */
    public void deleteAssociatedPart(Part selectedAssociatedPart) {
        associatedParts.remove(selectedAssociatedPart);
    }
/**Returns associated parts*/
    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts; }


    }




