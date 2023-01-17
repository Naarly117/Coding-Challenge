import java.io.*;
import java.util.*;

public class Main {
	
	public static void main(String[] args) {
		
		// Initialize variables
		int testingGit = 7;
		
		// Total number of words in the input file
		int wordCount = 0;
		
		// Map of each word to the number of times it occurs in the input file
		TreeMap<String, Integer> wordsAndCounts = new TreeMap<String, Integer>();
		
		// ArrayList of each sentence in the input file
		ArrayList<String> allSentences = new ArrayList<String>();
		
		String temp = "";
		String currentSentence = "";
		String path = System.getProperty("user.dir");
		
		// Read in and process the input file
		try {
			File inFile = new File(path + "\\Coding Challenge.txt");
			Scanner myReader = new Scanner(inFile);
			while (myReader.hasNext()) {
				
				// Read in the next word of the input file
				temp = myReader.next();
				
				// Add the current word to the current sentence
				currentSentence += temp;
				
				// Check the current word for punctuation to determine if a new sentence is starting
				if (temp.contains(".") || temp.contains("?") || temp.contains("!")) {
					// If temp is the end of a sentence, add the current sentence to
					// allSentences and reset it
					allSentences.add(currentSentence);
					currentSentence = "";
				} else {
					// If the sentence is continuing, add a space before the next word
					currentSentence += " ";
				}
				
				// Remove all punctuation and make each word lowercase to ensure 
				// correct comparisons for word counts
				temp = temp.toLowerCase().replaceAll("[^a-zA-Z-]", "");
				
				if (!wordsAndCounts.containsKey(temp)) {
					// If the word has not yet occurred, add it to wordsAndCounts
					wordsAndCounts.put(temp, 1);
				} else {
					// If the word has already occurred, increase its count
					wordsAndCounts.replace(temp, wordsAndCounts.get(temp), wordsAndCounts.get(temp)+1);
				}
				wordCount++;
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("File does not exist");
		}
		
		// At this point all information from the input file has been processed and is stored in two ways:
		// -Each individual word and its frequency is stored in wordsAndCounts (Map)
		// -Each individual sentence is stored in allSentences (ArrayList)
		
		// Initialize variables for determining top 10 most common words
		String mostCommonWord = "";
		int mostCommonWordCount = 0;
		String[] topTenWords = new String[10];
		int[] topTenCounts = new int[10];
		
		// Iterate through wordsAndCounts to determine the most common word still in the Map,
		// add it to topTenWords and its count to topTenCounts, and then remove it from the Map
		for (int i = 0; i < 10; i++) {
			for (String key : wordsAndCounts.keySet()) {
				// Determine the most common word currently in wordsAndCounts
				if (wordsAndCounts.get(key) > mostCommonWordCount) {
					mostCommonWord = key;
					mostCommonWordCount = wordsAndCounts.get(key);
				}
			}
			// Put the current most common word in topTenWords and topTenCounts
			topTenWords[i] = mostCommonWord;
			topTenCounts[i] = wordsAndCounts.get(mostCommonWord);
			// Remove it so that the next iteration of the loop will retrieve the next most common word
			wordsAndCounts.remove(mostCommonWord);
			// Reset variables for next loop iteration
			mostCommonWord = "";
			mostCommonWordCount = 0;
		}
		
		// Perform one more iteration through the Map to check for a tie on the 10th most common word
		for (String key : wordsAndCounts.keySet()) {
			if (wordsAndCounts.get(key).equals(topTenCounts[9])) {
				// Concatenate ties to the 10th string
				topTenWords[9] = (topTenWords[9] + ", " + key);
			}
		}
		
		// Check all sentences in order for the presence of the most common word
		String lastSentence = "";
		for (int i = 0; i < allSentences.size(); i++) {
			if (allSentences.get(i).contains(topTenWords[0])) {
				// Since each sentence was read into allSentences in the correct order, we can simply
				// overwrite lastSentence each time a later sentence is found that contains the most
				// common word
				lastSentence = allSentences.get(i);
			}
		}

		// At this point we have achieved all three objectives:
		// -Total word count is stored in totalWordCount
		// -The top ten most frequently occurring words and their counts are stored in topTenWords[] and topTenCounts[]
		// -The last sentence containing the most frequent word is stored in lastSentence
		
		// Output results to a new text file (or overwrite old results if output.txt already exists)
		try {
			File outFile = new File(path + "\\output.txt");
			PrintStream ps = new PrintStream(outFile);
			ps.println("Total word count: " + wordCount + "\n");
			ps.println("Top 10 most frequently occurring words:");
			for (int i = 0; i < topTenWords.length; i++) {
			        ps.println("#" + (i + 1) + ": " + topTenWords[i] + " (" + topTenCounts[i] + ")");
			}
			ps.println("\nLast sentence containing the most frequent word (\"" + topTenWords[0] + "\"):");
			ps.println("  " + lastSentence);
		} catch (Exception e) {
			System.out.println("Unable to create output file");
		}
		
		// Output results to the console
		System.out.println("\nTotal word count: " + wordCount + "\n");
		System.out.println("Top 10 most frequently occurring words:");
		for (int i = 0; i < topTenWords.length; i++) {
			System.out.println("#" + (i + 1) + ": " + topTenWords[i] + " (" + topTenCounts[i] + ")");
		}
		System.out.println("\nLast sentence containing the most frequent word (\"" + topTenWords[0] + "\"):");
		System.out.println("  " + lastSentence + "\n");
		
	}
	
}
