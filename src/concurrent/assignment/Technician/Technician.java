package concurrent.assignment.Technician;

import concurrent.assignment.ServiceTicketMachine;

public abstract class Technician {

    public static final int NUMBER_OF_RETRY = 3;
    public ServiceTicketMachine serviceTicketMachine;
    public String name;
}
