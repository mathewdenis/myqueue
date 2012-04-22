package myqueueworkbench.Connections;

import java.io.Serializable;

/**
 *
 * @author Nikos Siatras
 */
public class Connection implements Serializable
{

    public String fServerIP, fUsername, fPassword;
    public int fServerPort;

    public Connection(String serverIP, int serverPort, String username, String password)
    {
        fServerIP = serverIP;
        fServerPort = serverPort;
        fUsername = username;
        fPassword = password;
    }
}
