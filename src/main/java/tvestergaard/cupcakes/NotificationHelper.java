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
public class NotificationHelper implements Iterator<Object>
{

	/**
	 * The user {@link HttpSession} of the user making a request.
	 */
	private final HttpSession session;

	/**
	 * The {@link Queue} of notifications to send to them.
	 */
	private final Queue<Object> notifications;

	private int newNotifications = 0;

	private static final String KEY = "notifications";

	/**
	 * Creates a new {@link NotificationHelper}.
	 *
	 * @param request The {@link HttpServletRequest}.
	 */
	public NotificationHelper(HttpServletRequest request)
	{
		this.session = request.getSession();
		this.notifications = getStack();
		request.setAttribute(KEY, this);
	}

	/**
	 * Adds a new message to the queue.
	 *
	 * @param message The message to add.
	 */
	public void notify(Object message)
	{
		notifications.add(message);
		newNotifications++;
	}

	@Override
	public boolean hasNext()
	{
		return !notifications.isEmpty();
	}

	@Override
	public Object next()
	{
		return notifications.poll();
	}

	public void record()
	{
		this.newNotifications = 0;
	}

	public boolean hasNew()
	{
		return newNotifications != 0;
	}

	public int size()
	{
		return notifications.size();
	}

	/**
	 * Retrieves the queue from the session, or creates a new one.
	 *
	 * @return The queue.
	 */
	private Queue<Object> getStack()
	{

		Object attribute = session.getAttribute(KEY);

		if (attribute == null || !(attribute instanceof ArrayDeque)) {
			Queue<Object> messages = new ArrayDeque<>();
			session.setAttribute(KEY, messages);
			return messages;
		} else {
			Queue<Object> messages = (ArrayDeque) attribute;
			session.setAttribute(KEY, messages);
			return messages;
		}
	}
}