package myqueueserver.Users;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Nikos Siatras
 */
public class User implements Serializable
{

    private String fName, fPassword;
    private ArrayList<EUserPermissions> fPermissions;
    private HashMap<String, ArrayList<EUserQueuePermissions>> fQueuePermissions;

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
        fQueuePermissions = new HashMap<>();
    }

    public boolean HasPermission(EUserPermissions permission)
    {
        return fPermissions.contains(permission);
    }

    public boolean HasPermissionForQueue(String queueName, EUserQueuePermissions permission)
    {
        for (String s : fQueuePermissions.keySet())
        {
            if (s.equalsIgnoreCase(queueName))
            {
                return fQueuePermissions.get(s).contains(permission);
            }
        }
        return false;
    }

    public boolean CanConnectToQueue(String queueName)
    {
        boolean hasPermissionsForQueue = false;
        for (String s : fQueuePermissions.keySet())
        {
            if (s.equalsIgnoreCase(queueName))
            {
                hasPermissionsForQueue = true;
                break;
            }
        }
        return fPermissions.contains(EUserPermissions.All) || hasPermissionsForQueue;
    }

    public void DropQueue(String queueName)
    {
        HashMap<String, ArrayList<EUserQueuePermissions>> newPermissions = new HashMap<>();
        for (String s : fQueuePermissions.keySet())
        {
            if (!s.equalsIgnoreCase(queueName))
            {
                newPermissions.put(s, fQueuePermissions.get(s));
            }
        }
        fQueuePermissions = newPermissions;
    }

    /**
     * Check if the user has the required permission to create a new Queue
     *
     * @return true or false
     */
    public boolean CanCreateNewQueues()
    {
        return fPermissions.contains(EUserPermissions.CreateQueues) || fPermissions.contains(EUserPermissions.All);
    }

    /**
     * Check if the user has the required permission to create a new User
     *
     * @return true or false
     */
    public boolean CanCreateNewUsers()
    {
        return fPermissions.contains(EUserPermissions.CreateQueues) || fPermissions.contains(EUserPermissions.All);
    }

    /**
     * Check if the user has the required permission to drop a Queue
     *
     * @return true or false
     */
    public boolean CanDropQueues()
    {
        return fPermissions.contains(EUserPermissions.DropQueues) || fPermissions.contains(EUserPermissions.All);
    }

    /**
     * Check if the user has the required permission to drop a user
     *
     * @return true or false
     */
    public boolean CanDropUsers()
    {
        return fPermissions.contains(EUserPermissions.DropUsers) || fPermissions.contains(EUserPermissions.All);
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

    public ArrayList<EUserPermissions> getPermissions()
    {
        return fPermissions;
    }

    public void setPermissions(ArrayList<EUserPermissions> permissions)
    {
        fPermissions = permissions;
    }

    public HashMap<String, ArrayList<EUserQueuePermissions>> getQueuePermissions()
    {
        return fQueuePermissions;
    }

    public void setQueuePermissions(HashMap<String, ArrayList<EUserQueuePermissions>> permissions)
    {
        fQueuePermissions = permissions;
    }
}
