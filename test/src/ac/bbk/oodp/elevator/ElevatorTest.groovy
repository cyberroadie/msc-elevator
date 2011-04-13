package ac.bbk.oodp.elevator

/**
 * @author Olivier Van Acker, Richard Brown
 */
class ElevatorTest extends GroovyTestCase {

    Elevator testElevator1

    void testDecrementWait() {
        this.testElevator1 = new Elevator(1,5)

        testElevator1.waitTime = 4
        testElevator1.decrementWait()
        assertEquals(3,testElevator1.waitTime)
    }

    void testMove() {
        this.testElevator1 = new Elevator(1,5)

        testElevator1.moving = true
        testElevator1.travelTime = 4
        testElevator1.direction = "up"
        testElevator1.move()
        assertEquals(5,testElevator1.travelTime)

        testElevator1.moving = true
        testElevator1.travelTime = 4
        testElevator1.direction = "down"
        testElevator1.move()
        assertEquals(3,testElevator1.travelTime)

        testElevator1.moving = true
        testElevator1.travelTime = 1
        testElevator1.direction = "down"
        testElevator1.move()
        assertEquals(0,testElevator1.travelTime)
        assertEquals(5,testElevator1.currentFloor)

        testElevator1.moving = true
        testElevator1.travelTime = -9
        testElevator1.direction = "down"
        testElevator1.move()
        assertEquals(0,testElevator1.travelTime)
        assertEquals(4,testElevator1.currentFloor)

        testElevator1.moving = true
        testElevator1.travelTime = 9
        testElevator1.direction = "up"
        testElevator1.move()
        assertEquals(0,testElevator1.travelTime)
        assertEquals(5,testElevator1.currentFloor)

        testElevator1.moving = false
        testElevator1.travelTime = 4
        testElevator1.direction = "down"
        testElevator1.move()
        assertEquals(4,testElevator1.travelTime)
    }

    void testIncrementTravelTime() {
        this.testElevator1 = new Elevator(1,5)

        testElevator1.travelTime = 4
        testElevator1.incrementTravelTime()
        assertEquals(5,testElevator1.travelTime)
    }

    void testDecrementTravelTime() {
        this.testElevator1 = new Elevator(1,5)

        testElevator1.travelTime = 4
        testElevator1.decrementTravelTime()
        assertEquals(3,testElevator1.travelTime)
    }

    void testLetPassengerOn() {
        this.testElevator1 = new Elevator(1,5)

        testElevator1.currentFloor = 2
        testElevator1.letPassengerOn()
        assertEquals(testElevator1.callList[0],testElevator1.currentCalls[0])

        testElevator1.currentFloor = 3
        testElevator1.letPassengerOn()
        assertEquals(0,testElevator1.currentCalls.size())
    }

    void testLetPassengerOff() {
        this.testElevator1 = new Elevator(1,5)

        Elevator.addCall(CommandFactory.getCommand("call\tTess\t2\t12:30:02\t3"))
        testElevator1.currentCalls[0] = Elevator.callList[0]
        testElevator1.currentFloor = 3
        testElevator1.letPassengerOff()
        assertEquals(0,Elevator.callList.size())
        assertEquals(0,testElevator1.currentCalls.size())

        Elevator.addCall(CommandFactory.getCommand("call\tTess\t2\t12:30:02\t3"))
        testElevator1.currentCalls[0] = Elevator.callList[0]
        testElevator1.currentFloor = 4
        testElevator1.letPassengerOff()
        assertEquals(1,Elevator.callList.size())
        assertEquals(1,Elevator.callList.size())
    }

    void testCallUpFromFloor() {
        this.testElevator1 = new Elevator(1,5)

        testElevator1.currentFloor = 1
        assertTrue(testElevator1.callUpFromFloor())

        testElevator1.currentFloor = 6
        assertFalse(testElevator1.callUpFromFloor())
    }

    void testCallDownFromFloor() {
        this.testElevator1 = new Elevator(1,5)

        testElevator1.currentFloor = 7
        assertTrue(testElevator1.callDownFromFloor())

        testElevator1.currentFloor = 1
        assertFalse(testElevator1.callDownFromFloor())
    }
}
