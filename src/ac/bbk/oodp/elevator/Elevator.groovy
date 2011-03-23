package ac.bbk.oodp.elevator

import groovyx.gpars.actor.DefaultActor

/**
 * •	Keep track of a list of its current passengers.
 * •	Keep track of destinations requested by current passengers.
 * •	Keep track of the current destination it (the elevator) is attempting to reach.
 *      Note this may not be the next floor on which it will actually stop.
 * •	Keep track of whether it is broken or operational.
 * •	Keep track of the time delay when it stops to load/unload passengers (specified below).
 * •	Keep track of the time delay when moving between adjacent floors (specified below).
 * •	Keep track of its current location. This may be more complex than a floor number.
 * •	Keep track of its current direction of movement (if any).
 * •	Update its state when the simulated clock “ticks”.
 *
 *
 * @author Olivier Van Acker, Richard Brown
 * Date: 21/02/2011
 */
class Elevator extends DefaultActor {

    int elevatorNumber
    int destination
    int floorsTravelled
    int passengersDelivered
    int distanceTravelled
    int waitTime = 0
    int travelTime = 0
    String direction = "none"
    List passengers
    List calls
    boolean operational
    boolean moving

    Elevator(int elevator) {
        elevatorNumber = elevator
    }

    void act() {
        loop() {
            react {
                println "Elevator $elevatorNumber received second $it"
            }
        }
    }

    void makeMove() {
        if (!operational) {return}
        if (!moving()) {incrementWait()}
        if (!destinationValid()) {updateDestination()}
        if (!reachedDestination()) {letPassengersOn()}
    }

    void incrementWait() {
        waitTime+=1
        if (waitTime == 5) {
            waitTime = 0
            moving = true
        }
    }

    void incrementTravelTime() {
        travelTime+=1
        if (travelTime == 10) {
            travelTime = 0
            moving = false
        }
    }

    void updateDestination() {
        calls.each() {
            if (callIsInSameDirection(it.getFloor())) {
                destination = it.getFloor()
                return
            }
        }
    }

    String display() {
        "Elevator Number: ${elevatorNumber}\nCurrent location: ${}\nCurrent passengers: ${passengers.join(', ')}"
    }

    String stats() {
        "Elevator Number: ${elevatorNumber}\nNo of passengers delivered: ${passengersDelivered}\nCurrent passengers: ${passengers.size()}\nDistance Travelled: ${distanceTravelled}"
    }

}
