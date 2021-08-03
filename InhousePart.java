package Model;


/**
 * This is the Inhouse part subclass
 * of the abstract part class. This
 * class adds the machineID get
 * and set methods.
 */
public class InhousePart extends Part {

    private int machineId;


    /**
     *
     * @param id part id incrementer
     * @param name name of part
     * @param price cost per unit
     * @param stock number of parts in stock
     * @param min smallest number of parts allowed
     * @param max largest number of parts allowed
     * @param machineId ID of machine that created the part
     */
    public InhousePart(int id, String name, Double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }


    /**
     *
     * @param machineId sets machine ID to Inhouse part
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }


    /**
     *
     * @return machineID
     */
    public int getMachineId() {
        return machineId;
    }
}
