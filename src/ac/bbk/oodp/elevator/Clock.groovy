package ac.bbk.oodp.elevator
@GrabResolver(name='gpars', root='http://snapshots.repository.codehaus.org/', m2Compatible=true)
@GrabResolver(name='jboss', root='http://repository.jboss.org/maven2/')

import groovyx.gpars.actor.DefaultActor

/**
 * @author Olivier Van Acker, Richard Brown
 * Date: 21/02/2011
 */
@Grab(group='org.codehaus.gpars', module='gpars', version='0.12-beta-1-SNAPSHOT')
class Clock extends DefaultActor {
    int cycle;
    int endTime

    void afterStart() {
        cycle = 0;
    }

    void act() {
        loop {
            react {
                if(cycle < endTime)
                    reply cycle++
                else
                    reply "finish"
            }
        }
    }
}
