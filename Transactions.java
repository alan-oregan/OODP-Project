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
    private ArrayList<TransactionItem> transaction_list = new ArrayList<TransactionItem>();
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

    // adds an item from the menu ArrayList at the given index to the transaction ArrayList then returns the updated transaction ArrayList
    public ArrayList<MenuItem> addTransactionItem(int menu_item_index) {
        transaction_items.add(menu_list.get(menu_item_index));
        return transaction_items;
    }

    // removes the transaction item at the given index from the ArrayList then returns the updated transaction ArrayList
    public ArrayList<MenuItem> removeTransactionItem(int transaction_item_index) {
        transaction_items.remove(transaction_item_index);
        return transaction_items;
    }

    // gets the total price from the menu sub_total
    // gets the rest of the transaction information to complete the transaction
    // then adds it to the transactions ArrayList
    // returns the completed transaction information
    public TransactionItem completePayment() {

        // looping through the transaction items
        // and getting the item name and price from each MenuItem
        // using that information to aad them to a list of items_purchased and total items Price
        double total_items_price = 0;
        for (MenuItem item : transaction_items) {
            total_items_price += item.getItemPrice();
        }

        // gets the payment option
        int payment_option = in.limitOptionChoice("Cash/Card?", new int[] { 1, 2 });

        // amount tendered / card type

        // cash is 1
        if (payment_option == 1) {

            // amount tendered
            double tendered_amount = in.getTenderedAmount("Cash", total_items_price, 0);

            // set change as the exact change
            double change_tendered = tendered_amount - total_items_price;

            // let user input change
            // change_tendered = in.getTenderedAmount("Change", 0.01, (change_tendered));
            transaction_list.add(new cashTransaction(new Date(), transaction_items, total_items_price, payment_option, tendered_amount, change_tendered));
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

            transaction_list.add(new cardTransaction(new Date(), transaction_items, total_items_price, payment_option, card_type_string));
        }

        return transaction_list.get(transaction_list.size()-1); // returns the most recent transaction
    }

    public void saveTransactions() {
        fh.writeToTransactionsCSV(transactions_file_path, transaction_list);
    }
}