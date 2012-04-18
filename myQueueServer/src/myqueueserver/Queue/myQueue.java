package myqueueserver.Queue;

import java.util.ArrayList;
import myqueueserver.Queue.Engines.QueueEngine;
import myqueueserver.Network.Users.User;

/**
 *
 * @author Nikos Siatras
 */
public class myQueue
{

    private String fName, fSaveLocation;
    private QueueEngine fEngine;
    private ArrayList<User> fUsers;

    private myQueue(String name, String saveLocation, QueueEngine engine)
    {
        fUsers = new ArrayList<>();
        fName = name;
        fSaveLocation = saveLocation;
        fEngine = engine;
    }

    public void setName(String name)
    {
        fName = name;
    }

    public String getName()
    {
        return fName;
    }

    public void setSaveLocation(String location)
    {
        fSaveLocation = location;
    }

    public String getSaveLocation()
    {
        return fSaveLocation;
    }

    public void setEngine(QueueEngine engine)
    {
        fEngine = engine;
    }

    public QueueEngine getEngine()
    {
        return fEngine;
    }

    public ArrayList<User> getUsers()
    {
        return fUsers;
    }
}
