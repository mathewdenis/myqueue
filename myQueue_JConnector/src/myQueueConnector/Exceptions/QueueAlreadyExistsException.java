package myQueueConnector.Exceptions;

/**
 *
 * @author Nikos Siatras
 */
public class QueueAlreadyExistsException extends Exception
{

    public QueueAlreadyExistsException()
    {
        super("Queue already exists");
    }
}
