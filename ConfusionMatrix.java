/**
 * This class is a data storage object for the accuracy of the inference model
 *
 * @author 123456bk Balint Kovacs
 */
public class ConfusionMatrix {
    private int trueNegatives;
    private int truePositives;
    private int falseNegatives;
    private int falsePositives;

    /**
     * This constructor fills up private variables based on input
     *
     * @param trueNegativesIn the number of correctly identified no spam documents
     * @param truePositivesIn the number of correctly identified spam documents
     * @param falseNegativesIn the number of misidentified spam documents
     * @param falsePositivesIn the number of misidentified no spam documents
     */
    public ConfusionMatrix(int trueNegativesIn, int truePositivesIn, int falseNegativesIn, int falsePositivesIn) {
        // Filling up Object data
        this.trueNegatives = trueNegativesIn;
        this.truePositives = truePositivesIn;
        this.falseNegatives = falseNegativesIn;
        this.falsePositives = falsePositivesIn;
    }

    /**
     * This method is an interface to extract data form the object
     *
     * @return the number of correctly identified no spam documents
     */
    public int getTrueNegatives() {
        return this.trueNegatives;
    }
    /**
     * This method is an interface to extract data form the object
     *
     * @return the number of correctly identified spam documents
     */
    public int getTruePositives() {
        return this.truePositives;
    }
    /**
     * This method is an interface to extract data form the object
     *
     * @return the number of misidentified spam documents
     */
    public int getFalseNegatives() {
        return this.falseNegatives;
    }
    /**
     * This method is an interface to extract data form the object
     *
     * @return the number of misidentified no spam documents
     */
    public int getFalsePositives() {
        return this.falsePositives;
    }
}