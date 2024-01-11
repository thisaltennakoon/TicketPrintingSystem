package concurrent.assignment.Technician;

import concurrent.assignment.ServiceTicketMachine;
import concurrent.assignment.TicketMachine;

import java.util.Random;

public class TicketPaperTechnician extends Technician implements Runnable {

    Random random = new Random();

    public TicketPaperTechnician(String name, ServiceTicketMachine serviceTicketMachine) {
        this.name = name;
        this.serviceTicketMachine = serviceTicketMachine;
    }

    @Override
    public void run() {
        for (int i = 0; i < NUMBER_OF_RETRY; i++) {
            if (serviceTicketMachine.getPaperLevel() < ServiceTicketMachine.SHEETS_PER_PACK) {
                serviceTicketMachine.refillTicketPaper();
            }
            try {
                Thread.sleep(random.nextInt(1000) + 1000);
            } catch (InterruptedException e) {
                break;
            }
        }
        System.out.println("Paper Technician thread has finished, number of packs of paper used: " +
                TicketMachine.refillPaperPacksCount);
    }

}
