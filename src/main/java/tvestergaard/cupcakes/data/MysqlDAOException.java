package tvestergaard.cupcakes.data;

import java.sql.SQLException;

/**
 * Thrown when a generic error occurs during the operation on some MySQL data access object.
 */
public class MysqlDAOException extends DAOException
{

    /**
     * The cause of the {@link MysqlDAOException}.
     */
    private final SQLException cause;

    /**
     * Creates a new {@link MysqlDAOException}.
     *
     * @param cause The cause of the {@link MysqlDAOException}.
     */
    public MysqlDAOException(SQLException cause)
    {
        this.cause = cause;
    }

    /**
     * Returns the cause of the {@link MysqlDAOException}.
     *
     * @return The cause of the {@link MysqlDAOException}.
     */
    @Override public SQLException getCause()
    {
        return cause;
    }
}
