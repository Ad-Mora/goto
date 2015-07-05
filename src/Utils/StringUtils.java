package Utils;

import Core.Main;

import java.util.Set;

/**
 * Created by AdrianM on 6/28/15.
 */
public class StringUtils {

    // needs to ensure that the url begins with http:// or https://
    // adds a .com to the end of the url if a suffix is not specified
    public static String formatURL(String url) {
        
        String tempURL = url;
        String prefix = "http://";
        String suffix = "";

        if (tempURL.startsWith("http://") || tempURL.startsWith("https://")) {
            String[] tempArr = tempURL.split("://");
            prefix = tempArr[0] + "://";
            tempURL = tempArr[1];
        }

        if (tempURL.startsWith("www.")) {
            tempURL = tempURL.split("www.")[1];
        }
        prefix += "www.";

        if (!tempURL.contains(".")) {
            suffix = ".com";
        } else if (tempURL.endsWith(".")) {
            suffix = "com";
        }

        return prefix + tempURL + suffix;
    }

    public static boolean validateFlags(Set<String> flags) {
        for (String flag : flags) {
            if (!Main.VALID_FLAGS.contains(flag)) {
                return false;
            }
        }
        return true;
    }

}
