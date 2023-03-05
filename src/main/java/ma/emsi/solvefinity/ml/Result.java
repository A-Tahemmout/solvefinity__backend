package ma.emsi.solvefinity.ml;

import ma.emsi.solvefinity.ml.math.Vec;

/**
 * Result of a neural network.
 *
 * @author Tahemmout Aymane
 * @implNote This class contains the result of a neural network.
 * The output of an evaluation will always contain the output data.
 */
public class Result {
    private final Vec output;
    private final Double cost;

    public Result(Vec output) {
        this.output = output;
        cost = null;
    }

    public Result(Vec output, double cost) {
        this.output = output;
        this.cost = cost;
    }

    public Vec getOutput() {
        return output;
    }

    public Double getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return "Result{" + "output=" + output + ", cost=" + cost + '}';
    }
}
