package dev.vasishta.game.words;

import dev.vasishta.game.words.utility.Utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Main {

	public static void main(String[] args) {
		Set<String> words = new HashSet<>();
		getWords(words);
		startGame(words);
	}

	private static void startGame(Set<String> words) {
		String input = "START";
		HashMap<Character, Boolean> characterMap = Utility.getInitializedMap();
		Scanner sc = new Scanner(System.in);
		try {
			while (true) {
				input = sc.next();
				if (input.equals("STOP"))
					break;
				WordBean wordBean = Utility.getWordBean(input);
				words = Utility.filterWords(wordBean, characterMap, words);
				words.remove(wordBean.getWord());
				System.out.println("No of possible words are : " + words.size() + " Do you want to display them?");
				String shouldDisplay = sc.next();
				if (!shouldDisplay.equals("n"))
					Utility.computeAndDisplayWords(words);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			sc.close();
		}
	}

	private static void getWords(Set<String> words) {
		String FOUR_LETTER_WORDS_FILE_PATH = "C:\\Users\\Vasishta\\Desktop\\Word Game\\four-letter-words.txt";
		try {
			File file = new File(FOUR_LETTER_WORDS_FILE_PATH);
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			while ((line = br.readLine()) != null)
				words.add(line);
			br.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
