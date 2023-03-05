package ma.emsi.solvefinity.ml;

import ma.emsi.solvefinity.ml.math.Matrix;
import ma.emsi.solvefinity.ml.math.Vec;
import ma.emsi.solvefinity.ml.optimizer.Optimizer;

/**
 * A layer of a neural network.
 *
 * @author Tahemmout Aymane
 * @implNote This class represents a layer of a neural network.
 * It contains the weights and the biases coming into the layer.
 */
public class Layer {
    private final int size;

    private ThreadLocal<Vec> out = new ThreadLocal<>();
    private Activation activation;
    private Optimizer optimizer;
    private Matrix weights;
    private Vec bias;

    private Layer precedingLayer;

    // Not yet realized changes to the weights and biases ("observed things not yet learned")
    private transient Matrix deltaWeights;
    private transient Vec deltaBias;
    private transient int deltaWeightsAdded = 0;
    private transient int deltaBiasAdded = 0;
    private double l2 = 0;

    // ---------------------------------------------------------------------
    // Constructors
    // ---------------------------------------------------------------------
    public Layer(int size, Activation activation) {
        this(size, activation, 0);
    }

    public Layer(int size, Activation activation, double initialBias) {
        this.size = size;
        bias = new Vec(size).map(x -> initialBias);
        deltaBias = new Vec(size);
        this.activation = activation;
    }

    public Layer(int size, Activation activation, Vec bias) {
        this.size = size;
        this.bias = bias;
        deltaBias = new Vec(size);
        this.activation = activation;
    }

    public int size() {
        return size;
    }

    /**
     * Feed the in-vector, i, through this layer.
     * Stores a copy of the out vector.
     *
     * @param i The input vector
     * @return The out vector o (i.e. the result of o = iW + b)
     */
    public Vec evaluate(Vec i) {
        if (!hasPrecedingLayer()) {
            out.set(i);    // No calculation i input layer, just store data
        } else {
            out.set(activation.fn(i.mul(weights).add(bias)));
        }
        return out.get();
    }

    public Vec getOut() {
        return out.get();
    }

    public Activation getActivation() {
        return activation;
    }

    public void setOptimizer(Optimizer optimizer) {
        this.optimizer = optimizer;
    }

    public void setL2(double l2) {
        this.l2 = l2;
    }

    public Matrix getWeights() {
        return weights;
    }

    public void setWeights(Matrix weights) {
        this.weights = weights;
        deltaWeights = new Matrix(weights.rows(), weights.cols());
    }

    public Layer getPrecedingLayer() {
        return precedingLayer;
    }

    public void setPrecedingLayer(Layer precedingLayer) {
        this.precedingLayer = precedingLayer;
    }

    public boolean hasPrecedingLayer() {
        return precedingLayer != null;
    }

    public Vec getBias() {
        return bias;
    }

    /**
     * Add upcoming changes to the Weights and Biases.
     * This does not mean that the network is updated.
     */
    public synchronized void addDeltaWeightsAndBiases(Matrix dW, Vec dB) {
        deltaWeights.add(dW);
        deltaWeightsAdded++;
        deltaBias = deltaBias.add(dB);
        deltaBiasAdded++;
    }

    /**
     * Takes an average of all added Weights and Biases and tell the
     * optimizer to apply them to the current weights and biases.
     * <p>
     * Also applies L2 regularization on the weights if used.
     */
    public synchronized void updateWeightsAndBias() {
        if (deltaWeightsAdded > 0) {
            if (l2 > 0)
                weights.map(value -> value - l2 * value);

            Matrix average_dW = deltaWeights.mul(1.0 / deltaWeightsAdded);
            optimizer.updateWeights(weights, average_dW);
            deltaWeights.map(a -> 0);   // Clear
            deltaWeightsAdded = 0;
        }

        if (deltaBiasAdded > 0) {
            Vec average_bias = deltaBias.mul(1.0 / deltaBiasAdded);
            bias = optimizer.updateBias(bias, average_bias);
            deltaBias = deltaBias.map(a -> 0);  // Clear
            deltaBiasAdded = 0;
        }
    }

    // ------------------------------------------------------------------
    // LayerState
    // ------------------------------------------------------------------

    public LayerState getState() {
        return new LayerState(this);
    }

    public static class LayerState {

        double[][] weights;
        double[] bias;
        String activation;

        public LayerState(Layer layer) {
            weights = layer.getWeights() != null ? layer.getWeights().getData() : null;
            bias = layer.getBias().getData();
            activation = layer.activation.getName();
        }

        public double[][] getWeights() {
            return weights;
        }

        public double[] getBias() {
            return bias;
        }
    }
}
