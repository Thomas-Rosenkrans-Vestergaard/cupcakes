package tvestergaard.cupcakes.data;

import com.mysql.cj.jdbc.MysqlDataSource;

public class ProductionDatabaseSource extends MysqlDataSource
{

    public ProductionDatabaseSource()
    {
        setUser("cupcakes");
        setDatabaseName("cupcakes");
    }

    private static MysqlDataSource singleton;

    public static MysqlDataSource singleton()
    {
        if (singleton == null)
            singleton = new ProductionDatabaseSource();

        return singleton;
    }
}
