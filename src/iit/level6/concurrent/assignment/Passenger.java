package iit.level6.concurrent.assignment;

import iit.level6.concurrent.assignment.TicketInfo.Ticket;

import java.util.Random;

public class Passenger implements Runnable{

    private final TicketMachine machine;

    private final Ticket ticket;

    Random random = new Random();

    public Passenger(TicketMachine machine, Ticket ticket) {
        this.machine = machine;
        this.ticket = ticket;
    }

    @Override
    public void run(){

        machine.purchasingTicket(this.ticket);

        try{
            machine.printTicket(this.ticket);
            System.out.println("Passenger " + this.ticket.getPassengerInfo().getPassengerName() + " got " + ticket);
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
