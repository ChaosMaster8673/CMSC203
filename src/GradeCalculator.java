/*
 * Class: CMSC203
 * Instructor: Professor Grinberg
 * Description: take 2 files as input and calculate the final grade of a student based on the weights of each category and the grades in each category
 * Due: 9/14/2026
 * Platform/compiler: Windows 11, Java 21, IntelliJ IDEA
 * I pledge that I have completed the programming assignment
  independently. I have not copied the code from a student or   * any source. I have not given my code to any student.
 * Print your Name here: Jacen Cheskin
*/


import java.io.PrintWriter;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class GradeCalculator {
    public static void main(String[] args) throws IOException {
        //initialize scanners
        Scanner keyboard = new Scanner(System.in);

        //initialize default config

        boolean useDefaultConfig = false;
        String defaultCourseName = "CMSC 203 Computer Science 1";
        int defaultNumberOfGradedCategories = 3;
        String defaultCat1 = "Projects"; int defaultWeight1 = 40;
        String defaultCat2 = "Quizzes";  int defaultWeight2 = 30;
        String defaultCat3 = "Exams";    int defaultWeight3 = 30;

        //initialize file holding variables
        String configFileName;
        String gradeFileName;

        //course name and number of graded categories
        String courseName = ""; //IDE complains if this is not initialized, even though it is set later in the code
        int numberOfGradedCategories = 0; //IDE complains if this is not initialized, even though it is set later in the code

        //initialize plusMinus variable
        char plusMinus = 0; //if this was not initialized and the user entered an empty string, the program would crash because it would try to compare an uninitialized variable to 'Y' and 'N'

        // initialize student information
        String studentFirstName;
        String studentLastName;

        //initialize final average weight variable
        double finalAverageWeight = 0.0;


        //get config file name

        System.out.println("Enter the file name of the config file: ");
        configFileName = keyboard.nextLine();
        if ((!new File(configFileName).exists())) {
            System.out.println("File not found. Using default configuration.");
            useDefaultConfig = true;
        }

        //if we have not used the default config, read the config file
        if (!useDefaultConfig) {
            Scanner configFile = new Scanner(new File(configFileName));
            courseName = configFile.nextLine();


            // get course name and graded categories

            numberOfGradedCategories = configFile.nextInt();

            //  start reading category and calculate total weight
            int weightSum = 0;
            for (int i = 0; i < numberOfGradedCategories; i++) {
                configFile.next();      // category name - do not store it since I cannot use arrays
                weightSum += configFile.nextInt();
            }

            //check if sum of categories is 100
            if (weightSum != 100) {
                useDefaultConfig = true;
                System.out.println("Default configuration use, the sum of all category weights is not equal to 100");
            }
            configFile.close();
        }

        //separate if statement is needed instead of else or else if in case the weight sum was not equal to 100, and now we need the default config
        if (useDefaultConfig) {
            courseName = defaultCourseName;
            numberOfGradedCategories = defaultNumberOfGradedCategories;
        }

        //get grade file name

        System.out.println("Enter the grade file's name: ");
        gradeFileName = keyboard.nextLine();
        if (!new File(gradeFileName).exists()) {
            System.out.println("Error: File not found.");
            System.exit(1);
        }
        Scanner gradeFile = new Scanner(new File(gradeFileName));
        //ask user if they want plus minus grading

        do {
            System.out.println("Would you like to apply +/- grading? (Y/N): ");
            String testForEmpty =  keyboard.nextLine().toUpperCase();
            if (!testForEmpty.equals("")) {
                plusMinus = testForEmpty.charAt(0); // charAt() of an empty string crashes, so check length first
            }

        } while ( (plusMinus != 'Y' && plusMinus != 'N'));

        //read student information
        studentFirstName = gradeFile.nextLine();
        studentLastName = gradeFile.nextLine();

        //need to start displaying here since I do not want to reread everything again
        PrintWriter report = new PrintWriter("grades_report.txt");
        System.out.println("Course: " + courseName);
        report.println("Course: " + courseName);
        System.out.println("Student: " + studentFirstName + " " + studentLastName);
        report.println("Student: " + studentFirstName + " " + studentLastName);
        System.out.println("Default configuration was" + (useDefaultConfig ? " used" : " not used."));
        report.println("Default configuration was" + (useDefaultConfig ? " used" : " not used."));

        //get grades from file and check if invalid
        //loop through all categories from config

        //only do this if there is a custom config, if default config is not used, since we need to read the config file to get the category names and weights
        Scanner configFile = (useDefaultConfig) ? null : new Scanner(new File(configFileName));
        if (configFile != null) {
            configFile.nextLine(); //skip course name
            configFile.nextLine(); //skip numberOfGradedCategories (we already have this value)
        }


        for (int i = 0; i < numberOfGradedCategories; i++) {
            String category = gradeFile.nextLine();

            // have invalid in case of invalid scores
            int invalid = 0;

            //ensure category names match and default config is not used
            if (!useDefaultConfig && category.equals(configFile.next())) {
                int numberOfGrades = gradeFile.nextInt();
                double sumOfGrades = 0;
                //loop  through each grade
                for (int j = 0; j < numberOfGrades; j++) {
                    double grade = gradeFile.nextDouble();
                    if (grade < 0 || grade > 100) { // it's not possible to get a grade less than 0 or greater than 100, so we will not include it in the average
                        invalid++;
                    } else {
                        sumOfGrades += grade;
                    }
                }

                if (numberOfGrades != invalid) {
                    //display non-weighted average
                    double average = sumOfGrades / (numberOfGrades - invalid);
                    System.out.printf("Average for category %s: %.2f\n", category, average);
                    report.printf("Average for category %s: %.2f\n", category, average);

                    double weightedAverage = average * (configFile.nextInt() / 100.0);
                    finalAverageWeight += weightedAverage;
                   System.out.printf("Weighted average for category %s: %.2f\n", category, weightedAverage);
                    report.printf("Weighted average for category %s: %.2f\n", category, weightedAverage);

                } else {
                    System.out.println("All grades in category " + category + " are invalid.");
                    report.println("All grades in category " + category + " are invalid.");
                }

                // [BUG - desync, 1 test failing] The nextLine() below is ONLY reached when the
                // if-branch ran. When ALL grades are invalid, the else-branch above runs and this
                // newline is never consumed - so on the NEXT loop iteration nextLine() returns the
                // leftover score text (e.g. "90.0") as a "category name" -> false
                // "does not match" error, and every category after that one is misread.
                // FIX: the newline must be consumed on BOTH paths, e.g. move this line OUT of the
                // if-block so it always runs (or add the same nextLine() to the else above).
                gradeFile.nextLine(); // consume the newline

            } else if (useDefaultConfig){
                int numberOfGrades = gradeFile.nextInt();
                double sumOfGrades = 0;
                //loop  through each grade
                for (int j = 0; j < numberOfGrades; j++) {
                    double grade = gradeFile.nextDouble();
                    if (grade < 0 || grade > 100) { // it's not possible to get a grade less than 0 or greater than 100, so we will not include it in the average
                        invalid++;
                    } else {
                        sumOfGrades += grade;
                    }
                }

                if (numberOfGrades != invalid) {
                    //display non-weighted average
                    double average = sumOfGrades / (numberOfGrades - invalid);
                    System.out.printf("Average for category %s: %.2f\n", category, average);
                    report.printf("Average for category %s: %.2f\n", category, average);

                    // display weighted average
                    int weight;
                    if (category.equals(defaultCat1)) weight = defaultWeight1;
                    else if (category.equals(defaultCat2)) weight = defaultWeight2;
                    else weight = defaultWeight3;

                    double weightedAverage = average * (weight / 100.0);
                    finalAverageWeight += weightedAverage;
                   System.out.printf("Weighted average for category %s: %.2f\n", category, weightedAverage);
                    report.printf("Weighted average for category %s: %.2f\n", category, weightedAverage);

                } else {
                    System.out.println("All grades in category " + category + " are invalid.");
                    report.println("All grades in category " + category + " are invalid.");
                }

                // [BUG - same desync as in the custom-config branch above] When all grades in
                // this category are invalid, the nextLine() below never runs, and the next
                // category is misread. Same fix: consume the newline on BOTH paths.
                gradeFile.nextLine(); // consume the newline

            }else { // if category names don't match
                System.out.println("Category name in grade file does not match config file.");
                report.println("Category name in grade file does not match config file.");
                // discard bad category information
                // (correct: per the file format the whole score list is ONE line, so
                //  skipping the count line + one line drops the entire bad block)
                gradeFile.nextLine(); gradeFile.nextLine(); // skip the category name and number of grades
                configFile.nextLine();
            }
        }

        //close scanner for grades and config file since we have read through it all
        gradeFile.close();

        //close configFile is it was opened
        if (configFile != null) {
            configFile.close();
        }

        //display overall numeric average
        System.out.printf("Overall numeric average: %.2f\n", finalAverageWeight);
        report.printf("Overall numeric average: %.2f\n", finalAverageWeight);

        //determine letter grade

        String letterGrade = "";
        if (finalAverageWeight >= 90 && finalAverageWeight <= 100) {
            letterGrade = "A";
        } else if (finalAverageWeight >= 80) {
            letterGrade = "B";
        } else if (finalAverageWeight >= 70 ) {
            letterGrade = "C";
        } else if (finalAverageWeight >= 60) {
            letterGrade = "D";
        } else {
            letterGrade = "F";
        }

        //display base grade
        System.out.println("Base letter grade: " + letterGrade);
        report.println("Base letter grade: " + letterGrade);
        //apply plus minus grading if true

        //get the decimal
        // my cutoffs (spec allows defining my own): decimal part of the numeric average
        // above 70 gives "+", below 30 gives "-", otherwise no suffix; 100.0 is special-cased to A+
        double totalWeightedAverageDecimal = (finalAverageWeight*100)%100;

        if (plusMinus == 'Y') {
            if (totalWeightedAverageDecimal > 70 || finalAverageWeight == 100.0)  { // 100% would have given A- since the decimal would have been 0, so we special-case it here
                letterGrade += "+";
            } else if (totalWeightedAverageDecimal < 30) {
                letterGrade += "-";
            }
            //display final letter grade
            System.out.println("Final letter grade: " + letterGrade);
            report.println("Final letter grade: " + letterGrade);
        }

        System.out.println("Summary written to grades_report.txt");
        System.out.println("Program complete. Goodbye!");

        //display programmers name as per rubric
        System.out.println("Programmer: Jacen Cheskin");

        report.close();
        keyboard.close();

    }
}
