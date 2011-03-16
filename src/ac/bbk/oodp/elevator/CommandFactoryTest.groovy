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
        assertEquals("Richard Brown",testCall.getName())
        assertEquals(3,testCall.getFloor())
        assertEquals("12:30:02",testCall.getTime())
        assertEquals(5,testCall.getDest())
    }

    void testNewFail() {
        Command testFail = CommandFactory.getCommand("fail\t4\t12:30:02")
        assertEquals(4,testFail.getElevatorNumber())
        assertEquals("12:30:02",testFail.getTime())
    }

    void testNewFix() {
        Command testFix = CommandFactory.getCommand("fix\t4\t12:30:02")
        assertEquals(4,testFix.getElevatorNumber())
        assertEquals("12:30:02",testFix.getTime())
    }

    void testNewDisplay() {
        Command testDisplay = CommandFactory.getCommand("display\t12:30:02")
        assertEquals("12:30:02",testDisplay.getTime())
    }

    void testNewStatus() {
        Command testStatus = CommandFactory.getCommand("display\t12:30:02")
        assertEquals("12:30:02",testStatus.getTime())
    }

}
