package tvestergaard.cupcakes.database.toppings;

import java.util.Objects;

/**
 * Entity representing a topping from a database.
 */
public final class Topping
{

    /**
     * The id of the topping.
     */
    private final int id;

    /**
     * The name of the topping.
     */
    private final String name;

    /**
     * The description of the topping.
     */
    private final String description;

    /**
     * The price of the topping in cents.
     */
    private final int price;

    /**
     * Creates a new topping.
     *
     * @param id          The id of the topping.
     * @param name        The name of the topping.
     * @param description The description of the topping.
     * @param price       The price of the topping in cents.
     */
    public Topping(int id, String name, String description, int price)
    {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    /**
     * Returns the id of the topping.
     *
     * @return The id of the topping.
     */
    public int getId()
    {
        return this.id;
    }

    /**
     * Returns the name of the topping.
     *
     * @return The name of the topping.
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * Returns the description of the topping.
     *
     * @return The description of the topping.
     */
    public String getDescription()
    {
        return this.description;
    }

    /**
     * Returns the price of the topping in cents.
     *
     * @return The price of the topping in cents.
     */
    public int getPrice()
    {
        return this.price;
    }

    @Override public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Topping)) return false;
        Topping topping = (Topping) o;
        return id == topping.id;
    }

    @Override public int hashCode()
    {
        return Objects.hash(id);
    }
}
