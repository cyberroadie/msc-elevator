package ac.bbk.oodp.elevator

@GrabResolver(name='gpars', root='http://snapshots.repository.codehaus.org/', m2Compatible=true)
@GrabResolver(name='jboss', root='http://repository.jboss.org/maven2/')
@GrabResolver(name = 'gpars', root = 'http://snapshots.repository.codehaus.org/', m2Compatible = true)

/**
 * @author Olivier Van Acker, Richard Brown
 * Date: 27/02/11
 */
@Grab(group='joda-time', module='joda-time', version='1.6.2')
def controller = new Controller(new BufferedReader(new FileReader(this.args[0]))).run()
