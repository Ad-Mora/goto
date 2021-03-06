package Utils;

import Core.Main;
import Core.Strings;

/*
 * Created by Adrian Mora on 6/28/15.
 */

/**
 * This class provides functions to parse in and process the arguments given by the user.
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
     * @param url The URL that the user passes in, not necessarily formatted. Not an empty String.
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
     * Checks the validity of all passed in arguments. Returns false if any of the arguments contain
     * a newline character, if there are an invalid number of arguments, or if the array of arguments as a
     * whole form an invalid command.
     *
     * For example, the input "goto <bookmark flag> <alias> <URL> extraArgument" is an invalid command,
     * because the bookmark flag only requires an alias and a URL to follow the bookmark flag, and an
     * extra argument was given.
     *
     * @param args the arguments passed in directly by the user
     * @return false if any individual argument is invalid, or if the array of arguments as a whole form an
     * invalid command
     */
    public static boolean validateArgs(String[] args) {
        String firstArg;
        String alias;
        String url;

        if (args.length == 0) {
            return false;
        }
        firstArg = args[0];
        switch (firstArg) {
            case Main.BOOKMARK_FLAG:
                if (args.length != 3) {
                    return false;
                }
                alias = args[1];
                url = args[2];
                if (alias.contains(Strings.NEWLINE) ||
                        url.contains(Strings.NEWLINE) ||
                        Main.VALID_FLAGS.contains(alias)) {
                    return false;
                }
                break;
            case Main.DELETE_BOOKMARK_FLAG:
                if (args.length != 2) {
                    return false;
                }
                alias = args[1];
                if (alias.contains(Strings.NEWLINE)) {
                    return false;
                }
                break;
            case Main.VIEW_BOOKMARKS_FLAG:
                if (args.length != 1) {
                    return false;
                }
                break;
            case Main.HELP_FLAG:
                if (args.length != 1) {
                    return false;
                }
                break;
            default:
                if (args.length != 1 || firstArg.contains(Strings.NEWLINE)) {
                    return false;
                }
                break;
        }
        return true;
    }
}
