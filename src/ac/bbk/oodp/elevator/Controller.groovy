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
        println "Finished initializing controller"
    }

    private Actor clock
    private List elevatorList = []
    private List commandList = []

    void act() {
        loop() {
            clock.send "next"
            react {
                switch (it) {
                    case "finish":
                        terminate()
                    default:
                        elevatorList.each { def elevator -> elevator.send it }
                        println "cycle: $it"
                }
            }
        }
    }
}

