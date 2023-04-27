package Transaction;

import Menu.MenuItem;
import Singletons.OrderHandler;

import java.util.Date;
/*
 * TransactionBuilder class to build the transaction
 */
public class TransactionBuilder {

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


    public TransactionBuilder(Date date, double totalPrice, MenuItem[] itemsPurchased, boolean isCardTransaction) {
        this.date = date;
        this.totalPrice = totalPrice;
        this.itemsPurchased = itemsPurchased;
        this.isCardTransaction = isCardTransaction;
    }

    public Date getDate() {
        return date;
    }

    public TransactionBuilder setDate(Date date) {
        this.date = date;
        return this;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public TransactionBuilder setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public MenuItem[] getItemsPurchased() {
        return itemsPurchased;
    }

    public TransactionBuilder setItemsPurchased(MenuItem[] itemsPurchased) {
        this.itemsPurchased = itemsPurchased;
        return this;
    }

    public boolean isCardTransaction() {
        return isCardTransaction;
    }

    public TransactionBuilder setCardTransaction(boolean cardTransaction) {
        isCardTransaction = cardTransaction;
        return this;
    }

    public String getCardType() {
        return cardType;
    }

    public TransactionBuilder setCardType(String cardType) {
        this.cardType = cardType;
        return this;
    }

    public double getAmountTendered() {
        return amountTendered;
    }

    public TransactionBuilder setAmountTendered(double amountTendered) {
        this.amountTendered = amountTendered;
        return this;
    }

    public double getChangeTendered() {
        return changeTendered;
    }

    public TransactionBuilder setChangeTendered(double changeTendered) {
        this.changeTendered = changeTendered;
        return this;
    }

    public Transaction build() {
        return new Transaction(this);
    }
}
