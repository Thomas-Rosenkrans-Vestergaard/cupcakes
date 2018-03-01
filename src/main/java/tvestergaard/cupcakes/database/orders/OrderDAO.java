package tvestergaard.cupcakes.database.orders;

import tvestergaard.cupcakes.Cart;
import tvestergaard.cupcakes.database.users.User;

import java.util.List;

public interface OrderDAO
{

	Order get(int id);

	List<Order> get(User user);

	List<Order> get();

	Order create(User user, Cart cart, String comment);
}
