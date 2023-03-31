// imports
import java.util.Date;
import java.util.ArrayList;

/*
* src.CashTransaction inheriting from the parent src.Transaction Item Class
* Holds the data for the Cash transaction
*/
class CashTransaction extends TransactionItem {

    // variables
    private double amount_tendered;
    private double change_tendered;

    // constructor
    public CashTransaction(Date timestamp, ArrayList<MenuItem> items_purchased, double items_price,
            double amount_tendered, double change_tendered) {
        super(timestamp, items_purchased, items_price);
        this.amount_tendered = amount_tendered;
        this.change_tendered = change_tendered;
    }

    // getter and setters
    public double getAmountTendered() {return amount_tendered;}

    public void setAmountTendered(double amount_tendered) {this.amount_tendered = amount_tendered;}

    public double getChangeTendered() {return change_tendered;}

    public void setChangeTendered(double change_tendered) {this.change_tendered = change_tendered;}


    // toString Method with the common information and cash information separated by commas
    // Format: [Date and time stamp], [Item/s Purchased], [Price], [Amount tendered], [Change given]
    public String toString() {
        // returns the generic transaction string from the transactionItem class and add the cash specific data
        return String.format("%s,%.2f,%.2f", super.toString(), amount_tendered, change_tendered);
    }
}