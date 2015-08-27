package Utils;

/**
 * Created by AdrianM on 8/27/15.
 */
public class MessageUtils {

    /**
     * Prints out the given message with one newline before the message, and two newlines after the message.
     * Assumes the given message does not end with a newline.
     *
     * @param message The message to be printed. Should not end with a newline
     */
    public static void surroundWithNewlinesAndPrint(String message) {
        System.out.println();
        System.out.println(message);
        System.out.println();
    }
}
