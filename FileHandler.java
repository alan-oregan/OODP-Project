// imports
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * FileHandler class handles reading to and from the files for transactions and inventory.
 */
class FileHandler {

    //objects
    private Input in;

    // variables
    private ArrayList<MenuItem> menu_items = new ArrayList<MenuItem>();


    // constructor
    public FileHandler() {
        in = new Input();
    }


    // reads the csv file and returns it as a 2d arrayList
    public ArrayList<MenuItem> readInventoryCSV(String file_path, boolean header) {
        try {
            File fileObject = new File(file_path);
            Scanner myReader = new Scanner(fileObject);

            // skip first line if there is a header
            if (header == true) {
                myReader.nextLine();
            }

            // loops through the item rows/lines in the file while they exist
            while (myReader.hasNextLine()) {

                // getting the row as an array split by ,
                String[] item_row = myReader.nextLine().split(",");

                // separates the values in the row into variables
                String item_name = item_row[0];

                // takes the item price form the row as a string and converts it to a double
                // with a minimum limit of 0 and no maximum value
                // invalid input being -1 so we can skip the items with invalid prices
                double item_price = in.validateDoubleInput(item_row[1], 0, 0, false, true, -1);

                // skips the items with invalid prices
                if (item_price != -1) {
                    // add the string item name and double item price to the ArrayList of MenuItem objects
                    menu_items.add(new MenuItem(item_name, item_price));
                }
            }

            myReader.close(); // closes the Scanner

        } catch (FileNotFoundException e) {
            System.out.printf("Error - file %s not found.\n", file_path);
            e.printStackTrace();
            in.enterToContinue();
        }

        return menu_items;
    }


    // append to the file with the transaction
    public void writeToTransactionsCSV(ArrayList<TransactionItem> Transactions, String file_path, boolean append) {

        try {

            FileWriter wtr = new FileWriter(file_path, append); // append mode set to true

            // gets the system line separator for new lines
            String newLine = System.getProperty("line.separator");

            // loops through the transactions in the Transactions ArrayList
            for (TransactionItem transaction : Transactions) {
                // writes the transaction information a new line using the toString method
                wtr.write(transaction.toString() + newLine);
            }

            wtr.close(); // closes the FileWriter

            // catches any IOException and prints the cause
        } catch (IOException e) {
            System.out.printf("Transaction File Error:\n%s\n", e);
            in.enterToContinue();
        }
    }
}