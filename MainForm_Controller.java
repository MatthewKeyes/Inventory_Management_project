package View_Controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import Model.Inventory;
import Model.Part;
import Model.Product;


/**
 * Main Form controller shows observable lists for both Parts
 * and Products.
 */
public class MainForm_Controller implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private TextField MainPartSearch;
    @FXML
    private TableView<Part> PartTableView;
    @FXML
    private TableView<Product> ProductsTableView;
    @FXML
    private TableColumn<Part, Integer> PartIDColumn;
    @FXML
    private TableColumn<Part, String> PartNameColumn;
    @FXML
    private TableColumn<Part, Integer> PartInventoryColumn;
    @FXML
    private TableColumn<Part, Double> PartPriceColumn;
    @FXML
    private TextField MainProductsSearch;
    @FXML
    private TableColumn<Product, Integer> ProductIDColumn;
    @FXML
    private TableColumn<Product, String> ProductNameColumn;
    @FXML
    private TableColumn<Product, Integer> ProductInventoryColumn;
    @FXML
    private TableColumn<Product, Double> ProductPriceColumn;

    ObservableList<Part> searchPart = FXCollections.observableArrayList();
    ObservableList<Product> searchProduct = FXCollections.observableArrayList();

    /**
     * This method controls the changing
     * between the screens in the program.
     *
     *
     * @param event onMouseClick
     * @param switchPath Screen that is being switched too
     * @throws IOException catch read/write errors
     */
    private void switchScreen(ActionEvent event, String switchPath) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource(switchPath));
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }


    /**
     * Parses String input from user
     * to int and compares it to available
     * Part Id's. If no matching Id is found
     * then an error message displays
     * letting the user know no part was found.
     *
     * @param event onMouseClick
     *              Search for part by ID or String
     */
    @FXML
    void onActionSearchPart(ActionEvent event) {

        try {
            try {
                int id = Integer.parseInt(MainPartSearch.getText());
                searchPart.add(Inventory.lookupPart(id));
            } catch (Exception e) {
                String name = MainPartSearch.getText();
                searchPart = Inventory.lookupPart(name);
            }


            /**
             * Notifies user the part
             * does not exist
             */
            if (searchPart.size() == 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("Item does not exist");
                alert.showAndWait();
                return;
            }
            else {
                PartTableView.setItems(searchPart);
                PartIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
                PartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
                PartInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
                PartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
            }
        }
        finally {
            if (MainPartSearch.getText().equals("")) {
                PartTableView.setItems(Inventory.getAllParts());
            }
        }
    }


    /**
     *
     * @param event onMouseClick
     *              search for product by ID or String
     */
    @FXML
    void onActionSearchProduct(ActionEvent event) {

        try {
            try {
                int id = Integer.parseInt(MainProductsSearch.getText());
                searchProduct.add(Inventory.lookupProduct(id));
            }
            catch (Exception e) {
                String name = MainProductsSearch.getText();
                searchProduct = Inventory.lookupProduct(name);
            }

            /**
             * Notifies user the product
             * does not exist
             */
            if (searchProduct.size() == 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("Item does not exist");
                alert.showAndWait();
                return;
            }
            else {
                ProductsTableView.setItems(searchProduct);
                ProductIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
                ProductNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
                ProductInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
                ProductPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
            }
        }
        finally {
            if (MainProductsSearch.getText().equals("")) {
                ProductsTableView.setItems(Inventory.getAllProducts());
            }
        }
    }


    /**
     * When "Add" button in Parts pane
     * is clicked by user the screen is
     * changed to the Add Part form.
     *
     * @param event onMouseClick
     *              opens addPart form
     * @throws IOException catches read/write error
     */
    @FXML
    void onActionAddPart(ActionEvent event) throws IOException {
        switchScreen(event, "Add_Part.fxml");
    }


    /**
     * When "Modify" button in Parts pane
     * is clicked by user the screen is changed
     * to the Modify part from
     *
     * @param event onMouseClick
     *              opens the modifyPart form
     * @throws IOException catches read/write error
     */
    @FXML
    void onActionModifyPart(ActionEvent event) throws IOException {
        Part part = PartTableView.getSelectionModel().getSelectedItem();
        if (part == null) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setHeaderText("Please select an part to be modified!");
            alert.setContentText("Please click OK to continue.");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                return;
            }
        }
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("Modify_Part.fxml"));
                loader.load();
                ModifyPart_Controller PartController = loader.getController();
                PartController.GetPart(part);

                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                Parent scene = loader.getRoot();
                stage.setScene(new Scene(scene));
                stage.show();
    }

    /**
     *
     * @param event onMouseClick
     *              will notify user that
     *              they are deleting a part
     *              and will remove the part
     *              when the user clicks OK
     */
    @FXML
    void onActionDeletePart(ActionEvent event) {

        Part part = PartTableView.getSelectionModel().getSelectedItem();
        if (part == null) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setHeaderText("Please select an part to be deleted!");
            alert.setContentText("Please click OK to continue.");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                return;
            }
        }

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setHeaderText("This will remove the Part from the list!");
        alert.setContentText("Are you sure you want to continue?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            deletePart(part.getId());
            return;
        }

    }

    /**
     *
     * @param id user entered Id that will be
     *           used to find a matching part
     * @return If a matching part is found it is
     * deleted, otherwise method returns false.
     */
    public boolean deletePart(int id) {
        for (Part part : Inventory.getAllParts()) {
            if (part.getId() == id)
                return Inventory.getAllParts().remove(part);
        }
        return false;
    }


    /**
     *
     * @param event onMouseClick open addProduct screen
     * @throws IOException catches read/write errors
     */
    @FXML
    void onActionAddProduct(ActionEvent event) throws IOException {
        switchScreen(event, "Add_Product.fxml");
    }


    /**
     *
     * @param event onMouseClick open the modifyProduct form
     * @throws IOException catches read/write errors
     */
    @FXML
    void onActionModifyProduct(ActionEvent event) throws IOException {

        Product product = ProductsTableView.getSelectionModel().getSelectedItem();
        if (product == null) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setHeaderText("Please select an product to be modified!");
            alert.setContentText("Please click OK to continue.");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                return;
            }
        }
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Modify_Product.fxml"));
        loader.load();
        ModifyProduct_Controller ProductController = loader.getController();
        ProductController.getProduct(product);

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }


    /**
     *
     * @param event onMouseClick will delete selected product
     *              from the Main form.
     */
    @FXML
    void onActionDeleteProduct(ActionEvent event) {

        Product product = ProductsTableView.getSelectionModel().getSelectedItem();
        if (product == null) {

            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Please select a Product to delete!");
            alert.setContentText("click OK to continue.");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
             return;
            }
        }else {

            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("CONFIRMATION");
            alert.setHeaderText("This will delete the selected Product.");
            alert.setContentText("Are you sure you want to continue?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                deleteProduct(product.getId());
            return;
            }
        }
    }

    /**
     * This is the method that deletes a specific
     * product based on the Product Id
     * @param id The user enters an int that is
     *           then compared to existing products.
     *           If a matching Id is found, that product
     *           is deleted.
     * @return Returns true if a matching Id is found
     *  and removed, false if no such Id exists.
     */
    public boolean deleteProduct(int id) {

        for (Product product : Inventory.getAllProducts()) {
            if (product.getId() == id)
                return Inventory.getAllProducts().remove(product);
        }
        return false;
    }


    /**
     * This message is displayed if the user clicks "Exit"
     * on the Main Form.
     * @param event onMouseClick will close out program
     */
    @FXML
    void onActionExitProgram(ActionEvent event) {



        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("CONFIRMATION");
        alert.setHeaderText("This will Exit the Program. Any unsaved changes will be lost.");
        alert.setContentText("Are you sure you wish to continue?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }


    /**
     *
     * @param url location of resources
     * @param rb list of resources
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {


        PartTableView.setItems(Inventory.getAllParts());
        PartIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        PartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        PartInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        PartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));


        ProductsTableView.setItems(Inventory.getAllProducts());
        ProductIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        ProductNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        ProductInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ProductPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}