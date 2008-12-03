package myqueue.Core;

import myqueue.Core.Serializable.MyQueueSerializable;
import Extasys.Network.TCP.Server.Listener.TCPListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import myqueue.Core.Serializable.TCPListenerSerializable;
import myqueue.Core.StorageEngines.HDEngine;
import myqueue.Core.StorageEngines.StorageEngine;

/**
 *
 * @author Nikos Siatras
 */
public class QueueManager
{

    private static Hashtable<String, MyQueue> fQueues = new Hashtable<String, MyQueue>();

    public QueueManager()
    {
    }

    public static void CreateNewQueue(String name, String description, String location, int corePoolSize, int maxPoolSize, ArrayList listeners) throws Exception
    {
        name = name.trim();
        description = description.trim();
        location = location.trim();

        // Check if name is unique.
        if (fQueues.containsKey(name))
        {
            throw new Exception("Please select an other name for this Queue.\nThis name is allready in use.");
        }

        if (name.equals(""))
        {
            throw new Exception("Please select a name for this queue.");
        }

        if (corePoolSize < 10)
        {
            throw new Exception("Core pool size must be grater than 10.");
        }

        if (maxPoolSize < corePoolSize)
        {
            throw new Exception("Max pool size must be grater than core poolsize.");
        }

        if (listeners == null || listeners.size() == 0)
        {
            throw new Exception("Please add one or more listeners to this queue.");
        }

        try
        {
            File tmpFile = new File(location);
            if (!tmpFile.exists())
            {
                throw new Exception("Please select a valid location for this queue.");
            }
        }
        catch (Exception ex)
        {
            throw new Exception("Please select a valid location for this queue.");
        }

        HDEngine engine = new HDEngine(location);
        MyQueue queue = new MyQueue(name, description, engine, corePoolSize, maxPoolSize);

        for (int i = 0; i < listeners.size(); i++)
        {
            TCPListener listener = (TCPListener) listeners.get(i);
            queue.AddListener(listener.getName(), listener.getIPAddress(), listener.getPort(), listener.getMaxConnections(), listener.getReadBufferSize(), listener.getConnectionTimeOut(), 100, listener.getMessageSplitter());
        }

        fQueues.put(name, queue);
        StartQueue(name);
        Save();
    }

    public static void StartQueue(String name) throws IOException, Exception
    {
        if (fQueues.containsKey(name))
        {
            ((MyQueue) fQueues.get(name)).Start();
        }
        else
        {
            throw new Exception("Queue " + name + " does not exist.");
        }
    }

    public static void StopQueue(String name) throws Exception
    {
        if (fQueues.containsKey(name))
        {
            ((MyQueue) fQueues.get(name)).Stop();
        }
        else
        {
            throw new Exception("Queue " + name + " does not exist.");
        }
    }

    public static void DeleteQueue(String name) throws Exception
    {
        if (fQueues.containsKey(name))
        {
            StopQueue(name);
            fQueues.remove(name);
        }
        else
        {
            throw new Exception("Queue " + name + " does not exist.");
        }
    }

    private static void Save()
    {
        // Check if file QueueData exists.
        File tmpFolder = new File("QueueData");
        if (!tmpFolder.exists())
        {
            tmpFolder.mkdirs();
        }

        for (MyQueue queue : fQueues.values())
        {
            MyQueueSerializable tmp = new MyQueueSerializable();

            tmp.Name = queue.getName();
            tmp.Description = queue.getDescription();
            tmp.Location = queue.getEngine().getLocation();
            if (queue.getEngine().getClass().equals(new HDEngine("").getClass()))
            {
                tmp.Engine = EStorageEngine.HDEngine;
            }

            // Listeners.
            for (int i = 0; i < queue.getListeners().size(); i++)
            {
                TCPListenerSerializable tmpListener = new TCPListenerSerializable();
                TCPListener listener = (TCPListener) queue.getListeners().get(i);
                tmpListener.IPAddress = listener.getIPAddress();
                tmpListener.MaxConnections = listener.getMaxConnections();
                tmpListener.Port = listener.getPort();

                tmp.ConnectionsTimeOut = listener.getConnectionTimeOut();
                tmp.TCPListeners.add(tmpListener);
            }

            FileOutputStream fos = null;
            ObjectOutputStream out = null;
            try
            {
                fos = new FileOutputStream("QueueData\\" + tmp.Name + ".queue");
                out = new ObjectOutputStream(fos);
                out.writeObject(tmp);
                out.close();
            }
            catch (IOException ex)
            {
                System.err.println(ex.getMessage());
            }
        }
    }

    public static void Load()
    {
    }
}
