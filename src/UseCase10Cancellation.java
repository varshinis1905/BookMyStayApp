import java.util.*;

/**
 * ============================================================
 * CLASS - Room
 * ============================================================
 */
class Room {
    private String type;
    private int beds;
    private int size;
    private double price;

    public Room(String type, int beds, int size, double price) {
        this.type = type;
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    public String getType() { return type; }
}

/**
 * ============================================================
 * CUSTOM EXCEPTION
 * ============================================================
 */
class BookingException extends Exception {
    public BookingException(String message) {
        super(message);
    }
}

/**
 * ============================================================
 * CLASS - RoomInventory
 * ============================================================
 */
class RoomInventory {

    private Map<String, Integer> availability;

    public RoomInventory() {
        availability = new HashMap<>();
        availability.put("Single Room", 5);
        availability.put("Double Room", 3);
        availability.put("Suite Room", 2);
    }

    public int getAvailable(String type) {
        return availability.getOrDefault(type, 0);
    }

    public void decrease(String type) throws BookingException {
        int count = getAvailable(type);
        if (count <= 0) {
            throw new BookingException("No rooms available for " + type);
        }
        availability.put(type, count - 1);
    }

    public void increase(String type) {
        availability.put(type, getAvailable(type) + 1);
    }

    public void showInventory() {
        System.out.println("Current Inventory: " + availability);
    }
}

/**
 * ============================================================
 * CLASS - BookingService
 * ============================================================
 */
class BookingService {

    private Map<String, String> bookings; // bookingId -> roomType
    private Stack<String> rollbackStack;  // stores cancelled booking IDs
    private RoomInventory inventory;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
        bookings = new HashMap<>();
        rollbackStack = new Stack<>();
    }

    // Create booking
    public void bookRoom(String bookingId, String roomType) {
        try {
            if (bookings.containsKey(bookingId)) {
                throw new BookingException("Booking already exists: " + bookingId);
            }

            inventory.decrease(roomType);
            bookings.put(bookingId, roomType);

            System.out.println("Booking successful: " + bookingId + " -> " + roomType);

        } catch (BookingException e) {
            System.out.println("Booking failed: " + e.getMessage());
        }
    }

    // Cancel booking (Rollback)
    public void cancelBooking(String bookingId) {
        try {
            if (!bookings.containsKey(bookingId)) {
                throw new BookingException("Booking does not exist: " + bookingId);
            }

            String roomType = bookings.get(bookingId);

            // Push to stack (LIFO rollback tracking)
            rollbackStack.push(bookingId);

            // Restore inventory
            inventory.increase(roomType);

            // Remove booking
            bookings.remove(bookingId);

            System.out.println("Cancellation successful: " + bookingId);

        } catch (BookingException e) {
            System.out.println("Cancellation failed: " + e.getMessage());
        }
    }

    public void showRollbackStack() {
        System.out.println("Rollback Stack: " + rollbackStack);
    }
}

/**
 * ============================================================
 * MAIN CLASS
 * ============================================================
 */
public class UseCase10Cancellation {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        BookingService service = new BookingService(inventory);

        System.out.println("---- Booking Phase ----");
        service.bookRoom("B101", "Single Room");
        service.bookRoom("B102", "Double Room");

        inventory.showInventory();

        System.out.println("\n---- Cancellation Phase ----");
        service.cancelBooking("B101");  // valid
        service.cancelBooking("B999");  // invalid

        inventory.showInventory();

        System.out.println("\n---- Rollback Tracking ----");
        service.showRollbackStack();
    }
}