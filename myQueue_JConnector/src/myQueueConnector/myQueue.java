package myQueueConnector;

import Extasys.DataFrame;
import Extasys.Network.TCP.Client.Exceptions.ConnectorCannotSendPacketException;
import Extasys.Network.TCP.Client.Exceptions.ConnectorDisconnectedException;
import myQueueConnector.Exceptions.CommandTimeOutException;

/**
 *
 * @author Nikos Siatras
 */
public class myQueue
{

    private myQueueConnection fConnection;
    private String fQueueName;

    public myQueue(myQueueConnection connection, String queueName)
    {
        fConnection = connection;
        fQueueName = queueName;
    }

    public DataFrame SendToServer(String data) throws ConnectorDisconnectedException, ConnectorCannotSendPacketException, CommandTimeOutException, Exception
    {
        if (!fConnection.fIsConnected)
        {
            fConnection.Open();
        }

        return fConnection.SendToServer(data);
    }

    public myQueueConnection getConnection()
    {
        return fConnection;
    }
}
