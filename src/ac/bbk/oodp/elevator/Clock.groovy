package ac.bbk.oodp.elevator


import groovyx.gpars.actor.DefaultActor

/**
 * @author Olivier Van Acker, Richard Brown
 * Date: 21/02/2011
 */

class Clock extends DefaultActor {
    String startTime
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
