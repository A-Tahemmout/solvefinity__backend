package ma.emsi.solvefinity.ml.math;

import java.util.Arrays;

import static java.lang.String.format;
import static java.util.Arrays.stream;

/**
 * Matrix class for mathematical matrices.
 *
 * @author Tahemmout Aymane
 * @implNote This class contains the basic operations for matrices.
 * This class is not immutable.
 */
public class Matrix {
    private double[][] data;
    private int rows;
    private int cols;

    public Matrix(double[][] data) {
        this.data = data;
        this.rows = data.length;
        this.cols = data[0].length;
    }

    public Matrix(int rows, int cols) {
        this(new double[rows][cols]);
    }

    public int rows() {
        return rows;
    }

    public int cols() {
        return cols;
    }

    public double[][] getData() {
        return data;
    }

    /**
     * Applies the given function to each element of this matrix.
     *
     * @param fn The {@link Function} to apply to each element of this vector
     * @return this matrix with the result of applying the given function to each element of this matrix
     */
    public Matrix map(Function fn) {
        for (int y = 0; y < rows; y++)
            for (int x = 0; x < cols; x++)
                data[y][x] = fn.apply(data[y][x]);

        return this;
    }

    /**
     * Multiplies this matrix by the given scalar.
     *
     * @param scalar
     * @return this matrix with the result of multiplying this matrix by the given scalar
     */
    public Matrix mul(double scalar) {
        return map(value -> value * scalar);
    }

    /**
     * Multiplies this matrix by the given vector.
     *
     * @param v
     * @return a new vector with the result of multiplying this matrix by the given vector
     */
    public Vec multiply(Vec v) {
        double[] out = new double[rows];

        for (int y = 0; y < rows; y++)
            out[y] = new Vec(data[y]).dot(v);

        return new Vec(out);
    }

    /**
     * Computes the sum of this matrix and the given matrix.
     *
     * @param other
     * @return this matrix with the result of adding the given matrix to it
     */
    public Matrix add(Matrix other) {
        assertCorrectDimension(other);

        for (int y = 0; y < rows; y++)
            for (int x = 0; x < cols; x++)
                data[y][x] += other.data[y][x];

        return this;
    }

    /**
     * Computes the difference of this matrix and the given matrix.
     *
     * @param other
     * @return this matrix with the result of subtracting the given matrix from it
     */
    public Matrix sub(Matrix other) {
        assertCorrectDimension(other);

        for (int y = 0; y < rows; y++)
            for (int x = 0; x < cols; x++)
                data[y][x] -= other.data[y][x];

        return this;
    }

    /**
     * Computes the average of this matrix
     *
     * @return the average of this matrix
     */
    public double average() {
        return stream(data)
                .flatMapToDouble(Arrays::stream)
                .average()
                .getAsDouble();
    }

    /**
     * Gets the variance of this matrix.
     *
     * @return the variance of this matrix
     */
    public double variance() {
        double avg = average();
        return stream(data)
                .flatMapToDouble(Arrays::stream)
                .map(a -> (a - avg) * (a - avg))
                .average()
                .getAsDouble();
    }

    /**
     * Creates a new matrix copying the given matrix.
     *
     * @return a new matrix copying the given matrix
     */
    public Matrix copy() {
        Matrix m = new Matrix(rows, cols);
        for (int y = 0; y < rows; y++)
            if (cols >= 0) System.arraycopy(data[y], 0, m.data[y], 0, cols);

        return m;
    }

    // ------------------ Private Methods ------------------

    /**
     * Checks if this matrix is has the same dimensions as the given matrix.
     *
     * @param other
     * @throws IllegalArgumentException if this matrix does not have the same dimensions as the given matrix
     */
    private void assertCorrectDimension(Matrix other) {
        if (rows != other.rows || cols != other.cols) {
            throw new IllegalArgumentException(format("Matrices of different dimensions: Input is %d x %d, Matrix is %d x %d", rows, cols, other.rows, other.cols));
        }
    }
}
