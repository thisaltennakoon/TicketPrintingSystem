package iit.level6.concurrent.assignment;

import iit.level6.concurrent.assignment.PassengerInfo.PassengerInfo;
import iit.level6.concurrent.assignment.TicketInfo.Ticket;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class TicketMachine implements ServiceTicketMachine {

    private String ticketMachineName;

    private int currentPaperLevel;
    private int currentTonerLevel;

    private int numberOfTicketsSold;

    public static int replaceTonerCartridgeCount = 0;
    public static int refillPaperPacksCount = 0;

    public static ArrayList<String> ticketPurchasedList = new ArrayList<>();

    private final ReentrantLock lock = new ReentrantLock();

    private final Condition haveTonerLevel = lock.newCondition();
    private final Condition havePaperLevel = lock.newCondition();
    private final Condition noResource = lock.newCondition();

    public TicketMachine(String ticketMachineName, int currentPaperLevel, int currentTonerLevel) {
        this.ticketMachineName = ticketMachineName;
        this.currentPaperLevel = currentPaperLevel;
        this.currentTonerLevel = currentTonerLevel;
        this.numberOfTicketsSold = 0;
    }

    public Ticket getTicket(String passengerName, String phoneNumber, String emailAddress) {
        Ticket ticket = purchaseTicket(passengerName, phoneNumber, emailAddress);
        printTicket(ticket);
        return ticket;
    }

    @Override
    public Ticket purchaseTicket(String passengerName, String phoneNumber, String emailAddress) {
        lock.lock();
        try {
            Ticket ticket = new Ticket(numberOfTicketsSold + 1, 1000,
                    new PassengerInfo(passengerName, phoneNumber, emailAddress), null);
            numberOfTicketsSold++;
            System.out.println("Ticket number: "+ ticket.getTicketNumber().toString()+" purchased..");
            havePaperLevel.signalAll();
            haveTonerLevel.signalAll();
            return ticket;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void printTicket(Ticket ticket) {
        lock.lock();
        try {
            while (!(currentPaperLevel > 0) || !(currentTonerLevel > ServiceTicketMachine.MINIMUM_TONER_LEVEL)) {
                System.out.println("Passenger " + ticket.getPassengerInfo().getPassengerName() + " waiting for " +
                        "ticket machine " + ticketMachineName);
                noResource.await();
            }
            currentPaperLevel--;
            currentTonerLevel--;
            System.out.println("Ticket number: "+ ticket.getTicketNumber().toString()+" printed..");
            havePaperLevel.signalAll();
            haveTonerLevel.signalAll();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }


    @Override
    public void replaceTonerCartridge() {
        lock.lock();
        try {
            while (currentTonerLevel >= ServiceTicketMachine.MINIMUM_TONER_LEVEL) {
                System.out.println("Toner Technician waiting for refill ticket machine " + ticketMachineName);
                haveTonerLevel.await();
            }

            currentTonerLevel = ServiceTicketMachine.FULL_TONER_LEVEL;
            replaceTonerCartridgeCount++;
            System.out.println("Toner Technician refilled the toner of ticket machine " + ticketMachineName);
            noResource.signalAll();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }

//        if(currentTonerLevel >= ServiceTicketMachine.MINIMUM_TONER_LEVEL){
//            System.out.println("Current toner level is " + currentTonerLevel + " and is above minimum toner level");
//        }else {
//            currentTonerLevel = ServiceTicketMachine.FULL_TONER_LEVEL;
//            replaceTonerCartridgeCount++;
//            System.out.println("Toner Technician refilled the toner of ticket machine " + ticketMachineName);
//        }

    }

    @Override
    public void refillTicketPaper() {
        lock.lock();

        try {

            while ((currentPaperLevel + ServiceTicketMachine.SHEETS_PER_PACK) > ServiceTicketMachine.FULL_PAPER_TRAY) {
                System.out.println("Paper Technician waiting for refill ticket machine " + ticketMachineName);
                havePaperLevel.await();
            }

            currentPaperLevel += ServiceTicketMachine.SHEETS_PER_PACK;
            refillPaperPacksCount++;
            System.out.println("Paper Technician refilled the paper of ticket machine " + ticketMachineName);
            noResource.signalAll();

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
//        if((currentPaperLevel + ServiceTicketMachine.SHEETS_PER_PACK) > ServiceTicketMachine.FULL_PAPER_TRAY) {
//            System.out.println("Ticket machine " + ticketMachineName + " is full");
//        }else {
//            currentPaperLevel += ServiceTicketMachine.SHEETS_PER_PACK;
//            refillPaperPacksCount++;
//            System.out.println("Ticket machine " + ticketMachineName + " refilled");
//        }
    }

    @Override
    public int getPaperLevel() {
        return currentPaperLevel;
    }

    @Override
    public int getTonerLevel() {
        return currentTonerLevel;
    }
}
