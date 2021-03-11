// imports
import java.io.File; // Import the File class
import java.io.FileWriter; // for reading files
import java.io.IOException; // Import the IOException class to handle file write errors
import java.io.FileNotFoundException; // Import this class to handle file read errors
import java.util.ArrayList;
import java.util.Scanner;

/**
 * FileHandler class handles reading to and from the files for transactions and inventory.
 */
class FileHandler {

    //objects
    Input in;

    // variables
    private String file_path;
    private ArrayList<String[]> dataTable = new ArrayList<String[]>();

    // constructor
    public FileHandler(String file_path) {
        in = new Input();
        this.file_path = file_path;
    }

    // reads the csv file and returns it as a 2d arrayList
    public ArrayList<String[]> readCSV() {
        try {
            File fileObject = new File(file_path);
            Scanner myReader = new Scanner(fileObject);

            myReader.nextLine(); // skip first line (header)

            // loops through the item rows/lines in the file while they exist
            while (myReader.hasNextLine()) {

                // getting the row as an array split by ,
                String[] item_row = myReader.nextLine().split(",");

                dataTable.add(item_row);
            }

            myReader.close(); // closes the Scanner

        } catch (FileNotFoundException e) {
            System.out.printf("Error - file %s not found.\n", file_path);
            e.printStackTrace();
            in.enterToContinue();
        }

        return dataTable;
    }

    public void writeCSV(ArrayList<String[]> dataTable) {

        try {

            FileWriter wtr = new FileWriter(file_path, true); // append mode set to true

            // gets the system line separator for new lines
            String newLine = System.getProperty("line.separator");

            // wtr.write(key + newLine); // adds Key for csv on the line and moves cursor to the next

            // loops through the rows in the 2d array
            for (String[] row : dataTable) {

                // writes the items with , separator to the line for csv format
                // finishes before the last item
                int i;
                for (i = 0; i < row.length-1; i++)
                    wtr.write(row[i] + ", ");

                // writes the last item without the comma and moves the cursor for the next line
                wtr.write(row[i++] + newLine);
            }

            wtr.close(); // closes the FileWriter

        // catches any IOException and prints the cause
        } catch(IOException e) {
            System.out.printf("Transaction File Error:\n%s\n", e);
            in.enterToContinue();
        }
    }
}