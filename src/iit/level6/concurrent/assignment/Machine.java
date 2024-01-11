package iit.level6.concurrent.assignment;

import iit.level6.concurrent.assignment.TicketInfo.Ticket;

public interface Machine {

    void printTicket(Ticket ticket);

    void purchasingTicket(Ticket ticket);
}
