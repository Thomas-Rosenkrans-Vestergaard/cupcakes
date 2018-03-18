package tvestergaard.cupcakes;

import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

public class NotificationsTest
{

    private Notifications instance;

    @Before
    public void setUp() throws Exception
    {
        this.instance = new Notifications();
    }

    @Test
    public void _notify() throws Exception
    {
        Notification notification = Notification.of(null, null);
        instance.notify(notification);
        assertSame(notification, instance.iterator().next());
    }

    @Test
    public void success() throws Exception
    {
        instance.success("Some success message.");
        Notification notification = instance.iterator().next();
        assertEquals("Some success message.", notification.getMessage());
        assertEquals(Notification.Level.SUCCESS, notification.getLevel());
    }

    @Test
    public void info() throws Exception
    {
        instance.info("Some info message.");
        Notification notification = instance.iterator().next();
        assertEquals("Some info message.", notification.getMessage());
        assertEquals(Notification.Level.INFO, notification.getLevel());
    }

    @Test
    public void warning() throws Exception
    {
        instance.warning("Some warning message.");
        Notification notification = instance.iterator().next();
        assertEquals("Some warning message.", notification.getMessage());
        assertEquals(Notification.Level.WARNING, notification.getLevel());
    }

    @Test
    public void error() throws Exception
    {
        instance.error("Some error message.");
        Notification notification = instance.iterator().next();
        assertEquals("Some error message.", notification.getMessage());
        assertEquals(Notification.Level.ERROR, notification.getLevel());
    }

    @Test
    public void iterator() throws Exception
    {
        Notification info    = Notification.of("Some info message.", Notification.Level.INFO);
        Notification warning = Notification.of("Some warning message", Notification.Level.WARNING);
        Notification error   = Notification.of("Some error message", Notification.Level.ERROR);

        instance.notify(info);
        instance.notify(warning);
        instance.notify(error);

        Iterator<Notification> iterator = instance.iterator();

        assertTrue(iterator.hasNext());
        assertEquals(info, iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals(warning, iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals(error, iterator.next());

        assertFalse(iterator.hasNext());
        assertEquals(0, instance.size());
    }

    @Test
    public void record() throws Exception
    {
        instance.record();

        Notification info    = Notification.of("Some info message.", Notification.Level.INFO);
        Notification warning = Notification.of("Some warning message", Notification.Level.WARNING);
        Notification error   = Notification.of("Some error message", Notification.Level.ERROR);

        instance.notify(info);
        instance.notify(warning);
        instance.notify(error);

        assertEquals(3, instance.recorded());
        instance.record();
        assertEquals(0, instance.recorded());
    }

    @Test
    public void hasNew() throws Exception
    {
        instance.record();

        Notification info    = Notification.of("Some info message.", Notification.Level.INFO);
        Notification error   = Notification.of("Some error message", Notification.Level.ERROR);

        instance.notify(info);
        instance.notify(error);

        assertEquals(2, instance.recorded());
        instance.record();
        assertEquals(0, instance.recorded());
    }

    @Test
    public void size() throws Exception
    {
        Notification info    = Notification.of("Some info message.", Notification.Level.INFO);
        Notification warning = Notification.of("Some warning message", Notification.Level.WARNING);
        Notification error   = Notification.of("Some error message", Notification.Level.ERROR);

        assertEquals(0, instance.size());
        instance.notify(info);
        assertEquals(1, instance.size());
        instance.notify(warning);
        assertEquals(2, instance.size());
        instance.notify(error);
        assertEquals(3, instance.size());
        instance.clear();
        assertEquals(0, instance.size());
    }

    @Test
    public void isEmpty() throws Exception
    {
        Notification info = Notification.of("Some info message.", Notification.Level.INFO);

        assertTrue(instance.isEmpty());
        instance.notify(info);
        assertFalse(instance.isEmpty());
    }

    @Test
    public void clear() throws Exception
    {
        Notification info = Notification.of("Some info message.", Notification.Level.INFO);

        assertTrue(instance.isEmpty());
        instance.notify(info);
        assertFalse(instance.isEmpty());
        instance.clear();
        assertTrue(instance.isEmpty());
    }
}