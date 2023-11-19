import java.util.HashSet;
import java.util.Set;

public class WordStruct {
    public final char firstLetter, lastLetter;
    public String word;
    public Set<Character> charset;

    public WordStruct (String word) {
        this.word = word.toLowerCase();
        firstLetter = this.word.charAt(0);
        lastLetter = this.word.charAt(this.word.length() - 1);
        charset = new HashSet<>();

        for (int i = 0; i < word.length(); i++) {
            charset.add(this.word.charAt(i));
        }
    }

    /**
     * Compares this with the given word to check if there is a subset word relationship
     * two words have a subset word relationship if they start and end with the same letters and the set of
     * one is a subset of the other
     * @param other the word to check subset word with
     * @return -1 if this is a subset word of other
     *          1 if other is a subset word of this
     *          0 if this and other are unrelated
     *         If the two words have equal charsets, then the shorter word will be treated as the subset word
     */
    public int subsetWord (WordStruct other) {
        if (this.firstLetter != other.firstLetter || this.lastLetter != other.lastLetter) return 0;
        if (this.charset.size() > other.charset.size()) {
            if (this.charset.containsAll(other.charset)) return 1;
            return 0;
        } else if (this.charset.size() < other.charset.size()) {
            if (other.charset.containsAll(this.charset)) return -1;
            return 0;
        } else {
            if (this.charset.containsAll(other.charset)) {
                if (this.word.length() > other.word.length()) return 1;
                return -1;
            }
            return 0;
        }
    }

    public String toString() {
        return this.word;
    }
}
