package myqueueserver.Users;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import myqueueserver.File.FileManager;
import myqueueserver.Serializations.Serializer;

/**
 *
 * @author Nikos Siatras
 */
public class UsersManager implements Serializable
{

    private static ArrayList<User> fUsers;

    public UsersManager()
    {
    }

    public static void Initialize() throws FileNotFoundException, IOException, ClassNotFoundException
    {
        byte[] bytes = FileManager.ReadFile("UsersManager.dat");
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
            fUsers.add(root);

            Save();
        }
    }

    public static void Save() throws IOException
    {
        byte[] bytes = Serializer.Serialize(fUsers);
        FileManager.WriteFile(bytes, "UsersManager.dat");
    }
}
