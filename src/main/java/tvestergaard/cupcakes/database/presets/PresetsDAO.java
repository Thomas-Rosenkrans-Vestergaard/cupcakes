package tvestergaard.cupcakes.database.presets;

import java.util.List;

public interface PresetsDAO
{
	Preset get(int id);

	Preset get(String name);

	List<Preset> get();
}
