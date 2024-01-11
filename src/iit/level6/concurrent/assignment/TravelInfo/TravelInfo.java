package iit.level6.concurrent.assignment.TravelInfo;

public class TravelInfo {

    private final String destination;
    private final String departureLocation;

    public TravelInfo(String destination, String departureLocation) {
        this.destination = destination;
        this.departureLocation = departureLocation;
    }

    public String getDestination() {
        return destination;
    }

    public String getDepartureLocation() {
        return departureLocation;
    }

    @Override
    public String toString() {
        return "TravelInfo[ " + "Destination: " + destination + ", Departure Location: " + departureLocation + "]";
    }

}
