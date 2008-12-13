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
package myqueue.Connector;

import Extasys.DataFrame;
import Extasys.ManualResetEvent;
import Extasys.Network.TCP.Client.Connectors.TCPConnector;
import java.net.InetAddress;
import myqueue.Connector.Exceptions.DequeueMessageException;
import myqueue.Connector.Exceptions.EnqueueMessageException;
import myqueue.Connector.Exceptions.MyQueueConnectorDisconnectedException;
import myqueue.Connector.Exceptions.PeekMessageException;
import myqueue.Connector.Exceptions.ReceivedMessageException;

/**
 *
 * @author Nikos Siatras
 */
public class Connector extends Extasys.Network.TCP.Client.ExtasysTCPClient
{

    private boolean fIsConnected = false;
    private ManualResetEvent fWaitEvt = new ManualResetEvent(false);
    private final Object fSyncObject = new Object();
    // myQueue Server
    private InetAddress fIP;
    private int fPort;
    private String fSplitter = "#!" + String.valueOf(((char) 2)) + "!#";
    // Enqueue
    private boolean fMessageEnqueuedSuccessfully = false;
    private boolean fEnqueueMessageErrorReported = false;
    private String fEnqueueMessageProccessError = "";
    // Peek
    private boolean fMessagePeekedSuccessfully = false;
    private MessageQueueMessage fMessage;
    // Begin receive
    private boolean fMessageReceivedSuccessfully = false;
    private ObjectQueue fReceivedMesagesQueue = new ObjectQueue();
    private ManualResetEvent fWaitForMessagesEvt = new ManualResetEvent(false);
    private boolean fIsReceiving = false;
    private ManualResetEvent fBeginReceiveWaitEvt = new ManualResetEvent(false);
    private MessageQueueMessage fReceivedMessage;

    /**
     * Create a new myQueue connector.
     * @param ip is the myQueue server's ip address.
     * @param port is the myQueue server's port.
     */
    public Connector(InetAddress ip, int port)
    {
        super("", "", 5, 10);
        fIP = ip;
        fPort = port;
    }

    @Override
    public void OnConnect(TCPConnector connector)
    {
        fIsConnected = true;
        System.out.println("Connector connected");
    }

    @Override
    public void OnDisconnect(TCPConnector connector)
    {
        fIsConnected = false;
        System.err.println("Connector disconnected.");
        Disconnect();
    }

    @Override
    public void OnDataReceive(TCPConnector connector, DataFrame data)
    {
        try
        {
            int commandID = Integer.valueOf(new String(data.getBytes(), 0, 1));

            switch (commandID)
            {
                case 0: // Message enqueued successfully!
                    fMessageEnqueuedSuccessfully = true;
                    fWaitEvt.Set();
                    return;

                case 1: // An error occured during the message enqueue proccess.
                    fEnqueueMessageProccessError = "An error occured during the server's message enqueue proccess.";
                    System.err.println(fEnqueueMessageProccessError);
                    fMessageEnqueuedSuccessfully = false;
                    fWaitEvt.Set();
                    return;

                case 2: // Peek or Dequeue data.
                    String message = new String(data.getBytes(), 1, data.getLength() - 1);
                    int indexOfID = message.indexOf('\n');
                    if (indexOfID > 0)
                    {
                        String messageID = message.substring(0, indexOfID);
                        fMessage = new MessageQueueMessage(messageID, message.substring(indexOfID + 2), Integer.valueOf(message.substring(indexOfID + 1, indexOfID + 2)));
                    }
                    fMessagePeekedSuccessfully = true;
                    fWaitEvt.Set();
                    return;

                case 3: // Begin receive.
                    if (fIsReceiving)
                    {
                        fReceivedMesagesQueue.enqueue(new String(data.getBytes(), 1, data.getLength() - 1));
                        fWaitForMessagesEvt.Set();
                    }
                    return;

                case 4: // Receive requested message.
                    String requestedMessage = new String(data.getBytes(), 1, data.getLength() - 1);
                    int indexOfRequestedMessageID = requestedMessage.indexOf('\n');
                    if (indexOfRequestedMessageID > 0)
                    {
                        String messageID = requestedMessage.substring(0, indexOfRequestedMessageID);
                        fReceivedMessage = new MessageQueueMessage(messageID, requestedMessage.substring(indexOfRequestedMessageID + 2), Integer.valueOf(requestedMessage.substring(indexOfRequestedMessageID + 1, indexOfRequestedMessageID + 2)));
                    }
                    fMessageReceivedSuccessfully = true;
                    fBeginReceiveWaitEvt.Set();
                    return;

                case 9: // Fatal error.
                    fWaitEvt.Set();
                    System.err.println("Fatal error");
                    return;
            }
        }
        catch (Exception ex)
        {
            fWaitEvt.Set();
        }
    }

