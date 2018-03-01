package tvestergaard.cupcakes.database.presets;

import tvestergaard.cupcakes.database.bottoms.Bottom;
import tvestergaard.cupcakes.database.toppings.Topping;

import java.util.List;

public interface PresetDAO
{
	Preset get(int id);

	Preset get(String name);

	List<Preset> get();

	Preset create(String name, String description, Bottom bottom, Topping topping);

    Preset update(int id, String name, String description, Bottom bottom, Topping topping);

	default boolean delete(Preset topping)
	{
		return delete(topping.getId());
	}

	boolean delete(int id);
}
