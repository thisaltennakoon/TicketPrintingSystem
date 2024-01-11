package concurrentAssignment;

public class Ticket {

    private final Integer ticketNumber;
    private final float ticketPrice;
    private final String passengerName;
    private String phoneNumber;
    private String emailAddress;
    private final String arrivalLocation;
    private final String departureLocation;

    public Ticket(int ticketNumber, float ticketPrice, String passengerName, String phoneNumber, String emailAddress,
                  String arrivalLocation, String departureLocation) {
        this.ticketNumber = ticketNumber;
        this.ticketPrice = ticketPrice;
        this.passengerName = passengerName;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.arrivalLocation = arrivalLocation;
        this.departureLocation = departureLocation;
    }

    public Integer getticketNumber() {
        return ticketNumber;
    }

    public float getTicketPrice() {
        return ticketPrice;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getArrivalLocation() {
        return arrivalLocation;
    }

    public String getDepartureLocation() {
        return departureLocation;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "ticketNumber=" + ticketNumber +
                ", ticketPrice=" + ticketPrice +
                ", passengerName='" + passengerName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", arrivalLocation='" + arrivalLocation + '\'' +
                ", departureLocation='" + departureLocation + '\'' +
                '}';
    }
}
