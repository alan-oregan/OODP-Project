// imports
import java.util.Date; // for getting current timestamp
import java.util.ArrayList; // for sorting transaction data


public class cashTransaction extends TransactionItem {

    private double amount_tendered;
    private double change_tendered;

    public cashTransaction(Date timestamp, ArrayList<MenuItem> items_purchased, double items_price, int transaction_type,
            double amount_tendered, double change_tendered) {
        super(timestamp, items_purchased, items_price, transaction_type);

        this.amount_tendered = amount_tendered;
        this.change_tendered = change_tendered;
    }

    public double getAmountTendered() {return amount_tendered;}

    public void setAmountTendered(double amount_tendered) {this.amount_tendered = amount_tendered;}

    public double getChangeTendered() {return change_tendered;}

    public void setChangeTendered(double change_tendered) {this.change_tendered = change_tendered;}

    public String toString() {
        // returns the generic transaction string from the transactionItem class and add the cash specific data
        return super.toString() + String.format("%.2f,%.2f", amount_tendered, change_tendered);
    }
}