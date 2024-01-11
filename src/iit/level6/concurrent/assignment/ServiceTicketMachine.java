package iit.level6.concurrent.assignment;

public interface ServiceTicketMachine extends Machine {

    int FULL_PAPER_TRAY = 20;
    int SHEETS_PER_PACK = 5;

    int FULL_TONER_LEVEL = 15;
    int MINIMUM_TONER_LEVEL = 10;

    void replaceTonerCartridge();

    void refillTicketPaper();

    int getPaperLevel();
    int getTonerLevel();
}
