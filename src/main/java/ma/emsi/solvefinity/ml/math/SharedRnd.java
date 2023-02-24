package ma.emsi.solvefinity.ml.math;

import java.util.Random;

/**
 * Shared random number generator.
 *
 * @author Tahemmout Aymane
 */
public class SharedRnd {
    private static Random rnd = new Random();

    public static Random getRnd() {
        return rnd;
    }

    public static void setRnd(Random rnd) {
        SharedRnd.rnd = rnd;
    }
}
