package myqueueserver.Authentication;

import myqueueserver.Users.User;
import myqueueserver.Users.UsersManager;

/**
 *
 * @author Nikos Siatras
 */
public class UserAuthenticationManager
{

    public UserAuthenticationManager()
    {
    }

    /**
     * Check if user with the given username and password exists.
     *
     * @param username
     * @param password
     * @return true or false
     */
    public static boolean AuthenticateUser(String username, String password)
    {
        for (User u : UsersManager.fUsers)
        {
            if (u.getName().equals("username") && u.getPassword().equals(password))
            {
                return true;
            }
        }
        return false;
    }
}
