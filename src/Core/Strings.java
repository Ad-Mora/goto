package Core;

/**
 * Created by AdrianM on 8/27/15.
 */

/**
 * This class contains messages to be output by the program. No Strings constants end with a newline character.
 */
public class Strings {

    public static String INFO = "Welcome to goto. This is a command line program that enables a user to " +
            "browse the web from the command line." + Strings.DOUBLE_NEWLINE +

            "Enter a command in the format 'goto <site name> <flags>'. This will open your Chrome browser in a new" +
            "window, and will automatically navigate the browser to the given site." + Strings.DOUBLE_NEWLINE +

            "You do not need to type in a fully qualified URL. You can simply type in the site name, and " +
            "a default prefix (http://www.) and suffix (.com) will automatically be added." + Strings.DOUBLE_NEWLINE +

            "For example, entering 'goto google' will open up the Chrome browser and navigate to " +
            "'http://www.google.com'." + Strings.DOUBLE_NEWLINE +

            "Possible optional flags are as follows:" + Strings.DOUBLE_NEWLINE +

            Main.HELP_FLAG + ": Get help with goto (see this information again)";

    public static String INVALID_ARGS = "Invalid argument(s). Enter 'goto " + Main.HELP_FLAG + "' for help.";
    public static String ALIAS_DOES_NOT_EXIST = "Alias does not exist";
    public static String NO_BOOKMARKS_SAVED = "There are no bookmarks currently saved";
    public static String ERROR_WRITING_TO_FILE = "Error writing to file";
    public static String ERROR_CREATING_BOOKMARK_FILE = "Error creating bookmark file";
    public static String FILE_NOT_FOUND = "File not found";
    public static String ERROR_READING_FROM_FILE = "Error reading from file";
    public static String OPEN_BROWSER_AT = "Open browser at ";
    public static String ERROR_OPENING_BROWSER = "Error opening browser";
    public static String INVALID_URI_SYNTAX = "Invalid URI syntax";
    public static String NEWLINE = System.lineSeparator();
    public static String DOUBLE_NEWLINE = System.lineSeparator() + System.lineSeparator();
}
