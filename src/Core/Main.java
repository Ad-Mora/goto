package Core;

import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

import Utils.ArgUtils;
import Utils.MessageUtils;

/**
 * Created by AdrianM on 6/18/15.
 */

/**
 * This class and enclosed main method control the main flow of the program.
 *
 * It is assumed that every element in the args array is an individual argument, rather than there being only one item
 * in the args array containing a single String of all the arguments.
 *
 * If invalid arguments (or no arguments) are given, a invalid arguments message is output.
 *
 * If the first argument is a flag, and it is followed by the appropriate arguments (this can mean no arguments
 * at all for some flags), an action corresponding to that flag is executed. Otherwise, the argument is first checked
 * for being an alias for a URL. If it is, the browser navigates to the URL the alias corresponds to. If not, the
 * browser will navigate to the URL returned after formatting the given URL.
 *
 * An alias can be registered to be used in place of a URL via the bookmark flag.
 *
 * All alias and URL pairs are stored in a text file in the user's home directory. Before any actions are taken,
 * it is ensured that the file exists and that it is readable by this program.
 *
 * Strings returned or obtained from either methods or constants do not end with a newline character in this program.
 * Surrounding newlines are added around the message manually.
 *
 * All non-message constants are kept in this class.
 */
public class Main {
//    TODO: One newline at end of all files
//    TODO: Check for warning (yellow) errors
//    TODO: Check for remaining TODOs
//    TODO: Run all tests final time

    // Bookmark file location
    public static final String HOME_PATH = System.getProperty("user.home");
    public static final String GENERAL_PROJECT_DIR_PATH = HOME_PATH + File.separator + ".butler";
    public static final String GENERAL_PROJECT_DIR_PATH_TEMP = Main.GENERAL_PROJECT_DIR_PATH + "Temp";
    public static final String GOTO_DIR_PATH = GENERAL_PROJECT_DIR_PATH + File.separator + "goto";
    public static final String BOOKMARK_FILE_PATH = GOTO_DIR_PATH + File.separator + "bookmarks";

    // Flags
    public static final String HELP_FLAG = "--help";
    public static final String BOOKMARK_FLAG = "--bookmark";
    public static final String DELETE_BOOKMARK_FLAG = "--delete-bookmark";
    public static final String VIEW_BOOKMARKS_FLAG = "--view-bookmarks";
    public static final Set<String> VALID_FLAGS =
            new HashSet<>(Arrays.asList(HELP_FLAG, BOOKMARK_FLAG, DELETE_BOOKMARK_FLAG, VIEW_BOOKMARKS_FLAG));

    // Debug
    public static boolean DEBUG = false;

    public static void main(String[] args) {

        String firstArg;
        String alias;
        String url;

        File bookmarkFile = new File(BOOKMARK_FILE_PATH);

        Bookmark.createBookmarkFile(bookmarkFile);
        Bookmark.cleanBookmarkFile(bookmarkFile);

        if (ArgUtils.validateArgs(args)) {
            firstArg = args[0];

            switch (firstArg) {
                case HELP_FLAG:
                    MessageUtils.surroundWithNewlinesAndPrint(Strings.INFO);
                    break;
                case BOOKMARK_FLAG:
                    alias = args[1];
                    url = ArgUtils.formatURL(args[2]);
                    Bookmark.bookmark(bookmarkFile, alias, url);
                    break;
                case DELETE_BOOKMARK_FLAG:
                    alias = args[1];
                    Bookmark.deleteBookmark(bookmarkFile, alias);
                    break;
                case VIEW_BOOKMARKS_FLAG:
                    String formattedBookmarks = Bookmark.getFormattedBookmarks(bookmarkFile);
                    MessageUtils.surroundWithNewlinesAndPrint(formattedBookmarks);
                    break;
                default:
                    url = Bookmark.getURLFromAlias(bookmarkFile, firstArg);
                    if (url == null) {
                        url = ArgUtils.formatURL(firstArg);
                    }
                    if (DEBUG) {
                        System.out.println(Strings.OPEN_BROWSER_AT + url);
                    } else {
                        try {
                            Desktop.getDesktop().browse(new URI(url));
                        } catch (IOException e) {
                            MessageUtils.surroundWithNewlinesAndPrint(Strings.ERROR_OPENING_BROWSER);
                        } catch (URISyntaxException e) {
                            MessageUtils.surroundWithNewlinesAndPrint(Strings.INVALID_URI_SYNTAX);
                        }
                    }
            }
        } else {
            MessageUtils.surroundWithNewlinesAndPrint(Strings.INVALID_ARGS);
        }
    }
}
