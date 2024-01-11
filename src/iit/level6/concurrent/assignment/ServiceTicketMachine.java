package iit.level6.concurrent.assignment;

import iit.level6.concurrent.assignment.TicketInfo.Ticket;

public interface ServiceTicketMachine {

    int FULL_PAPER_TRAY = 20;
    int SHEETS_PER_PACK = 5;

    int FULL_TONER_LEVEL = 15;
    int MINIMUM_TONER_LEVEL = 10;

    void printTicket(Ticket ticket);

    Ticket purchaseTicket(String passengerName, String phoneNumber, String emailAddress);

    void replaceTonerCartridge();

    void refillTicketPaper();

    int getPaperLevel();
    int getTonerLevel();
}
