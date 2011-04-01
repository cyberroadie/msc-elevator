package ac.bbk.oodp.elevator

/**
 * @author Olivier Van Acker, Richard Brown
 */
class ElevatorTest extends GroovyTestCase {

    void testToString() {
        Elevator testElevator = new Elevator(2)
        testElevator.setPassengers(['Passenger 1','Passenger 2'])
        assertEquals('Elevator Number: 2\nCurrent location: null\nCurrent passengers: Passenger 1, Passenger 2',testElevator)
    }
}
