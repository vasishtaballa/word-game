package dev.vasishta.game.words;

import java.util.Scanner;

public class CountBlacksWhites {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Choose some word man");
		String chosenWord = sc.next();
		int chances = 0, totalChances = 10;
		while (true) {
			System.out.println("Give me word Buddy!");
			String word = sc.next();
			if (word.equals("STOP"))
				break;
			int whitesCount = 0, blacksCount = 0;
			for (int i = 0; i < word.length(); i++) {
				if (chosenWord.indexOf(word.charAt(i)) > -1) {
					if (chosenWord.indexOf(word.charAt(i)) == word.indexOf(word.charAt(i)))
						whitesCount += 1;
					else
						blacksCount += 1;
				}
			}
			chances += 1;
			if (whitesCount == 4) {
				System.out.println("Yay! You Won. Answer is correct.");
				break;
			} else
				System.out.println(
						whitesCount + "w" + blacksCount + "b. " + "Still " + (totalChances - chances) + " left");
		}
		sc.close();
	}

}
