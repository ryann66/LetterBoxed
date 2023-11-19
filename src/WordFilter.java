public class WordFilter {
    Character[][] edges;

    /**
     * Creates a new WordFilter for LetterBoxed
     * @param edges an array of edges where each edge is an array of the
     *              characters on that edge
     *              edges.length >= 2
     */
    public WordFilter (Character[][] edges) {
        // note: no guarantee on length of edges
        // note: edges may be jagged
        // note: characters may appear in multiple places in edges todo!!!!
        //       however may only appear once in each edge
        this.edges = edges;
    }

    /**
     * Checks if the word passes the filter
     * @param word the word to check
     * @return true if passes, else false
     */
    public boolean Valid(String word) {
        if (word.isEmpty()) return false;
        int i = findEdge(word.charAt(0));
        if (i == -1) return false;
        return Valid(word.substring(1), i);
    }

    private boolean Valid(String word, int lastEdge) {
        if (word.isEmpty()) return true;
        int i = findEdge(word.charAt(0));
        if (i == -1 || i == lastEdge) return false;
        return Valid(word.substring(1), i);
    }

    /**
     * Returns the index of the edge containing the given character,
     * or -1 if not found
     * @param c the character to search for
     * @return the index of the edge containing the given character
     */
    private int findEdge(char c) {
        for (int i = 0; i < edges.length; i++) {
            for (int j = 0; j < edges[i].length; j++)
                if (edges[i][j] == c) return i;
        }
        return -1;
    }
}
