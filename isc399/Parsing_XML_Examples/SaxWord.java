
import static java.lang.System.out;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;

import org.xml.sax.helpers.XMLReaderFactory;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;
import java.io.FileReader;

class SaxWordHandler extends org.xml.sax.helpers.DefaultHandler {
    // Accumulator for parsed text
    List<Word> words;
    StringBuffer characterData;
    boolean processingWord;

    public SaxWordHandler(List<Word> words) {
        this.words = words;
        characterData = new StringBuffer();
        processingWord = false;
    }

    public List<Word> getWords () {
        return words;
    }

    public void startDocument () {
        System.out.println("Starting processing of XML data ...");
    }

    public void endDocument () {
        System.out.println("Finished processing XML document");
    }

    // Plain text, not XML element, found.
    public void characters(char[] buffer, int start, int length) {
        characterData.append(buffer, start, length);
    }

    // When the parser encounters the start tag of an element,
    // it calls this method
    public void startElement(String uri, String name,
                               String qName, Attributes attrs) {
        out.println("Entering startElement...");
        // Ready to accumulate new text
        characterData.setLength(0);

        out.println("name is " + name);
        out.println("qName is " + qName);
        // if (qName.equalsIgnoreCase("word")) {
        if (name.equalsIgnoreCase("word")) {
            processingWord = true;
        } else {
        }
    }

    // When the parser encounters the end tag of an element,
    // it calls this method
    public void endElement(String uri, String name, String qName)
    {
        out.println("Entering endElement...");
        out.println("name is " + name);
        out.println("qName is " + qName);
        out.println("Accumulated text:");
        out.println(characterData.toString());
        out.println("----------------------");
        // if (qName.equalsIgnoreCase("word")) {
        if (name.equalsIgnoreCase("word")) {
            Word w = new Word(characterData.toString());
            words.add(w);
        } else {
        }
    }

    public void warning(SAXParseException exception) {
        System.err.println("WARNING: line " + exception.getLineNumber() + ": "
            + exception.getMessage());
    }

    public void error(SAXParseException exception) {
        System.err.println("ERROR: line " + exception.getLineNumber() + ": "
            + exception.getMessage());
    }

    // non-recoverable error
    public void fatalError(SAXParseException exception) throws SAXException {
        System.err.println("FATAL: line " + exception.getLineNumber() + ": "
          + exception.getMessage());
        throw (exception);
    }

}

public class SaxWord {
    static List<Word> words;
    public static void main(String[] args) throws Exception {
        XMLReader xr = XMLReaderFactory.createXMLReader();
        words = new ArrayList<Word>();
        SaxWordHandler handler = new SaxWordHandler(words);
        xr.setContentHandler(handler);
        xr.setErrorHandler(handler);

        if (args.length != 1) {
            out.println("Usage: java SaxWord xml-file-to-process");
            System.exit(1);
        }

        FileReader r = new FileReader(args[0]);
        xr.parse(new InputSource(r));

        for (Word w : words) {
            out.println("Word: |" + w + "|");
        }
    }
}
