// imports
import java.util.Scanner;

import java.util.Arrays;

/**
 * Input class handles user input,
 * validates user input and returns the correct input type.
 */
class Input {

    // objects
    private Scanner input;

    // variables
    private String currency;


    // constructor
    public Input(String currency) {
        input = new Scanner(System.in);
        this.currency = currency;
    }


    // overload constructor to allow for no currency input
    public Input() {
        input = new Scanner(System.in);
        currency = "EUR"; // default EUR if no currency is given
    }


    // validates a given double as a string
    public double validateDoubleInput(String string_input,
        double min_limit,
        double max_limit,
        boolean no_min_limit,
        boolean no_max_limit,
        double invalid_input) {

        // variables
        double double_user_input;

        // trying to convert string input to double
        try {
            double_user_input = Double.parseDouble(string_input);
        }
        // if conversion fails return as invalid input
        catch (NumberFormatException e) {
            return invalid_input;
        }

        // return the double input if:

        // there is no minimum and no maximum limit,
        // there is no the minimum and the value is less than the maximum limit,
        // the value is more than the minimum and there is no the maximum limit,
        // the value is more than the minimum and less than the maximum limit,

        if ((no_min_limit && no_max_limit) ||
            (no_min_limit && double_user_input <= max_limit) ||
            (double_user_input >= min_limit && no_max_limit) ||
            (double_user_input >= min_limit && double_user_input <= max_limit)) {

            return double_user_input;

        } else {
            return invalid_input;
        }
    }


    // overloaded Input validation with input taken from the user using a scanner
    public double validateDoubleInput(
            double min_limit,
            double max_limit,
            boolean no_min_limit,
            boolean no_max_limit,
            double invalid_input) {

        return validateDoubleInput(input.nextLine(), min_limit, max_limit, no_min_limit, no_max_limit, invalid_input);
    }


    // limit the option choice within the given array
    public int limitOptionChoice(String prompt, int[] options) {

        // variables
        int option = 0;

        // sorts the array of options since range is between the smallest and largest value
        Arrays.sort(options);

        // gets the payment option
        do {
            // prints the prompt and array of options on one line with / as the delimiter
            System.out.printf("\n%s %s: ", prompt, Arrays.toString(options).replace(", ", "/"));

            // gets the input within the valid range using the double validator and casts the returned double to an int
            option = (int) validateDoubleInput(options[0], options[options.length - 1], false, false, 0);

            // error with valid range printed
            if (option == 0) {
                System.out.printf("\nError - Invalid input please enter a valid option between %d and %d.\n",
                        options[0], options[options.length - 1]);
                enterToContinue();
            }

            // repeat while not valid
        } while (option == 0);

        // returns the chosen option
        return option;
    }


    public double getTenderedAmount(String type, double min, double max) {

        double tendered_amount = 0.00;
        String tendered_amount_string;
        boolean no_max_limit = false;

        // pass 0 for no max limit
        if (max == 0) {
            no_max_limit = true;
        }

        // gets the amount tendered
        System.out.printf("\nEnter %s tendered (%s): ", type, currency);
        tendered_amount = validateDoubleInput(min, max, false, no_max_limit, -1);
        tendered_amount_string = Double.toString(tendered_amount); // for validating decimal places

        if (tendered_amount == -1) {

            // error only depends on the min
            if (no_max_limit) {
                System.out.printf("\nError - Invalid input please enter %s tendered greater than %.2f %s.\n", type, min, currency);

            // error depends on the min and max
            } else {
                System.out.printf("\nError - Invalid input please enter %s tendered between %.2f and %.2f %s.\n", type, min, max, currency);
            }
            enterToContinue();

        // to check if the tendered amount has more than 2 decimal places
        // gets the amount of decimal places by getting the difference between .length and index of the '.' - 1 to account for the '.'
        } else if (((tendered_amount_string.length() - tendered_amount_string.indexOf('.')) - 1) > 2) {
            System.out.println("\nError - Invalid input please enter a tendered amount only up to the cent.");
            tendered_amount = -1;
            enterToContinue();
        }

        return tendered_amount;
    }


    public int getMenuChoice(int menu_size) {

        // variables
        int user_choice;

        // getting input
        do {
            System.out.printf("\nEnter choice: ");
            user_choice = (int) validateDoubleInput(1, menu_size, false, false, -1) - 1;

            // processing the input
            if (user_choice == -2) {
                System.out.printf("\nError - Invalid input please enter a menu option between 1 and %d.\n", menu_size);
                enterToContinue();
                user_choice++;
            }
        } while (user_choice == -2);

        // returning the user choice
        return user_choice;
    }


    public int getRemoveItemChoice(int order_size) {

        // variables
        int user_choice;

        // remove item automatically if only one
        if (order_size == 1) {
            return 0;
        }
        // getting input
        do {
            System.out.printf("\nEnter Item Order number to Remove: ");
            user_choice = (int) validateDoubleInput(1, order_size, false, false, -1) - 1;

            // processing the input
            if (user_choice == -2) {
                System.out.printf("\nError - Invalid input please enter an Item between 1 and %d.\n", order_size);
                enterToContinue();
                user_choice++;
            }
        } while (user_choice == -2);

        // returning the user choice
        return user_choice;
    }


    // uses a string input to pause the program output
    // requires user to press enter to continue
    public void enterToContinue() {
        System.out.print("\nEnter to continue.");
        input.nextLine();
    }
}