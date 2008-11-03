package MyQueue.StorageEngine;

import MyQueue.File.DataReader;
import MyQueue.File.DataWriter;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author Nikos Siatras
 */
public class HDEngine extends StorageEngine
{

    private DataReader fDataReader;
    private DataWriter fDataWriter;
    private String fLocation;
    private long fLastMessageID = 0; // This long holds the last message id.

    public HDEngine(String location)
    {
        fLocation = location;
        fDataReader = new DataReader(location, "mqf");
        fDataWriter = new DataWriter(location, "mqf");
    }

    @Override
    public byte[] Dequeue(long messageID)
    {
        try
        {
            String fileName = "F_" + String.valueOf(messageID);
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
    public byte[] Peek(long messageID)
    {
        try
        {
            String fileName = "F_" + String.valueOf(messageID);
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
