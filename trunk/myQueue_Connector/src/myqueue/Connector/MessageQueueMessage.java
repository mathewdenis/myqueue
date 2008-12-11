package myqueue.Connector;

/**
 *
 * @author Nikos Siatras
 */
public class MessageQueueMessage
{

    private long fID;
    private String fBinaryData;
    private int fPriority = 0;

    public static int PriorityNormal = 0;
    public static int PriorityAboveNormal = 1;
    public static int PriorityHigh = 2;


    public MessageQueueMessage(long id, String data)
    {
        fID = id;
        fBinaryData = data;
    }

    public MessageQueueMessage(long id, String data,int priority)
    {
        fID = id;
        fBinaryData = data;
        fPriority = priority;
    }

    /**
     * Message ID.
     * @return the message's ID.
     */
    public long getID()
    {
        return fID;
    }

    /**
     * The message's data.
     * @return message's data.
     */
    public String getData()
    {
        return fBinaryData;
    }

    /**
     * Message priority.
     * @return
     */
    public int getPriority()
    {
        return fPriority;
    }
}
