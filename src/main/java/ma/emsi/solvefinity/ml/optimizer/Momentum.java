package ma.emsi.solvefinity.ml.optimizer;

import ma.emsi.solvefinity.ml.math.Matrix;
import ma.emsi.solvefinity.ml.math.Vec;

/**
 * Momentum optimizer class implementing the {@link Optimizer} interface.
 *
 * @author Tahemmout Aymane
 * @implNote The momentum optimizer updates the weights and biases using the following equations:
 * v = γ * v_prev + η * dC/dW
 * W = W - v
 * where:
 * v is the velocity vector (the momentum) (i.e. how much of the last gradient we want to keep)
 * v_prev is the previous velocity vector
 * γ is the momentum coefficient (the learning rate)
 */
public class Momentum implements Optimizer {
    private double learningRate;
    private double momentum;
    private Matrix lastDW;
    private Vec lastDB;

    public Momentum(double learningRate, double momentum) {
        this.learningRate = learningRate;
        this.momentum = momentum;
    }

    public Momentum(double learningRate) {
        this(learningRate, 0.9);
    }

    /**
     * Update the weights using the given derivative of the cost function with respect to the weights.
     *
     * @param weights The weights to update
     * @param dCdW    dC/dW The derivative of the cost function with respect to the weights
     */
    @Override
    public void updateWeights(Matrix weights, Matrix dCdW) {
        if (lastDW == null) {
            lastDW = dCdW.copy().mul(learningRate);
        } else {
            lastDW.mul(momentum).add(dCdW.copy().mul(learningRate));
        }
        weights.sub(lastDW);
    }

    /**
     * Update the biases using the given derivative of the cost function with respect to the biases.
     *
     * @param biases The biases to update
     * @param dCdB   dC/dB The derivative of the cost function with respect to the biases
     */
    @Override
    public Vec updateBias(Vec biases, Vec dCdB) {
        if (lastDB == null) {
            lastDB = dCdB.mul(learningRate);
        } else {
            lastDB.mul(momentum).add(dCdB.mul(learningRate));
        }
        return biases.sub(lastDB);
    }

    @Override
    public Optimizer copy() {
        return new Momentum(learningRate, momentum);
    }
}
