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
            myQueueConnection con = new myQueueConnection(fConnectionNode.getConnection().fServerIP, fConnectionNode.getConnection().fServerPort, fConnectionNode.getConnection().fUsername, fConnectionNode.getConnection().fPassword);
            con.Open();
            con.Close();
            fConnectionNode.getConnection().fConnected = true;
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        fMainForm.RebuildConnectionsPopUp(fConnectionNode);
    }
}
