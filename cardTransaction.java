// imports
import java.util.Date; // for getting current timestamp
import java.util.ArrayList; // for sorting transaction data


public class CardTransaction extends TransactionItem {

        // for card transaction
        private String card_type;

        // constructor for card transaction
        public CardTransaction(Date timestamp, ArrayList<MenuItem> items_purchased, double items_price,
                String card_type) {
            super(timestamp, items_purchased, items_price);
            this.card_type = card_type;
        }

        // getter and setter methods
        public String getCardType() {return card_type;}

        public void setCardType(String card_type) {this.card_type = card_type;}

        public String toString() {
            // returns the generic transaction string from the transactionItem class and add the card specific data
            return String.format("%s,%s,N/A", super.toString(), card_type);
        }
}