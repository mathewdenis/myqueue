package myqueueworkbench.UI.ConnectionsPopUpActionListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import myqueueworkbench.UI.ConnectionTreeNode;
import myqueueworkbench.frmMain;

/**
 *
 * @author Nikos Siatras
 */
public class CloseConnectionActionListener implements ActionListener
{

    private frmMain fMainForm;
    private ConnectionTreeNode fConnectionNode;

    public CloseConnectionActionListener(frmMain mainForm, ConnectionTreeNode connectionNode)
    {
        fMainForm = mainForm;
        fConnectionNode = connectionNode;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        fConnectionNode.getConnection().fConnected = false;
        fMainForm.RebuildConnectionsPopUp(fConnectionNode);
    }
}
