package myqueueserver.Queue;

import java.io.Serializable;
import myqueueserver.Queue.Engines.QueueEngine;

/**
 *
 * @author Nikos Siatras
 */
public class myQueue implements Serializable
{

    private String fName, fSaveLocation;
    private QueueEngine fEngine;

    private myQueue(String name, String saveLocation, QueueEngine engine)
    {
        fName = name;
        fSaveLocation = saveLocation;
        fEngine = engine;
    }

    public void setName(String name)
    {
        fName = name;
    }

    public String getName()
    {
        return fName;
    }

    public void setSaveLocation(String location)
    {
        fSaveLocation = location;
    }

    public String getSaveLocation()
    {
        return fSaveLocation;
    }

    public void setEngine(QueueEngine engine)
    {
        fEngine = engine;
    }

    public QueueEngine getEngine()
    {
        return fEngine;
    }
}
