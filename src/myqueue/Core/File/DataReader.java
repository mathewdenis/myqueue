/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myqueue.Core.File;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author Nikos Siatras
 */
public class DataReader
{

    private String fLocation,  fFileExtension;

    public DataReader(String location, String fileExtension)
    {
        fLocation = location;
        fFileExtension = "." + fileExtension;
    }

    public String ReadFile(String fileName) throws IOException
    {
        try
        {
            StringBuilder answer = new StringBuilder();

            FileReader reader = new FileReader(fLocation + "\\" + fileName + fFileExtension);
            BufferedReader bufRead = new BufferedReader(reader);

            int character = bufRead.read();
            while (character > -1)
            {
                answer.append((char) character);
                character = bufRead.read();
            }

            bufRead.close();
            return answer.toString();
        }
        catch (Exception ex)
        {
            return null;
        }
    }

    public byte[] ReadBytes(String fileName) throws IOException
    {
        try
        {
            File file = new File(fLocation + "\\" + fileName + fFileExtension);
            InputStream inputStream = new FileInputStream(file);

            long length = file.length();
            byte[] bytes = new byte[(int) length];

            inputStream.read(bytes, 0, bytes.length);

            inputStream.close();
            return bytes;
        }
        catch (Exception ex)
        {
            return null;
        }
    }
}

