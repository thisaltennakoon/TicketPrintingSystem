package iit.level6.concurrent.assignment.Technician;

import iit.level6.concurrent.assignment.ServiceTicketMachine;
import iit.level6.concurrent.assignment.TicketMachine;

import java.util.Random;

public class TicketTonerTechnician implements Runnable{

        public static final int NUMBER_OF_RETRY = 3;
        private final ServiceTicketMachine serviceTicketMachine;
        private final String name;

        Random random = new Random();

        public TicketTonerTechnician(String name, ServiceTicketMachine serviceTicketMachine) {
            this.name = name;
            this.serviceTicketMachine = serviceTicketMachine;
        }

        @Override
        public void run() {
            for(int i = 0; i < NUMBER_OF_RETRY; i++){
                if(serviceTicketMachine.getTonerLevel() < ServiceTicketMachine.MINIMUM_TONER_LEVEL){
                    serviceTicketMachine.replaceTonerCartridge();
                }
                try {
                    Thread.sleep(random.nextInt(1000) + 1000);
                } catch (InterruptedException e) {
                    break;
//                    throw new RuntimeException(e);
                }
            }
            System.out.println("Toner Technician Finished, toner cartridges used: " + TicketMachine.replaceTonerCartridgeCount);
        }
}
