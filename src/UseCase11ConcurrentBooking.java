import java.util.*;

/**
 * ============================================================
 * CLASS - RoomInventory (Thread Safe)
 * ============================================================
 */
class RoomInventory {

    private Map<String, Integer> availability;

    public RoomInventory() {
        availability = new HashMap<>();
        availability.put("Single Room", 2);
        availability.put("Double Room", 2);
    }

    // synchronized method (critical section)
    public synchronized boolean bookRoom(String roomType) {

        int count = availability.getOrDefault(roomType, 0);

        if (count > 0) {
            availability.put(roomType, count - 1);
            System.out.println(Thread.currentThread().getName() +
                    " booked " + roomType + " | Remaining: " + (count - 1));
            return true;
        } else {
            System.out.println(Thread.currentThread().getName() +
                    " FAILED to book " + roomType + " (No rooms left)");
            return false;
        }
    }

    public void showInventory() {
        System.out.println("Final Inventory: " + availability);
    }
}

/**
 * ============================================================
 * CLASS - BookingRequest
 * ============================================================
 */
class BookingRequest {
    String guestName;
    String roomType;

    public BookingRequest(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

/**
 * ============================================================
 * CLASS - BookingProcessor (Thread)
 * ============================================================
 */
class BookingProcessor extends Thread {

    private Queue<BookingRequest> queue;
    private RoomInventory inventory;

    public BookingProcessor(String name, Queue<BookingRequest> queue, RoomInventory inventory) {
        super(name);
        this.queue = queue;
        this.inventory = inventory;
    }

    @Override
    public void run() {

        while (true) {

            BookingRequest request;

            // synchronized queue access
            synchronized (queue) {
                if (queue.isEmpty()) {
                    break;
                }
                request = queue.poll();
            }

            // process booking
            inventory.bookRoom(request.roomType);

            try {
                Thread.sleep(100); // simulate delay
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

/**
 * ============================================================
 * MAIN CLASS
 * ============================================================
 */
public class UseCase11ConcurrentBooking {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();

        // Shared booking queue
        Queue<BookingRequest> queue = new LinkedList<>();

        // Simulated guest requests
        queue.add(new BookingRequest("Guest1", "Single Room"));
        queue.add(new BookingRequest("Guest2", "Single Room"));
        queue.add(new BookingRequest("Guest3", "Single Room")); // extra
        queue.add(new BookingRequest("Guest4", "Double Room"));
        queue.add(new BookingRequest("Guest5", "Double Room"));
        queue.add(new BookingRequest("Guest6", "Double Room")); // extra

        // Multiple threads (guests)
        Thread t1 = new BookingProcessor("Thread-1", queue, inventory);
        Thread t2 = new BookingProcessor("Thread-2", queue, inventory);
        Thread t3 = new BookingProcessor("Thread-3", queue, inventory);

        System.out.println("---- Concurrent Booking Simulation ----\n");

        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\n---- Final Status ----");
        inventory.showInventory();
    }
}