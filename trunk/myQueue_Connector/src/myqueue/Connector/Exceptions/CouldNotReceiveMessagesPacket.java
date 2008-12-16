package myqueue.Connector.Exceptions;

/**
 *
 * @author Nikos Siatras
 */
public class CouldNotReceiveMessagesPacket extends Exception
{

    public CouldNotReceiveMessagesPacket()
    {
        super("Could not receive eqneued messages packet.");
    }
}
