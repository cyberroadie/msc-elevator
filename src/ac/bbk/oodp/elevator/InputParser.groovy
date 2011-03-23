package ac.bbk.oodp.elevator

/**
 * @author Olivier Van Acker, Richard Brown
 * Date: 21/02/2011
 */
class InputParser {

    List lines
    def file
    List setup
    List init
    List commands
    List errorLog
    Command currentCommand

    InputParser(String filename) {

        file = new BufferedReader(new FileReader(filename))
//        lines = new File(filename).readLines()
//        for (i in (0..lines.size()-1)) {
//            readLine(lines[i])
//        }
        currentCommand = CommandFactory.getCommand(readNextLine())
    }

    void readLine(String line) {

        switch (line[0..line.indexOf("\t")]) {
            case "StartTime":
            case "NumberOfFloors":
            case "NumberOfElevators":
                setup[setup.size()] = line
                break
            case "init":
                init[init.size()] = line
                break
            default:
                commands[commands.size()] = CommandFactory.getCommand(line)
        }

    }

    String readNextLine() {
        file.readLine()
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
