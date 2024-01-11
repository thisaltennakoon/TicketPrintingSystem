package iit.level6.concurrent.assignment;

import java.util.Random;

public class Passenger implements Runnable {

    private final TicketMachine machine;
    private final String passengerName;
    private String phoneNumber;
    private String emailAddress;
    private final String arrivalLocation;
    private final String departureLocation;

    Random random = new Random();

    public Passenger(TicketMachine machine, String passengerName, String phoneNumber, String emailAddress,
                     String arrivalLocation, String departureLocation) {
        this.machine = machine;
        this.passengerName = passengerName;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.arrivalLocation = arrivalLocation;
        this.departureLocation = departureLocation;
    }

    @Override
    public void run() {

//        machine.purchasingTicket(this.ticket);
//        machine.printTicket(this.ticket);
        TicketPrintingSystem.Ticket ticket = machine.getTicket(this.passengerName, this.phoneNumber, this.emailAddress, this.arrivalLocation,
                this.departureLocation);
//        System.out.println("Passenger " + this.ticket.getPassengerInfo().getPassengerName() + " got " + ticket);
        try {
            Thread.sleep(random.nextInt(1000) + 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
//        for(int i =0; i < TicketMachine.ticketPurchasedList.size(); i++){
//            if(TicketMachine.ticketPurchasedList.get(i).equals(Thread.currentThread().getName())){
//                machine.printTicket(this.ticket);
//                System.out.println("Passenger " + this.ticket.getPassengerInfo().getPassengerName() + " got " + ticket);
////                    Thread.sleep(random.nextInt(1000) + 1000);
//            }
//        }
    }
}
