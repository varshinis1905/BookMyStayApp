import java.util.*;

/**
 * Use Case 7: Add-On Service Selection
 * Demonstrates adding optional services to reservations
 */
public class UseCase7AddOnServices {

    /**
     * CLASS - Reservation
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
     * CLASS - AddOnService
     */
    static class AddOnService {
        private String serviceName;
        private double cost;

        public AddOnService(String serviceName, double cost) {
            this.serviceName = serviceName;
            this.cost = cost;
        }

        public String getServiceName() {
            return serviceName;
        }

        public double getCost() {
            return cost;
        }
    }

    /**
     * CLASS - AddOnServiceManager
     * Maps Reservation → List of Services
     */
    static class AddOnServiceManager {

        private Map<String, List<AddOnService>> serviceMap = new HashMap<>();

        // Add service to reservation
        public void addService(String reservationId, AddOnService service) {
            serviceMap.putIfAbsent(reservationId, new ArrayList<>());
            serviceMap.get(reservationId).add(service);
        }

        // Get services for reservation
        public List<AddOnService> getServices(String reservationId) {
            return serviceMap.getOrDefault(reservationId, new ArrayList<>());
        }

        // Calculate total cost
        public double calculateTotalCost(String reservationId) {
            double total = 0;
            for (AddOnService s : getServices(reservationId)) {
                total += s.getCost();
            }
            return total;
        }

        // Display services
        public void displayServices(String reservationId) {
            List<AddOnService> services = getServices(reservationId);

            if (services.isEmpty()) {
                System.out.println("No add-on services selected.");
                return;
            }

            System.out.println("Selected Services:");
            for (AddOnService s : services) {
                System.out.println("- " + s.getServiceName() + " : ₹" + s.getCost());
            }

            System.out.println("Total Add-On Cost: ₹" + calculateTotalCost(reservationId));
        }
    }

    /**
     * MAIN METHOD
     */
    public static void main(String[] args) {

        System.out.println("=== Add-On Service Selection ===\n");

        // Create reservations (already confirmed in Use Case 6)
        Reservation r1 = new Reservation("R101", "Abhi", "Single");
        Reservation r2 = new Reservation("R102", "Subha", "Double");

        // Create service manager
        AddOnServiceManager manager = new AddOnServiceManager();

        // Create services
        AddOnService wifi = new AddOnService("WiFi", 200);
        AddOnService breakfast = new AddOnService("Breakfast", 300);
        AddOnService spa = new AddOnService("Spa", 1000);

        // Add services to reservations
        manager.addService(r1.getReservationId(), wifi);
        manager.addService(r1.getReservationId(), breakfast);

        manager.addService(r2.getReservationId(), spa);

        // Display results
        System.out.println("Reservation ID: " + r1.getReservationId());
        System.out.println("Guest: " + r1.getGuestName());
        manager.displayServices(r1.getReservationId());

        System.out.println("\n-------------------------\n");

        System.out.println("Reservation ID: " + r2.getReservationId());
        System.out.println("Guest: " + r2.getGuestName());
        manager.displayServices(r2.getReservationId());
    }
}