package RestAPI;

/**
 * Created by Abanoub on 6/16/2015.
 */
public class RestMessages {

    final String error_message = "Failed to load data ,please contact your system administrator";
    final String connection_error_message = "Failed to load data ,please check your internet connection";
    final String success_message = "Loaded Successfully";

    private static RestMessages ourInstance = new RestMessages();

    public static RestMessages getInstance() {
        if (ourInstance == null) {
            ourInstance = new RestMessages();
            return ourInstance;
        }
        return ourInstance;
    }

    private RestMessages() {
    }

    public String getConnection_error_message() {
        return connection_error_message;
    }

    public String getSuccess_message() {
        return success_message;
    }

    public String getErrorMessage() {
        return error_message;
    }
}
