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
public class myQueueConnector extends Extasys.Network.TCP.Client.ExtasysTCPClient
{

    // Methods Lock
    private final Object fLock = new Object();
    // Server response
    private boolean fGotResponseFromServer = false;
    private DataFrame fServerResponse = null;
    private ManualResetEvent fWaitCommandEvent = new ManualResetEvent(false);
    // Connection Properties
    private boolean fIsConnected = false;
    private String fServerIP, fUsername, fPassword;
    private int fServerPort;
    private int fResponseTimeOut = 3000;
    private String fETX = "<3m{X34l*Uψ7q.!]'Cξ51g47Ω],g3;7=8@2:λHB4&4_lπ#>NM{-3ς3#7k\"mΠpX%(";

    public myQueueConnector(String serverIP, int serverPort, String username, String password) throws UnknownHostException
    {
        super("", "", 8, 24);
        fServerIP = serverIP;
        fServerPort = serverPort;
        fUsername = username;
        fPassword = password;
        this.AddConnector("", InetAddress.getByName(serverIP), serverPort, 32768, fETX);
    }

    @Override
    public void OnDataReceive(TCPConnector connector, DataFrame data)
    {
        fGotResponseFromServer = true;
        fWaitCommandEvent.Set();
        fServerResponse = data;
    }

    public void Connect() throws Exception
    {
        super.Start();
        String logIn = "LOGIN " + fUsername + " " + fPassword;
        DataFrame response = SendToServer(logIn);
        
    }

    private DataFrame SendToServer(String data) throws ConnectorDisconnectedException, ConnectorCannotSendPacketException, CommandTimeOutException
    {
        synchronized (fLock)
        {
            fServerResponse = null;
            fWaitCommandEvent.Reset();
            fGotResponseFromServer = false;
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
    public void OnConnect(TCPConnector connector)
    {
        fIsConnected = true;
    }

    @Override
    public void OnDisconnect(TCPConnector connector)
    {
        fIsConnected = false;
    }

    public void Disconnect()
    {
        super.Stop();
        fIsConnected = false;
    }

    /**
     * Set the connection's response time out
     * @param timeOut in milliseconds
     */
    public void setResponseTimeOut(int timeOut)
    {
        fResponseTimeOut = timeOut;
    }

    /**
     * Gets the connection's response time out
     * @param timeOut in milliseconds
     */
    public int getResponseTimeOut()
    {
        return fResponseTimeOut;
    }
}