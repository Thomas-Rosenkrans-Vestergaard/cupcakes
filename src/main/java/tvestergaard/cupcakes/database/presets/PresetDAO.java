package tvestergaard.cupcakes.database.presets;

import java.util.List;

public interface PresetDAO
{
	Preset get(int id);

	Preset get(String name);

	List<Preset> get();
}
