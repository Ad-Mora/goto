package Core;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by AdrianM on 7/16/15.
 */
public class Bookmark {

    public static void bookmark(String alias, String url) {

        String folderPath = Main.CONFIG_LOCATION + "/" + Main.GOTO_CONFIG_FOLDER_NAME;
        String filePath = folderPath + "/" + Main.CONFIG_FILE_NAME;
        File folder = new File(folderPath);
        File file = new File(filePath);

        folder.mkdirs();
        try {
            file.createNewFile();
        } catch(IOException ex) {
            System.out.println("Error creating file");
        }
        Bookmark.createOrUpdateBookmark(file, alias, url);
    }

    public static void createOrUpdateBookmark(File file, String alias, String url) {

        Map<String, String> aliasesToURLs = getFileData(file);

        if (aliasesToURLs.containsKey(alias)) {
            updateBookmark(file, aliasesToURLs);
        } else {
            createBookmark(file, alias, url);
        }
    }

    public static Map<String, String> getFileData(File file) {
        Map<String, String> aliasesToURLs = new HashMap<String, String>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
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

    public static void updateBookmark(File configFile, Map<String, String> aliasesToURLs) {
        try {
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(configFile, false)));
            String url;

            for (String alias : aliasesToURLs.keySet()) {
                url = aliasesToURLs.get(alias);
                writer.println(alias + " " + url);
            }
            writer.close();

        } catch(FileNotFoundException ex) {
            System.out.println("File not found");
        } catch(IOException ex) {
            System.out.println("Error writing to file");
        }
    }

    public static void createBookmark(File configFile, String alias, String url) {
        try {
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(configFile, true)));
            writer.println(alias + " " + url);
            writer.close();
        } catch(FileNotFoundException ex) {
            System.out.println("File not found");
        } catch(IOException ex) {
            System.out.println("Error writing to file");
        }
    }

    public static String getURLFromAlias(String alias) {
        Map<String, String> aliasesToURLs = getFileData(new File(Main.CONFIG_FILE_PATH));
        return aliasesToURLs.get(alias);
    }






}
