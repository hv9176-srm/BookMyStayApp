/**
 * UseCase5BookingRequestQueue
 * 
 * This class demonstrates handling booking requests using a Queue
 * to ensure First-Come-First-Served (FIFO) order.
 * 
 * No inventory updates are performed at this stage.
 * 
 * @author AKHILESWAR D
 * @version 5.0
 */

import java.util.*;

// Reservation Class (represents a booking request)
class Reservation {
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

    public void display() {
        System.out.println("Guest: " + guestName + ", Room Type: " + roomType);
    }
}

// Booking Queue Class
class BookingRequestQueue {

    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    // Add booking request (enqueue)
    public void addRequest(Reservation reservation) {
        queue.add(reservation);
        System.out.println("Request added for " + reservation.getGuestName());
    }

    // Display all requests (without removing)
    public void displayQueue() {
        System.out.println("\n----- Booking Request Queue -----");

        for (Reservation r : queue) {
            r.display();
        }
    }
}

// Main Class
public class UseCase5BookingRequestQueue {

    public static void main(String[] args) {

        System.out.println("=======================================");
        System.out.println(" Book My Stay - Hotel Booking System");
        System.out.println(" Version 5.0");
        System.out.println("=======================================\n");

        // Create booking queue
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Add booking requests (FIFO order)
        bookingQueue.addRequest(new Reservation("Alice", "Single Room"));
        bookingQueue.addRequest(new Reservation("Bob", "Double Room"));
        bookingQueue.addRequest(new Reservation("Charlie", "Suite Room"));

        // Display queue
        bookingQueue.displayQueue();

        System.out.println("\nAll requests stored in FIFO order.");
        System.out.println("No rooms allocated yet (inventory unchanged).");
    }
}
