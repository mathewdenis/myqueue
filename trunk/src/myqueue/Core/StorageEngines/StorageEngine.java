package myqueue.Core.StorageEngines;

/**
 *
 * @author Nikos Siatras
 */
public class StorageEngine
{

    private String fLocation;

    public StorageEngine(String location)
    {
        fLocation = location;
    }

    public byte[] Dequeue()
    {
        return null;
    }

    public byte[] Peek()
    {
        return null;
    }

    public byte[] GetMessageByID(String messageID)
    {
        return null;
    }

    public String Enqueue(byte[] bytes)
    {
        return null;
    }

    public void StartEngine()
    {
    }

    public String getLocation()
    {
        return fLocation;
    }

    public void Clear()
    {

    }
}
