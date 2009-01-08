/* myQueue
 * Copyright (C) 2008-2009 Nikos Siatras
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
package myqueue.Connector;

/**
 *
 * @author Nikos Siatras
 */
public class MessageQueueMessage
{

    private String fID;
    private String fBinaryData;
    private int fPriority = 0;
    public static int PriorityNormal = 0;
    public static int PriorityAboveNormal = 1;
    public static int PriorityHigh = 2;
    private String fSplitter = String.valueOf(((char) 3));

    public MessageQueueMessage(String id, String data)
    {
        fID = id;
        fBinaryData = RevertSpecialCharacters(data);
    }

    public MessageQueueMessage(String id, String data, int priority)
    {
        fID = id;
        fBinaryData = RevertSpecialCharacters(data);
        fPriority = priority;
    }

    // For Dequeue,Peek and Receive methods.
    private String RevertSpecialCharacters(String data)
    {
        if (data == null)
        {
            return null;
        }
        return data.replace("#_!3!_#", String.valueOf(fSplitter));
    }

    /**
     * Message ID.
     * @return the message's ID.
     */
    public String getID()
    {
        return fID;
    }

    /**
     * The message's data.
     * @return message's data.
     */
    public String getData()
    {
        return fBinaryData;
    }

    /**
     * Set message's data.
     */
    public void setData(String data)
    {
        fBinaryData = data;
    }

    /**
     * Message priority.
     * @return message's priority.
     */
    public int getPriority()
    {
        return fPriority;
    }
}
