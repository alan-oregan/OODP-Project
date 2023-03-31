/**
 * FOP2 Mini Project – 2021
 * Created by Alan O'Regan
 * Student Number: B00133474
 **/

class Main {

    // main method
    public static void main(String[] args) {

        // menu object passing the parameters:
        // inventory file path, if heading exists, ISO 4217 standard currency code, transaction file path, append mode, item spacing
        Menu menu = new Menu("program_files/inventory.csv", false, "EUR", "program_files/transactions.csv", true, 25);

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
