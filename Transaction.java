// imports
import java.util.Date;

/*
* Handles the processing of Transactions
* Holds the transaction data
*/
class Transactions {

    // variables
    private String transactions_file_path;

    // objects
    Input in;

    // constructor
    public Transactions(String transactions_file_path) {
        // setting objects
        in = new Input();

        this.transactions_file_path = transactions_file_path;
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
            System.out.printf("\n%s%" + "-" + (spacing - 1) + "s%10s\n", indent, "Card type:", transaction_row[3]);
        }

        System.out.println(indent + this.item_separator);

        in.enterToContinue();

        this.clearScreen();
    }
}