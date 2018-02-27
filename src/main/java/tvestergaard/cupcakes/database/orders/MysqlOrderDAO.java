package tvestergaard.cupcakes.database.orders;

import com.mysql.cj.jdbc.MysqlDataSource;
import tvestergaard.cupcakes.database.AbstractMysqlDAO;
import tvestergaard.cupcakes.database.users.User;

import java.util.List;

public class MysqlOrderDAO extends AbstractMysqlDAO implements OrderDAO
{

	public MysqlOrderDAO(MysqlDataSource source)
	{
		super(source);
	}

	@Override public Order get(int id)
	{
		return null;
	}

	@Override public List<Order> get(User user)
	{
		return null;
	}

	@Override public List<Order> get()
	{
		return null;
	}
}
