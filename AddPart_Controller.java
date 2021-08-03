package View_Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import Model.InhousePart;
import Model.Inventory;
import Model.OutsourcedPart;

/**
 * AddPart Controller has the methods to create, delete, and modify
 * parts within the program
 */
public class AddPart_Controller implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private RadioButton InhousePartRadio;
    @FXML
    private RadioButton OutsourcedPartRadio;
    @FXML
    private TextField AddPartId;
    @FXML
    private TextField AddPartName;
    @FXML
    private TextField AddInventory;
    @FXML
    private TextField AddPrice;
    @FXML
    private TextField MinQty;
    @FXML
    private TextField MaxQty;
    @FXML
    private TextField CompNameMachID;
    @FXML
    private Label LabelCompanyName;

    private int partCounter = Inventory.partIncrement();
    private boolean IsInHouse;


    /**
     *
     * @param event onMouseClick
     * @param switchPath The FXML file that will be opened
     * @throws IOException to catch read/write errors
     */
    private void changeScreen(ActionEvent event, String switchPath) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource(switchPath));
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }


    /**
     * Selects whether the part being
     * added is Inhouse or Outsourced
     *
     * @param event onMouseClick
     */
    public void SetInOut(ActionEvent event) {

        if(InhousePartRadio.isSelected()) {
            CompNameMachID.setPromptText("Machine ID");
            CompNameMachID.clear();
            LabelCompanyName.setText("Machine ID");
        }
        if(OutsourcedPartRadio.isSelected()) {
            IsInHouse = false;
            CompNameMachID.clear();
            CompNameMachID.setPromptText("Company Name");
            LabelCompanyName.setText("Company Name");
        }
    }


    /**
     *  Saves new part to Part list
     *  This method includes part
     *  validation. Checking that
     *  the name is not empty and stock
     *  value is within max and min values.
     *  Min is also checked to be under max.
     *
     * @param event onMouseClick
     * @throws IOException to catch read/write errors
     */
    @FXML
    void onActionSavePart(ActionEvent event) throws IOException {

        String name = AddPartName.getText();
        int stock = Integer.parseInt(AddInventory.getText());
        double price = Double.parseDouble(AddPrice.getText());
        int max = Integer.parseInt(MaxQty.getText());
        int min = Integer.parseInt(MinQty.getText());

        try {
            if(min > max){

                Alert maxAlert = new Alert(Alert.AlertType.ERROR);
                maxAlert.setTitle("ERROR");
                maxAlert.setContentText("Minimum stock quantity must not exceed maximum allowable quantity.");
                maxAlert.showAndWait();
                return;
            }
            if(stock < min){

                Alert minAlert = new Alert(Alert.AlertType.ERROR);
                minAlert.setTitle("ERROR");
                minAlert.setContentText("Minimum stock quantity must not exceed current inventory quantity.");
                minAlert.showAndWait();
                return;
            }
            if(stock > max){

                Alert minAlert = new Alert(Alert.AlertType.ERROR);
                minAlert.setTitle("ERROR");
                minAlert.setContentText("Current inventory quantity must not exceed maximum allowable quantity.");
                minAlert.showAndWait();
                return;
            }
            if (AddPartName.getText() == null || AddPartName.getText().trim().isEmpty()){
                Alert nameAlert = new Alert(Alert.AlertType.ERROR);
                nameAlert.setTitle("ERROR");
                nameAlert.setContentText("Part name cannot be empty.");
                nameAlert.showAndWait();
                return;
            }
            if(AddPrice.getText() == null || AddPrice.getText() == ""){

                Alert minAlert = new Alert(Alert.AlertType.ERROR);
                minAlert.setTitle("ERROR");
                minAlert.setContentText("Price field cannot be empty.");
                minAlert.showAndWait();
                return;
            }
            if(MaxQty.getText() == null || MaxQty.getText() == ""){

                Alert minAlert = new Alert(Alert.AlertType.ERROR);
                minAlert.setTitle("ERROR");
                minAlert.setContentText("Max fields cannot be empty.");
                minAlert.showAndWait();
                return;
            }
            if(MinQty.getText() == null || MinQty.getText() == ""){

                Alert minAlert = new Alert(Alert.AlertType.ERROR);
                minAlert.setTitle("ERROR");
                minAlert.setContentText("Min fields cannot be empty.");
                minAlert.showAndWait();
                return;
            }

            else{
                if (IsInHouse){
                    int machineId = Integer.parseInt(CompNameMachID.getText());
                    InhousePart InHousePart = new InhousePart(partCounter, name, price, stock, min, max, machineId);
                    Inventory.addPart(InHousePart);
                }
                else{
                    String companyName = CompNameMachID.getText();
                    OutsourcedPart OutsourcedPart = new OutsourcedPart(partCounter, name, price, stock, min, max, companyName);
                    Inventory.addPart(OutsourcedPart);
                }

            }

        }


        /**
         * Validates all fields are filled in correctly
         */
        catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Missing value(s) in required field(s).");
            alert.showAndWait();
            return;
        }

        changeScreen(event, "MainForm.fxml");
    }


    /**
     * Changes screen back to
     * the main form.
     *
     * @param event onMouseClick
     * @throws IOException to catch read/write errors
     */
    @FXML
    void onActionDisplayMainForm(ActionEvent event) throws IOException {


        /**
         * To confirm user will leave Add Part screen
         * and return to the Main Form
         */
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you wish to exit?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            partCounter = Inventory.partDecrement();
            changeScreen(event, "MainForm.fxml");

        }
    }


    /**
     * When Add Part screen is  opened
     * by the program.
     *
     * @param url location of resources
     * @param rb list of resources
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        AddPartId.setText(Integer.toString(partCounter));
    }
}