    /**
     * Adds a String object to the end of the Queue.
     * @param data is the string data to be enqueued.
     * @throws Exception
     */
    public void Enqueue(String data) throws EnqueueMessageException, MyQueueConnectorDisconnectedException
    {
        synchronized (fSyncObject)
        {
            TryToConnect();

            fMessageEnqueuedSuccessfully = false;
            fEnqueueMessageErrorReported = false;

            fWaitEvt.Reset();
            try
            {
                SendData("10" + data + fSplitter);
            }
            catch (Exception ex)
            {
                fWaitEvt.Set();
                throw new MyQueueConnectorDisconnectedException();
            }
            fWaitEvt.WaitOne(10000);

            if (!fMessageEnqueuedSuccessfully)
            {
                // Could not receive response from the server that certifies that the message has been enqueued successfully.
                throw new EnqueueMessageException("Could not receive response from the server that certifies the message has been enqueued successfully.");
            }
            if (fEnqueueMessageErrorReported)
            {
                // An error occured during the server's message enqueue proccess.
                throw new EnqueueMessageException(fEnqueueMessageProccessError);
            }
        }
    }

    public void Enqueue(String data, int priority) throws EnqueueMessageException, MyQueueConnectorDisconnectedException
    {
        synchronized (fSyncObject)
        {
            TryToConnect();

            fMessageEnqueuedSuccessfully = false;
            fEnqueueMessageErrorReported = false;

            fWaitEvt.Reset();
            try
            {
                SendData("1" + String.valueOf(priority) + data + fSplitter);
            }
            catch (Exception ex)
            {
                fWaitEvt.Set();
                throw new MyQueueConnectorDisconnectedException();
            }
            fWaitEvt.WaitOne(10000);

            if (!fMessageEnqueuedSuccessfully)
            {
                // Could not receive response from the server that certifies that the message has been enqueued successfully.
                throw new EnqueueMessageException("Could not receive response from the server that certifies the message has been enqueued successfully.");
            }
            if (fEnqueueMessageErrorReported)
            {
                // An error occured during the server's message enqueue proccess.
                throw new EnqueueMessageException(fEnqueueMessageProccessError);
            }
        }
    }

    /**
     * Returns the message at the beginning of the Queue without removing it.
     * @return the message at the beginning of the Queue without removing it.
     */
    public MessageQueueMessage Peek() throws PeekMessageException, MyQueueConnectorDisconnectedException
    {
        synchronized (fSyncObject)
        {
            fMessage = null;
            TryToConnect();

            fMessagePeekedSuccessfully = false;

            fWaitEvt.Reset();
            try
            {
                SendData("2" + fSplitter);
            }
            catch (Exception ex)
            {
                fWaitEvt.Set();
                throw new MyQueueConnectorDisconnectedException();
            }
            fWaitEvt.WaitOne(10000);

            if (!fMessagePeekedSuccessfully)
            {
                throw new PeekMessageException("Could not peek a message from the server");
            }

            return fMessage;
        }
    }

