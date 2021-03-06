package ac.bbk.oodp.elevator

import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import org.joda.time.DateTime

/**
 * @author Olivier Van Acker, Richard Brown
 * Date: 21/02/2011
 */

class CallTest extends GroovyTestCase {

    void testConstructor() {
        Call testCall = new Call("call\tRichard Brown\t3\t12:30:02\t5")
        assertEquals(testCall.getPassenger().name,"Richard Brown")
        assertEquals(testCall.getFloor(),3)
        assertEquals(testCall.getTime(),"12:30:02")
        assertEquals(testCall.getDest(),5)
    }

    void testJodaTime() {
        DateTimeFormatter fmt = DateTimeFormat.forPattern("HH:mm:ss");
        DateTime dt = fmt.parseDateTime("12:30:02");
        println dt
    }

}

class FailTest extends GroovyTestCase {

    void testConstructor() {
        Command testFail = new Fail("fail\t4\t12:30:02")
        assertEquals(testFail.getElevatorNumber(),4)
        assertEquals(testFail.getTime(),"12:30:02")
    }

}

class FixTest extends GroovyTestCase {

    void testConstructor() {
        Fix testFix = new Fix("fix\t4\t12:30:02")
        assertEquals(testFix.getElevatorNumber(),4)
        assertEquals(testFix.getTime(),"12:30:02")
    }

}

class DisplayTest extends GroovyTestCase {

    void testConstructor() {
        Display testDisplay
        testDisplay = new Display("display\t12:30:02")
        assertEquals(testDisplay.getTime(),"12:30:02")
    }

}

class StatusTest extends GroovyTestCase {

    void testConstructor() {
        Status testStatus = new Status("stats\t12:30:02")
        assertEquals(testStatus.getTime(),"12:30:02")
    }

}
