package tvestergaard.cupcakes.data;

import com.mysql.cj.jdbc.MysqlDataSource;

public class ProductionDatabaseSource extends MysqlDataSource
{

    /**
     * Creates a new {@link ProductionDatabaseSource}.
     */
    public ProductionDatabaseSource()
    {
        setUser("root");
        setPassword("Hightech4u");
        setDatabaseName("t_cupcakedb");
    }

    /**
     * Stores the singleton {@link MysqlDataSource}.
     */
    private static MysqlDataSource singleton;

    /**
     * Returns a singleton {@link MysqlDataSource}.
     *
     * @return The singleton {@link MysqlDataSource}.
     */
    public static MysqlDataSource get()
    {
        if (singleton == null)
            singleton = new ProductionDatabaseSource();

        return singleton;
    }
}
