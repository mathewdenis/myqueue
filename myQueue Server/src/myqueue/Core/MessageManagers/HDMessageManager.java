/* myQueue
 * Copyright (C) 2008-2009 Nikos Siatras
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
package myqueue.Core.MessageManagers;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.Comparator;
import myqueue.Core.File.DataReader;
import myqueue.Core.File.DataWriter;

/**
 *
 * @author Nikos Siatras
 */
public class HDMessageManager
{

    private DataReader fDataReader;
    private DataWriter fDataWriter;
    private long fLastMessageID = 0;  // Holds the last message id.
    private long fFirstInMessage = 1; // Holds the first message id.
    public long fMessageCount = 0;    // Holds the number of messages are stored in disk.
    private String fLocation;
    private String fPriorityInteger;

    public HDMessageManager(String location, String priorityInteger)
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

            // Write priority indicator and message in the final bytes.
            byte[] finalBytes = new byte[bytes.length + lastMessageIDStr.length() + 1];
            System.arraycopy(bytes, 0, finalBytes, lastMessageIDStr.length() + 1, bytes.length);

            // Write message id in the beginning of the finalBytes array.
            byte[] lastMessageIDBytes = lastMessageIDStr.getBytes();
            System.arraycopy(lastMessageIDBytes, 0, finalBytes, 0, lastMessageIDBytes.length);

            // \n character after message id.
            finalBytes[lastMessageIDStr.length()] = '\n';

            // Write the message to the disk.
            fDataWriter.WriteFile(finalBytes, fPriorityInteger + String.valueOf(fLastMessageID));

            fMessageCount++;
            return lastMessageIDStr;
        }
        catch (Exception ex)
        {
            return null;
        }
    }

    // Return "min message id-max message id"
    public String GetMessagesPack()
    {
        if (fFirstInMessage > fLastMessageID)
        {
            return "0-0";
        }

        return String.valueOf(fFirstInMessage) + "-" + String.valueOf(fLastMessageID);
    }

    public void Start()
    {
        try
        {
            fMessageCount = 0;
            // Create my folder.
            File myFolder = new File(fLocation);
            if (!myFolder.exists())
            {
                myFolder.mkdirs();
            }

            // Make a list of the message queue messages(files).
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

            // Sort files by ID.
            Arrays.sort(files, new Comparator()
            {

                @Override
                public int compare(Object o1, Object o2)
                {
                    long id1, id2;
                    id1 = Long.parseLong(((File) o1).getName().replace(fPriorityInteger, "").replace(".mqf", ""));
                    id2 = Long.parseLong(((File) o2).getName().replace(fPriorityInteger, "").replace(".mqf", ""));

                    if (id1 < id2)
                    {
                        return -1;
                    }
                    else if (id1 > id2)
                    {
                        return 1;
                    }
                    else
                    {
                        return 0;
                    }
                }
            });

            long maxID = 0;
            long minID = 0;

            if (files.length > 0)
            {
                // minID is the files[0] id.
                minID = Integer.parseInt(files[0].getName().replace(fPriorityInteger, "").replace(".mqf", ""));
                // maxID is the files[files.length - 1] id.
                maxID = Integer.parseInt(files[files.length - 1].getName().replace(fPriorityInteger, "").replace(".mqf", ""));
            }

            fMessageCount = files.length;

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
