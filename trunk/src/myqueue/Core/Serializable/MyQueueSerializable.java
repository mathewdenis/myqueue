package myqueue.Core.Serializable;

import java.io.Serializable;
import java.util.ArrayList;
import myqueue.Core.*;

/**
 *
 * @author Nikos Siatras
 */
public class MyQueueSerializable implements Serializable
{
    // This class is only used from QueueManager Save and Load methods.

    public String Name,  Description,  Location;
    public EStorageEngine Engine;
    public int CorePoolsSize,  MaxPoolSize;
    public int ConnectionsTimeOut;
    public ArrayList TCPListeners = new ArrayList();
    public boolean Running;
}
