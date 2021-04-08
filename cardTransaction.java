// imports
import java.util.Date; // for getting current timestamp
import java.util.ArrayList; // for sorting transaction data


public class cardTransaction extends TransactionItem {

        // for card transaction
        private String card_type;

        // constructor for card transaction
        public cardTransaction(Date timestamp, ArrayList<MenuItem> items_purchased, double items_price,
                int transaction_type, String card_type) {
            super(timestamp, items_purchased, items_price, transaction_type);
            this.card_type = card_type;
        }

        // getter and setter methods
        public String getCardType() {return card_type;}

        public void setCardType(String card_type) {this.card_type = card_type;}

        public String toString() {
            // returns the generic transaction string from the transactionItem class and add the card specific data
            return super.toString() + String.format("%s,N/A", card_type);
        }
}
