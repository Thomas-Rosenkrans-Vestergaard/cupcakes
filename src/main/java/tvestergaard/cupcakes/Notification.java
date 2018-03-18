package tvestergaard.cupcakes;

import java.util.Objects;

/**
 * Represents a message sent to a visitor via the {@link Notifications} class.
 */
public class Notification
{
    public enum Level
    {
        INFO,
        SUCCESS,
        WARNING,
        ERROR,
    }

    /**
     * The message to display to the visitor.
     */
    private final String message;

    /**
     * The level of the message.
     */
    private final Level level;

    /**
     * Creates a new notification with level info.
     *
     * @param message The message to forward to the user.
     * @return The newly created notification.
     */
    public static Notification info(String message)
    {
        return new Notification(message, Level.INFO);
    }

    /**
     * Creates a new notification with level success.
     *
     * @param message The message to forward to the user.
     * @return The newly created notification.
     */
    public static Notification success(String message)
    {
        return new Notification(message, Level.SUCCESS);
    }

    /**
     * Creates a new notification with level warning.
     *
     * @param message The message to forward to the user.
     * @return The newly created notification.
     */
    public static Notification warning(String message)
    {
        return new Notification(message, Level.WARNING);
    }

    /**
     * Creates a new notification with level error.
     *
     * @param message The message to forward to the user.
     * @return The newly created notification.
     */
    public static Notification error(String message)
    {
        return new Notification(message, Level.ERROR);
    }

    /**
     * Creates a new notification with a provided level and message.
     *
     * @param message The message to forward to the user.
     * @param level   The level of the notification to create.
     * @return The newly created notification.
     */
    public static Notification of(String message, Level level)
    {
        return new Notification(message, level);
    }

    /**
     * Creates a new notification with a provided level and message.
     *
     * @param message The message to forward to the user.
     * @param level   The level of the notification to create.
     * @return The newly created notification.
     */
    public Notification(String message, Level level)
    {
        this.message = message;
        this.level = level;
    }

    /**
     * Returns the message of the notification.
     *
     * @return The message of the notification.
     */
    public String getMessage()
    {
        return this.message;
    }

    /**
     * Returns the level of the notification.
     *
     * @return The level of the notification.
     */
    public Level getLevel()
    {
        return this.level;
    }

    @Override public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Notification)) return false;
        Notification that = (Notification) o;
        return Objects.equals(message, that.message) &&
                level == that.level;
    }

    @Override public int hashCode()
    {
        return Objects.hash(message, level);
    }
}
