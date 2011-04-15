package ac.bbk.oodp.elevator

/**
 * @author Olivier Van Acker, Richard Brown
 */
class ElevatorTest extends GroovyTestCase {

    Elevator testElevator1

    void setUp() {
        Elevator.callList = []
    }

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
        testElevator1.direction = "UP"
        testElevator1.move()
        assertEquals(5,testElevator1.travelTime)

        testElevator1.moving = true
        testElevator1.travelTime = 4
        testElevator1.direction = "DOWN"
        testElevator1.move()
        assertEquals(3,testElevator1.travelTime)

        testElevator1.moving = true
        testElevator1.travelTime = 1
        testElevator1.direction = "DOWN"
        testElevator1.move()
        assertEquals(0,testElevator1.travelTime)
        assertEquals(5,testElevator1.currentFloor)

        testElevator1.moving = true
        testElevator1.travelTime = -9
        testElevator1.direction = "DOWN"
        testElevator1.move()
        assertEquals(0,testElevator1.travelTime)
        assertEquals(4,testElevator1.currentFloor)

        testElevator1.moving = true
        testElevator1.travelTime = 9
        testElevator1.direction = "UP"
        testElevator1.move()
        assertEquals(0,testElevator1.travelTime)
        assertEquals(5,testElevator1.currentFloor)

        testElevator1.moving = false
        testElevator1.travelTime = 4
        testElevator1.direction = "DOWN"
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

    void testDestinationValid() {
        this.testElevator1 = new Elevator(1,5)
        Elevator.addCall(CommandFactory.getCommand("call\tTess\t2\t12:30:02\t3"))
        assertTrue(!this.testElevator1.destinationValid())

        this.testElevator1.respondingCall = Elevator.callList[0]
        assertTrue(this.testElevator1.destinationValid())
    }

    void testUpdateDestination() {
        this.testElevator1 = new Elevator(1,5)
        Elevator.addCall(CommandFactory.getCommand("call\tTess\t2\t12:30:02\t3"))

        this.testElevator1.updateDestination()
        assertEquals(2,this.testElevator1.destination)

        setUp()
        Elevator.addCall(CommandFactory.getCommand("call\tTess\t2\t12:30:02\t3"))
        Elevator.addCall(CommandFactory.getCommand("call\tBob\t7\t12:30:02\t3"))
        Elevator.addCall(CommandFactory.getCommand("call\tTrudy\t9\t12:30:02\t3"))
        testElevator1.moving = true
        testElevator1.direction = "UP"
        this.testElevator1.updateDestination()
        assertEquals(7,this.testElevator1.destination)

        setUp()
        Elevator.addCall(CommandFactory.getCommand("call\tTess\t2\t12:30:02\t3"))
        Elevator.addCall(CommandFactory.getCommand("call\tBob\t7\t12:30:02\t3"))
        Elevator.addCall(CommandFactory.getCommand("call\tTrudy\t9\t12:30:02\t3"))
        this.testElevator1.currentCalls[0] = Elevator.callList[1]
        testElevator1.moving = true
        testElevator1.currentFloor = 1
        testElevator1.direction = "DOWN"
        this.testElevator1.updateDestination()
        assertEquals(3,this.testElevator1.destination)
    }

    void testChangeFloor() {
        this.testElevator1 = new Elevator(1,5)

        this.testElevator1.travelTime = 10
        this.testElevator1.direction = "UP"
        this.testElevator1.changeFloor()
        assertEquals(0,this.testElevator1.travelTime)
        assertEquals(6,this.testElevator1.currentFloor)

        this.testElevator1.travelTime = -10
        this.testElevator1.direction = "DOWN"
        this.testElevator1.changeFloor()
        assertEquals(0,this.testElevator1.travelTime)
        assertEquals(5,this.testElevator1.currentFloor)
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

        println Elevator.callList.size()
        Elevator.addCall(CommandFactory.getCommand("call\tTess\t2\t12:30:02\t3"))
        Elevator.addCall(CommandFactory.getCommand("call\tDave\t2\t12:30:03\t3"))
        testElevator1.currentCalls[0] = Elevator.callList[0]
        testElevator1.currentCalls[1] = Elevator.callList[1]
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
        Elevator.addCall(CommandFactory.getCommand("call\tTess\t2\t12:30:02\t3"))

        testElevator1.currentFloor = 1
        assertTrue(testElevator1.callUpFromFloor())

        testElevator1.currentFloor = 6
        assertFalse(testElevator1.callUpFromFloor())
    }

    void testCallDownFromFloor() {
        this.testElevator1 = new Elevator(1,5)
        Elevator.addCall(CommandFactory.getCommand("call\tTess\t2\t12:30:02\t3"))

        testElevator1.currentFloor = 7
        assertTrue(testElevator1.callDownFromFloor())

        testElevator1.currentFloor = 1
        assertFalse(testElevator1.callDownFromFloor())
    }

    void testDisplay() {
        this.testElevator1 = new Elevator(1,5)
        this.testElevator1.currentCalls[0] = CommandFactory.getCommand("call\tTess\t2\t12:30:02\t3")
        assertEquals("Elevator Number: 1\nCurrent location: 5\nCurrent passengers: Tess",this.testElevator1.display())
    }
}
