package ac.bbk.oodp.elevator

/**
 * Created by IntelliJ IDEA.
 * User: brownr22
 * Date: 15/03/2011
 * Time: 20:35
 * To change this template use File | Settings | File Templates.
 */
class ElevatorTest extends GroovyTestCase {

    void testToString() {
        Elevator testElevator = new Elevator(2)
        testElevator.setPassengers(['Passenger 1','Passenger 2'])
        assertEquals('Elevator Number: 2\nCurrent location: null\nCurrent passengers: Passenger 1, Passenger 2',testElevator)
    }
}
