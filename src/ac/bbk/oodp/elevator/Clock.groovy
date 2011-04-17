package ac.bbk.oodp.elevator


import org.joda.time.DateTime

import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter

/**
 * Clock, maintains the time for the simulator
 * @author Olivier Van Acker, Richard Brown
 * Date: 21/02/2011
 */
class Clock {
    DateTime startTime
    int cycle;

    /**
     * Adds one second (tick) to time
     * @return time as string in format HH:mm:ss
     */
    def increaseByOneSecond() {
        startTime = startTime.plusSeconds(1)
        return startTime.toString("HH:mm:ss")
    }

    def getCurrentTime() {
        return startTime.toString("HH:mm:ss")
    }

    /**
     * Initializes the clock
     * @param timeString start time in format HH:mm:ss
     */
    public void initializeClock(String timeString) {
        DateTimeFormatter fmt = DateTimeFormat.forPattern("HH:mm:ss");
        startTime = fmt.parseDateTime(timeString.split("\t")[1])
    }

}
