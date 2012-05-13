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
                    fMainForm.UpdateRemoteMachineStatus();
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
                Thread.sleep(2000);
            }
            catch (Exception ex)
            {
            }
        }
    }
}
