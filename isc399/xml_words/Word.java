import java.util.Locale;

/**
 * Define a Word object
 * 
 * @author Angelle Flores
 */
public class Word {

    private String lexeme;
    private String definition;
    private String pos;

    public Word(String l, String d, String p) {
        this.lexeme = l.toLowerCase(Locale.ENGLISH);
        this.definition = d;
        this.pos = p;
    }

    public String lexeme() {
        return this.lexeme;
    }

    public String getDefinition() {
        return this.definition;
    }

    public String getPos() {
        return this.pos;
    }

    @Override
    public String toString() {
        return this.lexeme;
    }

}
