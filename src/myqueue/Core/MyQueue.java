package myqueue.Core;

import Extasys.DataFrame;
import Extasys.Network.TCP.Server.Listener.TCPClientConnection;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Hashtable;
import myqueue.Core.StorageEngines.StorageEngine;

/**
 *
 * @author Nikos Siatras
 */
public class MyQueue extends Extasys.Network.TCP.Server.ExtasysTCPServer
{

    private String fName,  fDescription;
    private StorageEngine fEngine;
    private int fCorePoolsSize,  fMaxPoolSize;
    private boolean fRunning = false;
    private String fSplitter = "#!" + String.valueOf(((char) 2)) + "!#";

    public MyQueue(String name, String description, StorageEngine engine, int corePoolSize, int maxPoolSize)
    {
        super(name, description, corePoolSize, maxPoolSize);
        fName = name;
        fDescription = description;
        fEngine = engine;
        fCorePoolsSize = corePoolSize;
        fMaxPoolSize = maxPoolSize;
    }

    @Override
    public void Start() throws IOException, Exception
    {
        super.Start();
        fEngine.StartEngine();
        fRunning = true;
    }

    @Override
    public void Stop()
    {
        super.Stop();
        fRunning = false;
    }

    @Override
    public void OnClientConnect(TCPClientConnection client)
    {
    }

    @Override
    public void OnClientDisconnect(TCPClientConnection client)
    {
    }

    @Override
    public void OnDataReceive(TCPClientConnection client, DataFrame data)
    {
        try
        {
            String commandIDStr = new String(data.getBytes(), 0, 1);
            int commandID = Integer.valueOf(commandIDStr);

            byte[] messageBytes = new byte[data.getLength() - 1];

            switch (commandID)
            {
                case 1: // Enqueue.
                    try
                    {
                        System.arraycopy(data.getBytes(), 1, messageBytes, 0, data.getLength() - 1);
                        String enqueuedMesageID = fEngine.Enqueue(messageBytes);
                        if (enqueuedMesageID != null)
                        {
                            client.SendData("0" + fSplitter); // Message enqueued successfully!
                            // Inform all clients that the message has been enqueued.
                            ReplyToAll("3" + enqueuedMesageID + fSplitter);
                        }
                        else
                        {
                            client.SendData("1" + fSplitter); // An error occured during during the message enqueue proccess.
                        }
                    }
                    catch (Exception ex)
                    {
                        client.SendData("1" + fSplitter); // An error occured during during the message enqueue proccess.
                    }
                    break;

                case 2: // Peek.
                    byte[] peekedBytes = fEngine.Peek();
                    String peekedMessage = "";
                    if (peekedBytes != null)
                    {
                        peekedMessage = new String(peekedBytes);
                    }
                    client.SendData("2" + peekedMessage + fSplitter);
                    break;

                case 3: // Dequeue.
                    byte[] dequeuedBytes = fEngine.Dequeue();
                    String dequeuedMessage = "";
                    if (dequeuedBytes != null)
                    {
                        dequeuedMessage = new String(dequeuedBytes);
                    }
                    client.SendData("2" + dequeuedMessage + fSplitter);
                    break;

                case 4: // Request message.
                    String messageID = new String(data.getBytes(), 1, data.getLength() - 1);
                    byte[] readedMessageBytes = fEngine.GetMessageByID(messageID);
                    String readedMessage = "";
                    if (messageBytes != null)
                    {
                        readedMessage = new String(readedMessageBytes);
                    }
                    client.SendData("4" + readedMessage + fSplitter);
                    break;
            }
        }
        catch (Exception ex)
        {
            System.err.println(ex.getMessage());
            try
            {
                client.SendData("9" + fSplitter); // A fatal error occured.
            }
            catch (Exception fatalException)
            {
            }
        }
    }

    // Clear all message queue messages from disk.
    public void Clear()
    {
        File dir = new File(fEngine.getLocation());
        File[] files = dir.listFiles();

        // This filter only returns files.
        FileFilter fileFilter = new FileFilter()
        {

            @Override
            public boolean accept(File file)
            {
                return !file.isDirectory() && file.getName().endsWith("mqf");
            }
        };
        files = dir.listFiles(fileFilter);

        for (File file : files)
        {
            try
            {
                file.delete();
            }
            catch (Exception ex)
            {
            }
        }

        fEngine.StartEngine();
    }

    public StorageEngine getEngine()
    {
        return fEngine;
    }

    public int getCorePoolSize()
    {
        return fCorePoolsSize;
    }

    public int getMaxPoolSize()
    {
        return fMaxPoolSize;
    }

    public boolean isRunning()
    {
        return fRunning;
    }
}
