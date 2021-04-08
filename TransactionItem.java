// imports
import java.util.Date;
import java.util.ArrayList;

/*
* transaction object for holding a transaction
* for cash: [Date and time stamp], [Item/s Purchased], [Price], [Amount tendered], [Change given]
* for card: [Date and time stamp], [Item/s Purchased], [Price], [Card type], ["N/A"]
*/

public class TransactionItem {

    // for every transaction
    private String timestamp;
    private ArrayList<MenuItem> items_purchased = new ArrayList<MenuItem>();
    private double items_price = 0;
    private int transaction_type;

    // constructor
    public TransactionItem(Date timestamp, ArrayList<MenuItem> items_purchased, double items_price, int transaction_type) {
        this.timestamp = timestamp.toString();
        this.items_purchased = items_purchased;
        this.items_price = items_price;
        this.transaction_type = transaction_type;
    }

    // getter and setter methods
    public String getTimestamp() {return timestamp;}

    public void setTimestamp(String timestamp) {this.timestamp = timestamp;}

    public ArrayList<MenuItem> getItemsPurchased() {return items_purchased;}

    public void setItemsPurchased(ArrayList<MenuItem> items_purchased) {this.items_purchased = items_purchased;}

    public double getItemsPrice() {return items_price;}

    public void setItemsPrice(double items_price) {this.items_price = items_price;}

    public int getTransactionType() {return transaction_type;}

    public void setTransactionType(int transaction_type) {this.transaction_type = transaction_type;}


    public String toString() {
        String str = timestamp + ",";

        // get the items purchased and convert to a string with '/' separating each item
        int i;
        for (i = 0; i < items_purchased.size() - 1; i++) {
            str += items_purchased.get(i).toString() + "/";
        }
        // adds the last item to the string without the '/' at the end
        str += items_purchased.get(i).toString() + ",";

        // add the items price
        str += items_price + ",";

        // returns a string for the transaction data separated by commas
        return str;
    }
}