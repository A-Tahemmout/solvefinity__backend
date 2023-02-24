package ma.emsi.solvefinity.ml.train;

import ma.emsi.solvefinity.ml.Initializer;
import ma.emsi.solvefinity.ml.Layer;
import ma.emsi.solvefinity.ml.NeuralNetwork;
import ma.emsi.solvefinity.ml.Result;
import ma.emsi.solvefinity.ml.math.Vec;
import ma.emsi.solvefinity.ml.optimizer.GradientDescent;
import ma.emsi.solvefinity.ml.train.utils.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.String.format;
import static java.lang.System.currentTimeMillis;
import static java.util.Collections.shuffle;
import static java.util.Collections.unmodifiableList;
import static ma.emsi.solvefinity.ml.Activation.Sigmoid;
import static ma.emsi.solvefinity.ml.math.SharedRnd.getRnd;
import static ma.emsi.solvefinity.ml.math.SharedRnd.setRnd;

public class TrainNetwork {
    private static final int numInputFeatures = 10;
    private static final int numClasses = 2;
    private static final int BATCH_SIZE = 32;
    private static Logger log = LoggerFactory.getLogger(TrainNetwork.class);

    public static void main(String[] args) throws IOException {
        log.info("Starting training at " + new Date());
        // Set random seed
        int seed = 42;
        setRnd(new Random(seed));

        NeuralNetwork network =
                new NeuralNetwork.Builder(numInputFeatures)
                        .addLayer(new Layer(12, Sigmoid, .5))
                        .addLayer(new Layer(numClasses, Sigmoid))
                        .initWeights(new Initializer.XavierNormal())
                        .setOptimizer(new GradientDescent(.5))
                        .create();

        List<LoanerData> trainData = FileUtil.loadLoanerData("train_data.csv");
        List<LoanerData> testData = FileUtil.loadLoanerData("test_data.csv");

        int epoch = 0;
        double errorRateOnTrainDS;
        double errorRateOnTestDS;

        StopEvaluator evaluator = new StopEvaluator(network, 10, null);
        boolean shouldStop = false;

        long t0 = currentTimeMillis();
        do {
            epoch++;

            // Shuffle the data
            shuffle(trainData, getRnd());

            // Learning
            int correctTrainDS = applyDataToNet(trainData, network, true);

            errorRateOnTrainDS = 100 - (100.0 * correctTrainDS / trainData.size());

            if (epoch % 5 == 0) {
                // Testing
                int correctOnTestDS = applyDataToNet(testData, network, false);
                errorRateOnTestDS = 100 - (100.0 * correctOnTestDS / testData.size());

                shouldStop = evaluator.stop(errorRateOnTestDS);

                double epochsPerMinute = epoch * 60000.0 / (currentTimeMillis() - t0);
                log.info(format("Epoch: %3d    |   Train error rate: %6.3f %%    |   Test error rate: %5.2f %%   |   Epocs/min: %5.2f", epoch, errorRateOnTrainDS, errorRateOnTestDS, epochsPerMinute));
            } else {
                log.info(format("Epoch: %3d    |   Train error rate: %6.3f %%    |", epoch, errorRateOnTrainDS));
            }

        } while (!shouldStop);

        double lowestErrorRate = evaluator.getLowestErrorRate();
        log.info(format("No improvement, aborting. Reached a lowest error rate of %7.4f %%", lowestErrorRate));
        writeFile(evaluator, lowestErrorRate);
    }

    /**
     * Run the entire dataset <code>data</code> through the network.
     * If <code>learn</code> is true the network will learn from the data.
     */
    private static int applyDataToNet(List<LoanerData> data, NeuralNetwork network, boolean learn) {
        final AtomicInteger correct = new AtomicInteger();

        for (int i = 0; i <= data.size() / BATCH_SIZE; i++) {
            getBatch(i, data).stream().parallel().forEach(loanerData -> {
                Vec input = new Vec(loanerData.getData());
                Vec wanted = new Vec(loanerData.getLabelAsArray());

                Result result = learn ?
                        network.evaluate(input, wanted) :
                        network.evaluate(input);

                // System.out.println("result: " + result.getOutput().indexOfLargestElement() + " wanted: " + loanerData.getLabel() + " " + result.getOutput());

                if (result.getOutput().indexOfLargestElement() == loanerData.getLabel()) {
                    correct.incrementAndGet();
                }
            });

            if (learn)
                network.updateFromLearning();
        }

        return correct.get();
    }

    /**
     * Run one loaner through the network.
     */
    private static int applyDataToNet(LoanerData loanerData, NeuralNetwork network) {
        Vec input = new Vec(loanerData.getData());

        Result result = network.evaluate(input);
        return result.getOutput().indexOfLargestElement() == loanerData.getLabel() ? 1 : 0;
    }

    /**
     * Cuts out batch i from dataset data.
     */
    private static List<LoanerData> getBatch(int i, List<LoanerData> data) {
        int fromIx = i * BATCH_SIZE;
        int toIx = Math.min(data.size(), (i + 1) * BATCH_SIZE);
        return unmodifiableList(data.subList(fromIx, toIx));
    }

    /**
     * Saves the weights and biases of the network in directory "./out"
     */
    private static void writeFile(StopEvaluator evaluator, double lowestErrorRate) throws IOException {
        File outDir = new File("src/main/resources/uploads/out");
        if (!outDir.exists())
            if (!outDir.mkdirs())
                throw new IOException("Could not create directory " + outDir.getAbsolutePath());

        // File name: <lowest error rate> <date> <time>.json
        File outFile = new File(outDir, format("%4.2f-%tF-%s.json", lowestErrorRate, new Date(), System.currentTimeMillis()));

        // Create the file
        if (!outFile.createNewFile())
            throw new IOException("Could not create file " + outFile.getAbsolutePath());

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outFile))) {
            bw.write(evaluator.getBestNetSoFar());
        }
    }
}
