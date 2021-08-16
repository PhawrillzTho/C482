package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/** This is the modify parts controller used to orchestrate everything on the modify parts form. */
public class ModifyPartsController implements Initializable {
    @FXML
    public TextField modify_part_part_id;
    public TextField modify_part_part_name;
    public TextField modify_part_part_inv;
    public TextField modify_part_part_price;
    public TextField modify_part_part_min;
    public TextField modify_part_part_max;
    public TextField modify_part_part_machine_id;
    public RadioButton modify_part_in_house_radio;
    public RadioButton modify_part_outsourced_radio;
    public Label modify_part_part_company_name_label;
    public TextField modify_part_part_company_name;
    public Label modify_part_part_machine_id_label;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Outsourced osPartToModify;
        InHouse ihPartToModify;
        ihPartToModify = Controller.getinHousePartToModify();
        osPartToModify = Controller.getoutsourcedPartToModify();

        if (ihPartToModify != null) {
            modify_part_in_house_radio.setSelected(true);
            modify_part_in_house_radio.setDisable(true);
            modify_part_part_company_name.setVisible(false);
            modify_part_part_company_name_label.setVisible(false);

            modify_part_part_id.setText(String.valueOf(ihPartToModify.getId()));
            modify_part_part_name.setText(String.valueOf(ihPartToModify.getName()));
            modify_part_part_inv.setText(String.valueOf(ihPartToModify.getStock()));
            modify_part_part_price.setText(String.valueOf(ihPartToModify.getPrice()));
            modify_part_part_min.setText(String.valueOf(ihPartToModify.getMin()));
            modify_part_part_max.setText(String.valueOf(ihPartToModify.getMax()));
            modify_part_part_machine_id.setText(String.valueOf(ihPartToModify.getMachineId()));
        } else {
            modify_part_outsourced_radio.setSelected(true);
            modify_part_outsourced_radio.setDisable(true);
            modify_part_part_machine_id.setVisible(false);
            modify_part_part_machine_id_label.setVisible(false);

            modify_part_part_id.setText(String.valueOf(osPartToModify.getId()));
            modify_part_part_name.setText(String.valueOf(osPartToModify.getName()));
            modify_part_part_inv.setText(String.valueOf(osPartToModify.getStock()));
            modify_part_part_price.setText(String.valueOf(osPartToModify.getPrice()));
            modify_part_part_min.setText(String.valueOf(osPartToModify.getMin()));
            modify_part_part_max.setText(String.valueOf(osPartToModify.getMax()));
            modify_part_part_company_name.setText(String.valueOf(osPartToModify.getCompanyName()));
        }
    }

    /** This method allows the user exit the current form and return the main form.
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
        if (modify_part_outsourced_radio.isSelected()) {
            modify_part_outsourced_radio.setSelected(false);
        }
        modify_part_in_house_radio.setDisable(true);
        modify_part_outsourced_radio.setDisable(false);

        modify_part_part_machine_id_label.setVisible(true);
        modify_part_part_company_name_label.setVisible(false);
        modify_part_part_company_name.setVisible(false);
        modify_part_part_machine_id.setVisible(true);

    }

    /** This method is used to control the radio buttons. */
    public void outsourced_radio_func() {
        if (modify_part_in_house_radio.isSelected()) {
            modify_part_in_house_radio.setSelected(false);
        }
        modify_part_in_house_radio.setDisable(false);
        modify_part_outsourced_radio.setDisable(true);

        modify_part_part_machine_id_label.setVisible(false);
        modify_part_part_company_name_label.setVisible(true);
        modify_part_part_company_name.setVisible(true);
        modify_part_part_machine_id.setVisible(false);

    }

    /** This method is used to alert the user of one or more different user input errors.
     * @param message The error message being fed to the function from the insertPart method.  */
    public void alertProblem(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.setTitle("Input Error");
        alert.showAndWait();
    }

    /** This method verifies user input prior to committing a modified part to the table.
     * RUNTIME ERROR: I encountered a runtime error here and was able to overcome it by verifying that the appropriate field references to the fxml were set.
     * FUTURE ENHANCEMENT: Do not allow the same part to be linked more than once or find a criteria for which this is valid in some cases and do not allow it to occur in cases that it is not.
     * @param actionEvent Action event used for setting the stage.
     * @throws IOException Throws an IO Exception when encountering a severe problem.
     * */
    public void insertPart(ActionEvent actionEvent) throws IOException {
        int totalIssues = 0;
        String problemMessage;

        int id = Integer.parseInt(String.valueOf(modify_part_part_id.getText()));
        String name = modify_part_part_name.getText();
        int min = 0;
        int max = 0;
        double price = 0;
        int stock = 0;
        int machine_id = 0;
        String company;

        if (modify_part_part_name.getText().length() == 0) {
            problemMessage = "Please provide a value in the Name field.";
            totalIssues++;
            alertProblem(problemMessage);
        }
        if (modify_part_part_price.getText().length() == 0) {
            problemMessage = "Please provide a value in the Price field.";
            totalIssues++;
            alertProblem(problemMessage);
        }
        if (modify_part_part_min.getText().length() == 0) {
            problemMessage = "Please provide a value in the Min field.";
            totalIssues++;
            alertProblem(problemMessage);
        }
        if (modify_part_part_max.getText().length() == 0) {
            problemMessage = "Please provide a value in the Max field.";
            totalIssues++;
            alertProblem(problemMessage);
        }
        if (modify_part_part_inv.getText().length() == 0) {
            problemMessage = "Please provide a value in the Inv field.";
            totalIssues++;
            alertProblem(problemMessage);
        }

        try {
            price = Double.parseDouble(modify_part_part_price.getText());
        } catch (NumberFormatException e) {
            problemMessage = "Please provide a dollar & cent value in the Price field.";
            totalIssues++;
            alertProblem(problemMessage);
        }
        try {
            min = Integer.parseInt(modify_part_part_min.getText());
        } catch (NumberFormatException e) {
            problemMessage = "Please provide an integer in the Min field";
            totalIssues++;
            alertProblem(problemMessage);
        }
        try {
            max = Integer.parseInt(modify_part_part_max.getText());
        } catch (NumberFormatException e) {
            problemMessage = "Please provide an integer in the Max field";
            totalIssues++;
            alertProblem(problemMessage);
        }
        try {
            stock = Integer.parseInt(modify_part_part_inv.getText());
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

        if (modify_part_outsourced_radio.isSelected()) {
            if (modify_part_part_company_name.getText().length() == 0) {
                problemMessage = "Please provide a value in the Company Name field.";
                totalIssues++;
                alertProblem(problemMessage);
            }

            company = modify_part_part_company_name.getText();

            if (totalIssues < 1) {

                Inventory.deletePart(Integer.parseInt(String.valueOf(modify_part_part_id.getText())));
                Inventory.addPart(new Outsourced(id, name, price, stock, min, max, company));

                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/main.fxml")));
                Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 1200, 500);
                stage.setTitle("Inventory Management System");
                stage.setScene(scene);
                stage.show();
            }

        } else if (modify_part_in_house_radio.isSelected()) {
            if (modify_part_part_machine_id.getText().length() == 0) {
                problemMessage = "Please provide a value in the Machine ID field.";
                totalIssues++;
                alertProblem(problemMessage);
            }
            try {
                machine_id = Integer.parseInt(modify_part_part_machine_id.getText());
            } catch (NumberFormatException e) {
                problemMessage = "Please provide an integer in the Machine ID field";
                totalIssues++;
                alertProblem(problemMessage);
            }

            if (totalIssues < 1) {
                Inventory.deletePart(Integer.parseInt(String.valueOf(modify_part_part_id.getText())));
                Inventory.addPart(new InHouse(id, name, price, stock, min, max, machine_id));

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