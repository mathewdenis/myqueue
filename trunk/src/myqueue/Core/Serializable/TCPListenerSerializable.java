package myqueue.Core.Serializable;

import java.io.Serializable;
import java.net.InetAddress;

/**
 *
 * @author Nikos Siatras
 */
public class TCPListenerSerializable implements Serializable
{

    public InetAddress IPAddress;
    public int Port;
    public int MaxConnections;
}
