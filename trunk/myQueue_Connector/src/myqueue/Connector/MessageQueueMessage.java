package myqueue.Connector;

/**
 *
 * @author Nikos Siatras
 */
public class MessageQueueMessage
{

    private long fID;
    private String fBinaryData;

    public MessageQueueMessage(long id, String data)
    {
        fID = id;
        fBinaryData = data;
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
}
