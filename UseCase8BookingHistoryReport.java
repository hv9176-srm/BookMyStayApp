import java.util.*;

class Reservation {
    private int bookingId;
    private String customerName;
    private String roomType;
    private int nights;
    private double pricePerNight;

    public Reservation(int bookingId, String customerName, String roomType, int nights, double pricePerNight) {
        this.bookingId = bookingId;
        this.customerName = customerName;
        this.roomType = roomType;
        this.nights = nights;
        this.pricePerNight = pricePerNight;
    }

    public double getTotalAmount() {
        return nights * pricePerNight;
    }

    public int getBookingId() {
        return bookingId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getRoomType() {
        return roomType;
    }

    public int getNights() {
        return nights;
    }

    @Override
    public String toString() {
        return "BookingID: " + bookingId +
               ", Name: " + customerName +
               ", Room: " + roomType +
               ", Nights: " + nights +
               ", Total: $" + getTotalAmount();
    }
}

// Booking History Class (Stores confirmed bookings)
class BookingHistory {
    private List<Reservation> reservations;

    public BookingHistory() {
        reservations = new ArrayList<>();
    }

    // Add confirmed booking
    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    public List<Reservation> getAllReservations() {
        return reservations;
    }
}

class BookingReportService {

    public void displayAllBookings(List<Reservation> reservations) {
        System.out.println("\n--- Booking History ---");
        for (Reservation r : reservations) {
            System.out.println(r);
        }
    }

    public void generateSummary(List<Reservation> reservations) {
        int totalBookings = reservations.size();
        double totalRevenue = 0;

        for (Reservation r : reservations) {
            totalRevenue += r.getTotalAmount();
        }

        System.out.println("\n--- Booking Summary Report ---");
        System.out.println("Total Bookings: " + totalBookings);
        System.out.println("Total Revenue: $" + totalRevenue);
    }
}


public class UseCase8BookingHistoryReport {

    public static void main(String[] args) {

        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        Reservation r1 = new Reservation(1, "Alice", "Single", 2, 100);
        Reservation r2 = new Reservation(2, "Bob", "Double", 3, 150);
        Reservation r3 = new Reservation(3, "Charlie", "Suite", 1, 300);

        history.addReservation(r1);
        history.addReservation(r2);
        history.addReservation(r3);

        reportService.displayAllBookings(history.getAllReservations());

        reportService.generateSummary(history.getAllReservations());
    }
}
