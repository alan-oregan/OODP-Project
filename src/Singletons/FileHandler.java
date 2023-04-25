package Singletons;

import Menu.MenuItem;
import Transaction.TransactionItem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * FileHandler singleton class that handles reading to and from the files for transactions and inventory.
 */
public class FileHandler {

    //objects
    private final Validator validator;
    private static FileHandler fileHandler;

    // variables
    private final ArrayList<MenuItem> menuItems = new ArrayList<>();


    // constructor
    private FileHandler() {
        validator = Validator.GetValidator();
    }

    public static synchronized FileHandler GetFileHandler() {
        if (fileHandler == null)
            fileHandler = new FileHandler();
        return fileHandler;
    }


    // reads the inventor csv file and returns the inventory items in an ArrayList of MenuItems
    public synchronized ArrayList<MenuItem> readInventoryCSV(String file_path, boolean header) {
        try {
            File fileObject = new File(file_path);
            Scanner myReader = new Scanner(fileObject);

            // skip first line if there is a header
            if (header) {
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
                // invalid input being -1, so we can skip the items with invalid prices
                double item_price = validator.validateDoubleInput(item_row[1], 0, 0, false, true, -1);

                // skips the items with invalid prices
                if (item_price != -1) {
                    // add the string item name and double item price to the ArrayList of MenuItem objects
                    menuItems.add(new MenuItem(item_name, item_price));
                }
            }

            myReader.close(); // closes the Scanner

        } catch (FileNotFoundException e) {
            System.out.printf("Error - Inventory file not found at %s.\n", file_path);
            e.printStackTrace();
        }

        return menuItems;
    }


    // write to the given filePath with the given transaction
    public synchronized void writeToTransactionsCSV(ArrayList<TransactionItem> Transactions, String filePath, boolean append) {

        try {

            FileWriter wtr = new FileWriter(filePath, append);

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
            System.out.printf("Error - Could not transaction File at %s.\n", filePath);
            e.printStackTrace();
        }
    }
}