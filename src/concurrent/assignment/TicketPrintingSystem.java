package concurrent.assignment;

import concurrent.assignment.Technician.TicketPaperTechnician;
import concurrent.assignment.Technician.TicketTonerTechnician;

public class TicketPrintingSystem {

    public static void main(String[] args) {

        TicketMachine ticketMachine = new TicketMachine(" TicketMachine1", 1, 2);

        Passenger passenger1 = new Passenger(ticketMachine, "passenger1", "01122334455",
                "passenger1@iit.com", "arrivalLocation1", "departureLocation1");
        Passenger passenger2 = new Passenger(ticketMachine, "passenger2", "01122334451",
                "passenger2@iit.com", "arrivalLocation2", "departureLocation2");
        Passenger passenger3 = new Passenger(ticketMachine, "passenger3", "01122334452",
                "passenger3@iit.com", "arrivalLocation3", "departureLocation3");
        Passenger passenger4 = new Passenger(ticketMachine, "passenger4", "01122334453",
                "passenger4@iit.com", "arrivalLocation4", "departureLocation4");

        TicketTonerTechnician ticketTonerTechnician = new TicketTonerTechnician("Toner Technician", ticketMachine);
        TicketPaperTechnician ticketPaperTechnician = new TicketPaperTechnician("Paper Technician", ticketMachine);

        ThreadGroup passengerThreadGroup = new ThreadGroup("PassengerThreadGroup");
        ThreadGroup technicianThreadGroup = new ThreadGroup("TechnicianThreadGroup");

        Thread passengerThread1 = new Thread(passengerThreadGroup, passenger1, "passenger1Thread");
        Thread passengerThread2 = new Thread(passengerThreadGroup, passenger2, "passenger2Thread");
        Thread passengerThread3 = new Thread(passengerThreadGroup, passenger3, "passenger3Thread");
        Thread passengerThread4 = new Thread(passengerThreadGroup, passenger4, "passenger4Thread");

        Thread tonerTechnicianThread = new Thread(technicianThreadGroup, ticketTonerTechnician, "Toner Technician");
        Thread paperTechnicianThread = new Thread(technicianThreadGroup, ticketPaperTechnician, "Paper Technician");

        passengerThread1.start();
        passengerThread2.start();
        passengerThread3.start();
        passengerThread4.start();

        tonerTechnicianThread.start();
        paperTechnicianThread.start();

        technicianThreadGroup.setMaxPriority(Thread.MAX_PRIORITY);

        while (technicianThreadGroup.activeCount() > 0 || passengerThreadGroup.activeCount() > 0) {
            if (passengerThreadGroup.activeCount() == 0) {
                technicianThreadGroup.interrupt();
                System.out.println("All passengers and technicians have finished..");
                break;
            }
        }
    }
}
