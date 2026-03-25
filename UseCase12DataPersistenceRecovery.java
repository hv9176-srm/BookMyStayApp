import java.io.*;
import java.util.*;

class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;

    private int bookingId;
    private String customerName;
    private String roomType;

    public Reservation(int bookingId, String customerName, String roomType) {
        this.bookingId = bookingId;
        this.customerName = customerName;
        this.roomType = roomType;
    }

    public String toString() {
        return "BookingID: " + bookingId +
               ", Name: " + customerName +
               ", Room: " + roomType;
    }
}

class RoomInventory implements Serializable {
    private static final long serialVersionUID = 1L;

    private Map<String, Integer> rooms;

    public RoomInventory() {
        rooms = new HashMap<>();
        rooms.put("Single", 2);
        rooms.put("Double", 2);
        rooms.put("Suite", 1);
    }

    public boolean isAvailable(String type) {
        return rooms.containsKey(type) && rooms.get(type) > 0;
    }

    public void allocateRoom(String type) {
        rooms.put(type, rooms.get(type) - 1);
    }

    public void displayInventory() {
        System.out.println("\nInventory State:");
        for (String type : rooms.keySet()) {
            System.out.println(type + " : " + rooms.get(type));
        }
    }
}

class SystemState implements Serializable {
    private static final long serialVersionUID = 1L;

    List<Reservation> reservations;
    RoomInventory inventory;

    public SystemState(List<Reservation> reservations, RoomInventory inventory) {
        this.reservations = reservations;
        this.inventory = inventory;
    }
}

class PersistenceService {

    private static final String FILE_NAME = "system_state.ser";

    public void save(SystemState state) {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            oos.writeObject(state);
            System.out.println("\nSystem state saved successfully.");

        } catch (IOException e) {
            System.out.println("\nError saving system state: " + e.getMessage());
        }
    }

    public SystemState load() {
        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            SystemState state = (SystemState) ois.readObject();
            System.out.println("\nSystem state loaded successfully.");
            return state;

        } catch (FileNotFoundException e) {
            System.out.println("\nNo saved state found. Starting fresh.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("\nError loading state. Starting with safe defaults.");
        }

        return null;
    }
}

public class UseCase12DataPersistenceRecovery {

    public static void main(String[] args) {

        PersistenceService persistence = new PersistenceService();

        SystemState state = persistence.load();

        List<Reservation> reservations;
        RoomInventory inventory;

        if (state != null) {
            reservations = state.reservations;
            inventory = state.inventory;
        } else {
            reservations = new ArrayList<>();
            inventory = new RoomInventory();
        }

        System.out.println("\n--- Booking Operation ---");

        if (inventory.isAvailable("Single")) {
            Reservation r = new Reservation(1, "Alice", "Single");
            reservations.add(r);
            inventory.allocateRoom("Single");
            System.out.println("Booking Successful: " + r);
        } else {
            System.out.println("Booking Failed: No rooms available.");
        }

        System.out.println("\n--- Current Reservations ---");
        for (Reservation r : reservations) {
            System.out.println(r);
        }

        inventory.displayInventory();

        System.out.println("\n--- Saving System State ---");
        SystemState newState = new SystemState(reservations, inventory);
        persistence.save(newState);

        System.out.println("\n--- Application End ---");
    }
}
