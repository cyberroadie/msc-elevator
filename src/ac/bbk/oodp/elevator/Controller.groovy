package ac.bbk.oodp.elevator

/**
 * @author Olivier Van Acker, Richard Brown
 * Date: 21/02/2011
 */
class Controller {

    def log = GroovyLog.newInstance(Controller.class)

    Clock clock = new Clock()
    int numberOfElevators
    int numberOfFloors
    List elevatorList = []
    private List commandList = []
    private CommandParser commandParser

    /**
     * Constructor
     * @param reader input reader for commands
     */
    Controller(BufferedReader reader) {
        parseHeader(reader)
        initElevators(reader)
        commandParser = new CommandParser(reader)
        log.info("Finished initializing controller")
    }

    /**
     * Reads the init commands and initialize the elevators
     * with floor and elevator number and starts elevators
     * @param reader input to read configuration from
     */
    void initElevators(reader) {
        for (i in 1..numberOfElevators) {
            def lineSplit = reader.readLine().split("\t")
            this.elevatorList.add((new Elevator(lineSplit[1].toInteger(), lineSplit[2].toInteger())))
        }
    }

    /**
     * Parses the header and sets start time, no of floors and no of elevators on the controller
     * Header should look like this:
     * StartTime: 	14:00:00
     * NumberOfFloors:	12
     * NumberOfElevators:	3
     *
     * @param reader input to read configuration from
     */
    void parseHeader(reader) {
        clock.initializeClock(reader.readLine())
        this.numberOfFloors = readNumberAfterFirstTabDelimiter(reader.readLine())
        this.numberOfElevators = readNumberAfterFirstTabDelimiter(reader.readLine())
    }

    /**
     * Read integer from string with following format:
     * <String><tab><number>
     * Integer is the first number after the tab
     * @param line input string
     * @return integer
     */
    private def readNumberAfterFirstTabDelimiter(line) {
        line.split("\t")[1].toInteger()
    }

    /**
     * Starts the controller and will loop until command parser sends Terminate Command AND
     * all passengers are delivered. Every loop the clock will increase, command will be
     * retrieved from command parser and send to the elevators and elevator will be asked
     * to respond to the next tick of the clock
     */
    void start() {
        while (true) {
            def time = clock.next()
            def command = commandParser.getNextCommand(time)
            if (command instanceof Terminate) {
                while(true) {
                    if(Elevator.callList.size() == 0)
                        System.exit(1)
                    elevatorList.each { def elevator -> elevator.respondToClock() }
                }
            }
            else if (command instanceof Display)
                display()
            else if (command instanceof Status)
                status()
            else if (command instanceof Call) {
                Elevator.addCall command
            } else if (command instanceof Fail) {
                elevatorList.get(((Fail) command).elevatorNumber).fail()
            } else if(command instanceof Fix) {
                elevatorList.get(((Fix) command).elevatorNumber).fix()
            }
            elevatorList.each { def elevator -> elevator.respondToClock() }
        }
    }

    /**
     * For each elevator display() prints  the elevator
     * number, its current location, its current direction of travel
     * and its current status (broken, stopped, moving, etc.), and a list
     * of the names of all current passengers
     */
    void display() {
        println "--------------- Display -------------"
        elevatorList.each() { elevator ->
            println elevator.display()
        }
        println "-------------------------------------"
    }

    /**
     * For  each elevator prints the total number of  passengers that elevator
     * has delivered to their destination, the number of current passengers,
     * and the total distance the elevator has traveled since the beginning of the simulation
     */
    void status() {
        println "--------------- Stats ---------------"
        elevatorList.each() { elevator ->
            println elevator.stats()
        }
        println "-------------------------------------"
    }
}

