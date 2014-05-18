package boersenspiel;
import java.util.Timer;

public class Zeitgeist extends Timer{
    private static Zeitgeist theInstance;

    private Zeitgeist() {
    }

    public static Zeitgeist getInstance() {
        if (theInstance == null)
            theInstance = new Zeitgeist();
        return theInstance;
    }
}