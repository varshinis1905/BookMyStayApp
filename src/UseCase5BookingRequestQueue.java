import java.util.Queue;
import java.util.LinkedList;

/**
 * Use Case 5: Booking Request (FIFO)
 * Single-file implementation
 */
public class UseCase5BookingRequestQueue {

    /**
     * CLASS - Reservation
     * Represents a booking request made by a guest
     */
    static class Reservation {

        private String guestName;
        private String roomType;

        // Constructor
        public Reservation(String guestName, String roomType) {
            this.guestName = guestName;
            this.roomType = roomType;
        }

        // Getter methods
        public String getGuestName() {
            return guestName;
        }

        public String getRoomType() {
            return roomType;
        }
    }

    /**
     * CLASS - BookingRequestQueue
     * Manages booking requests using FIFO queue
     */
    static class BookingRequestQueue {

        private Queue<Reservation> requestQueue;

        // Constructor
        public BookingRequestQueue() {
            requestQueue = new LinkedList<>();
        }

        // Add request
        public void addRequest(Reservation reservation) {
            requestQueue.offer(reservation);
        }

        // Get next request (FIFO)
        public Reservation getNextRequest() {
            return requestQueue.poll();
        }

        // Check if queue has requests
        public boolean hasPendingRequests() {
            return !requestQueue.isEmpty();
        }
    }

    /**
     * MAIN METHOD
     */
    public static void main(String[] args) {

        System.out.println("=== Booking Request Queue (FIFO) ===\n");

        // Create queue
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Create booking requests
        Reservation r1 = new Reservation("Abhi", "Single");
        Reservation r2 = new Reservation("Subha", "Double");
        Reservation r3 = new Reservation("Vanmathi", "Suite");

        // Add requests to queue
        bookingQueue.addRequest(r1);
        bookingQueue.addRequest(r2);
        bookingQueue.addRequest(r3);

        // Process requests in FIFO order
        System.out.println("Processing Requests:\n");

        while (bookingQueue.hasPendingRequests()) {
            Reservation r = bookingQueue.getNextRequest();

            System.out.println("Guest: " + r.getGuestName() +
                    " | Room Type: " + r.getRoomType());
        }
    }
}