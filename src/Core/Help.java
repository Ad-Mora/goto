package Core;

/**
 * Created by AdrianM on 7/4/15.
 */

/**
 * This class provides functions that output messages to aid the user. These include messages to alert the user of
 * invalid arguments, and messages to give the user information about usage of this program.
 */
public class Help {

    /**
     * This method is called directly by the user, when they ask for help. Provides information on the program itself,
     * on how to structure a command, and on the possible flags.
     *
     * @return A message containing information about this program, including basic usage and a list of all the
     * possible flags and their descriptions.
     */
    public static String getHelp() {
        String helpString = "\n";
        helpString += "Welcome to goto. This is a command line program that enables a user to browse the web from " +
                "the command line.\n\n";

        helpString += "Enter a command in the format 'goto <site name> <flags>'. This will open your " +
                "Chrome browser in a new window, and will automatically navigate the browser to the given site.\n\n";

        helpString += "You do not need to type in a fully qualified URL. You can simply type in the site name, and " +
                "a default prefix (http://www.) and suffix (.com) will automatically be added.\n\n";

        helpString += "For example, entering 'goto google' will open up the Chrome browser and navigate to " +
                "'http://www.google.com'.\n\n";

        helpString += "Possible optional flags are as follows:\n\n";

        helpString += Main.HELP_FLAG + ": Get help with goto (see this information again)\n";

        return helpString;
    }

    /**
     * This method is called when a user either enters an invalid argument, or enters no arguments at all. The user
     * is shown an invalid argument message.
     *
     * @return A message saying that the argument(s) (or lack of) are invalid. Also suggests usage of the help flag.
     */
    public static String getInvalidArgMessage() {
        return "\nInvalid argument(s). Enter 'goto " + Main.HELP_FLAG + "' for help.\n";
    }
}
