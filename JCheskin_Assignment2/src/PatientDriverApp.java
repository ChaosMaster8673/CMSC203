/**
 * PatientDriverApp.java
 * This class is the driver for the Patient and Procedure classes.
 * It prompts the user to enter patient information, creates three procedures,
 * and displays a summary of the patient and procedures.
 *
 * @author Your Name
 * @version 1.0
 */

import java.util.Scanner;

public class PatientDriverApp {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        Patient patient = inputPatient(input);
        System.out.println(patient);
    }

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
        System.out.print("Enter zip code: ");
        patient.setZipCode(input.nextLine());
        System.out.print("Enter phone number: ");
        patient.setPhoneNumber(input.nextLine());
        System.out.print("Enter emergency contact name: ");
        patient.setEmergencyContactName(input.nextLine());
        System.out.print("Enter emergency contact phone number: ");
        patient.setEmergencyContactPhoneNumber(input.nextLine());

        return patient;
    }

    //three methods to create three different procedures
    public static Procedure createProcedure1() {
        Procedure procedure1 = new Procedure("X-ray", "2023-01-15", "Dr. Smith", 200.00);
        return procedure1;
    }

    public static Procedure createProcedure2() {
        Procedure procedure2 = new Procedure("Blood Test", "2023-02-20", "Dr. Johnson", 150.00);
        return procedure2;
    }

    public static Procedure createProcedure3() {
        Procedure procedure3 = new Procedure("MRI", "2023-03-10", "Dr. Lee", 1000.00);
        return procedure3;
    }

    public static void displayPatient(Patient patient) {
        System.out.println("Patient Information:");
        System.out.println(patient);
    }

    //display procedures
    public static void displayProcedure(Procedure procedure) {
        System.out.println("Procedure Information:");
        System.out.println(procedure);
    }

    public static void displayProcedureTable(Procedure p1, Procedure p2, Procedure p3) {
        System.out.println("Procedure Table:");
        displayProcedure(p1);
        displayProcedure(p2);
        displayProcedure(p3);
    }

    //calculate total/average charges

    public static double calculateTotalCharges(Procedure p1, Procedure p2, Procedure p3) {
        return p1.getCharges() + p2.getCharges() + p3.getCharges();
    }

    public static double calculateAverageCharges(Procedure p1, Procedure p2, Procedure p3) {
        return calculateTotalCharges(p1, p2, p3) / 3;
    }

    //find highest charges

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

    //count expensive procedures
    public static int countExpensiveProcedures(Procedure p1, Procedure p2, Procedure
            p3) {
        int count = 0;
        if (p1.isExpensive()) {
            count++;
        }
        if (p2.isExpensive()) {
            count++;
        }
        if (p3.isExpensive()) {
            count++;
        }
        return count;
    }

    //display summary
    public static void displaySummary(Patient patient, Procedure p1, Procedure p2, Procedure p3) {
        System.out.println("Summary:");
        displayPatient(patient);
        displayProcedureTable(p1, p2, p3);
        System.out.println("Total Charges: $" + calculateTotalCharges(p1, p2, p3));
        System.out.println("Average Charges: $" + calculateAverageCharges(p1, p2, p3));
        System.out.println("Highest Charge Procedure: " + findHighestChargeProcedure(p1, p2, p3).getName() + " - $" + findHighestChargeProcedure(p1, p2, p3).getCharges());
        System.out.println("Number of Expensive Procedures: " + countExpensiveProcedures(p1, p2, p3));
    }

}
