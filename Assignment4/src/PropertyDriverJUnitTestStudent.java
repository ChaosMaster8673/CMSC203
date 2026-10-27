import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Class: CMSC203
 * Instructor: Professor Grinberg
 * Description: JUnit tests for the PropertyDriver. The driver's main method
 * reads from the console, so these tests exercise the same steps main
 * performs: creating the Campus Realty company, the add-property path, and
 * the total-rent path.
 * Due: 10/26/2026
 * Platform/compiler: Java 26, Eclipse IDE
 * I pledge that I have completed the programming
 * assignment independently. I have not copied the code
 * from a student or any source. I have not given my code
 * to any student.
 *   Print your Name here: Jacen Cheskin
 */
public class PropertyDriverJUnitTestStudent {

    // the company the driver creates in main must start out empty with the right details
    @Test
    public void testDriverCompanySetup() {
        ManagementCompany company = new ManagementCompany("Campus Realty", "123-45-6789");
        assertEquals("Campus Realty", company.getName());
        assertEquals("123-45-6789", company.getTaxId());
        assertEquals(0, company.getPropertyCount());
    }

    // the added property must be stored and readable back
    @Test
    public void testDriverAddPropertyPath() {
        ManagementCompany company = new ManagementCompany("Campus Realty", "123-45-6789");
        Property p = new Property("Property-1", "Rockville", 3500.00, "Joe");
        int index = company.addProperty(p);
        assertEquals(0, index);
        assertEquals("Property-1", company.getProperty(0).getPropertyName());
        assertEquals("Rockville", company.getProperty(0).getCity());
        assertEquals(3500.00, company.getProperty(0).getRentAmount(), 0.001);
        assertEquals("Joe", company.getProperty(0).getOwner());
    }

    // the two example properties total 6000
    @Test
    public void testDriverTotalRentPath() {
        ManagementCompany company = new ManagementCompany("Campus Realty", "123-45-6789");
        company.addProperty(new Property("Property-1", "Rockville", 3500.00, "Joe"));
        company.addProperty(new Property("Property-2", "Gaithersburg", 2500.00, "Mary"));
        assertEquals(6000.00, company.totalRent(), 0.001);
    }
}
