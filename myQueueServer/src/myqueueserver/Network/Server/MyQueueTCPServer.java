package myqueueserver.Network.Server;

import Extasys.DataFrame;
import Extasys.Network.TCP.Server.ExtasysTCPServer;
import Extasys.Network.TCP.Server.Listener.Exceptions.ClientIsDisconnectedException;
import Extasys.Network.TCP.Server.Listener.Exceptions.OutgoingPacketFailedException;
import Extasys.Network.TCP.Server.Listener.TCPClientConnection;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import myqueueserver.Authentication.UserAuthenticationManager;
import myqueueserver.Config.Config;
import myqueueserver.Queue.QueueManager;
import myqueueserver.Users.EUserQueuePermissions;
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
            this.AddListener(ip, InetAddress.getByName(ip), Config.fServerPort, Config.fMaxConnections, Config.fReadBufferSize, Config.fConnectionsTimeOut, Config.fMaxConnections, fETX);
        }
    }

    @Override
    public void OnDataReceive(TCPClientConnection sender, DataFrame data)
    {
        try
        {
            String strData = new String(data.getBytes());

            System.out.println("Data received: " + strData);
            String[] splittedStr = strData.split(" ");

            // Sender is not yet logged in
            if (sender.getTag() == null)
            {
                switch (splittedStr[0].toUpperCase())
                {
                    case "LOGIN":       // LOGIN <USERNAME> <PASSWORD>
                        if (UserAuthenticationManager.AuthenticateUser(splittedStr[1], splittedStr[2]))
                        {
                            sender.setTag(UsersManager.getUser(splittedStr[1]));
                            sender.SendData("OK" + fETX);
                        }
                        else
                        {
                            sender.SendData("Error Wrong username or password" + fETX);
                        }
                        break;
                }
                return;
            }

            switch (splittedStr[0].toUpperCase())
            {
                case "SELECT":          // SELECT <QUEUE_NAME>
                    SelectQueue(sender, strData);
                    break;

                case "CREATE":
                    switch (splittedStr[1])
                    {
                        case "QUEUE":   // CREATE QUEUE <QUEUE_NAME>
                            CreateQueue(sender, strData);
                            break;

                        case "USER":    // CREATE USER <USERNAME> <PASSWORD>
                            CreateUser(sender, strData);
                            break;
                    }
                    break;

                case "DROP":
                    switch (splittedStr[1])
                    {
                        case "QUEUE":   // DROP QUEUE <QUEUE_NAME>
                            DropQueue(sender, strData);
                            break;

                        case "USER":    // DROP USER <USERNAME> 
                            DropUser(sender, strData);
                            break;
                    }
                    break;

                case "GRANT":           // GRANT Read,Write ON <Queue_Name> to <Username>
                    Grant(sender, strData);
                    break;

                case "SHOW":
                    switch (splittedStr[1])
                    {
                        case "GRANTS":  // SHOW GRANTS FOR <USERNAME>
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

    private void Grant(TCPClientConnection sender, String strData) throws ClientIsDisconnectedException, OutgoingPacketFailedException
    {
        String originalStrData = strData;

        // GRANT Read,Write ON Queue TO User
        strData = strData.toUpperCase();
        int onIndex = strData.indexOf("ON");
        int toIndex = strData.indexOf("TO");

        String permissionsStr = strData.substring(0, onIndex).replace("GRANT", "");
        String[] permissions = permissionsStr.split(",");

        String user = originalStrData.substring(toIndex + 2).trim();
        String queueName = originalStrData.substring(onIndex + 2, toIndex).trim();

        User senderUser = (User) sender.getTag();

        // Check if Queue exists
        if (!QueueManager.QueueExists(queueName))
        {
            sender.SendData("ERROR 1" + fETX); // Queue does not exist
            return;
        }

        // Check if user has the required permissions
        if (!senderUser.CanCreateNewUsers())
        {
            sender.SendData("ERROR You dont have the required permissions to Grant permissions to users" + fETX);
            return;
        }

        // Give permissions to user
        User u = UsersManager.getUser(user);

        ArrayList<EUserQueuePermissions> grantedPermissions = new ArrayList<>();
        for (String s : permissions)
        {
            String permission = s.trim().toUpperCase();
            switch (permission)
            {
                case "READ":
                    grantedPermissions.add(EUserQueuePermissions.Read);
                    break;
                case "WRITE":
                    grantedPermissions.add(EUserQueuePermissions.Write);
                    break;
            }
        }

        // Remove old permissions
        if (u.getQueuePermissions().containsKey(queueName))
        {
            u.getQueuePermissions().remove(queueName);
        }

        // Add new permissions
        u.getQueuePermissions().put(queueName, grantedPermissions);

        try
        {
            UsersManager.UpdateUser(senderUser);
        }
        catch (IOException ex)
        {
            sender.SendData("ERROR " + ex.getMessage());
            return;
        }

        sender.SendData("OK" + fETX);
    }

    private void SelectQueue(TCPClientConnection sender, String strData) throws ClientIsDisconnectedException, OutgoingPacketFailedException
    {
        strData = strData.toUpperCase();
        String queueName = strData.replace("SELECT", "").trim();
        User senderUser = (User) sender.getTag();

        if (!QueueManager.QueueExists(queueName)) // Check if queue exists
        {
            sender.SendData("ERROR 1" + fETX);
        }
        else
        {
            // Check if user has permissions for the Queue
            if (UserAuthenticationManager.UserHasPermissionsForQueue(senderUser.getName(), queueName))
            {
                sender.SendData("OK" + fETX);
            }
            else // User does not have permissions for the given Queue
            {
                sender.SendData("ERROR You dont have any active permissions on Queue '" + queueName + "'" + fETX);
            }
        }
    }

    private void CreateQueue(TCPClientConnection sender, String strData) throws ClientIsDisconnectedException, OutgoingPacketFailedException
    {
        String queueName = strData.replace("CREATE QUEUE", "").trim();
        User senderUser = (User) sender.getTag();

        // Check if user has permission to Create Queue
        if (!senderUser.CanCreateNewQueues())
        {
            sender.SendData("ERROR You dont have the required permissions to create Queues" + fETX);
        }
        else
        {
            if (QueueManager.QueueExists(queueName)) // Check if Queue exists
            {
                sender.SendData("ERROR 1" + fETX);
            }
            else // Create Queue
            {
                try // Create Queue
                {
                    QueueManager.CreateQueue(queueName);
                    sender.SendData("OK" + fETX);
                }
                catch (IOException ex)
                {
                    sender.SendData("ERROR " + ex.getMessage() + fETX);
                }
            }
        }
    }

    private void CreateUser(TCPClientConnection sender, String strData) throws ClientIsDisconnectedException, OutgoingPacketFailedException
    {
        User senderUser = (User) sender.getTag();
        String[] splittedStr = strData.split(" ");

        if (!senderUser.CanCreateNewUsers())
        {
            sender.SendData("ERROR You dont have the required permissions to create users" + fETX);
        }
        else
        {
            try
            {
                UsersManager.AddUser(splittedStr[2], splittedStr[3]);
                sender.SendData("OK" + fETX);
            }
            catch (IOException ex)
            {
                sender.SendData("ERROR " + ex.getMessage() + fETX);
            }
        }
    }

    private void DropQueue(TCPClientConnection sender, String strData) throws ClientIsDisconnectedException, OutgoingPacketFailedException
    {
        User senderUser = (User) sender.getTag();
        String queueName = strData.replace("DROP QUEUE", "").trim();

        if (!senderUser.CanDropQueues())
        {
            sender.SendData("ERROR You dont have the required permissions to drop queue" + fETX);
        }
        else
        {
            if (!QueueManager.QueueExists(queueName))
            {
                sender.SendData("ERROR 1" + fETX); // Queue does not exist
            }
            else
            {
                try
                {
                    QueueManager.DropQueue(queueName);
                    sender.SendData("OK" + fETX);
                }
                catch (Exception ex)
                {
                    sender.SendData("ERROR " + ex.getMessage() + fETX);
                }
            }
        }
    }

    private void DropUser(TCPClientConnection sender, String strData) throws ClientIsDisconnectedException, OutgoingPacketFailedException
    {
        User senderUser = (User) sender.getTag();
        String username = strData.replace("DROP USER", "").trim();

        if (!senderUser.CanDropUsers())
        {
            sender.SendData("ERROR You dont have the required permissions to drop users" + fETX);
        }
        else
        {
            try
            {
                UsersManager.DropUser(username);
                sender.SendData("OK" + fETX);
            }
            catch (Exception ex)
            {
                sender.SendData("ERROR " + ex.getMessage() + fETX);
            }
        }
    }

    @Override
    public void OnClientConnect(TCPClientConnection client)
    {
        //System.out.println("Client " + client.getIPAddress() + " connected");
    }

    @Override
    public void OnClientDisconnect(TCPClientConnection client)
    {
        //System.out.println("Client " + client.getIPAddress() + " disconnected");
    }
}