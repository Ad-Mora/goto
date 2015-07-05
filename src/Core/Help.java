package Core;

/**
 * Created by AdrianM on 7/4/15.
 */
public class Help {

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

        helpString += "--max: Open browser at maximize to fit the screen\n\n";

        helpString += "--front: Bring the browser to the front of the screen (make the browser the active window)\n\n";

        helpString += "--help: Get help with goto (see this information again)\n";

        return helpString;
    }
}