    /**
     * Removes and returns the message at the beginning of the Queue.
     * @return Removes and returns the message at the beginning of the Queue.
     */
    public MessageQueueMessage Dequeue() throws MyQueueConnectorDisconnectedException, DequeueMessageException
    {
        synchronized (fSyncObject)
        {
            fMessage = null;
            TryToConnect();

            fMessagePeekedSuccessfully = false;

            fWaitEvt.Reset();
            try
            {
                SendData("3" + fSplitter);
            }
            catch (Exception ex)
            {
                fWaitEvt.Set();
                throw new MyQueueConnectorDisconnectedException();
            }
            fWaitEvt.WaitOne(10000);

            if (!fMessagePeekedSuccessfully)
            {
                throw new DequeueMessageException("Could not dequeue a message from the server");
            }
            return fMessage;
        }
    }

    /**
     * Start receiving new enqueued messages.
     */
    public void BeginReceive() throws MyQueueConnectorDisconnectedException
    {
        fIsReceiving = true;
        TryToConnect();
    }

    /**
     * Stop receiving messages.
     */
    public void StopReceive()
    {
        fIsReceiving = false;
        fReceivedMesagesQueue.clear();
        fWaitForMessagesEvt.Set();
        fBeginReceiveWaitEvt.Set();
    }

    /**
     * Receive message from the server.
     * @param timeOut is the time in milliseconds the connector will wait to receive a new message from the server.
     * As timeout use an integer above 2000 and below 20000 otherwise the timeout will be automatically set to 5000.
     * @return MessageQueueMessage
     * @throws myqueue.Connector.Exceptions.MyQueueConnectorDisconnectedException
     * @throws java.lang.Exception
     */
    public MessageQueueMessage Receive(int timeOut) throws MyQueueConnectorDisconnectedException, ReceivedMessageException
    {
        if (timeOut < 2000 || timeOut > 20000)
        {
            timeOut = 5000;
        }
        if (!fIsReceiving)
        {
            fBeginReceiveWaitEvt.WaitOne(timeOut);
            return null;
        }

        TryToConnect();

        try
        {
            fWaitForMessagesEvt.Reset();
            fBeginReceiveWaitEvt.Reset();

            fReceivedMessage = null;
            if (!fReceivedMesagesQueue.isEmpty() && fIsReceiving)
            {
                synchronized (fSyncObject)
                {
                    // Request the message from the server.
                    fMessageReceivedSuccessfully = false;
                    String messageID = fReceivedMesagesQueue.dequeue().toString();
                    SendData("4" + messageID + fSplitter);
                    fBeginReceiveWaitEvt.WaitOne(timeOut);

                    if (!fMessageReceivedSuccessfully)
                    {
                        throw new ReceivedMessageException("Message " + messageID + " could not be received.");
                    }
                }
            }
            else
            {
                // Send a keep alive.
                SendData("9" + fSplitter);
                fWaitForMessagesEvt.WaitOne(timeOut);
                if (!fReceivedMesagesQueue.isEmpty() && fIsReceiving)
                {
                    return Receive(timeOut);
                }
            }
        }
        catch (Exception ex)
        {
        }
        return fReceivedMessage;
    }

    private void TryToConnect() throws MyQueueConnectorDisconnectedException
    {
        if (!fIsConnected)
        //if (super.getConnectors().size() == 0 || !((TCPConnector) super.getConnectors().get(0)).isConnected())
        {
            try
            {
                Connect();
            }
            catch (Exception ex)
            {
                throw new MyQueueConnectorDisconnectedException();
            }
        }
    }

    private void Connect() throws Exception
    {
        fWaitEvt = new ManualResetEvent(false);
        super.Stop();
        super.RemoveConnector("myQueueConnector");
        super.AddConnector("myQueueConnector", fIP, fPort, 65535, fSplitter);
        super.Start();
    }

    private void Disconnect()
    {
        super.Stop();
    }

    @Override
    public void Dispose()
    {
        super.Stop();
    }
}
