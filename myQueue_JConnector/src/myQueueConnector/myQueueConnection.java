package myQueueConnector;

import Extasys.DataFrame;
import Extasys.ManualResetEvent;
import Extasys.Network.TCP.Client.Connectors.TCPConnector;
import Extasys.Network.TCP.Client.Exceptions.ConnectorCannotSendPacketException;
import Extasys.Network.TCP.Client.Exceptions.ConnectorDisconnectedException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import myQueueConnector.Exceptions.CommandTimeOutException;

/**
 *
 * @author Nikos Siatras
 */
public class myQueueConnection extends Extasys.Network.TCP.Client.ExtasysTCPClient implements AutoCloseable
{

    // Methods Lock
    private final Object fLock = new Object();
    // Server Connection Properties
    public boolean fIsConnected = false;
    private InetAddress fServerIP;
    private String fUsername, fPassword;
    private int fServerPort;
    private int fResponseTimeOut = 4000;
    private String fETX = "<1@#$)(*&yh^^3_18w43K2$009_+1##";
    // Server response
    private boolean fGotResponseFromServer = false;
    private DataFrame fServerResponse = null;
    private ManualResetEvent fWaitCommandEvent = new ManualResetEvent(false);
    private ManualResetEvent fWaitToConnectEvent = new ManualResetEvent(false);
    private TCPConnector fMyTCPConnector;

    public myQueueConnection(InetAddress serverIP, int serverPort, String username, String password) throws UnknownHostException
    {
        super("", "", 8, 24);
        fServerIP = serverIP;
        fServerPort = serverPort;
        fUsername = username;
        fPassword = password;

        fMyTCPConnector = super.AddConnector("TCPConnector", fServerIP, fServerPort, fServerPort, fETX);
    }

    @Override
    public void OnDataReceive(TCPConnector connector, DataFrame data)
    {
        fServerResponse = data;
        fGotResponseFromServer = true;
        fWaitCommandEvent.Set();
    }

    @Override
    public void OnConnect(TCPConnector connector)
    {
        fIsConnected = true;
        fWaitToConnectEvent.Set();
    }

    @Override
    public void OnDisconnect(TCPConnector connector)
    {
        fIsConnected = false;
        fWaitToConnectEvent.Set();
    }

    /**
     * Open connection with the server
     *
     * @throws Exception
     */
    public synchronized void Open() throws Exception
    {
        fMyTCPConnector.Stop();
        fWaitCommandEvent.Reset();
        fMyTCPConnector.Start();
        fWaitCommandEvent.WaitOne(fResponseTimeOut);

        String logIn = "LOGIN " + fUsername + " " + fPassword;
        DataFrame response = null;
        try
        {
            response = SendToServer(logIn);
            String responseStr = new String(response.getBytes());
            if (responseStr.startsWith("Error"))
            {
                throw new Exception(responseStr.substring(5).trim());
            }
        }
        catch (ConnectorDisconnectedException | ConnectorCannotSendPacketException ex)
        {
            Close();
            throw new Exception(ex.getMessage());
        }
        catch (CommandTimeOutException ex)
        {
            Close();
            throw new Exception("Connection time out!");
        }

        String responseStr = new String(response.getBytes());

        if (responseStr.startsWith("ERROR"))
        {
            throw new Exception(responseStr);
        }
    }

    /**
     * Close the connection
     */
    private synchronized void Close()
    {
        super.Dispose(); // Stops and disposes the client
        fMyTCPConnector = null;
        fIsConnected = false;
    }

    public DataFrame SendToServer(String data) throws ConnectorDisconnectedException, ConnectorCannotSendPacketException, CommandTimeOutException
    {
        synchronized (fLock)
        {
            fWaitCommandEvent.Reset();
            fGotResponseFromServer = false;
            fServerResponse = null;

            fMyTCPConnector.SendData(data + fETX);

            fWaitCommandEvent.WaitOne(fResponseTimeOut);
            if (!fGotResponseFromServer)
            {
                throw new CommandTimeOutException();
            }
            return fServerResponse;
        }
    }

    /**
     * Returns true if the connection is live
     *
     * @return
     */
    public boolean isConnected()
    {
        return fIsConnected;
    }

    @Override
    public void close() throws Exception
    {
        this.Close();
    }
}
