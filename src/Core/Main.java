package Core;

import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

import Utils.ArgUtils;

/**
 * Created by AdrianM on 6/18/15.
 */

/**
 * This class and enclosed main method control the main flow of the program.
 *
 * It is assumed that every element in the args array is an individual argument, rather than there being only one item
 * in the args array containing a single String of all the arguments.
 *
 * If invalid arguments (or no arguments) are given, a help message is output and the program ends.
 *
 * If the first argument is a flag, an action corresponding to that flag is executed. Otherwise, the argument is first
 * checked for being an alias for a URL. If it is, the browser navigates to that URL. If not, the browser will navigate
 * to the URL returned after formatting the first argument.
 *
 * An alias can be registered to be used in place of a URL via the bookmark flag.
 *
 * All alias and URL pairs are stored in a text file in the user's home directory. Before any actions are taken,
 * it is ensured that the file exists and that it is readable by this program.
 *
 * All constants are also kept in this class.
 */
public class Main {

//    TODO: Update Help messages
//    TODO: Add newline character to end of all writeStringToFile statements
//    TODO: Decide on ambiguous test cases in ArgUtils
//    TODO: Replace flag strings in tests with Main constants
//    TODO: Replace all newlines in program with generic System.getLineSeparator()
//    TODO: Ensure newline after new bookmark is placed in file
//    TODO: All returned strings do not end with a newline character mention somewhere

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

    // Debug
    public static boolean DEBUG = false;
    public static String OPEN_BROWSER_AT_MESSAGE = "Open browser at ";


    public static void main(String[] args) throws URISyntaxException, IOException {

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
                    System.out.println();
                    System.out.println(Help.getHelp());
                    System.out.println("\n");
                    break;
                case BOOKMARK_FLAG:
                    alias = args[1];
                    url = args[2]; // format URL? yes TODO
                    Bookmark.bookmark(bookmarkFile, alias, url);
                    break;
                case DELETE_BOOKMARK_FLAG:
                    alias = args[1];
                    Bookmark.deleteBookmark(bookmarkFile, alias);
                    break;
                case VIEW_BOOKMARKS_FLAG:
                    System.out.println();
                    System.out.println(Bookmark.getBookmarks(bookmarkFile));
                    System.out.println("\n");
                    break;
                default:
                    url = Bookmark.getURLFromAlias(bookmarkFile, firstArg);
                    if (url == null) {
                        url = ArgUtils.formatURL(firstArg);
                    }
                    if (DEBUG) {
                        System.out.println(OPEN_BROWSER_AT_MESSAGE + url);
                    } else {
                        Desktop.getDesktop().browse(new URI(url));
                    }
            }
        } else {
            System.out.println(Help.getInvalidArgMessage());
        }
    }
}
