package socialhour.socialhour.tools;

/**
 * Created by michael on 5/25/17.
 */

public class FirebaseData {
    public static String FirebaseEncodeEmail(String string) {
        return string.replace(".", ",");
    }
    public static String FirebaseDecodeEmail(String string) {
        return string.replace(",", ".");
    }
}
