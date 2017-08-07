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
