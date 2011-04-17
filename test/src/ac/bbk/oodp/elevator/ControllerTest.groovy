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
        Controller controller = new Controller(reader)
        assertEquals(12, controller.numberOfFloors)
        assertEquals(3, controller.elevatorList.size())

        assertEquals(1, controller.elevatorList[0].currentFloor)
        assertEquals(6, controller.elevatorList[1].currentFloor)
        assertEquals(12, controller.elevatorList[2].currentFloor)
    }

    void testSendCallToElevator() {
        Controller controller = new Controller(reader)

        controller.sendCallToElevator(controller.elevatorList[0], CommandFactory.getCommand("call\tTess\t5\t12:30:02\t3"))

        assertEquals(1, controller.elevatorList[0].currentCalls.size())
    }

    void testAssignCallsToStoppedElevators() {
        Controller controller = new Controller(reader)

        controller.elevatorList[0].moving = true
        controller.elevatorList[0].currentFloor = 5
        controller.elevatorList[1].moving = false
        controller.elevatorList[1].currentFloor = 5
        controller.callList.add(CommandFactory.getCommand("call\tTess\t5\t12:30:02\t3"))
        controller.assignCallsToStoppedElevators()

        assertEquals(1, controller.elevatorList[1].currentCalls.size())
        assertEquals(0, controller.elevatorList[0].currentCalls.size())
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

        assertEquals(1, controller.elevatorList[1].currentCalls.size())
        assertEquals(0, controller.elevatorList[0].currentCalls.size())
    }

    void testValidateCallCommand() {
        def controller = new Controller(reader)
        controller.numberOfFloors = 5

        def call = CommandFactory.getCommand("call\tBetty\t6\t14:21:00\t4")

        try {
            controller.validateCommand(call)
            fail()
        } catch (CommandException ex) {

        }

        call = CommandFactory.getCommand("call\tBetty\t4\t14:21:00\t6")

        try {
            controller.validateCommand(call)
            fail()
        } catch (CommandException ex) {

        }
    }

    void testValidateFailFixCommand() {
        def controller = new Controller(reader)
        controller.numberOfElevators = 4

        def failCommand = CommandFactory.getCommand("fail\t5\t14:15:00")

        try {
            controller.validateCommand(failCommand)
            fail()
        } catch (CommandException ex) {

        }

        def fix = CommandFactory.getCommand("fix\t6\t14:15:00")

        try {
            controller.validateCommand(fix)
            fail()
        } catch (CommandException ex) {

        }
    }
}
