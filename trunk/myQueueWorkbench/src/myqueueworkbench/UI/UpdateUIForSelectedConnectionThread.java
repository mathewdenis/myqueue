package myqueueworkbench.UI;

import myQueueConnector.myQueue;
import myQueueConnector.myQueueConnection;
import myqueueworkbench.frmMain;

/**
 *
 * @author Nikos Siatras
 */
public class UpdateUIForSelectedConnectionThread extends Thread
{

    private frmMain fMainForm;
    private boolean fKeepRunning = false;
    private myQueueConnection fMyQueueConnection;

    public UpdateUIForSelectedConnectionThread(frmMain mainForm)
    {
        fMainForm = mainForm;
    }

    @Override
    public synchronized void start()
    {
        fKeepRunning = true;
        super.start();
    }

    public void Abort()
    {
        fKeepRunning = false;
    }

    @Override
    public void run()
    {
        while (fKeepRunning)
        {
            try
            {
                if (fMainForm.fSelectedConnection != null && fMainForm.fSelectedConnection.fConnected)
                {
                    fMyQueueConnection = new myQueueConnection(fMainForm.fSelectedConnection.fServerIP, fMainForm.fSelectedConnection.fServerPort, fMainForm.fSelectedConnection.fUsername, fMainForm.fSelectedConnection.fPassword);
                    fMyQueueConnection.Open();
                    byte[] bytes = fMyQueueConnection.SendToServer("SHOW MACHINE STATUS").getBytes();
                    String str = new String(bytes);
                    fMyQueueConnection.Close();

                    if (!str.isEmpty())
                    {
                        String[] data = str.split("\n");
                        fMainForm.jLabelCPUUsage.setText(data[2].replace("CPU LOAD", "") + "%");
                        fMainForm.jLabelFreeMemory.setText(data[0].replace("FREE MEMORY", ""));
                        fMainForm.jLabelTotalMemory.setText(data[1].replace("TOTAL MEMORY", ""));
                    }
                }
                else
                {
                    fMainForm.jLabelCPUUsage.setText("N/A");
                    fMainForm.jLabelFreeMemory.setText("N/A");
                    fMainForm.jLabelTotalMemory.setText("N/A");
                }
            }
            catch (Exception ex)
            {
            }


            try
            {
                Thread.sleep(1000);
            }
            catch (Exception ex)
            {
            }
        }
    }
}
