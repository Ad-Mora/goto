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
 * - One invalid argument
 * - One valid argument
 * - Multiple invalid arguments
 * - Multiple valid arguments
 * - No newline arguments
 * - One newline character, single argument
 * - One newline character, multiple arguments
 * - Newline character at beginning of argument, one argument
 * - Newline character at end of argument, one argument
 * - Newline character at beginning of arguments, multiple arguments
 * - Newline character at end of arguments, multiple arguments
 * - Multiple newline characters, single argument
 * - Multiple newline characters, multiple arguments
 *
 * - First argument is not a flag, second argument is present
 * - First argument is not a flag, followed by two or more arguments
 *
 * - First argument is the help flag, no following arguments
 * - First argument is the help flag followed by a non-flag argument
 * - First argument is the help flag followed by another valid flag
 * - First argument is the help flag followed by an argument with a newline character
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
 * - First argument is the bookmark flag, second argument is valid, third argument is valid
 * - First argument is the bookmark flag, second argument is valid, third argument is a valid flag
 * - First argument is the bookmark flag, second argument is a valid flag, third argument is valid
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
    public void testValidateArgsOneInvalidArgument() {
        String[] args = {"\n"};
        assertFalse(ArgUtils.validateArgs(args));
    }

    @Test
    public void testValidateArgsOneValidArgument() {
        String[] args = {"google"};
        assertTrue(ArgUtils.validateArgs(args));
    }

    @Test
    public void testValidateArgsMultipleInvalidArguments() {
        String[] args = {"--bookmark", "--bookmark", "google"};
        assertFalse(ArgUtils.validateArgs(args));
    }

    @Test
    public void testValidateArgsMultipleValidArguments() {
        String[] args = {"--bookmark", "yt", "youtube.com"};
        assertTrue(ArgUtils.validateArgs(args));
    }

    @Test
    public void testValidateArgsNoNewlineCharacters() {
        String[] args = {"--view-bookmarks"};
        assertTrue(ArgUtils.validateArgs(args));
    }

    @Test
    public void testValidateArgsOneNewlineCharacterSingleArgument() {
        String[] args = {"goo\ngle"};
        assertFalse(ArgUtils.validateArgs(args));
    }

    @Test
    public void testValidateArgsOneNewlineCharacterMultipleArguments() {
        String[] args = {"--bookmark", "google", "http://\nwww.google.com"};
        assertFalse(ArgUtils.validateArgs(args));
    }

    @Test
    public void testValidateArgsNewlineCharacterAtBeginningOfArgumentSingleArgument() {
        String[] args = {"\napple"};
        assertFalse(ArgUtils.validateArgs(args));
    }

    @Test
    public void testValidateArgsNewlineCharacterAtEndOfArgumentSingleArgument() {
        String[] args = {"google\n"};
        assertFalse(ArgUtils.validateArgs(args));
    }

    @Test
    public void testValidateArgsNewlineCharacterAtBeginningOfArgumentsMultipleArguments() {
        String[] args = {"\n--delete-bookmark", "apple"};
        assertFalse(ArgUtils.validateArgs(args));
    }

    @Test
    public void testValidateArgsNewlineCharacterAtEndOfArgumentsMultipleArguments() {
        String[] args = {"--delete-bookmark", "stackoverflow\n"};
        assertFalse(ArgUtils.validateArgs(args));
    }

    @Test
    public void testValidateArgsMultipleNewlineCharactersSingleArgument() {
        String[] args = {"fa\nce\nbook\n"};
        assertFalse(ArgUtils.validateArgs(args));
    }

    @Test
    public void testValidateArgsMultipleNewlineCharactersMultipleArguments() {
        String[] args = {"--bookmark", "micro\nsoft\n", "\nmicrosoft.\ncom"};
        assertFalse(ArgUtils.validateArgs(args));
    }

    @Test
    public void testValidateArgsFirstArgumentValidSecondArgumentPresent() {
        String[] args = {"facebook", "secondArg"};
        assertFalse(ArgUtils.validateArgs(args));
    }

    @Test
    public void testValidateArgsFirstArgumentIsValidAtLeastTwoMoreArgumentsPresent() {
        String[] args = {"apple", "secondArg", "thirdArg"};
        assertFalse(ArgUtils.validateArgs(args));
    }

    @Test
    public void testValidateArgsFirstArgumentIsHelpFlagNoFollowingArguments() {
        String[] args = {"--help"};
        assertTrue(ArgUtils.validateArgs(args));
    }

    @Test
    public void testValidateArgsFirstArgumentIsHelpFlagFollowedByNonFlagArgument() {
        String[] args = {"--help", "secondArg"};
        assertFalse(ArgUtils.validateArgs(args));
    }

    @Test
    public void testValidateArgsFirstArgumentIsHelpFlagFollowedByValidFlag() {
        String[] args = {"--help", "--view-bookmarks"};
        assertFalse(ArgUtils.validateArgs(args));
    }

    @Test
    public void testValidateArgsFirstArgumentIsHelpFlagFollowedByArgumentWithNewlineCharacter() {
        String[] args = {"--help", "go\nogle"};
        assertFalse(ArgUtils.validateArgs(args));
    }

    @Test
    public void testValidateArgsFirstArgumentIsViewBookmarksFlagNoFollowingArguments() {
        String[] args = {"--view-bookmarks"};
        assertTrue(ArgUtils.validateArgs(args));
    }

    @Test
    public void testValidateArgsFirstArgumentIsViewBookmarksFlagFollowedByNonFlagArgument() {
        String[] args = {"--view-bookmarks", "secondArg"};
        assertFalse(ArgUtils.validateArgs(args));
    }

    @Test
    public void testValidateArgsFirstArgumentIsViewBookmarksFlagFollowedByValidFlag() {
        String[] args = {"--view-bookmarks", "--delete-bookmark"};
        assertFalse(ArgUtils.validateArgs(args));
    }

    @Test
    public void testValidateArgsFirstArgumentIsViewBookmarksFollowedByArgumentWithNewline() {
        String[] args = {"--view-bookmarks", "secondA\nrg"};
        assertFalse(ArgUtils.validateArgs(args));
    }

    @Test
    public void testValidateArgsFirstArgumentIsDeleteBookmarkFlagNoFollowingArguments() {
        String[] args = {"--delete-bookmark"};
        assertFalse(ArgUtils.validateArgs(args));
    }

    @Test
    public void testValidateArgsFirstArgumentIsDeleteBookmarkFlagSecondArgumentIsValid() {
        String[] args = {"--delete-bookmark", "google"};
        assertTrue(ArgUtils.validateArgs(args));
    }

    @Test
    public void testValidateArgsFirstArgumentIsDeleteBookmarkFlagSecondArgumentIsAValidFlag() {
        String[] args = {"--delete-bookmark", "--view-bookmarks"};
        assertFalse(ArgUtils.validateArgs(args));
    }

    @Test
    public void testValidateArgsFirstArgumentIsDeleteBookmarkFlagSecondAndThirdArgumentsArePresent() {
        String[] args = {"--delete-bookmark", "arg2", "arg3"};
        assertFalse(ArgUtils.validateArgs(args));
    }

    @Test
    public void testValidateArgsFirstArgumentIsDeleteBookmarkFlagSecondArgumentContainsANewlineCharacter() {
        String[] args = {"--delete-bookmark", "arg\n2"};
        assertFalse(ArgUtils.validateArgs(args));
    }

    @Test
    public void testValidateArgsFirstArgumentIsBookmarkFlagNoFollowingArguments() {
        String[] args = {"--bookmark"};
        assertFalse(ArgUtils.validateArgs(args));
    }

    @Test
    public void testValidateArgsFirstArgumentIsBookmarkFlagSecondArgumentIsValid() {
        String[] args = {"--bookmark", "ggl"};
        assertFalse(ArgUtils.validateArgs(args));
    }

    @Test
    public void testValidateArgsFirstArgumentIsBookmarkFlagSecondArgumentIsAValidFlag() {
        String[] args = {"--bookmark", "--view-bookmarks"};
        assertFalse(ArgUtils.validateArgs(args));
    }

    @Test
    public void testValidateArgsFirstArgumentIsBookmarkFlagSecondArgumentIsValidThirdArgumentIsValid() {
        String[] args = {"--bookmark", "fb", "facebook.com"};
        assertTrue(ArgUtils.validateArgs(args));
    }

    @Test
    public void testValidateArgsFirstArgumentIsBookmarkFlagSecondArgumentIsValidThirdArgumentIsValidFlag() {
        String[] args = {"--bookmark", "ggl", "--help"};
        assertTrue(ArgUtils.validateArgs(args));
    }

    @Test
    public void testValidateArgsFirstArgumentIsBookmarkFlagSecondArgumentIsValidFlagThirdArgumentIsValid() {
        String[] args = {"--bookmark", "--view-bookmarks", "http://www.google.com"};
        assertFalse(ArgUtils.validateArgs(args));
    }

    @Test
    public void testValidateArgsFirstArgumentIsBookmarkFlagSecondArgumentIsValidFlagThirdArgumentIsValidFlag() {
        String[] args = {"--bookmark", "--view-bookmarks", "--delete-bookmark"};
        assertFalse(ArgUtils.validateArgs(args));
    }

    @Test
    public void testValidateArgsFirstArgumentIsBookmarkFlagSecondThirdAndFourthArgumentsArePresent() {
        String[] args = {"--bookmark", "ggl", "http://www.google.com", "extraArg"};
        assertFalse(ArgUtils.validateArgs(args));
    }

    @Test
    public void testValidateArgsFirstArgumentIsBookmarkFlagSecondArgumentContainsANewlineCharacter() {
        String[] args = {"--bookmark", "goo\ngle"};
        assertFalse(ArgUtils.validateArgs(args));
    }

    @Test
    public void testValidateArgsFirstArgumentIsBookmarkFlagSecondArgumentPresentThirdArgumentHasNewlineCharacter() {
        String[] args = {"--bookmark", "fb", "http://www.face\nbook.com"};
        assertFalse(ArgUtils.validateArgs(args));
    }
}
