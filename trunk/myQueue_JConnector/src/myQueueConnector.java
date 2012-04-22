
import Extasys.ManualResetEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *
 * @author Nikos Siatras
 */
public class myQueueConnector extends Extasys.Network.TCP.Client.ExtasysTCPClient
{

    private String fServerIP, fUsername, fPassword;
    private int fServerPort;
    private int fResponseTimeOut = 2000;
    private String fETX = "<3m{X34l*Uψ7q.!]'Cξ51g47Ω],g3;7=8@2:λHB4&4_lπ#>NM{-3ς3#7k\"mΠpX%(";
    private ManualResetEvent fWaitCommandEvent = new ManualResetEvent(false);

    public myQueueConnector(String serverIP, int serverPort, String username, String password) throws UnknownHostException
    {
        super("", "", 8, 24);
        fServerIP = serverIP;
        fServerPort = serverPort;
        fUsername = username;
        fPassword = password;
        this.AddConnector("", InetAddress.getByName(serverIP), serverPort, 32768, fETX);
    }

    public void Connect() throws Exception
    {
        super.Start();
    }

    public void Disconnect()
    {
        super.Stop();
    }

    public void Enqueue(QueueMessage message)
    {
    }

    public QueueMessage Dequeue()
    {
        return null;
    }

    public QueueMessage Peek()
    {
        return null;
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
