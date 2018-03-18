package tvestergaard.cupcakes.database.bottoms;

import java.util.Objects;

/**
 * One of the components of a cupcake. The other being the {@link tvestergaard.cupcakes.database.toppings.Topping}.
 */
public final class Bottom
{

    /**
     * The id of the bottom in the database.
     */
    private final int id;

    /**
     * The name of the bottom in the database.
     */
    private final String name;

    /**
     * The description of the bottom in the database.
     */
    private final String description;

    /**
     * The price of the bottom in cents in the database.
     */
    private final int price;

    /**
     * Whether or not the bottom can currently be ordered.
     */
    private final boolean active;

    /**
     * Creates a new bottom.
     *
     * @param id          The id of the bottom.
     * @param name        The name of the bottom.
     * @param description The description of the bottom.
     * @param price       The price of the bottom in cents.
     * @param active      Whether or not the bottom is currently active.
     */
    public Bottom(int id, String name, String description, int price, boolean active)
    {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.active = active;
    }

    /**
     * Returns the id of the bottom.
     *
     * @return The id of the bottom.
     */
    public int getId()
    {
        return this.id;
    }

    /**
     * Returns the name of the bottom.
     *
     * @return The name of the bottom.
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * Returns the description of the bottom.
     *
     * @return The description of the bottom.
     */
    public String getDescription()
    {
        return this.description;
    }

    /**
     * Returns the price of the bottom in cents.
     *
     * @return The price of the bottom in cents.
     */
    public int getPrice()
    {
        return this.price;
    }

    /**
     * Checks if the bottom can currently be ordered.
     *
     * @return {@code true} if the bottom can currently be ordered.
     */
    public boolean isActive()
    {
        return this.active;
    }

    @Override public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Bottom)) return false;
        Bottom bottom = (Bottom) o;
        return id == bottom.id;
    }

    @Override public int hashCode()
    {
        return Objects.hash(id);
    }
}
