package iit.level6.concurrent.assignment;

import iit.level6.concurrent.assignment.PassengerInfo.PassengerInfo;
import iit.level6.concurrent.assignment.Technician.TicketPaperTechnician;
import iit.level6.concurrent.assignment.Technician.TicketTonerTechnician;
import iit.level6.concurrent.assignment.TicketInfo.Ticket;
import iit.level6.concurrent.assignment.TravelInfo.TravelInfo;

import java.math.BigDecimal;

public class TicketPrintingSystem {

    public static void main(String[] args) {

        PassengerInfo passengerInfo1 = new PassengerInfo("Peter", "0112345678", "peter@gmail.com");
        PassengerInfo passengerInfo2 = new PassengerInfo("John", "0711212122", "john@gmail.com");
        PassengerInfo passengerInfo3 = new PassengerInfo("Mary", "0771234567", "mary@gmail.com");
        PassengerInfo passengerInfo4 = new PassengerInfo("Jane", "0774234243", "jane@gmial.com");

        TravelInfo travelInfo1 = new TravelInfo("Colombo", "Kandy");
        TravelInfo travelInfo2 = new TravelInfo("Kandy", "Galle");
        TravelInfo travelInfo3 = new TravelInfo("Galle", "Matara");
        TravelInfo travelInfo4 = new TravelInfo("Matara", "Colombo");

        Ticket ticket1 = new Ticket(1001, new BigDecimal(1000), passengerInfo1, travelInfo1);
        Ticket ticket2 = new Ticket(1002, new BigDecimal(2000), passengerInfo2, travelInfo2);
        Ticket ticket3 = new Ticket(1003, new BigDecimal(3000), passengerInfo3, travelInfo3);
        Ticket ticket4 = new Ticket(1004, new BigDecimal(4000), passengerInfo4, travelInfo4);

        TicketMachine ticketMachine = new TicketMachine(" TicketMachine Name",1, 2);

        Passenger passenger1 = new Passenger(ticketMachine);
        Passenger passenger2 = new Passenger(ticketMachine);
        Passenger passenger3 = new Passenger(ticketMachine);
        Passenger passenger4 = new Passenger(ticketMachine);

        TicketTonerTechnician ticketTonerTechnician = new TicketTonerTechnician("Toner Technician", ticketMachine);
        TicketPaperTechnician ticketPaperTechnician = new TicketPaperTechnician("Paper Technician", ticketMachine);

        ThreadGroup passengerThreadGroup = new ThreadGroup("PassengerThreadGroup");
        ThreadGroup technicianThreadGroup = new ThreadGroup("TechnicianThreadGroup");

        Thread passengerThread1 = new Thread(passengerThreadGroup, passenger1, "PeterThread");
        Thread passengerThread2 = new Thread(passengerThreadGroup, passenger2, "JohnThread");
        Thread passengerThread3 = new Thread(passengerThreadGroup, passenger3, "MaryThread");
        Thread passengerThread4 = new Thread(passengerThreadGroup, passenger4, "JaneThread");

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
                System.out.println("All passengers have finished printing and stop technicians");
                break;
            }
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }

    }
}
