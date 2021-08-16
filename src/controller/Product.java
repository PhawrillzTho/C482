package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author William Tucker
 */

public class Product {

    private final ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    private int Id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    public Product(int id, String name, double price, int stock, int min, int max) {

        // ObservableList<Part> associatedParts = FXCollections.observableArrayList();
        this.Id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;


    }

    /**
     * @return the id
     */
    public int getId() {
        return Id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        Id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * @param stock the stock to set
     */

    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @return the min
     */

    public int getMin() {
        return min;
    }

    /**
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * @param part the part to add to associatedParts
     */
    public void addAssociatedPart(Part part) {
        this.associatedParts.add(part);
    }

    /**
     * @return associatedParts The associated parts to return.
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }

    /** Deletes an associated part from the product.
     * @param id The id of the associated part to delete.
     * @return boolean Returns True/False depending on whether the delete was successful.
     */
    public boolean deleteAssociatedPart(int id) {
        for (Part part : getAllAssociatedParts()) {
            if (part.getId() == id) {
                Inventory.getAllParts().remove(part);
                return true;
            }
        }
        return false;
    }
}