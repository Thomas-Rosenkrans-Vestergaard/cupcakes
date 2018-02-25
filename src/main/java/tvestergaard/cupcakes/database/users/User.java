package tvestergaard.cupcakes.database.users;

import java.util.Objects;

public class User
{

	private final int    id;
	private final String email;
	private final String username;
	private final int    balance;

	public User(int id, String email, String username, int balance)
	{
		this.id = id;
		this.email = email;
		this.username = username;
		this.balance = balance;
	}

	public final int getId()
	{
		return this.id;
	}

	public String getEmail()
	{
		return this.email;
	}

	public final String getUsername()
	{
		return this.username;
	}

	public final int getBalance()
	{
		return this.balance;
	}

	@Override public boolean equals(Object o)
	{
		if (this == o) return true;
		if (!(o instanceof User)) return false;
		User user = (User) o;
		return id == user.id &&
			   balance == user.balance &&
			   Objects.equals(email, user.email) &&
			   Objects.equals(username, user.username);
	}

	@Override public int hashCode()
	{
		return Objects.hash(id);
	}
}
