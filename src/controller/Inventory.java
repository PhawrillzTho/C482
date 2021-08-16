package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.Locale;

/** This is the Inventory class and it is used to manage all of the data within the tables and Observable lists in this project. */
public class Inventory {

    private static final ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static final ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static final ObservableList<Part> filteredParts = FXCollections.observableArrayList();
    private static final ObservableList<Product> filteredProducts = FXCollections.observableArrayList();

    /** This method is used to add a part to the allParts table.
     * @param part The part object to add to the table.
     * */
    public static void addPart(Part part) {
        allParts.add(part);
    }

    /** This method is used to add a product to the allProducts table.
     * @param product The product object to add to the table.
     * */
    public static void addProduct(Product product) {
        allProducts.add(product);
    }

    /** This method is used to lookup a part using the id of a part.
     * @param id The id of the part the user is looking for.
     * @return Part the part object that is identified from the lookup is returned. */
    public static Part lookupPart(int id) {
        for (Part part : Inventory.getAllParts()) {
            if (part.getId() == id) {
                return part;
            }
        }
        return null;
    }

    /** This method is used to lookup a product using the id of a product.
     * @param id The id of the product the user is looking for.
     * @return Part the product object that is identified from the lookup is returned. */
    public static Product lookupProduct(int id) {

        for (Product product : Inventory.getAllProducts()) {
            if (product.getId() == id ) {
                return product;
            }
        }
        return null;
    }

    /** This method is used to lookup one or more parts by name.
     * @param partName The full or partial name of the part the user is searching for.
     * @return ObservableList The Part ObservableList that is identified from the lookup is returned. */
    public static ObservableList<Part> lookupPart(String partName) {
        if (!(Inventory.filteredParts.isEmpty())) {
            Inventory.filteredParts.clear();
        }
        for (Part part : Inventory.getAllParts()) {
            if (part.getName().toLowerCase(Locale.ROOT).contains(partName.toLowerCase(Locale.ROOT))) {
                Inventory.filteredParts.add(part);
            }
        }
        if (Inventory.filteredParts.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Your search yielded no results.", ButtonType.OK);
            alert.setTitle("INFORMATION");
            alert.showAndWait();
            return allParts;
        } else {
            return filteredParts;
        }
    }

    /** This method is used to lookup one or more products by name.
     * @param productName The full or partial name of the product the user is searching for.
     * @return ObservableList The Product ObservableList that is identified from the lookup is returned. */
    public static ObservableList<Product> lookupProduct(String productName) {
        if (!(Inventory.filteredProducts.isEmpty())) {
            Inventory.filteredProducts.clear();
        }
        for (Product product : Inventory.getAllProducts()) {
            if (product.getName().contains(productName) || String.valueOf(product.getId()).contains(productName)) {
                Inventory.filteredProducts.add(product);
            }
        }
        if (Inventory.filteredProducts.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Your search yielded no results.", ButtonType.OK);
            alert.setTitle("INFORMATION");
            alert.showAndWait();
            return allProducts;
        } else {
            return filteredProducts;
        }
    }

    /** A method used to update a part.
     * @param part The part object to update.
     * @param index The index or id of the part to update.
     * */
    public static void updatePart(int index, Part part) {
        int iterator = -1;
        for (Part part1 : Inventory.getAllParts()) {
            iterator++;
            if (part1.getId() == index) {
                Inventory.getAllParts().set(iterator, part);
            }
        }
    }

    /** A method used to update a product.
     * @param product The product object to update.
     * @param index The index or id of the product to update.
     * */
    public static void updateProduct(int index, Product product) {
        int iterator = -1;
        for (Product product1 : Inventory.getAllProducts()) {
            iterator++;
            if (product1.getId() == index) {
                Inventory.getAllProducts().set(iterator, product);
            }
        }
    }

    /** A method used to delete a part from the allParts table.
     * @param id The id of the part to delete.
     * @return  boolean Returns true/false whether the delete was successful */
    public static boolean deletePart(int id) {
        for (Part part : Inventory.getAllParts()) {
            if (part.getId() == id) {
                Inventory.getAllParts().remove(part);
                return true;
            }
        }
        return false;
    }

    /** A method used to delete a product from the allProducts table.
     * @param id The id of the product to delete.
     * @return  boolean Returns true/false whether the delete was successful */
    public static boolean deleteProduct(int id) {
        for (Product product : Inventory.getAllProducts()) {
            if (product.getId() == id) {
                Inventory.getAllProducts().remove(product);
                return true;
            }
        }
        return false;
    }

    /** A method that returns all parts found within the allParts table.
     * @return allParts Observable List. */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /** A method that returns all products found within the allProducts table.
     * @return allProducts Observable List. */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}