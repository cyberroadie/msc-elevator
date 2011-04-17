package ac.bbk.oodp.elevator

import java.security.Permission

/**
 * @author Olivier Van Acker, Richard Brown
 * Date: 17/04/11
 */
class ControllerSystemExitTest extends GroovyTestCase {

    protected static class ExitException extends SecurityException {
        public final int status;

        public ExitException(int status) {
            super("There is no escape!");
            this.status = status;
        }
    }

    private static class NoExitSecurityManager extends SecurityManager {
        @Override
        public void checkPermission(Permission perm) {
            // allow anything.
        }

        @Override
        public void checkPermission(Permission perm, Object context) {
            // allow anything.
        }

        @Override
        public void checkExit(int status) {
            super.checkExit(status);
            throw new ExitException(status);
        }
    }

    BufferedReader reader

    void setUp() {
        System.setSecurityManager(new NoExitSecurityManager());

    }

    @Override
    protected void tearDown() throws Exception {
        System.setSecurityManager(null); // or save and restore original
        super.tearDown();
    }

    void testTerminate() {
    this.reader = new BufferedReader(
                new StringReader("StartTime:\t14:00:00\n" +
                        "NumberOfFloors:\t12\n" +
                        "NumberOfElevators:\t3\n" +
                        "init\t0\t1\n" +
                        "init\t1\t6\n" +
                        "init\t2\t12\n" +
                        "display\t14:00:01"
                ))
        def controller = new Controller(reader)

        try {
            controller.run();
            fail()
        } catch (ExitException e) {
            assertEquals("Exit status", 0, e.status);
        }

    }

    void testTerminateWrongAmountOfInits() {
    this.reader = new BufferedReader(
                new StringReader("StartTime:\t14:00:00\n" +
                        "NumberOfFloors:\t12\n" +
                        "NumberOfElevators:\t4\n" +
                        "init\t0\t1\n" +
                        "init\t1\t6\n" +
                        "init\t2\t12\n" +
                        "display\t14:00:01"
                ))


        try {
            def controller = new Controller(reader)
            fail()
        } catch (ExitException e) {
            assertEquals("Exit status", 1, e.status);
        }


    }
}