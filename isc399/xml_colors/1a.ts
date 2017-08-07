Script started on Tue 14 Jun 2016 02:08:29 PM EDT
[4maltair[24m:[1m~/public_html/coursework/isc399/xml_colors[0m> cat Color.java
import java.util.Locale;

/**
* Define a Color object
* 
* @author Angelle Flores
*/

public class Color {

    private String name;
    private String hex;

    public Color(String n, String h) {
        this.name = n.toLowerCase(Locale.ENGLISH);
        this.hex = h;
    }

    public String name() {
        return this.name;
    }

    public String getHex() {
        return this.hex;
    }

    @Override
    public String toString() {
        return this.name;
    }

}
[4maltair[24m:[1m~/public_html/coursework/isc399/xml_colors[0m> cat Fetcher.java
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
[4maltair[24m:[1m~/public_html/coursework/isc399/xml_colors[0m> cat PullColor.java
import static java.lang.System.out;

import java.util.List;

/**
* Pull data from a list of colors
* 
* @author Angelle Flores
*/

public class PullColor {
    static List<Color> colors;
    public static void main(String[] args) throws Exception {

        if (args.length != 1) {
            out.println("Usage: java PullColor xml-file-to-process");
            System.exit(1);
        }

        Fetcher f = new Fetcher(args[0]);
        colors = f.fetch();

        if (colors != null) {
            for (Color c : colors) {
                out.println("Color: |" + c + "|");
                out.println("Hex: |" + c.getHex() + "|\n");
            }
        } else {
            out.println("parser returned null list of colors.");
        }
    }
}
[4maltair[24m:[1m~/public_html/coursework/isc399/xml_colors[0m> javac -classpath .:xpp3-1.1.3.4.C.jar PullCo lor.java
[4maltair[24m:[1m~/public_html/coursework/isc399/xml_colors[0m> java -classpath .:xpp3.[K-1.1.3.4.C.jar PullCol or x[Kweb_colors.xml
Color: |aqua|
Hex: |#00FFFF|

Color: |chartreuse|
Hex: |#7FFF00|

Color: |crimson|
Hex: |#DC143C|

Color: |darkblue|
Hex: |#00008B|

Color: |darkgreen|
Hex: |#006400|

Color: |darkmagenta|
Hex: |#8B008B|

Color: |deeppink|
Hex: |#FF1493|

Color: |gold|
Hex: |#FFD700|

Color: |greenyellow|
Hex: |#ADFF2F|

Color: |indigo|
Hex: |#4B0082|

Color: |lime|
Hex: |#00FF00|

Color: |orange|
Hex: |#FFA500|

Color: |purple|
Hex: |#800080|

[4maltair[24m:[1m~/public_html/coursework/isc399/xml_colors[0m> exit
exit

Script done on Tue 14 Jun 2016 02:12:14 PM EDT
