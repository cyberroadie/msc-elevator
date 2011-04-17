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
class Elevator {

    int elevatorNumber
    int currentFloor
    int destination
    int floorsTravelled = 0
    int passengersDelivered = 0
    int distanceTravelled = 0
    int waitTime = 0
    int travelTime = 0
    String direction = "NONE"
    static List waitingFloors = []
    List currentCalls  = []
    boolean operational = true
    boolean moving = false
    boolean betweenFloors = false

    Elevator(int elevator, int startingFloor) {
        elevatorNumber = elevator
        currentFloor = startingFloor
    }

    /**
     * Runs through the logic required for each tick of the clock
     */
    void respondToClock() {
        if (!operational) return
        move()
        decrementWait()
        if (travelTime == 0) {
            letPassengerOff()
        }
    }

    /**
     * Returns whether an elevator is stopped at the specified floor
     *
     * @param floor the floor to check for
     * @return boolean true if elevator is stopped at floor, false otherwise
     */
    public boolean stoppedAtFloor(int floor) {
        if (!moving && operational && currentFloor == floor) return true
        return false
    }

    /**
     * Returns whether an elevator has just arrived, but not stopped, at the specified floor
     *
     * @param floor the floor to check for
     * @return boolean true if elevator is stopped at floor, false otherwise
     */
    public boolean justArrivedAtFloor(int floor) {
        if (currentFloor == floor && operational && !betweenFloors) return true
        return false
    }

    /**
     * Receives a call and places it in the list of current calls
     *
     * @param call the call to add to the current list
     */
    public void sendCall(Command call) {
        currentCalls.add(call)
        setWaiting()
    }

    /**
     * Decrements the waiting time
     *
     * If full waiting time has elapsed set the elevator to moving
     */
    private void decrementWait() {
        if (moving || direction == "NONE" || betweenFloors) return
        waitTime--
        if (waitTime == 0) startMoving()
    }

    /**
     * Moves the elevator one second in the relevant direction
     *
     * If elevator has reached a new floor, change the current floor
     * and check if there are passengers on that floor
     */
    private void move() {
        if (!moving) return

        if (direction == "UP")
            incrementTravelTime()
        else
            decrementTravelTime()

        if (travelTime % 10 == 0)
            if (travelTime != 0) changeFloor()
    }

    /**
     * Move the elevator up one second
     */
    private void incrementTravelTime() {
        travelTime+=1
        betweenFloors = true
    }

    /**
     * Move the elevator down one second
     */
    private void decrementTravelTime() {
        travelTime-=1
        betweenFloors = true
    }

    /**
     * start the elevator moving
     * Sets moving to true and sets betweenFloors to true
     */
    private void startMoving() {
        moving = true
        betweenFloors = true
    }

    /**
     * Change the current floor number
     * If elevator is moving up add one to the floor number
     * If elevator is moving down subtract one from the floor number
     */
    private void changeFloor() {
        currentFloor = (direction == "UP") ? currentFloor + 1 : currentFloor - 1
        distanceTravelled++
        travelTime = 0
        betweenFloors = false
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
        betweenFloors = false
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
        return (waitingFloors.contains(destination))
    }

    /**
     * Updates the destination
     *
     * Checks if the destination is valid, if it is it returns
     * If not it will set direction to
     * 1 a waiting call in the same direction
     * 2 the destination of a current passenger
     * 3 a waiting call in the opposite direction
     * if none available stops the elevator
     */
    private void updateDestination() {
        if (destinationValid()) return
        if (callInSameDirection()) {
            return
        }
        else if (currentCalls.size() > 0) {
            destinationFromCallList()
            return
        }
        else if (callInOppositeDirection()) {
            return
        }
        else if (waitingFloors.size() > 0) {
            destinationWhenNoDirection()
            return
        }
        moving = false
    }

    /**
     * sets the direction to the destination of the first passenger and starts the
     * elevator moving in the direction of the destination
     *
     * if destination is current floor stops elevator
     */
    private destinationFromCallList () {
        destination = currentCalls[0].dest
         if(destination == currentFloor)
             moving = false
         else {
             direction = destination > currentFloor ? "UP" : "DOWN"
             if (!moving) startMoving()
         }
    }

    /**
     * sets the destination to a call in the waiting list and starts the elevator
     * moving in the direction of the call
     */
    private destinationWhenNoDirection() {
        destination = waitingFloors[0]
        if(destination == currentFloor)
            moving = false
        else  {
            direction = destination > currentFloor ? "UP" : "DOWN"
            if (!moving) startMoving()
        }
    }

    /**
     * Checks if there is a call in the same direction the
     * elevator is moving
     *
     * @return true if a call exists
     */
    private boolean callInSameDirection() {
        if (direction == "UP") {
            return callUpFromFloor()
        }
        else if (direction == "DOWN") {
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
        if (direction == "UP") {
            return callDownFromFloor()
        }
        else if (direction == "DOWN") {
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
        if (waitingFloors.size() > 0) {
            for (i in 0..waitingFloors.size()-1) {
                if (waitingFloors[i] > currentFloor) {
                    destination = waitingFloors[i]
                    direction = "UP"
                    if (!moving) startMoving()
                    return true
                }
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
         if (waitingFloors.size() > 0) {
            for (i in 0..waitingFloors.size()-1) {
                if (waitingFloors[i] < currentFloor) {
                    destination = waitingFloors[i]
                    direction = "DOWN"
                    if (!moving) startMoving()
                    return true
                }
            }
        }
        return false
    }

    /**
     * set the elevator as non operational
     */
    public void fail() {
        operational = false
    }

    /**
     * sets the elevator to operational
     */
    public void fix() {
        operational = true
    }

    /**
     * returns the current status of the elevator
     * a combination of stopped/working and moving/stoppped
     *
     * @return String the current status
     */
    public def getStatus() {
        return ("${operational ? "WORKING" : "BROKEN"}, ${moving ? "MOVING" : "STOPPED"}")
    }

    /**
     * returns a string containing the elevator number, current location,
     * current direction of travel, current status (broken, stopped, moving, etc.)
     * and a list of the names of all current passengers

     * @return String the current state of the elevator
     */
    public String display() {
        "Elevator\t$elevatorNumber\tfloor\t$currentFloor\tdestination\t$destination\tdirection\t$direction\t\tstatus\t${getStatus()}\tpassengers:\t[${getPassengerNames()}]"
    }

    /**
     * returns a string containing the elevator number, number of passengers delivered,
     * current direction of travel, current status (broken, stopped, moving, etc.)
     * and a list of the names of all current passengers

     * @return String the current state of the elevator
     */
    public String stats() {
        """Elevator\t$elevatorNumber\tdelivered\t$passengersDelivered\tpassengers\t[${getPassengerNames()}]\tdistance\t$distanceTravelled"""
    }

    /**
     * returns the list of current passengers' names as a comma separated string
     *
     * @return String the list of names to be returned
     */
    public String getPassengerNames() {
        def passengerNames = []
        currentCalls.collect( passengerNames ) {
            it.passenger.name
        }
        if(passengerNames.size() == 0)
            return ""
        return passengerNames.join(', ')
    }
}
