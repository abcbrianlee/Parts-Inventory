package model;
/** Models an inhouse part.*/
public class InHouse extends Part {
    /**Machine Id for inhouse part*/
    private int machineID;

    /**
     Constructor for new instance on inhouse object.
     @param id for ID part
     @param name  for name part
     @param stock for stock park
     @param max for max part
     @param min for min part
     @param machineID for machineID part*/
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineID) {
        super(id, name, price, stock, min, max);
        this.machineID = machineID;
    }
    /** MachineID Getter*/
    public int getMachineID(){
        return machineID;
    }
    /**MachineID Setter*/
    public void setMachineID(int machineID){
        this.machineID = machineID;
    }
}
