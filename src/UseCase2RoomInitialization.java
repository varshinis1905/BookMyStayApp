public class UseCase2RoomInitialization{

    // ===== ABSTRACT CLASS =====
    static abstract class Room {

        protected int numberOfBeds;
        protected int squareFeet;
        protected double pricePerNight;

        public Room(int numberOfBeds, int squareFeet, double pricePerNight) {
            this.numberOfBeds = numberOfBeds;
            this.squareFeet = squareFeet;
            this.pricePerNight = pricePerNight;
        }

        public void displayRoomDetails() {
            System.out.println("Beds: " + numberOfBeds);
            System.out.println("Size: " + squareFeet + " sq.ft");
            System.out.println("Price per night: ₹" + pricePerNight);
        }
    }

    // ===== SINGLE ROOM =====
    static class SingleRoom extends Room {
        public SingleRoom() {
            super(1, 250, 1500.0);
        }
    }

    // ===== DOUBLE ROOM =====
    static class DoubleRoom extends Room {
        public DoubleRoom() {
            super(2, 400, 2500.0);
        }
    }

    // ===== SUITE ROOM =====
    static class SuiteRoom extends Room {
        public SuiteRoom() {
            super(3, 700, 5000.0);
        }
    }

    // ===== MAIN METHOD =====
    public static void main(String[] args) {

        // Create room objects (Polymorphism)
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Static availability
        int singleAvailable = 10;
        int doubleAvailable = 5;
        int suiteAvailable = 2;

        // Display details
        System.out.println("=== Single Room ===");
        single.displayRoomDetails();
        System.out.println("Available: " + singleAvailable);

        System.out.println("\n=== Double Room ===");
        doubleRoom.displayRoomDetails();
        System.out.println("Available: " + doubleAvailable);

        System.out.println("\n=== Suite Room ===");
        suite.displayRoomDetails();
        System.out.println("Available: " + suiteAvailable);
    }
}