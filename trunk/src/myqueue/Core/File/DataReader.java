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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author Nikos Siatras
 */
public class DataReader
{

    private String fLocation,  fFileExtension;

    /**
     * Initializes new DataReader.
     * @param location is the path (folder) in witch the files will be readed from.
     * @param fileExtension is the files extension (ex. "tmp" or "txt").
     */
    public DataReader(String location, String fileExtension)
    {
        fLocation = location + "/";
        fFileExtension = "." + fileExtension;
    }

    /**
     * Read file.
     * @param fileName is the file's name without the file's extension.
     * @return byte array.
     * @throws java.io.IOException
     */
    public byte[] ReadFile(String fileName) throws IOException
    {
        try
        {
            File file = new File(fLocation + fileName + fFileExtension);
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

