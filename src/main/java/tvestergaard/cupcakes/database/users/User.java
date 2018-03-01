package tvestergaard.cupcakes.database.users;

import java.util.Objects;

public class User
{

    public enum Role
    {
        USER(0),
        ADMINISTRATOR(1),
        OWNER(2);

        public final int code;

        Role(int code)
        {
            this.code = code;
        }

        public int getCode()
        {
            return this.code;
        }

        public boolean is(Role role)
        {
            return code >= role.code;
        }

        private static Role[] roles = Role.values();

        public static Role code(int code)
        {
            return roles[code];
        }
    }

    private final int    id;
    private final String email;
    private final String username;
    private final String password;
    private final int    balance;
    private final Role   role;

    public User(int id, String username, String email, String password, int balance, Role role)
    {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.balance = balance;
        this.role = role;
    }

    public final int getId()
    {
        return this.id;
    }

    public String getEmail()
    {
        return this.email;
    }

    public final String getUsername()
    {
        return this.username;
    }

    public String getPassword()
    {
        return this.password;
    }

    public final int getBalance()
    {
        return this.balance;
    }

    public Role getRole()
    {
        return this.role;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return id == user.id &&
                balance == user.balance &&
                Objects.equals(email, user.email) &&
                Objects.equals(username, user.username);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id);
    }

    @Override
    public String toString()
    {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", balance=" + balance +
                ", role=" + role +
                '}';
    }
}
