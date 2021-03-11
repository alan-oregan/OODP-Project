// imports
import java.util.Scanner; // for the inputs and file reading
import java.util.ArrayList; // for the arrays
import java.util.Arrays; // for converting arrays
import java.lang.Double; // for converting string to double
import java.io.File; // Import the File class
import java.io.FileWriter; // for reading files
import java.io.IOException; // Import the IOException class to handle file write errors
import java.io.FileNotFoundException; // Import this class to handle file read errors
import java.util.Date; // for getting current timestamp

// Notes -----------------------------------------------------------------------------------------------------------
// https://www.youtube.com/watch?v=yO_ctH4mEk4
// https://stackoverflow.com/questions/16956720/how-to-create-an-2d-arraylist-in-java/16956747
// https://www.w3schools.com/java/java_arraylist.asp
// https://www.youtube.com/watch?v=lcWV7hLYByk
// https://stackoverflow.com/questions/2979383/java-clear-the-console
// https://www.youtube.com/watch?v=Vs2ZR7-LJO0
// https://www.w3schools.com/java/java_files_read.asp
// https://www.w3schools.com/java/java_files_create.asp
// https://stackoverflow.com/questions/2255500/can-i-multiply-strings-in-java-to-repeat-sequences
// https://stackoverflow.com/questions/16956720/how-to-create-an-2d-arraylist-in-java/16956747
// https://www.w3schools.com/java/java_arraylist.asp
// https://www.tutorialspoint.com/convert-from-string-to-double-in-java#:~:text=To%20convert%20String%20to%20double%2C%20use%20the%20valueOf()%20method.
// https://www.geeksforgeeks.org/format-specifiers-in-java/


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
            boolean nomin_limit,
            boolean nomax_limit,
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

        if ((nomin_limit && nomax_limit) ||
            (nomin_limit && double_user_input <= max_limit) ||
            (double_user_input >= min_limit && nomax_limit) ||
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
        boolean invalid = false;
        boolean no_max_limit = false;

        // pass 0 for no max limit
        if (max == 0) {
            no_max_limit = true;
        }

        // gets cash tendered
        do {
            // gets the amount tendered
            System.out.printf("Enter %s tendered (EUR): ", type);
            tendered_amount = this.validateDoubleInput(min, max, false, no_max_limit, 0);

            if (tendered_amount == 0) {
                invalid = true;

                // error only depends on the min
                if (no_max_limit) {
                    System.out.printf("\nError - Invalid input please enter a tendered amount greater than %.2f EUR.\n", min);

                // error depends on the min and max
                } else {
                    System.out.printf("\nError - Invalid input please enter a tendered amount between %.2f and %.2f EUR.\n", min, max);
                }

            // if amount has more than 2 decimal places
            // to do this it converts the double to a string and splits it with "."
            // .split returns a string[] with the delimiter "." being the included in the second value
            // then gets the amount of decimal places from the second string in the array with .length() - 1 accounting for the "." delimiter
            } else if ((Double.toString(tendered_amount).split(".", 2)[1].length() - 1) > 2) {
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
        System.out.printf("Enter your choice: ", exit_num);
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

    // uses a string input to pause the program output
    // requires user to press enter to continue
    public void enterToContinue() {
        System.out.print("\nEnter to continue.");
        input.nextLine();
    }
}


/**
 * FileHandler class handles reading to and from the files for transactions and inventory.
 */
class FileHandler {

    //objects
    Input in;

    // variables
    private String file_path;
    private ArrayList<String[]> dataTable = new ArrayList<String[]>();

    // constructor
    public FileHandler(String file_path) {
        in = new Input();
        this.file_path = file_path;
    }

    // reads the csv file and returns it as a 2d arrayList
    public ArrayList<String[]> readCSV() {
        try {
            File fileObject = new File(file_path);
            Scanner myReader = new Scanner(fileObject);

            myReader.nextLine(); // skip first line (header)

            // loops through the item rows/lines in the file while they exist
            while (myReader.hasNextLine()) {

                // getting the row as an array split by ,
                String[] item_row = myReader.nextLine().split(",");

                dataTable.add(item_row);
            }

            myReader.close(); // closes the Scanner

        } catch (FileNotFoundException e) {
            System.out.printf("Error - file %s not found.\n", file_path);
            e.printStackTrace();
            in.enterToContinue();
        }

        return dataTable;
    }

    public void writeCSV(ArrayList<String[]> dataTable) {

        try {

            FileWriter wtr = new FileWriter(file_path, true); // append mode set to true

            // gets the system line separator for new lines
            String newLine = System.getProperty("line.separator");

            // wtr.write(key + newLine); // adds Key for csv on the line and moves cursor to the next

            // loops through the rows in the 2d array
            for (String[] row : dataTable) {

                // writes the items with , separator to the line for csv format
                // finishes before the last item
                int i;
                for (i = 0; i < row.length-1; i++)
                    wtr.write(row[i] + ", ");

                // writes the last item without the comma and moves the cursor for the next line
                wtr.write(row[i++] + newLine);
            }

            wtr.close(); // closes the FileWriter

        // catches any IOException and prints the cause
        } catch(IOException e) {
            System.out.printf("Transaction File Error:\n%s\n", e);
            in.enterToContinue();
        }
    }
}


/**
 * Menu class handles anything related to the menu in the coffee system.
 * displays the menu in the appropriate format,
 * processes the users choice,
 * holds the transaction data.
 */
class Menu {

    // objects
    FileHandler inventory_fh;
    FileHandler transactions_fh;
    Input in;

    // variables
    private ArrayList<String[]> menu_list;
    private ArrayList<String[]> transaction_data = new ArrayList<String[]>();
    private int menu_choice;
    private int spacing = 25;
    private String indent = "    "; // 4 spaces for indent to keep consistency between terminals replaces \t

    // separating output for better readability
    private String system_separator = "=".repeat(this.spacing + 20);
    private String item_separator = "-".repeat(this.spacing + 12);

    // if the program should exit, do while loop checks this public variable
    public static boolean exit = false;

    // constructor
    public Menu(String inventory_path, String transaction_path, int spacing) {

        // object declarations
        inventory_fh = new FileHandler(inventory_path);
        transactions_fh = new FileHandler(transaction_path);
        in = new Input();

        //variables
        this.menu_list = inventory_fh.readCSV();
        this.spacing = spacing; // can be changed depending on desired spacing
    }

    // clears the screen using the ansi escape codes to keep things clean
    // uses the codes 2J to clear the console and H to return to the top of the console
    // flushes the buffer stream to avoid possible data loss
    public void clearScreen() {
        System.out.println(system_separator); // if terminal doesn't support ansi codes
        System.out.print("\033[2J\033[H");
        System.out.flush();
    }

    // prints the values in an appropriate format
    public void displayMenu() {
        int item_ID = 1;

        //menu title with spacing
        System.out.printf("\n%s%s\n", " ".repeat(spacing/2 + 5), "System Menu");

        System.out.println(system_separator + "\n");

        System.out.printf("%s%s\n", " ".repeat(spacing/2 + 6), "Cafe Menu");

        System.out.println(indent + item_separator);

        // loops through the values in the 2D ArrayList
        for (String[] item : menu_list) {
            System.out.printf("%s%d. %s%s%5.2f EUR\n",
            indent, item_ID++, item[0], ".".repeat(spacing - item[0].length()), Float.parseFloat(item[1]));
        }

        System.out.println(indent + item_separator);

        System.out.printf("%s%d. Exit\n", indent, item_ID++);

        System.out.println("\n" + system_separator);
    }

    public void menuChoice() {
        menu_choice = in.getMenuChoice(menu_list.size() + 1);

        // exit choice
        if (menu_choice == menu_list.size()) {
            in.input.close(); // close the input scanner
            exit = true; // set exit to true for do while to exit program

            // Cafe menu choice
        } else if (menu_choice != -1) {
            this.getTransaction();
        }

        // if transaction data is not empty
        if (transaction_data.isEmpty() == false) {
            // writes the transaction data to the transactions csv
            transactions_fh.writeCSV(transaction_data);
        }
    }

    public void getTransaction() {

        // an array that contains all the information to be written to the transactions.csv
        // Format: Date and time stamp, Item Purchased, Price, Amount tendered / Card type, Change given
        String[] transaction_row = new String[5];

        // add time stamp
        Date time_stamp = new Date(); // gets the current time and date
        transaction_row[0] = time_stamp.toString(); //adds it as a string to the row array

        // add item Purchased
        transaction_row[1] = menu_list.get(menu_choice)[0]; // gets the chosen item name from the menu ArrayList

        // add Price (validate in future)
        double price = Float.parseFloat(menu_list.get(menu_choice)[1]); // gets item price and converts it to a double
        transaction_row[2] = String.format("%.2f", price);

        // gets the payment option
        int payment_option = in.limitOptionChoice("Cash/Card?", new int[] { 1, 2 });

        // amount tendered / card type
        String tendered_amount_or_card_type = "Error";

        // change given
        String change_given = "N/A";

        // cash is 1
        if (payment_option == 1) {

            // amount tendered
            double tendered_amount = in.getTenderedAmount("Cash", price, 0);
            tendered_amount_or_card_type = String.format("%.2f", tendered_amount);

            // change given (2 ways)

            // fixed change
            change_given = String.format("%.2f", (tendered_amount - price));

            // variable change
            // change_given = String.format("%.2f", in.getTenderedAmount("Change", 0.01, (tendered_amount - price)));

            // card is 2
        } else if (payment_option == 2) {

            // get card type
            int card_type = in.limitOptionChoice("Visa/Mastercard?", new int[] { 1, 2 });

            //returns 1 for Visa
            if (card_type == 1) {
                tendered_amount_or_card_type = "Visa";

                //returns  2 for mastercard
            } else if (card_type == 2) {
                tendered_amount_or_card_type = "Mastercard";
            }

            // change given (if card then no change given)
            change_given = "N/A";
        }

        // tendered amount / card type
        transaction_row[3] = tendered_amount_or_card_type;

        // change given
        transaction_row[4] = change_given;

        // prints the receipt
        this.displayReceipt(transaction_row, payment_option);

        // if the ArrayList is not empty, add the String[] row to the ArrayList, then clear it
        if (transaction_row.length > 0) {
            this.transaction_data.add(transaction_row);
            transaction_row = new String[transaction_row.length];
            // since one item at a time
            this.transaction_data.clear();
        }
    }

    public void displayReceipt(String[] transaction_row, int payment_type) {
        System.out.printf("\n%s%s%s\n", indent, " ".repeat(this.item_separator.length() / 2 - 4), "Receipt");

        System.out.println(indent + this.item_separator);

        // prints receipt in an appropriate format
        // transaction_row format: Date and time stamp, Item Purchased, Price, Amount tendered / Card type, Change given

        System.out.printf("%sTime: %s\n", indent, transaction_row[0]);
        System.out.printf("\n%sItem/s Purchased\n", indent);
        System.out.printf("%s%" + "-" + spacing + "s%5s EUR\n", indent, transaction_row[1], transaction_row[2]);

        // receipt for cash payment
        if (payment_type == 1) {
            System.out.printf("\n%s%" + "-" + spacing + "s%5s EUR\n", indent, "Payment:", transaction_row[3]);
            System.out.printf("%s%" + "-" + spacing + "s%5s EUR\n", indent, "Change:", transaction_row[4]);

            // receipt for card payment
        } else if (payment_type == 2) {
            // the spacing + 5 accounts for the difference in the spacing with the %5s in the item price
            System.out.printf("\n%s%" + "-" + (spacing + 5) + "s%s\n", indent, "Card type:", transaction_row[3]);
        }

        System.out.println(indent + this.item_separator);

        in.enterToContinue();

        this.clearScreen();
    }

    public static boolean continueMenu() {
        // return true if exit is false
        return !exit;
    }
}

// main class
public class CafeSystem {

    // main method
    public static void main(String[] args) {

        // object instances
        Menu menu = new Menu("program_files/inventory.csv", "program_files/transactions.csv", 25);

        // program loop while user doesn't exit
        do {

            // clears the screen
            menu.clearScreen();

            // displays menu
            menu.displayMenu();

            // gets users system menu choice and processes it
            menu.menuChoice();

        // repeat while exit is not true
        } while (Menu.continueMenu());

    // close gracefully
    System.out.println("Program Closed.");
    }
}
// lets goooooooooooooooooooooooooooooooooooooooooooooooooo