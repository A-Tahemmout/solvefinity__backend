package ma.emsi.solvefinity.ml.math;

import java.util.Arrays;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

import static java.lang.String.format;

/**
 * Vector class for mathematical vectors.
 *
 * @author Tahemmout Aymane
 */
public class Vec {
    private double[] data;

    public Vec(double... data) {
        this.data = data;
    }

    public Vec(int... data) {
        this(IntStream.of(data).asDoubleStream().toArray());
    }

    public Vec(int size) {
        this.data = new double[size];
    }

    public int dimension() {
        return data.length;
    }

    public double[] getData() {
        return data;
    }

    public void set(int index, double value) {
        data[index] = value;
    }

    public double get(int index) {
        return data[index];
    }

    /**
     * Applies the given function to each element of this vector.
     *
     * @param fn The {@link Function} to apply to each element of this vector
     * @return a new vector with the result of applying the given function to each element of this vector
     */
    public Vec map(Function fn) {
        double[] result = new double[data.length];

        for (int i = 0; i < data.length; i++)
            result[i] = fn.apply(data[i]);

        return new Vec(result);
    }

    /**
     * Multiplies this vector by the given scalar.
     *
     * @param scalar
     * @return a new vector with the result of multiplying this vector by the given scalar
     */
    public Vec mul(double scalar) {
        return map(value -> value * scalar);
    }

    /**
     * Subtracts the given vector by the given scalar.
     *
     * @param scalar
     * @return a new vector with the result of subtracting the given vector by the given scalar
     */
    public Vec sub(double scalar) {
        return map(value -> value - scalar);
    }

    /**
     * Divides this vector by the given scalar.
     *
     * @param scalar
     * @return a new vector with the result of dividing this vector by the given scalar
     */
    public Vec div(double scalar) {
        return map(value -> value / scalar);
    }

    /**
     * Computes the dot product of this vector with the given vector.
     *
     * @param u
     * @return the dot product of this vector with the given vector
     */
    public double dot(Vec u) {
        assertCorrectDimension(u.dimension());

        double sum = 0;

        for (int i = 0; i < data.length; i++)
            sum += data[i] * u.data[i];

        // Return the dot product of this vector and the other vector.
        return sum;
    }

    /**
     * Computes the sum of this vector with the given vector.
     *
     * @param u
     * @return a new vector with the sum of this vector with the given vector
     */
    public Vec add(Vec u) {
        assertCorrectDimension(u.dimension());

        double[] result = new double[u.dimension()];

        for (int i = 0; i < data.length; i++)
            result[i] = data[i] + u.data[i];

        // Return the sum of this vector and the other vector.
        return new Vec(result);
    }

    /**
     * Computes the difference of this vector with the given vector.
     *
     * @param u
     * @return a new vector with the difference of this vector with the given vector
     */
    public Vec sub(Vec u) {
        assertCorrectDimension(u.dimension());

        double[] result = new double[u.dimension()];

        for (int i = 0; i < data.length; i++)
            result[i] = data[i] - u.data[i];

        // Return the subtraction of this vector and the other vector.
        return new Vec(result);
    }

    /**
     * Computes the division of this vector with the given vector.
     *
     * @param u
     * @return a new vector with the division of this vector with the given vector
     */
    public Vec div(Vec u) {
        assertCorrectDimension(u.dimension());

        double[] result = new double[u.dimension()];

        for (int i = 0; i < data.length; i++)
            result[i] = data[i] / u.data[i];

        // Return the division of this vector and the other vector.
        return new Vec(result);
    }

    /**
     * Computes the product of this vector with the given vector.
     *
     * @param u
     * @return a new vector with the multiplication of this vector with the given vector
     */
    public Vec elementProduct(Vec u) {
        assertCorrectDimension(u.dimension());

        double[] result = new double[u.dimension()];

        for (int i = 0; i < data.length; i++)
            result[i] = data[i] * u.data[i];

        // Return the multiplication of this vector and the other vector.
        return new Vec(result);
    }

    /**
     * Computes the multiplication of this vector with the given matrix.
     *
     * @param matrix
     * @return a new vector with the multiplication of this vector with the given matrix
     */
    public Vec mul(Matrix matrix) {
        assertCorrectDimension(matrix.rows());

        double[][] matrixData = matrix.getData();
        double[] result = new double[matrix.cols()];

        for (int col = 0; col < matrix.cols(); col++)
            for (int row = 0; row < matrix.rows(); row++)
                result[col] += matrixData[row][col] * data[row];

        // Return the multiplication of this vector and the given matrix.
        return new Vec(result);
    }

    /**
     * Calculates the outer product of this vector with the given vector.
     *
     * @param u
     * @return the matrix that is the outer product of this vector with the given vector
     */
    public Matrix outerProduct(Vec u) {
        double[][] result = new double[u.dimension()][dimension()];

        for (int i = 0; i < data.length; i++)
            for (int j = 0; j < u.data.length; j++)
                result[j][i] = data[i] * u.data[j];

        return new Matrix(result);
    }

    /**
     * Gets the index of the largest element in this vector.
     *
     * @return the index of the largest element in this vector
     */
    public int indexOfLargestElement() {
        int ixOfLargest = 0;

        for (int i = 0; i < data.length; i++)
            if (data[i] > data[ixOfLargest]) ixOfLargest = i;

        return ixOfLargest;
    }

    /**
     * Get the maximum value in this vector.
     *
     * @return the maximum value in this vector
     */
    public double max() {
        return DoubleStream.of(data).max().getAsDouble();
    }

    /**
     * Get the sum of all elements in this vector.
     *
     * @return the sum of all elements in this vector
     */
    public double sumElements() {
        return DoubleStream.of(data).sum();
    }

    @Override
    public String toString() {
        return "Vec{" + "data=" + Arrays.toString(data) + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        if (!(obj instanceof Vec)) return false;

        Vec other = (Vec) obj;

        return Arrays.equals(this.data, other.data);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(data);
    }

    // ------------------ Private Methods ------------------

    /**
     * Checks if this vector has the same dimension as the given vector.
     *
     * @param inputDimension
     * @throws IllegalArgumentException if this vector does not have the same dimension as the given vector
     */
    private void assertCorrectDimension(int inputDimension) {
        if (dimension() != inputDimension)
            throw new IllegalArgumentException(format("Vectors of different dimensions: Input is %d, Vec is %d", inputDimension, dimension()));
    }
}
