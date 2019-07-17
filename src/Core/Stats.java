package Core;

import java.io.Serializable;
import java.util.List;

/**
 * This class contain methods for calculation of Mean, weighted mean and
 * Standard Deviation.
 *
 * @author Group 44 LAPR2
 */
// http://en.wikipedia.org/wiki/Mean
// http://en.wikipedia.org/wiki/Standard_deviation
public abstract class Stats implements Serializable {

    /**
     * Calculate the mean of values
     *
     * @param values Values to calculate mean
     * @return Mean of values
     * @throws Exception If divide by zero
     */
    public static double getMean(double[] values) throws Exception {
        int size = values.length;
        if (size == 0) {
            throw new Exception("Can't divide by zero");
        }
        double sum = 0.0;
        for (double value : values) {
            sum += value;
        }
        return sum / size;
    }

    /**
     * Calculate the mean of values
     *
     * @param values Values to calculate mean
     * @return Mean of values
     * @throws Exception If divide by zero
     */
    public static double getMean(List<Double> values) throws Exception {
        int size = values.size();
        if (size == 0) {
            throw new Exception("Can't divide by zero");
        }
        double sum = 0.0;
        for (double value : values) {
            sum += value;
        }
        return sum / size;
    }

    /**
     * Calculate Standard Deviation of values
     *
     * @param values Values to calculate standard deviation
     * @return Standard Deviation of values
     * @throws Exception If divide by zero
     */
    public static double getStdDev(double[] values) throws Exception {
        int size = values.length;
        if (size == 0) {
            throw new Exception("Can't divide by zero");
        }
        double mean = getMean(values);
        double sum = 0;
        for (double value : values) {
            sum += Math.pow((mean - value), 2);
        }
        double variance = sum / size;
        return Math.sqrt(variance);
    }

    /**
     * Calculate Standard Deviation of values
     *
     * @param values Values to calculate standard deviation
     * @return Standard Deviation of values
     * @throws Exception If divide by zero
     */
    public static double getStdDev(List<Double> values) throws Exception {
        int size = values.size();
        if (size == 0) {
            throw new Exception("Can't divide by zero");
        }
        double mean = getMean(values);
        double sum = 0;
        for (double value : values) {
            sum += Math.pow((mean - value), 2);
        }
        double variance = sum / size;
        return Math.sqrt(variance);
    }

    // http://www.investopedia.com/terms/w/weightedaverage.asp
    /**
     * Calculate weighted mean
     *
     * @param weights Weights of values
     * @param values Values
     * @return Weighted mean
     * @throws Exception If the number of elements in the weights is different
     * from the number of elements in the values and if divided by zero
     */
    public static double getWeightedMean(List<Double> weights, List<Double> values) throws Exception {
        if (weights.size() != values.size()) {
            throw new Exception("Invalid values");
        }
        double denominatorSum = 0;
        double numeratorSum = 0;
        for (int i = 0; i < weights.size(); i++) {
            numeratorSum += (weights.get(i) * values.get(i));
            denominatorSum += weights.get(i);
        }

        if (denominatorSum == 0) {
            throw new Exception("Can't divide by zero");
        }
        return numeratorSum / denominatorSum;
    }

    // http://www.itl.nist.gov/div898/software/dataplot/refman2/ch2/weightsd.pdf
    /**
     * Calculate weighted standard deviation
     *
     * @param weights Weights of values
     * @param values Values
     * @return Weighted standard deviation
     * @throws Exception If divided by zero
     */
    public static double getWeightedStd(List<Double> weights, List<Double> values) throws Exception {
        double numerator = 0;
        double denominator = 0;
        double mean = getWeightedMean(weights, values);

        int weightsDifZero = 0;
        for (Double value : weights) {
            if (value != 0) {
                weightsDifZero++;
            }
        }
        if (weightsDifZero == 0) {
            throw new Exception("Can't divide by zero");
        }
        for (int i = 0; i < values.size(); i++) {
            numerator += (weights.get(i) * Math.pow((values.get(i) - mean), 2));
            denominator += weights.get(i);
        }
        denominator = ((weightsDifZero - 1) * denominator) / weightsDifZero;
        if (denominator == 0) {
            throw new Exception("Can't divide by zero");
        }
        return Math.sqrt(numerator / denominator);
    }
}
