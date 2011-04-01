package ac.bbk.oodp.elevator

/**
 * @author Olivier Van Acker, Richard Brown
 * Date: 27/02/11
 */
class ControllerTest extends GroovyTestCase {

    void testControllerCreation() {
        BufferedReader reader = new BufferedReader(
                new StringReader("StartTime:\t14:00:00\n" +
                                 "NumberOfFloors:\t12\n" +
                                 "NumberOfElevators:\t3"))
        Clock clock = new Clock()
        Controller controller = new Controller(reader, clock)
        assertEquals("14:00:00", clock.startTime)
        assertEquals(12, controller.numberOfFloors)
        assertEquals(3, controller.elevatorList.size())
    }

}
