package ac.bbk.oodp.elevator

/**
 * @author Olivier Van Acker, Richard Brown
 * Date: 13/03/2011
 */
class InputParserTest extends GroovyTestCase {

    void testReadNextLine() {
        def testParser = new InputParser("/Users/brownr22/Documents/commands.txt")
        Command testCommand = testParser.getNextCommand("12:30:02")
        println "${testCommand.getTime()} ${testCommand.class.simpleName}"
        testCommand = testParser.getNextCommand("12:30:03")
        println testCommand
        testCommand = testParser.getNextCommand("12:30:04")
        println "${testCommand.getTime()} ${testCommand.class.simpleName}"
        testCommand = testParser.getNextCommand("12:30:05")
        println testCommand
        testCommand = testParser.getNextCommand("12:30:06")
        println "${testCommand.getTime()} ${testCommand.class.simpleName}"
        testCommand = testParser.getNextCommand("12:30:07")
        println testCommand
        testCommand = testParser.getNextCommand("12:30:08")
        println "${testCommand.getTime()} ${testCommand.class.simpleName}"
        testCommand = testParser.getNextCommand("12:30:09")
        println testCommand
        testCommand = testParser.getNextCommand("12:30:10")
        println "${testCommand.getTime()} ${testCommand.class.simpleName}"

    }

}
