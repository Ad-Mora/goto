package Core;

import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.List;

import Utils.ArgUtils;
import com.sun.org.apache.xpath.internal.Arg;
import org.apache.commons.io.FileUtils;

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
 * checked for being an alias for a url. If it is, the browser navigates to that url. If not, the browser will navigate
 * to the url returned after formatting the first argument.
 *
 * All alias and url pairs are stored in a text file in the user's home directory. Before any actions are taken,
 * it is ensured that the file exists and that it is readable by this program.
 *
 * All constants are also kept in this class.
 *
 */
public class Main {

    // Config file location
    public static final String HOME_PATH = System.getProperty("user.home");
    public static final String GOTO_CONFIG_FOLDER_PATH = HOME_PATH + File.separator + ".config/gotoconfig";
    public static final String CONFIG_FILE_PATH = GOTO_CONFIG_FOLDER_PATH + File.separator + "config";

    // Flags
    public static final String HELP_FLAG = "--help";
    public static final String BOOKMARK_FLAG = "--bookmark";
    public static final String DELETE_BOOKMARK_FLAG = "--delete-bookmark";
    public static final String VIEW_BOOKMARKS_FLAG = "--view-bookmarks";

    public static void main(String[] args) throws URISyntaxException, IOException {

        // TODO: Check that given aliases are not flags
        // TODO: FileUtils.writeStringToFile appends with new line or no?
        // TODO: Finish specs
        // TODO: Bookmark tests
        // TODO: main tests
        // TODO: Update Help messages

        String firstArg;
        String alias;
        String url;

        File configFile = new File(CONFIG_FILE_PATH);

        Bookmark.createConfigFile(configFile);
        Bookmark.cleanConfigFile(configFile);

        if (args.length > 0) {
            firstArg = args[0];

            switch (firstArg) {
                case HELP_FLAG:
                    System.out.println(Help.getHelp());
                    break;
                case BOOKMARK_FLAG:
                    if (args.length == 3) {
                        alias = args[1];
                        url = args[2];
                        Bookmark.bookmark(configFile, alias, url);
                    } else {
                        System.out.println(Help.getInvalidArgMessage());
                    }
                    break;
                case DELETE_BOOKMARK_FLAG:
                    if (args.length == 2) {
                        alias = args[1];
                        Bookmark.deleteBookmark(configFile, alias);
                    } else {
                        System.out.println(Help.getInvalidArgMessage());
                    }
                    break;
                case VIEW_BOOKMARKS_FLAG:
                    break;
                default:
                    if (args.length == 1) {
                        url = Bookmark.getURLFromAlias(configFile, firstArg);
                        if (url == null) {
                            url = ArgUtils.formatURL(firstArg);
                        }
                        Desktop.getDesktop().browse(new URI(url));
                    } else {
                        System.out.println(Help.getInvalidArgMessage());
                    }
                    break;
            }
        } else {
            System.out.println(Help.getInvalidArgMessage());
        }
    }
}
