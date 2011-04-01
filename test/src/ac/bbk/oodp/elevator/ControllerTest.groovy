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
        Controller controller = new Controller(reader, clock)
        assertEquals("14:00:00", clock.startTime)
        assertEquals(12, controller.numberOfFloors)
        assertEquals(3, controller.elevatorList.size())

        assertEquals(1, controller.elevatorList[0].currentFloor)
        assertEquals(6, controller.elevatorList[1].currentFloor)
        assertEquals(12, controller.elevatorList[2].currentFloor)
    }

}
