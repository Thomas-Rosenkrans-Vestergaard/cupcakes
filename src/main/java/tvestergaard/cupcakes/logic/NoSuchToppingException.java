package tvestergaard.cupcakes.logic;

/**
 * Thrown by the {@link ShoppingCartFacade} when a topping with the provided id does not exist in the application.
 */
public class NoSuchToppingException extends Exception
{

    /**
     * The provided id that caused the exception.
     */
    private final int topping;

    /**
     * Creates a new {@link tvestergaard.cupcakes.logic.NoSuchToppingException}.
     *
     * @param topping The provided id that caused the exception.
     */
    public NoSuchToppingException(int topping)
    {
        this.topping = topping;
    }

    /**
     * Returns the provided id that caused the exception.
     *
     * @return The provided id that caused the exception.
     */
    public int getTopping()
    {
        return this.topping;
    }
}