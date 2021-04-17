package dev.vasishta.game.words.utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;

public class WordsBuilder {

	public static void main(String[] args) {
		String ALL_WORDS_FILE_PATH = "C:\\Users\\Vasishta\\Desktop\\Word Game\\all-dict-words.txt";
		String FOUR_LETTER_WORDS_FILE_PATH = "C:\\Users\\Vasishta\\Desktop\\Word Game\\four-letter-words.txt";
		try {
			File allWordsFile = new File(ALL_WORDS_FILE_PATH);
			BufferedReader br = new BufferedReader(new FileReader(allWordsFile));
			FileWriter fw = new FileWriter(new File(FOUR_LETTER_WORDS_FILE_PATH));
			String line;
			while ((line = br.readLine()) != null) {
				if (isFourLetterWord(line) && areNoRepeatedLtrs(line)) {
					fw.write(line);
					fw.write("\r\n");
				}
			}
			fw.close();
			br.close();
			System.out.println("Separated desired words..!");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private static boolean areNoRepeatedLtrs(String line) {
		HashMap<Character, Integer> freqMap = new HashMap<>();
		for (int i = 0; i < line.length(); i++) {
			if (freqMap.containsKey(line.charAt(i)))
				return false;
			freqMap.put(line.charAt(i), 1);
		}
		return true;
	}

	private static boolean isFourLetterWord(String line) {
		return line.length() == 4;
	}

}
