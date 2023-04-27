package Transaction;

import Singletons.OrderHandler;

import java.util.Date;

/*
 * TransactionDirector class to build the transaction
 */
public class TransactionDirector {

    private static final OrderHandler orderHandler = OrderHandler.GetOrderHandler();

    public static Transaction getNewCardTransaction(String cardType) {
        return new TransactionBuilder(new Date(),
                orderHandler.getTotalPrice(),
                orderHandler.getOrderItemsArray(),
                true)
                .setCardType(cardType)
                .build();
    }

    public static Transaction getNewCashTransaction(double amountTendered, double changeTendered) {
        return new TransactionBuilder(
                new Date(),
                orderHandler.getTotalPrice(),
                orderHandler.getOrderItemsArray(),
                false)
                .setAmountTendered(amountTendered)
                .setChangeTendered(changeTendered)
                .build();
    }
}
