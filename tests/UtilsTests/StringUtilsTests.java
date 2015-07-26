package UtilsTests;

import Utils.ArgUtils;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertTrue;

/**
 * Created by AdrianM on 6/28/15.
 */

/*
 * Testing outline:
 *
 *  String formatURL(String url):
 *
 *  url:
 *  - Name, no prefixes or suffixes
 *  - Name and http prefix
 *  - Name and https prefix
 *  - Name and www prefix
 *  - Name and http and www prefix
 *  - Name and https and www prefix
 *  - Name and .com suffix
 *  - Name and non .com suffix
 *  - Name and suffix with two dots
 *  - Name, http prefix, and .com suffix
 *  - Name, https prefix, and .com suffix
 *  - Name, www prefix, and .com suffix
 *  - Name, http and www prefix, and .com suffix
 *  - Name, https and www prefix, and .com suffix
 *  - Name, and dot at end
 *
 *  boolean validateFlags(Set<String> flags):
 *
 *  flags:
 *  - Empty set
 *  - One valid flag (test different valid flags)
 *  - Multiple valid flags
 *  - One invalid flag
 *  - Multiple invalid flags
 *  - One valid flag, one invalid flag
 *  - One valid flag, multiple invalid flags
 *  - Multiple valid flags, one invalid flag
 *  - Multiple valid flags, multiple invalid flags
 *
 *
 */

public class StringUtilsTests {



    // formatURL tests

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

    // validateFlags tests

    @Test
    public void testValidateFlagsEmptySet() {
        Set<String> flags = new HashSet<String>();
        boolean desiredOutput = true;

        assertTrue(ArgUtils.validateFlags(flags) == desiredOutput);
    }

    @Test
    public void testValidateFlagsOneValidHelp() {
        Set<String> flags = new HashSet<String>();
        flags.add("--help");
        boolean desiredOutput = true;

        assertTrue(ArgUtils.validateFlags(flags) == desiredOutput);
    }

    @Test
    public void testValidateFlagsOneValidMax() {
        Set<String> flags = new HashSet<String>();
        flags.add("--max");
        boolean desiredOutput = true;

        assertTrue(ArgUtils.validateFlags(flags) == desiredOutput);
    }

    @Test
    public void testValidateFlagsOneValidFront() {
        Set<String> flags = new HashSet<String>();
        flags.add("--front");
        boolean desiredOutput = true;

        assertTrue(ArgUtils.validateFlags(flags) == desiredOutput);
    }

    @Test
    public void testValidateFlagsMultipleValid() {
        Set<String> flags = new HashSet<String>();
        flags.add("--help");
        flags.add("--max");
        flags.add("--front");
        boolean desiredOutput = true;

        assertTrue(ArgUtils.validateFlags(flags) == desiredOutput);
    }

    @Test
    public void testValidateFlagsOneInvalid() {
        Set<String> flags = new HashSet<String>();
        flags.add("front");
        boolean desiredOutput = false;

        assertTrue(ArgUtils.validateFlags(flags) == desiredOutput);
    }

    @Test
    public void testValidateFlagsMultipleInvalid() {
        Set<String> flags = new HashSet<String>();
        flags.add("front");
        flags.add("--");
        flags.add("--bad");
        boolean desiredOutput = false;

        assertTrue(ArgUtils.validateFlags(flags) == desiredOutput);
    }

    @Test
    public void testValidateFlagsOneValidOneInvalid() {
        Set<String> flags = new HashSet<String>();
        flags.add("--help");
        flags.add("--bad");
        boolean desiredOutput = false;

        assertTrue(ArgUtils.validateFlags(flags) == desiredOutput);
    }

    @Test
    public void testValidateFlagsOneValidMultipleInvalid() {
        Set<String> flags = new HashSet<String>();
        flags.add("--front");
        flags.add("front");
        flags.add("--bad");
        flags.add("-f");
        boolean desiredOutput = false;

        assertTrue(ArgUtils.validateFlags(flags) == desiredOutput);
    }

    @Test
    public void testValidateFlagsMultipleValidOneInvalid() {
        Set<String> flags = new HashSet<String>();
        flags.add("--help");
        flags.add("--front");
        flags.add("--max");
        flags.add("--bad");
        boolean desiredOutput = false;

        assertTrue(ArgUtils.validateFlags(flags) == desiredOutput);
    }

    @Test
    public void testValidateFlagsMultipleValidMultipleInvalid() {
        Set<String> flags = new HashSet<String>();
        flags.add("--help");
        flags.add("--front");
        flags.add("--max");
        flags.add("--bad");
        flags.add("front");
        flags.add("-f");
        boolean desiredOutput = false;

        assertTrue(ArgUtils.validateFlags(flags) == desiredOutput);
    }


}
