package tvestergaard.cupcakes.logic;

/**
 * Thrown by the {@link ShoppingCartFacade} when a bottom with the provided id does not exist in the application.
 */
public class NoSuchBottomException extends Exception
{

    /**
     * The provided id that caused the exception.
     */
    private final int bottom;

    /**
     * Creates a new {@link NoSuchBottomException}.
     *
     * @param bottom The provided id that caused the exception.
     */
    public NoSuchBottomException(int bottom)
    {
        this.bottom = bottom;
    }

    /**
     * Returns the provided id that caused the exception.
     *
     * @return The provided id that caused the exception.
     */
    public int getBottom()
    {
        return this.bottom;
    }
}
