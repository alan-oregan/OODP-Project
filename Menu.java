import java.util.ArrayList;
import java.util.Date; // for getting current timestamp

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
