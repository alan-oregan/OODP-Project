package Singletons;

import Menu.MenuItem;
import Transaction.Transaction;
import Transaction.TransactionLog;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * FileHandler singleton class that handles reading to and from the files for transactions and inventory.
 */
public class FileHandler {

    // variables
    private static final boolean HEADER = true;
    // gets the system line separator for new lines and file separator for file paths
    private static final String NEWLINE = System.getProperty("line.separator");
    private static final String FILE_SEPARATOR = System.getProperty("file.separator");

    private static final String INVENTORY_FILE_PATH = "Data" + FILE_SEPARATOR + "inventory.csv";
    private static final String TRANSACTIONS_FILE_PATH = "Data" + FILE_SEPARATOR + "transactions.csv";

    // singleton instance
    private final Validator validator;

//    private FileWriter transactionsFileWriter;
    private final DefaultListModel<MenuItem> menuItems;

    // singleton instance
    private static FileHandler fileHandler;

    // constructor
    private FileHandler() {
        validator = Validator.GetValidator();
        menuItems = readMenuItems(); // read the menu items from the inventory file
    }


    // singleton method
    public static synchronized FileHandler GetFileHandler() {
        if (fileHandler == null)
            fileHandler = new FileHandler();
        return fileHandler;
    }


    // reads the menu items from the inventory csv file and returns them in an ArrayList of MenuItem objects
    // skips the items with invalid prices
    // exits the program if the file cannot be opened
    private DefaultListModel<MenuItem> readMenuItems() {
        DefaultListModel<MenuItem> menuItems = new DefaultListModel<>();
        try {
            BufferedReader inventoryFileReader = new BufferedReader(new FileReader(INVENTORY_FILE_PATH));

            // skip first line if there is a header
            if (HEADER)
                inventoryFileReader.readLine();

            // loops through the item rows/lines in the file while they exist
            String line;
            String[] item_row;
            while (true) {

                // getting the row as an array split by ,
                line = inventoryFileReader.readLine();

                // if the line is null, we have reached the end of the file
                if (line == null)
                    break;

                item_row = line.split(",");

                // separates the values in the row into variables
                String item_name = item_row[0];

                // takes the item price form the row as a string and converts it to a double
                // with a minimum limit of 0 and no maximum value
                // invalid input being -1, so we can skip the items with invalid prices
                double item_price = validator.validateDoubleInput(item_row[1], 0, 0, false, true, -1);

                // skips the items with invalid prices
                if (item_price != -1) {
                    // add the string item name and double item price to the ArrayList of MenuItem objects
                    menuItems.add(menuItems.size(), new MenuItem(item_name, item_price));
                }
            }

            inventoryFileReader.close();

        } catch (IOException e) {
            System.out.printf("Error - Could not open inventory file at %s.\n", INVENTORY_FILE_PATH);
            System.exit(1); // exit the program if the file cannot be opened
        }

        return menuItems;
    }


    public ListModel<MenuItem> getMenuItems() {
        return menuItems;
    }


    // write to the given filePath with the given transaction
    // returns true if the file write was successful
    public synchronized boolean writeToTransactionsCSV(ArrayList<TransactionLog> Transactions) {
        // write each transaction to the file on a new line
        try (FileWriter transactionFileWriter = new FileWriter(TRANSACTIONS_FILE_PATH, HEADER)) {
            for (TransactionLog transaction : Transactions) {
                transactionFileWriter.write(transaction.getTransactionLog() + NEWLINE);
            }
            return true;

        } catch (IOException e) {
            System.out.printf("Error - Could not write to transaction File at %s.\n", TRANSACTIONS_FILE_PATH);
            return false;
        }
    }
}