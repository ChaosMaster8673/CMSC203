/**
 * PatientDriverApp.java
 * This class is the driver for the Patient and Procedure classes.
 * It uses a Scanner to read patient information from the keyboard,
 * creates a Patient object with the entered information, creates three
 * Procedure objects using all three constructor styles, and displays
 * the patient information, the three procedures in an aligned table,
 * and a summary of the total, average, and highest charges and the
 * number of expensive procedures.
 *
 * Course: CMSC203 CRN 21305
 * Professor Grinberg
 * Due Date: 09/28/2026
 * Platform/Compiler: Windows 11, javac
 *
 * Integrity Pledge: I pledge that I have completed the programming
 * assignment independently. I have not copied the code from a student
 * or any source.
 *
 * @author Jacen Cheskin
 * @version 1.0
 */
import java.util.Scanner;

public class PatientDriverApp {

    //Reads the patient from the keyboard, creates the three procedures,and displays the patient block, the aligned procedure table, the charge summary, and the required student attribution line.
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        //create the patient object from the keyboard input
        Patient patient = inputPatient(input);

        //create the three procedures, one per constructor style
        Procedure p1 = createProcedure1();
        Procedure p2 = createProcedure2();
        Procedure p3 = createProcedure3();

        //display the patient information block
        displayPatient(patient);

        //display the three procedures in an aligned, tabular format
        displayProcedureTable(p1, p2, p3);

        //display the total, average, and highest charges plus the number of expensive procedures
        displaySummary(p1, p2, p3);

        //required student attribution line
        System.out.println("The program was developed by a Student: Jacen Cheskin 09/28/26");
    }

    //prompts the user for every patient attribute via the Scanner and returns the completed Patient object
    public static Patient inputPatient(Scanner input) {
        Patient patient = new Patient();
        System.out.print("Enter first name: ");
        patient.setFirstName(input.nextLine());
        System.out.print("Enter middle name: ");
        patient.setMiddleName(input.nextLine());
        System.out.print("Enter last name: ");
        patient.setLastName(input.nextLine());
        System.out.print("Enter street address: ");
        patient.setStreetAddress(input.nextLine());
        System.out.print("Enter city: ");
        patient.setCity(input.nextLine());
        System.out.print("Enter state: ");
        patient.setState(input.nextLine());
        System.out.print("Enter zip: ");
        patient.setZipCode(input.nextLine());
        System.out.print("Enter phone number (###-###-####): ");
        patient.setPhoneNumber(input.nextLine());
        System.out.print("Enter emergency contact name: ");
        patient.setEmergencyContactName(input.nextLine());
        System.out.print("Enter emergency contact phone (###-###-####): ");
        patient.setEmergencyContactPhoneNumber(input.nextLine());

        return patient;
    }

    //builds the first procedure using the no-argument constructor and mutators for every attribute
    public static Procedure createProcedure1() {
        Procedure procedure1 = new Procedure();
        procedure1.setName("Physical Exam");
        procedure1.setDate("07/20/2026");
        procedure1.setPractitioner("Dr. Irvine");
        procedure1.setCharges(1000.00);
        procedure1.applyDiscount(20.0); // apply a 20% discount to the charges
        return procedure1;
    }

    //builds the second procedure using the name-and-date constructor, then sets the remaining attributes
    public static Procedure createProcedure2() {
        Procedure procedure2 = new Procedure("X-ray", "07/20/2026");
        procedure2.setPractitioner("Dr. Jamison");
        procedure2.setCharges(550.43);
        return procedure2;
    }

    //builds the third procedure using the full-argument constructor with every attribute provided
    public static Procedure createProcedure3() {
        Procedure procedure3 = new Procedure("Blood Test", "07/20/2026", "Dr. Smith", 1400.75);
        return procedure3;
    }

    //prints the patient information block in the format shown in the sample output, using the Patient build methods and the phone number validity checks
    public static void displayPatient(Patient patient) {
        System.out.println("Patient Information");
        System.out.println("-------------------");
        System.out.println("Name: " + patient.buildFullName());
        System.out.println("Address: " + patient.buildAddress());
        System.out.println("Phone Number: " + patient.getPhoneNumber());
        System.out.println("Emergency Contact: " + patient.buildEmergencyContact());
        System.out.println("Phone Valid: " + patient.isValidPhoneNumber());
        System.out.println("Emergency Phone Valid: " + patient.isValidEmergencyContactPhoneNumber());
    }

    //prints the full details of a single procedure
    public static void displayProcedure(Procedure procedure) {
        System.out.println("Procedure Information:");
        System.out.println(procedure);
    }

    //prints the header row, the separator line, and one aligned row per procedure showing the name, date, practitioner, comma-formatted charge, and charge category
    public static void displayProcedureTable(Procedure p1, Procedure p2, Procedure p3) {
        System.out.printf("%-20s %-13s %-19s %-16s %s%n",
                "Procedure", "Date", "Practitioner", "Charge", "Category");
        System.out.println("------------------------------------------------------------------------");
        printProcedureRow(p1);
        printProcedureRow(p2);
        printProcedureRow(p3);
    }

    //prints a single aligned row of the procedure table
    private static void printProcedureRow(Procedure procedure) {
        System.out.printf("%-20s %-13s %-19s %-16s %s%n",
                procedure.getName(),
                procedure.getDate(),
                procedure.getPractitioner(),
                procedure.getFormattedCharge(),
                procedure.getChargeCategory());
    }

    //adds together the charges of all three procedures
    public static double calculateTotalCharges(Procedure p1, Procedure p2, Procedure p3) {
        return p1.getCharges() + p2.getCharges() + p3.getCharges();
    }

    //divides the total charges by three
    public static double calculateAverageCharge(Procedure p1, Procedure p2, Procedure p3) {
        return calculateTotalCharges(p1, p2, p3) / 3;
    }

    //compares the charges of the three procedures and returns the one with the largest charge
    public static Procedure findHighestChargeProcedure(Procedure p1, Procedure p2, Procedure p3) {
        Procedure highest = p1;
        if (p2.getCharges() > highest.getCharges()) {
            highest = p2;
        }
        if (p3.getCharges() > highest.getCharges()) {
            highest = p3;
        }
        return highest;
    }

    //counts how many of the three procedures are flagged as expensive by the Procedure class
    public static int countExpensiveProcedures(Procedure p1, Procedure p2, Procedure p3) {
        int count = 0;
        if (p1.isExpensiveProcedure()) {
            count++;
        }
        if (p2.isExpensiveProcedure()) {
            count++;
        }
        if (p3.isExpensiveProcedure()) {
            count++;
        }
        return count;
    }

    //prints the total charges, the average charge, the name of the highest-charge procedure, and the count of expensive procedures, all with comma-separated dollar formatting
    public static void displaySummary(Procedure p1, Procedure p2, Procedure p3) {
        System.out.println("Total Charges: " + String.format("$%,.2f", calculateTotalCharges(p1, p2, p3)));
        System.out.println("Average Charge: " + String.format("$%,.2f", calculateAverageCharge(p1, p2, p3)));
        System.out.println("Highest Charge Procedure: " + findHighestChargeProcedure(p1, p2, p3).getName());
        System.out.println("Number of Expensive Procedures: " + countExpensiveProcedures(p1, p2, p3));
    }

}
