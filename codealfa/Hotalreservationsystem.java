import java.io.*;
import java.time.LocalDate;
import java.util.*;
public class Hotalreservationsystem{

    private static List<Room> rooms = new ArrayList<>();
    private static List<Booking> bookings = new ArrayList<>();
    private static final String DATA_FILE = "data.txt";

    public static void main(String[] args) {
        loadData();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n HOTEL RESERVATION SYSTEM  ");
            System.out.println("1. view available rooms ");
            System.out.println("2. make reservations  ");
            System.out.println("3.cnacel reservations ");
            System.out.println("4.view all bookings ");
            System.out.println("5.exit");
            System.out.println("choose an options :  ");
            int choice =sc.nextInt();

            switch(choice){
                case 1 :viewavailablerooms();
                case 2 : makereservation(sc);
                case 3 :cancelreservations(sc);
                case 4 :viewbooking();
                case 5 :{
                    saveData();
                    System.out.println("data saved existing ");
                    return;
                }
                default : System.out.println("invalid choice ");

            }

            
        }
    }

private static void viewavailablerooms()
{
    System.out.println(" \n Available rooms are ");
    for(Room room : rooms)
    {
        if(!room.isBooked()) System.out.println(room);
    }
}
private  static void makereservation(Scanner sc)
{
    sc.nextInt();
System.out.println(" \n Enter your name :  ");

String name = sc.nextLine();

viewavailablerooms();
System.out.println(" enter room number to book");
int roomno=sc.nextInt();

Room room = getRoomByNumber(roomno);
if(room == null||room.isBooked())
{
    System.out.println("invalid or allready booked room!");
    return;
}
System.out.println("enter check in date(YYYY-MM-DD):  ");
LocalDate in = LocalDate.parse(sc.next());
System.out.println("enetr check out date(YYYY-MM-DD):  ");
LocalDate out =LocalDate.parse(sc.next());

double total = room.getPrice() * (out.toEpochDay() - in.toEpochDay());
        System.out.println("Total Amount: ₹" + total);
        System.out.println("Simulating payment...");
        System.out.println("Payment successful ");

 room.setBooked(true);
        Booking newBookings = new Booking(name, roomno, in, out,total);
        bookings.add(newBookings);
        System.out.println("Booking confirmed for " + name + "!");
}

    private static void cancelreservations(Scanner sc) {
        sc.nextLine();
        System.out.print("\nEnter your name to cancel booking: ");
        
        String name = sc.nextLine();
        Booking toCancel = null;
        for (Booking b : bookings) {
            if (b.getCustomerName().equalsIgnoreCase(name)) {
                toCancel = b;
                break;
            }
        }

        if(toCancel !=null){
            bookings.remove(toCancel);
            getRoomByNumber(toCancel.getRoomNumber()).setBooked(false);
            System.out.println("booking cancelled successfully.");
        }else{
            System.out.println("no booking found under that name!");

        }

        }
        private  static void viewbooking()
        {
            System.out.println(" \n all bookings ");
            for(Booking b:bookings){
                System.out.println(b);
            }
        }
        private  static Room getRoomByNumber(int number){
            for(Room r: rooms){
                if(r.getRoomNumber() == number) return r;
            }
            return null;
        }
    @SuppressWarnings("unchecked")
    private static void loadData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            rooms = (List<Room>) ois.readObject();
            bookings = (List<Booking>) ois.readObject();
        } catch (Exception e) {
            System.out.println("No saved data found. Creating new room list...");
            rooms.add(new Room(101, "Standard", 2500));
            rooms.add(new Room(102, "Deluxe", 4000));
            rooms.add(new Room(103, "Suite", 7000));
        }
    }
    private static void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(rooms);
            oos.writeObject(bookings);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

    
