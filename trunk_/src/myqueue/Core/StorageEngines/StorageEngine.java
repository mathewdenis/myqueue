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

   
    public boolean Enqueue(byte[] bytes)
    {
        return false;
    }

    public void StartEngine()
    {
        
    }

    public String getLocation()
    {
        return fLocation;
    }
}
