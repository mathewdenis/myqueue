package myqueueworkbench.UI.ConnectionTabs;

/**
 *
 * @author Nikos Siatras
 */
public class frmServerStatusUpdateThread extends Thread
{

    private frmServerStatus fStatusForm;
    private boolean fKeepRunning = false;

    public frmServerStatusUpdateThread(frmServerStatus frm)
    {
        fStatusForm = frm;
    }

    @Override
    public void start()
    {
        fKeepRunning = true;
        super.start();
    }

    @Override
    public void run()
    {
        while (fKeepRunning)
        {
            fStatusForm.RefreshServerStatus();

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
