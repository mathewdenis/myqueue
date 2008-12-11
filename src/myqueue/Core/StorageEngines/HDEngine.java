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
        return fNormalPriorityMessageManager.Dequeue();
    }

    @Override
    public synchronized byte[] Peek()
    {
        return fNormalPriorityMessageManager.Peek();
    }

    @Override
    public synchronized byte[] GetMessageByID(String messageID)
    {
        return fNormalPriorityMessageManager.GetMessageByID(messageID);
    }

    @Override
    public synchronized String Enqueue(byte[] bytes)
    {
        return fNormalPriorityMessageManager.Enqueue(bytes);
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
}
