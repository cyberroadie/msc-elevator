package ac.bbk.oodp.elevator

/**
 * @author Olivier Van Acker, Richard Brown
 * Date: 13/03/2011
 */
class CommandParserTest extends GroovyTestCase {

    BufferedReader reader

    void setUp() {
        this.reader = new BufferedReader(
                new StringReader("display\t14:00:01\n" +
                                 "fail\t2\t14:00:02\n" +
                                 "call\tFred\t12\t14:00:03\t1\n" +
                                 "stats\t14:00:04\n"
                ))
    }

    void testReadNextLine() {
        def clock = new Clock()
        clock.initializeClock("init\t14:00:00")
        def testParser = new CommandParser(reader)
        assertNull(testParser.getNextCommand(clock))
        Command testCommand = testParser.getNextCommand(clock)
        assertTrue(testCommand instanceof Display)
        assertNull(testParser.getNextCommand(clock))
        testCommand = testParser.getNextCommand(clock)
        assertTrue(testCommand instanceof Fail)
        assertNull(testParser.getNextCommand(clock))
        testCommand = testParser.getNextCommand(clock)
        assertTrue(testCommand instanceof Call)
        assertNull(testParser.getNextCommand(clock))
        testCommand = testParser.getNextCommand(clock)
        assertTrue(testCommand instanceof Status)
    }

}
