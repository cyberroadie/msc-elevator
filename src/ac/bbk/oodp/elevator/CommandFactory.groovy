package ac.bbk.oodp.elevator

/**
 * @author Olivier Van Acker, Richard Brown
 * Date: 27/02/11
 */
class CommandFactory {

    static def getCommand(String line) {
        List command = line.split('\t')
        switch (command[0]) {
            case "call":
                return new Call(command[1],command[2].toInteger(),command[3],command[4].toInteger())
                break
            case "display":
                return new Display(command[1])
                break
            case "fail":
                return new Fail(command[1].toInteger(),command[2])
                break
            case "fix":
                return new Fix(command[1].toInteger(),command[2])
                break
            case "stats":
                return new Status(command[1])
                break
        }

    }
}
