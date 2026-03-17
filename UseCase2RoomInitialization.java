/**
 * UseCase2RoomInitialization
 * 
 * This class demonstrates object-oriented modeling using abstraction,
 * inheritance, and polymorphism for a Hotel Booking System.
 * 
 * @author AKHILESWAR REDDY DUGGEMPUDI
 * @version 2.1
 */

// Abstract Class
abstract class Room {
    protected String roomType;
    protected int beds;
    protected double price;

    // Constructor
    public Room(String roomType, int beds, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.price = price;
    }

    // Method to display room details
    public void displayDetails() {
        System.out.println("Room Type: " + roomType);
        System.out.println("Beds: " + beds);
        System.out.println("Price: ₹" + price);
    }
}

// Single Room
class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 2000);
    }
}

// Double Room
class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 3500);
    }
}

// Suite Room
class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 6000);
    }
}

// Main Class
public class UseCase2RoomInitialization {

    public static void main(String[] args) {

        System.out.println("=======================================");
        System.out.println(" Book My Stay - Hotel Booking System");
        System.out.println(" Version 2.1");
        System.out.println("=======================================\n");

        // Create room objects (Polymorphism)
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Static availability
        int singleAvailable = 5;
        int doubleAvailable = 3;
        int suiteAvailable = 2;

        // Display details
        System.out.println("----- Room Details & Availability -----\n");

        single.displayDetails();
        System.out.println("Available: " + singleAvailable + "\n");

        doubleRoom.displayDetails();
        System.out.println("Available: " + doubleAvailable + "\n");

        suite.displayDetails();
        System.out.println("Available: " + suiteAvailable + "\n");

        System.out.println("Application executed successfully!");
    }
}
