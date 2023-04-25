package Transaction;

import Menu.MenuItem;

import java.util.ArrayList;
import java.util.Date;

/*
* src.Old.CashTransaction inheriting from the parent src.Old.Transaction Item Class
* Holds the data for the Cash transaction
*/
class CardTransaction extends TransactionItem {

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


        // toString Method with the common information and cash information separated by commas
        // Format: [Date and time stamp], [Item/s Purchased], [Price], [Card Type]
        public String toString() {
            // returns the generic transaction string from the transactionItem class and add the card specific data
            return String.format("%s,%s,N/A", super.toString(), card_type);
        }
}