import java.io.*;
import java.util.*;

/**
 * ============================================================
 * CLASS - SystemState (Serializable)
 * ============================================================
 */
class SystemState implements Serializable {
    private static final long serialVersionUID = 1L;

    Map<String, Integer> inventory;
    Map<String, String> bookings;

    public SystemState(Map<String, Integer> inventory, Map<String, String> bookings) {
        this.inventory = inventory;
        this.bookings = bookings;
    }
}

/**
 * ============================================================
 * CLASS - PersistenceService
 * ============================================================
 */
class PersistenceService {

    private static final String FILE_NAME = "hotel_state.ser";

    // Save state
    public static void save(SystemState state) {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            oos.writeObject(state);
            System.out.println("System state saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving state: " + e.getMessage());
        }
    }

    // Load state
    public static SystemState load() {
        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            SystemState state = (SystemState) ois.readObject();
            System.out.println("System state loaded successfully.");
            return state;

        } catch (FileNotFoundException e) {
            System.out.println("No previous state found. Starting fresh.");
        } catch (Exception e) {
            System.out.println("Error loading state: " + e.getMessage());
        }
        return null;
    }
}

/**
 * ============================================================
 * MAIN CLASS
 * ============================================================
 */
public class UseCase12Persistence {

    public static void main(String[] args) {

        Map<String, Integer> inventory;
        Map<String, String> bookings;

        // Step 1: Load previous state
        SystemState state = PersistenceService.load();

        if (state != null) {
            inventory = state.inventory;
            bookings = state.bookings;
        } else {
            // Initialize fresh state
            inventory = new HashMap<>();
            inventory.put("Single Room", 5);
            inventory.put("Double Room", 3);
            inventory.put("Suite Room", 2);

            bookings = new HashMap<>();
        }

        // Step 2: Simulate booking
        System.out.println("\n---- Booking Simulation ----");

        String bookingId = "B101";
        String roomType = "Single Room";

        if (inventory.get(roomType) > 0) {
            inventory.put(roomType, inventory.get(roomType) - 1);
            bookings.put(bookingId, roomType);
            System.out.println("Booking successful: " + bookingId);
        } else {
            System.out.println("Booking failed: No rooms available");
        }

        // Step 3: Display current state
        System.out.println("\nCurrent Inventory: " + inventory);
        System.out.println("Current Bookings: " + bookings);

        // Step 4: Save state before shutdown
        System.out.println("\n---- Saving State ----");
        PersistenceService.save(new SystemState(inventory, bookings));
    }
}