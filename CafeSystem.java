/**
 * FOP2 Mini Project – 2021
 * Created by Alan O'Regan
 * Student Number: B00133474
 **/

// main class
public class CafeSystem {

    // main method
    public static void main(String[] args) {

        // menu object passing the inventory and transaction file paths with spacing
        Menu menu = new Menu("program_files/inventory.csv", false, "program_files/transactions.csv", true, 30);

        // program loop while user doesn't exit
        do {

            // displays menu
            menu.displayMenu();

            // gets users menu choice and processes it
            menu.menuChoice();

        } while (Menu.continueMenu());

    // close gracefully
    System.out.println("Program Closed.");
    }
}
// lets goooooooooooooooooooooooooooooooooooooooooooooooooo