package Core;

import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.List;

import Utils.ArgUtils;
import com.sun.org.apache.xpath.internal.Arg;

/**
 * Created by AdrianM on 6/18/15.
 */

/**
 * This class and enclosed main method control the main flow of the program, and contain the overall architecture.
 * The WebDriver is launched from here. Arguments are also passed in through here, and are parsed using helper classes.
 *
 * It is assumed that every element in the args array is an individual argument, rather than there being only one item
 * in the args array containing a single String of all the arguments.
 *
 * The very first argument passed in is treated as the URL argument, and every subsequent space separated argument is
 * treated as a flag.
 *
 * The browser that is opened is configured accordingly with the arguments passed in. The passing in of one or more
 * invalid arguments disallows the launching of the browser, and an invalid input message is output.
 * The optional flags are also set here, as constants.
 */
public class Main {

    // Config file location
    public static final String SETTINGS_LOCATION = "~";
    public static final String FOLDER_NAME = ".config";
    public static final String FILE_NAME = "gotoconfig";
    public static final String FILE_PATH = SETTINGS_LOCATION + "/" + FOLDER_NAME + "/" + FILE_NAME;

    // Flags
    public static String HELP_FLAG = "--help";
    public static String BOOKMARK_FLAG = "--bookmark";
    public static String DELETE_BOOKMARK_FLAG = "--delete-bookmark";
    public static String VIEW_BOOKMARKS_FLAG = "--view-bookmarks";
    public static Set<String> VALID_FLAGS = new HashSet<String>(Arrays.asList(
            HELP_FLAG, BOOKMARK_FLAG, DELETE_BOOKMARK_FLAG, VIEW_BOOKMARKS_FLAG));

    public static void main(String[] args) throws URISyntaxException, IOException {

        String url;
        String unformattedURL;
        String bookmarkAlias;
        List<String> flags;

        if (args.length > 0) {

            unformattedURL = args[0];
            url = Bookmark.getURLFromAlias(unformattedURL);
            if (url == null) {
                url = ArgUtils.formatURL(unformattedURL);
            }

            String[] flagsArr = Arrays.copyOfRange(args, 1, args.length);
            flags = Arrays.asList(flagsArr);

            if (url.equals(ArgUtils.formatURL(HELP_FLAG))) {

            } else if (url.equals(ArgUtils.formatURL(VIEW_BOOKMARKS_FLAG))) {

            } else if (url.equals(ArgUtils.formatURL(DELETE_BOOKMARK_FLAG))) {

            } else {

                // Consider making url a 'flag'
                // Cycle through the flags
                for (int i = 0; i < flags.size(); i++) {
                    if (flags.get(i).equals(BOOKMARK_FLAG)) {
                        if (i < flags.size()) {
                            bookmarkAlias = flags.get(i+1);
                            Bookmark.bookmark(bookmarkAlias, url);
                        } else {
                            System.out.println(Help.getNoBookmarkAliasMessage());
                        }
                    }
                }

            }


            // Open up the default browser at the given location
            Desktop.getDesktop().browse(new URI(url));

        } else {
            System.out.println(Help.getInvalidArgMessage());
        }
    }
}
