package myqueue.Core.StorageEngines;

import java.io.File;
import java.io.FileFilter;
import myqueue.Core.IntegerQueue;
import myqueue.Core.MessageManager;

/**
 *
 * @author Nikos Siatras
 */
public class HDEngine extends StorageEngine
{

    private MessageManager fNormalPriorityMessageManager;
    private MessageManager fAboveNormalPriorityMessageManager;
    private MessageManager fHighPriorityMessageManager;
    private String fLocation;
    private final Object fSyncObject = new Object();

    public HDEngine(String location)
    {
        super(location);
        fLocation = location;

        fNormalPriorityMessageManager = new MessageManager(location);
        fAboveNormalPriorityMessageManager = new MessageManager(location + "\\AboveNormalPriority");
        fHighPriorityMessageManager = new MessageManager(location + "\\HighPriority");
    }

    @Override
    public synchronized byte[] Dequeue()
    {
        synchronized (fSyncObject)
        {
            return fNormalPriorityMessageManager.Dequeue();
        }
    }

    @Override
    public synchronized byte[] Peek()
    {
        synchronized (fSyncObject)
        {
            return fNormalPriorityMessageManager.Peek();
        }
    }

    @Override
    public synchronized byte[] GetMessageByID(String messageID)
    {
        synchronized (fSyncObject)
        {
            return fNormalPriorityMessageManager.GetMessageByID(messageID);
        }
    }

    @Override
    public synchronized String Enqueue(byte[] bytes)
    {
        synchronized (fSyncObject)
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
            return fNormalPriorityMessageManager.Enqueue(bytes);
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
    public void Clear()
    {
        synchronized (fSyncObject)
        {
            fNormalPriorityMessageManager.ClearMessages();
            fAboveNormalPriorityMessageManager.ClearMessages();
            fHighPriorityMessageManager.ClearMessages();
        }
    }
}
