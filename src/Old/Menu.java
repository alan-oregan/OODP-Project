package Old;
import java.util.ArrayList;

/**
 * Old.Menu class handles anything related to the menu in the coffee system.
 * displays the menu in the appropriate format,
 * processes the users menu choice,
 * holds the transaction data.
 */
class Menu {

    // objects
    private final FileHandler fh;
    private final Input in;
    private final Transaction tn;

    // variables
    public static boolean exit = false;
    private final ArrayList<MenuItem> menu_list;
    private String currency = "EUR"; // Default is EUR

    // change switch statement in menuChoice if changing system_options strings
    private final String[] system_options = { "Remove Order Item", "Complete src.Old.Transaction", "Exit Program" };
    private final String transactions_file_path;
    private boolean header = false; // default false
    private boolean append_mode = true; // default true

    // separating output for better readability
    private int spacing = 30; // default spacing is 30
    private int indent_spacing = spacing / 5;
    private String indent = " ".repeat(indent_spacing);
    private String system_separator = "=".repeat(spacing + indent_spacing*2 + 13);
    private String item_separator = indent + "-".repeat(spacing + 13);


    // constructor
    public Menu(String inventory_file_path, boolean header, String currency, String transactions_file_path,
            boolean append_mode, int spacing) {
        // setting objects
        fh = new FileHandler();
        in = new Input(currency);

        // variables
        this.transactions_file_path = transactions_file_path;
        this.currency = currency;
        this.header = header;
        this.append_mode = append_mode;

        // read inventory file for menu item list
        menu_list = fh.readInventoryCSV(inventory_file_path, header);

        // setting spacing and separator lengths
        this.spacing = spacing;
        indent_spacing = spacing / 5;
        indent = " ".repeat(indent_spacing);
        system_separator = "=".repeat(spacing + 13 + indent_spacing * 2);
        item_separator = indent + "-".repeat(spacing + 13);

        // Declaring Transactions object with menu list and other parameters
        tn = new Transaction(menu_list, currency, transactions_file_path, append_mode);
    }


