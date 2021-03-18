// imports
import java.util.Scanner;
import java.util.Arrays; // for converting arrays

/**
 * Input class handles user input,
 * validates user input and returns the correct input type.
 */
class Input {

    // variables
    Scanner input;

    // constructor
    public Input() {
        input = new Scanner(System.in);
    }

    // Input validation
    public double validateDoubleInput(
            double min_limit,
            double max_limit,
            boolean no_min_limit,
            boolean no_max_limit,
            double invalid_input) {

        // variables
        double double_user_input;

        // trying to convert string input to double
        try {
            double_user_input = Double.parseDouble(input.nextLine());
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

        if ((no_min_limit && no_max_limit) || (no_min_limit && double_user_input <= max_limit)
                || (double_user_input >= min_limit && no_max_limit)
                || (double_user_input >= min_limit && double_user_input <= max_limit)) {

            return double_user_input;

        } else {
            return invalid_input;
        }
    }

    // Input validation
    public double validateDoubleInput( String string_user_input,
        double min_limit,
        double max_limit,
        boolean no_min_limit,
        boolean no_max_limit,
        double invalid_input) {

    //variables
    double double_user_input;

    // trying to convert string input to double
    try {
        double_user_input = Double.parseDouble(string_user_input);
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
            option = (int) this.validateDoubleInput(options[0], options[options.length - 1], false, false, 0);

            // error with valid range printed
            if (option == 0) {
                System.out.printf("\nError - Invalid input please enter a valid option between %d and %d.\n",
                options[0], options[options.length - 1]);
            }

            // repeat while not valid
        } while (option == 0);

        // returns the chosen option
        return option;
    }

    public double getTenderedAmount(String type, double min, double max) {

        double tendered_amount = 0.00;
        String tendered_amount_string;
        boolean invalid = false;
        boolean no_max_limit = false;

        // pass 0 for no max limit
        if (max == 0) {
            no_max_limit = true;
        }

        // gets cash tendered
        do {
            // gets the amount tendered
            System.out.printf("\nEnter %s tendered (EUR): ", type);
            tendered_amount = this.validateDoubleInput(min, max, false, no_max_limit, 0);
            tendered_amount_string = Double.toString(tendered_amount); // for validating decimal places

            if (tendered_amount == 0) {
                invalid = true;

                // error only depends on the min
                if (no_max_limit) {
                    System.out.printf("\nError - Invalid input please enter a tendered amount greater than %.2f EUR.\n", min);

                // error depends on the min and max
                } else {
                    System.out.printf("\nError - Invalid input please enter a tendered amount between %.2f and %.2f EUR.\n", min, max);
                }

            // to check if the tendered amount has more than 2 decimal places
            // gets the amount of decimal places by getting the difference between .length and index of the '.' - 1 to account for the '.'
            } else if (((tendered_amount_string.length() - tendered_amount_string.indexOf('.')) - 1) > 2) {
                invalid = true;
                System.out.println("\nError - Invalid input please enter a tendered amount only up to the cent.");
            // if the input is valid
            } else {
                invalid = false;
            }

        // repeat while invalid input
        } while (invalid);

        return tendered_amount;
    }

    public int getMenuChoice(int menu_size) {

        // variables
        int exit_num = menu_size;
        int max_value = exit_num;

        // getting input
        System.out.printf("\nEnter choice: ", exit_num);
        int user_choice = (int) this.validateDoubleInput(1, max_value, false, false, -1) - 1;

        // processing the input
        if (user_choice == -2) {
            System.out.printf("\nError - Invalid input please enter a menu option between 1 and %d.\n", max_value);
            this.enterToContinue();
            user_choice++;
        }

        // returning the user choice
        return user_choice;
    }

    public int getRemoveItemChoice(int order_size) {

        // variables
        int exit_num = order_size;
        int max_value = exit_num;

        // getting input
        System.out.printf("\nEnter Item to remove: ", exit_num);
        int user_choice = (int) this.validateDoubleInput(1, max_value, false, false, -1) - 1;

        // processing the input
        if (user_choice == -2) {
            System.out.printf("\nError - Invalid input please enter an Item between 1 and %d.\n", max_value);
            this.enterToContinue();
            user_choice++;
        }

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