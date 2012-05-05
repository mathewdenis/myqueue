package myQueueConnector;

import Extasys.DataFrame;
import Extasys.ManualResetEvent;
import Extasys.Network.TCP.Client.Connectors.TCPConnector;
import Extasys.Network.TCP.Client.Exceptions.ConnectorCannotSendPacketException;
import Extasys.Network.TCP.Client.Exceptions.ConnectorDisconnectedException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import myQueueConnector.Exceptions.CommandTimeOutException;
import myQueueConnector.Exceptions.QueueAlreadyExistsException;
import myQueueConnector.Exceptions.QueueDoesNotExistException;

/**
 *
 * @author Nikos Siatras
 */
public class myQueueConnector extends Extasys.Network.TCP.Client.ExtasysTCPClient implements AutoCloseable
{

    // Methods Lock
    private final Object fLock = new Object();
    // Server response
    private boolean fGotResponseFromServer = false;
    private DataFrame fServerResponse = null;
    private ManualResetEvent fWaitCommandEvent = new ManualResetEvent(false);
    // Connection Properties
    public boolean fIsConnected = false;
    private InetAddress fServerIP;
    private String fUsername, fPassword;
    private int fServerPort;
    private int fResponseTimeOut = 8000;
    private String fETX = "<3m{X34l*Uψ7q.!]'Cξ51g47Ω],g3;7=8@2:λHB4&4_lπ#>NM{-3ς3#7k;mΠpX%(";

    public myQueueConnector(InetAddress serverIP, int serverPort, String username, String password) throws UnknownHostException
    {
        super("", "", 8, 24);
        fServerIP = serverIP;
        fServerPort = serverPort;
        fUsername = username;
        fPassword = password;
        this.AddConnector("", serverIP, serverPort, 32768, fETX);
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

    @Override
    public void OnDataReceive(TCPConnector connector, DataFrame data)
    {
        //System.out.println(new String(data.getBytes()));
        fServerResponse = data;
        fGotResponseFromServer = true;
        fWaitCommandEvent.Set();
    }

    @Override
    public void OnConnect(TCPConnector connector)
    {
        fIsConnected = true;
        fWaitCommandEvent = new ManualResetEvent(false);
    }

    @Override
    public void OnDisconnect(TCPConnector connector)
    {
        fIsConnected = false;
        fWaitCommandEvent = new ManualResetEvent(false);
    }

    public synchronized void Open() throws Exception
    {
        super.Start();
        String logIn = "LOGIN " + fUsername + " " + fPassword;
        DataFrame response = SendToServer(logIn);
        String responseStr = new String(response.getBytes());

        if (responseStr.startsWith("ERROR"))
        {
            throw new Exception(responseStr);
        }
    }

    public synchronized void Close()
    {
        super.Stop();
        fIsConnected = false;
    }

    public synchronized void CreateQueue(String name) throws QueueAlreadyExistsException, Exception, CommandTimeOutException
    {
        DataFrame response = SendToServer("CREATE QUEUE " + name);
        String responseStr = new String(response.getBytes());
        if (responseStr.equals("ERROR 1"))
        {
            throw new QueueAlreadyExistsException();
        }
        else if (responseStr.startsWith("ERROR"))
        {
            throw new Exception(responseStr);
        }
    }

    public synchronized void DropQueue(String name) throws ConnectorDisconnectedException, ConnectorCannotSendPacketException, CommandTimeOutException, Exception
    {
        DataFrame response = SendToServer("DROP QUEUE " + name);
        String responseStr = new String(response.getBytes());
        if (responseStr.startsWith("ERROR"))
        {
            if (responseStr.equals("ERROR 1"))
            {
                throw new QueueDoesNotExistException();
            }
            else
            {
                throw new Exception(responseStr);
            }
        }
    }

    /**
     * Set the connection's response time out
     *
     * @param timeOut in milliseconds
     */
    public void setResponseTimeOut(int timeOut)
    {
        fResponseTimeOut = timeOut;
    }

    /**
     * Gets the connection's response time out
     *
     * @param timeOut in milliseconds
     */
    public int getResponseTimeOut()
    {
        return fResponseTimeOut;
    }

    @Override
    public void close() throws Exception
    {
        Close();
    }
}
