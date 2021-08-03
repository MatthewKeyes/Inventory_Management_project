package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Product class covers all methods
 * related to Product class
 *
 */
public class Product {

private final ObservableList<Part> associatedParts = FXCollections.observableArrayList();

private int id;
private String name;
private int inv;
private Double price;
private int max;
private int min;


    /**
     *
     * @param id Increments product id
     * @param name name of product
     * @param price cost per unit of product
     * @param inv number of product instances
     * @param min minimum number of products allowed
     * @param max maximum number of products allowed
     * @param associatedParts parts used to create a product
     */
    public Product(int id, String name, Double price, int inv, int min, int max, ObservableList associatedParts) {
        this.id = id;
        this.name = name;
        this.inv = inv;
        this.price = price;
        this.max = max;
        this.min = min;
        this.associatedParts.addAll(associatedParts);
        }

    /**
     *
     * @param id Increments product id
     * @param name name of product
     * @param price cost per unit of product
     * @param inv number of product instances
     * @param min minimum number of products allowed
     * @param max maximum number of products allowed
     */
    public Product(int id, String name, Double price, int inv, int min, int max) {
        this.id = id;
        this.name = name;
        this.inv = inv;
        this.price = price;
        this.max = max;
        this.min = min;
        }


public Product() {}

    /**
     *
     * @return product ID
     */
    public int getId() {
        return id;
        }

    /**
     *
     * @return product name
     */
    public String getName() {
        return name;
        }

    /**
     *
     * @return product inventory
     */
    public int getStock() {
        return inv;
        }

    /**
     *
     * @return product price
     */
    public Double getPrice() {
        return price;
        }

    /**
     *
     * @return maximum number of products
     */
    public int getMax() {
        return max;
        }

    /**
     *
     * @return minimum number of products
     */
    public int getMin() {return min; }


    /**
     *
     * @param id set ID for product
     */
    public void setId(int id) {this.id = id;}

    /**
     *
     * @param name set product name
     */
    public void setName(String name) {
        this.name = name;
        }

    /**
     *
     * @param inv number of product instances
     */
    public void setStock(int inv) {
        this.inv = inv;
        }

    /**
     *
     * @param price cost of single product instance
     */
    public void setPrice(Double price) {
        this.price = price;
        }

    /**
     *
     * @param max maximum number of product instances allowed
     */
    public void setMax(int max) {
        this.max = max;
        }

    /**
     *
     * @param min minimum number of product instances allowed
     */
    public void setMin(int min) {
        this.min = min;
        }

    /**
     *
     * @return list of associated parts
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }


    /**
     *
     * @param part part being associated to product
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }


    /**
     *
     * @param selectedAssociatedPart  user selected part to be removed
     * @return true if part is found and removed. False if not matching
     * ID is found in the associatedParts list
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        if(associatedParts.contains(selectedAssociatedPart)){
            associatedParts.remove(selectedAssociatedPart);
            return true;
        }
        return false;
    }
}