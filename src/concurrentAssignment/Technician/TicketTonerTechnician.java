package concurrentAssignment.Technician;

import concurrentAssignment.ServiceTicketMachine;
import concurrentAssignment.TicketMachine;

import java.util.Random;

public class TicketTonerTechnician extends Technician implements Runnable {

    public TicketTonerTechnician(String name, ServiceTicketMachine serviceTicketMachine) {
        this.name = name;
        this.serviceTicketMachine = serviceTicketMachine;
    }

    @Override
    public void run() {
        for (int i = 0; i < NUMBER_OF_RETRY; i++) {
            if (serviceTicketMachine.getTonerLevel() < ServiceTicketMachine.MIN_TONER_LEVEL) {
                serviceTicketMachine.replaceTonerCartridge();
            }
            try {
                Thread.sleep(new Random().nextInt(1000) + 1000);
            } catch (InterruptedException e) {
                break;
            }
        }
        System.out.println("Toner Technician thread has finished, number of toner cartridges used: "
                + TicketMachine.replaceTonerCartridgeCount);
    }
}
