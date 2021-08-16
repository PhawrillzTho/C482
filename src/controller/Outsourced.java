package controller;

/** This class is an extension of the part class and inherits several attributes from it. */
public class Outsourced extends Part {

    private String companyName;

    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /** This method is used to obtain the company name of an Outsourced type part.
     * @return companyName Returns the companyname. */
    public String getCompanyName() {
        return companyName;
    }

    /** This method is used to set the company name of an Outsourced type part.
     * @param companyName  The company name to set. */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
