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
            // pp.nextTag();
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
                // out.println("DEBUG: continuing");
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the word tag
            // out.println("DEBUG: got name " + name);
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
        String word = readText(parser);
        parser.require(XmlPullParser.END_TAG, nameSpace, WORD);
        return new Word(word);
    }

    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            // parser.nextTag();
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
