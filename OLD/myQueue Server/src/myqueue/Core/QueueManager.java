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
package myqueue.Core;

import myqueue.Core.StorageEngines.EStorageEngine;
import myqueue.Core.Serializable.MyQueueSerializable;
import Extasys.Network.TCP.Server.Listener.TCPListener;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import javax.swing.JOptionPane;
import myqueue.Core.Serializable.TCPListenerSerializable;
import myqueue.Core.StorageEngines.HDEngine;
import myqueue.UI.frmLoading;

/**
 *
 * @author Nikos Siatras
 */
public class QueueManager
{

    private static Hashtable<String, MyQueue> fQueues = new Hashtable<String, MyQueue>();
    private static frmLoading fProgress;

    public QueueManager()
    {
    }

    public static void CreateNewQueue(String name, String description, String location, int corePoolSize, int maxPoolSize, ArrayList listeners, int connectionsTimeOut, boolean journalRecording) throws Exception
    {
        name = name.trim();
        description = description.trim();


        // Check if name is unique.
        if (fQueues.containsKey(name))
        {
            throw new Exception("Please select an other name for this Queue.\nThis name is allready in use.");
        }

        if (name.equals(""))
        {
            throw new Exception("Please give a name to this queue.");
        }

        if (corePoolSize < 10)
        {
            throw new Exception("Core pool size must be greater than 10.");
        }

        if (maxPoolSize < corePoolSize)
        {
            throw new Exception("Max pool size must be greater than core poolsize.");
        }

        /*if (listeners != null && listeners.size() == 0)
        {
        throw new Exception("Please add one or more listeners to this queue.");
        }*/

        try
        {
            if (location != null)
            {
                File tmpFile = new File(location);
                if (!tmpFile.exists())
                {
                    throw new Exception("Please select a valid location for this queue.");
                }
            }
        }
        catch (Exception ex)
        {
            throw new Exception("Please select a valid location for this queue.");
        }

        HDEngine engine = new HDEngine(location);
        MyQueue queue = new MyQueue(name, description, engine, corePoolSize, maxPoolSize, connectionsTimeOut, journalRecording);

        if (listeners != null)
        {
            for (int i = 0; i < listeners.size(); i++)
            {
                TCPListener listener = (TCPListener) listeners.get(i);
                queue.AddListener(listener.getName(), listener.getIPAddress(), listener.getPort(), listener.getMaxConnections(), listener.getReadBufferSize(), listener.getConnectionTimeOut(), 100, listener.getMessageSplitter());
            }
        }

        fQueues.put(name, queue);
        Save();
    }

    public static void StartQueue(String name) throws IOException, Exception
    {
        if (fQueues.containsKey(name))
        {
            MyQueue queue = ((MyQueue) fQueues.get(name));
            if (queue.getEngine().getLocation() == null || queue.getEngine().getLocation().equals(""))
            {
                throw new Exception("This queue needs a valid location in disk to save the messages.\nPlease select that location and then start the queue.");
            }
            ((MyQueue) fQueues.get(name)).Start();
            SaveQueue(name);
        }
        else
        {
            throw new Exception("Queue " + name + " does not exist.");
        }
    }

    public static void StopQueue(String name) throws Exception
    {
        if (fQueues.containsKey(name))
        {
            ((MyQueue) fQueues.get(name)).Stop();
            SaveQueue(name);
        }
        else
        {
            throw new Exception("Queue " + name + " does not exist.");
        }
    }

    public static void StopAllQueues()
    {
        for (MyQueue queue : fQueues.values())
        {
            queue.Stop();
        }
    }

    public static void DeleteQueue(String name) throws Exception
    {
        if (fQueues.containsKey(name))
        {
            ((MyQueue) fQueues.get(name)).Dispose();
            fQueues.remove(name);

            try
            {
                File savedQueueFile = new File("MyQueueActiveQueues/" + name + ".queue");
                savedQueueFile.delete();
            }
            catch (Exception ex)
            {
            }
        }
        else
        {
            throw new Exception("Queue " + name + " does not exist.");
        }
    }

    public static void ClearQueue(String name) throws Exception
    {
        if (fQueues.containsKey(name))
        {
            ((MyQueue) fQueues.get(name)).Clear();
            JOptionPane.showMessageDialog(null, "Queue " + name + " cleared!", "Clear", JOptionPane.INFORMATION_MESSAGE);
        }
        else
        {
            throw new Exception("Queue " + name + " does not exist.");
        }
    }

