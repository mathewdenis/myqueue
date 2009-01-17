package myqueue.UI;

import java.awt.Component;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import myqueue.Core.MyQueue;
import myqueue.Core.QueueManager;

/**
 *
 * @author Nikos Siatras
 */
public class MyJTreeCellRenderer extends DefaultTreeCellRenderer
{

    private Icon fActiveServerIcon = new ImageIcon(getClass().getResource("/Images/enable-server-16x16.png"));
    private Icon fDisabledServerIcon = new ImageIcon(getClass().getResource("/Images/desable-server-16x16.png"));
    private frmMain fMain;

    public MyJTreeCellRenderer(frmMain frm)
    {
        fMain = frm;
    }

    @Override
    public Component getTreeCellRendererComponent(
            JTree tree,
            Object value,
            boolean sel,
            boolean expanded,
            boolean leaf,
            int row,
            boolean hasFocus)
    {

        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

        if (leaf && !value.toString().equals("myQueue Servers"))
        {
            if (IsServerActive(value))
            {
                setIcon(fActiveServerIcon);
            }
            else
            {
                setIcon(fDisabledServerIcon);
            }
        }
        else
        {
        }

        return this;
    }

    private boolean IsServerActive(Object value)
    {
        String serverName = value.toString();
        if (QueueManager.getQueues().containsKey(serverName))
        {
            MyQueue queue = QueueManager.getQueues().get(serverName);
            if (queue.isRunning())
            {
                return true;
            }
        }
        return false;
    }
}

