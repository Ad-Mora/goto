package UtilsTests;

import Utils.ArgUtils;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by AdrianM on 6/28/15.
 */

/*
 * Testing outline:
 *
 * ##################################################
 * public static String formatURL(String url)
 *
 * url:
 * - Name, no prefixes or suffixes
 * - Name and http prefix
 * - Name and https prefix
 * - Name and www prefix
 * - Name and http and www prefix
 * - Name and https and www prefix
 * - Name and .com suffix
 * - Name and non .com suffix
 * - Name and suffix with two dots
 * - Name, http prefix, and .com suffix
 * - Name, https prefix, and .com suffix
 * - Name, www prefix, and .com suffix
 * - Name, http and www prefix, and .com suffix
 * - Name, https and www prefix, and .com suffix
 * - Name, and dot at end
 *
 * ##################################################
 *
 * public static boolean validateArgs(String[] args)
 *
 *
 */
public class ArgUtilsTests {

    // ####################################################################################################
    // formatURL(String url) tests

    @Test
    public void testFormatURLNoPrefixOrSuffix() {
        String name = "google";
        String prefix = "";
        String suffix = "";
        String url = prefix + name + suffix;
        String formattedURL = ArgUtils.formatURL(url);
        String desiredOutput = "http://www.google.com";

        assertTrue(formattedURL.equals(desiredOutput));
    }
    @Test
    public void testFormatURLHttpPrefix() {
        String name = "google";
        String prefix = "http://";
        String suffix = "";
        String url = prefix + name + suffix;
        String formattedURL = ArgUtils.formatURL(url);
        String desiredOutput = "http://www.google.com";

        assertTrue(formattedURL.equals(desiredOutput));
    }

    @Test
    public void testFormatURLHttpsPrefix() {
        String name = "google";
        String prefix = "https://";
        String suffix = "";
        String url = prefix + name + suffix;
        String formattedURL = ArgUtils.formatURL(url);
        String desiredOutput = "https://www.google.com";

        assertTrue(formattedURL.equals(desiredOutput));
    }

    @Test
    public void testFormatURLWWWPrefix() {
        String name = "google";
        String prefix = "www.";
        String suffix = "";
        String url = prefix + name + suffix;
        String formattedURL = ArgUtils.formatURL(url);
        String desiredOutput = "http://www.google.com";

        assertTrue(formattedURL.equals(desiredOutput));
    }

    @Test
    public void testFormatURLHttpWWWPrefix() {
        String name = "google";
        String prefix = "http://www.";
        String suffix = "";
        String url = prefix + name + suffix;
        String formattedURL = ArgUtils.formatURL(url);
        String desiredOutput = "http://www.google.com";

        assertTrue(formattedURL.equals(desiredOutput));
    }

    @Test
    public void testFormatURLHttpsWWWPrefix() {
        String name = "google";
        String prefix = "https://www.";
        String suffix = "";
        String url = prefix + name + suffix;
        String formattedURL = ArgUtils.formatURL(url);
        String desiredOutput = "https://www.google.com";

        assertTrue(formattedURL.equals(desiredOutput));
    }

    @Test
    public void testFormatURLComSuffix() {
        String name = "google";
        String prefix = "";
        String suffix = ".com";
        String url = prefix + name + suffix;
        String formattedURL = ArgUtils.formatURL(url);
        String desiredOutput = "http://www.google.com";

        assertTrue(formattedURL.equals(desiredOutput));
    }

    @Test
    public void testFormatURLNonComSuffix() {
        String name = "google";
        String prefix = "";
        String suffix = ".org";
        String url = prefix + name + suffix;
        String formattedURL = ArgUtils.formatURL(url);
        String desiredOutput = "http://www.google.org";

        assertTrue(formattedURL.equals(desiredOutput));
    }

    @Test
    public void testFormatURLDotAtEnd() {
        String name = "google";
        String prefix = "";
        String suffix = ".";
        String url = prefix + name + suffix;
        String formattedURL = ArgUtils.formatURL(url);
        String desiredOutput = "http://www.google.com";

        assertTrue(formattedURL.equals(desiredOutput));
    }

    @Test
    public void testFormatURLTwoDotSuffix() {
        String name = "google";
        String prefix = "";
        String suffix = ".co.uk";
        String url = prefix + name + suffix;
        String formattedURL = ArgUtils.formatURL(url);
        String desiredOutput = "http://www.google.co.uk";

        assertTrue(formattedURL.equals(desiredOutput));
    }

    @Test
    public void testFormatURLHttpPrefixAndComSuffix() {
        String name = "google";
        String prefix = "http://";
        String suffix = ".com";
        String url = prefix + name + suffix;
        String formattedURL = ArgUtils.formatURL(url);
        String desiredOutput = "http://www.google.com";

        assertTrue(formattedURL.equals(desiredOutput));
    }

    @Test
    public void testFormatURLHttpsPrefixAndComSuffix() {
        String name = "google";
        String prefix = "https://";
        String suffix = ".com";
        String url = prefix + name + suffix;
        String formattedURL = ArgUtils.formatURL(url);
        String desiredOutput = "https://www.google.com";

        assertTrue(formattedURL.equals(desiredOutput));
    }

    @Test
    public void testFormatURLWWWPrefixAndComSuffix() {
        String name = "google";
        String prefix = "www.";
        String suffix = ".com";
        String url = prefix + name + suffix;
        String formattedURL = ArgUtils.formatURL(url);
        String desiredOutput = "http://www.google.com";

        assertTrue(formattedURL.equals(desiredOutput));
    }

    @Test
    public void testFormatURLHttpWWWPrefixAndComSuffix() {
        String name = "google";
        String prefix = "http://www.";
        String suffix = ".com";
        String url = prefix + name + suffix;
        String formattedURL = ArgUtils.formatURL(url);
        String desiredOutput = "http://www.google.com";

        assertTrue(formattedURL.equals(desiredOutput));
    }

    @Test
    public void testFormatURLHttpsWWWPrefixAndComSuffix() {
        String name = "google";
        String prefix = "https://www.";
        String suffix = ".com";
        String url = prefix + name + suffix;
        String formattedURL = ArgUtils.formatURL(url);
        String desiredOutput = "https://www.google.com";

        assertTrue(formattedURL.equals(desiredOutput));
    }

    // ####################################################################################################
    // validateArgs(String[] args) tests


}
