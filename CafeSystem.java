// Notes -----------------------------------------------------------------------------------------------------------
// https://www.youtube.com/watch?v=yO_ctH4mEk4
// https://stackoverflow.com/questions/16956720/how-to-create-an-2d-arraylist-in-java/16956747
// https://www.w3schools.com/java/java_arraylist.asp
// https://www.youtube.com/watch?v=lcWV7hLYByk
// https://stackoverflow.com/questions/2979383/java-clear-the-console
// https://www.youtube.com/watch?v=Vs2ZR7-LJO0
// https://www.w3schools.com/java/java_files_read.asp
// https://www.w3schools.com/java/java_files_create.asp
// https://stackoverflow.com/questions/2255500/can-i-multiply-strings-in-java-to-repeat-sequences
// https://stackoverflow.com/questions/16956720/how-to-create-an-2d-arraylist-in-java/16956747
// https://www.w3schools.com/java/java_arraylist.asp
// https://www.tutorialspoint.com/convert-from-string-to-double-in-java#:~:text=To%20convert%20String%20to%20double%2C%20use%20the%20valueOf()%20method.
// https://www.geeksforgeeks.org/format-specifiers-in-java/
// https://stackoverflow.com/questions/6264576/number-of-decimal-digits-in-a-double

// main class
public class CafeSystem {

    // main method
    public static void main(String[] args) {

        // object instances
        // menu object passing the inventory and transaction file paths
        Menu menu = new Menu("program_files/inventory.csv", "program_files/transactions.csv");

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