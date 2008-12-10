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

    private StorageEngine fEngine;
    private int fCorePoolsSize,  fMaxPoolSize;
    private boolean fRunning = false;
    private final String fSplitter = "#!" + String.valueOf(((char) 2)) + "!#";
    private int fSplitterLength = 0;
    private byte[] fSplitterBytes = null;

    public MyQueue(String name, String description, StorageEngine engine, int corePoolSize, int maxPoolSize)
    {
        super(name, description, corePoolSize, maxPoolSize);
        fEngine = engine;
        fCorePoolsSize = corePoolSize;
        fMaxPoolSize = maxPoolSize;

        fSplitterLength = fSplitter.length();
        fSplitterBytes = fSplitter.getBytes();
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

            byte[] finalBytes;
            byte[] peekedBytes;
            int peekedBytesLength = 0;

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
                            ReplyToAllExceptSender("3" + enqueuedMesageID + fSplitter, client);
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

                case 2: // Peek (2 - PeekedMessage - Splitter).
                    peekedBytes = fEngine.Peek();
                    peekedBytesLength = peekedBytes == null ? 1 : peekedBytes.length + 1;
                    finalBytes = new byte[peekedBytesLength + fSplitterLength];
                    if (peekedBytes != null)
                    {
                        System.arraycopy(peekedBytes, 0, finalBytes, 1, peekedBytes.length);
                    }
                    finalBytes[0] = (byte) '2';
                    System.arraycopy(fSplitterBytes, 0, finalBytes, peekedBytesLength, fSplitterLength);
                    client.SendData(finalBytes, 0, finalBytes.length);
                    break;

                case 3: // Dequeue (2 - DequeuedMessage - Splitter).
                    peekedBytes = fEngine.Dequeue();
                    peekedBytesLength = peekedBytes == null ? 1 : peekedBytes.length + 1;
                    finalBytes = new byte[peekedBytesLength + fSplitterLength];
                    if (peekedBytes != null)
                    {
                        System.arraycopy(peekedBytes, 0, finalBytes, 1, peekedBytes.length);
                    }
                    finalBytes[0] = (byte) '2';
                    System.arraycopy(fSplitterBytes, 0, finalBytes, peekedBytesLength, fSplitterLength);
                    client.SendData(finalBytes, 0, finalBytes.length);
                    break;

                case 4: // Request message ( 4 - Message - Splitter).
                    String messageID = new String(data.getBytes(), 1, data.getLength() - 1);
                    peekedBytes = fEngine.GetMessageByID(messageID);
                    peekedBytesLength = peekedBytes == null ? 1 : peekedBytes.length + 1;
                    finalBytes = new byte[peekedBytesLength + fSplitterLength];
                    if (peekedBytes != null)
                    {
                        System.arraycopy(peekedBytes, 0, finalBytes, 1, peekedBytes.length);
                    }
                    finalBytes[0] = (byte) '4';
                    System.arraycopy(fSplitterBytes, 0, finalBytes, peekedBytesLength, fSplitterLength);
                    client.SendData(finalBytes, 0, finalBytes.length);
                    break;

                case 9: // Keep - Alive
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
