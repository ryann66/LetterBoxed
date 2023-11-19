import java.util.*;

public class WordStruct implements Comparable<WordStruct> {
    public final char firstLetter, lastLetter;
    public Queue<String> words;
    public Set<Character> charset;

    public WordStruct (String word) {
        words = new LinkedList<>();
        words.add(word);
        firstLetter = word.charAt(0);
        lastLetter = word.charAt(word.length() - 1);
        charset = new HashSet<>();

        for (int i = 0; i < word.length(); i++) {
            charset.add(word.charAt(i));
        }
    }

    /**
     * Minimalist constructor for private internal use
     */
    private WordStruct (char firstLetter, char lastLetter) {
        this.firstLetter = firstLetter;
        this.lastLetter = lastLetter;
        this.words = new LinkedList<>();
        this.charset = new HashSet<>();
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
                if (this.totalLength() > other.totalLength()) return 1;
                return -1;
            }
            return 0;
        }
    }

    public String toString() {
        Iterator<String> iter = words.iterator();
        StringBuilder str = new StringBuilder(iter.next());
        while (iter.hasNext()) {
            str.append(" ").append(iter.next());
        }
        return str.toString();
    }

    public int totalLength() {
        int sum = 0;
        for (String s : words) {
            sum += s.length();
        }
        return sum;
    }

    /**
     * Producer method that creates a new WordStruct := this appended by word
     * @param word the word to append
     * @return a new WordStruct this + word
     */
    public WordStruct append(WordStruct word) {
        WordStruct ret = new WordStruct(this.firstLetter, word.lastLetter);
        ret.words.addAll(this.words);
        ret.words.addAll(word.words);
        ret.charset.addAll(this.charset);
        ret.charset.addAll(word.charset);
        return ret;
    }

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     *
     * <p>NOTE: this implementation is inconsistent with equals</p>
     * <p>NOTE: this implementation is not transitive or reflective, thus
     * <code>signum(x.compareTo(y)) != -signum(y.compareTo(x))</code> in some cases</p>
     *
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     */
    @Override
    public int compareTo(WordStruct o) {
        if (o == null) throw new NullPointerException();
        if (this.words.size() < o.words.size()) return -1;
        if (this.words.size() > o.words.size()) return 1;
        if (this.charset.size() < o.charset.size()) return -1;
        return 1;
    }
}
