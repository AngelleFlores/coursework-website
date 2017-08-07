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
    static private final String COLORS = "colors";
    static private final String COLOR = "color";
    static private final String NAME = "name";
    static private final String HEX = "hex";

    static private String nameSpace;

    static {
        nameSpace = null;
    }

    private String filename = null;
    
    public Fetcher(String filename) {
        this.filename = filename;
    }
    
    public List<Color> fetch() {
        try {
            XmlPullParserFactory ppf = XmlPullParserFactory.newInstance();
            XmlPullParser pp = ppf.newPullParser();
            pp.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            FileReader r = new FileReader(filename);
            pp.setInput(r);
            pp.next();
            return parseColors(pp);
        } catch (XmlPullParserException xppe) {
            out.println("DEBUG: XmlPullParserException");
            return null;
        } catch (Exception ioe) {
            out.println("DEBUG: plain old Exception");
            return null;
        } finally {
        }

    }

    private List<Color> parseColors(XmlPullParser parser) throws XmlPullParserException, IOException {
        List<Color> entries = new ArrayList<>();

        parser.require(XmlPullParser.START_TAG, nameSpace, COLORS);
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the color tag
            if (name.equals(COLOR)) {
                entries.add(readColor(parser));
            } else {
                skip(parser);
            }
        }
        return entries;
    }

    private Color readColor(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, nameSpace, COLOR);
        String name = null;
        String hex = null;
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String tag = parser.getName();
            if (tag.equals(NAME)) {
                name = readName(parser);
            } else if (tag.equals(HEX)) {
                hex = readHex(parser);
            } else {
                skip(parser);
            }
        }
        return new Color(name, hex);
    }

    private String readName(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, nameSpace, NAME);
        String name = readText(parser);
        parser.require(XmlPullParser.END_TAG, nameSpace, NAME);
        return name;
    }

    private String readHex(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, nameSpace, HEX);
        String hex = readText(parser);
        parser.require(XmlPullParser.END_TAG, nameSpace, HEX);
        return hex;
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
