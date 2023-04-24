package Old;
import java.util.Date;
import java.util.ArrayList;

/*
* Handles the processing of Transactions
* Holds the transaction data
*/
class Transaction {

    // objects
    private final Input in;
    private final FileHandler fh;

    // variables
    private final ArrayList<MenuItem> menu_list;
    private final ArrayList<MenuItem> items = new ArrayList<>();
    private final ArrayList<TransactionItem> transaction_list = new ArrayList<>();
    private final String transactions_file_path;
    private final boolean append_mode;


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
        switch (in.limitOptionChoice("Cash/Card?", new int[]{1, 2})) {

            // invalid
            case 0 -> {
                return false;
            }


            // cash
            case 1 -> {

                // amount tendered
                double tendered_amount = in.getTenderedAmount("Amount", total_items_price, 0);

                if (tendered_amount == -1) {
                    return false; // transaction failed
                }

                // get change
                double change = tendered_amount - total_items_price;

                transaction_list.add(new CashTransaction(new Date(), items, total_items_price, tendered_amount, change));
            }


            // card
            case 2 -> {

                // returns invalid card type or returns "Invalid" if invalid
                String card_type = in.limitOptionChoice(new String[]{"Visa", "Mastercard"});

                if (card_type.equals("Invalid")) {
                    return false; // transaction failed
                }

                transaction_list.add(new CardTransaction(new Date(), items, total_items_price, card_type));
            }
        }

        // since transaction succeed
        clearItems();
        return true;
    }

    // saves the transactions to the file path with append mode
    public void saveTransactions() {
        fh.writeToTransactionsCSV(transaction_list, transactions_file_path, append_mode);
    }
}