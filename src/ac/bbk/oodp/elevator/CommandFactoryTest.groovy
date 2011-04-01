package ac.bbk.oodp.elevator

/**
 * Created by IntelliJ IDEA.
 * User: brownr22
 * Date: 16/03/2011
 * Time: 11:38
 * To change this template use File | Settings | File Templates.
 */
class CommandFactoryTest extends GroovyTestCase {

    void testNewCall() {
        Command testCall = CommandFactory.getCommand('call\tRichard Brown\t3\t12:30:02\t5')
        assertEquals("Call",testCall.getClass().simpleName)
        assertEquals("Richard Brown",testCall.getName())
        assertEquals(3,testCall.getFloor())
        assertEquals("12:30:02",testCall.getTime())
        assertEquals(5,testCall.getDest())
    }

    void testNewFail() {
        Command testFail = CommandFactory.getCommand("fail\t4\t12:30:02")
        assertEquals("Fail",testFail.getClass().simpleName)
        assertEquals(4,testFail.getElevatorNumber())
        assertEquals("12:30:02",testFail.getTime())
    }

    void testNewFix() {
        Command testFix = CommandFactory.getCommand("fix\t4\t12:30:02")
        assertEquals("Fix",testFix.getClass().simpleName)
        assertEquals(4,testFix.getElevatorNumber())
        assertEquals("12:30:02",testFix.getTime())
    }

    void testNewDisplay() {
        Command testDisplay = CommandFactory.getCommand("display\t12:30:02")
        assertEquals("Display",testDisplay.getClass().simpleName)
        assertEquals("12:30:02",testDisplay.getTime())
    }

    void testNewStatus() {
        Command testStatus = CommandFactory.getCommand("status\t12:30:02")
        assertEquals("Status",testStatus.getClass().simpleName)
        assertEquals("12:30:02",testStatus.getTime())
    }

    void testAll() {
        def f = new File("/Users/brownr22/Documents/commands.txt")
        List commands = []
        f.eachLine() {
            commands[commands.size()] = CommandFactory.getCommand(it)
        }
        List commands2 = []
        commands2[0] = "Call"
        commands2[1] = "Fail"
        commands2[2] = "Fix"
        commands2[3] = "Display"
        commands2[4] = "Status"
        for (i in 1..commands2.size()-1) {
            assertEquals(commands2[i],commands[i].class.simpleName)
        }
    }

}
