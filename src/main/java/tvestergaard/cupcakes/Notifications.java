package tvestergaard.cupcakes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Queue;

/**
 * Helper for sending notifications to the users of the site.
 *
 * @author Thomas
 */
public class Notifications implements Iterator<Notification>
{

    private static final String SESSION_KEY = "notifications";

    /**
     * The user {@link HttpSession} of the user making a request.
     */
    private final HttpSession session;

    /**
     * The {@link Queue} of notifications to send to them.
     */
    private final Queue<Notification> notifications;

    private int recorded = 0;

    /**
     * Creates a new {@link Notifications}.
     *
     * @param request The {@link HttpServletRequest}.
     */
    public Notifications(HttpServletRequest request)
    {
        this.session = request.getSession();
        this.notifications = getStack();
        request.setAttribute(SESSION_KEY, this);
    }

    /**
     * Adds a new message to the queue.
     *
     * @param notification The message to add.
     */
    public void notify(Notification notification)
    {
        notifications.add(notification);
    }

    /**
     * Adds a new notification with the provided message and the <code>Notification.Level</code> of <code>SUCCESS</code>.
     *
     * @param message The message.
     */
    public void success(String message)
    {
        notify(Notification.success(message));
    }

    /**
     * Adds a new notification with the provided message and the <code>Notification.Level</code> of <code>INFO</code>.
     *
     * @param message The message.
     */
    public void info(String message)
    {
        notify(Notification.info(message));
    }

    /**
     * Adds a new notification with the provided message and the <code>Notification.Level</code> of <code>WARNING</code>.
     *
     * @param message The message.
     */
    public void warning(String message)
    {
        notify(Notification.warning(message));
    }

    /**
     * Adds a new notification with the provided message and the <code>Notification.Level</code> of <code>ERROR</code>.
     *
     * @param message The message.
     */
    public void error(String message)
    {
        notify(Notification.error(message));
    }

    @Override
    public boolean hasNext()
    {
        return !notifications.isEmpty();
    }

    @Override
    public Notification next()
    {
        Notification notification = notifications.poll();
        if (notification != null)
            recorded--;

        return notification;
    }

    /**
     * Records the current number of notifications. The {@link Notifications#hasNew()} method can then be used to see
     * if the number of notifications in the queue has changed.
     *
     * @see Notifications#hasNew()
     */
    public void record()
    {
        this.recorded = 0;
    }

    /**
     * Checks if new notifications has been added to the queue since the last time the {@link Notifications#record()}
     * method was called.
     *
     * @return True is new notifications has been added to the queue since the last call to {@link Notifications#record()}.
     * @see Notifications#record()
     */
    public boolean hasNew()
    {
        return recorded != size();
    }

    /**
     * Returns the number of notifications.
     *
     * @return The number of notifications.
     */
    public int size()
    {
        return notifications.size();
    }

    /**
     * Retrieves the queue from the session, or creates a new one.
     *
     * @return The queue.
     */
    private Queue<Notification> getStack()
    {

        Object attribute = session.getAttribute(SESSION_KEY);

        if (attribute == null || !(attribute instanceof ArrayDeque)) {
            Queue<Notification> messages = new ArrayDeque<>();
            session.setAttribute(SESSION_KEY, messages);
            return messages;
        } else {
            Queue<Notification> messages = (ArrayDeque<Notification>) attribute;
            session.setAttribute(SESSION_KEY, messages);
            return messages;
        }
    }
}