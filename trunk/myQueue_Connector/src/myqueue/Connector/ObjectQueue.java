package myqueue.Connector;

import java.util.LinkedList;

/**
 *
 * @author Nikos Siatras
 */
public class ObjectQueue extends LinkedList
{

    public synchronized void enqueue(Object element)
    {
        add(element);
    }

    public synchronized Object dequeue()
    {
        return removeFirst();
    }
}

