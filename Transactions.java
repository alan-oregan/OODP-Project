// imports
import java.util.Date; // for getting current timestamp
import java.util.ArrayList; // for sorting transaction data

/*
* Handles the processing of Transactions
* Holds the transaction data
*/
class transactions {

    // variables
    private ArrayList<MenuItem> menu_list;
    private ArrayList<MenuItem> transaction_items = new ArrayList<MenuItem>();
    private ArrayList<TransactionItem> transactions = new ArrayList<TransactionItem>();
    private String transactions_file_path;

    // objects
    Input in;
    Menu menu;
    FileHandler fh;

    // constructor
    public transactions(ArrayList<MenuItem> menu_list, String transactions_file_path) {
        // setting objects
        in = new Input();
        fh = new FileHandler();

        // variables
        this.menu_list = menu_list;
        this.transactions_file_path = transactions_file_path;
    }

    // adds a transaction item to the ArrayList then returns the ArrayList
    public ArrayList<MenuItem> addTransactionItem(int menu_choice) {
        transaction_items.add(menu_list.get(menu_choice));
        return transaction_items;
    }

    // removes a transaction item from the ArrayList then returns the ArrayList
    public ArrayList<MenuItem> removeTransactionItem(int item_index) {
        transaction_items.remove(item_index);
        return transaction_items;
    }

    // gets the total price from the menu sub_total
    // and gets the rest of the transaction information to complete the transaction
    // and adds it to the transactions ArrayList
    public TransactionItem completePayment() {

        // gets the current time and date using the Date constructor
        Date time_stamp = new Date();

        // looping through the transaction items
        // and getting the item name and price from each MenuItem
        // using that information to aad them to a list of items_purchased and total items Price
        int items_price = 0;
        for (MenuItem item : transaction_items) {
            items_price += item.getItemPrice();
        }

        // gets the payment option
        int payment_option = in.limitOptionChoice("Cash/Card?", new int[] { 1, 2 });

        // amount tendered / card type

        // cash is 1
        if (payment_option == 1) {

            // amount tendered
            double tendered_amount = in.getTenderedAmount("Cash", items_price, 0);

            // set change as the exact change
            double change_tendered = tendered_amount - items_price;

            // let user input change
            // change_tendered = in.getTenderedAmount("Change", 0.01, (change_tendered));

            transactions.add(new cashTransaction(time_stamp, transaction_items, items_price, payment_option, tendered_amount, change_tendered));
        }

        // card is 2
        else if (payment_option == 2) {

            // get card type
            int card_type = in.limitOptionChoice("Visa/Mastercard?", new int[] { 1, 2 });

            String card_type_string = "";
            switch (card_type) {
                case 1: card_type_string = "Visa"; break;
                case 2: card_type_string = "Mastercard"; break;
            }

            transactions.add(new cardTransaction(time_stamp, transaction_items, items_price, payment_option, card_type_string));

        }

        return transactions.get(transactions.size()-1); // returns the most recent transaction
    }

    public void saveTransactions() {
        fh.writeToTransactionsCSV(transactions_file_path, transactions);
    }
}