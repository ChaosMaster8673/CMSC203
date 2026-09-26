/**
 * Assignment Name: Lab1 Movie Driver Task 1
 * File Name: MovieDriverTask1.java
 * Author: Jacen Cheskin
 * Class: CMSC 203
 * Professor: Professor Grinberg
 * Description: This program creates a Movie object and allows the user to input the title, rating, and number of tickets sold. It then displays the information about the movie.
 */

import java.util.Scanner;

public class MovieDriverTask1 {
    public static void main(String[] args) {
        //create scanner that reads from keyboard
        Scanner keyboard = new Scanner(System.in);

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

        //print it out
        System.out.println(movie.toString());

    }
}