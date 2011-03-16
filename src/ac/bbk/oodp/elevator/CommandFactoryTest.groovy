package ac.bbk.oodp.elevator

/**
 * Created by IntelliJ IDEA.
 * User: brownr22
 * Date: 16/03/2011
 * Time: 11:38
 * To change this template use File | Settings | File Templates.
 */
class CommandFactoryTest extends GroovyTestCase {

    void testGetCommand() {
        Command testCall = CommandFactory.getCommand('call\tRichard Brown\t3\t12:30:02\t5')
        assertEquals("Richard Brown",testCall.getName())
        Command testFail = CommandFactory.getCommand("fail\t4\t12:30:02")
        assertEquals(testFail.getElevatorNumber(),4)
    }
}
