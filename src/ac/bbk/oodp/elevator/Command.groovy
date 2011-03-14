package ac.bbk.oodp.elevator

/**
 * @author Olivier Van Acker, Richard Brown
 * Date: 21/02/2011
 */
class Commands {

    String time

}


class Call extends Commands {
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

class Fail extends Commands {
    int elevatorNumber

    Fail(String line) {
        this.elevatorNumber = line[0..line.indexOf("\t")].toInteger()
        super.time = line[line.indexOf("\t")..line.size()-1]
    }
}

class Fix extends Commands {
    int elevatorNumber

    Fix(String line) {
        this.elevatorNumber = line[0..line.indexOf("\t")].toInteger()
        super.time = line[line.indexOf("\t")..line.size()-1]
    }
}

class Display extends Commands {

    Display(String line) {
        super.time = line[line.indexOf("\t")..line.size()-1]
    }
}

class Status extends Commands {

    Status(String line) {
        super.time = line[line.indexOf("\t")..line.size()-1]
    }
}

