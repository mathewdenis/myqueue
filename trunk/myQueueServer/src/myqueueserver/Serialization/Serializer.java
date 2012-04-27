package myqueueserver.Serialization;

import java.io.*;

/**
 *
 * @author Nikos Siatras
 */
public class Serializer
{

    public Serializer()
    {
    }

    public static byte[] Serialize(Object obj) throws IOException
    {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        ObjectOutputStream o = new ObjectOutputStream(b);
        o.writeObject(obj);
        return b.toByteArray();
    }

    public static Object Deserialize(byte[] bytes) throws IOException, ClassNotFoundException
    {
        ByteArrayInputStream b = new ByteArrayInputStream(bytes);
        ObjectInputStream o = new ObjectInputStream(b);
        return o.readObject();
    }
}
