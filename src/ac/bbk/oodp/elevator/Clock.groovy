package ac.bbk.oodp.elevator


import groovyx.gpars.actor.DefaultActor
import org.joda.time.DateTime

import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter

/**
 * @author Olivier Van Acker, Richard Brown
 * Date: 21/02/2011
 */

class Clock extends DefaultActor {
    DateTime startTime
    int cycle;

    void act() {
        loop {
            react {String message ->
                if(message == 'next') {
                    increaseTime()
                    reply getNextStringTime()
                } else if(message.startsWith("StartTime:"))
                    initializeClock(message)
            }
        }
    }

    void increaseTime() {
        startTime = startTime.plusSeconds(1)
    }

    void initializeClock(String timeString) {
        DateTimeFormatter fmt = DateTimeFormat.forPattern("HH:mm:ss");
        startTime = fmt.parseDateTime(timeString.split("\t")[1])
    }

    String getNextStringTime() {
        startTime.toString("HH:mm:ss")
    }
}
