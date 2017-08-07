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
