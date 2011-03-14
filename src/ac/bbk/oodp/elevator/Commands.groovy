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
        super.time = time
        this.name = name
        this.floor = floor
        this.dest = dest
    }
}

class Fail extends Command {
    int elevatorNumber

    Fail(String line) {
        this.elevatorNumber = line[0..line.indexOf("\t")].toInteger()
        super.time = line[line.indexOf("\t")..line.size()-1]
    }
}

class Fix extends Command {
    int elevatorNumber

    Fix(String line) {
        this.elevatorNumber = line[0..line.indexOf("\t")].toInteger()
        super.time = line[line.indexOf("\t")..line.size()-1]
    }
}

class Display extends Command {

    Display(String line) {
        super.time = line[line.indexOf("\t")..line.size()-1]
    }
}

class Status extends Command {

    Status(String line) {
        super.time = line[line.indexOf("\t")..line.size()-1]
    }
}

