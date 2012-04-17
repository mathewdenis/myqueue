package myqueueserver;

import myqueueserver.Config.Config;
import myqueueserver.Log.LogMessageType;
import myqueueserver.Log.ServerLog;
import myqueueserver.Network.Server.MyQueueServer;

/**
 *
 * @author Nikos Siatras
 */
public class myQueueServer
{

    private static MyQueueServer fServer;

    public static void main(String[] args)
    {
        try
        {
            // Read the config file
            Config.ReadConfigFile();

            // Create Server with readed config settings (listeners, security etc...)
            fServer = new MyQueueServer();
            fServer.Start();

            ServerLog.WriteToLog("Server started", LogMessageType.Information);
        }
        catch (Exception ex)
        {
            ServerLog.WriteToLog(ex.getMessage(), LogMessageType.Error);
        }
    }
}
