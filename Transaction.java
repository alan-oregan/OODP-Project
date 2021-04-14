// imports
import java.util.Date;
import java.util.ArrayList;

/*
* Handles the processing of Transactions
* Holds the transaction data
*/
class Transaction {

    // objects
    private Input in;
    private FileHandler fh;

    // variables
    private ArrayList<MenuItem> menu_list;
    private ArrayList<MenuItem> items = new ArrayList<MenuItem>();
    private ArrayList<TransactionItem> transaction_list = new ArrayList<TransactionItem>();
    private String transactions_file_path;
    private boolean append_mode;


    // constructor
    public Transaction(ArrayList<MenuItem> menu_list, String currency, String transactions_file_path, boolean append_mode) {
        // setting objects
        in = new Input(currency);
        fh = new FileHandler();

        // variables
        this.menu_list = menu_list;
        this.transactions_file_path = transactions_file_path;
        this.append_mode = append_mode;
    }


    // adds an item from the menu ArrayList at the given index
    // to the transaction ArrayList
    public void addItem(int menu_item_index) {
        items.add(menu_list.get(menu_item_index));
    }


    // removes the transaction item at the given index from the ArrayList
    public void removeItem(int transaction_item_index) {
        items.remove(transaction_item_index);
    }


    public ArrayList<MenuItem> getItems() {
        return items;
    }


    // clears the items list
    public void clearItems() {
        items.clear();
    }


    // returns the most recent transaction
    public TransactionItem getLatestTransaction() {
        return transaction_list.get(transaction_list.size() - 1);
    }


    // gets the rest of the transaction information
    // then adds it to the transactions ArrayList
    public boolean completePayment() {

        // looping through the items
        // and getting the total items Price
        double total_items_price = 0;
        for (MenuItem item : items) {
            total_items_price += item.getItemPrice();
        }

        // gets the payment option and processes it in a switch
        switch (in.limitOptionChoice("Cash/Card?", new int[] { 1, 2 })) {

            case 1: {

                // amount tendered
                double tendered_amount = in.getTenderedAmount("Amount", total_items_price, 0);

                if (tendered_amount == -1) {
                    return false; // transaction failed
                }

                // get exact change
                double change = tendered_amount - total_items_price;

                // possibly let user input change
                // loop while invalid
                // do {
                //     change = in.getTenderedAmount("Change", 0, change);
                // } while (change == -1);

                transaction_list.add(new CashTransaction(new Date(), items, total_items_price, tendered_amount, change));

                break;
            }

            case 2: {

                // get card type
                String card_type = in.limitOptionChoice(new String[] { "Visa", "Mastercard" });

                transaction_list.add(new CardTransaction(new Date(), items, total_items_price, card_type));

                break;
            }
        }

        // since transaction succeed
        clearItems();
        return true;
    }

    // 
    public void saveTransactions() {
        fh.writeToTransactionsCSV(transaction_list, transactions_file_path, append_mode);
    }
}