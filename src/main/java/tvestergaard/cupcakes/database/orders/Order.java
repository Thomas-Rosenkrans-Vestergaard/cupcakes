package tvestergaard.cupcakes.database.orders;

import tvestergaard.cupcakes.database.bottoms.Bottom;
import tvestergaard.cupcakes.database.toppings.Topping;
import tvestergaard.cupcakes.database.users.User;

public class Order
{
	private final int     id;
	private final User    user;
	private final Bottom  bottom;
	private final Topping topping;
	private final int     amount;
	private final int     total;

	public Order(int id, User user, Bottom bottom, Topping topping, int amount, int total)
	{
		this.id = id;
		this.user = user;
		this.bottom = bottom;
		this.topping = topping;
		this.amount = amount;
		this.total = total;
	}

	public int getId()
	{
		return this.id;
	}

	public User getUser()
	{
		return this.user;
	}

	public Bottom getBottom()
	{
		return this.bottom;
	}

	public Topping getTopping()
	{
		return this.topping;
	}

	public int getAmount()
	{
		return this.amount;
	}

	public int getTotal()
	{
		return this.total;
	}
}
