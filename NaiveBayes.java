import java.io.*;
import java.util.ArrayList;

/**
 * This class manages training data and stores and trains WordCounter objects.
 *
 * @author 123456bk Balint Kovacs
 */
public class NaiveBayes {
    private ArrayList words = new ArrayList<WordCounter>();
    private int noSpamDocNum = 0;
    private int spamDocNum = 0;

    /**
     * This constructor fills up the private ArrayList with WordCounter objects.
     *
     * @param focusWords is the data that is fed to the WordCounter objects.
     */
    public NaiveBayes(String[] focusWords) {
        for (String wordSearched : focusWords) {
            words.add(new WordCounter(wordSearched));
        }
    }

    /**
     * This method trains the WordCounter objects relating to the words of interest.
     *
     * @param document is the training data that is used to train the WordCounter objects.
     */
    public void addSample(String document) {
        // Determines if the document is a spam or not and adjusts the counter accordingly
        if (Integer.parseInt(document.substring(0,1)) == 0) {
            this.noSpamDocNum++;
        } else if (Integer.parseInt(document.substring(0,1)) == 1) {
            this.spamDocNum++;
        }

        // Sets the probabilites using the training data
        for (int i = 0; i < words.size(); i++) {
            WordCounter wc = (WordCounter) words.get(i);
            wc.addSample(document);
        }
    }

    /**
     * This method provides a classification on an unlabeled document based on trained WordCount objects.
     *
     * @param unclassifiedDocument is an unlabeled string that contains words separated by whitespaces.
     * @return a boolean: true = algorithm classifies data as spam, false = algorithm classifies data as no spam.
     */
    public boolean classify(String unclassifiedDocument) {
        // Sets initial spam and no spam scores
        double spamScore = (double) this.spamDocNum / (double) (this.spamDocNum + this.noSpamDocNum);
        double noSpamScore = (double) this.noSpamDocNum / (double) (this.spamDocNum + this.noSpamDocNum);

        // Split the unclassified document into an array of Strings
        String[] unclassifiedWords = unclassifiedDocument.split(" ");

        // Matching the words with the corresponding WordCounter
        for (String tempWord : unclassifiedWords) {
            for (int i = 0; i < words.size(); i++) {
                WordCounter wc = (WordCounter) words.get(i);

                // Matching WordCounter found
                if (wc.getFocusWord().equals(tempWord)) {
                    spamScore = spamScore * wc.getConditionalSpam();
                    noSpamScore = noSpamScore * wc.getConditionalNoSpam();
                }
            }
        }

        return spamScore > noSpamScore;
    }

    /**
     * Trains the WordCounter objects based on a .txt file.
     * Uses the addSample function.
     *
     * @param trainingFile is a .txt file that contains training data.
     * @throws IOException if there is any issue when reading the file.
     */
    public void trainClassifier(File trainingFile) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(trainingFile))) {
            String line = reader.readLine();
            while (line != null) { // While there are new lines to read in the document
                // We add the read line (document) to the training data
                this.addSample(line);

                // We try to read the next line
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method classifies an unlabeled file based on pre-trained WordCounters.
     * It reads the document line-by-line and calls upon the method classify.
     *
     * @param input is the unlabeled document (.txt file)
     * @param output is the name of the .txt file that is going to be produced by the method
     * @throws IOException if there is a problem while reading the input file
     */
    public void classifyFile(File input, File output) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(input))) {
            // Initializing the PrintWriter
            PrintWriter writer = new PrintWriter(output);

            String line = reader.readLine();

            // While there are new lines to read in the document
            while (line != null) {
                // We add the read line (document) to the training data
                if (this.classify(line)) {
                    writer.println(1);
                } else {
                    writer.println(0);
                }

                // We try to read the next line
                line = reader.readLine();
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Fills up a ConfusionMatrix that scores the accuracy of the model.
     *
     * @param testdata is the input data based on which the model is scored.
     * @return a ConfusionMatrix object, that summarizes the accuracy of the model.
     * @throws IOException if there is an issue while reading the input file.
     */
    public ConfusionMatrix computeAccuracy(File testdata) throws IOException {
        int trueNegatives = 0, truePositives = 0, falseNegatives = 0, falsePositives = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(testdata))) {
            String line = reader.readLine();

            // While there are new lines to read in the document
            while (line != null) {
                // We classify the data excluding the number at the beginning

                // Classification given by algorithm
                boolean algoClassification = this.classify(line.substring(2));

                // Is true if is a spam, false if not spam
                boolean trueClassification = Integer.parseInt(line.substring(0,1)) == 1;

                // Both record it as a spam
                if (algoClassification && trueClassification) {
                    truePositives++;
                }
                // Both record it as a no spam
                else if (!algoClassification && !trueClassification) {
                    trueNegatives++;
                } else {

                    // The algorithm classified it as spam, but it is not a spam
                    if (algoClassification) {
                        falsePositives++;
                    }
                    // The algorithm classified it as a no spam but it is indeed a spam
                    else {
                        falseNegatives++;
                    }
                }

                // We try to read the next line
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ConfusionMatrix(trueNegatives, truePositives, falseNegatives, falsePositives);
    }
}