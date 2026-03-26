import java.util.HashMap;
import java.util.Map;

/**
 * ============================================================
 * MAIN CLASS - UseCase4RoomSearch
 * ============================================================
 *
 * Demonstrates read-only room search functionality.
 */
public class UseCase4RoomSearch {

    public static void main(String[] args) {

        // Step 1: Create Room objects (Domain Model)
        Room singleRoom = new Room("Single", 100.0, "1 Bed, Free WiFi");
        Room doubleRoom = new Room("Double", 180.0, "2 Beds, Free WiFi, TV");
        Room suiteRoom = new Room("Suite", 300.0, "Luxury Suite, King Bed, Ocean View");

        // Step 2: Setup Inventory (State Holder)
        RoomInventory inventory = new RoomInventory();
        inventory.setRoomAvailability("Single", 3);
        inventory.setRoomAvailability("Double", 0); // unavailable
        inventory.setRoomAvailability("Suite", 2);

        // Step 3: Perform Search (Read-only)
        RoomSearchService searchService = new RoomSearchService();
        searchService.searchAvailableRooms(inventory, singleRoom, doubleRoom, suiteRoom);
    }
}

/**
 * ============================================================
 * CLASS - Room
 * ============================================================
 * Represents room details (Domain Model)
 */
class Room {
    private String type;
    private double price;
    private String amenities;

    public Room(String type, double price, String amenities) {
        this.type = type;
        this.price = price;
        this.amenities = amenities;
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public String getAmenities() {
        return amenities;
    }

    public void displayDetails() {
        System.out.println("Room Type: " + type);
        System.out.println("Price: $" + price);
        System.out.println("Amenities: " + amenities);
        System.out.println("----------------------------------");
    }
}

/**
 * ============================================================
 * CLASS - RoomInventory
 * ============================================================
 * Centralized inventory (State Holder)
 */
class RoomInventory {
    private Map<String, Integer> availability = new HashMap<>();

    public void setRoomAvailability(String roomType, int count) {
        availability.put(roomType, count);
    }

    public Map<String, Integer> getRoomAvailability() {
        return availability; // read-only usage expected
    }
}

/**
 * ============================================================
 * CLASS - RoomSearchService
 * ============================================================
 * Handles read-only search functionality
 */
class RoomSearchService {

    public void searchAvailableRooms(
            RoomInventory inventory,
            Room singleRoom,
            Room doubleRoom,
            Room suiteRoom) {

        Map<String, Integer> availability = inventory.getRoomAvailability();

        System.out.println("===== Available Rooms =====");

        // Defensive check: null-safe access
        if (availability.getOrDefault("Single", 0) > 0) {
            System.out.println("Available: " + availability.get("Single") + " Single Rooms");
            singleRoom.displayDetails();
        }

        if (availability.getOrDefault("Double", 0) > 0) {
            System.out.println("Available: " + availability.get("Double") + " Double Rooms");
            doubleRoom.displayDetails();
        }

        if (availability.getOrDefault("Suite", 0) > 0) {
            System.out.println("Available: " + availability.get("Suite") + " Suite Rooms");
            suiteRoom.displayDetails();
        }

        System.out.println("===== End of Search =====");
    }
}