package tvestergaard.cupcakes.database.orders;

import tvestergaard.cupcakes.database.bottoms.Bottom;
import tvestergaard.cupcakes.database.toppings.Topping;
import tvestergaard.cupcakes.database.users.User;

import java.util.Collections;
import java.util.List;

public class Order
{
	private final int        id;
	private final User       user;
	private final int        total;
	private final String     comment;
	private final Status     status;
	private final List<Item> items;

	public Order(int id, User user, int total, String comment, Status status, List<Item> items)
	{
		this.id = id;
		this.user = user;
		this.total = total;
		this.comment = comment;
		this.status = status;
		this.items = items;
	}

	public int getId()
	{
		return this.id;
	}

	public User getUser()
	{
		return this.user;
	}

	public int getTotal()
	{
		return this.total;
	}

	public String getComment()
	{
		return this.comment;
	}

	public Status getStatus()
	{
		return this.status;
	}

	public List<Item> getItems()
	{
		return this.items;
	}

	public enum Status
	{
		PLACED(0),
		READY(1),
		INACTIVE(2);

		private final int code;

		Status(int code)
		{
			this.code = code;
		}

		public int getCode()
		{
			return this.code;
		}

		private static Status[] statuses = Status.values();

		public static Status fromCode(int code)
		{
			return statuses[code];
		}
	}

	public static class Item
	{
		private final int     id;
		private final Bottom  bottom;
		private final Topping topping;
		private final int     amount;
		private final int     unitPrice;
		private final int     totalPrice;

		public Item(int id, Bottom bottom, Topping topping, int amount, int unitPrice, int totalPrice)
		{
			this.id = id;
			this.bottom = bottom;
			this.topping = topping;
			this.amount = amount;
			this.unitPrice = unitPrice;
			this.totalPrice = totalPrice;
		}

		public int getId()
		{
			return this.id;
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

		public int getUnitPrice()
		{
			return this.unitPrice;
		}

		public int getTotalPrice()
		{
			return this.totalPrice;
		}
	}
}
