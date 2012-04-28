package myQueueConnector.Exceptions;

/**
 *
 * @author Nikos Siatras
 */
public class QueueDoesNotExistException extends Exception
{

    public QueueDoesNotExistException()
    {
        super("Queue does not exist");
    }
}
