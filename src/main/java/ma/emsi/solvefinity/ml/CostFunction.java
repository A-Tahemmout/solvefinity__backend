package ma.emsi.solvefinity.ml;

import ma.emsi.solvefinity.ml.math.Vec;

/**
 * Cost function interface.
 *
 * @author Tahemmout Aymane
 * @implNote This interface contains the cost function.
 * The cost function is used to calculate the cost of the neural network.
 */
public interface CostFunction {
    String getName();

    double getTotal(Vec expected, Vec actual);

    Vec getDerivative(Vec expected, Vec actual);

    // ----------------------------------------------------------------------
    // --------------------------- Cost functions ---------------------------
    // ----------------------------------------------------------------------

    /**
     * Cost function: Binary Cross-Entropy, C = -1/n * ∑(y * log(exp) + (1 - y) * log(1 - exp))
     * Derivative: 1/n * (y - exp) / (p * (1 - exp))
     */
    public class BinaryCrossEntropy implements CostFunction {
        @Override
        public String getName() {
            return "Binary Cross Entropy";
        }

        @Override
        public double getTotal(Vec expected, Vec actual) {
            double total = 0.0;
            for (int i = 0; i < expected.dimension(); i++) {
                total += expected.get(i) * Math.log(actual.get(i)) + (1 - expected.get(i)) * Math.log(1 - actual.get(i));
            }
            return -total / expected.dimension();
        }

        @Override
        public Vec getDerivative(Vec expected, Vec actual) {
            Vec derivative = new Vec(actual.dimension());
            for (int i = 0; i < expected.dimension(); i++) {
                derivative.set(i, (actual.get(i) - expected.get(i)) / (actual.get(i) * (1 - actual.get(i))));
            }
            return derivative.mul(-1.0 / expected.dimension());
        }
    }

    /**
     * Cost function: Hinge Loss, C = max(0, 1 - y * exp)
     * Derivative: -y / (1 - y * exp)
     */
    class HingeLoss implements CostFunction {
        @Override
        public String getName() {
            return "Hinge Loss";
        }

        @Override
        public double getTotal(Vec expected, Vec actual) {
            double loss = 0;
            for (int i = 0; i < expected.dimension(); i++) {
                double y = expected.get(i);
                double y_hat = actual.get(i);
                loss += Math.max(0, 1 - y * y_hat);
            }
            return loss;
        }

        @Override
        public Vec getDerivative(Vec expected, Vec actual) {
            Vec derivative = new Vec(actual.dimension());
            for (int i = 0; i < expected.dimension(); i++) {
                double y = expected.get(i);
                double y_hat = actual.get(i);
                if (y * y_hat < 1) {
                    derivative.set(i, -y);
                } else {
                    derivative.set(i, 0);
                }
            }
            return derivative;
        }
    }

    /**
     * Cost function: Mean Square Error, C = 1/n * ∑(y−exp)^2
     * Derivative: 2/n * (y - exp)
     */
    class MSE implements CostFunction {
        @Override
        public String getName() {
            return "MSE";
        }

        @Override
        public double getTotal(Vec expected, Vec actual) {
            Vec diff = expected.sub(actual);
            return diff.dot(diff) / actual.dimension();
        }

        @Override
        public Vec getDerivative(Vec expected, Vec actual) {
            return actual.sub(expected).mul(2.0 / actual.dimension());
        }
    }

    /**
     * Cost function: Quadratic, C = ∑(y−exp)^2
     * Derivative: 2 * (y - exp)
     */
    class Quadratic implements CostFunction {
        @Override
        public String getName() {
            return "Quadratic";
        }

        @Override
        public double getTotal(Vec expected, Vec actual) {
            Vec diff = actual.sub(expected);
            return diff.dot(diff);
        }

        @Override
        public Vec getDerivative(Vec expected, Vec actual) {
            return actual.sub(expected).mul(2);
        }
    }

    /**
     * Cost function: HalfQuadratic, C = 0.5 ∑(y−exp)^2
     * Derivative: (y - exp)
     */
    class HalfQuadratic implements CostFunction {
        @Override
        public String getName() {
            return "HalfQuadratic";
        }

        @Override
        public double getTotal(Vec expected, Vec actual) {
            Vec diff = expected.sub(actual);
            return diff.dot(diff) * 0.5;
        }

        @Override
        public Vec getDerivative(Vec expected, Vec actual) {
            return actual.sub(expected);
        }
    }

    /**
     * Cost function: Squared Hinge Loss, C = 1/n * ∑max(0, 1 - y * exp)^2
     * Derivative: 2/n * max(0, 1 - y * exp) * -y / (1 - y * exp)
     */
    class SquaredHingeLoss implements CostFunction {
        private final double delta;

        public SquaredHingeLoss(double delta) {
            this.delta = delta;
        }

        @Override
        public String getName() {
            return "Squared Hinge Loss";
        }

        @Override
        public double getTotal(Vec expected, Vec actual) {
            double loss = 0;
            for (int i = 0; i < expected.dimension(); i++) {
                double e = expected.get(i);
                double a = actual.get(i);
                double error = 1 - e * a;
                if (error > 0) {
                    loss += error * error;
                }
            }
            return loss / expected.dimension();
        }

        @Override
        public Vec getDerivative(Vec expected, Vec actual) {
            Vec derivative = new Vec(actual.dimension());
            for (int i = 0; i < expected.dimension(); i++) {
                double e = expected.get(i);
                double a = actual.get(i);
                double error = 1 - e * a;
                if (error > 0) {
                    derivative.set(i, -2 * e * error);
                }
            }
            return derivative.div(expected.dimension());
        }
    }
}
