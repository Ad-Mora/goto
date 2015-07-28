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
        Map<String, String> aliasesToURLs = getConfigFileData(configFile);

        if (aliasesToURLs.containsKey(alias)) {
            aliasesToURLs.put(alias, url);
            updateConfigFile(configFile, aliasesToURLs);
        } else {
            try {
                String entry = getLineEntry(alias, url);
                FileUtils.writeStringToFile(configFile, entry, true);
            } catch (IOException e) {
                System.out.println("Error writing to file");
            }
        }
    }

    public static void deleteBookmark(File configFile, String alias) {
        Map<String, String> aliasesToURLs = getConfigFileData(configFile);
        if (aliasesToURLs.containsKey(alias)) {
            aliasesToURLs.remove(alias);
            updateConfigFile(configFile, aliasesToURLs);
        } else {
            System.out.println("Alias does not exist");
        }
    }

    public static void viewBookmarks(File configFile) {
    }

    public static void createConfigFile(File configFile) {

        File gotoConfigFolder = configFile.getParentFile().getAbsoluteFile();
        gotoConfigFolder.mkdirs();

        try {
            configFile.createNewFile();
        } catch(IOException ex) {
            System.out.println("Error creating file");
        }
    }

    public static void cleanConfigFile(File configFile) {
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
    public static void updateConfigFile(File configFile, Map<String, String> aliasesToURLs) {
        try {
            String url;
            String entry;
            for (String alias : aliasesToURLs.keySet()) {
                url = aliasesToURLs.get(alias);
                entry = getLineEntry(alias, url);
                FileUtils.writeStringToFile(configFile, entry, true);
            }
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

    public static String getLineEntry(String alias, String url) {
        return alias + " " + url;
    }

}
