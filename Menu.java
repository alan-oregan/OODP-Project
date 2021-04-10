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
    private transactions tn;

    // variables
    private ArrayList<MenuItem> menu_list;
    private String transactions_file_path;
    private int menu_choice;

    // separating output for better readability
    private int spacing = 25; // default spacing is 25
    private String indent = " ".repeat(spacing/5);
    private String system_separator = "=".repeat(spacing + (spacing / 5) * 4);
    private String item_separator = "-".repeat(spacing + (spacing / 5) * 2);

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
        tn = new transactions(menu_list, transactions_file_path);
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
        indent = " ".repeat(spacing/5);
        system_separator = "=".repeat(spacing + (spacing / 5) * 4);
        item_separator = "-".repeat(spacing + (spacing / 5) * 2);

        tn = new transactions(menu_list, transactions_file_path);
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

    // prints the values in an appropriate format
    public void displayMenu() {

        // clears the screen
        Menu.clearScreen(system_separator);

        int item_ID = 1;

        //menu title with spacing
        System.out.printf("\n%s%s\n", " ".repeat(spacing/2 + 5), "System Menu");

        System.out.println(system_separator + "\n");

        System.out.printf("%s%s\n", " ".repeat(spacing/2 + 6), "Cafe Items");

        System.out.println(indent + item_separator);

        // loops through the values in the 2D ArrayList
        for (MenuItem item : menu_list) {
            System.out.printf("%s%2d. %s%s%5.2f EUR\n",
            indent, item_ID++, item.getItemName(), ".".repeat(spacing - item.getItemName().length()), item.getItemPrice());
        }

        System.out.println(indent + item_separator);

        System.out.printf("%s%2d. Remove Item\n", indent, item_ID++);

        System.out.printf("%s%2d. Payment\n", indent, item_ID++);

        System.out.printf("%s%2d. Exit\n", indent, item_ID++);

        if (tn.getItems().size() > 0) {
            System.out.printf("%s%s\n", " ".repeat(spacing / 2 + 4), "Current Order");
            System.out.println(indent + item_separator);

            double sub_total = 0; // for calculating a subtotal
            item_ID = 1; // reset item id
            // print out each item in the transaction items and calculate sub total
            for (MenuItem item : tn.getItems()) {
                System.out.printf("%s%2d. %s%s%5.2f EUR\n", indent, item_ID++, item.getItemName(), ".".repeat(spacing - item.getItemName().length()), item.getItemPrice());
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
        menu_choice = in.getMenuChoice(menu_list.size() + 3);

        // if choice is remove item
        if (menu_choice == menu_list.size()) {
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
        }

        // if choice is payment
        else if (menu_choice == menu_list.size() + 1) {
            // if there is a transaction to pay for
            if (tn.getItems().size() > 0) {
                // gets the transaction information
                // adds the transaction to the transactions ArrayList
                tn.completePayment();
                displayReceipt();
                // since payment complete
                tn.getItems().clear();
            } else {
                System.out.println("\nError - Please enter an item first.");
                in.enterToContinue();
            }

        // if choice is exit
        } else if (menu_choice == menu_list.size() + 2) {
            // if there is no current transaction
            if (tn.getItems().size() == 0) {
                tn.saveTransactions();
                System.out.printf("\nTransactions Saved to: %s\n", transactions_file_path);
                exit = true; // set exit to true for do while to exit program
            } else {
                System.out.println("\nError - Please complete payment or clear order first.");
                in.enterToContinue();
            }

        // Cafe menu choice if not invalid
        } else if (menu_choice != -1) {
            tn.addItem(menu_choice);
        }
    }

    public void displayReceipt() {
        TransactionItem transaction = tn.getRecentTransaction();

        System.out.printf("\n%s%s\n", " ".repeat(spacing / 2 + 7), "Receipt");

        System.out.println(indent + item_separator);

        // prints receipt in an appropriate format
        // transaction_row format: Date and time stamp, Item Purchased, Price, Amount tendered / Card type, Change given

        System.out.printf("%sTime: %" + (spacing + 7) + "s\n", indent, transaction.getTimestamp());
        System.out.printf("\n%sItem/s Purchased\n", indent);

        for (MenuItem item : transaction.getItemsPurchased()) {
            System.out.printf("%s%" + "-" + (spacing + 4) + "s%5.2f EUR\n", indent, item.getItemName(), item.getItemPrice());
        }

        // receipt for cash payment
        if (transaction instanceof cashTransaction) {
            System.out.printf("\n%s%" + "-" + (spacing + 4) + "s%5.2f EUR\n", indent, "Payment:", transaction.getItemsPrice());
            System.out.printf("%s%" + "-" + (spacing + 4) + "s%5.2f EUR\n", indent, "Change:", ((cashTransaction) transaction).getChangeTendered());

        // receipt for card payment
        } else if (transaction instanceof cardTransaction) {
            System.out.printf("\n%s%" + "-" + (spacing + 4) + "s%5.2f EUR\n", indent, "Payment:", transaction.getItemsPrice());
            System.out.printf("%s%" + "-" + (spacing + 3) + "s%10s\n", indent, "Card type:", ((cardTransaction) transaction).getCardType());
        }

        System.out.println(indent + item_separator);

        in.enterToContinue();
    }

    public static boolean continueMenu() {
        // return true if exit is false
        return !exit;
    }
}