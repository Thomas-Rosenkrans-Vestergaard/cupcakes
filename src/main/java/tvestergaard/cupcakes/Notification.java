package tvestergaard.cupcakes;

public class Notification
{

    public enum Level
    {
        INFO,
        SUCCESS,
        WARNING,
        ERROR,
    }

    private final String message;
    private final Level  level;

    public static Notification info(String message)
    {
        return new Notification(message, Level.INFO);
    }

    public static Notification success(String message)
    {
        return new Notification(message, Level.SUCCESS);
    }

    public static Notification warning(String message)
    {
        return new Notification(message, Level.WARNING);
    }

    public static Notification error(String message)
    {
        return new Notification(message, Level.ERROR);
    }

    public static Notification of(String message, Level level)
    {
        return new Notification(message, level);
    }

    public Notification(String message, Level level)
    {
        this.message = message;
        this.level = level;
    }

    public String getMessage()
    {
        return this.message;
    }

    public Level getLevel()
    {
        return this.level;
    }
}
