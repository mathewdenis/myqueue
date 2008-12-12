/* myQueue
 * Copyright (C) 2008 Nikos Siatras
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
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
    private long fLastMessageID = 0;  // Holds the last message id.
    private long fFirstInMessage = 1; // Holds the first message id.
    public long fMessageCount = 0;    // Holds the number of messages are stored in disk.
    private String fLocation;
    private String fPriorityInteger;

    public MessageManager(String location, String priorityInteger)
    {
        fLocation = location;
        fPriorityInteger = priorityInteger;
        fDataReader = new DataReader(location, "mqf");
        fDataWriter = new DataWriter(location, "mqf");
    }

    public byte[] Dequeue()
    {
        try
        {
            String fileName = fPriorityInteger + String.valueOf(fFirstInMessage);
            byte[] bytes = fDataReader.ReadFile(fileName);
            if (bytes != null)
            {
                File file = new File(fLocation + "\\" + fileName + ".mqf");
                file.delete();

                fFirstInMessage++;
                fMessageCount--;
            }

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
            return fDataReader.ReadFile(fPriorityInteger + String.valueOf(fFirstInMessage));
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
            // The fileName is the messageID.
            return fDataReader.ReadFile(messageID);
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
            fLastMessageID++;
            String lastMessageIDStr = fPriorityInteger + String.valueOf(fLastMessageID);

            byte[] finalBytes = new byte[bytes.length + lastMessageIDStr.length() + 1];

            System.arraycopy(bytes, 0, finalBytes, lastMessageIDStr.length() + 1, bytes.length);

            for (int i = 0; i < lastMessageIDStr.length(); i++)
            {
                finalBytes[i] = (byte) lastMessageIDStr.charAt(i);
            }
            finalBytes[lastMessageIDStr.length()] = '\n';

            fDataWriter.WriteFile(finalBytes, fPriorityInteger + String.valueOf(fLastMessageID));

            fMessageCount++;
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
            // Create my folder.
            File myFolder = new File(fLocation);
            if (!myFolder.exists())
            {
                myFolder.mkdirs();
            }

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
                    int tmpID = Integer.parseInt(file.getName().replace(fPriorityInteger, "").replace(".mqf", ""));

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
        }
        catch (Exception ex)
        {
        }
    }

    public void ClearMessages()
    {
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

        for (File file : files)
        {
            try
            {
                file.delete();
            }
            catch (Exception ex)
            {
            }
        }
        fMessageCount = 0;
    }

    public long getMessageCount()
    {
        return fMessageCount;
    }
}
