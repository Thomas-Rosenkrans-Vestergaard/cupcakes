package tvestergaard.cupcakes.logic;

import java.util.HashSet;
import java.util.Set;

public class UserUpdateException extends Exception
{

    /**
     * The reasons for the {@link UserUpdateException}.
     */
    private final Set<UserUpdateException.Reason> reasons;

    /**
     * Creates a new {@link UserUpdateException}.
     *
     * @param reasons The reasons for the {@link UserUpdateException}.
     */
    public UserUpdateException(Set<UserUpdateException.Reason> reasons)
    {
        this.reasons = reasons;
    }

    /**
     * Creates a new {@link UserUpdateException}.
     *
     * @param reason The reason for the {@link UserUpdateException}.
     */
    public UserUpdateException(UserUpdateException.Reason reason)
    {
        this.reasons = new HashSet<>();
        this.reasons.add(reason);
    }

    /**
     * Checks if the provided reason is one of the causes of the {@link UserUpdateException}.
     *
     * @param reason The reason to check for.
     * @return {@code true} if the provided reason was one of the causes of the {@link UserUpdateException}.
     */
    public boolean has(UserUpdateException.Reason reason)
    {
        return this.reasons.contains(reason);
    }

    public enum Reason
    {
        USERNAME_SHORTER_THAN_3,
        USERNAME_TAKEN,
        EMAIL_FORMAT,
        EMAIL_TAKEN,
        PASSWORD_SHORTER_THAN_4
    }
}
