package myqueueserver.File;

import java.io.*;

/**
 *
 * @author Nikos Siatras
 */
public class FileManager
{

    public FileManager()
    {
    }

    public static void WriteFile(byte[] bytes, String filepath) throws FileNotFoundException, IOException
    {
        File file = new File(filepath);
        try (FileOutputStream stream = new FileOutputStream(filepath))
        {
            stream.write(bytes);
            stream.close();
        }
    }

    public static byte[] ReadFile(String filepath) throws FileNotFoundException, IOException
    {
        File file = new File(filepath);
        if (file.exists() && file.canRead())
        {
            InputStream is = new FileInputStream(file);

            long length = file.length();
            byte[] bytes = new byte[(int) length];

            int offset = 0;
            int numRead = 0;
            while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0)
            {
                offset += numRead;
            }

            return bytes;
        }

        return null;
    }
}
