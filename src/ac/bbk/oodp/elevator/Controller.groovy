package ac.bbk.oodp.elevator

/**
 * @author Olivier Van Acker, Richard Brown
 * Date: 21/02/2011
 */
class Controller {

    def log = GroovyLog.newInstance(Controller.class)

    File errorLog = new File("error.log")
    Clock clock = new Clock()
    int numberOfElevators
    int numberOfFloors
    List elevatorList = []
    private List callList = []
    private CommandParser commandParser

    /**
     * Constructor
     * @param reader input reader for commands
     */
    Controller(BufferedReader reader) {
        parseHeader(reader)
        initElevators(reader)
        commandParser = new CommandParser(reader)
        errorLog.write("")
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
    void run() {
        while (true) {
            def command
            try {
                command = commandParser.getNextCommand(clock)
                validateCommand(command)
            } catch (CommandException ex) {
                writeToErrorLog(ex)
                continue
            }
            if (command instanceof Terminate) {
                while(true) {
                    if(callList.size() == 0)
                        System.exit(1)
                    elevatorList.each { def elevator -> elevator.respondToClock() }
                    assignCallsToStoppedElevators()
                    assignCallsToJustArrivedElevators()
                }
            }
            else if (command instanceof Display)
                display()
            else if (command instanceof Status)
                status()
            else if (command instanceof Call) {
                callList[callList.size()] = command
                updateWaitingFloors()
                command.showCall()
            } else if (command instanceof Fail) {
                elevatorList.get(((Fail) command).elevatorNumber).fail()
            } else if(command instanceof Fix) {
                elevatorList.get(((Fix) command).elevatorNumber).fix()
            }
            elevatorList.each { def elevator -> elevator.respondToClock() }
            assignCallsToStoppedElevators()
            assignCallsToJustArrivedElevators()
        }
    }

    void validateCommand(Command command) throws CommandException {
        if (command instanceof Call) {
            if(command.floor > numberOfFloors)
                throw new CommandException("Floor passanger is on is greater than max number of floors")
            if(command.dest > numberOfFloors)
                throw new CommandException("Destination floor is on is greater than max number of floors")
        } else if (command instanceof Fail || command instanceof Fix) {
            if(command.elevatorNumber > numberOfElevators)
                throw new CommandException("Elevator to fail doesn't exist")
        }
    }

    void writeToErrorLog(CommandException ex) {
        errorLog << ex.toString() << "\n"
    }

    /**
     * Check the elevator list to see if any elevators are stopped on floors
     * with calls waiting
     * If there are the relevant call is sent to the elevator
     */
    void assignCallsToStoppedElevators() {
        callList.each() { call ->
            elevatorList.each() { elevator ->
                if (!elevator.moving && elevator.operational && elevator.currentFloor == call.floor) sendCallToElevator(elevator, call)
            }
        }
    }

    /**
     * Checks the elevator list to see if any elevators have just arrived
     * on floors with calls waiting
     * If there are the relevant call is sent to the elevator
     */
    void assignCallsToJustArrivedElevators() {
        callList.each() { call ->
            elevatorList.each() { elevator ->
                if (elevator.currentFloor == call.floor && elevator.operational && !elevator.betweenFloors) sendCallToElevator(elevator, call)
            }
        }
    }

    /**
     * Send a call to a specific elevator by calling the elevator's send call command
     * Removes the calls from the controller's call list
     * Calls the updateWaitingFloors method to set the floors in the Elevator static list
     *
     * @param elevatorNumber
     * @param call
     */
    void sendCallToElevator(Elevator elevator, Command call) {
        elevator.sendCall(call)
        callList = callList.minus(call)
        updateWaitingFloors()
    }

    /**
     * Sets the current floors with waiting calls in the Elevator static list
     */
    void updateWaitingFloors() {
        List waitingFloors = []
        callList.each() {
            waitingFloors.add(it.floor)
        }
        Elevator.waitingFloors = waitingFloors
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

