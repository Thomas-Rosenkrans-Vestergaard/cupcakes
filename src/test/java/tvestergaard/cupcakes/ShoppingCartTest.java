package tvestergaard.cupcakes;

import org.junit.Before;
import org.junit.Test;
import tvestergaard.cupcakes.data.bottoms.Bottom;
import tvestergaard.cupcakes.data.toppings.Topping;
import tvestergaard.cupcakes.logic.ShoppingCart;

import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

public class ShoppingCartTest
{

    private ShoppingCart instance;
    private final Bottom  bottom  = new Bottom(0, "", "", 500, true);
    private final Topping topping = new Topping(0, "", "", 500, true);

    @Before
    public void setUp()
    {
        instance = new ShoppingCart();
    }

    @Test
    public void getTotal() throws Exception
    {
        assertEquals(0, instance.getTotal());
        instance.addItem(new ShoppingCart.Item(bottom, topping, 5));
        assertEquals(5000, instance.getTotal());
        instance.addItem(new ShoppingCart.Item(bottom, topping, 5));
        assertEquals(10000, instance.getTotal());
    }

    @Test
    public void clear() throws Exception
    {
        assertTrue(instance.isEmpty());
        instance.addItem(new ShoppingCart.Item(bottom, topping, 5));
        assertFalse(instance.isEmpty());
        instance.clear();
        assertTrue(instance.isEmpty());
    }

    @Test
    public void iterator() throws Exception
    {
        instance.addItem(new ShoppingCart.Item(bottom, topping, 5));
        instance.addItem(new ShoppingCart.Item(bottom, topping, 5));
        instance.addItem(new ShoppingCart.Item(bottom, topping, 5));

        Iterator<ShoppingCart.Item> iterator = instance.iterator();

        int counter = 0;
        while (iterator.hasNext()) {
            iterator.next();
            counter++;
        }

        assertEquals(1, counter);
    }

    @Test
    public void isEmpty() throws Exception
    {
        assertTrue(instance.isEmpty());
        instance.addItem(new ShoppingCart.Item(bottom, topping, 5));
        assertFalse(instance.isEmpty());
    }

    @Test
    public void addItem() throws Exception
    {
        ShoppingCart.Item different = new ShoppingCart.Item(
                new Bottom(1, null, null, 0, false),
                new Topping(1, null, null, 0, false),
                5
        );

        instance.addItem(new ShoppingCart.Item(bottom, topping, 5));
        instance.addItem(new ShoppingCart.Item(bottom, topping, 5));
        instance.addItem(different);

        assertEquals(2, instance.size());
        List<ShoppingCart.Item> items = instance.getItems();
        assertEquals(10, items.get(0).getQuantity());
        assertEquals(5, items.get(1).getQuantity());
    }

    @Test
    public void size() throws Exception
    {
        assertEquals(0, instance.size());
        instance.addItem(new ShoppingCart.Item(bottom, topping, 5));
        assertEquals(1, instance.size());
        instance.addItem(new ShoppingCart.Item(bottom, topping, 5));
        assertEquals(1, instance.size());
        instance.clear();
        assertEquals(0, instance.size());
    }

    @Test
    public void getItems() throws Exception
    {
        ShoppingCart.Item item = new ShoppingCart.Item(bottom, topping, 5);
        instance.addItem(item);
        List<ShoppingCart.Item> list = instance.getItems();
        assertSame(item, list.get(0));
    }
}