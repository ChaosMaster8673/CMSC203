import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import org.junit.jupiter.api.Test;

/**
 * Class: CMSC203
 * Instructor: Professor Grinberg
 * Description: JUnit tests for the ManagementCompany class. Checks adding
 * properties, the copy stored in the array, the full-array error case,
 * total rent, property count, and the deep copy constructor.
 * Due: 10/26/2026
 * Platform/compiler: Java 26, Eclipse IDE
 * I pledge that I have completed the programming
 * assignment independently. I have not copied the code
 * from a student or any source. I have not given my code
 * to any student.
 *   Print your Name here: Jacen Cheskin
 */
public class ManagementCompanyJUnitTestStudent {

    // addProperty must store the property and return the index it was placed at
    @Test
    public void testAddPropertyReturnsIndex() {
        ManagementCompany mc = new ManagementCompany("Test Realty", "987-65-4321");
        Property p = new Property("Apt A", "Rockville", 1200.00, "A. Owner");
        assertEquals(0, mc.addProperty(p));
        assertEquals(1, mc.addProperty(new Property("Apt B", "Gaithersburg", 1500.00, "B. Owner")));
        assertEquals(2, mc.getPropertyCount());
    }

    // addProperty must store a copy, changing the original afterwards must not affect the array
    @Test
    public void testAddPropertyStoresACopy() {
        ManagementCompany mc = new ManagementCompany("Test Realty", "987-65-4321");
        Property p = new Property("Apt A", "Rockville", 1200.00, "A. Owner");
        mc.addProperty(p);
        p.setCity("Changed City");
        p.setRentAmount(1.00);
        assertEquals("Rockville", mc.getProperty(0).getCity());
        assertEquals(1200.00, mc.getProperty(0).getRentAmount(), 0.001);
    }

    // full array, the 6th add must fail with -1 and the count must not change
    @Test
    public void testAddPropertyWhenFull() {
        ManagementCompany mc = new ManagementCompany("Test Realty", "987-65-4321");
        for (int i = 0; i < ManagementCompany.MAX_PROPERTIES; i++) {
            mc.addProperty(new Property("P" + i, "City", 1000.00, "Owner"));
        }
        int result = mc.addProperty(new Property("One Too Many", "City", 500.00, "Owner"));
        assertEquals(-1, result);
        assertEquals(ManagementCompany.MAX_PROPERTIES, mc.getPropertyCount());
    }

    // totalRent, empty company sums to 0, and several properties sum to the exact total
    @Test
    public void testTotalRent() {
        ManagementCompany mc = new ManagementCompany("Test Realty", "987-65-4321");
        assertEquals(0.0, mc.totalRent(), 0.001);
        mc.addProperty(new Property("A", "City", 1000.00, "O"));
        mc.addProperty(new Property("B", "City", 250.75, "O"));
        mc.addProperty(new Property("C", "City", 449.25, "O"));
        assertEquals(1700.00, mc.totalRent(), 0.001);
    }

    // deep copy constructor, same data in the copy, but brand new Property objects
    @Test
    public void testDeepCopyConstructor() {
        ManagementCompany original = new ManagementCompany("Campus Realty", "123-45-6789");
        original.addProperty(new Property("Apt 1", "Rockville", 1200.00, "Bob"));
        original.addProperty(new Property("Apt 2", "Rockville", 1500.00, "Carol"));

        ManagementCompany copy = new ManagementCompany(original);

        assertEquals(original.getName(), copy.getName());
        assertEquals(original.getTaxId(), copy.getTaxId());
        assertEquals(original.getPropertyCount(), copy.getPropertyCount());
        assertEquals(original.totalRent(), copy.totalRent(), 0.001);
        assertNotSame(original.getProperty(0), copy.getProperty(0));
        assertNotSame(original.getProperty(1), copy.getProperty(1));
    }

    // deep copy independence, adding to or editing the original must not change the copy
    @Test
    public void testDeepCopyIndependence() {
        ManagementCompany original = new ManagementCompany("Campus Realty", "123-45-6789");
        original.addProperty(new Property("Apt 1", "Rockville", 1200.00, "Bob"));

        ManagementCompany copy = new ManagementCompany(original);

        original.addProperty(new Property("Apt 2", "Bethesda", 999.00, "X"));
        original.getProperty(0).setCity("Somewhere Else");

        assertEquals(1, copy.getPropertyCount());
        assertEquals("Rockville", copy.getProperty(0).getCity());
    }
}
