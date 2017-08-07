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
