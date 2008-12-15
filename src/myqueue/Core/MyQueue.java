/* myQueue
 * Copyright (C) 2008 Nikos Siatras
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package myqueue.Core;

import Extasys.DataFrame;
import Extasys.Network.TCP.Server.Listener.TCPClientConnection;
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
    private final String fSplitter = String.valueOf(((char) 3));
    private int fSplitterLength = 0;
    private byte[] fSplitterBytes = null;
    private int fConnectionsTimeOut = 30000;

    public MyQueue(String name, String description, StorageEngine engine, int corePoolSize, int maxPoolSize, int connectionsTimeOut)
    {
        super(name, description, corePoolSize, maxPoolSize);
        fEngine = engine;
        fCorePoolsSize = corePoolSize;
        fMaxPoolSize = maxPoolSize;

        fSplitterLength = fSplitter.length();
        fSplitterBytes = fSplitter.getBytes();
        fConnectionsTimeOut = connectionsTimeOut;
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
            int commandID = Integer.valueOf(new String(data.getBytes(), 0, 1));

            switch (commandID)
            {
                case 1: // Enqueue.
                    try
                    {
                        byte[] messageBytes = new byte[data.getLength() - 1];
                        System.arraycopy(data.getBytes(), 1, messageBytes, 0, data.getLength() - 1);
                        String enqueuedMesageID = fEngine.Enqueue(messageBytes);

                        if (enqueuedMesageID != null)
                        {
                            client.SendData("0" + fSplitter); // Message enqueued successfully!
                            // Inform all clients that the message has been enqueued.
                            ReplyToAllExceptSender("3" + enqueuedMesageID + fSplitter, client);
                            return;
                        }
                        else
                        {
                            client.SendData("1An error occured on server enqueue message procedure." + fSplitter); // A fatal error occured.
                            return;
                        }
                    }
                    catch (Exception ex)
                    {
                        client.SendData("9Fatal error occured on server enqueue message procedure.\n" + ex.getMessage() + fSplitter); // A fatal error occured.
                    }
                    break;

                case 2: // Peek (2 - PeekedMessage - Splitter).
                    try
                    {
                        byte[] finalBytes;
                        byte[] peekedBytes;
                        int peekedBytesLength = 0;

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
                        return;
                    }
                    catch (Exception ex)
                    {
                        client.SendData("9Fatal error occured on server peek message procedure.\n" + ex.getMessage() + fSplitter); // A fatal error occured.
                    }
                    break;

                case 3: // Dequeue (2 - DequeuedMessage - Splitter).
                    try
                    {
                        byte[] finalBytes;
                        byte[] peekedBytes;
                        int peekedBytesLength = 0;

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
                        return;
                    }
                    catch (Exception ex)
                    {
                        client.SendData("9Fatal error occured on server dequeue message procedure.\n" + ex.getMessage() + fSplitter); // A fatal error occured.
                    }
                    break;

                case 4: // Request message (4 - Message - Splitter).
                    try
                    {
                        byte[] finalBytes;
                        byte[] peekedBytes;
                        int peekedBytesLength = 0;

                        peekedBytes = fEngine.GetMessageByID(new String(data.getBytes(), 1, data.getLength() - 1));
                        peekedBytesLength = peekedBytes == null ? 1 : peekedBytes.length + 1;
                        finalBytes = new byte[peekedBytesLength + fSplitterLength];
                        if (peekedBytesLength > 1)
                        {
                            System.arraycopy(peekedBytes, 0, finalBytes, 1, peekedBytes.length);
                        }
                        finalBytes[0] = (byte) '4';
                        System.arraycopy(fSplitterBytes, 0, finalBytes, peekedBytesLength, fSplitterLength);
                        client.SendData(finalBytes, 0, finalBytes.length);
                        return;
                    }
                    catch (Exception ex)
                    {
                        client.SendData("9Fatal error occured on server request message procedure.\n" + ex.getMessage() + fSplitter); // A fatal error occured.
                    }
                    break;

                case 5:

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
        fEngine.Clear();
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

    public long getMessageCount()
    {
        return fEngine.getMessageCount();
    }

    public long getNormalPriorityMessageCount()
    {
        return fEngine.getNormalPriorityMessageCount();
    }

    public long getAboveNormalPriorityMessageCount()
    {
        return fEngine.getAboveNormalPriorityMessageCount();
    }

    public long getHighPriorityMessageCount()
    {
        return fEngine.getHighPriorityMessageCount();
    }

    public int getConnectionsTimeOut()
    {
        return fConnectionsTimeOut;
    }
}
