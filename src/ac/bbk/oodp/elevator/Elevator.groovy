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
    int currentFloor
    int destination
    int floorsTravelled
    int passengersDelivered
    int distanceTravelled
    int waitTime = 0
    int travelTime = 0
    String direction = "none"
    List passengers = new ArrayList()
    List callList
    List currentCalls
    boolean operational
    boolean moving

    Elevator(int elevator, int startingFloor) {
        elevatorNumber = elevator
        currentFloor = startingFloor
    }

    void act() {
        loop() {
            react {
                println "Elevator $elevatorNumber received command $it"
            }
        }
    }

    void makeMove() {
        if (!operational) {return}
        if (moving()) {incrementTravelTime()}
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

    private void incrementTravelTime() {
        travelTime+=1
        if (travelTime == 10) {
            changeFloor()
            travelTime = 0
            moving = false
        }
    }

    private changeFloor() {
        if (direction == "up") {
            currentFloor += 1
        }
        else {
            currentFloor -= 1
        }
    }

    private boolean destinationValid() {
        return
    }

    void updateDestination() {
        if (callInSameDirection()) {
            return
        }
        if (currentCallList.size() > 0) {
            destination = currentCallList[0].getPassenger().getFloor()
            return
        }
        if (callInOppositeDirection()) {
            return
        }
    }

    void addCall(Command call) {
        callList[callList.size()] = call
    }

    boolean callInSameDirection() {
        if (direction == "up") {
            return callUpFromFloor()
        }
        else {
            return callDownFromFloor()
        }
    }

    boolean callInOppositeDirection() {
        if (direction == "up") {
            return callDownFromFloor()
        }
        else {
            return callUpFromFloor()
        }
    }

    boolean callUpFromFloor() {
        callList.each() {
            if (it.getFloor() > floor) {
                destination = it.getFloor()
                return true
            }
        }
        return false
    }

    boolean callDownFromFloor() {
        callList.each() {
            if (it.getFloor() < floor) {
                destination = it.getFloor()
                return true
            }
        }
        return false
    }

    String display() {
        "Elevator Number: ${elevatorNumber}\nCurrent location: ${}\nCurrent passengers: ${passengers.join(', ')}"
    }

    String stats() {
        "Elevator Number: ${elevatorNumber}\nNo of passengers delivered: ${passengersDelivered}\nCurrent passengers: ${passengers.size()}\nDistance Travelled: ${distanceTravelled}"
    }

}
