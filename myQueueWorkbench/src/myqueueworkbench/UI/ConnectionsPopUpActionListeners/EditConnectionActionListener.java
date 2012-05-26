package myqueueworkbench.UI.ConnectionsPopUpActionListeners;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import myqueueworkbench.UI.ConnectionTreeNode;
import myqueueworkbench.frmMain;
import myqueueworkbench.UI.frmNewConnection;

/**
 *
 * @author Nikos Siatras
 */
public class EditConnectionActionListener implements ActionListener
{

    private frmMain fMainForm;
    private ConnectionTreeNode fConnectionNode;

    public EditConnectionActionListener(frmMain mainForm, ConnectionTreeNode connectionNode)
    {
        fMainForm = mainForm;
        fConnectionNode = connectionNode;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frmNewConnection frm = new frmNewConnection(fMainForm, fConnectionNode.getConnection());

        int w = frm.getSize().width;
        int h = frm.getSize().height;
        int x = (dim.width - w) / 2;
        int y = (dim.height - h) / 2;

        frm.setLocation(x, y);
        frm.setVisible(true);

    }
}
