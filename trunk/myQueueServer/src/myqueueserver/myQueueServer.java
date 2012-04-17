package myqueueserver;

import myqueueserver.Config.Config;
import myqueueserver.Log.LogMessageType;
import myqueueserver.Log.ServerLog;

/**
 *
 * @author Nikos Siatras
 */
public class myQueueServer
{

    public static void main(String[] args)
    {
        try
        {
            Config.ReadConfigFile();
            ServerLog.WriteToLog("Server started", LogMessageType.Information);
        }
        catch (Exception ex)
        {
            ServerLog.WriteToLog(ex.getMessage(), LogMessageType.Error);
        }
    }
}
