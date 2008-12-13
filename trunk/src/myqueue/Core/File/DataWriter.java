/* myQueue
 * Copyright (C) 2008 Nikos Siatras
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package myqueue.Core.File;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
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
        fLocation = location + "/";
        fFileExtension = "." + fileExtension;
    }

    /**
     * Write a file.
     * @param data is the string data will be written in the file.
     * @param fileName is the file's name without the file extension.
     * @throws java.io.IOException
     */
    public void WriteFile(String data, String fileName) throws IOException
    {
        FileWriter writer = new FileWriter(fLocation + fileName + fFileExtension);
        writer.write(data);
        writer.close();
    }

    /**
     * Write a file.
     * @param data is the string data will be written in the file.
     * @param offset from which to start writing characters.
     * @param length is the number of characters to write.
     * @param fileName is the file's name without the file extension.
     * @throws java.io.IOException
     */
    public void WriteFile(String data, int offset, int length, String fileName) throws IOException
    {
        FileWriter writer = new FileWriter(fLocation + fileName + fFileExtension);
        writer.write(data, offset, length);
        writer.close();
    }

    /**
     * Write a file.
     * @param bytes is the byte[] data will be written in the file.
     * @param fileName is the file's name without the file extension.
     * @throws java.io.FileNotFoundException
     * @throws java.io.IOException
     */
    public void WriteFile(byte[] bytes, String fileName) throws FileNotFoundException, IOException
    {
        FileOutputStream writer = new FileOutputStream(fLocation + fileName + fFileExtension);
        writer.write(bytes);
        writer.close();
    }

    /**
     * Write a file.
     * @param bytes is the byte[] data will be written in the file.
     * @param offset is the start offset in the bytes.
     * @param length is the number of bytes to write.
     * @param fileName is the file's name without the file extension.
     * @throws java.io.FileNotFoundException
     * @throws java.io.IOException
     */
    public void WriteFile(byte[] bytes, int offset, int length, String fileName) throws FileNotFoundException, IOException
    {
        FileOutputStream writer = new FileOutputStream(fLocation + fileName + fFileExtension);
        writer.write(bytes, offset, length);
        writer.close();
    }
}
