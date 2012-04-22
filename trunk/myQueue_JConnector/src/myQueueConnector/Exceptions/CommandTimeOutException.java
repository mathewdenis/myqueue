package myQueueConnector.Exceptions;

/**
 *
 * @author Nikos Siatras
 */
public class CommandTimeOutException extends Exception
{

    public CommandTimeOutException()
    {
        super("Command failed to get response from the server!");
    }
}
