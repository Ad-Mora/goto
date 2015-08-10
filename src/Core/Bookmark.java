package Core;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by AdrianM on 7/16/15.
 */

/**
 * This class provides static methods to handle all interactions with the alias-URL pairs in the bookmark file. Here,
 * the alias-URL pairs will be referred to as bookmarks.
 *
 * Methods are provided for adding to, deleting from, reading from, creating, and cleaning the bookmark file.
 *
 * The bookmark file is located as follows: home directory/general project directory/goto directory/bookmark file
 *
 * All URLs in the bookmark file are fully qualified. No alias in the bookmark file matches any of the program flags.
 *
 * Bookmarks are written to the bookmark file in the following format: the alias, followed by a space, followed
 * by the URL, with no leading or trailing spaces. All bookmark entries are each on a separate line,
 * with no blank lines in between entries.
 *
 * The aliases and URLs are written to the bookmark file in this specific manner so that data may be easily read from the
 * file. Cleaning the file ensures that this format is preserved.
 */
public class Bookmark {

    /**
     * Adds a new bookmark to the bookmark file. If the alias given already exists, the URL
     * corresponding to that alias is updated to match the new URL. Otherwise, a new bookmark
     * is added to the file.
     *
     * @param bookmarkFile the file containing the bookmarks. Must exist, and be properly formatted
     * @param alias the String to be used in place of a URL argument
     * @param url a formatted URL that will be referenced in place of the alias
     */
    public static void bookmark(File bookmarkFile, String alias, String url) {
        Map<String, String> aliasesToURLs = getBookmarkFileData(bookmarkFile);

        if (aliasesToURLs.containsKey(alias)) {
            aliasesToURLs.put(alias, url);
            updateBookmarkFile(bookmarkFile, aliasesToURLs);
        } else {
            try {
                String entry = getLineEntry(alias, url);
                FileUtils.writeStringToFile(bookmarkFile, entry, true);
            } catch (IOException e) {
                System.out.println("Error writing to file");
            }
        }
    }

    /**
     * Deletes a bookmark from the bookmark file. Does not leave a blank line in the file, but instead rearranges all
     * other pairs so that there are no bookmarks separated by a blank line.
     *
     * @param bookmarkFile the file containing the bookmarks. Must exist, and be properly formatted
     * @param alias the alias specifying the bookmark to be deleted
     */
    public static void deleteBookmark(File bookmarkFile, String alias) {
        Map<String, String> aliasesToURLs = getBookmarkFileData(bookmarkFile);
        if (aliasesToURLs.containsKey(alias)) {
            aliasesToURLs.remove(alias);
            updateBookmarkFile(bookmarkFile, aliasesToURLs);
        } else {
            System.out.println("Alias does not exist");
        }
    }

    /**
     * Prints out all bookmarks from the bookmark file.
     *
     * @param bookmarkFile the file containing the bookmarks. Must exist, and be properly formatted
     */
    public static void viewBookmarks(File bookmarkFile) {
        Map<String, String> aliasesToURLs = getBookmarkFileData(bookmarkFile);
        for (String alias : aliasesToURLs.keySet()) {
            String url = aliasesToURLs.get(alias);
            System.out.println(alias + ": " + url);
        }
    }

    /**
     * Ensures that the file exists. If the file already exists, nothing is changed. Otherwise,
     * the file is created at the path given by the file.
     *
     * @param bookmarkFile the file to be created
     */
    public static void createBookmarkFile(File bookmarkFile) {

        File gotoBookmarkFolder = bookmarkFile.getParentFile().getAbsoluteFile();
        gotoBookmarkFolder.mkdirs();

        try {
            bookmarkFile.createNewFile();
        } catch(IOException ex) {
            System.out.println("Error creating file");
        }
    }

    /**
     * Ensures that the bookmark file is formatted properly so that it can be handled from without conflict.
     * This is meant to guard against a user manually editing the file themselves.
     *
     * If a line in the file is found to be improperly formatted, everything on that line is deleted. URLs are
     * also checked for being fully qualified. If a URL is not fully qualified, the entire bookmark is deleted.
     * When performing deletions, no blank lines will be left between correctly formatted bookmarks.
     *
     * @param bookmarkFile the file containing the bookmarks. Must exist
     */
    public static void cleanBookmarkFile(File bookmarkFile) {
    }

    /**
     * Returns a mapping of existing aliases to the URLs they represent.
     *
     * @param bookmarkFile the file containing the bookmarks. Must exist, and be properly formatted
     * @return a map that maps existing aliases to the URLs they represent
     */
    public static Map<String, String> getBookmarkFileData(File bookmarkFile) {
        Map<String, String> aliasesToURLs = new HashMap<String, String>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(bookmarkFile));
            String line = null;

            while ((line = reader.readLine()) != null) {
                String[] fileLine = line.split(" ");
                String fileAlias = fileLine[0];
                String fileURL = fileLine[1];
                aliasesToURLs.put(fileAlias, fileURL);
            }
            reader.close();
        } catch(FileNotFoundException ex) {
            System.out.println("File not found");
        } catch(IOException ex) {
            System.out.println("Error reading file");
        }
        return aliasesToURLs;
    }

    /**
     * Overwrites the current bookmark file alias to URL pairs with the alias to URL pairs contained in the
     * given alias to URL map.
     *
     * @param bookmarkFile the file to be overwritten by the new bookmarks. Must exist
     * @param aliasesToURLs the new mapping of alias to URLs. This mapping overwrites the existing one
     */
    public static void updateBookmarkFile(File bookmarkFile, Map<String, String> aliasesToURLs) {
        try {
            String url;
            String entry;
            for (String alias : aliasesToURLs.keySet()) {
                url = aliasesToURLs.get(alias);
                entry = getLineEntry(alias, url);
                FileUtils.writeStringToFile(bookmarkFile, entry, true);
            }
        } catch(FileNotFoundException ex) {
            System.out.println("File not found");
        } catch(IOException ex) {
            System.out.println("Error writing to file");
        }
    }

    /**
     * Retrieves the URL from the bookmark file that corresponds to the given alias. If the alias does not exist,
     * null is returned.
     *
     * @param bookmarkFile the file containing the bookmarks. Must exist, and be properly formatted
     * @param alias a String specifying which URL to retrieve
     * @return the URL that corresponds to the given alias
     */
    public static String getURLFromAlias(File bookmarkFile, String alias) {
        Map<String, String> aliasesToURLs = getBookmarkFileData(bookmarkFile);
        return aliasesToURLs.get(alias);
    }

    /**
     * Returns a properly formatted single bookmark entry. Made for consistency purposes.
     *
     * @param alias the alias half of the bookmark
     * @param url the URL half of the bookmark
     * @return a properly formatted single bookmark entry
     */
    public static String getLineEntry(String alias, String url) {
        return alias + " " + url + System.getProperty("line.separator");
    }
}
