import java.util.HashMap;
import java.util.Map;

/**
 * ============================================================
 * CLASS - Room
 * ============================================================
 * Represents room details like beds, size and price.
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

    public String getType() {
        return type;
    }

    public int getBeds() {
        return beds;
    }

    public int getSize() {
        return size;
    }

    public double getPrice() {
        return price;
    }
}

/**
 * ============================================================
 * CLASS - RoomInventory
 * ============================================================
 * Centralized Room Inventory Management
 */
class RoomInventory {

    // Key -> Room type
    // Value -> Available count
    private Map<String, Integer> roomAvailability;

    // Constructor
    public RoomInventory() {
        roomAvailability = new HashMap<>();
        initializeInventory();
    }

    // Initialize default values
    private void initializeInventory() {
        roomAvailability.put("Single Room", 5);
        roomAvailability.put("Double Room", 3);
        roomAvailability.put("Suite Room", 2);
    }

    // Get availability
    public Map<String, Integer> getRoomAvailability() {
        return roomAvailability;
    }

    // Update availability
    public void updateAvailability(String roomType, int count) {
        roomAvailability.put(roomType, count);
    }
}

/**
 * ============================================================
 * MAIN CLASS
 * ============================================================
 */
public class UseCase3RoomInventory {

    public static void main(String[] args) {

        // Create Room objects
        Room single = new Room("Single Room", 1, 250, 1500.0);
        Room doubleroom = new Room("Double Room", 2, 400, 2500.0);
        Room suite = new Room("Suite Room", 3, 750, 5000.0);

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Display output
        System.out.println("Hotel Room Inventory Status\n");

        displayRoom(single, inventory);
        displayRoom(doubleroom, inventory);
        displayRoom(suite, inventory);
    }

    // Helper method to print room details
    private static void displayRoom(Room room, RoomInventory inventory) {
        System.out.println(room.getType() + ":");
        System.out.println("Beds: " + room.getBeds());
        System.out.println("Size: " + room.getSize() + " sqft");
        System.out.println("Price per night: " + room.getPrice());

        int available = inventory.getRoomAvailability().get(room.getType());
        System.out.println("Available Rooms: " + available + "\n");
    }
}