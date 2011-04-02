package ac.bbk.oodp.elevator

/**
 * @author Olivier Van Acker, Richard Brown
 * Date: 02/04/11
 */
class ClockTest extends GroovyTestCase {

    void testInitialize() {
        Clock clock = new Clock()
        clock.initilizeClock("StartTime:\t14:00:00")
        assertEquals("14:00:00", clock.getNextStringTime())
    }

    void testNext() {
        Clock clock = new Clock()
        clock.initilizeClock("StartTime:\t14:00:00")
        assertEquals("14:00:00", clock.getNextStringTime())
        clock.increaseTime()
        assertEquals("14:00:01", clock.getNextStringTime())
    }
}
