package myqueueserver.Queue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Queue;
import myqueueserver.File.FileManager;
import myqueueserver.Serializations.Serializer;

/**
 *
 * @author Nikos Siatras
 */
public class QueueManager
{

    private static ArrayList<Queue> fQueues;
    private static final Object fQueueManagerLock = new Object();

    public QueueManager()
    {
    }

    public static void Initialize() throws FileNotFoundException, IOException, ClassNotFoundException
    {
        synchronized (fQueueManagerLock)
        {
            byte[] bytes = FileManager.ReadFile("Queues.dat");
            if (bytes != null)
            {
                fQueues = (ArrayList<Queue>) Serializer.Deserialize(bytes);
            }
            else
            {
                fQueues = new ArrayList<>();
            }
        }
    }

    public static void Save() throws IOException
    {
        synchronized (fQueueManagerLock)
        {
            byte[] bytes = Serializer.Serialize(fQueues);
            FileManager.WriteFile(bytes, "Queues.dat");
        }
    }

    public static ArrayList<Queue> getQueues()
    {
        synchronized (fQueueManagerLock)
        {
            return fQueues;
        }
    }
}
