package tvestergaard.cupcakes;

import com.mysql.cj.jdbc.MysqlDataSource;
import tvestergaard.cupcakes.database.PrimaryDatabase;

import java.sql.Connection;
import java.sql.SQLException;

public class New
{

    public static void main(String[] args) throws Exception
    {
        for(int x = 0; x < 1000; x++){
            MysqlDataSource source = new PrimaryDatabase();
            Connection connection = source.getConnection();
            connection.createStatement().execute("SELECT * FROM toppings");
            Thread.sleep(1000);
        }
    }
}
