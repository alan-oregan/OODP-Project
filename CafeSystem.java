import java.util.Scanner; // for the inputs and file reading
import java.util.ArrayList; // for the arrays
import java.lang.Double; // for converting string to double
import java.io.File; // Import the File class
import java.io.FileWriter; // for reading files
import java.io.IOException; // Import the IOException class to handle file write errors
import java.io.FileNotFoundException; // Import this class to handle file read errors
import java.sql.Timestamp; // for getting current timestamp

// Notes -----------------------------------------------------------------------------------------------------------
// https://www.youtube.com/watch?v=yO_ctH4mEk4
// https://stackoverflow.com/questions/16956720/how-to-create-an-2d-arraylist-in-java/16956747
// https://www.w3schools.com/java/java_arraylist.asp
// https://www.youtube.com/watch?v=lcWV7hLYByk
// https://stackoverflow.com/questions/2979383/java-clear-the-console


/**
 * FileHandler class handles reading to and from the files for transactions and inventory
 */
class FileHandler {
    // variables
    private String file_path;
    private ArrayList<String[]> dataTable = new ArrayList<String[]>();

    // constructor
    public FileHandler(String file_path) {
        this.file_path = file_path;
    }

    // https://stackoverflow.com/questions/16956720/how-to-create-an-2d-arraylist-in-java/16956747
    // https://www.w3schools.com/java/java_arraylist.asp
    // https://www.tutorialspoint.com/convert-from-string-to-double-in-java#:~:text=To%20convert%20String%20to%20double%2C%20use%20the%20valueOf()%20method.
    // https://www.geeksforgeeks.org/format-specifiers-in-java/

    // reads the csv file and returns it as a 2d arrayList
    public ArrayList<String[]> readCSV() {
        try {
            File fileObject = new File(file_path);
            Scanner myReader = new Scanner(fileObject);

            myReader.nextLine(); // skip first line (header)

            // loops through the item rows in the file while they exist
            while (myReader.hasNextLine()) {

                // getting the row as an array split by ,
                String[] item_row = myReader.nextLine().split(",");

                dataTable.add(item_row);
            }

            myReader.close(); // closes the Scanner

        } catch (FileNotFoundException e) {
            System.out.printf("Error - file %s not found.\n", file_path);
            e.printStackTrace();
        }

        return dataTable;
    }

    public void writeCSV(ArrayList<String[]> dataTable) {

        try {

            FileWriter wtr = new FileWriter(file_path, true); // append mode set to true

            // gets the system line separator for new lines
            String newLine = System.getProperty("line.separator");

            // wtr.write(key + newLine); // adds Key for csv on one line and moves cursor to the next

            // loops through the rows in the 2d array
            for (String[] row : dataTable) {

                // writes the items with , separator to the line for csv format
                for (String item : row)
                    wtr.write(item + ", " );

                // moves the cursor for the next line
                wtr.write(newLine);
            }

            wtr.close(); // closes the FileWriter

        } catch(IOException e) {
            System.out.printf("Error - %s\n", e);

        }
    }
}


// /**
//  * Transaction class handles the transactions and their types]
//  * holds the transaction data
//  */
// class Transaction extends FileHandler {

//     private ArrayList<String[]> transaction_data = new ArrayList<String[]>();
//     private String file_path;

//     // constructor
//     public Transaction(String file_path) {
//         super(file_path);
//         this.file_path = file_path;
//     }
// }


/**
 * Input class handles user input
 * validates user input and returns the correct input type
 */
class Input {

    // variables
    private boolean exit = false;


    // class objects
    Scanner input = new Scanner(System.in);

    // constructor
    public Input() {

    }

    public boolean continueLoop() {
        return exit;
    }

    // Input validation
    public double validateDoubleInput(String user_string_Input,
            double min_limit,
            double max_limit,
            boolean nomin_limit,
            boolean nomax_limit,
            double invalid_input) {

        double double_user_input = 0;

        // if the input isn't nothing
        if (user_string_Input.length() >= 1) {

            // trying to convert input to integer
            try {
                double_user_input = Double.parseDouble(user_string_Input);
            }

            // if conversion fails return as invalid input
            catch (NumberFormatException e) {
                return invalid_input;
            }
        // if the input is nothing
        } else {
            return invalid_input;
        }

        // return the integer input if:

        // there is no minimum and no maximum limit,
        // there is no the minimum and the value is less than the maximum limit,
        // the value is more than the minimum and there is no the maximum limit,
        // the value is more than the minimum and less than the maximum limit,

        if ((nomin_limit && nomax_limit) || (nomin_limit && double_user_input <= max_limit) || (double_user_input >= min_limit && nomax_limit) || (double_user_input >= min_limit && double_user_input <= max_limit)) {
            return double_user_input;

        } else {
            return invalid_input;
        }
    }

    public int twoOptionChoice(String prompt) {
        int option = 0;

        // gets the payment option
        do {
            System.out.printf("%s [1/2]: ", prompt);
            option = (int) this.validateDoubleInput(this.input.nextLine(), 1, 2, false, false, 0);
            if (option == 0) {
                System.out.println("\nError - Invalid input please enter a valid option ie. Enter 1 or 2.");
            }
            // repeat while not valid
        } while (option == 0);

        return option;
    }

    public double getTenderedAmount() {

        double tendered_amount = 0.00;
        boolean invalid = true;

        // gets cash tendered
        do {

            // gets the amount tendered
            System.out.print("Enter amount tendered (EUR): ");
            tendered_amount = this.validateDoubleInput(this.input.nextLine(), 0.01, 0, false, true, 0);

            if (tendered_amount == 0 || tendered_amount.valueOf()) {
                invalid = true;
                System.out.println("\nError - Invalid input please enter a value above 0.00");
            }

        // repeat while invalid input
        } while (invalid);

        return tendered_amount;
    }

