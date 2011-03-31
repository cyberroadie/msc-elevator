package ac.bbk.oodp.elevator

import groovyx.gpars.actor.DefaultActor
import groovyx.gpars.actor.Actor

@GrabResolver(name = 'gpars', root = 'http://snapshots.repository.codehaus.org/', m2Compatible = true)

/**
 * @author Olivier Van Acker, Richard Brown
 * Date: 21/02/2011
 */
@Grab(group = 'org.codehaus.gpars', module = 'gpars', version = '0.12-beta-1-SNAPSHOT')
class Controller extends DefaultActor {

    Controller(int numberOfElevators, Actor clock) {
        this.clock = clock
        for(i in 1..numberOfElevators)
            this.elevatorList.add(new Elevator(elevatorNumber: i).start())
        controllerParser = new InputParser()
        setupSystem()
        println "Finished initializing controller"
    }

    private Actor clock
    private int numberOfElevators
    private List elevatorList = []
    private List commandList = []
    private InputParser controllerParser

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

    private void setupSystem() {
        controllerParser
    }

    private void initElevators() {
        controllerParser.initElevators(numberOfElevators).each {
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