    private static void Save()
    {
        // Check if file QueueData exists.
        File tmpFolder = new File("MyQueueActiveQueues");
        if (!tmpFolder.exists())
        {
            tmpFolder.mkdirs();
        }

        for (MyQueue queue : fQueues.values())
        {
            SaveQueue(queue.getName());
        }
    }

    private static void SaveQueue(String queueName)
    {
        // Check if file QueueData exists.
        File tmpFolder = new File("MyQueueActiveQueues");
        if (!tmpFolder.exists())
        {
            tmpFolder.mkdirs();
        }

        MyQueue queue = null;
        if (!fQueues.containsKey(queueName))
        {
            return;
        }

        queue = fQueues.get(queueName);

        MyQueueSerializable tmp = new MyQueueSerializable();

        tmp.Name = queue.getName();
        tmp.Description = queue.getDescription();
        tmp.Location = queue.getEngine().getLocation();
        tmp.Running = queue.isRunning();
        tmp.CorePoolsSize = queue.getCorePoolSize();
        tmp.MaxPoolSize = queue.getMaxPoolSize();
        if (queue.getEngine().getClass().equals(new HDEngine("").getClass()))
        {
            tmp.Engine = EStorageEngine.HDEngine;
        }
        tmp.JournalRecording = queue.isJournalRecording();

        // Listeners.
        for (int i = 0; i < queue.getListeners().size(); i++)
        {
            TCPListenerSerializable tmpListener = new TCPListenerSerializable();
            TCPListener listener = (TCPListener) queue.getListeners().get(i);
            tmpListener.IPAddress = listener.getIPAddress();
            tmpListener.MaxConnections = listener.getMaxConnections();
            tmpListener.Port = listener.getPort();
            tmpListener.Splitter = listener.getMessageSplitter();
            tmp.ConnectionsTimeOut = listener.getConnectionTimeOut();
            tmp.TCPListeners.add(tmpListener);
        }

        FileOutputStream fos = null;
        ObjectOutputStream out = null;
        try
        {
            fos = new FileOutputStream("MyQueueActiveQueues/" + tmp.Name + ".queue");
            out = new ObjectOutputStream(fos);
            out.writeObject(tmp);
            out.close();
        }
        catch (IOException ex)
        {
            System.err.println(ex.getMessage());
        }
    }

    public static void Load(frmLoading progress)
    {
        fProgress = progress;
        Thread loadThread = new Thread(new Runnable()
        {

            @Override
            public void run()
            {
                try
                {
                    File dir = new File("MyQueueActiveQueues");

                    File[] files = dir.listFiles();
                    
                    // This filter only returns files.
                    FileFilter fileFilter = new FileFilter()
                    {

                        @Override
                        public boolean accept(File file)
                        {
                            return !file.isDirectory() && file.getName().endsWith("queue");
                        }
                    };
                    files = dir.listFiles(fileFilter);

                    // Set maximum value to progress.
                    fProgress.SetMaximumProgressValue(files.length);

                    FileInputStream fis = null;
                    ObjectInputStream in = null;
                    for (File file : files)
                    {
                        try
                        {
                            fProgress.UpdateStatus("Loading " + file.getName());
                            fis = new FileInputStream(file.getPath());
                            in = new ObjectInputStream(fis);
                            MyQueueSerializable tmp = (MyQueueSerializable) in.readObject();

                            // Listeners.
                            ArrayList listeners = new ArrayList();
                            for (int i = 0; i < tmp.TCPListeners.size(); i++)
                            {
                                TCPListenerSerializable tmpListener = (TCPListenerSerializable) tmp.TCPListeners.get(i);
                                TCPListener listener = new TCPListener("", tmpListener.IPAddress, tmpListener.Port, tmpListener.MaxConnections, 65535, tmp.ConnectionsTimeOut, 100, tmpListener.Splitter);
                                listeners.add(listener);
                            }

                            String queueFileName = file.getName().substring(0, file.getName().lastIndexOf("."));
                            CreateNewQueue(queueFileName, tmp.Description, tmp.Location, tmp.CorePoolsSize, tmp.MaxPoolSize, listeners, tmp.ConnectionsTimeOut, tmp.JournalRecording);

                            if (tmp.Running)
                            {
                                StartQueue(tmp.Name);
                            }

                            in.close();
                        }
                        catch (Exception ex)
                        {
                            System.err.println(ex.getMessage());
                        }

                        fProgress.ProgressPlus();
                    }
                }
                catch (Exception ex)
                {
                }
                fProgress.LoadFinished();
            }
        });
        loadThread.start();
    }

    public static Hashtable<String, MyQueue> getQueues()
    {
        return fQueues;
    }
}
