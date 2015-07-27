package Core;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by AdrianM on 7/16/15.
 */
public class Bookmark {

    public static void bookmark(File configFile, String alias, String url) {

        File gotoConfigFolder = configFile.getParentFile().getAbsoluteFile();
        gotoConfigFolder.mkdirs();

        try {
            configFile.createNewFile();
        } catch(IOException ex) {
            System.out.println("Error creating file");
        }
        Bookmark.cleanFile(configFile);
        Bookmark.createOrUpdateBookmark(configFile, alias, url);
    }

    public static void cleanFile(File configFile) {

    }


    public static void createOrUpdateBookmark(File configFile, String alias, String url) {

        Map<String, String> aliasesToURLs = getConfigFileData(configFile);

        if (aliasesToURLs.containsKey(alias)) {
            if (url.equals("")) {
                aliasesToURLs.remove(alias);
            } else {
                aliasesToURLs.put(alias, url);
            }
            updateBookmark(configFile, aliasesToURLs);
        } else {
            try {
                FileUtils.writeStringToFile(configFile, alias + " " + url, true);
            } catch (IOException e) {
                System.out.println("Error writing to file");
            }
        }
    }

    public static Map<String, String> getConfigFileData(File configFile) {
        Map<String, String> aliasesToURLs = new HashMap<String, String>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(configFile));
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

    // overwrites current config file with modfied dictionary (aliasesToURLs)
    // assumes alias exists
    public static void updateBookmark(File configFile, Map<String, String> aliasesToURLs) {
        try {
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(configFile, false)));
            String url;

            for (String alias : aliasesToURLs.keySet()) {
                url = aliasesToURLs.get(alias);
//                FileUtils.writeStringToFile(configFile, alias + " " + url, true);
                writer.println(alias + " " + url);
            }
            writer.close();

        } catch(FileNotFoundException ex) {
            System.out.println("File not found");
        } catch(IOException ex) {
            System.out.println("Error writing to file");
        }
    }


    public static String getURLFromAlias(File configFile, String alias) {
        Map<String, String> aliasesToURLs = getConfigFileData(configFile);
        return aliasesToURLs.get(alias);
    }






}
