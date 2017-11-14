import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * A heap of MESS
 * @author wongt1
 *
 */
public class Markov_Chain {

	/**
	 * Main
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
		start();
	}
	
	/**
	 * @throws IOException
	 */
	public static void start() throws IOException{
		Scanner scanner = new Scanner(new File("src/greenEggs.txt"));
		TreeMap<String, TreeMap<String, Integer>> freqTable = new TreeMap<>();
		
		construct(scanner, freqTable, cleanUpString(scanner.next()), cleanUpString(scanner.next()));
		generate(freqTable);
		
		//To print out the frequency table 
//		for(String s : freqTable.keySet()){
//			System.out.println(s + freqTable.get(s));
//		}
		
		scanner.close();
	}
	
	/**
	 * Method to construct the frequency table 
	 * @param br
	 * @param freqTable
	 * @param line
	 * @throws IOException
	 */
	private static void construct(Scanner scanner, TreeMap<String, TreeMap<String, Integer>> freqTable, String firstWord, String secondWord) throws IOException{
		String thirdWord = "";
		if(scanner.hasNext()) { thirdWord = cleanUpString(scanner.next()); }
		//End of file, base case
		else { return; }
		String biString =  firstWord + " " + secondWord;
		
		//TreeMap for the third word, keeps the frequency of the third word
		TreeMap<String, Integer> nextWord = new TreeMap<>();
		//First mapping of the biString
		if(!freqTable.containsKey(biString)){
			nextWord = new TreeMap<>();
			nextWord.put(thirdWord, 1);
		}
		//Use the TreeMap of the existing biString
		else{
			nextWord = freqTable.get(biString);
			if(freqTable.get(biString).get(thirdWord) != null) {
				int thirdWordCount = freqTable.get(biString).get(thirdWord); 
				nextWord.put(thirdWord, thirdWordCount + 1);
			} else{
				nextWord.put(thirdWord, 1);
			}
		}
		
		//puts the biString and the frequency of the next word into the frequency table
		freqTable.put(biString, nextWord);
		
		//Runs this method until end of file
		construct(scanner, freqTable, secondWord, thirdWord);
	}

	/**
	 * Generate the random 50 words
	 * @param freqTable
	 */
	private static void generate(TreeMap<String, TreeMap<String, Integer>> freqTable){
		//All this to get a random biGram
		int random = (new Random()).nextInt(freqTable.size());
		ArrayList<String> list = new ArrayList<String>();
		Iterator<String> iterator = freqTable.keySet().iterator();
		//Adds all the first possible biGram into an ArrayList, O(freqTable.size)
		while(iterator.hasNext()){
			list.add(iterator.next());
		}
		String firstBiGram = list.get(random);
		System.out.print(firstBiGram + " ");
		list.clear();
		
		//Generate the next word, up to 50 (change if needed)
		for(int i = 2; i <= 50; i++){
			TreeMap<String, Integer> thirdWord = freqTable.get(firstBiGram);
			//There is no third word following the biGram, which exits the program automatically
			if(thirdWord == null){
				System.out.println();
				return;
			}
			
			
			iterator = thirdWord.keySet().iterator();
			while(iterator.hasNext()){
				String word = iterator.next();
				//Loop to add the words based on it's frequency
				for(int j = 0; j < thirdWord.get(word); j++){
					list.add(word);
				}
				
			}
			random = (new Random()).nextInt(list.size());
			
			//Third Word
			String firstWord = firstBiGram.split(" ")[1];
			String randomWord = list.get(random);
			firstBiGram = firstWord + " " + randomWord;
			
			System.out.print(randomWord + " ");
			list.clear();
		}
		System.out.println();
	}
	
	/**
	 * Method to clean up the String.
	 * @param s
	 * @return
	 */
	private static String cleanUpString(String s) {
		s = s.toLowerCase();
		s = s.replaceAll("[!?]", "");
		s = s.replaceAll("[,.-]", "");
		s = s.replaceAll("’s", "");
		s = s.replaceAll("'s", "");
		s = s.replaceAll("[â€˜™]", "");
		s = s.replaceAll("[0123456789]", "");
		s = s.replace("\n", "");
		s = s.trim();
		return s;
	}
}
