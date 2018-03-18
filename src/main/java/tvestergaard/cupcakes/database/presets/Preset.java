package tvestergaard.cupcakes.database.presets;

import tvestergaard.cupcakes.database.bottoms.Bottom;
import tvestergaard.cupcakes.database.toppings.Topping;

/**
 * A purchasable predefined bottom and topping pair.
 */
public class Preset
{

    /**
     * The id of the preset.
     */
    private final int id;

    /**
     * The name of the preset.
     */
    private final String name;

    /**
     * The description of the preset.
     */
    private final String description;

    /**
     * The bottom of the preset.
     */
    private final Bottom bottom;

    /**
     * The topping of the preset.
     */
    private final Topping topping;

    /**
     * Creates a new preset entity.
     *
     * @param id          The id of the preset.
     * @param name        The name of the preset.
     * @param description The description of the preset.
     * @param bottom      The bottom of the preset.
     * @param topping     The topping of the preset.
     */
    public Preset(int id, String name, String description, Bottom bottom, Topping topping)
    {
        this.id = id;
        this.name = name;
        this.description = description;
        this.bottom = bottom;
        this.topping = topping;
    }

    /**
     * Returns the id of the preset.
     *
     * @return The id of the preset.
     */
    public int getId()
    {
        return this.id;
    }

    /**
     * Returns the name of the preset.
     *
     * @return The name of the preset.
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * Returns the description of the preset.
     *
     * @return The description of the preset.
     */
    public String getDescription()
    {
        return this.description;
    }

    /**
     * Returns the bottom of the preset.
     *
     * @return The bottom of the preset.
     */
    public Bottom getBottom()
    {
        return this.bottom;
    }

    /**
     * Returns the topping of the preset.
     *
     * @return The topping of the preset.
     */
    public Topping getTopping()
    {
        return this.topping;
    }

    /**
     * Returns the price of the preset.
     *
     * @return The price of the preset.
     */
    public int getPrice()
    {
        return this.bottom.getPrice() + this.topping.getPrice();
    }

    /**
     * Returns the price of the preset formatted as dollars using a period as decimal separator.
     *
     * @return The price of the preset formatted as dollars using a period as decimal separator.
     */
    public String getFormattedPrice()
    {
        int price   = getPrice();
        int cents   = price % 100;
        int dollars = (price - cents) / 100;

        return dollars + "." + (cents < 9 ? "0" + cents : cents);
    }
}
