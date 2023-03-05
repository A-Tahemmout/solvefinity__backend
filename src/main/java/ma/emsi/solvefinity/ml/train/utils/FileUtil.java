package ma.emsi.solvefinity.ml.train.utils;

import ma.emsi.solvefinity.ml.train.LoanerData;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
    private static final String ROOT_LOCATION = "src/main/resources/uploads/data/";
    private static final String DEFAULT_DELIMITER = ",";

    public static List<LoanerData> loadLoanerData(String fileName) {
        // Loaners data list
        List<LoanerData> loanersData = new ArrayList<>();

        // Read the CSV file
        try (BufferedReader br = new BufferedReader(new FileReader(ROOT_LOCATION + fileName))) {
            br.readLine(); // skip the header row
            String line; // current line

            while ((line = br.readLine()) != null) {
                String[] data = line.split(DEFAULT_DELIMITER);

                double[] loanData = new double[data.length - 1];

                for (int i = 0; i < loanData.length; i++) {
                    loanData[i] = Double.parseDouble(data[i]);
                }

                int label = Double.parseDouble(data[data.length - 1]) == 0.0 ? 0 : 1;

                loanersData.add(new LoanerData(loanData, label));
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("An error occurred while reading the CSV file: " + e.getMessage());
        }

        return loanersData;
    }
}
