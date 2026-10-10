import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

/** Demonstrates the required constructor, remove, add, update, and export. */
public class HomeInventory {
    public static void main(String[] args) {
        try (Scanner input = new Scanner(System.in)) {
            Home home = new Home(1800, "125 Aspen Lane", "Denver", "CO",
                    80202, "Aspen", "available");
            System.out.println("Home constructed successfully.");
            for (String value : home.listHome()) {
                System.out.println(value);
            }

            System.out.println("\n" + home.removeHome());
            String added = home.addHome(2400, "48 Maple Drive", "Boston",
                    "MA", 2108, "Maple", "available");
            System.out.println(added);
            for (String value : home.listHome()) {
                System.out.println(value);
            }

            System.out.println("\n" + home.updateHome("under contract"));
            for (String value : home.listHome()) {
                System.out.println(value);
            }

            String response;
            do {
                System.out.print("\nPrint home information to a file (Y or N)? ");
                response = input.nextLine().trim();
                if (!response.equalsIgnoreCase("Y")
                        && !response.equalsIgnoreCase("N")) {
                    System.out.println("Please enter Y or N.");
                }
            } while (!response.equalsIgnoreCase("Y")
                    && !response.equalsIgnoreCase("N"));

            if (response.equalsIgnoreCase("Y")) {
                System.out.println(printToFile(home.listHome()));
            } else {
                System.out.println("A file will not be printed.");
            }
        } catch (Exception e) {
            System.out.println("Home inventory failed: " + e.getMessage());
        }
    }

    public static String printToFile(String[] values) {
        try {
            if (values == null || values.length != 7) {
                throw new IOException("A complete home record is required.");
            }
            // Fixed destination relative to the program's working directory.
            Path destination = Paths.get("output", "Home.txt");
            Files.createDirectories(destination.getParent());
            Files.write(destination, Arrays.asList(values), StandardCharsets.UTF_8);
            return "File written successfully to " + destination.toAbsolutePath();
        } catch (Exception e) {
            return "File output failed: " + e.getMessage();
        }
    }
}
