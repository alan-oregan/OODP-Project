/**
 * FOP2 Mini Project – 2021
 * Created by Alan O'Regan
 * Student Number: B00133474
 *
 * This is a point of sale computer program for a Coffee shop
 * The menu is read from an inventory file
 * The Program displays the menu and allows the user to process an order
 * The transaction information is processed in the program and a receipt is generated
 * The program keeps a record of transactions for the user by saving them to a transaction file
 **/

// main class
public class CafeSystem {

    // main method
    public static void main(String[] args) {

        // object instances
        // menu object passing the inventory and transaction file paths with spacing
        Menu menu = new Menu("program_files/inventory.csv", "program_files/transactions.csv", 30);

        // program loop while user doesn't exit
        do {
            // displays menu
            menu.displayMenu();

            // gets users system menu choice and processes it
            menu.menuChoice();

        // repeat while exit is not true
        } while (Menu.continueMenu());

    // close gracefully
    System.out.println("Program Closed.");
    }
}
// lets goooooooooooooooooooooooooooooooooooooooooooooooooo