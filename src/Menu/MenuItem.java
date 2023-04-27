package Menu;

import static Menu.CafeMenuGUI.CURRENCY;

/*
 * Data class data stores menu item name and price
 */
public class MenuItem {

    //variables
    private final String itemName;
    private final double itemPrice;


    // constructor
    public MenuItem(String itemName, double itemPrice) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
    }

    // getter methods
    public String getName() {
        return itemName;
    }

    public double getPrice() {
        return itemPrice;
    }

    // toString method
    public String toString() {
        return String.format("%s%.2f %s", CURRENCY, itemPrice, itemName);
    }
}