package tvestergaard.cupcakes.database;

import com.mysql.cj.jdbc.MysqlDataSource;

public class PrimaryDatabase extends MysqlDataSource
{

	public PrimaryDatabase()
	{
		setUser("cupcakes");
		setDatabaseName("cupcakes");
	}
}
