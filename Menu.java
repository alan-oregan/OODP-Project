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
    FileHandler fh;
    Input in;
    Transactions tn;

    // variables
    private ArrayList<MenuItem> menu_list;
    private String transactions_file_path;
    private ArrayList<MenuItem> current_transaction_items = new ArrayList<MenuItem>();
    private int menu_choice;
    private int spacing = 25; // default spacing is 25
    private String indent = " ".repeat(4); // 4 spaces for indent to keep consistency between terminals replaces \t

    // separating output for better readability
    private String system_separator = "=".repeat(this.spacing + 21);
    private String item_separator = "-".repeat(this.spacing + 13);

    // if the program should exit, do while loop checks this public variable
    public static boolean exit = false;

    // constructor with spacing default
    public Menu(String inventory_file_path, String transactions_file_path) {
        // object declarations
        fh = new FileHandler();
        in = new Input();

        //variables
        this.transactions_file_path = transactions_file_path;
        this.menu_list = fh.readInventoryCSV(inventory_file_path);

        // Declaring Transactions object with menu list from the variables
        tn = new Transactions(this, this.menu_list, transactions_file_path);
    }

    // constructor with spacing specified
    public Menu(String inventory_file_path, String transactions_file_path, int spacing) {
        // object declarations
        fh = new FileHandler();
        in = new Input();

        //variables
        this.transactions_file_path = transactions_file_path;
        this.menu_list = fh.readInventoryCSV(inventory_file_path);
        this.spacing = spacing; // changes the menu spacing according to the user preferences

        // setting separator lengths
        system_separator = "=".repeat(this.spacing + 21);
        item_separator = "-".repeat(this.spacing + 13);
        tn = new Transactions(this, menu_list, transactions_file_path);
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

        // clears the screen
        clearScreen();

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

        if (current_transaction_items.size() > 0) {
            System.out.printf("%s%s\n", " ".repeat(spacing / 2 + 4), "Current Order");
            System.out.println(indent + item_separator);

            double sub_total = 0; // for calculating a subtotal
            item_ID = 1; // reset item id
            // print out each item in the transaction items and calculate sub total
            for (MenuItem item : current_transaction_items) {
                System.out.printf("%s%2d. %s%s%5.2f EUR\n", indent, item_ID++, item.getItemName(), ".".repeat(spacing - item.getItemName().length()), item.getItemPrice());
                sub_total += item.getItemPrice();
            }
            System.out.println(indent + item_separator);
            // + 4 to account for the difference with '%2d. '
            System.out.printf("%s%" + "-" + (spacing + 4) + "s %5.2f EUR\n", indent, "Sub-Total:", sub_total);
        }

        System.out.println("\n" + system_separator);
    }

    public void menuChoice() {

        // the number of menu items + number of system options
        menu_choice = in.getMenuChoice(menu_list.size() + 3);

        // if choice is remove item
        if (menu_choice == menu_list.size()) {
            // if there is a transaction item to remove
            if (current_transaction_items.size() > 0) {
                int item_index = in.getRemoveItemChoice(current_transaction_items.size());
                current_transaction_items = tn.removeTransactionItem(item_index);
            }
        }
        // if choice is payment
        else if (menu_choice == menu_list.size() + 1) {
            // if there is a transaction to pay for
            if (current_transaction_items.size() > 0) {
                // gets the transaction information
                // adds the transaction to the transactions ArrayList
                // and then prints the receipt
                tn.completePayment();
                // since payment complete
                current_transaction_items.clear();
            }
        // if choice is exit
        } else if (menu_choice == menu_list.size() + 2) {
            tn.saveTransactions();
            System.out.printf("\nTransactions Saved to: %s\n", transactions_file_path);
            in.input.close(); // close the input scanner
            exit = true; // set exit to true for do while to exit program

        // Cafe menu choice if not invalid
        } else if (menu_choice != -1) {
            current_transaction_items = tn.addTransactionItem(menu_choice);
        }
    }

    public void displayReceipt(Transaction transaction) {
        System.out.printf("\n%s%s%s\n", indent, " ".repeat(this.item_separator.length() / 2 - 4), "Receipt");

        System.out.println(indent + this.item_separator);

        // prints receipt in an appropriate format
        // transaction_row format: Date and time stamp, Item Purchased, Price, Amount tendered / Card type, Change given

        System.out.printf("%sTime: %s\n", indent, transaction.getTimestamp());
        System.out.printf("\n%sItem/s Purchased\n", indent);

        for (MenuItem item : transaction.getItemsPurchased()) {
            System.out.printf("%s%" + "-" + spacing + "s%5s EUR\n", indent, item.getItemName(), item.getItemPrice());
        }

        // receipt for cash payment
        if (transaction.getTransactionType() == 1) {
            System.out.printf("\n%s%" + "-" + spacing + "s%5s EUR\n", indent, "Payment:", transaction.getItemsPrice());
            System.out.printf("%s%" + "-" + spacing + "s%5s EUR\n", indent, "Change:", transaction.getChangeTendered());

        // receipt for card payment
        } else if (transaction.getTransactionType() == 2) {
            System.out.printf("\n%s%" + "-" + (spacing - 1) + "s%10s\n", indent, "Card type:", transaction.getCardType());
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