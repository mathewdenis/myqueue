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
    private int fResponseTimeOut = 2000;
    private String fETX = "<3m{X34l*Uψ7q.!]'Cξ51g47Ω],g3;7=8@2:λHB4&4_lπ#>NM{-3ς3#7k;mΠpX%(";
    // Server response
    private boolean fGotResponseFromServer = false;
    private DataFrame fServerResponse = null;
    private ManualResetEvent fWaitCommandEvent = new ManualResetEvent(false);

    public myQueueConnection(InetAddress serverIP, int serverPort, String username, String password) throws UnknownHostException
    {
        super("", "", 8, 24);
        fServerIP = serverIP;
        fServerPort = serverPort;
        fUsername = username;
        fPassword = password;
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
    }

    @Override
    public void OnDisconnect(TCPConnector connector)
    {
        fIsConnected = false;
    }

    /**
     * Open connection with the server
     *
     * @throws Exception
     */
    public synchronized void Open() throws Exception
    {
        super.RemoveConnector("C1");
        super.AddConnector("C1", fServerIP, fServerPort, fServerPort, fETX);
        super.Start();

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
        catch (ConnectorDisconnectedException | ConnectorCannotSendPacketException | CommandTimeOutException ex)
        {
            throw new Exception("Cannot contact server!");
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
    public synchronized void Close()
    {
        super.Stop();
        fIsConnected = false;
    }

    public DataFrame SendToServer(String data) throws ConnectorDisconnectedException, ConnectorCannotSendPacketException, CommandTimeOutException
    {
        synchronized (fLock)
        {
            fGotResponseFromServer = false;
            fWaitCommandEvent = new ManualResetEvent(false);
            fServerResponse = null;
            super.SendData(data + fETX);
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
