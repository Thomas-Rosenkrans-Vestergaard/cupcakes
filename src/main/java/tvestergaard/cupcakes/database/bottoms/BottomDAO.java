package tvestergaard.cupcakes.database.bottoms;

import java.util.List;

public interface BottomDAO
{
	Bottom get(int id);

	Bottom get(String name);

	List<Bottom> get();
}
