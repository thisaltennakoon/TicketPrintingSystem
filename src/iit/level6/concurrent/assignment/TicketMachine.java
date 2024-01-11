package iit.level6.concurrent.assignment;

import iit.level6.concurrent.assignment.TicketInfo.Ticket;

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

    @Override
    public void printTicket(Ticket ticket) {
        lock.lock();
        try{
            while (currentPaperLevel <= 0 || currentTonerLevel <= ServiceTicketMachine.MINIMUM_TONER_LEVEL){
                System.out.println("Passenger " + ticket.getPassengerInfo().getPassengerName() + " waiting for ticket machine " + ticketMachineName);
                noResource.await();
            }

            for(int i =0; i < TicketMachine.ticketPurchasedList.size(); i++){
                if(TicketMachine.ticketPurchasedList.get(i).equals(Thread.currentThread().getName())){
                    currentPaperLevel--;
                    currentTonerLevel--;
                    numberOfTicketsSold++;
                    System.out.println("Ticket printed");
//                    Thread.sleep(random.nextInt(1000) + 1000);
                }
            }

            havePaperLevel.signalAll();
            haveTonerLevel.signalAll();
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }
        finally {
            lock.unlock();
        }

//        if(currentPaperLevel > 0 && currentTonerLevel >= ServiceTicketMachine.MINIMUM_TONER_LEVEL){
//            for(int i = 0; i < ticketPurchasedList.size(); i++){
//                if(ticketPurchasedList.get(i).equals(Thread.currentThread().getName())){
//                    System.out.println("Ticket already purchased");
//                    currentPaperLevel--;
//                    currentTonerLevel--;
//                    numberOfTicketsSold++;
//                    System.out.println("Ticket printed");
//                }
//            }
//        }else {
//            System.out.println("Ticket machine " + ticketMachineName + " is out of paper or toner");
//        }
    }

    @Override
    public void purchasingTicket(Ticket ticket) {
        lock.lock();

        try{
            ticketPurchasedList.add(Thread.currentThread().getName());
            System.out.println(Thread.currentThread().getName()+ " Ticket purchased");

            havePaperLevel.signalAll();
            haveTonerLevel.signalAll();
        }catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }

//        ticketPurchasedList.add(ticket.getTicketNUmber());
//        System.out.println("Ticket purchased");
    }

    @Override
    public void replaceTonerCartridge() {
        lock.lock();
        try {
            while (currentTonerLevel >= ServiceTicketMachine.MINIMUM_TONER_LEVEL){
                System.out.println("Toner Technician waiting for refill ticket machine " + ticketMachineName);
                haveTonerLevel.await();
            }

            currentTonerLevel = ServiceTicketMachine.FULL_TONER_LEVEL;
            replaceTonerCartridgeCount++;
            System.out.println("Toner Technician refilled the toner of ticket machine " + ticketMachineName);
            noResource.signalAll();
        }catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        finally {
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

            while ((currentPaperLevel + ServiceTicketMachine.SHEETS_PER_PACK) > ServiceTicketMachine.FULL_PAPER_TRAY){
                System.out.println("Paper Technician waiting for refill ticket machine " + ticketMachineName);
                havePaperLevel.await();
            }

            currentPaperLevel += ServiceTicketMachine.SHEETS_PER_PACK;
            refillPaperPacksCount++;
            System.out.println("Paper Technician refilled the paper of ticket machine " + ticketMachineName);
            noResource.signalAll();

        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }
        finally {
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