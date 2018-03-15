package tvestergaard.cupcakes.database.users;

import org.apache.commons.validator.routines.EmailValidator;
import tvestergaard.cupcakes.Notification;
import tvestergaard.cupcakes.Notifications;

import javax.servlet.http.HttpServletRequest;

public class UserRequestInputValidator
{

    private String usernameParameter       = "username";
    private String emailParameter          = "email";
    private String passwordParameter       = "password";
    private String passwordRepeatParameter = "repeat-password";
    private String balanceParameter        = "balance";
    private String roleParameter           = "role";

    private HttpServletRequest request;
    private Notification.Level errorLevel = Notification.Level.ERROR;


    public UserRequestInputValidator(HttpServletRequest request)
    {
        this.request = request;
    }

    public boolean username(UserDAO userDAO)
    {
        return usernameWasSent() && usernameLength() && usernameAvailable(userDAO);
    }

    public boolean username(UserDAO userDAO, Notifications notifications, String[] errors)
    {
        return usernameWasSent(notifications, errors[0]) &&
                usernameLength(notifications, errors[1]) &&
                usernameAvailable(userDAO, notifications, errors[2]);
    }

    public boolean usernameWasSent()
    {
        String username = request.getParameter(usernameParameter);

        return username != null && username.length() > 0;
    }

    public boolean usernameWasSent(Notifications notifications, String error)
    {
        if (!usernameWasSent()) {
            notifications.notify(Notification.of(error, errorLevel));
            return false;
        }

        return true;
    }

    public boolean usernameLength()
    {
        String username = request.getParameter(usernameParameter);

        return username == null ? false : username.length() > 2;
    }

    public boolean usernameLength(Notifications notifications, String error)
    {
        if (!usernameLength()) {
            notifications.notify(Notification.of(error, errorLevel));
            return false;
        }

        return true;
    }

    public boolean usernameAvailable(UserDAO userDAO)
    {
        String username = request.getParameter(usernameParameter);

        return userDAO == null ? false : userDAO.getFromUsername(username) == null;
    }

    public boolean usernameAvailable(UserDAO userDAO, Notifications notifications, String error)
    {
        if (!usernameAvailable(userDAO)) {
            notifications.notify(Notification.of(error, errorLevel));
            return false;
        }

        return true;
    }

    public boolean email(UserDAO userDAO)
    {
        return emailWasSent() && emailFormat() && emailAvailable(userDAO);
    }

    public boolean email(UserDAO userDAO, Notifications notifications, String[] errors)
    {
        return emailWasSent(notifications, errors[0]) &&
                emailFormat(notifications, errors[1]) &&
                emailAvailable(userDAO, notifications, errors[2]);
    }

    public boolean emailWasSent()
    {
        String email = request.getParameter(emailParameter);

        return email != null && email.length() > 0;
    }

    public boolean emailWasSent(Notifications notifications, String error)
    {
        if (!emailWasSent()) {
            notifications.notify(Notification.of(error, errorLevel));
            return false;
        }

        return true;
    }

    public boolean emailFormat()
    {
        String email = request.getParameter(emailParameter);

        return email == null ? false : EmailValidator.getInstance().isValid(email);
    }

    public boolean emailFormat(Notifications notifications, String error)
    {
        if (!emailFormat()) {
            notifications.notify(Notification.of(error, errorLevel));
            return false;
        }

        return true;
    }

    public boolean emailAvailable(UserDAO userDAO)
    {
        String email = request.getParameter(emailParameter);

        return email == null ? false : userDAO.getFromEmail(email) == null;
    }

    public boolean emailAvailable(UserDAO userDAO, Notifications notifications, String error)
    {
        if (!emailAvailable(userDAO)) {
            notifications.notify(Notification.of(error, errorLevel));
            return false;
        }

        return true;
    }

    public boolean password()
    {
        return passwordWasSent() && passwordLength() && passwordMatchesRepeat();
    }

    public boolean password(Notifications notifications, String[] errors)
    {
        return passwordWasSent(notifications, errors[0]) &&
                passwordLength(notifications, errors[1]) &&
                passwordMatchesRepeat(notifications, errors[2]);
    }

    public boolean passwordWasSent()
    {
        String password = request.getParameter(passwordParameter);

        return password != null && password.length() > 0;
    }

