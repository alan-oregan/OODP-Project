// imports
import java.util.Date;
import java.util.ArrayList;

/*
* transaction object for holding transaction data
* for cash: [Date and time stamp], [Item/s Purchased], [Price], [Amount tendered], [Change given]
* for card: [Date and time stamp], [Item/s Purchased], [Price], [Card type], ["N/A"]
*/

public class Transaction {

    // for every transaction
    private String timestamp;
    private ArrayList<MenuItem> items_purchased = new ArrayList<MenuItem>();
    private double items_price = 0;
    private int transaction_type;

    // for cash transaction
    private double amount_tendered;
    private double change_tendered;

    // for card transaction
    private String card_type;

    // constructor for cash transaction
    public Transaction(String timestamp, ArrayList<MenuItem> items_purchased, double items_price, int transaction_type,
            double amount_tendered, double change_tendered) {
        this.timestamp = timestamp;
        this.items_purchased = items_purchased;
        this.items_price = items_price;
        this.transaction_type = transaction_type;
        this.amount_tendered = amount_tendered;
        this.change_tendered = change_tendered;
    }

    // constructor for card transaction
    public Transaction(String timestamp, ArrayList<MenuItem> items_purchased, double items_price, int transaction_type,
            String card_type) {
        this.timestamp = timestamp;
        this.items_purchased = items_purchased;
        this.items_price = items_price;
        this.transaction_type = transaction_type;
        this.card_type = card_type;
    }

    // constructor for finishing transaction information using getter and setter methods
    public Transaction(Date timestamp, ArrayList<MenuItem> transaction_items) {
        // setting time stamp
        this.timestamp = timestamp.toString();

        // looping through the transaction items
        // and getting the item name and price from each MenuItem
        // using that information to aad them to a list of items_purchased and total items Price
        for (MenuItem item : transaction_items) {
            items_purchased.add(item);
            items_price += item.getItemPrice();
        }
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

    // for cash transaction
    public double getAmountTendered() {return amount_tendered;}

    public void setAmountTendered(double amount_tendered) {this.amount_tendered = amount_tendered;}

    public double getChangeTendered() {return change_tendered;}

    public void setChangeTendered(double change_tendered) {this.change_tendered = change_tendered;}

    // for card transaction
    public String getCardType() {return card_type;}

    public void setCardType(String card_type) {this.card_type = card_type;}

    public String toString() {
        String str = "Invalid transaction type"; // if the transaction_type is not 1 or 2

        // cash transaction
        if (transaction_type == 1) {
            str = String.format("%s, \"%s\", %.2f, %.2f, %.2f", timestamp, items_purchased.toString(), items_price,
                    amount_tendered, change_tendered);
        }

        // card transaction with N/A for change column
        else if (transaction_type == 2) {
            str = String.format("%s, \"%s\", %.2f, %s, N/A", timestamp, items_purchased.toString(), items_price,
                    card_type);
        }

        // return the appropriate string for the transaction type
        return str;
    }
}