package Model;

/**
 * OutsourcedPart subclass of Part class
 * gets company name who created part
 */
public class OutsourcedPart extends Part {

    private String companyName;


    /**
     *
     * @param id Outsourced part incrementer
     * @param name name of Outsourced part
     * @param price cost per unit of part
     * @param stock number of parts in the system
     * @param min minimum number of parts allowed
     * @param max maximum number of parts allowed
     * @param companyName name of company that created part
     */
    public OutsourcedPart(int id, String name, Double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }


    /**
     *
     * @return Company name
     */
    public String getCompanyName() {
        return companyName;
    }


    /**
     *
     * @param companyName Name of company that created
     *                    the outsourced part
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}