package myqueue.Core;

import Extasys.DataFrame;
import Extasys.Network.TCP.Server.Listener.TCPClientConnection;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
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
                        if (fEngine.Enqueue(messageBytes))
                        {
                            client.SendData("0" + fSplitter); // Message enqueued successfully!
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
