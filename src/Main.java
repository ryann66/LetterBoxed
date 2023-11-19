import com.google.common.collect.ListMultimap;
import com.google.common.collect.MultimapBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    private static final File BASIC_DICT = new File("src/dict.txt");

    public static void main(String[] args) throws FileNotFoundException {
        Character[][] edges;
        do {
            edges = getEdgesFromUser();
        } while (edges.length < 2);

        WordFilter filter = new WordFilter(edges);
        ListMultimap<Character, WordStruct> charMap = createValidWordsMultiMap(filter, BASIC_DICT);


        for (Character c : charMap.keySet()) {
            List<WordStruct> words = charMap.get(c);
            System.out.println(c + ": " + words);
        }
    }

    /**
     * Creates a multimap from the first character of words to the best WordStruct possible using the given
     * text dictionary file
     * @param filter a WordFilter that all words must use
     * @return a multimap from the first character of the word to the most general word structs possible
     */
    private static ListMultimap<Character, WordStruct> createValidWordsMultiMap(WordFilter filter, File dict)
            throws FileNotFoundException {
        Scanner input = new Scanner(dict);
        String line;
        ListMultimap<Character, WordStruct> charMap = MultimapBuilder.hashKeys().linkedListValues().build();

        outer: while (input.hasNextLine()) {
            if (!filter.Valid(line = input.nextLine())) continue outer;
            WordStruct word = new WordStruct(line);
            // add word to charMap, attempting to coalesce (if possible)
            List<WordStruct> words = charMap.get(word.firstLetter);
            Iterator<WordStruct> iter = words.iterator();
            while (iter.hasNext()) {
                WordStruct other = iter.next();
                int subset = word.subsetWord(other);
                if (subset == -1) {
                    // word is a subset of other
                    // all other words must also be a subset
                    // toss new word and continue
                    continue outer;
                }
                if (subset == 1) {
                    // other is a subset of word
                    // remove other
                    iter.remove();
                }
                // else: assumed unrelated, no action needed
            }
            words.add(word);
        }

        return charMap;
    }

    /**
     * Prompts the user to enter the characters on each edge
     * @return an array of edges, where each edge is an array of the characters on it
     *         may be a jagged array
     */
    private static Character[][] getEdgesFromUser() {
        List<Character[]> edgeList = new ArrayList<>();
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the characters of each edge on a new line.  Enter an empty line to stop.");
        Set<Character> charSet = new HashSet<>();
        boolean nonEmpty;
        do {
            nonEmpty = false;
            for (char c : input.nextLine().toCharArray()) {
                if (validCharacter(c)) {
                    nonEmpty = true;
                    charSet.add(c);
                }
            }
            if (nonEmpty) {
                edgeList.add(charSet.toArray(new Character[0]));
                charSet.clear();
            }
        } while (nonEmpty);
        return edgeList.toArray(new Character[0][0]);
    }

    private static boolean validCharacter(char c) {
        return Character.isAlphabetic(c);
    }
}