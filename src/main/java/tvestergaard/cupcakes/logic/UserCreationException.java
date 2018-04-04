package tvestergaard.cupcakes.logic;

import java.util.HashSet;
import java.util.Set;

public class UserCreationException extends Exception
{

    /**
     * The reasons for the {@link UserCreationException}.
     */
    private final Set<Reason> reasons;

    /**
     * Creates a new {@link UserCreationException}.
     *
     * @param reasons The reasons for the {@link UserCreationException}.
     */
    public UserCreationException(Set<Reason> reasons)
    {
        this.reasons = reasons;
    }

    /**
     * Creates a new {@link UserCreationException}.
     *
     * @param reason The reason for the {@link UserCreationException}.
     */
    public UserCreationException(Reason reason)
    {
        this.reasons = new HashSet<>();
        this.reasons.add(reason);
    }

    /**
     * Checks if the provided reason is one of the causes of the {@link UserCreationException}.
     *
     * @param reason The reason to check for.
     * @return {@code true} if the provided reason was one of the causes of the {@link UserCreationException}.
     */
    public boolean has(Reason reason)
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
