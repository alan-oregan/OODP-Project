package Transaction;

import Menu.MenuItem;

import java.util.Date;

/*
* Transaction class for the transaction
 */
public class Transaction implements TransactionLog {

    // Required parameters
    private Date date;
    private double totalPrice;
    private MenuItem[] itemsPurchased;

    // Optional parameters
    private boolean isCardTransaction;

    // for card transaction
    private String cardType;

    // for cash transaction
    private double amountTendered;
    private double changeTendered;

    // constructor
    public Transaction(TransactionBuilder transactionBuilder) {
        this.date = transactionBuilder.getDate();
        this.totalPrice = transactionBuilder.getTotalPrice();
        this.itemsPurchased = transactionBuilder.getItemsPurchased();
        this.isCardTransaction = transactionBuilder.isCardTransaction();
        this.cardType = transactionBuilder.getCardType();
        this.amountTendered = transactionBuilder.getAmountTendered();
        this.changeTendered = transactionBuilder.getChangeTendered();
    }


    // getter and setter methods
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public MenuItem[] getItemsPurchased() {
        return itemsPurchased;
    }

    public void setItemsPurchased(MenuItem[] itemsPurchased) {
        this.itemsPurchased = itemsPurchased;
    }

    public boolean isCardTransaction() {
        return isCardTransaction;
    }

    public void setCardTransaction(boolean cardTransaction) {
        isCardTransaction = cardTransaction;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public double getAmountTendered() {
        return amountTendered;
    }

    public void setAmountTendered(double amountTendered) {
        this.amountTendered = amountTendered;
    }

    public double getChangeTendered() {
        return changeTendered;
    }

    public void setChangeTendered(double changeTendered) {
        this.changeTendered = changeTendered;
    }

    public String getTransactionLog() {
        return String.format("%s,%s,%s", totalPrice, isCardTransaction);
    }
}