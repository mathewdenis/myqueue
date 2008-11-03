package MyQueue;

import Extasys.DataFrame;
import Extasys.Network.TCP.Server.Listener.TCPClientConnection;
import MyQueue.StorageEngine.StorageEngine;
import java.io.IOException;

/**
 *
 * @author Nikos Siatras
 */
public class MyQueue extends Extasys.Network.TCP.Server.ExtasysTCPServer
{

    public String fName,  fDescription;
    public StorageEngine fEngine;

    public MyQueue(String name, String description, StorageEngine engine, int corePoolSize, int maxPoolSize)
    {
        super(name, description, corePoolSize, maxPoolSize);
        fName = name;
        fDescription = description;
        fEngine = engine;
    }

    @Override
    public void Start() throws IOException, Exception
    {
        super.Start();
    }

    @Override
    public void Stop()
    {
        super.Stop();
    }

    @Override
    public void OnClientConnect(TCPClientConnection client)
    {
    }

    @Override
    public void OnClientDisconnect(TCPClientConnection client)
    {
    }

    @Override
    public void OnDataReceive(TCPClientConnection client, DataFrame data)
    {
    }

    public StorageEngine getEngine()
    {
        return fEngine;
    }
}
