import java.util.*;

/**
 * Use Case 8: Booking History & Reporting
 * Stores confirmed bookings and generates reports
 */
public class UseCase8BookingHistory {

    /**
     * CLASS - Reservation
     * Represents a confirmed booking
     */
    static class Reservation {
        private String reservationId;
        private String guestName;
        private String roomType;

        public Reservation(String reservationId, String guestName, String roomType) {
            this.reservationId = reservationId;
            this.guestName = guestName;
            this.roomType = roomType;
        }

        public String getReservationId() {
            return reservationId;
        }

        public String getGuestName() {
            return guestName;
        }

        public String getRoomType() {
            return roomType;
        }
    }

    /**
     * CLASS - BookingHistory
     * Stores confirmed reservations in order
     */
    static class BookingHistory {

        private List<Reservation> historyList = new ArrayList<>();

        // Add confirmed booking
        public void addReservation(Reservation r) {
            historyList.add(r);
        }

        // Get all bookings
        public List<Reservation> getAllBookings() {
            return historyList;
        }
    }

    /**
     * CLASS - BookingReportService
     * Generates reports from booking history
     */
    static class BookingReportService {

        // Display all bookings
        public void displayAllBookings(List<Reservation> bookings) {
            System.out.println("=== Booking History ===\n");

            for (Reservation r : bookings) {
                System.out.println("ID: " + r.getReservationId()
                        + " | Guest: " + r.getGuestName()
                        + " | Room: " + r.getRoomType());
            }
        }

        // Generate summary report
        public void generateSummary(List<Reservation> bookings) {

            Map<String, Integer> roomCount = new HashMap<>();

            for (Reservation r : bookings) {
                String type = r.getRoomType();
                roomCount.put(type, roomCount.getOrDefault(type, 0) + 1);
            }

            System.out.println("\n=== Booking Summary Report ===\n");

            for (String type : roomCount.keySet()) {
                System.out.println(type + " Rooms Booked: " + roomCount.get(type));
            }

            System.out.println("\nTotal Bookings: " + bookings.size());
        }
    }

    /**
     * MAIN METHOD
     */
    public static void main(String[] args) {

        System.out.println("=== Booking History & Reporting ===\n");

        // Create booking history
        BookingHistory history = new BookingHistory();

        // Add confirmed reservations (from previous use cases)
        history.addReservation(new Reservation("R101", "Abhi", "Single"));
        history.addReservation(new Reservation("R102", "Subha", "Double"));
        history.addReservation(new Reservation("R103", "Ram", "Single"));
        history.addReservation(new Reservation("R104", "Divya", "Suite"));

        // Create report service
        BookingReportService reportService = new BookingReportService();

        // Display all bookings
        reportService.displayAllBookings(history.getAllBookings());

        // Generate summary report
        reportService.generateSummary(history.getAllBookings());
    }
}