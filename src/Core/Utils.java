package Core;

/**
 * Created by AdrianM on 6/20/15.
 */
public class Utils {

    // only needs to ensure that the url begins with http:// or https://
    public static String formatURL(String url) {
        return null;
    }

    public static boolean validateFlags(String[] flags) {
        for (String flag : flags) {
            if (!Main.VALID_FLAGS.contains(flag)) {
                return false;
            }
        }
        return true;
    }

}
