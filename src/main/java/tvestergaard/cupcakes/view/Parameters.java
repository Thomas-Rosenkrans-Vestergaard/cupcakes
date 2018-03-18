package tvestergaard.cupcakes.view;

import javax.servlet.http.HttpServletRequest;

/**
 * Helper class for performing validation operations on incoming requests.
 */
public class Parameters
{

    /**
     * The request to validate the parameters of.
     */
    protected final HttpServletRequest request;

    /**
     * Creates a new {@link Parameters} class.
     *
     * @param request The request to perform parameter validation upon.
     */
    public Parameters(HttpServletRequest request)
    {
        this.request = request;
    }

    /**
     * Returns the parameter value as a string.
     *
     * @param parameterName The name of the parameter.
     * @return The parameter value as a string, return {@code null} if the parameter was not provided.
     */
    protected String asString(String parameterName)
    {
        return request.getParameter(parameterName);
    }

    /**
     * Checks that the parameter with the provided name was sent.
     *
     * @param parameterName The name of the parameter to perform the check upon.
     * @return {@code true} if the check passes, {@code false} if the check fails.
     */
    public boolean isPresent(String parameterName)
    {
        return asString(parameterName) != null;
    }

    /**
     * Checks that the parameter with the provided name not sent.
     *
     * @param parameterName The name of the parameter to perform the check upon.
     * @return {@code true} if the check passes, {@code false} if the check fails.
     */
    public boolean notPresent(String parameterName)
    {
        return asString(parameterName) == null;
    }

    /**
     * Checks that the parameter value with the provided name is empty (0 length).
     *
     * @param parameterName The name of the parameter to perform the check upon.
     * @return {@code true} if the check passes, {@code false} if the check fails.
     */
    public boolean isEmpty(String parameterName)
    {
        String value = asString(parameterName);

        return value == null || value.length() < 1;
    }

    /**
     * Checks that the parameter value with the provided name is not empty (not 0 length).
     *
     * @param parameterName The name of the parameter to perform the check upon.
     * @return {@code true} if the check passes, {@code false} if the check fails.
     */
    public boolean notEmpty(String parameterName)
    {
        return !isEmpty(parameterName);
    }

    /**
     * Checks that the parameter value with the provided name can be converted as an {@link Integer}.
     *
     * @param parameterName The name of the parameter to perform the check upon.
     * @return {@code true} if the check passes, {@code false} if the check fails.
     * @see Parameters#getInt(String)
     */
    public boolean isInt(String parameterName)
    {
        try {
            Integer.parseInt(asString(parameterName));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Checks that the parameter value with the provided name cannot be formatted as an {@link Integer}.
     *
     * @param parameterName The name of the parameter to perform the check upon.
     * @return {@code true} if the check passes, {@code false} if the check fails.
     * @see Parameters#getInt(String)
     */
    public boolean notInt(String parameterName)
    {
        return !isInt(parameterName);
    }

    /**
     * Returns the parameter value with the provided name as an {@code Integer}.
     *
     * @param parameterName The name of the parameter to return as an {@code Integer}.
     * @return The integer value.
     */
    public int getInt(String parameterName)
    {
        return Integer.parseInt(asString(parameterName));
    }

    /**
     * Returns the parameter value with the provided name as a {@code String}.
     *
     * @param parameterName The name of the parameter to return as a {@code String}.
     * @return The resulting {@code String}. If no parameter was sent, this method returns an empty {@code String}.
     */
    public String getString(String parameterName)
    {
        String value = asString(parameterName);

        return value == null ? "" : value;
    }

    /**
     * Checks that the parameter value with the provided name can be formatted as a positive {@code Integer}.
     *
     * @param parameterName The name of the parameter to perform the check upon.
     * @return {@code true} if the check passes, {@code false} if the check fails.
     * @see Parameters#isInt(String)
     */
    public boolean isPositiveInt(String parameterName)
    {
        return isInt(parameterName) && getInt(parameterName) > 0;
    }

    /**
     * Checks that the parameter value with the provided name can be converted to a negative {@code Integer}.
     *
     * @param parameterName The name of the parameter to perform the check upon.
     * @return {@code true} if the check passes, {@code false} if the check fails.
     * @see Parameters#isInt(String)
     */
    public boolean notPositiveInt(String parameterName)
    {
        return !isPositiveInt(parameterName);
    }

    /**
     * Checks that the parameter value with the provided name can be converted to a negative {@code Integer}.
     *
     * @param parameterName The name of the parameter to perform the check upon.
     * @return {@code true} if the check passes, {@code false} if the check fails.
     * @see Parameters#isInt(String)
     */
    public boolean isNegativeInt(String parameterName)
    {
        return isInt(parameterName) && getInt(parameterName) < 0;
    }

    /**
     * Checks that the parameter value with the provided name cannot be converted to a negative {@code Integer}.
     *
     * @param parameterName The name of the parameter to perform the check upon.
     * @return {@code true} if the check passes, {@code false} if the check fails.
     * @see Parameters#isInt(String)
     */
    public boolean notNegativeInt(String parameterName)
    {
        return isInt(parameterName) && getInt(parameterName) > -1;
    }

    /**
     * Checks that the parameter value with the provided name can be converted to a boolean value.
     *
     * These are the string values considered boolean:
     *      - true:
     *        - "true"
     *        - "t"
     *        - "1"
     *      - false:
     *        - "false"
     *        - "f"
     *        - "0"
     *
     * @param parameterName The name of the parameter to perform the check upon.
     * @return {@code true} if the check passes, {@code false} if the check fails.
     * @see Parameters#isInt(String)
     */
    public boolean isBoolean(String parameterName)
    {
        String value = asString(parameterName);

        if (value == null)
            return false;

        return value.equals("true") || value.equals("false") || value.equals("t") || value.equals("f") || value.equals("1") || value.equals("0");
    }

    /**
     * Checks that the parameter value with the provided name cannot be converted to a boolean value.
     *
     * These are the string values considered boolean:
     *      - true:
     *        - "true"
     *        - "t"
     *        - "1"
     *      - false:
     *        - "false"
     *        - "f"
     *        - "0"
     *
     * @param parameterName The name of the parameter to perform the check upon.
     * @return {@code true} if the check passes, {@code false} if the check fails.
     * @see Parameters#isInt(String)
     */
    public boolean notBoolean(String parameterName)
    {
        return !isBoolean(parameterName);
    }

    /**
     * Returns the parameter value with the provided name as a boolean value.
     *
     * @param parameterName The name of the parameter to return as a boolean value.
     * @return The boolean value.
     */
    public boolean getBoolean(String parameterName)
    {
        String value = asString(parameterName);

        if (value.equals("true") || value.equals("t") || value.equals("!"))
            return true;

        if (value.equals("false") || value.equals("f") || value.equals("0"))
            return false;

        throw new UnsupportedOperationException("getBoolean found " + value);
    }
}
