package ma.emsi.solvefinity.ml;

import ma.emsi.solvefinity.ml.math.Matrix;

import static ma.emsi.solvefinity.ml.math.SharedRnd.getRnd;

/**
 * Initializer interface.
 *
 * @author Tahemmout Aymane
 * @implNote This interface contains the initializer.
 * The initializer is used to initialize the weights of the neural network.
 */
public interface Initializer {
    void initWeights(Matrix weights, int layer);

    // ---------------------------------------------------------------------
    // ---------------------- Predefined Initializers ----------------------
    // ---------------------------------------------------------------------

    class Random implements Initializer {
        private double min;
        private double max;

        public Random(double min, double max) {
            this.min = min;
            this.max = max;
        }

        @Override
        public void initWeights(Matrix weights, int layer) {
            double delta = max - min;
            weights.map(value -> min + getRnd().nextDouble() * delta);
        }
    }


    class XavierUniform implements Initializer {
        @Override
        public void initWeights(Matrix weights, int layer) {
            final double factor = 2.0 * Math.sqrt(6.0 / (weights.cols() + weights.rows()));
            weights.map(value -> (getRnd().nextDouble() - 0.5) * factor);
        }
    }

    class XavierNormal implements Initializer {
        @Override
        public void initWeights(Matrix weights, int layer) {
            final double factor = Math.sqrt(2.0 / (weights.cols() + weights.rows()));
            weights.map(value -> getRnd().nextGaussian() * factor);
        }
    }

    class LeCunUniform implements Initializer {
        @Override
        public void initWeights(Matrix weights, int layer) {
            final double factor = 2.0 * Math.sqrt(3.0 / weights.cols());
            weights.map(value -> (getRnd().nextDouble() - 0.5) * factor);
        }
    }

    class LeCunNormal implements Initializer {
        @Override
        public void initWeights(Matrix weights, int layer) {
            final double factor = 1.0 / Math.sqrt(weights.cols());
            weights.map(value -> getRnd().nextGaussian() * factor);
        }
    }

    class HeUniform implements Initializer {
        @Override
        public void initWeights(Matrix weights, int layer) {
            final double factor = 2.0 * Math.sqrt(6.0 / weights.cols());
            weights.map(value -> (getRnd().nextDouble() - 0.5) * factor);
        }
    }

    class HeNormal implements Initializer {
        @Override
        public void initWeights(Matrix weights, int layer) {
            final double factor = Math.sqrt(2.0 / weights.cols());
            weights.map(value -> getRnd().nextGaussian() * factor);
        }
    }
}
