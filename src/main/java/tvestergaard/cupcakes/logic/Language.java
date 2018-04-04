package tvestergaard.cupcakes.logic;

public class Language
{

    // Generic messages
    public final static String GENERAL_ERROR          = "An error occurred";
    public final static String GENERAL_ERROR_RENDER   = "An error prevented this page from being rendered.";
    public final static String MISSING_ID_PARAMETER   = "Missing id parameter.";
    public final static String MALFORMED_ID_PARAMETER = "Malformed provided id parameter.";

    // Access messages
    public final static String ERROR_ACCESS_USER          = "You must be logged in to access this page.";
    public final static String ERROR_ACCESS_ADMINISTRATOR = "You must be logged in as an administrator to access this page.";

    // Registration messages
    public final static String REGISTRATION_SUCCESS = "You successfully created a new user account.";
    public final static String REGISTRATION_ERROR   = "The user account could not be created.";

    // Login messages
    public final static String LOGIN_ERROR   = "Incorrect username or password.";
    public final static String LOGIN_SUCCESS = "You are now logged in.";

    // Database messages
    public final static String RECORD_CREATED_SUCCESS = "The record was successfully created.";
    public final static String RECORD_UPDATED_SUCCESS = "The record was successfully updated.";
    public final static String RECORD_DELETED_SUCCESS = "The record was successfully deleted.";
    public final static String RECORD_CREATED_ERROR   = "The record could not be created.";
    public final static String RECORD_UPDATED_ERROR   = "The record could not be updated.";
    public final static String RECORD_DELETED_ERROR   = "The record could not be deleted.";

    public final static String UNKNOWN_ACTION_ERROR = "That action is not supported.";
    public final static String INCOMPLETE_FORM_POST = "Incomplete form data.";

    public static final String LOGOUT_SUCCESS_NOTIFICATION = "You were successfully logged out, have a nice day.";
}
