package Transaction;

/*
 * CashTransactionLogDecorator class to decorate the transaction log for the Cash transaction
 */
public class CashTransactionLogDecorator extends TransactionLogDecorator {
    Transaction transaction;

    public CashTransactionLogDecorator(Transaction transaction) {
        super(transaction);
        this.transaction = transaction;
    }

    @Override
    public String getTransactionLog() {
        return super.getTransactionLog() + ","
                + transaction.getAmountTendered() + ","
                + transaction.getChangeTendered();
    }
}
