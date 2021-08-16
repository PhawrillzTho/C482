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

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * This is the modify products controller. It orchestrates everything within the modify products form.
 */
public class ModifyProductsController implements Initializable {
    public ObservableList<Part> tempAssociatedParts = FXCollections.observableArrayList();
    private final ObservableList<Part> searchResultPartID = FXCollections.observableArrayList();

    @FXML
    public TableView<Part> parts_table;
    public TableColumn<Object, Object> part_part_id_col;
    public TableColumn<Object, Object> part_part_name_col;
    public TableColumn<Object, Object> part_inventory_level_col;
    public TableColumn<Object, Object> part_price_cost_col;

    public TableView<Part> associated_parts_table;
    public TableColumn<Object, Object> associated_parts_part_id_col;
    public TableColumn<Object, Object> associated_parts_part_name_col;
    public TableColumn<Object, Object> associated_parts_inventory_level_col;
    public TableColumn<Object, Object> associated_parts_price_cost_col;

    public TextField modify_product_id;
    public TextField modify_product_name;
    public TextField modify_product_inv;
    public TextField modify_product_price;
    public TextField modify_product_max;
    public TextField modify_product_min;

    public TextField Part_to_search;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Product productToModify;
        productToModify = Controller.getProductToModify();

        tempAssociatedParts = productToModify.getAllAssociatedParts();

        modify_product_id.setText(String.valueOf(productToModify.getId()));
        modify_product_name.setText(String.valueOf(productToModify.getName()));
        modify_product_inv.setText(String.valueOf(productToModify.getStock()));
        modify_product_price.setText(String.valueOf(productToModify.getPrice()));
        modify_product_max.setText(String.valueOf(productToModify.getMax()));
        modify_product_min.setText(String.valueOf(productToModify.getMin()));

        parts_table.setItems(Inventory.getAllParts());
        part_part_id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
        part_part_name_col.setCellValueFactory(new PropertyValueFactory<>("name"));
        part_inventory_level_col.setCellValueFactory(new PropertyValueFactory<>("stock"));
        part_price_cost_col.setCellValueFactory(new PropertyValueFactory<>("price"));

        associated_parts_table.setItems(tempAssociatedParts);
        associated_parts_part_id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
        associated_parts_part_name_col.setCellValueFactory(new PropertyValueFactory<>("name"));
        associated_parts_inventory_level_col.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associated_parts_price_cost_col.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * This method allows the user to search through the parts list.
     */
    public void searchParts() {
        try {
            searchResultPartID.clear();
            int searchPartId = Integer.parseInt(Part_to_search.getText());

            if (Inventory.lookupPart(searchPartId) == null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Your search yielded no results.", ButtonType.OK);
                alert.setTitle("INFORMATION");
                alert.showAndWait();

            } else {
                searchResultPartID.add(Inventory.lookupPart(searchPartId));
                parts_table.setItems(searchResultPartID);
            }

        } catch (Exception e) {
            parts_table.setItems(Inventory.lookupPart(Part_to_search.getText()));

        }
    }

    /**
     * This method is used to alert the user of one or more different user input errors.
     *
     * @param message The error message being fed to the function from the insertPart method.
     */
    public void alertProblem(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.setTitle("Input Error");
        alert.showAndWait();
    }

