// imports
import java.util.Date; // for getting current timestamp
import java.util.ArrayList;

/*
* Handles the processing of Transactions
* Holds the transaction data
*/
class Transactions {

    // variables
    private ArrayList<MenuItem> menu_list;
    private ArrayList<MenuItem> transaction_items = new ArrayList<MenuItem>();
    private ArrayList<Transaction> transactions = new ArrayList<Transaction>();
    private String transactions_file_path;

    // objects
    Input in;
    Menu menu;
    FileHandler fh;

    // constructor
    public Transactions(Menu menu, ArrayList<MenuItem> menu_list, String transactions_file_path) {
        // setting objects
        in = new Input();
        fh = new FileHandler();
        this.menu = menu;

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

    // gets the transaction information to complete the transaction
    // and adds it to the transactions ArrayList
    public void completePayment() {

        // gets the current time and date using the Date default initialisation
        Date time_stamp = new Date();

        // a Transaction abject that contains all the information to be for the transaction
        Transaction transaction = new Transaction(time_stamp, transaction_items);

        // gets the payment option
        int payment_option = in.limitOptionChoice("Cash/Card?", new int[] { 1, 2 });

        transaction.setTransactionType(payment_option);

        // amount tendered / card type

        // cash is 1
        if (payment_option == 1) {

            // amount tendered
            double tendered_amount = in.getTenderedAmount("Cash", transaction.getItemsPrice(), 0);
            transaction.setAmountTendered(tendered_amount);

            // change given (2 ways)

            // set change as the exact change
            transaction.setChangeTendered((tendered_amount - transaction.getItemsPrice()));

            // let cashier input change
            // transaction.setChangeTendered(in.getTenderedAmount("Change", 0.01, (tendered_amount - transaction.getItemsPrice())));
        }

        // card is 2
        else if (payment_option == 2) {

            // get card type
            int card_type = in.limitOptionChoice("Visa/Mastercard?", new int[] { 1, 2 });

            //returns 1 for Visa
            if (card_type == 1) {
                transaction.setCardType("Visa");

                //returns  2 for mastercard
            } else if (card_type == 2) {
                transaction.setCardType("Mastercard");
            }
        }

        transactions.add(transaction);

        menu.displayReceipt(transaction);
    }

    public void saveTransactions() {
        fh.writeToTransactionsCSV(transactions_file_path, transactions);
    }
}