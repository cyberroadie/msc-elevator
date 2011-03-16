package ac.bbk.oodp.elevator

/**
 * Created by IntelliJ IDEA.
 * User: brownr22
 * Date: 16/03/2011
 * Time: 06:57
 * To change this template use File | Settings | File Templates.
 */
class CommandFactory {

    static getCommand(String line) {
        println line
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
            case "status":
                return new Status(command[1])
                break
        }

    }
}
