package ac.bbk.oodp.elevator

/**
 * @author Olivier Van Acker, Richard Brown
 * Date: 27/02/11
 */
@Grapes(
    @Grab(group='joda-time', module='joda-time', version='1.6.2')
)

def clock = new Clock(endTime: 100).start()
def controller = new Controller(3, clock).start()

[clock, controller]*.join()