package myqueueworkbench.Connections;

import java.io.Serializable;
import java.net.InetAddress;

/**
 *
 * @author Nikos Siatras
 */
public class Connection implements Serializable
{

    public String fName;
    public InetAddress fServerIP;
    public String fUsername, fPassword;
    public int fServerPort;
    public boolean fConnected = false;

    public Connection(String name, InetAddress serverIP, int serverPort, String username, String password)
    {
        fName = name;
        fServerIP = serverIP;
        fServerPort = serverPort;
        fUsername = username;
        fPassword = password;
    }
}
