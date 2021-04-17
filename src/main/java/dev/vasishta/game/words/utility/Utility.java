package dev.vasishta.game.words.utility;


import dev.vasishta.game.words.WordBean;

import java.util.*;

public class Utility {
    public static WordBean getWordBean(String input) {
        String[] tokens = input.split("-");
        WordBean wordBean = new WordBean();
        wordBean.setWord(tokens[0]);
        wordBean.setResult(tokens[1]);
        wordBean.setBlacks(getCount(wordBean.getResult(), 'b'));
        wordBean.setWhites(getCount(wordBean.getResult(), 'w'));
        return wordBean;
    }

    private static int getCount(String result, char ch) {
        if (result.indexOf(ch) > -1)
            return result.charAt(result.indexOf(ch) - 1) - '0';
        return 0;
    }

    public static HashMap<Character, Boolean> getInitializedMap() {
        HashMap<Character, Boolean> map = new HashMap<>();
        for (int i = 97; i < 123; i++) {
            char ch = (char) i;
            map.put(ch, true);
        }
        return map;
    }

    public static Set<String> filterWords(WordBean wordBean, HashMap<Character, Boolean> characterMap,
                                          Set<String> words) {
        if (wordBean.getBlacks() == 0 && wordBean.getWhites() == 0)
            return handleEmptyWords(wordBean, characterMap, words);
        else {
            ArrayList<Character> subset = new ArrayList<>();
            ArrayList<ArrayList<Character>> subsets = new ArrayList<>();
            getSubsets(wordBean.getWord().toCharArray(), wordBean.getBlacks(), 0, subset, 0, subsets);
            if (wordBean.getBlacks() > 0)
                return handleBlackWords(subsets, wordBean, characterMap, words);
            subsets.clear();
            getSubsets(wordBean.getWord().toCharArray(), wordBean.getWhites(), 0, subset, 0, subsets);
            if (wordBean.getWhites() > 0)
                return handleWhiteWords(subsets, wordBean, characterMap, words);
            return null;
        }
    }

    private static Set<String> handleWhiteWords(ArrayList<ArrayList<Character>> subsets, WordBean wordBean,
                                                HashMap<Character, Boolean> characterMap, Set<String> words) {
        return generatePossibleWords(subsets, wordBean, words, characterMap, 'w');
    }

    private static Set<String> handleBlackWords(ArrayList<ArrayList<Character>> subsets, WordBean wordBean,
                                                HashMap<Character, Boolean> characterMap, Set<String> words) {
        return generatePossibleWords(subsets, wordBean, words, characterMap, 'b');
    }

    private static Set<String> generatePossibleWords(ArrayList<ArrayList<Character>> subsets, WordBean wordBean,
                                                     Set<String> words, HashMap<Character, Boolean> characterMap, char ch) {
        Set<String> possibleWords = new HashSet<>();
        Iterator<String> itr = words.iterator();
        while (itr.hasNext()) {
            String word = itr.next();
            ArrayList<ArrayList<Character>> possibleSubsets = getPossibleSubsets(word, wordBean, subsets);
            for (ArrayList<Character> subset : possibleSubsets) {
                if (subset != null) {
                    if (ch == 'b' && isBlackFitWord(word, subset, wordBean))
                        possibleWords.add(word);
                    else if (ch == 'w' && isWhiteFitWord(word, subset, wordBean))
                        possibleWords.add(word);
                    updateMap(characterMap, subset, word, wordBean.getWord());
                }
            }
        }
        return possibleWords;
    }

    private static boolean isWhiteFitWord(String word, ArrayList<Character> subset, WordBean wordBean) {
        int whitesCount = wordBean.getWhites();
        for (int i = 0; i < subset.size(); i++) {
            if (word.indexOf(subset.get(i)) != wordBean.getWord().indexOf(subset.get(i)))
                return false;
        }
        int count = subset.size();
        for (int i = 0; i < wordBean.getWord().length(); i++) {
            if (!subset.contains(wordBean.getWord().charAt(i))) {
                if (StringUtils.indexOf(wordBean.getWord(), wordBean.getWord().charAt(i)) == StringUtils.indexOf(word,
                        wordBean.getWord().charAt(i)))
                    count += 1;
            }
        }
        return count == whitesCount;
    }

    private static boolean isBlackFitWord(String word, ArrayList<Character> subset, WordBean wordBean) {
        for (int i = 0; i < subset.size(); i++) {
            if (word.indexOf(subset.get(i)) == wordBean.getWord().indexOf(subset.get(i)))
                return false;
        }
        return true;
    }

    private static ArrayList<ArrayList<Character>> getPossibleSubsets(String word, WordBean wordBean,
                                                                      ArrayList<ArrayList<Character>> subsets) {
        Iterator<ArrayList<Character>> itr = subsets.iterator();
        ArrayList<ArrayList<Character>> possibleSubsets = new ArrayList<>();
        while (itr.hasNext()) {
            ArrayList<Character> subset = itr.next();
            int j = 0;
            for (j = 0; j < subset.size(); j++) {
                if (!word.contains(String.valueOf(subset.get(j))))
                    break;
            }
            if (j == subset.size())
                possibleSubsets.add(subset);
        }
        return possibleSubsets;
    }

    private static void getSubsets(char[] charArray, int blacks, int index, ArrayList<Character> subset, int i,
                                   ArrayList<ArrayList<Character>> subsets) {
        if (index == blacks) {
            ArrayList<Character> sbst = new ArrayList<>();
            for (int idx = 0; idx < blacks; idx++) {
                sbst.add(subset.get(idx));
            }
            subsets.add(sbst);
            return;
        }
        if (i >= charArray.length)
            return;
        subset.add(index, charArray[i]);
        getSubsets(charArray, blacks, index + 1, subset, i + 1, subsets);
        getSubsets(charArray, blacks, index, subset, i + 1, subsets);
    }

    private static Set<String> handleEmptyWords(WordBean wordBean, HashMap<Character, Boolean> characterMap,
                                                Set<String> words) {
        String inputWord = wordBean.getWord();
        for (int i = 0; i < inputWord.length(); i++)
            characterMap.put(inputWord.charAt(i), false);
        Iterator<String> itr = words.iterator();
        while (itr.hasNext()) {
            String word = itr.next();
            if (wordNotIncluded(word, inputWord)) {
                itr.remove();
            }
        }
        return words;
    }

    private static boolean wordNotIncluded(String word, String inputWord) {
        boolean result = false;
        for (int i = 0; i < inputWord.length(); i++) {
            if (StringUtils.contains(word, inputWord.charAt(i))) {
                result = true;
                break;
            }
        }
        return result;
    }

    private static void updateMap(HashMap<Character, Boolean> characterMap, ArrayList<Character> subset, String word1,
                                  String word2) {
        for (int i = 0; i < subset.size(); i++)
            characterMap.put(subset.get(i), true);
        for (int i = 0; i < word1.length(); i++) {
            if (!subset.contains(word1.charAt(i)))
                characterMap.put(word1.charAt(i), false);
            if (!subset.contains(word2.charAt(i)))
                characterMap.put(word2.charAt(i), false);
        }
    }

    public static void computeAndDisplayWords(Set<String> words) {
        displayWords(words);
    }

    public static void displayWords(Set<String> words) {
        int cols = 23, index = 0;
        Iterator<String> itr = words.iterator();
        while (itr.hasNext()) {
            String word = itr.next();
            System.out.print(word + "\t");
            index += 1;
            index %= cols;
            if (index == 0)
                System.out.println();
        }
    }
}
