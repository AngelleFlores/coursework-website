import java.util.Locale;

public class Word {
    private static char[] chars;

    static {
        chars = new char[26];
        for (int i = 0; i < 26; i++)
            chars[i] = 0;
    }

    private String lexeme;
    private String definition;
    private int length;

    public Word(String l) {
        this.lexeme = l.toLowerCase(Locale.ENGLISH);
        this.definition = "definition of " + l + " goes here";
        this.length = l.length();
    }

    public String lexeme() {
        return this.lexeme;
    }

    public String getDefinition() {
        return this.definition;
    }

    public int length() {
        return this.length;
    }

    /**
     * Pretend measure of word complexity based on proportion of unique chars.
     *
     * @return # of unique characters / length of word
     */
    public float level() {
        int count = 0;
        for (int i = 0; i < 26; i++)
            chars[i] = 0;
        for (int i = 0, j = length; i < j; i++) {
            int k = lexeme.charAt(i) - 'a';
            chars[k]++;
            if (chars[k] == 1) count++;
        }
        return (float) count / length;
    }

    @Override
    public String toString() {
        return this.lexeme;
    }


}
