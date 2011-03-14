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

    Call(String line) {
        List arLine = line.split("  ")
        name = arLine[0]
        floor = arLine[1].toInteger()
        time = arLine[2]
        dest = arLine[3].toInteger()
    }
}

class Fail extends Command {
    int elevatorNumber

    Fail(String line) {
        List arLine = line.tokenize("   ")
        elevatorNumber = arLine[0].toInteger()
        time = arLine[1]
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

