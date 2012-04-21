package myqueueserver.Network.Server;

import Extasys.DataFrame;
import Extasys.Network.TCP.Server.ExtasysTCPServer;
import Extasys.Network.TCP.Server.Listener.TCPClientConnection;
import java.net.InetAddress;
import java.net.UnknownHostException;
import myqueueserver.Config.Config;
import myqueueserver.Queue.QueueManager;

/**
 *
 * @author Nikos Siatras
 */
public class MyQueueTCPServer extends ExtasysTCPServer
{

    public MyQueueTCPServer() throws UnknownHostException
    {
        super("MyQueue Server", "MyQueue Server", Config.fCorePoolSize, Config.fMaxPoolSize);

        // Add listeners
        for (String ip : Config.fBindAddresses)
        {
            this.AddListener(ip, InetAddress.getByName(ip), Config.fServerPort, Config.fMaxConnections, Config.fReadBufferSize, 2000, Config.fMaxConnections);
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

    @Override
    public void OnDataReceive(TCPClientConnection sender, DataFrame data)
    {
        String strData = new String(data.getBytes());
        String[] splittedStr = strData.split(" ");
        
        
        switch (splittedStr[0].toUpperCase())
        {
            case "CREATE":
                switch (splittedStr[1])
                {
                    case "QUEUE":   // CREATE QUEUE <QUEUE_NAME> <QUEUE_SAVE_LOCATION>
                        
                        break;

                    case "USER":    // CREATE USER <USERNAME> <PASSWORD>
                        break;
                }
                break;
        }
    }
}
