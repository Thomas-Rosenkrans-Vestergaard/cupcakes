package tvestergaard.cupcakes.database.bottoms;

import tvestergaard.cupcakes.database.toppings.Topping;

import java.util.List;

public interface BottomDAO
{
    Bottom get(int id);

    Bottom get(String name);

    List<Bottom> get();

    Bottom create(String name, String description, int price);

    Bottom update(int id, String name, String description, int price);

    default boolean delete(Topping topping)
    {
        return delete(topping.getId());
    }

    boolean delete(int id);
}
