package MyQueue.Core;

import Extasys.Network.TCP.Server.Listener.TCPListener;
import MyQueue.MyQueue;
import MyQueue.StorageEngine.HDEngine;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import net.sf.sojo.interchange.json.JsonSerializer;

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

    public static void CreateNewQueue(String name, String description, String location, int corePoolSize, int maxPoolSize, ArrayList listeners) throws Exception
    {
        name = name.trim();
        description = description.trim();
        location = location.trim();

        // Check if name is unique.
        if (fQueues.containsKey(name))
        {
            throw new Exception("Please an other name for this Queue.\nThis name is allready in use.");
        }

        if (name.equals(""))
        {
            throw new Exception("Please select a name for thus queue.");
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

        HDEngine engine = new HDEngine(location);
        MyQueue queue = new MyQueue(name, description, engine, corePoolSize, maxPoolSize);

        for (int i = 0; i < listeners.size(); i++)
        {
            TCPListener listener = (TCPListener) listeners.get(i);
            queue.AddListener(listener.getName(), listener.getIPAddress(), listener.getPort(), listener.getMaxConnections(), listener.getReadBufferSize(), listener.getConnectionTimeOut(), 100, listener.getMessageSplitter());
        }

        fQueues.put(name, queue);
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

        ArrayList dataToSave = new ArrayList();

        for (Enumeration e = fQueues.keys(); e.hasMoreElements();)
        {
            try
            {
                Hashtable queue = new Hashtable();

                MyQueue tmp = (MyQueue) fQueues.get(e.nextElement());

                queue.put("name", tmp.getName());
                queue.put("description", tmp.getDescription() + " ");
                queue.put("corepoolsize", tmp.getCorePoolSize());
                queue.put("maxpoolsize", tmp.getMaxPoolSize());
                queue.put("location", tmp.getEngine().getLocation());

                // Listeners.
                ArrayList listeners = new ArrayList();
                for (int i = 0; i < tmp.getListeners().size(); i++)
                {
                    Hashtable listenerHashtable = new Hashtable();
                    TCPListener listener = (TCPListener) tmp.getListeners().get(i);

                    InetAddress ip = listener.getIPAddress();
                    int port = listener.getPort();
                    int maxConnections = listener.getMaxConnections();

                    listenerHashtable.put("ip", ip.toString());
                    listenerHashtable.put("port", port);
                    listenerHashtable.put("maxconnections", maxConnections);

                    listeners.add(listenerHashtable);
                }
                queue.put("listeners", listeners);
                dataToSave.add(queue);
            }
            catch (Exception ex)
            {
                System.err.println(ex.getMessage());
            }
        }

        JsonSerializer serializer = new JsonSerializer();
        Object result = serializer.serialize(dataToSave);
        System.out.println(result.toString());

        // Save data.
        try
        {
            FileWriter file = new FileWriter("QueueManager.dat");
            BufferedWriter out = new BufferedWriter(file);
            out.write(result.toString());
            out.close();
        }
        catch (IOException e)
        {
            System.err.println(e);
        }
    }

    static void Load()
    {
        
    }
}
