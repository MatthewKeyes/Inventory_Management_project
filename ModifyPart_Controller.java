package View_Controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import Model.InhousePart;
import Model.Inventory;
import Model.OutsourcedPart;
import Model.Part;

/**
 * Modify Part controller contains the methods
 * to change the fields in an already existing
 * Part class instance
 */
public class ModifyPart_Controller implements Initializable {

    @FXML
    private RadioButton InHouseRadio;
    @FXML
    private RadioButton OutsourcedRadio;
    @FXML
    private TextField ModifyPartID;
    @FXML
    private TextField ModifyPartName;
    @FXML
    private TextField ModifyPartStock;
    @FXML
    private TextField ModifyPartPrice;
    @FXML
    private TextField ModifyPartMax;
    @FXML
    private TextField ModifyPartMin;
    @FXML
    private TextField ModCompNameMachID;
    @FXML
    private Label ModLabelCompName;

    private int partCounter;
    private Part modifyPart;


    /**
     *
     * @param event onMouseClick change the screen depending on the switch path
     *              declared in the controller
     * @param switchPath FXML file that will be opened on event
     * @throws IOException catches read/write error
     */
    private void changeScreen(ActionEvent event, String switchPath) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource(switchPath));
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }


    /**
     *
     * @param event onMouseClick will select
     *              Inhouse or Outsourced part
     *              fields. Changes to MachineID or
     *              Company name.
     *
     */
    public void SetModPartInOut(ActionEvent event) {
        if (InHouseRadio.isSelected()) {
            ModLabelCompName.setText("Machine ID");
            ModCompNameMachID.clear();
            ModCompNameMachID.setPromptText("Machine ID");
        }
        if (OutsourcedRadio.isSelected()) {
            ModLabelCompName.setText("Company Name");
            ModCompNameMachID.clear();
            ModCompNameMachID.setPromptText("Company Name");
        }
    }


    /**
     *
     * @param part Instance of part being modified
     */
    public void GetPart(Part part) {
        modifyPart = part;
        ModifyPartID.setText(String.valueOf(part.getId()));
        ModifyPartName.setText(part.getName());
        ModifyPartStock.setText(String.valueOf(part.getStock()));
        ModifyPartPrice.setText(String.valueOf(part.getPrice()));
        ModifyPartMax.setText(String.valueOf(part.getMax()));
        ModifyPartMin.setText(String.valueOf(part.getMin()));

        if (part instanceof InhousePart) {
            ModCompNameMachID.setText(String.valueOf(((InhousePart) part).getMachineId()));
            ModLabelCompName.setText("Machine ID");
            InHouseRadio.setSelected(true);
        }
        else if (part instanceof OutsourcedPart) {
            ModCompNameMachID.setText(((OutsourcedPart) part).getCompanyName());
            OutsourcedRadio.setSelected(true);
        }
    }


    /**
     *
     * @param event onMouseClick commit changes to part and update the
     *              fields in the list on the main form
     * @throws IOException catches read/write errors
     */
    @FXML
    public void onActionSavePart(ActionEvent event) throws IOException {
        if (InHouseRadio.isSelected()) {

            //For max and min inventory values in the exception set
            int max = Integer.parseInt(ModifyPartMax.getText());
            int min = Integer.parseInt(ModifyPartMin.getText());
            int invMax = Integer.parseInt(ModifyPartStock.getText());

            int modifiedPart = Inventory.lookupPartIndex(modifyPart.getId());
            Part newPart = new InhousePart(
                    modifyPart.getId(),
                    ModifyPartName.getText(),
                    Double.parseDouble(ModifyPartPrice.getText()),
                    Integer.parseInt(ModifyPartStock.getText()),
                    Integer.parseInt(ModifyPartMin.getText()),
                    Integer.parseInt(ModifyPartMax.getText()),
                    Integer.parseInt(ModCompNameMachID.getText())
            );

            /**
             * Validation method to ensure the inventory is
             * at an acceptable quantity before saving changes
             */
            if (min > max) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("Quantity minimum is larger than maximum.");
                alert.showAndWait();
                return;
            }
            if (invMax > max) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("Current inventory exceeds maximum number allowed.");
                alert.showAndWait();
                return;
            }
            if (invMax < min){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("Minimum inventory requirement exceeds current inventory.");
                alert.showAndWait();
                return;
            }

            Inventory.updatePart(modifiedPart, newPart);

            changeScreen(event, "MainForm.fxml");
        }
        else if (OutsourcedRadio.isSelected()) {

            int max = Integer.parseInt(ModifyPartMax.getText());
            int min = Integer.parseInt(ModifyPartMin.getText());
            int invMax = Integer.parseInt(ModifyPartStock.getText());

            int moddedPart = Inventory.lookupPartIndex(modifyPart.getId());
            Part newPart = new OutsourcedPart(
                    modifyPart.getId(),
                    ModifyPartName.getText(),
                    Double.parseDouble(ModifyPartPrice.getText()),
                    Integer.parseInt(ModifyPartStock.getText()),
                    Integer.parseInt(ModifyPartMin.getText()),
                    Integer.parseInt(ModifyPartMax.getText()),
                    ModCompNameMachID.getText()
            );

            /**
             * Validation method to ensure the inventory is
             * at an acceptable quantity before saving changes
             */
            if (min > max) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("Quantity minimum must not be larger than maximum.");
                alert.showAndWait();
                return;
            }
            if (invMax > max) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("Inventory must not exceed maximum quantity allowed.");
                alert.showAndWait();
                return;
            }
            if (invMax < min){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("Minimum inventory requirement exceeds current inventory.");
                alert.showAndWait();
                return;
            }

            Inventory.updatePart(moddedPart, newPart);

            changeScreen(event, "MainForm.fxml");
        }
    }


    /**
     *
     * @param event onMouseClick brings user back to main form
     * @throws IOException catches read/write errors
     */
    @FXML
    void onActionDisplayMainForm(ActionEvent event) throws IOException {


        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("No Changes will be saved.");
        alert.setContentText("Would you like to continue?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {

            changeScreen(event, "MainForm.fxml");
        }
        else if (result.get() == ButtonType.CANCEL){
            return;
        }
    }


    /**
     *
     * @param url location of resources
     * @param rb list of resources
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        partCounter = Inventory.partIncrement();
        ModifyPartID.setText(Integer.toString(partCounter));
    }
}