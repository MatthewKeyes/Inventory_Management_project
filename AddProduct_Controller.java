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
 * Controller for the Add Product form in my program.
 */
public class AddProduct_Controller implements Initializable {

    Stage stage;
    Parent scene;

    Product product;

    @FXML
    private TextField AddProductSearch;
    @FXML
    private TextField AddProductId;
    @FXML
    private TextField AddProductName;
    @FXML
    private TextField AddProductStock;
    @FXML
    private TextField AddProductPrice;
    @FXML
    private TextField AddProductMax;
    @FXML
    private TextField AddProductMin;
    @FXML
    private TableView<Part> TableView1;
    @FXML
    private TableColumn<Part, Integer> AddProdIdColumn1;
    @FXML
    private TableColumn<Part, String> AddProdNameColumn1;
    @FXML
    private TableColumn<Part, Integer> AddProdInvColumn1;
    @FXML
    private TableColumn<Part, Double> AddProdPriceColumn1;
    @FXML
    private TableView<Part> TableView2;
    @FXML
    private TableColumn<Part, Integer> AddProdIdColumn2;
    @FXML
    private TableColumn<Part, String> AddProdNameColumn2;
    @FXML
    private TableColumn<Part, Integer> AddProdInvColumn2;
    @FXML
    private TableColumn<Part, Double> AddProdPriceColumn2;

    ObservableList<Part> search = FXCollections.observableArrayList();
    ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int productCounter = Inventory.productIncrement();


    /**
     * @param event      onMouseClick in SceneBuilder is the event
     * @param switchPath The form the program will get for the user
     * @throws IOException This method has the potential to throw a file related
     *                     exception.
     */
    private void changeScreen(ActionEvent event, String switchPath) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource(switchPath));
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }


    /**
     * Searches Product list by parsing String input
     * to an int and comparing to the current Product Id's
     *
     * @param event Clicking "Search" to filter the parts list
     */
    @FXML
    void onActionSearchProduct(ActionEvent event) {
        try {
            try {
                int id = Integer.parseInt(AddProductSearch.getText());
                search.add(Inventory.lookupPart(id));
            } catch (Exception e) {
                String name = AddProductSearch.getText();
                search = Inventory.lookupPart(name);
            }
            if (search.size() == 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("Item does not exist");
                alert.showAndWait();
                return;
            } else {
                TableView1.setItems(search);
                AddProdIdColumn1.setCellValueFactory(new PropertyValueFactory<>("id"));
                AddProdNameColumn1.setCellValueFactory(new PropertyValueFactory<>("name"));
                AddProdInvColumn1.setCellValueFactory(new PropertyValueFactory<>("stock"));
                AddProdPriceColumn1.setCellValueFactory(new PropertyValueFactory<>("price"));
            }
        }


        /**
         * If no matching int ID or String name is present
         */
        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Product does not exist!");
        }
        if (AddProductSearch.getText().equals("")) {
            TableView1.setItems(Inventory.getAllParts());
        }
    }


    /**
     * Adds the selected part to the Product
     * being created.
     *
     * @param event clicking "Add" with a part highlighted in the list
     *              will add that part to the product being built.
     */
    @FXML
    void onActionAddPart(ActionEvent event) {

        Part part = TableView1.getSelectionModel().getSelectedItem();

        if (part == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Select a part to add!");
            alert.showAndWait();
            return;
        }
        if(product.getAllAssociatedParts().contains(part)){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ERROR");
                    alert.setContentText("Part already added to product!");
                    alert.showAndWait();
            }
        else
        {
            product.getAllAssociatedParts().add(part);
        }
    }


    /**
     * Deletes selected part from the Associated Parts table.
     * If no part is selected, an error message is displayed.
     *
     */
    public void onActionDeletePart() {

        ObservableList<Part> selectedRow, allParts;
        allParts = TableView2.getItems();
        selectedRow = TableView2.getSelectionModel().getSelectedItems();

        if (allParts == null) {
            Alert selectAlert = new Alert(Alert.AlertType.CONFIRMATION);
            selectAlert.setTitle("Alert");
            selectAlert.setContentText("You must select a part to delete!");
            Optional<ButtonType> result = selectAlert.showAndWait();

            if(result.get() == ButtonType.OK);
            {
                return;
            }
        }
        else {

            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Alert");
            confirmAlert.setContentText("Do you want to remove the selected part?");
            Optional<ButtonType> result = confirmAlert.showAndWait();

            if (result.get() == ButtonType.OK) {
                for (Part part: selectedRow)
                {
                    product.getAllAssociatedParts().remove(part);
                }
            }
        }
    }


    /**
     * Saves Product to Product list after new Product has been
     * created with available parts.
     *
     * @param event clicking "Save" after adding the needed parts to the new product
     * @throws IOException possible exceptions during read/write to file
     */
    @FXML
    void onActionSaveProduct(ActionEvent event) throws IOException {

        int id = Integer.parseInt(AddProductId.getText());
        String name = AddProductName.getText();
        int stock = Integer.parseInt(AddProductStock.getText());
        Double price = Double.parseDouble(AddProductPrice.getText());
        int max = Integer.parseInt(AddProductMax.getText());
        int min = Integer.parseInt(AddProductMin.getText());

        Inventory.addProduct(new Product(id, name, price, stock, min, max, product.getAllAssociatedParts()));

        if(AddProductName.getText() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Product must have a name.");
            alert.showAndWait();
            return;
        }
        if(min > max){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Quantity minimum is larger than maximum.");
            alert.showAndWait();
            return;
        }
        if (stock > max){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Inventory quantity exceeds maximum stock allowed.");
            alert.showAndWait();
            return;
        }
        if (stock < min){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Inventory quantity is below minimum stock allowed.");
            alert.showAndWait();
            return;
        }
        changeScreen(event, "MainForm.fxml");
    }


    /**
     *
     * @param event clicking "cancel" without saving a new product
     * @throws IOException possible exceptions during read/write to file
     */
    @FXML
    void onActionDisplayMainScreen(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Changes will not be Saved.");
        alert.setContentText("Would you like to continue?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            productCounter = Inventory.productDecrement();
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("MainForm.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
        return;
    }


    /**
     * Opens the Add Product screen
     *
     * @param url find relative paths between root object and child files
     * @param rb resources used to localize the root object
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {


        product = new Product();
        AddProductId.setText(Integer.toString(productCounter));

        /**
         * Top table in the Add Product form
         * This holds all the parts that can be
         * added to create a new product.
         */
        TableView1.setItems(Inventory.getAllParts());
        AddProdIdColumn1.setCellValueFactory(new PropertyValueFactory<>("id"));
        AddProdNameColumn1.setCellValueFactory(new PropertyValueFactory<>("name"));
        AddProdInvColumn1.setCellValueFactory(new PropertyValueFactory<>("stock"));
        AddProdPriceColumn1.setCellValueFactory(new PropertyValueFactory<>("price"));


        /**
         * Bottom table in the Add Product form
         * This will hold all the parts the user
         * selects to create a new product.
         */
        TableView2.setItems(product.getAllAssociatedParts());
        AddProdIdColumn2.setCellValueFactory(new PropertyValueFactory<>("id"));
        AddProdNameColumn2.setCellValueFactory(new PropertyValueFactory<>("name"));
        AddProdInvColumn2.setCellValueFactory(new PropertyValueFactory<>("stock"));
        AddProdPriceColumn2.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}