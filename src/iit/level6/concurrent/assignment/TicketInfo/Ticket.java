package iit.level6.concurrent.assignment.TicketInfo;

import iit.level6.concurrent.assignment.PassengerInfo.PassengerInfo;
import iit.level6.concurrent.assignment.TravelInfo.TravelInfo;

import java.math.BigDecimal;

public class Ticket {

    private final int ticketNUmber;
    private final BigDecimal ticketPrice;
    private final PassengerInfo passengerInfo;
    private final TravelInfo travelInfo;

    public Ticket(int ticketNUmber, BigDecimal ticketPrice, PassengerInfo passengerInfo, TravelInfo travelInfo) {
        this.ticketNUmber = ticketNUmber;
        this.ticketPrice = ticketPrice;
        this.passengerInfo = passengerInfo;
        this.travelInfo = travelInfo;
    }

    public int getTicketNUmber() {
        return ticketNUmber;
    }

    public BigDecimal getTicketPrice() {
        return ticketPrice;
    }

    public PassengerInfo getPassengerInfo() {
        return passengerInfo;
    }

    public TravelInfo getTravelInfo() {
        return travelInfo;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "ticketNUmber=" + ticketNUmber +
                ", ticketPrice=" + ticketPrice +
                ", passengerInfo=" + passengerInfo +
                ", travelInfo=" + travelInfo +
                '}';
    }
}
