package Core;

/**
 * Created by AdrianM on 8/27/15.
 */

/**
 * This class contains messages to be output by the program. No String constants end with a newline character.
 */
public class Strings {

    public static String NEWLINE = System.lineSeparator();
    public static String DOUBLE_NEWLINE = System.lineSeparator() + System.lineSeparator();

    public static String INFO = "Welcome to goto. This is a command line program that enables a user to " +
            "browse the web from the command line." + Strings.DOUBLE_NEWLINE +

            "Enter a command in the format 'goto <site name/URL>' or 'goto <flag> <...extra arguments...>'. " +
            "Entering a command in the format 'goto <site name/URL>' will open a new tab in your default browser, " +
            "and you will be automatically navigated the to the given site." + Strings.DOUBLE_NEWLINE +

            "You do not need to type in a fully qualified URL for the site name. You can simply type " +
            "in the site name, and a default prefix (http://www.) and suffix (.com) will automatically be added. " +
            "Any different prefixes or suffixes that you add yourself will override the default " +
            "prefixes and suffixes." + Strings.DOUBLE_NEWLINE +

            "For example, entering 'goto somesite', 'goto somesite.com', or 'goto www.somesite.com', etc. will " +
            "open up your default browser and navigate to 'http://www.somesite.com'." + Strings.DOUBLE_NEWLINE +

            "Similarly, entering 'goto somesite.net' will navigate your default browser to " +
            "'http://www.somesite.net', and entering 'goto https://www.somesite.net' will navigate " +
            "your default browser to 'https://www.somesite.net'." + Strings.DOUBLE_NEWLINE +

            "This program also has a bookmarking system, wherein you can register aliases to use in place of a " +
            "site name or URL. For example, if you have registered 'ggl' as an alias for the " +
            "URL 'http://www.google.com', you can simply type in 'goto ggl' and the browser will navigate " +
            "to http://www.google.com. Here, alias-URL pairs are referred to as bookmarks." + Strings.DOUBLE_NEWLINE +

            "The saved bookmarks are stored and recorded in a file in your home directory." + Strings.DOUBLE_NEWLINE +

            "You are able to create new bookmarks, and view or delete existing ones " +
            "through the program flags. These flags are listed below, along with their " +
            "descriptions and how to use them." + Strings.DOUBLE_NEWLINE +

            "NOTE: If any of your arguments contain a special character (such as an ampersand), you must surround " +
            "the special character (or the whole argument) in single quotes so that the program is able to process " +
            "the character as intended. For example, if your original entry is |goto google.com/exa&mple|, you " +
            "must instead input |goto 'google.com/exa&mple'| (vertical bars are used here for clarity " +
            "and should not be to be used in the actual command). This is unavoidable due to the nature of the " +
            "command line, and must be done for the program to work as intended." + Strings.DOUBLE_NEWLINE +

            "The usage of this program is outlined as follows:" + Strings.DOUBLE_NEWLINE +

            "No flags:" + Strings.NEWLINE +
            "Usage: goto <site name/URL>" + Strings.NEWLINE +
            "Description: Opens a new tab in your default browser at the given site. You do not need to fully " +
            "qualify the URL, as the program will format your entry for you and add a default prefix (http://www.)" +
            "and suffix (.com) to the site name if you have not provided your own. For example, 'goto google'" +
            "becomes 'goto http://www.google.com'." + Strings.DOUBLE_NEWLINE +

            Main.BOOKMARK_FLAG + ": " + Strings.NEWLINE +
            "Usage: goto " + Main.BOOKMARK_FLAG + " <alias> <URL>" + Strings.NEWLINE +
            "Description: Saves an alias-URL pair to a bookmark file in your home directory. If the alias given " +
            "already exists, the old URL is overwritten with the new one given. Program flags cannot be registered " +
            "as aliases." + Strings.DOUBLE_NEWLINE +

            Main.DELETE_BOOKMARK_FLAG + ": " + Strings.NEWLINE +
            "Usage: goto " + Main.DELETE_BOOKMARK_FLAG + " <alias>" + Strings.NEWLINE +
            "Description: Deletes an existing, currently saved alias. If the alias given does not exist, " +
            "a message will be output saying that the alias does not exist." + Strings.DOUBLE_NEWLINE +

            Main.VIEW_BOOKMARKS_FLAG + ": " + Strings.NEWLINE +
            "Usage: goto " + Main.VIEW_BOOKMARKS_FLAG + Strings.NEWLINE +
            "Description: Display all currently saved bookmarks." + Strings.DOUBLE_NEWLINE +

            Main.HELP_FLAG + ": " + Strings.NEWLINE +
            "Usage: goto " + Main.HELP_FLAG +  Strings.NEWLINE +
            "Description: Get help with goto (see this information again).";

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
}
