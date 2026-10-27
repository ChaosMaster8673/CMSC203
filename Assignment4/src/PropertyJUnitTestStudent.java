import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Class: CMSC203
 * Instructor: Professor Grinberg
 * Description: JUnit tests for the Property class. Checks the default
 * constructor, the parameterized constructor, the copy constructor,
 * the getters/setters, and the required toString format.
 * Due: 10/26/2026
 * Platform/compiler: Java 26, Eclipse IDE
 * I pledge that I have completed the programming
 * assignment independently. I have not copied the code
 * from a student or any source. I have not given my code
 * to any student.
 *   Print your Name here: Jacen Cheskin
 */
public class PropertyJUnitTestStudent {

    // no-arg constructor, every field must start at its default value
    @Test
    public void testDefaultConstructor() {
        Property empty = new Property();
        assertEquals("", empty.getPropertyName());
        assertEquals("", empty.getCity());
        assertEquals(0.0, empty.getRentAmount(), 0.001);
        assertEquals("", empty.getOwner());
    }

    // parameterized constructor, getters must return exactly what was passed in
    @Test
    public void testParameterizedConstructor() {
        Property p = new Property("Lakeside Apts", "Gaithersburg", 1850.50, "M. Rivera");
        assertEquals("Lakeside Apts", p.getPropertyName());
        assertEquals("Gaithersburg", p.getCity());
        assertEquals(1850.50, p.getRentAmount(), 0.001);
        assertEquals("M. Rivera", p.getOwner());
    }

    // copy constructor, the copy must hold the same field values as the original
    @Test
    public void testCopyConstructorValues() {
        Property original = new Property("Oak Court", "Bethesda", 2400.00, "J. Doe");
        Property copy = new Property(original);
        assertEquals(original.getPropertyName(), copy.getPropertyName());
        assertEquals(original.getCity(), copy.getCity());
        assertEquals(original.getRentAmount(), copy.getRentAmount(), 0.001);
        assertEquals(original.getOwner(), copy.getOwner());
    }

    // copy constructor, changing the original afterwards must not change the copy
    @Test
    public void testCopyConstructorIndependence() {
        Property original = new Property("Oak Court", "Bethesda", 2400.00, "J. Doe");
        Property copy = new Property(original);
        original.setCity("Silver Spring");
        original.setRentAmount(999.99);
        assertEquals("Bethesda", copy.getCity());
        assertEquals(2400.00, copy.getRentAmount(), 0.001);
    }

    // setters actually change the stored values
    @Test
    public void testSetters() {
        Property p = new Property();
        p.setPropertyName("New Name");
        p.setCity("New City");
        p.setRentAmount(750.25);
        p.setOwner("New Owner");
        assertEquals("New Name", p.getPropertyName());
        assertEquals("New City", p.getCity());
        assertEquals(750.25, p.getRentAmount(), 0.001);
        assertEquals("New Owner", p.getOwner());
    }

    // toString follows the required display format
    @Test
    public void testToStringFormat() {
        Property p = new Property("Property-1", "Rockville", 3500.00, "Joe");
        String expected = "Property-1, Rockville, Rent: $3500.00, Owner: Joe";
        assertEquals(expected, p.toString());
    }
}
