package tvestergaard.cupcakes.database.toppings;

import java.util.List;

public interface ToppingDAO
{

	Topping get(int id);
	Topping get(String name);
	List<Topping> get();
}
