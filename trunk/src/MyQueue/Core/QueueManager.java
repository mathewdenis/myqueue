package MyQueue.Core;

import Extasys.Network.TCP.Server.Listener.TCPListener;
import MyQueue.MyQueue;
import MyQueue.StorageEngine.HDEngine;

import java.io.IOException;
import java.util.Hashtable;
import java.util.List;

/**
 *
 * @author Nikos Siatras
 */
public class QueueManager
{

    public static Hashtable fQueues = new Hashtable();

    public QueueManager()
    {
    }

    static void CreateNewQueue(String name, String description, String location, int corePoolSize, int maxPoolSize, List<TCPListener> listeners) throws Exception
    {
        // Check if name is unique.
        if (fQueues.containsKey(name))
        {
            throw new Exception("Please an other name for this Queue.\nThis name allready in use.");
        }

        HDEngine engine = new HDEngine(location);
        MyQueue queue = new MyQueue(name, description, engine, corePoolSize, maxPoolSize);

        for (TCPListener listener : listeners)
        {
            queue.AddListener(listener.getName(), listener.getIPAddress(), listener.getPort(), listener.getMaxConnections(), listener.getReadBufferSize(), listener.getConnectionTimeOut(), 100);
        }

        fQueues.put(name, queue);
    }

    static void StartQueue(String name) throws IOException, Exception
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

    static void StopQueue(String name) throws Exception
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

    static void DeleteQueue(String name) throws Exception
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
    
    
}
