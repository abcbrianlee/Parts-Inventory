package model;
/** Main class that acts as super*/
public abstract class Part {
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    /** Constructor for part
     @param id for id part
     @param name for name part
     @param price for price part
     @param min for min part
     @param max for max part
     @param stock for stock part*/
    public Part(int id, String name, double price, int stock, int min, int max) {
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



}
