package controller;

/** This class is an extension of the part class and inherits several attributes from it. */
public class InHouse extends Part {
    private int machineId;

    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /** This method is used to obtain the machine ID of an InHouse type part.
     * @return  machineId The machine id of the part*/
    public int getMachineId() {
        return machineId;
        }

    /** This method is used to set the machine ID of an InHouse type part.
     * @param machineId the machine id to set */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}