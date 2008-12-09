package myqueue.Connector;

import Extasys.DataFrame;
import Extasys.ManualResetEvent;
import Extasys.Network.TCP.Client.Connectors.TCPConnector;
import Extasys.Network.TCP.Client.Exceptions.ConnectorCannotSendPacketException;
import Extasys.Network.TCP.Client.Exceptions.ConnectorDisconnectedException;
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
