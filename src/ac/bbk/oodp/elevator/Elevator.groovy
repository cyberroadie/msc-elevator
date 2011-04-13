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
    static List callList = new ArrayList()
    List currentCalls  = new ArrayList()
    Command respondingCall
    boolean operational = true
    boolean moving = false

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

    /**
     * Runs through the logic required for each tick of the clock
     */
    void respondToClock() {
        if (!operational) {return}
        move()
        decrementWait()
        if (travelTime == 0) {
            letPassengerOff()
            letPassengerOn()
        }
        if (!destinationValid()) {updateDestination()}
    }

    /**
     * Decrements the waiting time
     *
     * If full waiting time has elapsed set the elevator to moving
     */
    private void decrementWait() {
        if (moving) return
        waitTime-=1
        if (waitTime == 0) moving = true
    }

    /**
     * Moves the elevator one second in the relevant direction
     *
     * If elevator has reached a new floor, change the current floor
     * and check if there are passengers on that floor
     */
    private void move() {
        if (!moving) return

        if (direction == "up") incrementTravelTime()
        else decrementTravelTime()

        if (travelTime % 10 == 0) {
            if (travelTime != 0) changeFloor()
        }
    }

    /**
     * Move the elevator up one second
     */
    private void incrementTravelTime() {
        travelTime+=1
    }

    /**
     * Move the elevator down one second
     */
    private void decrementTravelTime() {
        travelTime-=1
    }

    /**
     * Change the current floor number
     * If elevator is moving up add one to the floor number
     * If elevator is moving down subtract one from the floor number
     */
    private void changeFloor() {
        currentFloor = (direction == "up") ? currentFloor + 1 : currentFloor - 1
        floorsTravelled++
        travelTime = 0
    }

    /**
     * Runs through list of outstanding calls and checks if on same floor
     * If call on the same floor add to list of current calls,
     * set answered to true and set the elevator to waiting
     */
    private void letPassengerOn() {
        callList.each {
            if (it.getFloor() == currentFloor && !it.getAnswered()) {
                currentCalls[currentCalls.size()] = it
                it.answered = true
                setWaiting()
            }
        }
    }

    /**
     * Runs through list of current calls
     * If passenger has reached destination then let them off and
     * increment number of passengers delivered and set the elevator to waiting
     */
    private void letPassengerOff() {
        currentCalls.each {
            if (it.dest == currentFloor) {
                passengersDelivered++
                currentCalls = currentCalls.minus(it)
                callList = callList.minus(it)
                setWaiting()
            }
        }
    }

    /**
     * Set the elevator to a waiting state
     * Sets moving to false and if waiting time has dropped to 0 reset it to 5
     */
    private void setWaiting() {
        moving = false
        if (waitTime == 0) {
           waitTime = 5
        }
    }

    /**
     * Checks if current destination is still valid
     *
     * @return true if it is
     */
    private boolean destinationValid() {
        return (callList.contains(respondingCall))
    }

    /**
     * Updates the destination
     *
     * to complete
     */
    private void updateDestination() {
        if (callInSameDirection()) {
            return
        }
        if (currentCalls.size() > 0) {
            destination = currentCalls[0].getPassenger().getFloor()
            respondingCall = currentCalls[0]
            return
        }
        if (callInOppositeDirection()) {
            return
        }
    }

    /**
     * Adds a new call to the call list
     *
     * @param call The call to be added
     */
    public static void addCall(Command call) {
        callList[callList.size()] = call
    }

    /**
     * Checks if there is a call in the same direction the
     * elevator is moving
     *
     * @return true if a call exists
     */
    private boolean callInSameDirection() {
        if (direction == "up") {
            return callUpFromFloor()
        }
        else {
            return callDownFromFloor()
        }
    }

    /**
     * Checks if there is a call in the opposite direction the
     * elevator is moving
     *
     * @return true if a call exists
     */
    private boolean callInOppositeDirection() {
        if (direction == "up") {
            return callDownFromFloor()
        }
        else {
            return callUpFromFloor()
        }
    }

    /**
     * Checks if there is a call up from the
     * elevator's current location
     *
     * @return true if a call exists
     */
    private boolean callUpFromFloor() {
        for (i in 0..callList.size()-1) {
            if (callList[i].floor > currentFloor) {
                destination = callList[i].floor
                respondingCall = callList[i]
                return true
            }
        }
        return false
    }

    /**
     * Checks if there is a call down from the
     * elevator's current location
     *
     * @return true if a call exists
     */
     private boolean callDownFromFloor() {
        for (i in 0..callList.size()-1) {
            if (callList[i].floor < currentFloor) {
                destination = callList[i].floor
                respondingCall = callList[i]
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
