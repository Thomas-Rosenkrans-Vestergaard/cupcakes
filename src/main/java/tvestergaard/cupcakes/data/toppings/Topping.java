package tvestergaard.cupcakes.data.toppings;

import java.util.Objects;

/**
 * One of the components of a cupcake. The other being the {@link tvestergaard.cupcakes.data.bottoms.Bottom}.
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
     * Whether or not the topping can currently be ordered.
     */
    private final boolean active;

    /**
     * Creates a new topping.
     *
     * @param id          The id of the topping.
     * @param name        The name of the topping.
     * @param description The description of the topping.
     * @param price       The price of the topping in cents.
     * @param active      Whether or not the topping can currently be ordered.
     */
    public Topping(int id, String name, String description, int price, boolean active)
    {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.active = active;
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

    /**
     * Checks whether or not the topping can currently be ordered.
     *
     * @return {@code true} if the topping can currently be ordered, otherwise {@code false}.
     */
    public boolean isActive()
    {
        return this.active;
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
