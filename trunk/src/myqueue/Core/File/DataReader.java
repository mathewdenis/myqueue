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

    public byte[] ReadBytes(String fileName) throws IOException
    {
        File file = new File(fLocation + "\\" + fileName + fFileExtension);
        InputStream is = new FileInputStream(file);

        long length = file.length();

        /*if (length > Integer.MAX_VALUE)
        {
        // File is too large
        }*/

        byte[] bytes = new byte[(int) length];

        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0)
        {
            offset += numRead;
        }

        if (offset < bytes.length)
        {
            throw new IOException("Could not completely read file " + file.getName());
        }

        is.close();
        return bytes;
    }
}

