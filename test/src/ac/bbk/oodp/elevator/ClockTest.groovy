package ac.bbk.oodp.elevator

/**
 * @author Olivier Van Acker, Richard Brown
 * Date: 02/04/11
 */
class ClockTest extends GroovyTestCase {

    void testInitialize() {
        Clock clock = new Clock()
        clock.initializeClock("StartTime:\t14:00:00")
        def result = clock.increaseByOneSecond()
        assertEquals("14:00:01", result)
    }

    void testNext() {
        Clock clock = new Clock()
        clock.initializeClock("StartTime:\t14:00:00")
        def result = clock.increaseByOneSecond()
        assertEquals("14:00:01", result)
    }
}
