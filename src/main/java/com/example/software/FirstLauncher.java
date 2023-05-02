package com.example.software;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;


import java.io.IOException;

/**
 This class creates an application that manages an inventory of parts and products.
 FUTURE ENHANCEMENT: to further enhance the functionality of this application, I could add an archive page that would
 show old records and transactions that occurred.  This archive could be further analyzed for future trends in
 transactions that occurred 1 month- 1 year ago.
 RUNTIME ERROR can be found on mainmenu.java at the onActionSaveParts.

 */
public class FirstLauncher extends Application {

/**
 The entry point for program.
 This class starts the program with pre-inputed data. After, it launches the main.fxml file.
 @param stage */
    @Override
    public void start(Stage stage) throws IOException {

        Inventory.addPart(new InHouse(1, "Espresso Machine", 320.0, 8, 1, 20, 19));
        Inventory.addPart(new InHouse(2, "Grinder", 200.0, 6, 1, 20,50));
        Inventory.addPart(new InHouse(3, "Espresso cups", 12.0, 14, 1, 30,79));
        Inventory.addPart(new InHouse(4, "Fresh Roast Beans", 20.0, 15, 1, 30,91));

        Inventory.addPart(new OutSourced(5,"Ground beans", 17.0,17,1,30,"Peets"));
        Inventory.addPart(new OutSourced(6,"Smart Grinder", 170.0,13,1,30,"Breville"));
        Inventory.addPart(new OutSourced(7,"Cleaner", 34.0,32,1,50,"Lime"));

        Inventory.addProduct(new Product(1,"level",45.0,6,1,30));
        Inventory.addProduct(new Product(2,"scale",35.0,19,1,30));
        Inventory.addProduct(new Product(3,"water-container",36.0,7,1,30));


        FXMLLoader fxmlLoader = new FXMLLoader(FirstLauncher.class.getResource("MainMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1260, 580);
        stage.setTitle("Brian's Application!!");
        stage.setScene(scene);
        stage.show();
    }

/**
 This prints out a message when program is terminated.
 This class is not necessary.*/
    @Override
    public void stop()
    {
        System.out.println("Terminated!!!");
    }
    public static void main(String[] args) {
        launch(args);
    }
}

