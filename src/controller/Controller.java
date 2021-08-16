package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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
 * This is the main controller for the application and orchestrates the main form as well as provides public static variables for modifying parts and products.
 */
public class Controller implements Initializable {
    //These three public static variables as well as methods are used to obtain the part or product to modify.
    public static InHouse inHousePartToModify = null;
    public static Outsourced outsourcedPartToModify = null;
    public static Product productToModify = null;

    /** This function returns the InHouse Part to modify.
     * @return inHousePartToModify Returns the selected part object to modify.
     * */
    public static InHouse getinHousePartToModify() {
        return inHousePartToModify;
    }

    /** This function returns the Outsourced Part to modify.
     * @return outsourcedPartToModify Returns the selected part object to modify.
     * */
    public static Outsourced getoutsourcedPartToModify() {
        return outsourcedPartToModify;
    }

    /** This function returns the Product to modify.
     * @return productToModify Returns the selected product object to modify.
     * */
    public static Product getProductToModify() {
        return productToModify;
    }

    @FXML
    public TableView<Product> products_table;
    public TableColumn<Product, Integer> product_product_id_col;
    public TableColumn<Product, String> product_product_name_col;
    public TableColumn<Product, Integer> product_inventory_level_col;
    public TableColumn<Product, Double> product_price_cost_col;

    public TableView<Part> parts_table;
    public TableColumn<Part, Integer> part_part_id_col;
    public TableColumn<Part, String> part_part_name_col;
    public TableColumn<Part, Integer> part_inventory_level_col;
    public TableColumn<Part, Double> part_price_cost_col;

    public TextField part_to_search;
    public TextField product_to_search;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        parts_table.setItems(Inventory.getAllParts());
        part_part_id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
        part_part_name_col.setCellValueFactory(new PropertyValueFactory<>("name"));
        part_inventory_level_col.setCellValueFactory(new PropertyValueFactory<>("stock"));
        part_price_cost_col.setCellValueFactory(new PropertyValueFactory<>("price"));

        products_table.setItems(Inventory.getAllProducts());
        product_product_id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
        product_product_name_col.setCellValueFactory(new PropertyValueFactory<>("name"));
        product_inventory_level_col.setCellValueFactory(new PropertyValueFactory<>("stock"));
        product_price_cost_col.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    private final ObservableList<Product> searchResultProductID = FXCollections.observableArrayList();
    private final ObservableList<Part> searchResultPartID = FXCollections.observableArrayList();

    /**
     * This method is used to search for parts.
     */
    public void searchParts() {
        try {
            searchResultPartID.clear();
            int searchPartId = Integer.parseInt(part_to_search.getText());

            if (Inventory.lookupPart(searchPartId) == null) {
                System.out.println(part_to_search.getText());
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Your search yielded no results.", ButtonType.OK);
                alert.setTitle("INFORMATION");
                alert.showAndWait();
            } else {
                searchResultPartID.add(Inventory.lookupPart(searchPartId));
                parts_table.setItems(searchResultPartID);
            }

        } catch (Exception e) {
            parts_table.setItems(Inventory.lookupPart(part_to_search.getText()));
        }
    }

    /**
     * This method is used to search for products.
     */
    public void searchProducts() {
        try {
            searchResultProductID.clear();
            int searchProductId = Integer.parseInt(product_to_search.getText());

            if (Inventory.lookupProduct(searchProductId) == null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Your search yielded no results.", ButtonType.OK);
                alert.setTitle("INFORMATION");
                alert.showAndWait();
            } else {
                searchResultProductID.add(Inventory.lookupProduct(searchProductId));
                products_table.setItems(searchResultProductID);
            }

        } catch (Exception e) {
            products_table.setItems(Inventory.lookupProduct(product_to_search.getText()));
        }
    }

