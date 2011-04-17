package ac.bbk.oodp.elevator

/**
 * Parses commands from reader
 * @author Olivier Van Acker, Richard Brown
 * Date: 21/02/2011
 */
class CommandParser {

    def reader
    private Command currentCommand

    /**
     * Constructor
     * @param reader in put reader for commands
     */
    CommandParser(BufferedReader reader) {
        this.reader = reader
        currentCommand = CommandFactory.getCommand(reader.readLine())
    }

    /**
     * Gets the next command available if time is reached
     * if EOF will return Terminiate command
     * will push clock forward, if no command son current time are left
     * @param time current time
     * @return Command, Terminate if no more commands available or
     * null if no commands are available with current time
     */
    Command getNextCommand(Clock clock) {
        if (currentCommand.getTime() == clock.getCurrentTime()) {
            Command tmpCommand = currentCommand
            currentCommand = CommandFactory.getCommand(reader.readLine())
            return tmpCommand
        } else if (currentCommand instanceof Terminate) {
            return currentCommand
        } else {
            clock.increaseByOneSecond()
            return null
        }
    }
}
