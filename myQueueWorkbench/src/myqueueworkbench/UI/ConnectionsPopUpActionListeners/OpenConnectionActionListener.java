package myqueueworkbench.UI.ConnectionsPopUpActionListeners;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import myqueueworkbench.UI.ConnectionTreeNode;
import myqueueworkbench.frmMain;
import myqueueworkbench.frmNewConnection;

/**
 *
 * @author Nikos Siatras
 */
public class OpenConnectionActionListener implements ActionListener
{
    
    private frmMain fMainForm;
    private ConnectionTreeNode fConnectionNode;
    
    public OpenConnectionActionListener(frmMain mainForm, ConnectionTreeNode connectionNode)
    {
        fMainForm = mainForm;
        fConnectionNode = connectionNode;
    }
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        fConnectionNode.getConnection().fConnected = true;
        fMainForm.RebuildConnectionsPopUp(fConnectionNode);
    }
}
