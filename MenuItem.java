/*
* Creating an object for the menu items array list
*/
public class MenuItem {

    //variables
    private String item_name;
    private Double item_price;

    // input class for verifying item the price
    Input in = new Input();

    // constructor
    public MenuItem(String item_name, Double item_price) {
        this.item_name = item_name;
        this.item_price = item_price;
    }

    // returns the item name
    public String itemName() {return item_name;}
    // returns the item_price
    public Double itemPrice() {return item_price;}

    public String toString() {return String.format("Item: %s, Price: %f", item_name, item_price);}
}
