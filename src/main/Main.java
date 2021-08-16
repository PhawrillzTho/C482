package main;

import controller.InHouse;
import controller.Inventory;
import controller.Outsourced;
import controller.Product;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/** This is the main class for an inventory management program written by William Tucker for C482. The Javadoc for the is program can be found within '...\C482_WilliamTucker\Javadoc' */
public class Main extends Application {

    @Override
    /** This method sets the stage for the main form of the program.
     @param primaryStage The stage being set, in this case the main stage. */
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/view/main.fxml"));
        primaryStage.setTitle("Inventory Management System");
        primaryStage.setScene(new Scene(root, 1200, 500));
        primaryStage.getIcons().add(new Image("/images/icon.png"));
        primaryStage.show();
    }

    /** This is the main method.
    This is the first method that gets call when you run this program. It contains some initial set up data.
     @param args The arguments used by this main function to launch the application.
     */
    public static void main(String[] args) {
        Inventory.addPart(new InHouse(1,"Corsair Liquid CPU Cooler",35.99,60,1,100,61));
        Inventory.addPart(new Outsourced(2, "MSI Z170A M5 Motherboard", 200.75, 42,1,100,"CDW"));
        Inventory.addPart(new InHouse(3, "ASUS Z490A Motherboard", 350.12, 6,1,100,62));
        Inventory.addPart(new Outsourced(4, "Intel Core i9 10900k CPU", 500.62, 52,1,100,"CDW"));
        Inventory.addPart(new InHouse(5, "AMD Ry-zen 7 3700X CPU", 300.81, 2,1,100,63));
        Inventory.addPart(new Outsourced(6, "Corsair 16GB (2x8) RAM", 118.75, 33,1,100,"CDW"));
        Inventory.addPart(new InHouse(7, "G.SKILLS 32GB (2x16) RAM", 100.99, 51,1,100,64));
        Inventory.addPart(new Outsourced(8, "Samsung 256GB m.2 Storage", 212.93, 19,1,100,"CDW"));
        Inventory.addPart(new InHouse(9, "Samsung 1TB m.2 Storage", 350.66, 35,1,100,65));
        Inventory.addPart(new Outsourced(10, "Cooler Master Case 500x", 105.5, 5,1,100,"CDW"));

        Inventory.addProduct(new Product(1,"Computer Bundle 1",106.23,9,1,100));
        Inventory.addProduct(new Product(2,"Computer Bundle 2",250.99,7,1,100));
        Inventory.addProduct(new Product(3,"Computer Bundle 3",750.99,3,1,100));

        launch(args);
    }
}
