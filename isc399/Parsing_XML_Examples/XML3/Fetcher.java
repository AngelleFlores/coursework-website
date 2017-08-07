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
 */
public class Fetcher {
    static private final String WORDS = "words";
    static private final String WORD = "word";
    static private final String LEXEME = "lexeme";
    static private final String DEFINITION = "definition";

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
            String name = parser.getName();
            // Starts by looking for the word tag (entry would have been a better term but...)
            if (name.equals(WORD)) {
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
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals(LEXEME)) {
                lexeme = readLexeme(parser);
            } else if (name.equals(DEFINITION)) {
                definition = readDefinition(parser);
            } else {
                skip(parser);
            }
        }
        return new Word(lexeme, definition);
    }

    // THE NEXT TWO METHODS COULD BE ABSTRACTED INTO ONE: String readLeaf(String, String)
    //     - explain advantages
    // Processes lexeme tags in the feed.
    private String readLexeme(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, nameSpace, LEXEME);
        String lexeme = readText(parser);
        parser.require(XmlPullParser.END_TAG, nameSpace, LEXEME);
        return lexeme;
    }

    // Processes lexeme tags in the feed.
    private String readDefinition(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, nameSpace, DEFINITION);
        String definition = readText(parser);
        parser.require(XmlPullParser.END_TAG, nameSpace, DEFINITION);
        return definition;
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
