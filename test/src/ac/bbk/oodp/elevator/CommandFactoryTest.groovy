package ac.bbk.oodp.elevator

/**
 * @author Olivier Van Acker, Richard Brown
 */
class CommandFactoryTest extends GroovyTestCase {

    void testNewCall() {
        def testCall = CommandFactory.getCommand('call\tRichard Brown\t3\t12:30:02\t5')
        assertTrue(testCall instanceof Call)
        assertEquals("Richard Brown",testCall.getPassenger().getName())
        assertEquals(3,testCall.getFloor())
        assertEquals("12:30:02",testCall.getTime())
        assertEquals(5,testCall.getDest())
    }

    void testNewFail() {
        def testFail = CommandFactory.getCommand("fail\t4\t12:30:02")
        assertTrue(testFail instanceof Fail)
        assertEquals(4,testFail.getElevatorNumber())
        assertEquals("12:30:02",testFail.getTime())
    }

    void testNewFix() {
        def testFix = CommandFactory.getCommand("fix\t4\t12:30:02")
        assertTrue(testFix instanceof Fix)
        assertEquals(4,testFix.getElevatorNumber())
        assertEquals("12:30:02",testFix.getTime())
    }

    void testNewDisplay() {
        def testDisplay = CommandFactory.getCommand("display\t12:30:02")
        assertTrue(testDisplay instanceof Display)
        assertEquals("12:30:02",testDisplay.getTime())
    }

    void testNewStatus() {
        def testStatus = CommandFactory.getCommand("stats\t12:30:02")
        assertTrue(testStatus instanceof Status)
        assertEquals("12:30:02",testStatus.getTime())
    }

    void testUnknownCommand() {
        try {
            CommandFactory.getCommand("blahblah\t12:30:02")
            fail()
        } catch (CommandException ex) {

        }
    }

    void testAll() {
        def commands = [
                "call\tFred\t12\t14:15:00\t1",
                "fail\t2\t14:15:00",
                "fix\t2	14:32:00",
                "display\t14:36:00",
                "stats\t14:39:00"
        ]

        def commandTypes = [
                Call.class,
                Fail.class,
                Fix.class,
                Display.class,
                Status.class
        ]

        for (i in 0..(commandTypes.size() - 1)) {
            assertEquals(commandTypes[i], CommandFactory.getCommand(commands[i]).class)
        }
    }

}
