package ma.emsi.solvefinity.ml.optimizer;

import ma.emsi.solvefinity.ml.math.Matrix;
import ma.emsi.solvefinity.ml.math.Vec;

/**
 * Gradient descent optimizer class implementing the {@link Optimizer} interface.
 *
 * @author Tahemmout Aymane
 * @implNote The gradient descent optimizer updates the weights and biases using the following equations:
 * W = W - η * dC/dW
 * B = B - η * dC/dB
 * Where:
 * W is the weights matrix
 * B is the biases vector
 * η is the learning rate
 * dC/dW is the derivative of the cost function with respect to the weights
 * dC/dB is the derivative of the cost function with respect to the biases
 */
public class GradientDescent implements Optimizer {
    private double learningRate;

    public GradientDescent(double learningRate) {
        this.learningRate = learningRate;
    }

    /**
     * Update the weights using the given derivative of the cost function with respect to the weights.
     *
     * @param weights The weights to update
     * @param dCdW    dC/dW The derivative of the cost function with respect to the weights
     */
    @Override
    public void updateWeights(Matrix weights, Matrix dCdW) {
        weights.sub(dCdW.mul(learningRate));
    }

    /**
     * Update the biases using the given derivative of the cost function with respect to the biases.
     *
     * @param biases The biases to update
     * @param dCdB   dC/dB The derivative of the cost function with respect to the biases
     */
    @Override
    public Vec updateBias(Vec biases, Vec dCdB) {
        return biases.sub(dCdB.mul(learningRate));
    }

    /**
     * Create a copy of the optimizer.
     *
     * @return A new instance of the optimizer with the same configuration.
     */
    @Override
    public Optimizer copy() {
        // No need to make copies since this optimizer is stateless.
        // Same instance can be used for all layers.
        return this;
    }
}