    /**
     * This method is used to delete parts in the table.
     * @param actionEvent Action event used for setting the stage.
     * @throws IOException Throws an IO Exception when encountering a severe problem.
     */
    public void deletePart(ActionEvent actionEvent) throws IOException {
        if (parts_table.getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete " + parts_table.getSelectionModel().getSelectedItem().getName() + " ?", ButtonType.YES, ButtonType.CANCEL);
            alert.setTitle("Delete Part?");
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                Inventory.deletePart(parts_table.getSelectionModel().getSelectedItem().getId());

                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/main.fxml")));
                Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 1200, 500);
                stage.setTitle("Inventory Management System");
                stage.setScene(scene);
                stage.show();

            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Nothing selected", ButtonType.OK);
            alert.setTitle("INFORMATION");
            alert.showAndWait();
        }
    }

    /**
     * This method is used to delete products in the table.
     * @param actionEvent Action event used for setting the stage.
     * @throws IOException Throws an IO Exception when encountering a severe problem.
     */
    public void deleteProduct(ActionEvent actionEvent) throws IOException {
        if (products_table.getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete " + products_table.getSelectionModel().getSelectedItem().getName() + " ?", ButtonType.YES, ButtonType.CANCEL);
            alert.setTitle("Delete Product?");
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                if (products_table.getSelectionModel().getSelectedItem().getAllAssociatedParts() != null) {
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION, "You may not delete this product as it has associated parts.", ButtonType.OK);
                    alert2.setTitle("INFORMATION");
                    alert2.showAndWait();
                } else {
                    Inventory.deleteProduct(products_table.getSelectionModel().getSelectedItem().getId());

                    Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/main.fxml")));
                    Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root, 1200, 500);
                    stage.setTitle("Inventory Management System");
                    stage.setScene(scene);
                    stage.show();
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Nothing selected", ButtonType.OK);
            alert.setTitle("INFORMATION");
            alert.showAndWait();
        }
    }

    /**
     * This method is used to exit the application.
     * @param actionEvent Action event used for setting the stage.
     * @throws IOException Throws an IO Exception when encountering a severe problem.
     */
    public void exit(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/main.fxml")));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1200, 500);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.close();
    }

    /**
     * This method is used to open the add part form.
     * @param actionEvent Action event used for setting the stage.
     * @throws IOException Throws an IO Exception when encountering a severe problem.
     */
    public void addPart(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/addPart.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 550);
        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method is used to open the modify part form.
     * @param actionEvent Action event used for setting the stage.
     * @throws IOException Throws an IO Exception when encountering a severe problem.
     */
    public void modifyPart(ActionEvent actionEvent) throws IOException {
        if (parts_table.getSelectionModel().getSelectedItem() != null) {
            if (parts_table.getSelectionModel().getSelectedItem().getClass().toString().contains("InHouse")) {
                inHousePartToModify = (InHouse) parts_table.getSelectionModel().getSelectedItem();
            } else {
                outsourcedPartToModify = (Outsourced) parts_table.getSelectionModel().getSelectedItem();
            }

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/modifyPart.fxml")));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 600, 550);
            stage.setTitle("Modify Part");
            stage.setScene(scene);
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Nothing selected", ButtonType.OK);
            alert.setTitle("INFORMATION");
            alert.showAndWait();
        }
    }

    /**
     * This method is used to open the add product form.
     * @param actionEvent Action event used for setting the stage.
     * @throws IOException Throws an IO Exception when encountering a severe problem.
     */
    public void addProduct(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/addProduct.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1200, 800);
        stage.setTitle("Add Product");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method is used to open the modify product form.
     * @param actionEvent Action event used for setting the stage.
     * @throws IOException Throws an IO Exception when encountering a severe problem.
     */
    public void modifyProduct(ActionEvent actionEvent) throws IOException {
        if (products_table.getSelectionModel().getSelectedItem() != null) {
            productToModify = products_table.getSelectionModel().getSelectedItem();

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/modifyProduct.fxml")));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1200, 800);
            stage.setTitle("Modify Product");
            stage.setScene(scene);
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Nothing selected", ButtonType.OK);
            alert.setTitle("INFORMATION");
            alert.showAndWait();
        }
    }
}