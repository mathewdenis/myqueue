package myqueueworkbench.UI.ConnectionsPopUpActionListeners;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import javax.swing.JOptionPane;
import myQueueConnector.myQueueConnection;
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
        fConnectionNode.getConnection().fConnected = false;
        try
        {
            myQueueConnection con = fConnectionNode.getConnection().getQueueConnection();
            con.Open();

            // Get user authorized Queues
            String userPermissions = new String(con.SendToServer("SHOW PERMISSIONS FOR CURRENT_USER").getBytes());
            fConnectionNode.getConnection().fConnected = true;
            con.close();
            
            fMainForm.SelectedConnectionChanged();
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        fMainForm.RebuildConnectionsPopUp(fConnectionNode);
    }
}
