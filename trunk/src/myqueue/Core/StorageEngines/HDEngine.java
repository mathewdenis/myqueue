package myqueue.Core.StorageEngines;

import java.io.File;
import java.io.IOException;
import myqueue.Core.File.DataReader;
import myqueue.Core.File.DataWriter;

/**
 *
 * @author Nikos Siatras
 */
public class HDEngine extends StorageEngine
{

    private DataReader fDataReader;
    private DataWriter fDataWriter;
    private long fLastMessageID = 0; // This long holds the last message id.
    private String fLocation;

    public HDEngine(String location)
    {
        super(location);
        fLocation = location;
        fDataReader = new DataReader(location, "mqf");
        fDataWriter = new DataWriter(location, "mqf");
    }

    @Override
    public byte[] Dequeue()
    {
        try
        {
            String fileName = "F_" + String.valueOf(fLastMessageID);
            byte[] bytes = fDataReader.ReadBytes(fileName);
            File file = new File(fLocation + "\\" + fileName + ".mqf");
            file.delete();
            return bytes;
        }
        catch (Exception ex)
        {
            return null;
        }
    }

    @Override
    public byte[] Peek()
    {
        try
        {
            String fileName = "F_" + String.valueOf(fLastMessageID);
            return fDataReader.ReadBytes(fileName);
        }
        catch (Exception ex)
        {
            return null;
        }
    }

    @Override
    public boolean Enqueue(String data)
    {
        try
        {
            fLastMessageID++;
            fDataWriter.WriteFile(data, "F_" + String.valueOf(fLastMessageID));
            return true;
        }
        catch (IOException ex)
        {
            return false;
        }
    }

    @Override
    public boolean Enqueue(byte[] bytes)
    {
        try
        {
            fLastMessageID++;
            fDataWriter.WriteFile(bytes, "F_" + String.valueOf(fLastMessageID));
            return true;
        }
        catch (IOException ex)
        {
            return false;
        }
    }
}
