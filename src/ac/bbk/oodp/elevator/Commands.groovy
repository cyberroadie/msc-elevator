package ac.bbk.oodp.elevator

/**
 * @author Olivier Van Acker, Richard Brown
 * Date: 21/02/2011
 */
class Command {

    String time

}


class Call extends Command {
    String name
    int floor
    int dest

    Call(String name, int floor, String time, int dest) {
        this.name = name
        this.floor = floor
        super.time = time
        this.dest = dest
    }
}

class Fail extends Command {
    int elevatorNumber

    Fail(int elevatorNumber, String time) {
        this.elevatorNumber = elevatorNumber
        super.time = time
    }
}

class Fix extends Command {
    int elevatorNumber

    Fix(String line) {
        List arLine = line.tokenize("   ")
        elevatorNumber = arLine[0].toInteger()
        time = arLine[1]
    }
}

class Display extends Command {

    Display(String line) {
        time = line
    }
}

class Status extends Command {

    Status(String line) {
        time = line
    }
}

