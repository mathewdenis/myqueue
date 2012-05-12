/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myqueueworkbench.UI;

import java.awt.Point;
import java.awt.event.*;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.tree.TreePath;
import myqueueworkbench.Connections.Connection;
import myqueueworkbench.Connections.ConnectionsManager;

/**
 *
 * @author Nikos Siatras
 */
public class PopUpHandler implements ActionListener
{

    private JTree fMyTree;
    private JPopupMenu fPopUpMenu;
    private Point fPoint;

    public PopUpHandler(JTree tree, JPopupMenu popup)
    {
        fMyTree = tree;
        fPopUpMenu = popup;
        tree.addMouseListener(MyMouseAdapter);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        String ac = e.getActionCommand();
        TreePath path = fMyTree.getPathForLocation(fPoint.x, fPoint.y);

        System.out.println("path = " + path);


        if (ac.equals("OPEN CONNECTION"))
        {
            String connectionName = path.getLastPathComponent().toString();
            Connection con = ConnectionsManager.getConnection(connectionName);
            if (con != null)
            {
                con.fConnected = true;
            }

        }
        if (ac.equals("CLOSE CONNECTION"))
        {
            String connectionName = path.getLastPathComponent().toString();
            Connection con = ConnectionsManager.getConnection(connectionName);
            if (con != null)
            {
                con.fConnected = false;
            }
        }
    }
    private MouseListener MyMouseAdapter = new MouseAdapter()
    {

        private void checkForPopup(MouseEvent e)
        {
            if (e.isPopupTrigger())
            {
                fPoint = e.getPoint();
                fPopUpMenu.show(fMyTree, fPoint.x, fPoint.y);
            }
        }

        @Override
        public void mousePressed(MouseEvent e)
        {
            checkForPopup(e);
        }

        @Override
        public void mouseReleased(MouseEvent e)
        {
            checkForPopup(e);
        }

        @Override
        public void mouseClicked(MouseEvent e)
        {
            checkForPopup(e);
        }
    };
}