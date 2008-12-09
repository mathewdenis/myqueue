/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myqueue.Core.File;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Nikos Siatras
 */
public class DataWriter
{

    private String fLocation,  fFileExtension;

    /**
     * Initializes new DataWriter.
     * @param location is the path (folder) in witch the files will be written.
     * @param fileExtension is the files extension (ex. "tmp" or "txt").
     */
    public DataWriter(String location, String fileExtension)
    {
        fLocation = location;
        fFileExtension = "." + fileExtension;
    }

    public void WriteFile(String data, String fileName) throws IOException
    {
        FileWriter writer = new FileWriter(fLocation + "\\" + fileName + "." + fFileExtension);
        writer.write(data);
        writer.close();
    }

    public void WriteFile(String data, int offset, int length, String fileName) throws IOException
    {
        FileWriter writer = new FileWriter(fLocation + "\\" + fileName + fFileExtension);
        writer.write(data, offset, length);
        writer.close();
    }

    public void WriteFile(byte[] bytes, String fileName) throws FileNotFoundException, IOException
    {
        FileOutputStream writer = new FileOutputStream(fLocation + "\\" + fileName + fFileExtension);
        writer.write(bytes);
        writer.close();
    }

    public void WriteFile(byte[] bytes, int offset, int length, String fileName) throws FileNotFoundException, IOException
    {
        FileOutputStream writer = new FileOutputStream(fLocation + "\\" + fileName + fFileExtension);
        writer.write(bytes, offset, length);
        writer.close();
    }

    public String getLocation()
    {
        return fLocation;
    }

    public void setLocation(String value)
    {
        fLocation = value;
    }

    public String getFileExtension()
    {
        return fFileExtension;
    }

    public void setFileExtension(String value)
    {
        fFileExtension = value;
    }
}
