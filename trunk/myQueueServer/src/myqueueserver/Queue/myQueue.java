package myqueueserver.Queue;

import java.io.Serializable;

/**
 *
 * @author Nikos Siatras
 */
public class myQueue implements Serializable
{

    private String fName, fSaveLocation;

    public myQueue(String name, String saveLocation)
    {
        fName = name;
        fSaveLocation = saveLocation;
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
}
