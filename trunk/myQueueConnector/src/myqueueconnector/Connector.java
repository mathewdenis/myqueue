package myqueueconnector;

import Extasys.DataFrame;
import Extasys.ManualResetEvent;
import Extasys.Network.TCP.Client.Connectors.TCPConnector;
import Extasys.Network.TCP.Client.Exceptions.ConnectorCannotSendPacketException;
import Extasys.Network.TCP.Client.Exceptions.ConnectorDisconnectedException;
import java.net.InetAddress;

/**
 *
 * @author Nikos Siatras
 */
public class Connector extends Extasys.Network.TCP.Client.ExtasysTCPClient
{

    private String fSplitter = "#!" + String.valueOf(((char) 2)) + "!#";
    private ManualResetEvent fWaitEvt = new ManualResetEvent(false);

    public Connector(InetAddress ip, int port)
    {
        super("", "", 5, 10);
        super.AddConnector("", ip, port, 65535, fSplitter);
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
    }

    @Override
    public void OnDataReceive(TCPConnector connector, DataFrame data)
    {
        try
        {
            String commandIDStr = new String(data.getBytes(), 0, 1);
            int commandID = Integer.valueOf(commandIDStr);

            byte[] messageBytes = new byte[data.getLength() - 1];

            switch (commandID)
            {
                case 0: // Message enqueued successfully!
                    fWaitEvt.Set();
                    break;

                case 1: // An error occured during during the message enqueue proccess.
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
     * @throws Extasys.Network.TCP.Client.Exceptions.ConnectorDisconnectedException
     * @throws Extasys.Network.TCP.Client.Exceptions.ConnectorCannotSendPacketException
     */
    public synchronized void Enqueue(String data) throws ConnectorDisconnectedException, ConnectorCannotSendPacketException
    {
        fWaitEvt.Reset();
        SendData("1" + data + fSplitter);
        fWaitEvt.WaitOne();
    }

    /**
     * Peek the last message of the queue.
     * @return the last message of the queue.
     */
    public synchronized DataFrame Peek()
    {
        return null;
    }

    /**
     * Dequeue the last message of the queue.
     * @return the last message of the queue.
     */
    public synchronized DataFrame Dequeue()
    {
        return null;
    }

    public void Connect() throws Exception
    {
        super.Start();
    }

    public void Disconnect()
    {
        super.Stop();
    }
}
