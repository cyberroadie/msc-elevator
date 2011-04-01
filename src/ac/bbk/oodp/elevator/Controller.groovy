package ac.bbk.oodp.elevator

import groovyx.gpars.actor.DefaultActor
import groovyx.gpars.actor.Actor

/**
 * @author Olivier Van Acker, Richard Brown
 * Date: 21/02/2011
 */
class Controller extends DefaultActor {

    Clock clock
    int numberOfElevators
    int numberOfFloors
    List elevatorList = []
    private List commandList = []
    private InputParser inputParser

    Controller(BufferedReader reader, Actor clock) {
        this.clock = clock
        parseHeader(reader)
        inputParser = new InputParser(reader)
        for(i in 1..numberOfElevators)
            this.elevatorList.add((new Elevator(i)).start())
        println "Finished initializing controller"
    }

    /**
     * Parses the header and sets start time, no of floors and no of elevators on the controller
     * @param controller
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

    private void initElevators() {
        inputParser.initElevators(numberOfElevators).each {
            elevatorList[elevatorList.size()] = it
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

