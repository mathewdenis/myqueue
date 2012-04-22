package myqueueserver.Network.Server;

import Extasys.DataFrame;
import Extasys.Network.TCP.Server.ExtasysTCPServer;
import Extasys.Network.TCP.Server.Listener.TCPClientConnection;
import java.net.InetAddress;
import java.net.UnknownHostException;
import myqueueserver.Config.Config;
import myqueueserver.Queue.QueueManager;
import myqueueserver.Users.UsersManager;

/**
 *
 * @author Nikos Siatras
 */
public class MyQueueTCPServer extends ExtasysTCPServer
{
    
    private String fETX = "<3m{X34l*Uψ7q.!]'Cξ51g47Ω],g3;7=8@2:λHB4&4_lπ#>NM{-3ς3#7k\"mΠpX%(";
    
    public MyQueueTCPServer() throws UnknownHostException
    {
        super("MyQueue Server", "MyQueue Server", Config.fCorePoolSize, Config.fMaxPoolSize);

        // Add listeners
        for (String ip : Config.fBindAddresses)
        {
            this.AddListener(ip, InetAddress.getByName(ip), Config.fServerPort, Config.fMaxConnections, Config.fReadBufferSize, 2000, Config.fMaxConnections, fETX);
        }
    }
    
    @Override
    public void OnDataReceive(TCPClientConnection sender, DataFrame data)
    {
        try
        {
            String strData = new String(data.getBytes());
            String[] splittedStr = strData.split(" ");
            
            switch (splittedStr[0].toUpperCase())
            {
                
                case "LOGIN": // LOGIN <USERNAME> <PASSWORD>
                    sender.SendData("OK" + fETX);
                    break;
                
                case "CREATE":
                    switch (splittedStr[1])
                    {
                        case "QUEUE":   // CREATE QUEUE <QUEUE_NAME> <QUEUE_SAVE_LOCATION>
                            // TODO 
                            // Check if sender has permission to CREATE QUEUE
                            QueueManager.CreateQueue(splittedStr[2], splittedStr[3]);
                            break;
                        
                        case "USER":    // CREATE USER <USERNAME> <PASSWORD>
                            // TODO 
                            // Check if sender has permission to CREATE USER
                            UsersManager.AddUser(splittedStr[2], splittedStr[3]);
                            break;
                    }
                    break;
                
                case "DROP":
                    switch (splittedStr[1])
                    {
                        case "QUEUE":   // DROP QUEUE <QUEUE_NAME>
                            // TODO
                            // Check if sender has permission to DROP QUEUE
                            QueueManager.DropQueue(splittedStr[2]);
                            break;
                        
                        case "USER":    // DROP USER <USERNAME> 
                            // TODO
                            // Check if sender has permission to DROP USER
                            UsersManager.DropUser(splittedStr[2]);
                            break;
                    }
                    break;
            }
        }
        catch (Exception ex)
        {
        }
    }
    
    @Override
    public void OnClientConnect(TCPClientConnection client)
    {
        System.out.println("Client " + client.getIPAddress() + " connected");
    }
    
    @Override
    public void OnClientDisconnect(TCPClientConnection client)
    {
        System.out.println("Client " + client.getIPAddress() + " disconnected");
    }
}
