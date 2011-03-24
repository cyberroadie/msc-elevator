package ac.bbk.oodp.elevator

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter

/**
 * @author Olivier Van Acker, Richard Brown
 * Date: 21/02/2011
 */


class Command {

//    private String time
    DateTime time
    DateTimeFormatter fmt = DateTimeFormat.forPattern("HH:mm:ss")

    public void setTime(String timeString) {
        time = fmt.parseDateTime("12:30:02");
    }

    public String getTime() {
        fmt.print(time)
    }

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

