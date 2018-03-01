package tvestergaard.cupcakes.database.presets;

import com.mysql.cj.jdbc.MysqlDataSource;
import tvestergaard.cupcakes.database.AbstractMysqlDAO;
import tvestergaard.cupcakes.database.bottoms.Bottom;
import tvestergaard.cupcakes.database.toppings.Topping;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MysqlPresetDAO extends AbstractMysqlDAO implements PresetDAO
{
	public MysqlPresetDAO(MysqlDataSource source)
	{
		super(source);
	}

	@Override public Preset get(int id)
	{

		try {

			String sql = "SELECT * FROM presets INNER JOIN bottoms ON bottom = bottoms.id INNER JOIN toppings ON " +
						 "topping = toppings.id WHERE presets.id = ?";

			PreparedStatement statement = getConnection().prepareStatement(sql);
			statement.setInt(1, id);
			ResultSet results = statement.executeQuery();
			if (!results.first())
				return null;

			Preset preset = createPreset(results);

			results.close();
			statement.close();

			return preset;

		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	@Override public Preset get(String name)
	{
		try {

			String sql = "SELECT * FROM presets INNER JOIN bottoms ON bottom = bottoms.id INNER JOIN toppings ON " +
						 "topping = toppings.id WHERE presets.name = ?";

			PreparedStatement statement = getConnection().prepareStatement(sql);
			statement.setString(1, name);
			ResultSet results = statement.executeQuery();
			if (!results.first())
				return null;

			Preset preset = createPreset(results);

			results.close();
			statement.close();

			return preset;

		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	@Override public List<Preset> get()
	{
		List<Preset> list = new ArrayList<>();

		try {

			String sql = "SELECT * FROM presets INNER JOIN bottoms ON bottom = bottoms.id INNER JOIN toppings ON " +
						 "topping = toppings.id";

			PreparedStatement statement = getConnection().prepareStatement(sql);
			ResultSet         results   = statement.executeQuery();
			while (results.next())
				list.add(createPreset(results));

			results.close();
			statement.close();

			return list;

		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	private Preset createPreset(ResultSet resultSet) throws SQLException
	{
		return new Preset(
				resultSet.getInt("presets.id"),
				resultSet.getString("presets.name"),
				resultSet.getString("presets.description"),
				new Bottom(
						resultSet.getInt("bottoms.id"),
						resultSet.getString("bottoms.name"),
						resultSet.getString("bottoms.description"),
						resultSet.getInt("bottoms.price")
				),
				new Topping(
						resultSet.getInt("toppings.id"),
						resultSet.getString("toppings.name"),
						resultSet.getString("toppings.description"),
						resultSet.getInt("toppings.price")
				),
				resultSet.getInt("presets.price")
		);
	}
}
