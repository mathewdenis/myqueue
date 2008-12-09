package myqueue.Connector.Exceptions;

/**
 *
 * @author Nikos Siatras
 */
public class MyQueueConnectorDisconnectedException extends Exception
{

    public MyQueueConnectorDisconnectedException()
    {
        super("Connector is disconnected!");
    }
}
