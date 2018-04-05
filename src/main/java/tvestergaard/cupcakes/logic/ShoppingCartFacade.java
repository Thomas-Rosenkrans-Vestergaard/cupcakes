package tvestergaard.cupcakes.logic;

import tvestergaard.cupcakes.data.DAOException;
import tvestergaard.cupcakes.data.ProductionDatabaseSource;
import tvestergaard.cupcakes.data.bottoms.Bottom;
import tvestergaard.cupcakes.data.bottoms.BottomDAO;
import tvestergaard.cupcakes.data.bottoms.MysqlBottomDAO;
import tvestergaard.cupcakes.data.toppings.MysqlToppingDAO;
import tvestergaard.cupcakes.data.toppings.Topping;
import tvestergaard.cupcakes.data.toppings.ToppingDAO;

public class ShoppingCartFacade
{

    /**
     * The {@link BottomDAO} to use when querying persistent storage for {@link Bottom}s.
     */
    private final BottomDAO bottomDAO;

    /**
     * The {@link ToppingDAO} to use when querying persistent storage for {@link Topping}s.
     */
    private final ToppingDAO toppingDAO;

    /**
     * Creates a new {@link ShoppingCartFacade}.
     *
     * @param bottomDAO  The {@link BottomDAO} to use when querying persistent storage for {@link Bottom}s.
     * @param toppingDAO The {@link ToppingDAO} to use when querying persistent storage for {@link Topping}s.
     */
    public ShoppingCartFacade(BottomDAO bottomDAO, ToppingDAO toppingDAO)
    {
        this.bottomDAO = bottomDAO;
        this.toppingDAO = toppingDAO;
    }

    /**
     * Creates a new {@link ShoppingCartFacade} using an {@link MysqlBottomDAO} and {@link MysqlToppingDAO} and the
     * connection from {@link ProductionDatabaseSource#get()}.
     */
    public ShoppingCartFacade()
    {
        this.bottomDAO = new MysqlBottomDAO(ProductionDatabaseSource.get());
        this.toppingDAO = new MysqlToppingDAO(ProductionDatabaseSource.get());
    }

    /**
     * Creates an item using the provided details, and inserts that item into the provided {@link ShoppingCart}.
     *
     * @param cart      The {@link ShoppingCart} to insert the new item into.
     * @param bottomId  The id of the bottom to insert as an item.
     * @param toppingId The id of the topping to insert as an item.
     * @param quantity  The number of the items to insert into the {@link ShoppingCart}.
     * @throws ApplicationException   When a general exception occurs.
     * @throws NoSuchBottomException  When the provided bottom id matches no known {@link Bottom}.
     * @throws NoSuchToppingException When the provided topping id matches no known {@link Topping}.
     */
    public void addToCart(ShoppingCart cart, int bottomId, int toppingId, int quantity) throws NoSuchBottomException, NoSuchToppingException
    {
        try {

            Bottom bottom = bottomDAO.get(bottomId);
            if (bottom == null)
                throw new NoSuchBottomException(bottomId);

            Topping topping = toppingDAO.get(toppingId);
            if (topping == null)
                throw new NoSuchToppingException(toppingId);

            ShoppingCart.Item item = new ShoppingCart.Item(bottom, topping, quantity);
            cart.addItem(item);

        } catch (DAOException e) {
            throw new ApplicationException(e);
        }
    }
}
