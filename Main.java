package main;
import Model.*;
import View_Controller.MainForm_Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


/**
 *
 * RunTime error encountered:
 * Invocation Target Exception - I encountered this error
 * because in the switch path field for each
 * screen I didn't specify the .fxml file extension
 * in the document. The IDE highlighted getClass().getResource(switchPath)
 * as the problem when it was actually the file name used in the
 * instance of the call throwing the error.
 *
 *
 * Future enhancement:
 *
 * To improve this program
 * I would expand on the inventory count feature
 * to specify the number of a part instance is used
 * for a specific product
 *
 * ex. scissor jack requires 6 screws.
 * when screw is added to product it will
 * subtract 6 screws from the inventory count of that
 * part.
 */


/**
 * Loads the FXML controller for the
 * main form in the project
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainForm_Controller.class.getResource("/View_Controller/MainForm.fxml"));


            Parent root = fxmlLoader.load();
            MainForm_Controller mainFormController = fxmlLoader.getController();
            Scene scene = new Scene(root);

            stage.setTitle("Inventory Management System");
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * The Javadoc folder is in the
     * "MatthewKeyesInventory" Module
     * in the "JavaDoc" folder.
     *
     *
     * This is the main function that
     * is called when the program is ran.
     * Parts and Products are populated
     * automatically.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Part InHousePart1 = new InhousePart(1, "Control Arm", 139.25, 10, 5, 50, 1);
        Part InHousePart2 = new InhousePart(2, "Bracket", 75.56, 5, 2, 10, 1);
        Part InHousePart3 = new InhousePart(3, "Screws", .02, 100, 25, 250, 2);


        Part OutsourcedPart1 = new OutsourcedPart(4, "Sump Pump", 60.00, 18, 10, 20, "United Pump Co.");
        Part OutsourcedPart2 = new OutsourcedPart(5, "Mineral Oil", 11.60, 20, 5, 30, "Smith Lubricants");
        Part OutsourcedPart3 = new OutsourcedPart(6, "Electric Relay", 1800.95, 3, 1, 10, "");


        Product product1 = new Product(1, "Solar Generator", 1399.99, 4, 1, 4);
        product1.addAssociatedPart(InHousePart1);
        Product product2 = new Product(2, "Scissor Jack", 2150.95, 1, 1, 4);
        product2.addAssociatedPart(InHousePart2);
        Product product3 = new Product(3, "Lithium Batteries", 21.50, 20, 10, 40);
        product3.addAssociatedPart(OutsourcedPart3);
        Product product4 = new Product(4, "Safety Suit", 324.00, 5, 1, 10);
        product4.addAssociatedPart(InHousePart3);



        Inventory.addPart(InHousePart1);
        Inventory.addPart(InHousePart2);
        Inventory.addPart(InHousePart3);


        Inventory.addPart(OutsourcedPart1);
        Inventory.addPart(OutsourcedPart2);
        Inventory.addPart(OutsourcedPart3);


        Inventory.addProduct(product1);
        Inventory.addProduct(product2);
        Inventory.addProduct(product3);
        Inventory.addProduct(product4);

        launch(args);
    }

}
