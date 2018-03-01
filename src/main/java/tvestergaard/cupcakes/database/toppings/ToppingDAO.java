package tvestergaard.cupcakes.database.toppings;

import java.util.List;

public interface ToppingDAO
{

    Topping get(int id);

    Topping get(String name);

    List<Topping> get();

    Topping create(String name, String description, int price);

    Topping update(int id, String name, String description, int price);

    default boolean delete(Topping topping)
    {
        return delete(topping.getId());
    }

    boolean delete(int id);
}
