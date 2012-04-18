package myqueueserver.Config;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

/**
 *
 * @author Nikos Siatras
 */
public class Config
{

    public static int fServerPort, fMaxConnections;
    public static int fReadBufferSize;
    public static int fCorePoolSize, fMaxPoolSize;
    public static ArrayList<String> fBindAddresses = new ArrayList<>();

    public Config()
    {
    }

    public static void ReadConfigFile() throws Exception
    {
        java.io.File currentDir = new java.io.File("");
        String applicationPath = currentDir.getAbsolutePath();

        String configPath = applicationPath + "\\myqueue.cnf";
        File confFile = new File(configPath);

        if (!confFile.exists())
        {
            throw new Exception("File 'myqueue.cnf' does not exist!");
        }
        else if (!confFile.canRead())
        {
            throw new Exception("File 'myqueue.cnf' exists but has no required read permissions!");
        }

        FileInputStream fstream = new FileInputStream(confFile);

        Properties properties = new Properties();
        properties.load(fstream);

        fServerPort = Integer.parseInt(properties.get("port").toString());
        fMaxConnections = Integer.parseInt(properties.get("max_connections").toString());
        fReadBufferSize = Integer.parseInt(properties.get("read_buffer_size").toString());

        String bindAddresses = properties.getProperty("bind-address");
        fBindAddresses.addAll(Arrays.asList(bindAddresses.split(",")));

        fCorePoolSize = Integer.parseInt(properties.get("core_pool_size").toString());
        fMaxPoolSize = Integer.parseInt(properties.get("max_pool_size").toString());
    }
}
