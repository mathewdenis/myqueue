package myqueue.Connector.Exceptions;

/**
 *
 * @author Nikos Siatras
 */
public class DequeueMessageException extends Exception
{

    public DequeueMessageException(String message)
    {
        super(message);
    }
}
