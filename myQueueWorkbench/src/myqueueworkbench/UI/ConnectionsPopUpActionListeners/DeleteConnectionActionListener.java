package myqueueworkbench.UI.ConnectionsPopUpActionListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import myqueueworkbench.UI.ConnectionTreeNode;
import myqueueworkbench.frmMain;

/**
 *
 * @author Nikos Siatras
 */
public class DeleteConnectionActionListener implements ActionListener
{

    private frmMain fMainForm;
    private ConnectionTreeNode fConnectionNode;

    public DeleteConnectionActionListener(frmMain mainForm, ConnectionTreeNode connectionNode)
    {
        fMainForm = mainForm;
        fConnectionNode = connectionNode;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
    }
}
