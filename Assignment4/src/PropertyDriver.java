/**
 * Class: CMSC203
 * Instructor: Professor Grinberg
 * Description: Console driver for Assignment 4. Creates the Campus Realty
 * ManagementCompany and runs a 4-option menu: add a property, display all
 * properties, show total rent, and exit. Demonstrates arrays of objects,
 * copy constructors, and aggregation.
 * Due: 10/26/2026
 * Platform/compiler: Java 26, Eclipse IDE
 * I pledge that I have completed the programming
 * assignment independently. I have not copied the code
 * from a student or any source. I have not given my code
 * to any student.
 *   Print your Name here: Jacen Cheskin
 */

/**
 * main, creates the campus reality company, and loops on a 4 option menu and performs the action.
 */
public class PropertyDriver {
	public static void main(String[] args) {
		
		ManagementCompany company = new ManagementCompany("Campus Realty", "123-45-6789");
		boolean running = true;
		java.util.Scanner scanner = new java.util.Scanner(System.in);

		while (running) {
			System.out.println("====================================");
			System.out.println("   Assignment 4: Property Manager");
			System.out.println("====================================");
			System.out.println("1 - Add Property");
			System.out.println("2 - Display All Properties");
			System.out.println("3 - Show Total Rent");
			System.out.println("4 - Exit");
			System.out.print("Enter choice (1-4): ");
			int choice = scanner.nextInt();
			scanner.nextLine(); // consume newline

			switch (choice) {
				case 1:
					System.out.print("Enter property name: ");
					String name = scanner.nextLine();
					System.out.print("Enter city: ");
					String city = scanner.nextLine();
					System.out.print("Enter monthly rent: ");
					double rent = scanner.nextDouble();
					scanner.nextLine(); // consume newline
					System.out.print("Enter owner name: ");
					String owner = scanner.nextLine();

					Property newProperty = new Property(name, city, rent, owner);
					int result = company.addProperty(newProperty);
					if (result == -1) {
						System.out.println("The company's property list is full. Nothing was added.");
					} else {
						System.out.println("Property added at index " + result);
					}
					break;
				case 2:
					System.out.println(company.toString());
					break;
				case 3:
					System.out.printf("Total rent: $%.2f%n", company.totalRent());
					break;
				case 4:
					running = false;
					break;
				default:
					System.out.println("Invalid choice. Please enter a number from 1 to 4.");
			}
		}
		scanner.close();
		
		//print name
		System.out.println("Jacen Cheskin");
	}
}
