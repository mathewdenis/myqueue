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
package myqueue.Core.StorageEngines;

/**
 *
 * @author Nikos Siatras
 */
public class StorageEngine
{

    private String fLocation;

    public StorageEngine(String location)
    {
        fLocation = location;
    }

    public byte[] Dequeue()
    {
        return null;
    }

    public byte[] Peek()
    {
        return null;
    }

    public byte[] GetMessageByID(String messageID)
    {
        return null;
    }

    public String Enqueue(byte[] bytes)
    {
        return null;
    }

    public void StartEngine()
    {
    }

    public String getLocation()
    {
        return fLocation;
    }

    public void Clear()
    {
    }
}
