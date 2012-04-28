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
    
    public static void Initialize()
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
            if (u.getName().equals(username) && u.getPassword().equals(password))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if user has active permissions for a specific queue
     *
     * @param username is the user's name
     * @param queueName is the queue's name
     * @return true or false
     */
    public static boolean UserHasPermissionsForQueue(String username, String queueName)
    {
        for (User u : UsersManager.fUsers)
        {
            if (u.getName().equals("username"))
            {
                if (u.CanConnectToQueue(queueName))
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }
        }
        return false;
    }
}
