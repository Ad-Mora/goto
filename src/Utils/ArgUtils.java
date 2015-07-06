package Utils;

import Core.Main;

import java.util.Set;

/**
 * Created by AdrianM on 6/28/15.
 */

/**
 * This class provides helper functions to parse in and validate the arguments given by the user.
 */
public class ArgUtils {

    /**
     * Takes in the URL that the user passes in, and returns a properly formatted URL. The URL passed in is
     * not an empty String.
     *
     * Passing in just a website name will result in default prefixes
     * and suffixes being added. For example, if the website name passed in is 'google', the String output
     * will be 'http://www.google.com'.
     *
     * If a URL is passed in that contains its own prefixes and/or suffixes, those will be used instead of the
     * defaults (or, if they match the defaults, they will be left unchanged).
     *
     * If a URL is passed in that does not have a proper suffix, but ends in a dot, the URL suffix will be
     * made to be the default .com suffix.
     *
     * @param url The url that the user passes in, not necessarily formatted. Not an empty String.
     * @return A fully qualified URL, with an http:// or https:// prefix, and a dot something suffix.
     */
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

    /**
     * This function takes in the flag arguments passed in, and determines whether or not any of them are invalid
     * If any flag in the flags set is invalid, this function will return false. Otherwise, this function will
     * return true.
     *
     * @param flags The flags passed in by the user. All arguments after the URL argument are treated as flags.
     * @return True if all flags in the flags array are valid, false otherwise.
     */
    public static boolean validateFlags(Set<String> flags) {
        for (String flag : flags) {
            if (!Main.VALID_FLAGS.contains(flag)) {
                return false;
            }
        }
        return true;
    }
}
