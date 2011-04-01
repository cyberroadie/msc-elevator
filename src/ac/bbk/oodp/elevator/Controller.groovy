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
    private CommandParser inputParser

    Controller(BufferedReader reader, Actor clock) {
        this.clock = clock
        parseHeader(reader)
        inputParser = new CommandParser(reader)
        initElevators(reader)
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
        this.clock.startTime = reader.readLine().split("\t")[1]
        this.numberOfFloors = reader.readLine().split("\t")[1].toInteger()
        this.numberOfElevators = reader.readLine().split("\t")[1].toInteger()
    }

    void act() {
        loop() {
            clock.send "next"
            react {
                switch (it) {
                    case "finish":
                        terminate()
                    break;
                    default:
                        elevatorList.each { def elevator -> elevator.send it }
                        println "cycle: $it"

                }
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

