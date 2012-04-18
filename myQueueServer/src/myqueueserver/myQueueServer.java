package myqueueserver;

import myqueueserver.Config.Config;
import myqueueserver.Log.LogMessageType;
import myqueueserver.Log.ServerLog;
import myqueueserver.Network.Server.MyQueueTCPServer;
import myqueueserver.Queue.QueueManager;
import myqueueserver.Users.UsersManager;

/**
 *
 * @author Nikos Siatras
 */
public class myQueueServer
{

    private static MyQueueTCPServer fServer;

    public static void main(String[] args)
    {
        try
        {
            // Read the config file
            Config.ReadConfigFile();

            UsersManager.Initialize();
            UsersManager.Save();

            QueueManager.Initialize();
            QueueManager.Save();

            // Create Server with readed config settings (listeners, security etc...)
            fServer = new MyQueueTCPServer();
            fServer.Start();

            ServerLog.WriteToLog("Server started", LogMessageType.Information);
        }
        catch (Exception ex)
        {
            ServerLog.WriteToLog(ex.getMessage(), LogMessageType.Error);
        }
    }
}
