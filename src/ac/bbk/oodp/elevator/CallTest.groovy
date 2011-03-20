package ac.bbk.oodp.elevator

/**
 * @author Olivier Van Acker, Richard Brown
 * Date: 21/02/2011
 */

class CallTest extends GroovyTestCase {

    void testConstructor() {
        Call testCall = new Call("Richard Brown  3  12:30:02   5")
        assertEquals(testCall.getName(),"Richard Brown")
        assertEquals(testCall.getFloor(),3)
        assertEquals(testCall.getTime(),"12:30:02")
        assertEquals(testCall.getDest(),5)
    }

}

class FailTest extends GroovyTestCase {

    void testConstructor() {
        Command testFail = new Fail("4 12:30:02")
        assertEquals(testFail.getElevatorNumber(),4)
        assertEquals(testFail.getTime(),"12:30:02")
    }

}

class FixTest extends GroovyTestCase {

    void testConstructor() {
        Fix testFix = new Fix("4    12:30:02")
        assertEquals(testFix.getElevatorNumber(),4)
        assertEquals(testFix.getTime(),"12:30:02")
    }

}

class DisplayTest extends GroovyTestCase {

    void testConstructor() {
        Display testDisplay
        testDisplay = new Display("12:30:02")
        assertEquals(testDisplay.getTime(),"12:30:02")
    }

}

class StatusTest extends GroovyTestCase {

    void testConstructor() {
        Status testStatus = new Status("12:30:02")
        assertEquals(testStatus.getTime(),"12:30:02")
    }

}
