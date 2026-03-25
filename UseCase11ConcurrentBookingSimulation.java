import java.util.*;

class BookingRequest {
    String customerName;
    String roomType;

    public BookingRequest(String customerName, String roomType) {
        this.customerName = customerName;
        this.roomType = roomType;
    }
}

class RoomInventory {
    private Map<String, Integer> rooms;

    public RoomInventory() {
        rooms = new HashMap<>();
        rooms.put("Single", 2);
        rooms.put("Double", 2);
        rooms.put("Suite", 1);
    }

    public synchronized boolean allocateRoom(String type) {
        if (!rooms.containsKey(type)) {
            System.out.println(Thread.currentThread().getName() +
                    " -> Invalid room type: " + type);
            return false;
        }

        int available = rooms.get(type);

        if (available > 0) {

            try { Thread.sleep(100); } catch (InterruptedException e) {}

            rooms.put(type, available - 1);

            System.out.println(Thread.currentThread().getName() +
                    " -> Booking SUCCESS for " + type +
                    " | Remaining: " + (available - 1));
            return true;
        } else {
            System.out.println(Thread.currentThread().getName() +
                    " -> Booking FAILED for " + type +
                    " | No rooms available");
            return false;
        }
    }

    public void displayInventory() {
        System.out.println("\nFinal Inventory:");
        for (String type : rooms.keySet()) {
            System.out.println(type + " : " + rooms.get(type));
        }
    }
}

// Shared Booking Queue (Thread-Safe)
class BookingQueue {
    private Queue<BookingRequest> queue = new LinkedList<>();

    // Add request
    public synchronized void addRequest(BookingRequest request) {
        queue.add(request);
    }

    // Get request safely
    public synchronized BookingRequest getRequest() {
        if (queue.isEmpty()) return null;
        return queue.poll();
    }
}

// Worker Thread (Processes Bookings)
class BookingProcessor extends Thread {
    private BookingQueue queue;
    private RoomInventory inventory;

    public BookingProcessor(String name, BookingQueue queue, RoomInventory inventory) {
        super(name);
        this.queue = queue;
        this.inventory = inventory;
    }

    public void run() {
        while (true) {
            BookingRequest request;

            // synchronized access to queue
            synchronized (queue) {
                request = queue.getRequest();
            }

            if (request == null) break;

            // Critical section handled inside inventory
            inventory.allocateRoom(request.roomType);
        }
    }
}

public class UseCase11ConcurrentBookingSimulation {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        BookingQueue queue = new BookingQueue();

        queue.addRequest(new BookingRequest("Alice", "Single"));
        queue.addRequest(new BookingRequest("Bob", "Single"));
        queue.addRequest(new BookingRequest("Charlie", "Single")); // should fail
        queue.addRequest(new BookingRequest("David", "Suite"));
        queue.addRequest(new BookingRequest("Eve", "Suite")); // should fail

        BookingProcessor t1 = new BookingProcessor("Thread-1", queue, inventory);
        BookingProcessor t2 = new BookingProcessor("Thread-2", queue, inventory);
        BookingProcessor t3 = new BookingProcessor("Thread-3", queue, inventory);

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

        inventory.displayInventory();
    }
}
