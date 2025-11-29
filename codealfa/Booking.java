import java.io.Serializable;
import java.time.LocalDate;

public class Booking implements Serializable {
    private String customerName;
    private int roomNumber;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private double totalPrice;

    public Booking(String customerName, int roomNumber, LocalDate checkIn, LocalDate checkOut, double totalPrice) {
        this.customerName = customerName;
        this.roomNumber = roomNumber;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.totalPrice = totalPrice;
    }

    public String getCustomerName() { return customerName; }
    public int getRoomNumber() { return roomNumber; }

    @Override
    public String toString() {
        return "Booking [Name: " + customerName +
               ", Room: " + roomNumber +
               ", From: " + checkIn +
               " To: " + checkOut +
               ", Total: ₹" + totalPrice + "]";
    }
}
