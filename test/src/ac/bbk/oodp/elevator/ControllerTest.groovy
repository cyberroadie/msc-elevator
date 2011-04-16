package ac.bbk.oodp.elevator

/**
 * @author Olivier Van Acker, Richard Brown
 * Date: 27/02/11
 */
class ControllerTest extends GroovyTestCase {

    BufferedReader reader

    void setUp() {
        this.reader = new BufferedReader(
                new StringReader("StartTime:\t14:00:00\n" +
                                 "NumberOfFloors:\t12\n" +
                                 "NumberOfElevators:\t3\n" +
                                 "init\t0\t1\n" +
                                 "init\t0\t6\n" +
                                 "init\t0\t12\n"
                ))
    }

    void testControllerCreation() {

        Clock clock = new Clock()
        clock.start()
        Controller controller = new Controller(reader, clock)
        sleep(100)
        assertEquals("14:00:00", clock.getNextStringTime())
        assertEquals(12, controller.numberOfFloors)
        assertEquals(3, controller.elevatorList.size())

        assertEquals(1, controller.elevatorList[0].currentFloor)
        assertEquals(6, controller.elevatorList[1].currentFloor)
        assertEquals(12, controller.elevatorList[2].currentFloor)
    }

    void testSendCallToElevator() {
        Controller controller = new Controller(reader)

        controller.sendCallToElevator(controller.elevatorList[0],CommandFactory.getCommand("call\tTess\t5\t12:30:02\t3"))

        assertEquals(1,controller.elevatorList[0].currentCalls.size())
    }

    void testAssignCallsToStoppedElevators() {
        Controller controller = new Controller(reader)

        controller.elevatorList[0].moving = true
        controller.elevatorList[0].currentFloor = 5
        controller.elevatorList[1].moving = false
        controller.elevatorList[1].currentFloor = 5
        controller.callList.add(CommandFactory.getCommand("call\tTess\t5\t12:30:02\t3"))
        controller.assignCallsToStoppedElevators()

        assertEquals(1,controller.elevatorList[1].currentCalls.size())
        assertEquals(0,controller.elevatorList[0].currentCalls.size())
    }

    void testAssignCallsToJustArrivedElevators() {
        Controller controller = new Controller(reader)

        controller.elevatorList[0].moving = true
        controller.elevatorList[0].betweenFloors = true
        controller.elevatorList[0].currentFloor = 5
        controller.elevatorList[1].moving = true
        controller.elevatorList[1].betweenFloors = false
        controller.elevatorList[1].currentFloor = 5
        controller.callList.add(CommandFactory.getCommand("call\tTess\t5\t12:30:02\t3"))
        controller.assignCallsToJustArrivedElevators()

        assertEquals(1,controller.elevatorList[1].currentCalls.size())
        assertEquals(0,controller.elevatorList[0].currentCalls.size())
    }
}
