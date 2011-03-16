package ac.bbk.oodp.elevator

/**
 * @author Olivier Van Acker, Richard Brown
 * Date: 21/02/2011
 */
abstract class Command {

    String time

    Command(String time) {
        this.time = time
    }

}


class Call extends Command {
    String name
    int floor
    int dest

    Call(String name, int floor, String time, int dest) {
        super(time)
        this.name = name
        this.floor = floor
        this.dest = dest
    }
}

class Fail extends Command {
    int elevatorNumber

    Fail(int elevatorNumber, String time) {
        super(time)
        this.elevatorNumber = elevatorNumber
    }
}

class Fix extends Command {
    int elevatorNumber

    Fix(int elevatorNumber, String time) {
        super(time)
        this.elevatorNumber = elevatorNumber
    }
}

class Display extends Command {

    Display(String time) {
        super(time)
    }
}

class Status extends Command {

    Status(String time) {
        super(time)
    }
}

