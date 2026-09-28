/**
 * Procedure class represents a medical procedure with its details.
 * It includes attributes such as name, date, practitioner, and charges.
 * The class provides constructors, getters, setters, and additional methods
 * to manipulate and retrieve information about the procedure.
 */

public class Procedure {

    //initialize variables
    private String name;
    private String date;
    private String practitioner;
    private double charges;

    //constructors
    //no argument constructor
    public Procedure() {
        this.name = "";
        this.date = "";
        this.practitioner = "";
        this.charges = 0.0;
    }

    // constructor with name and date
    public Procedure(String name, String date) {
        this.name = name;
        this.date = date;
        this.practitioner = "";
        this.charges = 0.0;
    }

    // constructor with all variables
    public Procedure(String name, String date, String practitioner, double charges) {
        this.name = name;
        this.date = date;
        this.practitioner = practitioner;
        this.charges = charges;
    }

    //getters and setters
    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPractitioner() {
        return practitioner;
    }

    public void setPractitioner(String practitioner) {
        this.practitioner = practitioner;
    }

    public double getCharges() {
        return charges;
    }

    public void setCharges(double charges) {
        this.charges = charges;
    }

    //toString method
    public String toString() {
        return "Procedure Name: " + name + "\nDate: " + date + "\nPractitioner: " + practitioner + "\nCharges: $" + charges;
    }

    //additional methods

    //expensive procedure method
    public boolean isExpensive() {
        return charges > 1000;
    }

    //apply discount method
    public void applyDiscount(double percentage) {
        charges = charges - (charges * (percentage / 100));
    }

    //get charge category method
    public String getChargeCategory() {
        if (charges < 100) {
            return "Low";
        } else if (charges >= 100 && charges <= 500) {
            return "Medium";
        } else {
            return "High";
        }
    }

    //is performed by practitioner method name

    public boolean isPerformedBy(String practitionerName) {
        return practitioner.equalsIgnoreCase(practitionerName);
    }

    //get formatted charges method
    public String getFormattedCharge() {
        return String.format("$%.2f", charges);
    }

}