    public int getMenuChoice(int menuSize) {

        // variables
        int exit_num = menuSize + 1;
        int max_value = exit_num;

        // getting input
        System.out.printf("Enter your choice: ",exit_num);
        int user_choice = (int) this.validateDoubleInput(input.nextLine(), 1, max_value, false, false, -1) - 1;

        // processing the input
        if (user_choice == -2) {
            System.out.println("\nError - Invalid input please enter a valid option.");
            user_choice++;
        }

        // returning the user choice
        return user_choice;
    }
}


/**
 * Menu class handles anything related to the menu in the coffee system
 * displays the menu in the appropriate format
 * processes the users choice
 * holds the transaction data
 */
class Menu {

    //class objects
    FileHandler inventory_fh = new FileHandler("program_files/inventory.csv");
    FileHandler transactions_fh = new FileHandler("program_files/transactions.csv");
    Input in = new Input();

    // variables
    private ArrayList<String[]> menu_list = inventory_fh.readCSV();
    private ArrayList<String[]> transaction_data = new ArrayList<String[]>();
    private int menu_choice;
    private double total = 0;
    private int spacing = 25; // can be changed depending on desired spacing
    private String indent = "    "; // 4 spaces for indent to keep consistency
    private String system_separator = new String(new char[this.spacing + 20]).replace("\0", "=") + "\n";
    private String item_separator = new String(new char[spacing + 12]).replace("\0", "-");
    public boolean exit = false;

    // constructor
    public Menu(int spacing) {
        this.spacing = spacing;
    }

    // public void clearScreen() {
    //     System.out.print("\033[H\033[2J");
    //     // System.out.flush();
    // }

    // prints the values in an appropriate format
    public void displayMenu() {
        int item_ID = 1;

        //menu title with spacing
        System.out.printf("\n%s%s\n", new String(new char[spacing/2 + 5]).replace("\0", " "),"System Menu");

        System.out.println(system_separator);

        System.out.printf("%s%s\n", new String(new char[spacing/2 + 6]).replace("\0", " "),"Cafe Menu");

        System.out.println(indent + item_separator);

        // loops through the values in the 2D ArrayList
        for (String[] item : menu_list) {
            System.out.printf("%s%d. %s%s%5.2f EUR\n", indent, item_ID++, item[0], new String(new char[spacing - item[0].length()]).replace("\0", "."),   Float.parseFloat(item[1]));
            // System.out.printf("\n\t%d. %-15s  %.2f EUR\n", item_ID++, item[0], double.valueOf(item[1]).doubleValue());
        }

        System.out.println(indent + item_separator);

        System.out.printf("%s%d. Exit\n", indent, item_ID++);

        System.out.printf("%s%d. Calculate Total\n", indent, item_ID++);

        System.out.printf("%s%d. Clear Last Entry\n", indent, item_ID++);

        System.out.println("\n" + system_separator);
    }

    public void menuChoice() {
        menu_choice = in.getMenuChoice(menu_list.size()+1);

        // exit choice
        if (menu_choice == menu_list.size()) {
            exit = true;

        // calculate total choice
        } else if (menu_choice == menu_list.size()+1) {
            // writes the transaction to the transactions csv
            transactions_fh.writeCSV(transaction_data);

        // clear last entry
        } else if (menu_choice == menu_list.size()+2){

        // Cafe menu choice
        } else if (menu_choice != -1) {

            // an array that contains all the information to be written to the transactions.csv
            // Format: Date and time stamp, Item Purchased, Price, Amount tendered / Card type, Change given
            String[] transaction_row = new String[5];

            // add time stamp
            Timestamp timestamp = new Timestamp(System.currentTimeMillis()); // gets the current time in milliseconds and converts it to a timestamp
            transaction_row[0] = timestamp+"";

            // add item Purchased
            transaction_row[1] = menu_list.get(menu_choice)[0]; // gets the chosen item name

            // add Price (validate in future)
            double price = Float.parseFloat(menu_list.get(menu_choice)[1]); // gets item price and converts it to a double

            // add price to the total
            this.total += price;
            transaction_row[2] = price+"";

            // gets the payment option
            int payment_option = in.twoOptionChoice("Cash or Card?");

            // amount tendered / card type

            // cash is 1
            if (payment_option == 1) {

                // amount tendered
                double tendered_amount = in.getAmountTendered();
                transaction_row[3] = tendered_amount+"";

                // change given
                double change_given = 0.00;
                double change_given = in.getAmountTendered();
                transaction_row[4] = change+"";

            // card is 2
            } else if (payment_option == 2) {

                // get card type
                int card_type = in.twoOptionChoice("Visa or Mastercard?");

                if (card_type == 1) {
                    transaction_row[3] = "Visa";
                } else if (card_type == 2) {
                    transaction_row[3] = "Mastercard";
                }

                // change given (if card then no change given ie. "0.00")
                transaction_row[4] = "0.00";
            }

            this.transaction_data.add(transaction_row);

        }
    }
}


// main class
public class CafeSystem {

    // main method
    public static void main(String[] args) {

        // object instances
        Menu menu = new Menu(25);

        // program loop while user doesn't exit
        do {
            // display menu
            menu.displayMenu();

            menu.menuChoice();

        } while (!menu.exit);
    }
}
// lets goooooooooooooooooooooooooooooooooooooooooooooooooo