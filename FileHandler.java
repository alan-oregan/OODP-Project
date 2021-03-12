// imports
import java.io.File; // Import the File class
import java.io.FileWriter; // for reading files
import java.io.IOException; // Import the IOException class to handle file write errors
import java.io.FileNotFoundException; // Import this class to handle file read errors
import java.util.ArrayList; // for the ArrayLists of objects to return and write to files
import java.util.Scanner; // for writing to the files

/**
 * FileHandler class handles reading to and from the files for transactions and inventory.
 */
class FileHandler {

    //objects
    Input in;

    // variables
    private ArrayList<MenuItem> menu_items = new ArrayList<MenuItem>();

    // constructor
    public FileHandler() {
        in = new Input();
    }

    // reads the csv file and returns it as a 2d arrayList
    public ArrayList<MenuItem> readInventoryCSV(String file_path) {
        try {
            File fileObject = new File(file_path);
            Scanner myReader = new Scanner(fileObject);

            myReader.nextLine(); // skip first line (header)

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
    public void writeToTransactionsCSV(String file_path, ArrayList<Transaction> Transactions) {

        try {

            FileWriter wtr = new FileWriter(file_path, true); // append mode set to true

            // gets the system line separator for new lines
            String newLine = System.getProperty("line.separator");

            // loops through the transactions in the Transactions ArrayList
            for (Transaction transaction : Transactions) {
                // writes the transaction information the line using the toString method
                // and moves the cursor for the next line
                wtr.write(transaction.toString() + newLine);
            }

            wtr.close(); // closes the FileWriter

            // catches any IOException and prints the cause
        } catch (IOException e) {
            System.out.printf("Transaction File Error:\n%s\n", e);
            in.enterToContinue();
        }
    }

    // overwrites or creates new file with key and adds the transaction information
    public void writeToTransactionsCSV(String file_path, ArrayList<Transaction> Transactions, String key) {

        try {

            FileWriter wtr = new FileWriter(file_path, false); // append mode set to false

            // gets the system line separator for new lines
            String newLine = System.getProperty("line.separator");

            // writes the key to the file and moves cursor to the next line
            wtr.write(key + newLine);

            // loops through the transactions in the Transactions ArrayList
            for (Transaction transaction : Transactions) {
                // writes the transaction information the line using the toString method
                // and moves the cursor for the next line
                wtr.write(transaction.toString() + newLine);
            }

            wtr.close(); // closes the FileWriter

        // catches any IOException and prints the cause
        } catch(IOException e) {
            System.out.printf("Transaction File Error:\n%s\n", e);
            in.enterToContinue();
        }
    }
}