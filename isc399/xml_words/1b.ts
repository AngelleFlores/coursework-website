Script started on Thu 16 Jun 2016 12:33:28 PM EDT
[4maltair[24m:[1m~/public_html/coursework/isc399/xml_words[0m> cat Word.java
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
[4maltair[24m:[1m~/public_html/coursework/isc399/xml_words[0m> cat Fetcher.java
import static java.lang.System.out;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Parse XML file indicated by file name
 * 
 * @author Angelle Flores
 */
public class Fetcher {
    static private final String WORDS = "words";
    static private final String WORD = "word";
    static private final String LEXEME = "lexeme";
    static private final String DEFINITION = "definition";
    static private final String POS = "pos";

    static private String nameSpace;

    static {
        nameSpace = null;
    }

    private String filename = null;
    
    public Fetcher(String filename) {
        this.filename = filename;
    }
    
    public List<Word> fetch() {
        try {
            XmlPullParserFactory ppf = XmlPullParserFactory.newInstance();
            XmlPullParser pp = ppf.newPullParser();
            pp.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            FileReader r = new FileReader(filename);
            pp.setInput(r);
            pp.next();
            return parseWords(pp);
        } catch (XmlPullParserException xppe) {
            out.println("DEBUG: XmlPullParserException");
            return null;
        } catch (Exception ioe) {
            out.println("DEBUG: plain old Exception");
            return null;
        } finally {
        }

    }

    private List<Word> parseWords(XmlPullParser parser) throws XmlPullParserException, IOException {
        List<Word> entries = new ArrayList<>();

        parser.require(XmlPullParser.START_TAG, nameSpace, WORDS);
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String tag = parser.getName();
            if (tag.equals(WORD)) {
                entries.add(readWord(parser));
            } else {
                skip(parser);
            }
        }
        return entries;
    }

    private Word readWord(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, nameSpace, WORD);
        String lexeme = null;
        String definition = null;
        String pos = null;
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String tag = parser.getName();
            if (tag.equals(LEXEME)) {
                lexeme = readLeaf(parser, LEXEME);
            } else if (tag.equals(DEFINITION)) {
                definition = readLeaf(parser, DEFINITION);
            } else if (tag.equals(POS)) {
                pos = readLeaf(parser, POS);
            } else {
                skip(parser);
            }
        }
        return new Word(lexeme, definition, pos);
    }
    
    private String readLeaf(XmlPullParser parser, String name) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, nameSpace, name);
        String text = readText(parser);
        parser.require(XmlPullParser.END_TAG, nameSpace, name);
        return text;
    }

    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.next();
        }
        return result;
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }
}
[4maltair[24m:[1m~/public_html/coursework/isc399/xml_words[0m> cat PullWord.java
import static java.lang.System.out;

import java.util.List;

/**
* Pull data from a list of words
* 
* @author Angelle Flores
*/

public class PullWord {
    static List<Word> words;
    public static void main(String[] args) throws Exception {

        if (args.length != 1) {
            out.println("Usage: java PullWord xml-file-to-process");
            System.exit(1);
        }

        Fetcher f = new Fetcher(args[0]);
        words = f.fetch();

        if (words != null) {
            for (Word w : words) {
                out.println("Word: |" + w + "|");
                out.println("Definition: |" + w.getDefinition() + "|");
                out.println("Part of Speech: |" + w.getPos() + "|\n");
            }
        } else {
            out.println("parser returned null list of words.");
        }
    }
}
[4maltair[24m:[1m~/public_html/coursework/isc399/xml_words[0m> javac -classpath ,:[K[K.:xpp3-1.1.3. 4.C.jar PullWord.java
[4maltair[24m:[1m~/public_html/coursework/isc399/xml_words[0m> java -claspaths[1@sspath .:xpp3-1.1.3.4 .C.jar PullWord words_defs.xml
Word: |ab ovo|
Definition: |from the beginning|
Part of Speech: |adverb|

Word: |abecedarian|
Definition: |alphabetically arranged; rudimentary|
Part of Speech: |adjective|

Word: |abeyance|
Definition: |temporary inactivity; suspension|
Part of Speech: |noun|

Word: |abjure|
Definition: |to renounce upon oath|
Part of Speech: |verb|

Word: |abrogate|
Definition: |to abolish by authoritative action; annul|
Part of Speech: |verb|

Word: |abstemious|
Definition: |marked by restraint|
Part of Speech: |adjective|

Word: |abulia|
Definition: |lack of ability to act or to make decisions|
Part of Speech: |noun|

Word: |abstruse|
Definition: |difficult to comprehend|
Part of Speech: |adjective|

Word: |acceptation|
Definition: |generally accepted meaning of a word|
Part of Speech: |noun|

Word: |acedia|
Definition: |apathy|
Part of Speech: |noun|

Word: |acolyte|
Definition: |follower|
Part of Speech: |noun|

Word: |adjure|
Definition: |to advise earnestly|
Part of Speech: |verb|

Word: |adjuvant|
Definition: |assisting in prevention or amelioration [of disease]|
Part of Speech: |adjective|

[4maltair[24m:[1m~/public_html/coursework/isc399/xml_words[0m> exit
exit

Script done on Thu 16 Jun 2016 12:36:31 PM EDT
