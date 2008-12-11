package myqueue.Core;

import java.io.File;
import java.io.FileFilter;
import myqueue.Core.File.DataReader;
import myqueue.Core.File.DataWriter;

/**
 *
 * @author Nikos Siatras
 */
public class MessageManager
{

    private DataReader fDataReader;
    private DataWriter fDataWriter;
    private long fLastMessageID = 0;  // This long holds the last message id.
    private long fFirstInMessage = 1; // This long holds the first message id.
    private String fLocation;
    private final Object fSyncObject = new Object();

    public MessageManager(String location)
    {
        fLocation = location;
        fDataReader = new DataReader(location, "mqf");
        fDataWriter = new DataWriter(location, "mqf");
    }

    public byte[] Dequeue()
    {
        try
        {
            String fileName = "F_" + String.valueOf(fFirstInMessage);
            byte[] bytes = fDataReader.ReadBytes(fileName);
            File file = new File(fLocation + "\\" + fileName + ".mqf");
            file.delete();

            fFirstInMessage++;

            return bytes;
        }
        catch (Exception ex)
        {
            return null;
        }
    }

    public byte[] Peek()
    {
        try
        {
            String fileName = "F_" + String.valueOf(fFirstInMessage);
            return fDataReader.ReadBytes(fileName);
        }
        catch (Exception ex)
        {
            return null;
        }
    }

    public byte[] GetMessageByID(String messageID)
    {
        try
        {
            String fileName = "F_" + messageID;
            return fDataReader.ReadBytes(fileName);
        }
        catch (Exception ex)
        {
            return null;
        }
    }

    public String Enqueue(byte[] bytes)
    {
        try
        {
            String messagePriorityStr = new String(bytes, 0, 1);
            int messagePriority = Integer.parseInt(messagePriorityStr);
            switch (messagePriority)
            {
                case 1: // Above normal priority.
                    // fAboveNormalPriorityQueue.add(messagePriority);
                    break;
                case 2: // High priority
                    // fHighPriorityQueue.add(messagePriority);
                    break;
            }

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
            return lastMessageIDStr;
        }
        catch (Exception ex)
        {
            return null;
        }

    }

    public void Start()
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

            long maxID = 0;
            long minID = 0;
            for (File file : files)
            {
                try
                {
                    int tmpID = Integer.parseInt(file.getName().replace("F_", "").replace(".mqf", ""));

                    maxID = Math.max(maxID, tmpID);

                    if (minID == 0)
                    {
                        minID = tmpID;
                    }
                    else
                    {
                        minID = Math.min(tmpID, minID);
                    }
                }
                catch (Exception ex)
                {
                }
            }
            fLastMessageID = maxID;
            fFirstInMessage = minID;
            if (fFirstInMessage == 0)
            {
                fFirstInMessage = 1;
            }

            // Create my folder.
            File myFolder = new File(fLocation);
            if (!myFolder.exists())
            {
                myFolder.mkdirs();
            }
        }
        catch (Exception ex)
        {
        }
    }
}
