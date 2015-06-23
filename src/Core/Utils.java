package Core;

/**
 * Created by AdrianM on 6/20/15.
 */
public class Utils {

    // needs to ensure that the url begins with http:// or https://
    // adds a .com to the end of the url if a suffix is not specified
    public static String formatURL(String url) {

        String tempURL = url;
        String prefix = "";
        String suffix = "";

        if (tempURL.startsWith("http://") || tempURL.startsWith("https://")) {
            String[] tempArr = tempURL.split("://", 1);
            prefix += tempArr[0] + "www.";
            tempURL = tempArr[1];
        } else {
            prefix += "http://www.";
        }

        if (tempURL.startsWith("www.")) {
            tempURL = tempURL.split("www.", 1)[1];
        }

        if (!tempURL.contains(".")) {
            suffix = ".com";
        }

        return prefix + tempURL + suffix;

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
