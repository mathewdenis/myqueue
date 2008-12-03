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

    public String Name,  Description,  Location;
    public EStorageEngine Engine;
    public int CorePoolsSize,  MaxPoolSize;
    public int ConnectionsTimeOut;
    public ArrayList TCPListeners = new ArrayList();
}
