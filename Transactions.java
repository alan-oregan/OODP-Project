// imports
import java.util.Date;
import java.util.ArrayList;

/*
* Handles the processing of Transactions
* Holds the transaction data
*/
class Transactions {

    // variables
    private String transactions_file_path;
    private ArrayList<MenuItem> menu_list;
    private ArrayList<String[]> transaction_data = new ArrayList<String[]>();

    // objects
    Input in;

    // constructor
    public Transactions(ArrayList<MenuItem> menu_list, String transactions_file_path) {
        // setting objects
        in = new Input();

        // variables
        this.menu_list = menu_list;
        this.transactions_file_path = transactions_file_path;
    }


    public void addTransaction(Menu menu, int menu_choice) {

        // an array that contains all the information to be written to the transactions.csv
        // Format: Date and time stamp, Item Purchased, Price, Amount tendered / Card type, Change given
        String[] transaction_row = new String[5];
        MenuItem menu_choice_item = menu_list.get(menu_choice);

        // add time stamp
        Date time_stamp = new Date(); // gets the current time and date
        transaction_row[0] = time_stamp.toString(); //adds it as a string to the row array

        // add item Purchased
        transaction_row[1] = menu_choice_item.itemName(); // gets the chosen item name from the menu ArrayList

        // add Price (validate in future)
        double price = menu_choice_item.itemPrice(); // gets item price and converts it to a double
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
        menu.displayReceipt(transaction_row, payment_option);

        // if the ArrayList is not empty, add the String[] row to the ArrayList, then clear it
        if (transaction_row.length > 0) {
            this.transaction_data.add(transaction_row);
            transaction_row = new String[transaction_row.length];
            // since one item at a time
            this.transaction_data.clear();
        }
    }
}