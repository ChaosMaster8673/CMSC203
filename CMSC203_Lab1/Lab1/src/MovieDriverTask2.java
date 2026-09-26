import java.util.Scanner;

public class MovieDriverTask2 {
    public static void main(String[] args) {
        //create scanner that reads from keyboard
        Scanner keyboard = new Scanner(System.in);
        String ask = "";

        while (!ask.equals("n")) {

            //initialize variables
            String title;
            String rating; //age rating
            int ticketsSold;

            //make new movie object
            Movie movie = new Movie();

            //get inputs and set values

            //title
            System.out.print("Enter title: ");
            title = keyboard.nextLine();
            movie.setTitle(title);

            //rating
            System.out.print("Enter rating: ");
            rating = keyboard.nextLine();
            movie.setRating(rating);

            //tickets sold
            System.out.print("Enter tickets sold: ");
            ticketsSold = keyboard.nextInt();
            movie.setSoldTickets(ticketsSold);

            keyboard.nextLine(); // consume the newline character left by nextInt()

            //print it out
            System.out.println(movie.toString());

            System.out.println("Do you want to enter another? (y or n)");
            ask = keyboard.nextLine();
        }
    }
}
