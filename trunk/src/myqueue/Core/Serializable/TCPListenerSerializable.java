package myqueue.Core.Serializable;

import java.io.Serializable;
import java.net.InetAddress;

/**
 *
 * @author Nikos Siatras
 */
public class TCPListenerSerializable implements Serializable
{
    // This class is only used from QueueManager Save and Load methods.

    public InetAddress IPAddress;
    public int Port;
    public int MaxConnections;
    public String Splitter;
}
