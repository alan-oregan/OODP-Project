package Transaction;

import Singletons.OrderHandler;

import java.util.Date;

/*
 * TransactionDirector class to build the transaction
 */
public class TransactionDirector {

    public static Transaction getNewCardTransaction(String cardType) {
        return new TransactionBuilder(new Date(),
                OrderHandler.GetTransactionHandler().getTotalPrice(),
                OrderHandler.GetTransactionHandler().getOrderItemsArray(),
                true)
                .setCardType(cardType)
                .build();
    }

    public static Transaction getNewCashTransaction(double amountTendered, double changeTendered) {
        return new TransactionBuilder(
                new Date(),
                OrderHandler.GetTransactionHandler().getTotalPrice(),
                OrderHandler.GetTransactionHandler().getOrderItemsArray(),
                false)
                .setAmountTendered(amountTendered)
                .setChangeTendered(changeTendered)
                .build();
    }
}
