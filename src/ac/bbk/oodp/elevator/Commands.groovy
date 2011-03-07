package ac.bbk.oodp.elevator

/**
 * @author Olivier Van Acker, Richard Brown
 * Date: 21/02/2011
 */
class Commands {

    int time

}


class Call extends Commands {
    String name
    int floor
    int dest

    void Call(String name, int floor, int time, int dest) {

    }
}

class Fail extends Commands {
    int elevatornumber

    void Fail(int elevatornumber, int time) {

    }
}

class Fix extends Commands {
    int elevatornumber

    void Fix(int elevatornumber, int time) {

    }
}

class Display extends Commands {

    void Display(int time) {

    }
}

class Status extends Commands {

    void Status(int time) {

    }
}

