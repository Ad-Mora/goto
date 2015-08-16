package UtilsTests;

import Utils.ArgUtils;
import org.junit.Test;

import static org.junit.Assert.*;

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
 *
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
 * args:
 *
 * - No arguments
 * - One argument
 * - Multiple arguments
 * - One newline character, single argument
 * - One newline character, multiple arguments
 * - Multiple newline characters, single argument
 * - Multiple newline characters, multiple arguments
 *
 * - First argument is the view bookmarks flag, no following arguments
 * - First argument is the view bookmarks flag followed by a non-flag argument
 * - First argument is the view bookmarks flag followed by another valid flag
 * - First argument is the view bookmarks flag followed by an argument containing a newline character
 *
 * - First argument is the delete bookmark flag, no following arguments
 * - First argument is the delete bookmark flag, second argument is present, not a flag
 * - First argument is the delete bookmark flag, second argument is another valid flag
 * - First argument is the delete bookmark flag, second argument is present, third argument is present
 * - First argument is the delete bookmark flag, second argument contains a newline character
 *
 * - First argument is the bookmark flag, no following arguments
 * - First argument is the bookmark flag, second argument is not a valid flag
 * - First argument is the bookmark flag, second argument is a valid flag
 * - First argument is the bookmark flag, second argument is not a valid flag, third argument is not a valid flag
 * - First argument is the bookmark flag, second argument is not a valid flag, third argument is a valid flag
 * - First argument is the bookmark flag, second argument is a valid flag, third argument is not a valid flag
 * - First argument is the bookmark flag, second argument is a valid flag, third argument is a valid flag
 * - First argument is the bookmark flag, second, third, and fourth arguments are present
 * - First argument is the bookmark flag, second argument contains a newline character
 * - First argument is the bookmark flag, second argument is present, third argument contains a newline character
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

    @Test
    public void testValidateArgsNoArguments() {
        String[] args = {};
        assertFalse(ArgUtils.validateArgs(args));
    }

    @Test
    public void testValidateArgsOneArgument() {
    }

    @Test
    public void testValidateArgsMultipleArguments() {
    }

    @Test
    public void testValidateArgsOneNewlineCharacterSingleArgument() {
    }

    @Test
    public void testValidateArgsOneNewlineCharacterMultipleArguments() {
    }

    @Test
    public void testValidateArgsMultipleNewlineCharactersSingleArgument() {
    }

    @Test
    public void testValidateArgsMultipleNewlineCharactersMultipleArguments() {
    }

    @Test
    public void testValidateArgsViewBookmarksNoFollowingArguments() {
    }

    @Test
    public void testValidateArgsViewBookmarksFollowedByNonFlagArgument() {
    }

    @Test
    public void testValidateArgsViewBookmarksFollowedByValidFlag() {
    }

    @Test
    public void testValidateArgsViewBookmarksFollowedByArgumentWithNewline() {
    }

    @Test
    public void testValidateArgsDeleteBookmarkFlagNoFollowingArguments() {
    }

    @Test
    public void testValidateArgsDeleteBookmarkFlagSecondArgumentNotAValidFlag() {
    }

    @Test
    public void testValidateArgsDeleteBookmarkFlagSecondArgumentIsAValidFlag() {
    }

    @Test
    public void testValidateArgsSecondAndThirdArgumentsArePresent() {
    }

    @Test
    public void testValidateArgsSecondArgumentContainsANewlineCharacter() {
    }

    @Test
    public void testValidateArgsBookmarkFlagNoFollowingArguments() {
    }

    @Test
    public void testValidateArgsBookmarkFlagSecondArgumentIsNotAValidFlag() {
    }

    @Test
    public void testValidateArgsBookmarkFlagSecondArgumentIsAValidFlag() {
    }

    @Test
    public void testValidateArgsBookmarkFlagSecondArgumentIsNotValidFlagThirdArgumentIsNotValidFlag() {
    }

    @Test
    public void testValidateArgsBookmarkFlagSecondArgumentIsNotValidFlagThirdArgumentIsValidFlag() {
    }

    @Test
    public void testValidateArgsBookmarkFlagSecondArgumentIsValidFlagThirdArgumentIsNotValidFlag() {
    }

    @Test
    public void testValidateArgsBookmarkFlagSecondArgumentIsValidFlagThirdArgumentIsValidFlag() {
    }

    @Test
    public void testValidateArgsBookmarkFlagSecondThirdAndFourthArgumentsArePresent() {
    }

    @Test
    public void testValidateArgsBookmarkFlagSecondArgumentContainsANewlineCharacter() {
    }

    @Test
    public void testValidateArgsBookmarkFlagSecondArgumentIsPresentThirdArgumentContainsNewlineCharacter() {
    }
}
