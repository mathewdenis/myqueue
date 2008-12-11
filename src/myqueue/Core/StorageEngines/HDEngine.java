package myqueue.Core.StorageEngines;

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

        fNormalPriorityMessageManager = new MessageManager(location, "PN");
        fAboveNormalPriorityMessageManager = new MessageManager(location + "\\AboveNormalPriority", "PA");
        fHighPriorityMessageManager = new MessageManager(location + "\\HighPriority", "PH");
    }

    @Override
    public synchronized byte[] Dequeue()
    {
        synchronized (fSyncObject)
        {
            byte[] bytesToReturn;

            // Check if there is a message in the HighPriority queue.
            bytesToReturn = fHighPriorityMessageManager.Dequeue();
            if (bytesToReturn != null)
            {
                return bytesToReturn;
            }

            // Check if there is a message in the AboveNormalPriority queue.
            bytesToReturn = fAboveNormalPriorityMessageManager.Dequeue();
            if (bytesToReturn != null)
            {
                return bytesToReturn;
            }

            // Check if there is a message in the NormalPriority queue.
            return fNormalPriorityMessageManager.Dequeue();
        }
    }

    @Override
    public synchronized byte[] Peek()
    {
        synchronized (fSyncObject)
        {
            byte[] bytesToReturn;

            // Check if there is a message in the HighPriority queue.
            bytesToReturn = fHighPriorityMessageManager.Peek();
            if (bytesToReturn != null)
            {
                return bytesToReturn;
            }

            // Check if there is a message in the AboveNormalPriority queue.
            bytesToReturn = fAboveNormalPriorityMessageManager.Peek();
            if (bytesToReturn != null)
            {
                return bytesToReturn;
            }

            // Check if there is a message in the NormalPriority queue.
            return fNormalPriorityMessageManager.Peek();
        }
    }

    @Override
    public synchronized byte[] GetMessageByID(String messageID)
    {
        synchronized (fSyncObject)
        {
            byte[] bytesToReturn;

            // Check if there is a message in the HighPriority queue.
            bytesToReturn = fHighPriorityMessageManager.GetMessageByID(messageID);
            if (bytesToReturn != null)
            {
                return bytesToReturn;
            }

            // Check if there is a message in the AboveNormalPriority queue.
            bytesToReturn = fAboveNormalPriorityMessageManager.GetMessageByID(messageID);
            if (bytesToReturn != null)
            {
                return bytesToReturn;
            }

            // Check if there is a message in the NormalPriority queue.
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
                case 0: // Normal priority
                    return fNormalPriorityMessageManager.Enqueue(bytes);

                case 1: // Above normal priority.
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
