/* myQueue
 * Copyright (C) 2008-2009 Nikos Siatras
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package myqueue.UI;

import java.awt.Component;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
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

    private Icon fActiveServerIcon;
    private Icon fDisabledServerIcon;
    private frmMain fMain;

    public MyJTreeCellRenderer(frmMain frm)
    {
        fMain = frm;
        try
        {
            fActiveServerIcon = new ImageIcon(getClass().getResource("/Images/enable-server-16x16.png"));
            fDisabledServerIcon = new ImageIcon(getClass().getResource("/Images/desable-server-16x16.png"));
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, "Cannot find enable-server-16x16.png and desable-server-16x16.png");
        }
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
        try
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
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, "getTreeCellRendererComponent: " + ex.getMessage());
        }

        return this;
    }

    private boolean IsServerActive(Object value)
    {
        try
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
        }
        catch (Exception ex)
        {
        }
        return false;
    }
}

