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
package myqueue.Connector;

import java.util.concurrent.ArrayBlockingQueue;

/**
 *
 * @author Nikos Siatras
 */
public class ObjectQueue extends ArrayBlockingQueue
{

    private final Object fSync = new Object();

    public ObjectQueue()
    {
        super(100000);
    }

    public void enqueue(Object element)
    {
        synchronized (fSync)
        {
            add(element);
        }
    }

    public Object dequeue()
    {
        synchronized (fSync)
        {
            return remove();
        }
    }
}

