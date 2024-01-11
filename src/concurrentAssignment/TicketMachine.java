package concurrentAssignment;

import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class TicketMachine implements ServiceTicketMachine {

    private String ticketMachineName;
    private int currentPaperLevel;
    private int currentTonerLevel;
    private int numberOfTicketsSold;
    public static int replaceTonerCartridgeCount = 0;
    public static int refillPaperPacksCount = 0;

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

    public Ticket getTicket(String passengerName, String phoneNumber, String emailAddress, String arrivalLocation,
                            String departureLocation) {
        Ticket ticket = purchaseTicket(passengerName, phoneNumber, emailAddress, arrivalLocation, departureLocation);
        printTicket(ticket);
        System.out.println("Passenger " + ticket.getPassengerName() + " got " + ticket);
        return ticket;
    }

    @Override
    public Ticket purchaseTicket(String passengerName, String phoneNumber, String emailAddress, String arrivalLocation,
                                 String departureLocation) {
        lock.lock();
        try {
            Ticket ticket = new Ticket(numberOfTicketsSold + 1, 1000,
                    passengerName, phoneNumber, emailAddress, arrivalLocation, departureLocation);
            numberOfTicketsSold++;
            System.out.println("Ticket number: " + ticket.getticketNumber().toString() + " purchased by " +
                    Thread.currentThread().getName());
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
            Thread.sleep(new Random().nextInt(1000) + 1000);
            while (!(currentPaperLevel > 0) || !(currentTonerLevel > ServiceTicketMachine.MIN_TONER_LEVEL)) {
                System.out.println("Passenger: " + ticket.getPassengerName() + " waiting for " +
                        "ticket machine:" + ticketMachineName);
                noResource.await();
            }
            currentPaperLevel--;
            currentTonerLevel--;
            System.out.println("Ticket number: " + ticket.getticketNumber().toString() + " printed by " +
                    Thread.currentThread().getName());
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
            while (currentTonerLevel >= ServiceTicketMachine.MIN_TONER_LEVEL) {
                System.out.println("Current toner level is " + currentTonerLevel + " and toner refilling is not " +
                        "needed for the ticket machine: " + ticketMachineName);
                haveTonerLevel.await();
            }
            currentTonerLevel = ServiceTicketMachine.MAX_TONER_LEVEL;
            replaceTonerCartridgeCount++;
            System.out.println("Toner Technician refilled the toner of ticket machine: " + ticketMachineName);
            noResource.signalAll();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void refillTicketPaper() {
        lock.lock();
        try {
            while ((currentPaperLevel + ServiceTicketMachine.SHEETS_PER_PACK) > ServiceTicketMachine.FULL_PAPER_TRAY) {
                System.out.println("Machine has enugh papaer and the paper technician is waiting to refill ticket " +
                        "machine " + ticketMachineName);
                havePaperLevel.await();
            }
            currentPaperLevel += ServiceTicketMachine.SHEETS_PER_PACK;
            refillPaperPacksCount++;
            System.out.println("Paper Technician refilled the paper of ticket machine: " + ticketMachineName);
            noResource.signalAll();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
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
