package model;
/**Models outsourced part*/
public class OutSourced extends Part {

private String companyName;
    /** Constructor for Outsourced part.
     @param id id for outsourced id
     @param name name for outsourced name
     @param stock for outsourced stock
     @param max stock for max outsourced
     @param min for min outsourced
     @param price for price outsourced
     @param companyName  for company Name outsourced*/
    public OutSourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }
    /** Company Name Getter*/
    public String getCompanyName(){
        return companyName;
    }
    /**Company Name Setter. */
    public void setCompanyName(String companyName){
        this.companyName = companyName;
    }

}
