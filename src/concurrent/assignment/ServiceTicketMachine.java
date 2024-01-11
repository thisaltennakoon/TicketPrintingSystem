package concurrent.assignment;

public interface ServiceTicketMachine {

    int FULL_PAPER_TRAY = 20;
    int SHEETS_PER_PACK = 5;
    int MAX_TONER_LEVEL = 15;
    int MIN_TONER_LEVEL = 10;

    void printTicket(Ticket ticket);

    Ticket purchaseTicket(String passengerName, String phoneNumber, String emailAddress, String arrivalLocation,
                          String departureLocation);

    void replaceTonerCartridge();

    void refillTicketPaper();

    int getPaperLevel();

    int getTonerLevel();
}
