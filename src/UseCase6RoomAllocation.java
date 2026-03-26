import java.util.*;

/**
 * Use Case 6: Reservation Confirmation & Room Allocation
 * Ensures no double booking using Set and HashMap
 */
public class UseCase6RoomAllocation {

    /**
     * CLASS - Reservation (Booking Request)
     */
    static class Reservation {
        private String guestName;
        private String roomType;

        public Reservation(String guestName, String roomType) {
            this.guestName = guestName;
            this.roomType = roomType;
        }

        public String getGuestName() {
            return guestName;
        }

        public String getRoomType() {
            return roomType;
        }
    }

    /**
     * CLASS - BookingRequestQueue (FIFO)
     */
    static class BookingRequestQueue {
        private Queue<Reservation> requestQueue = new LinkedList<>();

        public void addRequest(Reservation r) {
            requestQueue.offer(r);
        }

        public Reservation getNextRequest() {
            return requestQueue.poll();
        }

        public boolean hasRequests() {
            return !requestQueue.isEmpty();
        }
    }

    /**
     * CLASS - InventoryService
     * Manages available rooms
     */
    static class InventoryService {
        private Map<String, Integer> roomInventory = new HashMap<>();

        public InventoryService() {
            // Initial room availability
            roomInventory.put("Single", 2);
            roomInventory.put("Double", 1);
            roomInventory.put("Suite", 1);
        }

        public boolean isAvailable(String roomType) {
            return roomInventory.getOrDefault(roomType, 0) > 0;
        }

        public void allocateRoom(String roomType) {
            roomInventory.put(roomType, roomInventory.get(roomType) - 1);
        }

        public void displayInventory() {
            System.out.println("\nRemaining Inventory:");
            for (String type : roomInventory.keySet()) {
                System.out.println(type + " Rooms: " + roomInventory.get(type));
            }
        }
    }

    /**
     * CLASS - BookingService
     * Handles allocation & confirmation
     */
    static class BookingService {

        private Set<String> allocatedRoomIds = new HashSet<>();
        private Map<String, Set<String>> roomTypeMap = new HashMap<>();
        private int roomCounter = 1;

        public void processReservation(Reservation r, InventoryService inventory) {

            String type = r.getRoomType();

            // Check availability
            if (!inventory.isAvailable(type)) {
                System.out.println("❌ No " + type + " rooms available for " + r.getGuestName());
                return;
            }

            // Generate unique room ID
            String roomId = type.substring(0, 1).toUpperCase() + roomCounter++;

            // Ensure uniqueness (extra safety)
            while (allocatedRoomIds.contains(roomId)) {
                roomId = type.substring(0, 1).toUpperCase() + roomCounter++;
            }

            // Allocate room
            allocatedRoomIds.add(roomId);

            // Map room type → room IDs
            roomTypeMap.putIfAbsent(type, new HashSet<>());
            roomTypeMap.get(type).add(roomId);

            // Update inventory
            inventory.allocateRoom(type);

            // Confirm booking
            System.out.println("✅ Booking Confirmed:");
            System.out.println("Guest: " + r.getGuestName());
            System.out.println("Room Type: " + type);
            System.out.println("Room ID: " + roomId + "\n");
        }
    }

    /**
     * MAIN METHOD
     */
    public static void main(String[] args) {

        System.out.println("=== Reservation Confirmation & Room Allocation ===\n");

        // Initialize queue
        BookingRequestQueue queue = new BookingRequestQueue();

        // Add booking requests
        queue.addRequest(new Reservation("Abhi", "Single"));
        queue.addRequest(new Reservation("Subha", "Double"));
        queue.addRequest(new Reservation("Ram", "Single"));
        queue.addRequest(new Reservation("Divya", "Suite"));
        queue.addRequest(new Reservation("Kumar", "Single")); // extra request

        // Initialize services
        InventoryService inventory = new InventoryService();
        BookingService bookingService = new BookingService();

        // Process queue
        while (queue.hasRequests()) {
            Reservation r = queue.getNextRequest();
            bookingService.processReservation(r, inventory);
        }

        // Show remaining inventory
        inventory.displayInventory();
    }
}