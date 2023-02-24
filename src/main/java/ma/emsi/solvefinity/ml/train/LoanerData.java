package ma.emsi.solvefinity.ml.train;

import java.util.Arrays;

/**
 * LoanerData class for loaner data.
 *
 * @author Tahemmout Aymane
 * @implNote This class contains the loaner data with its label.
 */
public class LoanerData {
    /**
     * The expected array for the label.
     * The first array is for the label 0 and the second array is for the label 1.
     */
    private static final double[][] EXPECTED_ARRAY = new double[][]{
            {1, 0},
            {0, 1}
    };
    private double[] data;
    private int label;

    public LoanerData(double[] data, int label) {
        this.data = data;
        this.label = label;
    }

    public double[] getData() {
        return data;
    }

    public int getLabel() {
        return label;
    }

    public double[] getLabelAsArray() {
        return EXPECTED_ARRAY[label];
    }

    @Override
    public String toString() {
        String sb = "Loan amount: " + data[0] + " | " +
                "Term: " + data[1] + " | " +
                "Interest rate: " + data[2] + " | " +
                "Installment: " + data[3] + " | " +
                "Grade: " + data[4] + " | " +
                "Employment length: " + data[5] + " | " +
                "Home ownership: " + data[6] + " | " +
                "Annual income: " + data[7] + " | " +
                "Verification status: " + data[8] + " | " +
                "Purpose: " + data[9] + " | " +
                "Label: " + (label == 1 ? "Insolvent" : "Solvent") + " | " +
                " Data: " + Arrays.toString(data) + " | " +
                " Expected: " + Arrays.toString(getLabelAsArray());

        return sb;
    }
}
