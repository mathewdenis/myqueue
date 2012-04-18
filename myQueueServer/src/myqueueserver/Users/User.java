package myqueueserver.Users;

import java.io.Serializable;

/**
 *
 * @author Nikos Siatras
 */
public class User implements Serializable
{

    private String fName, fPassword;

    public User(String name, String password)
    {
        fName = name;
        fPassword = password;
    }

    public void setName(String name)
    {
        fName = name;
    }

    public String getName()
    {
        return fName;
    }

    public void setPassword(String password)
    {
        fPassword = password;
    }

    public String getPassword()
    {
        return fPassword;
    }
}
