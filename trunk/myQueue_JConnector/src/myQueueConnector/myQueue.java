package myQueueConnector;

/**
 *
 * @author Nikos Siatras
 */
public class myQueue
{

    private myQueueConnector fMyConnector;
    private String fName;

    public myQueue(myQueueConnector connector, String name)
    {
        fMyConnector = connector;
        fName = name;
    }
}
