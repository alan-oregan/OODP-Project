package Transaction;

/*
 * CardTransactionLogDecorator class to decorate the transaction log for the card transaction
 */
public class CardTransactionLogDecorator extends TransactionLogDecorator {
    Transaction transaction;

    public CardTransactionLogDecorator(Transaction transaction) {
        super(transaction);
        this.transaction = transaction;
    }

    @Override
    public String getTransactionLog() {
        return super.getTransactionLog() + ","
                + transaction.getCardType();
    }
}