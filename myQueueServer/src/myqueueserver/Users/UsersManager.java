package myqueueserver.Users;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import myqueueserver.File.FileManager;
import myqueueserver.Serialization.Serializer;

/**
 *
 * @author Nikos Siatras
 */
public class UsersManager implements Serializable
{

    private static String fUsersManagerSaveLocation = "UsersManager.dat";
    public static ArrayList<User> fUsers;
    private static final Object fUsersLock = new Object();

    public UsersManager()
    {
    }

    public static void Initialize() throws FileNotFoundException, IOException, ClassNotFoundException
    {
        synchronized (fUsersLock)
        {
            byte[] bytes = FileManager.ReadFile(fUsersManagerSaveLocation);
            if (bytes != null)
            {
                fUsers = (ArrayList<User>) Serializer.Deserialize(bytes);
            }
            else
            {
                fUsers = new ArrayList<>();
            }

            // Add a default root account
            if (fUsers.isEmpty())
            {
                fUsers = new ArrayList<>();
                User root = new User("root", "pass");
                root.getPermissions().add(EUserPermissions.All);
                fUsers.add(root);

                Save();
            }
        }
    }

    /**
     * Add a new user to the server
     *
     * @param username is the user's name
     * @param password is the user's password
     * @throws IOException
     */
    public static void AddUser(String username, String password) throws IOException
    {
        synchronized (fUsersLock)
        {
            boolean userExists = false;
            for (User u : fUsers)
            {
                if (u.getName().equalsIgnoreCase(username))
                {
                    userExists = true;
                    break;
                }
            }

            if (!userExists)
            {
                User newUser = new User(username, password);
                fUsers.add(newUser);
                Save();
            }
        }
    }

    /**
     * Remove a user
     *
     * @param username is the user's name to remove
     * @throws IOException
     */
    public static void DropUser(String username) throws IOException
    {
        synchronized (fUsersLock)
        {
            int indexToRemove = -1;

            for (int i = 0; i < fUsers.size(); i++)
            {
                if (fUsers.get(i).getName().equals(username))
                {
                    indexToRemove = i;
                    break;
                }
            }

            if (indexToRemove > -1)
            {
                fUsers.remove(indexToRemove);
                Save();
            }
        }
    }

    /**
     * Give a permission to user
     *
     * @param permission
     * @param username is the user's name to give the permission
     * @throws IOException
     */
    public static void GivePermissionToUser(EUserPermissions permission, String username) throws IOException
    {
        User u = getUser(username);
        if (u != null)
        {
            if (!u.HasPermission(permission))
            {
                u.getPermissions().add(permission);
            }
        }
        UpdateUser(u);
        Save();
    }

    /**
     * Take a permission from a user
     *
     * @param permission
     * @param username is the user's name to take the permission
     * @throws IOException
     */
    public static void TakePermissionFromUser(EUserPermissions permission, String username) throws IOException
    {
        User u = getUser(username);
        if (u != null)
        {
            if (u.HasPermission(permission))
            {
                u.getPermissions().remove(permission);
            }
        }
        UpdateUser(u);
        Save();
    }

    /**
     * Get a users from his username
     *
     * @param username
     * @return
     */
    public static User getUser(String username)
    {
        for (User u : UsersManager.fUsers)
        {
            if (u.getName().equalsIgnoreCase(username))
            {
                return u;
            }
        }
        return null;
    }

    public static ArrayList<User> getUsers()
    {
        return fUsers;
    }

    public static void DropQueueFromAllUsers(String queueName)
    {
        for (User u : UsersManager.fUsers)
        {
            u.DropQueue(queueName);
        }
    }

    public static void UpdateUser(User user) throws IOException
    {
        for (User u : fUsers)
        {
            if (u.getName().equals(user.getName()))
            {
                u.setPassword(user.getPassword());
                u.setPermissions(user.getPermissions());
                u.setQueuePermissions(user.getQueuePermissions());
            }
        }

        Save();
    }

    public static void Save() throws IOException
    {
        synchronized (fUsersLock)
        {
            byte[] bytes = Serializer.Serialize(fUsers);
            FileManager.WriteFile(bytes, fUsersManagerSaveLocation);
        }
    }
}
