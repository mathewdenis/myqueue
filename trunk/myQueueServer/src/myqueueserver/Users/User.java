package myqueueserver.Users;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Nikos Siatras
 */
public class User implements Serializable
{

    private String fName, fPassword;
    private ArrayList<UserPermissions> fPermissions;

    /**
     * Initialize a new user instance
     *
     * @param name is the user's name
     * @param password is the user's password
     */
    public User(String name, String password)
    {
        fName = name;
        fPassword = password;
        fPermissions = new ArrayList<>();
    }

    public boolean HasPermission(UserPermissions permission)
    {
        return fPermissions.contains(permission);
    }

    /**
     * Check if the user has the required permission to create a new Queue
     *
     * @return true or false
     */
    public boolean CanCreateNewQueues()
    {
        return fPermissions.contains(UserPermissions.CreateQueues) || fPermissions.contains(UserPermissions.All);
    }

    /**
     * Check if the user has the required permission to create a new User
     *
     * @return true or false
     */
    public boolean CanCreateNewUsers()
    {
        return fPermissions.contains(UserPermissions.CreateQueues) || fPermissions.contains(UserPermissions.All);
    }

    public void setName(String name)
    {
        fName = name;
    }

    public String getName()
    {
        return fName;
    }

    public void setPassword(String password)
    {
        fPassword = password;
    }

    public String getPassword()
    {
        return fPassword;
    }

    public ArrayList<UserPermissions> getPermissions()
    {
        return fPermissions;
    }

    public void setPermissions(ArrayList<UserPermissions> permissions)
    {
        fPermissions = permissions;
    }
}
