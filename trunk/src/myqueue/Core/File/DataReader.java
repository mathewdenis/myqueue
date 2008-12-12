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
        fLocation = location + "\\";
        fFileExtension = "." + fileExtension;
    }

    public String ReadFile(String fileName) throws IOException
    {
        try
        {
            StringBuilder answer = new StringBuilder();

            FileReader reader = new FileReader(fLocation + fileName + fFileExtension);
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

