package Singletons;

import Menu.MenuItem;
import Transaction.Transaction;
import Transaction.TransactionLog;
import Transaction.CardTransactionLogDecorator;
import Transaction.CashTransactionLogDecorator;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;

/*
 * Handles the processing of Order items
 * Holds the current order items
 * Holds a list of completed transactions
 */
public class OrderHandler {

    private static OrderHandler orderHandler;
    // objects
    private final FileHandler fileHandler = FileHandler.GetFileHandler();
    // variables
    private final DefaultListModel<MenuItem> orderItems;
    private final ArrayList<TransactionLog> transactions;
    private Transaction latestTransaction;

    public OrderHandler() {
        orderItems = new DefaultListModel<>();
        transactions = new ArrayList<>();
    }

    public static OrderHandler GetTransactionHandler() {
        if (orderHandler == null)
            orderHandler = new OrderHandler();
        return orderHandler;
    }


    // adds an item to the end of the transaction list
    public void addItem(MenuItem item) {
        orderItems.add(orderItems.size(), item);
    }

    // removes the transaction item at the given index from the ArrayList
    public void removeItem(MenuItem item) {
        orderItems.removeElement(item);
    }

    public DefaultListModel<MenuItem> getOrderItems() {
        return orderItems;
    }

    public MenuItem[] getOrderItemsArray() {
        return Arrays.copyOf(orderItems.toArray(), orderItems.size(), MenuItem[].class);
    }


    // clears the current order items
    public void clearOrder() {
        orderItems.clear();
    }


    // returns the total price of the current order
    public double getTotalPrice() {
        double total = 0;
        for (int i = 0; i < orderItems.size(); i++) {
            total += orderItems.get(i).getPrice();
        }
        return total;
    }


    // returns the most recent transaction
    public Transaction getLatestTransaction() {
        return latestTransaction;
    }


    // add completed transaction
    public void addTransaction(Transaction transaction) {
        if (transaction == null) {
            System.out.println("Error - Transaction is null");
            return;
        }
        if (transaction.isCardTransaction()) {
            transactions.add(new CardTransactionLogDecorator(transaction));
        } else {
            transactions.add(new CashTransactionLogDecorator(transaction));
        }
        latestTransaction = transaction;
    }

    // saves the transactions to the transactions csv file
    public boolean saveTransactions() {
        if (transactions.isEmpty()) {
            return true;
        }

        System.out.println("Saving transactions...");
        return fileHandler.writeToTransactionsCSV(transactions);
    }
}