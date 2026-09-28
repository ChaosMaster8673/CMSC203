


/**
 * Patient class represents a patient with their personal and contact information.
 * It includes attributes such as name, address, and emergency contact details.
 * The class provides constructors, getters, setters, and additional methods
 * to manipulate and retrieve information about the patient.
 */
public class Patient {
    //initialize variables

    // name of the patient
    private String firstName;
    private String middleName;
    private String lastName;

    // address of the patient
    private String streetAddress;
    private String city;
    private String state;
    private String zipCode;

    //phone number and emergency contact
    private String phoneNumber;
    private String emergencyContactName;
    private String emergencyContactPhoneNumber;

    //constructors
    //no argument constructor
    public Patient() {
        this.firstName = "";
        this.middleName = "";
        this.lastName = "";
        this.streetAddress = "";
        this.city = "";
        this.state = "";
        this.zipCode = "";
        this.phoneNumber = "";
        this.emergencyContactName = "";
        this.emergencyContactPhoneNumber = "";
    }

    // name constructor
    public Patient(String firstName, String middleName, String lastName) {
        //initialize variables that we have
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;

        //initialize variables that we don't have
        this.streetAddress = "";
        this.city = "";
        this.state = "";
        this.zipCode = "";
        this.phoneNumber = "";
        this.emergencyContactName = "";
        this.emergencyContactPhoneNumber = "";
    }

    // all variable constructor
    public Patient(String firstName, String middleName, String lastName, String streetAddress, String city, String state, String zipCode, String phoneNumber, String emergencyContactName, String emergencyContactPhoneNumber) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.streetAddress = streetAddress;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.phoneNumber = phoneNumber;
        this.emergencyContactName = emergencyContactName;
        this.emergencyContactPhoneNumber = emergencyContactPhoneNumber;
    }

    //accessor and getter methods

    //first name
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    //middle name
    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    //last name
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    //address
    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    //phone number and emergency contact
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmergencyContactName() {
        return emergencyContactName;
    }

    public void setEmergencyContactName(String emergencyContactName) {
        this.emergencyContactName = emergencyContactName;
    }

    public String getEmergencyContactPhoneNumber() {
        return emergencyContactPhoneNumber;
    }

    public void setEmergencyContactPhoneNumber(String emergencyContactPhoneNumber) {
        this.emergencyContactPhoneNumber = emergencyContactPhoneNumber;
    }

    // full first name method
    public String buildFullName() {
        String fullName = firstName;
        fullName += " " + middleName;
        fullName += " " + lastName;
        return fullName;
    }

    //build full address method
    public String buildAddress() {
        String fullAddress = streetAddress;
        fullAddress += " " + city;
        fullAddress += " " + state;
        fullAddress += " " + zipCode;
        return fullAddress;
    }

    //build emergency contact method
    public String buildEmergencyContact() {
        String emergencyContact = emergencyContactName;
        emergencyContact += " " + emergencyContactPhoneNumber;
        return emergencyContact;
    }

    //toString
    public String toString() {
        return "Patient Name: " + buildFullName() + "\n" +
                "Address: " + buildAddress() + "\n" +
                "Phone Number: " + phoneNumber + "\n" +
                "Emergency Contact: " + buildEmergencyContact();
    }

    //method for checking if phone number is valid
    public boolean isValidPhoneNumber() {
        //check if correct number of digits
        if (phoneNumber.length() != 12) {
            return false;
        }
        //check for dashes and digits
        for (int i = 0; i < phoneNumber.length(); i++) {
            if (i != 3 && i != 7){
                if (!Character.isDigit(phoneNumber.charAt(i))) {
                    return false;
                }
            } else{
                if (phoneNumber.charAt(i) != '-') {
                    return false;
                }

            }

        }
        return true;
    }

    //check if emergency contact phone number is valid
    public boolean isValidEmergencyContactPhoneNumber() {
        //check if emergency contact phone number is 12 digits
        if (emergencyContactPhoneNumber.length() != 12) {
            return false;
        }
        //check if phone number is all digits except for dashes at the correct positions
        for (int i = 0; i < emergencyContactPhoneNumber.length(); i++) {
            if (i != 3 && i != 7) {
                if (!Character.isDigit(emergencyContactPhoneNumber.charAt(i))) {
                    return false;
                }
            } else {
                if (emergencyContactPhoneNumber.charAt(i) != '-') {
                    return false;
                }
            }
        }
        return true;
    }

    //get name in last, first, middle format
    public String getLastFirstMiddle() {
        String fullName = lastName;
        fullName += ", " + firstName;
        fullName += " " + middleName;
        return fullName;
    }

    //has same city state
    public boolean hasSameCityState(String city, String state) {
        return this.city.equals(city) && this.state.equals(state);
    }

    //update street address/zipcode
    public void updateAddress(String streetAddress, String city, String state, String zipCode) {
        this.streetAddress = streetAddress;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
    }

    //get contact summary
    public String getContactSummary() {
        return "Patient Name: " + buildFullName() + "\n" +
                "Phone Number: " + phoneNumber + "\n" +
                "Emergency Contact: " + buildEmergencyContact();
    }
}