    public boolean passwordWasSent(Notifications notifications, String error)
    {
        if (!passwordWasSent()) {
            notifications.notify(Notification.of(error, errorLevel));
            return false;
        }

        return true;
    }

    public boolean passwordLength()
    {
        String password = request.getParameter(passwordParameter);

        return password == null ? false : password.length() > 7;
    }

    public boolean passwordLength(Notifications notifications, String error)
    {
        if (!passwordLength()) {
            notifications.notify(Notification.of(error, errorLevel));
            return false;
        }

        return true;
    }

    public boolean passwordMatchesRepeat()
    {
        String password       = request.getParameter(passwordParameter);
        String passwordRepeat = request.getParameter(passwordRepeatParameter);

        if (password == null)
            return passwordRepeat == null;

        return password.equals(passwordRepeat);
    }

    public boolean passwordMatchesRepeat(Notifications notifications, String error)
    {
        if (!passwordMatchesRepeat()) {
            notifications.notify(Notification.of(error, errorLevel));
            return false;
        }

        return true;
    }

    public boolean balance()
    {
        return balanceWasSent() && balanceFormat();
    }

    public boolean balance(Notifications notifications, String[] errors)
    {
        return balanceWasSent(notifications, errors[0]) &&
                balanceFormat(notifications, errors[1]);
    }

    public boolean balanceWasSent()
    {
        String balance = request.getParameter(balanceParameter);

        return balance != null && balance.length() > 0;
    }

    public boolean balanceWasSent(Notifications notifications, String error)
    {
        if (!balanceWasSent()) {
            notifications.notify(Notification.of(error, errorLevel));
            return false;
        }

        return true;
    }

    public boolean balanceFormat()
    {
        String balance = request.getParameter(balanceParameter);

        try {
            Integer.parseInt(balance);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean balanceFormat(Notifications notifications, String error)
    {
        if (!balanceFormat()) {
            notifications.notify(Notification.of(error, errorLevel));
            return false;
        }

        return true;
    }

    public boolean role()
    {
        return roleWasSent() && roleFormat();
    }

    public boolean role(Notifications notifications, String[] errors)
    {
        return roleWasSent(notifications, errors[0])
                && roleFormat(notifications, errors[1]);
    }

    public boolean roleWasSent()
    {
        String role = request.getParameter(roleParameter);

        return role != null && role.length() > 0;
    }

    public boolean roleWasSent(Notifications notifications, String error)
    {
        if (!roleWasSent()) {
            notifications.notify(Notification.of(error, errorLevel));
            return false;
        }

        return true;
    }

    public boolean roleFormat()
    {
        String type = request.getParameter(roleParameter);

        if (type == null)
            return false;

        return type.equals("0") || type.equals("1");
    }

    public boolean roleFormat(Notifications notifications, String error)
    {
        if (!roleFormat()) {
            notifications.notify(Notification.of(error, errorLevel));
            return false;
        }

        return true;
    }

    public String getUsername()
    {
        return request.getParameter(usernameParameter);
    }

    public String getEmail()
    {
        return request.getParameter(emailParameter);
    }

    public String getPassword()
    {
        return request.getParameter(passwordParameter);
    }

    public User.Role getRole()
    {
        return User.Role.code(Integer.parseInt(request.getParameter(roleParameter)));
    }

    public int getBalance()
    {
        return Integer.parseInt(request.getParameter(balanceParameter));
    }

    public void setUsernameParameter(String usernameParameter)
    {
        this.usernameParameter = usernameParameter;
    }

    public void setEmailParameter(String emailParameter)
    {
        this.emailParameter = emailParameter;
    }

    public void setPasswordParameter(String passwordParameter)
    {
        this.passwordParameter = passwordParameter;
    }

    public void setPasswordRepeatParameter(String passwordRepeatParameter)
    {
        this.passwordRepeatParameter = passwordRepeatParameter;
    }

    public void setBalanceParameter(String balanceParameter)
    {
        this.balanceParameter = balanceParameter;
    }

    public void setAdministratorParameter(String administratorParameter)
    {
        this.roleParameter = administratorParameter;
    }

    public void setErrorLevel(Notification.Level errorLevel)
    {
        this.errorLevel = errorLevel;
    }
}
