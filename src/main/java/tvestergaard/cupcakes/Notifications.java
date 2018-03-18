package tvestergaard.cupcakes;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * Helper for sending notifications to the users of the site.
 *
 * @author Thomas
 */
public class Notifications implements Iterator<Notification>
{

    /**
     * The {@link Queue} of notifications to send to them.
     */
    private final Queue<Notification> notifications;

    /**
     * The number of notifications added since last call to {@link Notifications#record()}.
     */
    private int recorded = 0;

    /**
     * Creates a new {@link Notifications}.
     */
    public Notifications()
    {
        this.notifications = new ArrayDeque<>();
    }

    /**
     * Adds a new message to the queue.
     *
     * @param notification The message to add.
     */
    public void notify(Notification notification)
    {
        recorded++;
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

    /**
     * Returns {@code true} if the iteration has more elements.
     * (In other words, returns {@code true} if {@link #next} would
     * return an element rather than throwing an exception.)
     *
     * @return {@code true} if the iteration has more elements
     */
    @Override public boolean hasNext()
    {
        return !notifications.isEmpty();
    }

    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration
     * @throws NoSuchElementException if the iteration has no more elements
     */
    @Override public Notification next()
    {
        if (notifications.isEmpty())
            throw new NoSuchElementException();

        Notification notification = notifications.poll();
        if (notification != null)
            recorded--;

        return notification;
    }

    /**
     * Records the current number of getNotifications. The {@link Notifications#hasNew()} method can then be used to see
     * if the number of getNotifications in the queue has changed.
     *
     * @see Notifications#hasNew()
     */
    public void record()
    {
        this.recorded = 0;
    }

    /**
     * Checks if new getNotifications has been added to the queue since the last time the {@link Notifications#record()}
     * method was called.
     *
     * @return True is new getNotifications has been added to the queue since the last call to {@link Notifications#record()}.
     * @see Notifications#record()
     */
    public boolean hasNew()
    {
        return recorded != size();
    }

    /**
     * Returns the number of notifications added since the last call to {@link Notifications#recorded()}.
     *
     * @return the number of notifications added since the last call to {@link Notifications#recorded()}.
     * @see Notifications#record()
     * @see Notifications#hasNew()
     */
    public int recorded()
    {
        return this.recorded;
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
     * Returns {@code true} if there are no Notification instances.
     *
     * @return {@code true} if there are no Notification instances.
     */
    public boolean isEmpty()
    {
        return notifications.isEmpty();
    }

    /**
     * Removes all the notifications.
     */
    public void clear()
    {
        notifications.clear();
    }
}