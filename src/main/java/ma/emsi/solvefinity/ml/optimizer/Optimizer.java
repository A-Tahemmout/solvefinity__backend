package ma.emsi.solvefinity.ml.optimizer;

import ma.emsi.solvefinity.ml.math.Matrix;
import ma.emsi.solvefinity.ml.math.Vec;

/**
 * Optimizer interface for optimization algorithms.
 *
 * @author Tahemmout Aymane
 */
public interface Optimizer {
    /**
     * Update the weights using the given derivative of the cost function with respect to the weights.
     *
     * @param weights The weights to update
     * @param dCdW    dC/dW The derivative of the cost function with respect to the weights
     */
    void updateWeights(Matrix weights, Matrix dCdW);

    /**
     * Update the biases using the given derivative of the cost function with respect to the biases.
     *
     * @param biases The biases to update
     * @param dCdB   dC/dB The derivative of the cost function with respect to the biases
     */
    Vec updateBias(Vec biases, Vec dCdB);

    /**
     * Create a copy of the optimizer.
     *
     * @return A new instance of the optimizer with the same configuration.
     */
    Optimizer copy();
}
