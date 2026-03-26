import java.util.HashMap;
import java.util.Map;

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
    public int getBeds() { return beds; }
    public int getSize() { return size; }
    public double getPrice() { return price; }
}

/**
 * ============================================================
 * CUSTOM EXCEPTION
 * ============================================================
 */
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

/**
 * ============================================================
 * CLASS - RoomInventory
 * ============================================================
 */
class RoomInventory {

    private Map<String, Integer> roomAvailability;

    public RoomInventory() {
        roomAvailability = new HashMap<>();
        initializeInventory();
    }

    private void initializeInventory() {
        roomAvailability.put("Single Room", 5);
        roomAvailability.put("Double Room", 3);
        roomAvailability.put("Suite Room", 2);
    }

    public Map<String, Integer> getRoomAvailability() {
        return roomAvailability;
    }

    // Safe update (prevents negative values)
    public void reduceAvailability(String roomType, int count) throws InvalidBookingException {

        if (!roomAvailability.containsKey(roomType)) {
            throw new InvalidBookingException("Invalid room type: " + roomType);
        }

        int available = roomAvailability.get(roomType);

        if (count <= 0) {
            throw new InvalidBookingException("Booking count must be greater than 0");
        }

        if (available < count) {
            throw new InvalidBookingException("Not enough rooms available for " + roomType);
        }

        // Valid case
        roomAvailability.put(roomType, available - count);
    }
}

/**
 * ============================================================
 * VALIDATOR CLASS
 * ============================================================
 */
class BookingValidator {

    public static void validate(String roomType, int count, RoomInventory inventory)
            throws InvalidBookingException {

        if (roomType == null || roomType.isEmpty()) {
            throw new InvalidBookingException("Room type cannot be empty");
        }

        if (count <= 0) {
            throw new InvalidBookingException("Invalid booking count");
        }

        if (!inventory.getRoomAvailability().containsKey(roomType)) {
            throw new InvalidBookingException("Room type does not exist");
        }
    }
}

/**
 * ============================================================
 * MAIN CLASS
 * ============================================================
 */
public class UseCase9ErrorHandling {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();

        System.out.println("---- Booking Simulation with Validation ----\n");

        // Test cases
        processBooking("Single Room", 2, inventory);   // valid
        processBooking("Double Room", 5, inventory);   // insufficient
        processBooking("Suite Room", -1, inventory);   // invalid count
        processBooking("Luxury Room", 1, inventory);   // invalid type

        System.out.println("\n---- Final Inventory ----");
        System.out.println(inventory.getRoomAvailability());
    }

    // Booking flow with error handling
    private static void processBooking(String roomType, int count, RoomInventory inventory) {
        try {
            BookingValidator.validate(roomType, count, inventory);
            inventory.reduceAvailability(roomType, count);
            System.out.println("Booking successful for " + roomType + " (" + count + ")");
        } catch (InvalidBookingException e) {
            System.out.println("Booking failed: " + e.getMessage());
        }
    }
}