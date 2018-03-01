package tvestergaard.cupcakes.database.bottoms;

import java.util.Objects;

public final class Bottom
{

    private final int    id;
    private final String name;
    private final String description;
    private final int    price;

    public Bottom(int id, String name, String description, int price)
    {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public int getId()
    {
        return this.id;
    }

    public String getName()
    {
        return this.name;
    }

    public String getDescription()
    {
        return this.description;
    }

    public int getPrice()
    {
        return this.price;
    }

    public String getFormattedPrice()
    {
        int cents = price % 100;
        int dollars = (price - cents) / 100;

        return dollars + "." + (cents < 9 ? "0" + cents : cents);
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
