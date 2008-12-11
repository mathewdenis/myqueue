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
    private final Object fSyncObject = new Object();

    public HDEngine(String location)
    {
        super(location);

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
            if (messageID.startsWith("PH")) // High priority message.
            {
                return fHighPriorityMessageManager.GetMessageByID(messageID);
            }
            else if (messageID.startsWith("PA")) // Above normal priority message.
            {
                return fAboveNormalPriorityMessageManager.GetMessageByID(messageID);
            }
            return fNormalPriorityMessageManager.GetMessageByID(messageID); // Normal priority message.
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
