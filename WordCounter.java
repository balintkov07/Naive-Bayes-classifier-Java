/**
 * This class stores probability data about a specific word.
 * This probability data can be used to make inferences to the category of an unlabeled dataset
 *
 * @author 677733bk Balint Kovacs
 */
public class WordCounter {
    private String searchedWord;
    private int totalSpamWords = 0;
    private int totalNoSpamWords = 0;
    private int spamOccurrence = 0;
    private int noSpamOccurrence = 0;

    /**
     * Initialized the object, and sets the private variable searchedWord
     *
     * @param focusWord the word for which we store probability data
     */
    public WordCounter(String focusWord) {
        this.searchedWord = focusWord;
    }

    /**
     * Gets the focus word of the probability data storer
     *
     * @return the word for which data is stored.
     */
    public String getFocusWord() {
        return this.searchedWord;
    }

    /**
     * This method updates probability data about the searched word based on training data
     *
     * @param document is the training document for updating probability data.
     */
    public void addSample(String document) {
        // Setting the initial data
        String[] data = document.split(" ");
        int occurrence = 0;

        // Extracting classification
        int classification = Integer.parseInt(data[0]);

        // Extracting occurrence of searched word
        for (String comparedS : data) {
            if(comparedS.equals(searchedWord)) {
                occurrence++;
            }
        }

        // Extracting number of words
        int numWords = data.length - 1;

        // Calculations
        // Data is not a spam
        if (classification == 0) {
            this.totalNoSpamWords += numWords;
            this.noSpamOccurrence += occurrence;
        }
        // Data is a spam
        else if (classification == 1) {
            this.totalSpamWords += numWords;
            this.spamOccurrence += occurrence;
        }
    }

    /**
     * This method decides whehter the object is properly trained and ready to make inferences
     *
     * @return a boolean: true = object properly trained, false = not ready to make inferences
     */
    public boolean isCounterTrained() {
        return Math.max(this.noSpamOccurrence, this.spamOccurrence) > 0 &&
                Math.min(this.totalNoSpamWords, this.totalSpamWords) > 0;
        // True if the lower of the two occurrences is more than 0
    }

    /**
     * This method acts as an interface to return probability data
     *
     * @return the probability P(word is present|document is not a spam)
     * @throws IllegalStateException if the object is not properly trained to make inferences
     */
    public double getConditionalNoSpam() throws IllegalStateException {
        if (this.isCounterTrained()) {
            return (double) this.noSpamOccurrence / (double) this.totalNoSpamWords;
        } else {
            throw new IllegalStateException();
        }
    }

    /**
     * This method acts as an interface to return probability data
     *
     * @return the probability P(word is present|document is a spam)
     * @throws IllegalStateException if the object is not properly trained to make inferences
     */
    public double getConditionalSpam() throws IllegalStateException {
        if (this.isCounterTrained()) {
            return (double) this.spamOccurrence / (double) this.totalSpamWords;
        } else {
            throw new IllegalStateException();
        }
    }
}