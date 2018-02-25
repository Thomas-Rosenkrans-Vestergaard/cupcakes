package tvestergaard.cupcakes.database;

import com.mysql.cj.jdbc.MysqlDataSource;

public class CupcakeMysqlDataSource extends MysqlDataSource
{

	public CupcakeMysqlDataSource()
	{
		setUser("cupcakes");
		setDatabaseName("cupcakes");
	}
}
