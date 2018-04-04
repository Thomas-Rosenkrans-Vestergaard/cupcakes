package tvestergaard.cupcakes.data;

/**
 * Thrown when a generic error occurs during the operation on some data access object.
 */
public abstract class DAOException extends Throwable
{

    /**
     * Returns the cause of the {@link DAOException}. Methods the override this method should narrow the return type.
     *
     * @return The cause of the {@link DAOException}.
     */
    @Override public abstract Exception getCause();
}
