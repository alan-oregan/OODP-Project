// imports
import java.util.ArrayList;
import java.util.Date; // for getting current timestamp

/**
 * Menu class handles anything related to the menu in the coffee system.
 * displays the menu in the appropriate format,
 * processes the users menu choice,
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
    private int spacing = 25; // default spacing is 25
    private String indent = "    "; // 4 spaces for indent to keep consistency between terminals replaces \t

    // separating output for better readability
    private String system_separator = "=".repeat(this.spacing + 20);
    private String item_separator = "-".repeat(this.spacing + 12);

    // if the program should exit, do while loop checks this public variable
    public static boolean exit = false;

    // constructor
    public Menu(String inventory_path, String transaction_path) {

        // object declarations
        inventory_fh = new FileHandler(inventory_path);
        transactions_fh = new FileHandler(transaction_path);
        in = new Input();

        //variables
        this.menu_list = inventory_fh.readCSV();
    }

    // constructor with spacing
    public Menu(String inventory_path, String transaction_path, int spacing) {

        // object declarations
        inventory_fh = new FileHandler(inventory_path);
        transactions_fh = new FileHandler(transaction_path);
        in = new Input();

        //variables
        this.menu_list = inventory_fh.readCSV();
        this.spacing = spacing; // changes the menu spacing according to the user preferences

        // setting sepeator lengths
        system_separator = "=".repeat(this.spacing + 20);
        item_separator = "-".repeat(this.spacing + 12);
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

    public static boolean continueMenu() {
        // return true if exit is false
        return !exit;
    }
}
