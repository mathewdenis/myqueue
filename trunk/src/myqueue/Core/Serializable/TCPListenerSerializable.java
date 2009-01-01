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
package myqueue.Core.Serializable;

import java.io.Serializable;
import java.net.InetAddress;

/**
 *
 * @author Nikos Siatras
 */
public class TCPListenerSerializable implements Serializable
{
    // This class is only used from QueueManager Save and Load methods.

    public InetAddress IPAddress;
    public int Port;
    public int MaxConnections;
    public String Splitter;
}
