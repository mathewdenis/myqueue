package myqueueserver.Queue;

import java.io.Serializable;

/**
 *
 * @author Nikos Siatras
 */
public class myQueue implements Serializable
{

    private String fName;

    public myQueue(String name)
    {
        fName = name;
    }

    public void setName(String name)
    {
        fName = name;
    }

    public String getName()
    {
        return fName;
    }
}
