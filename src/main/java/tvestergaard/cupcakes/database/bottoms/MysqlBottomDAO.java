package tvestergaard.cupcakes.database.bottoms;

import com.mysql.cj.jdbc.MysqlDataSource;
import tvestergaard.cupcakes.database.AbstractMysqlDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MysqlBottomDAO extends AbstractMysqlDAO implements BottomDAO
{

    private static final String ID_COLUMN          = "bottoms.id";
    private static final String NAME_COLUMN        = "bottoms.name";
    private static final String DESCRIPTION_COLUMN = "bottoms.description";
    private static final String PRICE_COLUMN       = "bottoms.price";
    private static final String ACTIVE_COLUMN      = "bottoms.active";

    public MysqlBottomDAO(MysqlDataSource source)
    {
        super(source);
    }

    @Override
    public Bottom get(int id)
    {
        try {

            PreparedStatement statement = null;
            ResultSet         results   = null;

            try {

                statement = getConnection().prepareStatement("SELECT * FROM bottoms WHERE id = ?");
                statement.setInt(1, id);
                results = statement.executeQuery();
                return !results.first() ? null : createBottom(results);

            } finally {
                if (statement != null) statement.close();
                if (results != null) results.close();
            }

        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Bottom get(String name)
    {
        try {

            PreparedStatement statement = null;
            ResultSet         results   = null;

            try {

                statement = getConnection().prepareStatement("SELECT * FROM bottoms WHERE name = ?");
                statement.setString(1, name);
                results = statement.executeQuery();
                return !results.first() ? null : createBottom(results);

            } finally {
                if (statement != null) statement.close();
                if (results != null) results.close();
            }

        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<Bottom> get()
    {
        List<Bottom> list = new ArrayList<>();

        try {

            PreparedStatement statement = null;
            ResultSet         results   = null;

            try {

                statement = getConnection().prepareStatement("SELECT * FROM bottoms");
                results = statement.executeQuery();
                while (results.next()) list.add(createBottom(results));

                return list;

            } finally {
                if (statement != null) statement.close();
                if (results != null) results.close();
            }

        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }


    @Override
    public Bottom create(String name, String description, int price)
    {
        try {

            String            update     = "INSERT INTO bottoms (`name`, description, price) VALUES (?, ?, ?)";
            Connection        connection = getConnection();
            PreparedStatement statement  = null;

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

                return new Bottom(id, name, description, price);

            } catch (SQLException e) {
                connection.rollback();
                throw e;
            } finally {
                if (statement != null) statement.close();
            }

        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Bottom update(int id, String name, String description, int price)
    {
        try {

            String            update     = "UPDATE bottoms SET `name` = ?, `description` = ?, `price` = ? WHERE id = ?";
            Connection        connection = getConnection();
            PreparedStatement statement  = null;

            try {

                statement = connection.prepareStatement(update);
                statement.setString(1, name);
                statement.setString(2, description);
                statement.setInt(3, price);
                statement.setInt(4, id);

                statement.executeUpdate();
                connection.commit();

                return new Bottom(id, name, description, price);

            } catch (SQLException e) {
                connection.rollback();
                throw e;
            } finally {
                if (statement != null) statement.close();
            }

        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public boolean delete(int id)
    {
        try {

            String            delete     = "DELETE FROM bottoms WHERE id = ?";
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
                if (statement != null) statement.close();
            }

        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    Bottom createBottom(ResultSet results) throws SQLException
    {
        return new Bottom(
                results.getInt(ID_COLUMN),
                results.getString(NAME_COLUMN),
                results.getString(DESCRIPTION_COLUMN),
                results.getInt(PRICE_COLUMN)
        );
    }
}
