package Old;

/*
* Creating an object for the menu items array list
*/
class MenuItem {

    //variables
    private String item_name;
    private double item_price;


    // constructor
    public MenuItem(String item_name, double item_price) {
        this.item_name = item_name;
        this.item_price = item_price;
    }

    // getter and setter methods
    public void setItemName(String item_name) {this.item_name = item_name;}

    public String getItemName() {return item_name;}

    public void setItemPrice(double item_price) {this.item_price = item_price;}

    public double getItemPrice() {return item_price;}

    // toString method
    public String toString() {return String.format("%s:%.2f", item_name, item_price);}
}