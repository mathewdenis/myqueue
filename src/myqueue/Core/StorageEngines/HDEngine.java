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
package myqueue.Core.StorageEngines;

import myqueue.Core.MessageManagers.HDMessageManager;

/**
 *
 * @author Nikos Siatras
 */
public class HDEngine extends StorageEngine
{

    private HDMessageManager fNormalPriorityMessageManager;
    private HDMessageManager fAboveNormalPriorityMessageManager;
    private HDMessageManager fHighPriorityMessageManager;
    private final Object fSyncObject = new Object();

    public HDEngine(String location)
    {
        super(location);

        fNormalPriorityMessageManager = new HDMessageManager(location, "PN");
        fAboveNormalPriorityMessageManager = new HDMessageManager(location + "/AboveNormalPriority", "PA");
        fHighPriorityMessageManager = new HDMessageManager(location + "/HighPriority", "PH");
    }

    @Override
    public byte[] Dequeue()
    {
        synchronized (fSyncObject)
        {
            return fHighPriorityMessageManager.fMessageCount > 0 ? fHighPriorityMessageManager.Dequeue()
                    : fAboveNormalPriorityMessageManager.fMessageCount > 0 ? fAboveNormalPriorityMessageManager.Dequeue()
                    : fNormalPriorityMessageManager.Dequeue();
        }
    }

    @Override
    public byte[] Peek()
    {
        synchronized (fSyncObject)
        {
            return fHighPriorityMessageManager.fMessageCount > 0 ? fHighPriorityMessageManager.Peek()
                    : fAboveNormalPriorityMessageManager.fMessageCount > 0 ? fAboveNormalPriorityMessageManager.Peek()
                    : fNormalPriorityMessageManager.Peek();
        }
    }

    @Override
    public byte[] GetMessageByID(String messageID)
    {
        String priorityIdicator = messageID.substring(0, 2);
        return priorityIdicator.equals("PH") ? fHighPriorityMessageManager.GetMessageByID(messageID)
                : priorityIdicator.equals("PA") ? fAboveNormalPriorityMessageManager.GetMessageByID(messageID)
                : fNormalPriorityMessageManager.GetMessageByID(messageID);
    }

    @Override
    public String Enqueue(byte[] bytes)
    {
        synchronized (fSyncObject)
        {
            int messagePriority = Integer.parseInt(new String(bytes, 0, 1));
            switch (messagePriority)
            {
                case 0: // Normal priority
                    return fNormalPriorityMessageManager.Enqueue(bytes);

                case 1: // Above normal priority
                    return fAboveNormalPriorityMessageManager.Enqueue(bytes);

                case 2: // High priority
                    return fHighPriorityMessageManager.Enqueue(bytes);
            }
            return null;
        }
    }

    @Override
    public void StartEngine()
    {
        try
        {
            fNormalPriorityMessageManager.Start();
            fAboveNormalPriorityMessageManager.Start();
            fHighPriorityMessageManager.Start();
        }
        catch (Exception ex)
        {
        }
    }

    @Override
    public String GetMessagesPack()
    {
        return fNormalPriorityMessageManager.GetMessagesPack() + ":" + fAboveNormalPriorityMessageManager.GetMessagesPack() + ":" + fHighPriorityMessageManager.GetMessagesPack();
    }

    @Override
    public void Clear()
    {
        synchronized (fSyncObject)
        {
            fNormalPriorityMessageManager.ClearMessages();
            fAboveNormalPriorityMessageManager.ClearMessages();
            fHighPriorityMessageManager.ClearMessages();
        }
    }

    @Override
    public long getMessageCount()
    {
        return fNormalPriorityMessageManager.getMessageCount() + fAboveNormalPriorityMessageManager.getMessageCount() + fHighPriorityMessageManager.getMessageCount();
    }

    @Override
    public long getNormalPriorityMessageCount()
    {
        return fNormalPriorityMessageManager.getMessageCount();
    }

    @Override
    public long getAboveNormalPriorityMessageCount()
    {
        return fAboveNormalPriorityMessageManager.getMessageCount();
    }

    @Override
    public long getHighPriorityMessageCount()
    {
        return fHighPriorityMessageManager.getMessageCount();
    }
}
