package ac.bbk.oodp.elevator

/**
 * @author Olivier Van Acker, Richard Brown
 * Date: 21/02/2011
 */
class InputParser {

    List lines
    List setup
    List init
    List commands
    List errorLog

    InputParser(String filename) {

        lines = new File(filename).readLines()
        for (i in (0..lines.size()-1)) {
            readLine(lines[i])
        }
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
}
