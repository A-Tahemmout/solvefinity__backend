package ma.emsi.solvefinity.ml;

import ma.emsi.solvefinity.ml.math.Function;
import ma.emsi.solvefinity.ml.math.Vec;

import static java.lang.Math.exp;
import static java.lang.Math.log;

/**
 * Activation class for activation functions.
 *
 * @author Tahemmout Aymane
 * @implNote The activation functions are implemented as static methods.
 */
public class Activation {
    // ----------------------------------------------------------------------
    // ------------- Activation functions and their derivatives -------------
    // ----------------------------------------------------------------------
    public static Activation ReLU = new Activation(
            "ReLU",
            x -> x <= 0 ? 0 : x,                // fn
            x -> x <= 0 ? 0 : 1                 // dFn
    );

    public static Activation Leaky_ReLU = new Activation(
            "Leaky_ReLU",
            x -> x <= 0 ? 0.01 * x : x,         // fn
            x -> x <= 0 ? 0.01 : 1              // dFn
    );


    public static Activation Sigmoid = new Activation(
            "Sigmoid",
            Activation::sigmoidFn,                      // fn
            x -> sigmoidFn(x) * (1.0 - sigmoidFn(x))    // dFn
    );


    public static Activation Softplus = new Activation(
            "Softplus",
            x -> log(1.0 + exp(x)),             // fn
            Activation::sigmoidFn               // dFn
    );

    public static Activation Identity = new Activation(
            "Identity",
            x -> x,                             // fn
            x -> 1                              // dFn
    );


    // --------------------------------------------------------------------------
    // Modified Softmax function
    // Since elements of the output vector are not independent, we need to use a modified version of
    // the softmax function that takes into account the other elements of the vector.
    // --------------------------------------------------------------------------
    public static Activation Softmax = new Activation("Softmax") {
        @Override
        public Vec fn(Vec in) {
            double[] data = in.getData();
            double sum = 0;
            double max = in.max();    // Trick: translate the input by largest element to avoid overflow.
            for (double a : data)
                sum += exp(a - max);

            double finalSum = sum;
            return in.map(a -> exp(a - max) / finalSum);
        }

        @Override
        public Vec dCdI(Vec out, Vec dCdO) {
            double x = out.elementProduct(dCdO).sumElements();
            Vec sub = dCdO.sub(x);
            return out.elementProduct(sub);
        }
    };

    // --------------------------------------------------------------------------
    private final String name; // Name of the activation function
    private Function fn;      // The activation function
    private Function dFn;    // The derivative of the activation function

    public Activation(String name) {
        this.name = name;
    }

    public Activation(String name, Function fn, Function dFn) {
        this.name = name;
        this.fn = fn;
        this.dFn = dFn;
    }

    private static double sigmoidFn(double x) {
        return 1.0 / (1.0 + exp(-x));
    }

    // For most activation function it suffice to map each separate element.
    // I.e. they depend only on the single component in the vector.
    public Vec fn(Vec in) {
        return in.map(fn);
    }

    public Vec dFn(Vec out) {
        return out.map(dFn);
    }

    // Also when calculating the Error change rate in terms of the input (dCdI)
    // it is just a matter of multiplying, i.e. ∂C/∂I = ∂C/∂O * ∂O/∂I.
    public Vec dCdI(Vec out, Vec dCdO) {
        return dCdO.elementProduct(dFn(out));
    }

    // --------------------------------------------------------------------------

    public String getName() {
        return name;
    }
}
