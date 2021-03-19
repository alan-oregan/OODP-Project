import java.io.IOException;
import java.lang.InterruptedException;

/**
 * test
 */
public class test {
    // static method to clear the screen
    public static void clearScreen() {
        // tries to get the system name and if it is windows creates a process that clears the screen in cmd with cls
        // if its not windows it uses ansi escape codes
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                // clears the screen using the ansi escape codes to keep things clean
                // uses the codes 2J to clear the console and H to return to the top of the console
                // flushes the buffer stream to avoid possible data loss
                System.out.print("\033[2J\033[H");
            }
        } catch (IOException | InterruptedException ex) {
            // if there is an error print out a system separator to break up the output
            System.out.print(system_separator);
        }
    }
}