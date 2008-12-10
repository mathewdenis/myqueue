package myqueue.Connector;

import Extasys.DataFrame;
import Extasys.ManualResetEvent;
import Extasys.Network.TCP.Client.Connectors.TCPConnector;
import java.net.InetAddress;
import myqueue.Connector.Exceptions.DequeueMessageException;
import myqueue.Connector.Exceptions.EnqueueMessageException;
import myqueue.Connector.Exceptions.MyQueueConnectorDisconnectedException;
import myqueue.Connector.Exceptions.PeekMessageException;

/**
 *
 * @author Nikos Siatras
 */
public class Connector extends Extasys.Network.TCP.Client.ExtasysTCPClient
{

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
    private ObjectQueue fReceivedMesagesQueue = new ObjectQueue();
    private ManualResetEvent fWaitForMessagesEvt = new ManualResetEvent(false);
    private boolean fIsReceiving = false;
    private ManualResetEvent fBeginReceiveWaitEvt = new ManualResetEvent(false);
    private MessageQueueMessage fReceivedMessage;

    public Connector(InetAddress ip, int port)
    {
        super("", "", 5, 10);
        fIP = ip;
        fPort = port;
    }

    @Override
    public void OnConnect(TCPConnector connector)
    {
        System.out.println("Connector connected");
    }

    @Override
    public void OnDisconnect(TCPConnector connector)
    {
        System.err.println("Connector disconnected.");
        Disconnect();
    }

    @Override
    public void OnDataReceive(TCPConnector connector, DataFrame data)
    {
        try
        {
            String commandIDStr = new String(data.getBytes(), 0, 1);
            int commandID = Integer.valueOf(commandIDStr);

            switch (commandID)
            {
                case 0: // Message enqueued successfully!
                    fMessageEnqueuedSuccessfully = true;
                    fWaitEvt.Set();
                    break;

                case 1: // An error occured during the message enqueue proccess.
                    fEnqueueMessageProccessError = "An error occured during the server's message enqueue proccess.";
                    fMessageEnqueuedSuccessfully = false;
                    fWaitEvt.Set();
                    break;

                case 2: // Peek or Dequeue data.
                    String message = new String(data.getBytes(), 1, data.getLength() - 1);
                    int indexOfID = message.indexOf('\n');
                    if (indexOfID > 0)
                    {
                        long messageID = Long.parseLong(message.substring(0, indexOfID));
                        fMessage = new MessageQueueMessage(messageID, message.substring(indexOfID + 1));
                    }
                    fMessagePeekedSuccessfully = true;
                    fWaitEvt.Set();
                    break;

                case 3: // Begin receive.
                    if (fIsReceiving)
                    {
                        fReceivedMesagesQueue.enqueue(new String(data.getBytes(), 1, data.getLength() - 1));
                        fWaitForMessagesEvt.Set();
                    }
                    break;

                case 4: // Reveive requested message.
                    String requestedMessage = new String(data.getBytes(), 1, data.getLength() - 1);
                    int indexOfRequestedMessageID = requestedMessage.indexOf('\n');
                    if (indexOfRequestedMessageID > 0)
                    {
                        long messageID = Long.parseLong(requestedMessage.substring(0, indexOfRequestedMessageID));
                        fReceivedMessage = new MessageQueueMessage(messageID, requestedMessage.substring(indexOfRequestedMessageID + 1));
                    }
                    fBeginReceiveWaitEvt.Set();
                    break;

                case 9: // Fatal error.
                    fWaitEvt.Set();
                    break;
            }
        }
        catch (Exception ex)
        {
            fWaitEvt.Set();
        }
    }

    /**
     * Enqueue data.
     * @param data is the string data to be enqueued.
     * @throws Exception
     */
    public synchronized void Enqueue(String data) throws EnqueueMessageException, MyQueueConnectorDisconnectedException
    {
        synchronized (fSyncObject)
        {
            TryToConnect();

            fMessageEnqueuedSuccessfully = false;
            fEnqueueMessageErrorReported = false;

            fWaitEvt.Reset();
            try
            {
                SendData("1" + data + fSplitter);
            }
            catch (Exception ex)
            {
                fWaitEvt.Set();
                throw new MyQueueConnectorDisconnectedException();
            }
            fWaitEvt.WaitOne(10000);

            if (!fMessageEnqueuedSuccessfully)
            {
                // Could not receive response from the server that certifies that the message has been queued successfully.
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
     * Peek the last message of the queue.
     * @return the last message of the queue.
     */
    public synchronized MessageQueueMessage Peek() throws PeekMessageException, MyQueueConnectorDisconnectedException
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
     * Dequeue the last message of the queue.
     * @return the last message of the queue.
     */
    public synchronized MessageQueueMessage Dequeue() throws MyQueueConnectorDisconnectedException, DequeueMessageException
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
    }

    /**
     * Receive message from the server.
     * @param timeOut is the time in milliseconds the connector will wait to receive a new message from the server.
     * As timeout use an integer above 2000 and below 25000 otherwise the timeout will be automatically set to 5000.
     * @return MessageQueueMessage
     * @throws myqueue.Connector.Exceptions.MyQueueConnectorDisconnectedException
     * @throws java.lang.Exception
     */
    public MessageQueueMessage Receive(int timeOut) throws MyQueueConnectorDisconnectedException, Exception
    {
        if (timeOut < 2000 || timeOut > 25000)
        {
            timeOut = 5000;
        }
        if (!fIsReceiving)
        {
            throw new Exception("Use BeginReceive method before Receive method.");
        }
        TryToConnect();
        try
        {
            fWaitForMessagesEvt.Reset();
            fBeginReceiveWaitEvt.Reset();

            fReceivedMessage = null;
            if (!fReceivedMesagesQueue.isEmpty() && fIsReceiving)
            {
                SendData("4" + fReceivedMesagesQueue.dequeue().toString() + fSplitter);
                fBeginReceiveWaitEvt.WaitOne(timeOut);
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
        if (super.getConnectors().size() == 0 || !((TCPConnector) super.getConnectors().get(0)).isConnected())
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

    public void Connect() throws Exception
    {
        fWaitEvt = new ManualResetEvent(false);
        super.Stop();
        super.RemoveConnector("myQueueConnector");
        super.AddConnector("myQueueConnector", fIP, fPort, 65535, fSplitter);
        super.Start();
    }

    public void Disconnect()
    {
        super.Stop();
    }
}
