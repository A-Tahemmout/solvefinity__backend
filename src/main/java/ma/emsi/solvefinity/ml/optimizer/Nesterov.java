package ma.emsi.solvefinity.ml.optimizer;

import ma.emsi.solvefinity.ml.math.Matrix;
import ma.emsi.solvefinity.ml.math.Vec;

/**
 * Nesterov's Accelerated Gradient Descent optimizer class implementating the Optimizer interface.
 *
 * @author Tahemmout Aymane
 * @implNote The Nesterov's Accelerated Gradient Descent optimizer updates the weights and biases using the following equations:
 * v_prev = v
 * v = γ * v - η * dC/dW
 * W += -γ * v_prev + (1 + γ) * v
 * where:
 * v is the velocity vector (the momentum) (i.e. how much of the last gradient we want to keep)
 * v_prev is the previous velocity vector
 * γ is the momentum coefficient (the learning rate)
 * η is the learning rate
 * dC/dW is the derivative of the cost function with respect to the weights
 */
public class Nesterov implements Optimizer {
    private double learningRate;
    private double momentum;
    private Matrix lastDW;
    private Vec lastDB;

    public Nesterov(double learningRate, double momentum) {
        this.learningRate = learningRate;
        this.momentum = momentum;
    }

    public Nesterov(double learningRate) {
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
            lastDW = new Matrix(weights.rows(), weights.cols());
        }

        Matrix lastDWCopy = lastDW.copy();
        lastDW.mul(momentum).sub(dCdW.mul(learningRate));
        weights.add(lastDWCopy.mul(-momentum).add(lastDW.copy().mul(1 + momentum)));
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
            lastDB = new Vec(biases.dimension());
        }

        Vec lastDBCopy = lastDB;
        lastDB.mul(momentum).sub(dCdB.mul(learningRate));
        return biases.add(lastDBCopy.mul(-momentum).add(lastDB.mul(1 + momentum)));
    }

    /**
     * Create a copy of the optimizer.
     *
     * @return A new instance of the optimizer with the same configuration.
     */
    @Override
    public Optimizer copy() {
        return new Nesterov(learningRate, momentum);
    }
}
