package ac.bbk.oodp.elevator

/**
 * @author Olivier Van Acker, Richard Brown
 * Date: 21/02/2011
 */
abstract class Command {

    String time

}

class Call extends Command {
    Passenger passenger
    int floor
    int dest
    boolean answered = false

    Call(String commandString) throws CommandException {
        List command = commandString.split('\t')
        if(command.size() != 5)
            throw new CommandException("Incorrect number of commands in string")
        try {
            this.passenger = new Passenger(command[1])
            this.floor = command[2].toInteger()
            this.time = command[3]
            this.dest = command[4].toInteger()
        } catch (Exception ex) {
            throw new CommandException("Incorrect command: ", ex)
        }
    }
}

class Fail extends Command {
    int elevatorNumber

    Fail(String commandString) throws CommandException {
        List command = commandString.split('\t')
        if(command.size() != 3)
            throw new CommandException("Incorrect number of commands in string")
        try {
            this.elevatorNumber = command[1].toInteger()
            this.time = command[2]
        } catch (Exception ex) {
            throw new CommandException("Incorrect command: ", ex)
        }
    }
}

class Fix extends Command {
    int elevatorNumber

    Fix(String commandString) {
        List command = commandString.split('\t')
        if(command.size() != 3)
            throw new CommandException("Incorrect number of commands in string")
        try {
            this.elevatorNumber = command[1].toInteger()
            this.time = command[2]
        } catch (Exception ex) {
            throw new CommandException("Incorrect command: ", ex)
        }
    }
}

class Display extends Command {

    Display(String commandString) {
        List command = commandString.split('\t')
        if(command.size() != 2)
            throw new CommandException("Incorrect number of commands in string")
        try {
            this.time = command[1]
        } catch (Exception ex) {
            throw new CommandException("Incorrect command: ", ex)
        }
    }
}

class Status extends Command {

    Status(String commandString) {
        List command = commandString.split('\t')
        if(command.size() != 2)
            throw new CommandException("Incorrect number of commands in string")
        try {
            this.time = command[1]
        } catch (Exception ex) {
            throw new CommandException("Incorrect command: ", ex)
        }
    }
}

