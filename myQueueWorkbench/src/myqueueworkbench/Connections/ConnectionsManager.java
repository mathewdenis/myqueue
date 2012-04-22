package myqueueworkbench.Connections;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import myqueueworkbench.Serialization.Serializer;

/**
 *
 * @author Nikos Siatras
 */
public class ConnectionsManager
{

    private static boolean fInitialized = false;
    private static String fSaveFilename = "Connections.dat";
    private static ArrayList<Connection> fConnections;

    public ConnectionsManager()
    {
    }

    public static void Initialize() throws FileNotFoundException, IOException, ClassNotFoundException
    {
        if (!fInitialized)
        {
            File file = new File(fSaveFilename);
            InputStream is = new FileInputStream(file);
            long length = file.length();
            byte[] bytes = new byte[(int) length];
            int offset = 0;
            int numRead = 0;
            while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0)
            {
                offset += numRead;
            }

            fConnections = (ArrayList<Connection>) Serializer.Deserialize(bytes);
        }
    }

    public static void Save() throws IOException
    {
        byte[] bytes = Serializer.Serialize(fConnections);

        File f = new File(fSaveFilename);
        if (!f.exists())
        {
            f.setReadable(true);
            f.setWritable(true);
            f.createNewFile();
        }

        try (FileOutputStream stream = new FileOutputStream(fSaveFilename))
        {
            stream.write(bytes);
            stream.close();
        }
    }

    public ArrayList<Connection> getConnections()
    {
        return fConnections;
    }
}
