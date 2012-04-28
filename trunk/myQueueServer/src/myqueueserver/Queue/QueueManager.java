package myqueueserver.Queue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import myqueueserver.File.FileManager;
import myqueueserver.Log.LogMessageType;
import myqueueserver.Log.ServerLog;
import myqueueserver.Serialization.Serializer;

/**
 *
 * @author Nikos Siatras
 */
public class QueueManager
{

    private static String fQueueManagerSaveLocation = "Queues.dat";
    private static ArrayList<myQueue> fQueues;
    private static final Object fQueueManagerLock = new Object();

    public QueueManager()
    {
    }

    public static void Initialize() throws FileNotFoundException, IOException, ClassNotFoundException
    {
        synchronized (fQueueManagerLock)
        {
            byte[] bytes = FileManager.ReadFile(fQueueManagerSaveLocation);
            if (bytes != null)
            {
                fQueues = (ArrayList<myQueue>) Serializer.Deserialize(bytes);
            }
            else
            {
                fQueues = new ArrayList<>();
            }
        }
    }

    /**
     * Create a new myQueue
     *
     * @param name is the Queue's name
     */
    public static boolean CreateQueue(String name) throws IOException
    {
        synchronized (fQueueManagerLock)
        {
            boolean queueExists = false;
            for (myQueue q : fQueues)
            {
                if (q.getName().equals(name))
                {
                    queueExists = true;
                    break;
                }
            }

            if (!queueExists)
            {
                myQueue q = new myQueue(name);
                fQueues.add(q);
                Save();
                ServerLog.WriteToLog("Queue " + name + " created", LogMessageType.Information);
                return true;
            }

            return false;
        }
    }

    /**
     * Drop myQueue
     *
     * @param name is the name of the queue to drop
     */
    public static void DropQueue(String name) throws IOException
    {
        synchronized (fQueueManagerLock)
        {
            int indexToRemove = -1;
            for (int i = 0; i < fQueues.size(); i++)
            {
                if (fQueues.get(i).getName().equals(name))
                {
                    indexToRemove = i;
                    break;
                }
            }

            if (indexToRemove > -1)
            {
                fQueues.remove(indexToRemove);
                Save();
            }

        }
    }

    /**
     * Check if a Queue with the given name exists
     *
     * @param name
     * @return
     */
    public static boolean QueueExists(String name)
    {
        for (myQueue q : fQueues)
        {
            if (q.getName().equals(name))
            {
                return true;
            }
        }
        return false;
    }

    public static void Save() throws IOException
    {
        synchronized (fQueueManagerLock)
        {
            byte[] bytes = Serializer.Serialize(fQueues);
            FileManager.WriteFile(bytes, fQueueManagerSaveLocation);
        }
    }

    public static ArrayList<myQueue> getQueues()
    {
        synchronized (fQueueManagerLock)
        {
            return fQueues;
        }
    }
}