    /**
     * This method performs user input checks and ultimately will insert a modified product.
     * FIXED BUG / NOT RUNTIME ERROR: I found that my min and max fields were switched around and it took me a while to get that sorted out.
     * FUTURE ENHANCEMENT: Put functionality behind the Min/Max fields such as if the max or min number of parts must be included in a product must be respected.
     * @param actionEvent Action event used for setting the stage.
     * @throws IOException Throws an IO Exception when encountering a severe problem.
     */
    public void insertPart(ActionEvent actionEvent) throws IOException {
        int totalIssues = 0;
        String problemMessage;

        int id = Integer.parseInt(modify_product_id.getText());
        String name = modify_product_name.getText();
        int min = 0;
        int max = 0;
        double price = 0;
        int stock = 0;

        if (modify_product_name.getText().length() == 0) {
            problemMessage = "Please provide a value in the Name field.";
            totalIssues++;
            alertProblem(problemMessage);
        }
        if (modify_product_price.getText().length() == 0) {
            problemMessage = "Please provide a value in the Price field.";
            totalIssues++;
            alertProblem(problemMessage);
        }
        if (modify_product_min.getText().length() == 0) {
            problemMessage = "Please provide a value in the Min field.";
            totalIssues++;
            alertProblem(problemMessage);
        }
        if (modify_product_max.getText().length() == 0) {
            problemMessage = "Please provide a value in the Max field.";
            totalIssues++;
            alertProblem(problemMessage);
        }
        if (modify_product_inv.getText().length() == 0) {
            problemMessage = "Please provide a value in the Inv field.";
            totalIssues++;
            alertProblem(problemMessage);
        }

        try {
            price = Double.parseDouble(modify_product_price.getText());
        } catch (NumberFormatException e) {
            problemMessage = "Please provide a dollar & cent value in the Price field.";
            totalIssues++;
            alertProblem(problemMessage);
        }
        try {
            min = Integer.parseInt(modify_product_min.getText());
        } catch (NumberFormatException e) {
            problemMessage = "Please provide an integer in the Min field";
            totalIssues++;
            alertProblem(problemMessage);
        }
        try {
            max = Integer.parseInt(modify_product_max.getText());
        } catch (NumberFormatException e) {
            problemMessage = "Please provide an integer in the Max field";
            totalIssues++;
            alertProblem(problemMessage);
        }
        try {
            stock = Integer.parseInt(modify_product_inv.getText());
        } catch (NumberFormatException e) {
            problemMessage = "Please provide an integer in the Inv field";
            totalIssues++;
            alertProblem(problemMessage);
        }

        if (min > max) {
            System.out.println(problemMessage = "The Min field cannot be larger than the Max field.");
            totalIssues++;
            alertProblem(problemMessage);
        }
        if (min > stock || max < stock) {
            problemMessage = "The Inv field must be between Min and Max.";
            totalIssues++;
            alertProblem(problemMessage);
        }

        if (totalIssues < 1) {

            System.out.println(Inventory.deleteProduct(Integer.parseInt(String.valueOf(modify_product_id.getText()))));
            Inventory.addProduct(new Product(id, name, price, stock, min, max));

            for (Product product : Inventory.getAllProducts()) {
                if (product.getId() == id) {

                    for (Part part : tempAssociatedParts) {

                        product.addAssociatedPart(part);

                    }
                }
            }

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/main.fxml")));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1200, 500);
            stage.setTitle("Inventory Management System");
            stage.setScene(scene);
            stage.show();

        }

    }

    /**
     * This method allows the user to add a associated part to the associated parts table for the particular product.
     */
    public void addAssociatedPart() {
        if (parts_table.getSelectionModel().getSelectedItem() != null) {
            tempAssociatedParts.add(parts_table.getSelectionModel().getSelectedItem());
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Nothing selected.", ButtonType.OK);
            alert.setTitle("INFORMATION");
            alert.showAndWait();
        }
    }

    /**
     * This method allows the user exit the current form and return the main form.
     * @param actionEvent Action event used for setting the stage.
     * @throws IOException Throws an IO Exception when encountering a severe problem.
     */
    public void cancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/main.fxml")));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1200, 500);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method allows the user to remove an associated part from the associated parts table for the particular product.
     */
    public void removeAssociatedPart() {
        if (associated_parts_table.getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Remove select associated part?", ButtonType.YES, ButtonType.CANCEL);
            alert.setTitle("Remove Part?");
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                tempAssociatedParts.remove(associated_parts_table.getSelectionModel().getSelectedItem());
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Nothing selected", ButtonType.OK);
            alert.setTitle("INFORMATION");
            alert.showAndWait();
        }
    }
}