// imports
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/*
* Parent transaction class for holding common transaction information
* Child classes:
*  - CashTransaction
*  - CardTransaction
*/
public class TransactionItem {

    // variables
    private String timestamp;
    private ArrayList<MenuItem> items_purchased;
    private double items_price = 0;

    // objects
    private SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");


    // constructor
    public TransactionItem(Date timestamp, ArrayList<MenuItem> items_purchased, double items_price) {
        this.timestamp = df.format(timestamp);
        this.items_purchased = new ArrayList<MenuItem>(items_purchased);
        this.items_price = items_price;
    }


    // getter and setter methods
    public String getTimestamp() {return timestamp;}

    public void setTimestamp(String timestamp) {this.timestamp = timestamp;}

    public ArrayList<MenuItem> getItemsPurchased() {return items_purchased;}

    public void setItemsPurchased(ArrayList<MenuItem> items_purchased) {this.items_purchased = new ArrayList<MenuItem>(items_purchased);}

    public double getItemsPrice() {return items_price;}

    public void setItemsPrice(double items_price) {this.items_price = items_price;}


    // toString Method with the common information separated by commas
    // Format: [Date and time stamp], [Item/s Purchased], [Price]
    public String toString() {
        String items_string = "";

        // loops through the items purchased and converts them to a string
        // with '/' separating each item except for when there is only one item
        int i;
        for (i = 0; i < items_purchased.size() - 1; i++) {
            items_string += items_purchased.get(i).toString() + "/";
        }
        // adds the last item to the string without the '/' at the end
        items_string += items_purchased.get(i).toString();

        // returns a string for the transaction data separated by commas
        return String.format("%s,%s,%.2f", timestamp, items_string, items_price);
    }
}