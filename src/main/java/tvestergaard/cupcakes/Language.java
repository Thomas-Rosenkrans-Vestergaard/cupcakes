package tvestergaard.cupcakes;

public class Language
{

    public final static String GENERAL_ERROR = "An error occurred";
    public final static String ERROR_ACCESS_USER = "You must be logged in to access this page.";
    public final static String ERROR_ACCESS_ADMINISTRATOR = "You must be logged in as an administrator to access this page.";

    public final static String MISSING_ID_PARAMETER = "Missing id parameter.";
    public final static String MALFORMED_ID_PARAMETER = "Malformed provided id parameter.";

    public final static String RECORD_CREATED_SUCCESS = "The record was successfully created.";
    public final static String RECORD_UPDATED_SUCCESS = "The record was successfully updated.";
    public final static String RECORD_DELETED_SUCCESS = "The record was successfully deleted.";
    public final static String RECORD_CREATED_ERROR = "The record could not be created.";
    public final static String RECORD_UPDATED_ERROR = "The record could not be updated.";
    public final static String RECORD_DELETED_ERROR = "The record could not be deleted.";

    public static final String LOGOUT_SUCCESS_NOTIFICATION = "You were successfully logged out, have a nice day.";
    public static final String LOGOUT_ERROR_NOTIFICATION = "You could not be logged out.";

    public final static String[] USER_USERNAME_ERRORS = {
            "Username field is required, but was not sent.",
            "Username must be longer than 3 characters.",
            "That username is already is use on our site.",
    };

    public final static String[] USER_EMAIL_ERRORS = {
            "Email field is required, but was not sent.",
            "Email was not formatted correctly.",
            "That email is already is use on our site.",
    };

    public final static String[] USER_PASSWORD_ERRORS = {
            "Password field is required, but was not sent.",
            "Password must by longer than 7 characters.",
            "Passwords must match.",
    };

    public final static String[] USER_BALANCE_ERRORS = {
            "Balance field is required, but was not sent.",
            "Balance must be formatted as a number.",
    };

    public final static String[] USER_TYPE_ERRORS = {
            "User role field is required, but was not sent.",
            "User role was not one of the listed options.",
    };

    public final static String[] ORDER_BOTTOM_ERRORS = {
            "Bottom id field is required, but was not sent.",
            "Bottom id must be a number."
    };

    public final static String[] ORDER_TOPPING_ERRORS = {
            "Bottom id field is required, but was not sent.",
            "Bottom id must be a number."
    };
}
