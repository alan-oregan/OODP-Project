// imports
import java.util.ArrayList;

/**
 * Menu class handles anything related to the menu in the coffee system.
 * displays the menu in the appropriate format,
 * processes the users menu choice,
 * holds the transaction data.
 */
class Menu {

    // objects
    private FileHandler fh;
    private Input in;
    private Transaction tn;

    // variables
    private ArrayList<MenuItem> menu_list;
    private String[] system_options = { "Remove Order Item", "Complete Transaction", "Exit Program" };
    private String transactions_file_path;
    private int menu_choice;

    // separating output for better readability
    private int spacing = 25; // default spacing is 25
    private int indent_spacing = spacing / 5;
    private String indent = " ".repeat(indent_spacing);
    private String system_separator = "=".repeat(spacing + indent_spacing*2 + 13);
    private String item_separator = "-".repeat(spacing + 13);

    // if the program should exit, do while loop checks this public variable
    public static boolean exit = false;

    // constructor with spacing default
    public Menu(String inventory_file_path, String transactions_file_path) {
        // object declarations
        fh = new FileHandler();
        in = new Input();

        //variables
        this.transactions_file_path = transactions_file_path;
        menu_list = fh.readInventoryCSV(inventory_file_path, true);

        // Declaring Transactions object with menu list from the variables
        tn = new Transaction(menu_list, transactions_file_path);
    }

    // constructor with spacing specified
    public Menu(String inventory_file_path, String transactions_file_path, int spacing) {
        // object declarations
        fh = new FileHandler();
        in = new Input();

        //variables
        this.transactions_file_path = transactions_file_path;
        menu_list = fh.readInventoryCSV(inventory_file_path, true);

        // setting separator lengths
        this.spacing = spacing; // changes the menu spacing according to the user preferences
        indent_spacing = spacing / 5;
        indent = " ".repeat(indent_spacing);
        system_separator = "=".repeat(spacing + indent_spacing*2 + 13);
        item_separator = "-".repeat(spacing + 13);

        tn = new Transaction(menu_list, transactions_file_path);
    }

    // static method to clear the screen
    public static void clearScreen(String backup_string) {
        // tries to get the system name and if it is windows creates a process that clears the screen in cmd with cls
        // if its not windows it uses ansi escape codes
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                // uses the codes 2J to clear the console and H to return to the top of the console
                System.out.print("\033[2J\033[H");
            }
        } catch (java.io.IOException | java.lang.InterruptedException ex) {
            // if there is an error print out a given backup string to break up the output
            System.out.print(backup_string);
        }
    }

    // prints the given heading to the middle relative to the spacing
    public void printHeader(String heading) {
        int padding = (indent_spacing*2 + spacing)/2 + (heading.length());
        System.out.printf("\n%"+ padding +"s\n", heading); // adds padding using the format specifier
    }

    // prints the values in an appropriate format
    public void displayMenu() {

        // clears the screen
        Menu.clearScreen(system_separator);

        int item_ID = 1;

        //menu title with spacing
        printHeader("System Menu");

        System.out.println(system_separator);

        printHeader("Cafe Items");

        System.out.println(indent + item_separator);

        // loops through the values in the 2D ArrayList
        for (MenuItem item : menu_list) {
            System.out.printf("%s%2d. %s%s%5.2f EUR\n", indent, item_ID++, item.getItemName(),
                    ".".repeat(spacing - item.getItemName().length()), item.getItemPrice());
        }

        // Separates the system options
        System.out.println(indent + item_separator);

        // prints the system options
        for (String option : system_options) {
            System.out.printf("%s%2d. %s\n", indent, item_ID++, option);
        }

        // display current order items if they exist
        if (tn.getItems().size() > 0) {

            // separate it from the System menu
            System.out.println("\n" + system_separator);

            printHeader("Current Order");

            System.out.println(indent + item_separator);

            double sub_total = 0; // for calculating a subtotal
            item_ID = 1; // reset item id

            // print out each item in the transaction items and calculate sub total
            for (MenuItem item : tn.getItems()) {
                System.out.printf("%s%2d. %s%s%5.2f EUR\n", indent, item_ID++, item.getItemName(),
                        ".".repeat(spacing - item.getItemName().length()), item.getItemPrice());

                sub_total += item.getItemPrice();
            }

            System.out.println(indent + item_separator);

            // + 3 to account for the difference with '%2d. '
            System.out.printf("%s%" + "-" + (spacing + 3) + "s %5.2f EUR\n", indent, "Sub-Total:", sub_total);
        }

        System.out.println("\n" + system_separator);
    }

    public void menuChoice() {

        // the number of menu items + number of system options
        menu_choice = in.getMenuChoice(menu_list.size() + system_options.length);

        if (menu_choice - (menu_list.size()-1) < 0) {
            tn.addItem(menu_choice);

        } else {

            switch (system_options[menu_choice - (menu_list.size())]) {
                case "Remove Order Item":

                    // if there is a transaction item to remove
                    if (tn.getItems().size() > 0) {
                        int item_index = in.getRemoveItemChoice(tn.getItems().size());

                        if (item_index != -1) {
                            tn.removeItem(item_index);
                        }

                    } else {
                        System.out.println("\nError - Please enter an item first.");
                        in.enterToContinue();
                    }
                    break;

                case "Complete Transaction":

                    // if there is a transaction to pay for
                    if (tn.getItems().size() > 0) {

                        // gets the transaction information
                        // if the transaction is successful then print the receipt
                        if (tn.completePayment()) {
                            displayReceipt();
                        }

                    } else {
                        System.out.println("\nError - Please enter an item first.");
                        in.enterToContinue();
                    }
                    break;

                case "Exit Program":

                    // if there is no current transaction
                    if (tn.getItems().size() == 0) {
                        tn.saveTransactions();
                        System.out.printf("\nTransactions Saved to: %s\n", transactions_file_path);
                        exit = true; // set exit to true for do while to exit program

                    } else {
                        System.out.println("\nError - Please complete payment or clear order first.");
                        in.enterToContinue();
                    }
                    break;
            }
        }
    }

    // prints receipt in an appropriate format
    public void displayReceipt() {

        // gets the most recent transaction information
        TransactionItem transaction = tn.getLatestTransaction();

        printHeader("Receipt");

        System.out.println(indent + item_separator);

        System.out.printf("%sTime: %" + (spacing + 7) + "s\n", indent, transaction.getTimestamp());
        System.out.printf("\n%sItem/s Purchased\n", indent);

        for (MenuItem item : transaction.getItemsPurchased()) {
            System.out.printf("%s%" + "-" + (spacing + 4) + "s%5.2f EUR\n", indent, item.getItemName(), item.getItemPrice());
        }

        // receipt for cash payment
        if (transaction instanceof CashTransaction) {
            System.out.printf("\n%s%" + "-" + (spacing + 4) + "s%5.2f EUR\n", indent, "Payment:", transaction.getItemsPrice());
            System.out.printf("%s%" + "-" + (spacing + 4) + "s%5.2f EUR\n", indent, "Change:", ((CashTransaction) transaction).getChangeTendered());

        // receipt for card payment
        } else if (transaction instanceof CardTransaction) {
            System.out.printf("\n%s%" + "-" + (spacing + 4) + "s%5.2f EUR\n", indent, "Payment:", transaction.getItemsPrice());
            System.out.printf("%s%" + "-" + (spacing + 3) + "s%10s\n", indent, "Card type:", ((CardTransaction) transaction).getCardType());
        }

        System.out.println(indent + item_separator);

        in.enterToContinue();
    }

    public static boolean continueMenu() {
        // return true if exit is false
        return !exit;
    }
}