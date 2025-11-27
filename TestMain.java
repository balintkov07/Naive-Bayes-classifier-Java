import java.io.File;
import java.io.IOException;

/**
 * @author 123456bk Balint Kovacs
 */
public class TestMain {
	public static void main(String [] args) throws IOException {
		testWordCounter();
		testClassification();
		testFileReadingWriting();
		testConfusionMatrix();
	}
	
	public static void testWordCounter() {
		WordCounter wc = new WordCounter("good");
		System.out.println(wc.getFocusWord());
		wc.addSample("1 good bad bad bad");
		wc.addSample("0 bad good good");
		wc.addSample("0 bad good");
		System.out.println(wc.getConditionalSpam());
		System.out.println(wc.getConditionalNoSpam());		
	}
	
	public static void testClassification() {
		String [] words = {"good", "bad"};
		NaiveBayes nb = new NaiveBayes(words);
		nb.addSample("1 good bad bad bad casino");
		nb.addSample("0 bad good good pizza");
		nb.addSample("0 bad good tapas");
		System.out.println(nb.classify("good"));
		System.out.println(nb.classify("bad"));
		System.out.println(nb.classify("good bad bad"));
		System.out.println(nb.classify("pizza"));
		System.out.println(nb.classify("casino"));		
	}

	public static void testFileReadingWriting() throws IOException {
		String [] words = {"good", "bad"};
		NaiveBayes nb = new NaiveBayes(words);
		nb.trainClassifier(new File("traindata.txt"));
		nb.classifyFile(new File("newdata.txt"), new File("classifications.txt"));
	}

	
	public static void testConfusionMatrix() throws IOException {
		String [] words = {"good", "bad"};
		NaiveBayes nb = new NaiveBayes(words);
		nb.trainClassifier(new File("traindata.txt"));
		ConfusionMatrix cm = nb.computeAccuracy(new File("testdata.txt"));
		System.out.println(cm.getTruePositives());
		System.out.println(cm.getFalsePositives());
		System.out.println(cm.getTrueNegatives());
		System.out.println(cm.getFalseNegatives());		
	}
	
}
