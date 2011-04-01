package ac.bbk.oodp.elevator

/**
 * @author Olivier Van Acker, Richard Brown
 * Date: 21/02/2011
 */
class CommandParser {

    List lines
    def reader
    List setup
    List init
    List commands
    List errorLog
    Command currentCommand

    CommandParser(BufferedReader reader) {
        this.reader = reader
    }

    String readNextLine() {
        reader.readLine()
    }

    Command getNextCommand(String time) {
        if (currentCommand.getTime() == time) {
            Command tmpCommand = currentCommand
            String line = readNextLine()
            if (line != null) {
                currentCommand = CommandFactory.getCommand(line)
            }
            else {
                currentCommand = null
            }
            return tmpCommand
        }
    }
}
