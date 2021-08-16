package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/** This class the Add Parts Controller, used to orchestrate the the add parts form. */
public class AddPartsController implements Initializable {

    @FXML
    public AnchorPane addPart_form;
    public Pane add_part_pane;
    public TextField add_part_part_id;
    public TextField add_part_part_name;
    public TextField add_part_part_cost;
    public TextField add_part_part_min;
    public TextField add_part_part_inv;
    public TextField add_part_part_max;
    public TextField add_part_machine_id;
    public TextField add_part_company_name;
    public RadioButton add_part_outsourced_radio;
    public RadioButton add_part_in_house_radio;
    public Label add_part_machine_id_label;
    public Label add_part_company_name_label;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    /** This is a method that will close the current form and return the main form.
     * @param actionEvent Action event used for setting the stage.
     * @throws IOException Throws an IO Exception when encountering a severe problem.
     * */
    public void cancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/main.fxml")));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1200, 500);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }

    /** This method is used to control the radio buttons. */
    public void in_house_radio_func() {
        if (add_part_outsourced_radio.isSelected()) {
            add_part_outsourced_radio.setSelected(false);
        }
        add_part_in_house_radio.setDisable(true);
        add_part_outsourced_radio.setDisable(false);
        add_part_machine_id_label.setVisible(true);
        add_part_company_name_label.setVisible(false);
        add_part_machine_id.setVisible(true);
        add_part_company_name.setVisible(false);

    }

    /** This method is used to control the radio buttons. */
    public void outsourced_radio_func() {
        if (add_part_in_house_radio.isSelected()) {
            add_part_in_house_radio.setSelected(false);
        }
        add_part_outsourced_radio.setDisable(true);
        add_part_in_house_radio.setDisable(false);
        add_part_machine_id_label.setVisible(false);
        add_part_company_name_label.setVisible(true);
        add_part_machine_id.setVisible(false);
        add_part_company_name.setVisible(true);

    }

    /** This method is used to alert the user of one or more different user input errors.
     * @param message The error message being fed to the function from the insertPart method.  */
    public void alertProblem(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.setTitle("Input Error");
        alert.showAndWait();
    }

    /** This method verifies user input prior to committing a new part to the table.
     * RUNTIME ERROR: Runtime errors were observed in the form of NumberFormatExceptions. I was able to control for these using Try/Cast and requiring that the user provide the correct data type.
     * FUTURE ENHANCEMENT: Have the parts list operate with a total amount of parts available and be removed or un-addable in a product when there are no remaining parts.
     *
     * @param actionEvent Action event used for setting the stage.
     * @throws IOException Throws an IO Exception when encountering a severe problem.
     * */
    public void insertPart(ActionEvent actionEvent) throws IOException {

        int totalIssues = 0;
        String problemMessage;

        int new_id = Counter.get_new_part_id();
        String name = add_part_part_name.getText();
        int min = 0;
        int max = 0;
        double price = 0;
        int stock = 0;
        int machine_id = 0;
        String company;

        if (add_part_part_name.getText().length() == 0) {problemMessage = "Please provide a value in the Name field."; totalIssues++; alertProblem(problemMessage);}
        if (add_part_part_cost.getText().length() == 0) {problemMessage = "Please provide a value in the Price field.";totalIssues++; alertProblem(problemMessage);}
        if (add_part_part_min.getText().length() == 0) {problemMessage = "Please provide a value in the Min field.";totalIssues++; alertProblem(problemMessage);}
        if (add_part_part_max.getText().length() == 0) {problemMessage = "Please provide a value in the Max field.";totalIssues++; alertProblem(problemMessage);}
        if (add_part_part_inv.getText().length() == 0) {problemMessage = "Please provide a value in the Inv field.";totalIssues++; alertProblem(problemMessage);}

        try { price = Double.parseDouble(add_part_part_cost.getText()); } catch (NumberFormatException e) { problemMessage = "Please provide a dollar & cent value in the Price field."; totalIssues++; alertProblem(problemMessage);}
        try { min = Integer.parseInt(add_part_part_min.getText()); } catch (NumberFormatException e) { problemMessage = "Please provide an integer in the Min field"; totalIssues++; alertProblem(problemMessage);}
        try { max = Integer.parseInt(add_part_part_max.getText()); } catch (NumberFormatException e) { problemMessage = "Please provide an integer in the Max field"; totalIssues++; alertProblem(problemMessage);}
        try { stock = Integer.parseInt(add_part_part_inv.getText()); } catch (NumberFormatException e) { problemMessage = "Please provide an integer in the Inv field"; totalIssues++; alertProblem(problemMessage);}

        if (min > max) {System.out.println( problemMessage = "The Min field cannot be larger than the Max field."); totalIssues++; alertProblem(problemMessage);}
        if (min > stock || max < stock) { problemMessage = "The Inv field must be between Min and Max."; totalIssues++; alertProblem(problemMessage);}


        if (add_part_outsourced_radio.isSelected()) {
            if (add_part_company_name.getText().length() == 0) { problemMessage = "Please provide a value in the Company Name field."; totalIssues++; alertProblem(problemMessage);}

            company = add_part_company_name.getText();

            if (totalIssues < 1) {Inventory.addPart(new Outsourced(new_id,name,price,stock,min,max,company));

                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/main.fxml")));
                Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 1200, 500);
                stage.setTitle("Inventory Management System");
                stage.setScene(scene);
                stage.show();
            }

        } else if (add_part_in_house_radio.isSelected()) {
            if (add_part_machine_id.getText().length() == 0) { problemMessage = "Please provide a value in the Machine ID field."; totalIssues++; alertProblem(problemMessage);}
            try { machine_id = Integer.parseInt(add_part_machine_id.getText()); } catch (NumberFormatException e) { problemMessage = "Please provide an integer in the Machine ID field"; totalIssues++; alertProblem(problemMessage);}

            if (totalIssues < 1) {Inventory.addPart(new InHouse(new_id,name, price,stock ,min,max,machine_id));

                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/main.fxml")));
                Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 1200, 500);
                stage.setTitle("Inventory Management System");
                stage.setScene(scene);
                stage.show();
            }
        }
    }

}