package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Inventory class contains methods for adding, looking up,
 * removing, incrementing and decrementing parts and products
 */
public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static ObservableList<Part> searchedParts = FXCollections.observableArrayList();
    private static ObservableList<Product> searchedProducts = FXCollections.observableArrayList();

     static int partCounter = 6;
     static int productCounter = 4;

    public static ObservableList<Part> getAllParts() { return allParts; }
    public static ObservableList<Product> getAllProducts() { return allProducts; }


    /**
     *
     * @param partId int entered by user
     * @return Part with ID matching user entered int
     */
    public static Part lookupPart(int partId) {
        int index = 0;
        searchedParts.clear();
        for (int i = 0; i < allParts.size(); i++) {
            if (allParts.get(i).getId() == partId) {
                index = i;
                return allParts.get(index);
            }
        }
        return searchedParts.get(index);
    }


    /**
     *
     * @param partName user entered string used to find matching part
     * @return part containing the matching substring
     */
    public static ObservableList<Part> lookupPart(String partName) {
        searchedParts.clear();
        for (int i = 0; i < allParts.size(); i++) {
            if (allParts.get(i).getName().toLowerCase().contains(partName.toLowerCase())) {
                searchedParts.add(allParts.get(i));
            }
        }
        return searchedParts;
    }


    /**
     *
     * @param inputID int entered by user to find part
     * @return returns part with matching ID
     */

    public static int lookupPartIndex(int inputID) {
        for (Part lookupPart : allParts) {
            if (lookupPart.getId() == inputID) {
                return allParts.indexOf(lookupPart);
            }
        }
        return -1;
    }


    /**
     *
     * @param newPart part being added to list
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     *
     * @param id Id of part to be deleted
     * @return If no Id is found return false
     */
    public boolean deletePart(int id) {
        for (Part part : Inventory.getAllParts()) {
            if (part.getId() == id) {
                Inventory.getAllParts().remove(part);
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param moddedPart ID of part being modified
     * @param modifiedPart values being changed in part instance
     */
    public static void updatePart(int moddedPart, Part modifiedPart) {
        allParts.set(moddedPart, modifiedPart);
    }


    /**
     * @param productID int entered by user to find product
     * @return product matching the ID entered by user
     */
    public static Product lookupProduct(int productID) {
        int index = 0;
        searchedProducts.clear();
        for (Product p : allProducts) {
            if (p.getId() == productID) {
                return p;
            }
        }
        return searchedProducts.get(index);
    }


    /**
     *
     * @param productName String entered by user to find product
     * @return product matching the string entered by user
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        searchedProducts.clear();
        for (int i = 0; i < allProducts.size(); i++) {
            if (allProducts.get(i).getName().toLowerCase().contains(productName.toLowerCase())) {
                searchedProducts.add(allProducts.get(i));
            }
        }
        return searchedProducts;
    }


    /**
     *
     * @param productID ID entered by user to find product
     * @return product matching the ID
     */
    public static int lookupProductIndex(int productID) {
        searchedProducts.clear();
        for (Product lookupProduct : allProducts) {
            if (lookupProduct.getId() == productID) {
                return allProducts.indexOf(lookupProduct);
            }
        }
        return -1;
    }


    /**
     *
     * @param newProduct product that is being created
     */
    public static void addProduct(Product newProduct)
    {
        allProducts.add(newProduct);
    }


    /**
     * This is the method to delete a product
     * @param id User entered Id that will be
     *           used to find the correct product
     * @return True if matching Id is found,
     *         False if no matching Id is found.
     */
    public static boolean deleteProduct(int id) {
        for (Product p : getAllProducts()) {
            if (p.getId() == id) {
                Inventory.getAllProducts().remove(p);
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param index list of products
     * @param updatedProduct product being updated
     */
    public static void updateProduct(int index, Product updatedProduct) {
        allProducts.set(index, updatedProduct);
    }


    /**
     *
     * @return increment PartID running total by 1
     */
    public static int partIncrement() {
        partCounter++;
        return partCounter;
    }

    /**
     *
     * @return decrement PartID running total by 1
     */
    public static int partDecrement(){
        partCounter--;
        return partCounter;
    }


    /**
     *
     * @return increment ProductID running total by 1
     */
    public static int productIncrement() {
        productCounter++;
        return productCounter;
    }

    /**
     *
     * @return decrement ProductID running total by 1
     */
    public static int productDecrement(){
        productCounter--;
        return productCounter;
    }
}