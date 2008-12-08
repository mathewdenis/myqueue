package myqueue.Core.StorageEngines;

import java.io.File;
import java.io.FileFilter;
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
    public synchronized byte[] Dequeue()
    {
        try
        {
            String fileName = "F_" + String.valueOf(fLastMessageID);
            byte[] bytes = fDataReader.ReadBytes(fileName);
            File file = new File(fLocation + "\\" + fileName + ".mqf");
            file.delete();
            if (fLastMessageID > 0)
            {
                fLastMessageID--;
            }
            return bytes;
        }
        catch (Exception ex)
        {
            return null;
        }
    }

    @Override
    public synchronized byte[] Peek()
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
    public synchronized boolean Enqueue(byte[] bytes)
    {
        try
        {
            fLastMessageID++;
            String lastMessageIDStr = String.valueOf(fLastMessageID);

            byte[] finalBytes = new byte[bytes.length + lastMessageIDStr.length() + 1];

            System.arraycopy(bytes, 0, finalBytes, lastMessageIDStr.length() + 1, bytes.length);

            for (int i = 0; i < lastMessageIDStr.length(); i++)
            {
                finalBytes[i] = (byte) lastMessageIDStr.charAt(i);
            }
            finalBytes[lastMessageIDStr.length()] = '\n';

            fDataWriter.WriteFile(finalBytes, "F_" + String.valueOf(fLastMessageID));
            return true;
        }
        catch (Exception ex)
        {
            return false;
        }
    }

    @Override
    public void StartEngine()
    {
        try
        {
            // Find the last message id in the engine location.
            File dir = new File(fLocation);
            File[] files = dir.listFiles();

            // This filter only returns files.
            FileFilter fileFilter = new FileFilter()
            {

                @Override
                public boolean accept(File file)
                {
                    return !file.isDirectory() && file.getName().endsWith("mqf");
                }
            };
            files = dir.listFiles(fileFilter);


            int fileID = 0;
            for (File file : files)
            {
                try
                {
                    int tmpID = Integer.parseInt(file.getName().replace("F_", "").replace(".mqf", ""));
                    if (tmpID > fileID)
                    {
                        fileID = tmpID;
                    }
                }
                catch (Exception ex)
                {
                }
            }
            fLastMessageID = fileID;

        }
        catch (Exception ex)
        {
        }
    }
}