    // overloaded constructor to just use defaults
    public Menu(String inventory_file_path, String transactions_file_path) {
        // setting objects
        fh = new FileHandler();
        in = new Input(currency);

        // variables
        this.transactions_file_path = transactions_file_path;

        // read inventory file for menu item list
        menu_list = fh.readInventoryCSV(inventory_file_path, header);

        // Declaring Transactions object with menu list and other parameters
        tn = new Transaction(menu_list, currency, transactions_file_path, append_mode);
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
            System.out.print("\n" + backup_string);
        }
    }


    // prints the given heading to the middle relative to the spacing
    public void printHeader(String heading) {
        int padding = indent_spacing + (spacing + 13) / 2 + heading.length() / 2;
        System.out.printf("\n%" + padding + "s\n", heading); // adds padding using the format specifier
    }


    // prints the menu items and system options in an appropriate format
    public void displayMenu() {

        // clears the screen with system separator as a backup
        Menu.clearScreen(system_separator);

        int item_ID = 1; // menu items id number start at 1

        // menu title with spacing
        printHeader("System Menu");

        System.out.println(system_separator);

        printHeader("Cafe Items");

        System.out.println(item_separator);

        // prints the menu items
        for (MenuItem item : menu_list) {
            System.out.printf("%s%2d. %s%s%5.2f %s\n", indent, item_ID++, item.getItemName(),
                    ".".repeat(spacing - item.getItemName().length()), item.getItemPrice(), currency);
        }

        // Separates the system options
        System.out.println(item_separator);

        // prints the system options
        for (String option : system_options) {
            System.out.printf("%s%2d. %s\n", indent, item_ID++, option);
        }

        // display current order items if they exist
        if (tn.getItems().size() > 0) {

            // separate it from the System menu
            System.out.println("\n" + system_separator);

            printHeader("Current Order");

            System.out.println(item_separator);

            double sub_total = 0; // for calculating a subtotal
            item_ID = 1; // reset item id to 1

            // print out each item in the transaction items and calculate subtotal
            for (MenuItem item : tn.getItems()) {
                System.out.printf("%s%2d. %s%s%5.2f %s\n", indent, item_ID++, item.getItemName(),
                        ".".repeat(spacing - item.getItemName().length()), item.getItemPrice(), currency);

                sub_total += item.getItemPrice();
            }

            System.out.println(item_separator);

            // + 3 to account for the difference with '%2d. '
            System.out.printf("%s%" + "-" + (spacing + 3) + "s %5.2f %s\n", indent, "Sub-Total:", sub_total, currency);
        }

        // separates from the rest of the output
        System.out.println("\n" + system_separator);
    }


    // Gets the users' menu choice
    public void menuChoice() {

        // default min input is 1 the number of menu items + number of system options is the max input
        int menu_choice = in.getMenuChoice(menu_list.size() + system_options.length) - 1;

        // users menu choice is valid within the menu_list
        if (menu_choice < menu_list.size() && menu_choice != -2) {
            tn.addItem(menu_choice);

        // users menu choice is valid then it is in the system_options
        } else if (menu_choice != -2) {

            // switch with the option chosen as a string from the array as a parameter
            switch (system_options[menu_choice - (menu_list.size())]) {

                case "Remove Order Item":

                    // if there is a transaction item to remove
                    if (tn.getItems().size() > 0) {
                        int item_index = in.getRemoveItemChoice(tn.getItems().size());

                        if (item_index != -1) {
                            tn.removeItem(item_index);
                        }

                    } else {
                        System.out.println("\nError - Please order an item first by entering the item number associated.");
                        in.enterToContinue();
                    }
                    break;

                case "Complete src.Old.Transaction":

                    // if there is a transaction to pay for
                    if (tn.getItems().size() > 0) {

                        // gets the transaction information
                        // if the transaction is successful then print the receipt
                        if (tn.completePayment()) {
                            displayReceipt();
                        }

                    } else {
                        System.out.println("\nError - Please order an item first by entering the item number associated.");
                        in.enterToContinue();
                    }
                    break;

                case "Exit Program":

                    // if there isn't a pending current transaction
                    if (tn.getItems().size() == 0) {
                        tn.saveTransactions();
                        System.out.printf("\nTransactions Saved to: %s\n", transactions_file_path);
                        exit = true; // set exit to true for do while loop to end

                    } else {
                        System.out.println("\nError - Please complete payment or clear order first.");
                        in.enterToContinue();
                    }
                    break;

                // if switch and system_options have different strings
                default:
                    System.out.println("\nError - Option not specified in menuChoice Switch");
                    in.enterToContinue();
            }
        }
    }


    // prints receipt in an appropriate format
    public void displayReceipt() {

        // gets the most recent transaction information
        TransactionItem transaction = tn.getLatestTransaction();

        printHeader("Receipt");

        System.out.println(item_separator);

        System.out.printf("%sTime: %" + (spacing + 7) + "s\n", indent, transaction.getTimestamp());

        System.out.printf("\n%sItem/s Purchased\n\n", indent);

        for (MenuItem item : transaction.getItemsPurchased()) {
            System.out.printf("%s%" + "-" + (spacing + 4) + "s%5.2f %s\n", indent, item.getItemName(),
                    item.getItemPrice(), currency);
        }

        // receipt for cash payment
        if (transaction instanceof CashTransaction) {
            System.out.printf("\n%s%" + "-" + (spacing + 4) + "s%5.2f %s\n", indent, "Payment:",
                    transaction.getItemsPrice(), currency);
            System.out.printf("%s%" + "-" + (spacing + 4) + "s%5.2f %s\n", indent, "Change:",
                    ((CashTransaction) transaction).getChangeTendered(), currency);

            // receipt for card payment
        } else if (transaction instanceof CardTransaction) {
            System.out.printf("\n%s%" + "-" + (spacing + 4) + "s%5.2f %s\n", indent, "Payment:",
                    transaction.getItemsPrice(), currency);
            System.out.printf("%s%" + "-" + (spacing + 3) + "s%10s\n", indent, "Card type:",
                    ((CardTransaction) transaction).getCardType());
        }

        System.out.println(item_separator);

        in.enterToContinue();
    }


    // for continuing the loop
    // return true if exit is false
    public static boolean continueMenu() {
        return !exit;
    }
}