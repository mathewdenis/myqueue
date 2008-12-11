package myqueue.Core;

import java.util.LinkedList;

/**
 *
 * @author Nikos Siatras
 */
public class IntegerQueue extends LinkedList
{

    public synchronized void enqueue(int element)
    {
        add(element);
    }

    public synchronized int dequeue()
    {
        return Integer.valueOf(removeFirst().toString());
    }
}


