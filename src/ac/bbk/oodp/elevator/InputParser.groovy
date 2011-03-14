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
            case "init":
                setup[setup.size()] = line
                break
            case "init":
                init[init.size()] = line
                break
            case "call":
                commands[commands.size()] = new Call(line[line.indexOf("\t")+1..line.size()-1])
                break
            case "display":
                commands[commands.size()] = new Display(line[line.indexOf("\t")+1..line.size()-1])
                break
            case "fail":
                commands[commands.size()] = new Fail(line[line.indexOf("\t")+1..line.size()-1])
                break
            case "fix":
                commands[commands.size()] = new Fix(line[line.indexOf("\t")+1..line.size()-1])
                break
            case "status":
                commands[commands.size()] = new Status(line[line.indexOf("\t")+1..line.size()-1])
                break
            default:
                errorLog[errorLog.size()] = "Unknown command in line: ${line}"
        }

    }
}
