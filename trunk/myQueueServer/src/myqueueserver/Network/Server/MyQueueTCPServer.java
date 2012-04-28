package myqueueserver.Network.Server;

import Extasys.DataFrame;
import Extasys.Network.TCP.Server.ExtasysTCPServer;
import Extasys.Network.TCP.Server.Listener.TCPClientConnection;
import java.net.InetAddress;
import java.net.UnknownHostException;
import myqueueserver.Authentication.UserAuthenticationManager;
import myqueueserver.Config.Config;
import myqueueserver.Queue.QueueManager;
import myqueueserver.Users.User;
import myqueueserver.Users.UsersManager;

/**
 *
 * @author Nikos Siatras
 */
public class MyQueueTCPServer extends ExtasysTCPServer
{

    private String fETX = "<3m{X34l*Uψ7q.!]'Cξ51g47Ω],g3;7=8@2:λHB4&4_lπ#>NM{-3ς3#7k;mΠpX%(";

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

            // Sender is not yet logged in
            if (sender.getTag() == null)
            {
                switch (splittedStr[0].toUpperCase())
                {
                    case "LOGIN": // LOGIN <USERNAME> <PASSWORD> <QUEUE NAME>
                        if (UserAuthenticationManager.AuthenticateUser(splittedStr[1], splittedStr[2]))
                        {
                            sender.setTag(UsersManager.getUser(splittedStr[1]));
                            sender.SendData("OK" + fETX);
                        }
                        else
                        {
                            sender.SendData("ERROR Wrong username or password" + fETX);
                        }
                        break;
                }
                return;
            }


            User senderUser = (User) sender.getTag();
            switch (splittedStr[0].toUpperCase())
            {
                case "SELECT": // SELECT <QUEUE NAME>
                    if (UserAuthenticationManager.UserHasPermissionsForQueue(senderUser.getName(), splittedStr[1]))
                    {
                        sender.SendData("OK" + fETX);
                    }
                    else
                    {
                        sender.SendData("ERROR You dont have any active permissions on Queue '" + splittedStr[1] + "'" + fETX);
                    }
                    break;

                case "CREATE":
                    switch (splittedStr[1])
                    {
                        case "QUEUE":   // CREATE QUEUE <QUEUE_NAME>
                            if (((User) sender.getTag()).CanCreateNewQueues())
                            {
                                if (QueueManager.CreateQueue(splittedStr[2]))
                                {
                                    sender.SendData("OK" + fETX);
                                }
                                else
                                {
                                    sender.SendData("ERROR Cannot create Queue. Check if Queue already exists" + fETX);
                                }
                            }
                            else
                            {
                                sender.SendData("ERROR You dont have permission to create Queues" + fETX);
                            }
                            break;

                        case "USER":   // CREATE USER <USERNAME> <PASSWORD>
                            if (((User) sender.getTag()).CanCreateNewUsers())
                            {
                                UsersManager.AddUser(splittedStr[2], splittedStr[3]);
                                sender.SendData("OK" + fETX);
                            }
                            else
                            {
                                sender.SendData("ERROR You dont have permission to create Users" + fETX);
                            }
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

                default:
                    sender.DisconnectMe();
                    break;
            }
        }
        catch (Exception ex)
        {
            try
            {
                sender.SendData("ERROR " + ex.getMessage() + fETX);
            }
            catch (Exception e)
            {
            }
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
