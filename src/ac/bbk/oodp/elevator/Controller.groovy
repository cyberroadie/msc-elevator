package ac.bbk.oodp.elevator

import groovyx.gpars.actor.DefaultActor
import groovyx.gpars.actor.Actor

/**
 * @author Olivier Van Acker, Richard Brown
 * Date: 21/02/2011
 */
class Controller extends DefaultActor {

    def log = GroovyLog.newInstance(Controller.class)

    Clock clock
    int numberOfElevators
    int numberOfFloors
    List elevatorList = []
    private List commandList = []
    private CommandParser commandParser

    Controller(BufferedReader reader, Actor clock) {
        this.clock = clock
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
        for(i in 1..numberOfElevators) {
            def lineSplit = reader.readLine().split("\t")
            this.elevatorList.add((new Elevator(lineSplit[1].toInteger(), lineSplit[2].toInteger())).start())
        }
    }

    /**
     * Parses the header and sets start time, no of floors and no of elevators on the controller
     * @param reader input to read configuration from
     */
    void parseHeader(reader) {
        clock.send readStartTime(reader)
        this.numberOfFloors = readNumberOfFloors(reader)
        this.numberOfElevators = readNumberOfElevators(reader)
    }

    private def readNumberOfElevators(reader) {
        reader.readLine().split("\t")[1].toInteger()
    }

    private def readNumberOfFloors(reader) {
        reader.readLine().split("\t")[1].toInteger()
    }

    private def readStartTime(reader) {
        reader.readLine()
    }

    void act() {
        loop() {
            clock.send "next"
            react {
                def command = commandParser.getNextCommand(it)
                if(command instanceof Terminate)
                    System.exit(1)
                else if(command instanceof Display)
                    display()
                else if(command instanceof Status)
                    status()
                else if(command != null) {
                    elevatorList.each { def elevator -> elevator.send command}
                    println "Command received: " + command
                }
                println "time: $it"
            }
        }
    }

    private passCall(Command call) {
        elevatorList.each() {
            elevator.addCall(call)
        }
    }

    void display() {
        elevatorList.each() { elevator ->
            println elevator.display()
        }
    }

    void status() {
        elevatorList.each() { elevator ->
            println elevator.stats()
        }
    }
}

