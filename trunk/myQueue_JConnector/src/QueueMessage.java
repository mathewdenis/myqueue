
/**
 *
 * @author Nikos Siatras
 */
public class QueueMessage
{

    private long fID;
    private byte[] fData;
    private int fPriority;
    public static int PriorityNormal = 0;
    public static int PriorityAboveNormal = 1;
    public static int PriorityHigh = 2;

    public QueueMessage()
    {
    }

    public QueueMessage(byte[] data, int priority)
    {
        fData = data;
        fPriority = priority;
    }

    public byte[] getData()
    {
        return fData;
    }

    public void setData(byte[] data)
    {
        fData = data;
    }

    public int getPriority()
    {
        return fPriority;
    }

    public void setPriority(int priority)
    {
        fPriority = priority;
    }
}
