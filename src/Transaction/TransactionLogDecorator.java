package Transaction;

import Menu.MenuItem;

import java.text.SimpleDateFormat;

/*
 * TransactionLogDecorator class to decorate the transaction log for the transaction
 */
public class TransactionLogDecorator implements TransactionLog {
    private Transaction transaction;

    public TransactionLogDecorator(Transaction transaction) {
        this.transaction = transaction;
    }

    @Override
    public String getTransactionLog() {
        StringBuilder transactionLog = new StringBuilder();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        transactionLog.append(df.format(transaction.getDate())).append(",");

        for (MenuItem menuItem : transaction.getItemsPurchased()) {
            transactionLog.append(menuItem.getName()).append(":").append(menuItem.getPrice()).append(",");
        }

        transactionLog.append(transaction.getTotalPrice());

        return transactionLog.toString();
    }
}
