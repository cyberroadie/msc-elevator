package ac.bbk.oodp.elevator

@GrabResolver(name='gpars', root='http://snapshots.repository.codehaus.org/', m2Compatible=true)
@GrabResolver(name='jboss', root='http://repository.jboss.org/maven2/')
@GrabResolver(name = 'gpars', root = 'http://snapshots.repository.codehaus.org/', m2Compatible = true)

/**
 * @author Olivier Van Acker, Richard Brown
 * Date: 27/02/11
 */
@Grab(group='joda-time', module='joda-time', version='1.6.2')
@Grab(group='org.codehaus.gpars', module='gpars', version='0.12-beta-1-SNAPSHOT')
@Grab(group = 'org.codehaus.gpars', module = 'gpars', version = '0.12-beta-1-SNAPSHOT')
def clock = new Clock(endTime: 100).start()
def controller = new Controller(new BufferedReader(new FileReader("test.txt")), clock).start()

[clock, controller]*.join()