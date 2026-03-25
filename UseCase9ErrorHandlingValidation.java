import java.util.*;

class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

class Reservation {
    private String customerName;
    private String roomType;
    private int nights;

    public Reservation(String customerName, String roomType, int nights) {
        this.customerName = customerName;
        this.roomType = roomType;
        this.nights = nights;
    }

    public String toString() {
        return "Customer: " + customerName +
               ", Room Type: " + roomType +
               ", Nights: " + nights;
    }
}

// Inventory Class (Manages Room Availability)
class RoomInventory {
    private Map<String, Integer> rooms;

    public RoomInventory() {
        rooms = new HashMap<>();
        rooms.put("Single", 2);
        rooms.put("Double", 2);
        rooms.put("Suite", 1);
    }

    public void validateRoomType(String roomType) throws InvalidBookingException {
        if (!rooms.containsKey(roomType)) {
            throw new InvalidBookingException("Invalid room type: " + roomType);
        }
    }

    public void checkAvailability(String roomType) throws InvalidBookingException {
        int available = rooms.get(roomType);
        if (available <= 0) {
            throw new InvalidBookingException("No rooms available for type: " + roomType);
        }
    }

    public void allocateRoom(String roomType) {
        rooms.put(roomType, rooms.get(roomType) - 1);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Room Availability:");
        for (String type : rooms.keySet()) {
            System.out.println(type + " : " + rooms.get(type));
        }
    }
}

class BookingValidator {

    public void validate(String name, String roomType, int nights, RoomInventory inventory)
            throws InvalidBookingException {

        if (name == null || name.trim().isEmpty()) {
            throw new InvalidBookingException("Customer name cannot be empty.");
        }

        if (nights <= 0) {
            throw new InvalidBookingException("Number of nights must be greater than 0.");
        }

        inventory.validateRoomType(roomType);
        inventory.checkAvailability(roomType);
    }
}

class BookingService {
    private RoomInventory inventory;
    private List<Reservation> reservations;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
        this.reservations = new ArrayList<>();
    }

    public void bookRoom(String name, String roomType, int nights) {
        BookingValidator validator = new BookingValidator();

        try {
            // Validate first (Fail-Fast)
            validator.validate(name, roomType, nights, inventory);

            inventory.allocateRoom(roomType);

            Reservation r = new Reservation(name, roomType, nights);
            reservations.add(r);

            System.out.println("\nBooking Successful!");
            System.out.println(r);

        } catch (InvalidBookingException e) {

            System.out.println("\nBooking Failed: " + e.getMessage());
        }
    }

    public void showReservations() {
        System.out.println("\n--- Confirmed Reservations ---");
        for (Reservation r : reservations) {
            System.out.println(r);
        }
    }
}

public class UseCase9ErrorHandlingValidation {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        BookingService service = new BookingService(inventory);

        service.bookRoom("Alice", "Single", 2);

        service.bookRoom("Bob", "Deluxe", 1);

        service.bookRoom("Charlie", "Double", 0);

        service.bookRoom("David", "Suite", 1);
        service.bookRoom("Eve", "Suite", 1); // should fail

        service.bookRoom("", "Single", 1);
      
        service.showReservations();
        inventory.displayInventory();
    }
}
