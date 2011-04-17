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
        this.reader = new BufferedReader(
                new StringReader("StartTime:\t14:00:00\n" +
                        "NumberOfFloors:\t12\n" +
                        "NumberOfElevators:\t3\n" +
                        "init\t0\t1\n" +
                        "init\t0\t6\n" +
                        "init\t0\t12\n"
                ))
    }

    @Override
    protected void tearDown() throws Exception {
        System.setSecurityManager(null); // or save and restore original
        super.tearDown();
    }

    void testTerminate() {
        def controller = new Controller(reader)

        try {
            controller.run();
            fail()
        } catch (ExitException e) {
            assertEquals("Exit status", 0, e.status);
        }

    }

}
