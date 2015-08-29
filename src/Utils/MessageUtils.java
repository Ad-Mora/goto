package Utils;

/*
 * Created by Adrian Mora on 8/27/15.
 */

/**
 * This class provides functions used to standardize and format the messages output to the user.
 */
public class MessageUtils {

    /**
     * Prints out the given message with one newline before the message, and two newlines after the message.
     *
     * @param message The message to be printed
     */
    public static void surroundWithNewlinesAndPrint(String message) {
        System.out.println();
        System.out.println(message);
        System.out.println();
    }
}
