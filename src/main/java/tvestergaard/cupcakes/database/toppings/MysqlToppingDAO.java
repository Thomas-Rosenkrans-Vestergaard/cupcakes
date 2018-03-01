package tvestergaard.cupcakes.database.toppings;

import com.mysql.cj.jdbc.MysqlDataSource;
import tvestergaard.cupcakes.database.AbstractMysqlDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MysqlToppingDAO extends AbstractMysqlDAO implements ToppingDAO
{

    private static final String ID_COLUMN          = "toppings.id";
    private static final String NAME_COLUMN        = "toppings.name";
    private static final String DESCRIPTION_COLUMN = "toppings.description";
    private static final String PRICE_COLUMN       = "toppings.price";

    public MysqlToppingDAO(MysqlDataSource source)
    {
        super(source);
    }

    @Override public Topping get(int id)
    {
        try {

            PreparedStatement statement = null;
            ResultSet results = null;

            try {

                statement = getConnection().prepareStatement("SELECT * FROM toppings WHERE id = ?");
                statement.setInt(1, id);
                results = statement.executeQuery();
                return !results.first() ? null : createTopping(results);

            } finally {
                if (statement != null)
                    statement.close();
                if (results != null)
                    results.close();
            }

        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override public Topping get(String name)
    {
        try {

            PreparedStatement statement = null;
            ResultSet results = null;

            try {

                statement = getConnection().prepareStatement("SELECT * FROM toppings WHERE name = ?");
                statement.setString(1, name);
                results = statement.executeQuery();
                return !results.first() ? null : createTopping(results);

            } finally {
                if (statement != null)
                    statement.close();
                if (results != null)
                    results.close();
            }

        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override public List<Topping> get()
    {
        List<Topping> list = new ArrayList<>();

        try {

            PreparedStatement statement = null;
            ResultSet results = null;

            try {

                statement = getConnection().prepareStatement("SELECT * FROM toppings");
                results = statement.executeQuery();
                while (results.next())
                    list.add(createTopping(results));

                return list;

            } finally {
                if (statement != null)
                    statement.close();
                if (results != null)
                    results.close();
            }

        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override public Topping create(String name, String description, int price)
    {
        try {

            String update = "INSERT INTO toppings (`name`, description, price) VALUES (?, ?, ?)";
            Connection connection = getConnection();
            PreparedStatement statement = null;

            try {

                statement = connection.prepareStatement(update, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, name);
                statement.setString(2, description);
                statement.setInt(3, price);

                statement.executeUpdate();

                ResultSet generatedKeys = statement.getGeneratedKeys();
                generatedKeys.first();

                int id = generatedKeys.getInt(1);
                connection.commit();

                return new Topping(id, name, description, price);

            } catch (SQLException e) {
                connection.rollback();
                throw e;
            } finally {
                if (statement != null)
                    statement.close();
            }

        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override public Topping update(int id, String name, String description, int price)
    {
        try {

            String update = "UPDATE toppings SET `name` = ?, `description` = ?, `price` = ? WHERE id = ?";
            Connection connection = getConnection();
            PreparedStatement statement = null;

            try {

                statement = connection.prepareStatement(update);
                statement.setString(1, name);
                statement.setString(2, description);
                statement.setInt(3, price);
                statement.setInt(4, id);

                statement.executeUpdate();
                connection.commit();

                return new Topping(id, name, description, price);

            } catch (SQLException e) {
                connection.rollback();
                throw e;
            } finally {
                if (statement != null)
                    statement.close();
            }

        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override public boolean delete(int id)
    {
        try {

            String            delete     = "DELETE FROM toppings WHERE id = ?";
            Connection        connection = getConnection();
            PreparedStatement statement  = null;

            try {

                statement = connection.prepareStatement(delete);
                statement.setInt(1, id);

                int deleted = statement.executeUpdate();
                connection.commit();

                return deleted > 0;

            } catch (SQLException e) {
                connection.rollback();
                throw e;
            } finally {
                if (statement != null)
                    statement.close();
            }

        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    private Topping createTopping(ResultSet results) throws SQLException
    {
        return new Topping(
                results.getInt(ID_COLUMN),
                results.getString(NAME_COLUMN),
                results.getString(DESCRIPTION_COLUMN),
                results.getInt(PRICE_COLUMN)
        );
    }
}
