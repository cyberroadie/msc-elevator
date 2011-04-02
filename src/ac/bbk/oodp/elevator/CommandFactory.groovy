package ac.bbk.oodp.elevator

/**
 * @author Olivier Van Acker, Richard Brown
 * Date: 27/02/11
 */
class CommandFactory {

    static def getCommand(String line) throws CommandException {
        if(line == null)
            return new Terminate()
        if(line.startsWith("call"))
            return new Call(line)
        if(line.startsWith("display"))
            return new Display(line)
        if(line.startsWith("fail"))
            return new Fail(line)
        if(line.startsWith("fix"))
            return new Fix(line)
        if(line.startsWith("stats"))
            return new Status(line)
        throw new CommandException("Unknown command: " + line)
    }
}

class CommandException extends Exception {

    CommandException(String s) {
        super(s)
    }

    CommandException(String s, Throwable throwable) {
        super(s, throwable)
    }
}