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

import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import myqueue.Core.MyQueue;
import myqueue.Core.QueueManager;
import myqueue.UI.PropertiesViews.MainProperties;
import myqueue.UI.PropertiesViews.MyQueueServerInfo;
import myqueue.UI.PropertiesViews.MyQueueServerProperties;

/**
 *
 * @author Nikos Siatras
 */
public class frmMain extends javax.swing.JFrame
{

    private TrayIcon fTrayIcon;
    private DefaultMutableTreeNode fMyQueueNode;
    private MyJTreeCellRenderer fMyJTreeCellRenderer;
    private MainProperties fMainPropertiesView;
    private MyQueueServerProperties fMyQueueServerPropertiesView;
    private MyQueueServerInfo fMyQueueServerInfoView;

    public frmMain()
    {
        try
        {
            fMyQueueNode = new DefaultMutableTreeNode("myQueue Servers");
            fMyJTreeCellRenderer = new MyJTreeCellRenderer(this);
            fMainPropertiesView = new MainProperties(this);
            fMyQueueServerPropertiesView = new MyQueueServerProperties(this);
            fMyQueueServerInfoView = new MyQueueServerInfo(this);
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, "1" + ex.getMessage());
        }

        initComponents();

        try
        {
            ImageIcon titleImage = new ImageIcon(getClass().getResource("/Images/data-server-16x16.png"));
            this.setIconImage(titleImage.getImage());
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, "/Images/data-server-16x16.png does not exists");
        }

        // Create Root treeNode
        try
        {
            jTree1.setCellRenderer(fMyJTreeCellRenderer);
            ((DefaultTreeModel) jTree1.getModel()).insertNodeInto(fMyQueueNode, (DefaultMutableTreeNode) jTree1.getModel().getRoot(), 0);
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, "Create Root failed!");
        }

        try
        {
            jDesktopPane1.add(fMainPropertiesView);
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, "Failed to create Main Properties View!");
        }
        try
        {
            jDesktopPane1.add(fMyQueueServerPropertiesView);
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, "Failed to create  MyQueue Server Properties View!");
        }
        try
        {
            jDesktopPane1.add(fMyQueueServerInfoView);
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, "Failed to create MyQueue Server Info View!");
        }


        ShowMainView();

        // Load myQueueUI.properties.
        try
        {
            FileInputStream fis = null;
            ObjectInputStream in = null;
            fis = new FileInputStream("myQueueUI.properties");
            in = new ObjectInputStream(fis);

            MainFormStatus status = (MainFormStatus) in.readObject();

            this.setBounds((int) status.X, (int) status.Y, status.Width, status.Height);
        }
        catch (Exception ex)
        {
        }

        ShowForm();

        Update();

        try
        {
            if (SystemTray.isSupported())
            {
                SystemTray tray = SystemTray.getSystemTray();

                String trayIconPath = "";

                if (tray.getTrayIconSize().width < 17 && tray.getTrayIconSize().height < 17)
                {
                    // 16 x 16 pixels tray image.
                    trayIconPath = "/Images/data-server-16x16.png";
                    ImageIcon trayImage = new ImageIcon(getClass().getResource(trayIconPath));
                    fTrayIcon = new TrayIcon(trayImage.getImage());
                }
                else
                {
                    // Bigger than 16 x 16 pixels tray image.
                    trayIconPath = "/Images/data-server-32x32.png";
                    ImageIcon trayImage = new ImageIcon(getClass().getResource(trayIconPath));
                    fTrayIcon = new TrayIcon(trayImage.getImage().getScaledInstance(tray.getTrayIconSize().width, tray.getTrayIconSize().height, 0));
                }

                fTrayIcon.setToolTip("myQueue Server");

                fTrayIcon.setPopupMenu(popupMenuTray);
                tray.add(fTrayIcon);
            }
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, "Cannot set tray icon!");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        popupMenuTray = new java.awt.PopupMenu();
        menuItemShowServer = new java.awt.MenuItem();
        jPopupMenuMyQueueServers = new javax.swing.JPopupMenu();
        jMenuItemNewQueue = new javax.swing.JMenuItem();
        jMenuItemStart = new javax.swing.JMenuItem();
        jMenuItemStop = new javax.swing.JMenuItem();
        jMenuItemEdit = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JSeparator();
        jMenuItemDelete = new javax.swing.JMenuItem();
        jMenuItemClear = new javax.swing.JMenuItem();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();

        menuItemShowServer.setLabel("Show");
        menuItemShowServer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemShowServerActionPerformed(evt);
            }
        });
        popupMenuTray.add(menuItemShowServer);

        jPopupMenuMyQueueServers.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
                jPopupMenuMyQueueServersPopupMenuWillBecomeVisible(evt);
            }
        });

        jMenuItemNewQueue.setText("New Queue");
        jMenuItemNewQueue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemNewQueueActionPerformed(evt);
            }
        });
        jPopupMenuMyQueueServers.add(jMenuItemNewQueue);

        jMenuItemStart.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Button-Play-16x16.png"))); // NOI18N
        jMenuItemStart.setText("Start");
        jMenuItemStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemStartActionPerformed(evt);
            }
        });
        jPopupMenuMyQueueServers.add(jMenuItemStart);

        jMenuItemStop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Button-Stop-16x16.png"))); // NOI18N
        jMenuItemStop.setText("Stop");
        jMenuItemStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemStopActionPerformed(evt);
            }
        });
        jPopupMenuMyQueueServers.add(jMenuItemStop);

        jMenuItemEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/notes-edit-16x16.png"))); // NOI18N
        jMenuItemEdit.setText("Edit");
        jMenuItemEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemEditActionPerformed(evt);
            }
        });
        jPopupMenuMyQueueServers.add(jMenuItemEdit);
        jPopupMenuMyQueueServers.add(jSeparator1);

        jMenuItemDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Button-Delete-16x16.png"))); // NOI18N
        jMenuItemDelete.setText("Delete");
        jMenuItemDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemDeleteActionPerformed(evt);
            }
        });
        jPopupMenuMyQueueServers.add(jMenuItemDelete);

        jMenuItemClear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/trash-full-16x16.png"))); // NOI18N
        jMenuItemClear.setText("Clear");
        jMenuItemClear.setToolTipText("Clear all messages stored by this queue.");
        jMenuItemClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemClearActionPerformed(evt);
            }
        });
        jPopupMenuMyQueueServers.add(jMenuItemClear);

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("myQueue 2.0.0.5");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jScrollPane2.setPreferredSize(new java.awt.Dimension(104, 324));

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("myQueue");
        jTree1.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jTree1.setComponentPopupMenu(jPopupMenuMyQueueServers);
        jTree1.setRootVisible(false);
        jTree1.setShowsRootHandles(true);
        jTree1.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                jTree1ValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(jTree1);

        jMenu1.setText("File");

        jMenuItem1.setText("New Queue");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("Exit");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Help");

        jMenuItem3.setText("About");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem3);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jDesktopPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 617, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jDesktopPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 456, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 456, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuItem1ActionPerformed
    {//GEN-HEADEREND:event_jMenuItem1ActionPerformed
        frmNewQueue frm = new frmNewQueue(this);
        frm.setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    // Exit.
    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuItem2ActionPerformed
    {//GEN-HEADEREND:event_jMenuItem2ActionPerformed
        Exit();
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void Exit()
    {
        int answer = JOptionPane.showConfirmDialog(null, "Exit myQueue server?", "Exit", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (answer == JOptionPane.YES_OPTION)
        {
            // Save form status.
            MainFormStatus status = new MainFormStatus();
            status.Height = this.getHeight();
            status.Width = this.getWidth();
            status.X = this.getLocation().getX();
            status.Y = this.getLocation().getY();

            FileOutputStream fos = null;
            ObjectOutputStream out = null;
            try
            {
                fos = new FileOutputStream("myQueueUI.properties");
                out = new ObjectOutputStream(fos);
                out.writeObject(status);
                out.close();
            }
            catch (IOException ex)
            {
                System.err.println(ex.getMessage());
            }

            QueueManager.StopAllQueues();
            this.dispose();

            // Dipose Properties Views.
            fMainPropertiesView.Dispose();
            fMyQueueServerPropertiesView.Dispose();

            System.exit(0);
        }
    }

    // Start queue.
    private void jMenuItemStartActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuItemStartActionPerformed
    {//GEN-HEADEREND:event_jMenuItemStartActionPerformed
        java.awt.EventQueue.invokeLater(new Runnable()
        {

            @Override
            public void run()
            {
                try
                {
                    TreePath selectedPath = jTree1.getSelectionPath();
                    DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) (selectedPath.getLastPathComponent());
                    String selectedServer = selectedNode.getUserObject().toString();
                    QueueManager.StartQueue(selectedServer);
                    Update();

                    if (fMyQueueServerPropertiesView.fMyQueueServerName.equals(selectedServer))
                    {
                        fMyQueueServerPropertiesView.Update();
                    }
                }
                catch (Exception ex)
                {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }//GEN-LAST:event_jMenuItemStartActionPerformed

    // Stop queue.
    private void jMenuItemStopActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuItemStopActionPerformed
    {//GEN-HEADEREND:event_jMenuItemStopActionPerformed
        try
        {
            TreePath selectedPath = jTree1.getSelectionPath();
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) (selectedPath.getLastPathComponent());
            String selectedServer = selectedNode.getUserObject().toString();
            QueueManager.StopQueue(selectedServer);
            Update();

            if (fMyQueueServerPropertiesView.fMyQueueServerName.equals(selectedServer))
            {
                fMyQueueServerPropertiesView.Update();
            }
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR);
        }
    }//GEN-LAST:event_jMenuItemStopActionPerformed

    // Edit queue.
    private void jMenuItemEditActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuItemEditActionPerformed
    {//GEN-HEADEREND:event_jMenuItemEditActionPerformed
        try
        {
            TreePath selectedPath = jTree1.getSelectionPath();
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) (selectedPath.getLastPathComponent());
            String selectedNodeText = selectedNode.getUserObject().toString();

            ShowMyQueueServerPropertiesView(selectedNodeText);
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR);
        }
    }//GEN-LAST:event_jMenuItemEditActionPerformed

    // Delete queue.
    private void jMenuItemDeleteActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuItemDeleteActionPerformed
    {//GEN-HEADEREND:event_jMenuItemDeleteActionPerformed
        try
        {
            TreePath selectedPath = jTree1.getSelectionPath();
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) (selectedPath.getLastPathComponent());
            String selectedServer = selectedNode.getUserObject().toString();
            int answer = JOptionPane.showConfirmDialog(null, "Delete " + selectedServer + "?", "Delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (answer == JOptionPane.YES_OPTION)
            {
                ShowMainView();
                QueueManager.DeleteQueue(selectedServer);
                Update();
            }
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR);
        }
    }//GEN-LAST:event_jMenuItemDeleteActionPerformed

    // Clear queue.
    private void jMenuItemClearActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuItemClearActionPerformed
    {//GEN-HEADEREND:event_jMenuItemClearActionPerformed
        java.awt.EventQueue.invokeLater(new Runnable()
        {

            @Override
            public void run()
            {
                try
                {
                    TreePath selectedPath = jTree1.getSelectionPath();
                    DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) (selectedPath.getLastPathComponent());
                    String selectedServer = selectedNode.getUserObject().toString();
                    int answer = JOptionPane.showConfirmDialog(null, "Clear " + selectedServer + " messages?", "Clear Queue", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                    if (answer == JOptionPane.YES_OPTION)
                    {
                        QueueManager.ClearQueue(selectedServer);
                        Update();
                    }
                }
                catch (Exception ex)
                {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR);
                }
            }
        });
    }//GEN-LAST:event_jMenuItemClearActionPerformed

    // Closing form.
    private void formWindowClosing(java.awt.event.WindowEvent evt)//GEN-FIRST:event_formWindowClosing
    {//GEN-HEADEREND:event_formWindowClosing
        Exit();
    }//GEN-LAST:event_formWindowClosing

    private void menuItemShowServerActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_menuItemShowServerActionPerformed
    {//GEN-HEADEREND:event_menuItemShowServerActionPerformed
        if (menuItemShowServer.getLabel().equals("Show"))
        {
            ShowForm();
        }
        else
        {
            HideForm();
        }
    }//GEN-LAST:event_menuItemShowServerActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        frmAbout frm = new frmAbout();
        frm.setVisible(true);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jPopupMenuMyQueueServersPopupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt)//GEN-FIRST:event_jPopupMenuMyQueueServersPopupMenuWillBecomeVisible
    {//GEN-HEADEREND:event_jPopupMenuMyQueueServersPopupMenuWillBecomeVisible
        TreePath selectedPath = jTree1.getSelectionPath();
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) (selectedPath.getLastPathComponent());

        if (selectedNode.isLeaf() && selectedNode.getParent().equals(fMyQueueNode)) // Queue server selected.
        {
            jMenuItemNewQueue.setVisible(false);

            if (QueueManager.getQueues().containsKey(selectedNode.getUserObject().toString()))
            {
                if (QueueManager.getQueues().get(selectedNode.getUserObject().toString()).isRunning())
                {
                    jMenuItemStart.setVisible(false);
                    jMenuItemStop.setVisible(true);
                }
                else
                {
                    jMenuItemStart.setVisible(true);
                    jMenuItemStop.setVisible(false);
                }
            }
            jMenuItemEdit.setVisible(true);
            jSeparator1.setVisible(true);
            jMenuItemDelete.setVisible(true);
            jMenuItemClear.setVisible(true);
        }
        else
        {
            if (selectedNode.equals(fMyQueueNode))
            {
                jMenuItemNewQueue.setVisible(true);

                jMenuItemStart.setVisible(false);
                jMenuItemStop.setVisible(false);
                jMenuItemEdit.setVisible(false);
                jSeparator1.setVisible(false);
                jMenuItemDelete.setVisible(false);
                jMenuItemClear.setVisible(false);
            }
        }
    }//GEN-LAST:event_jPopupMenuMyQueueServersPopupMenuWillBecomeVisible

    private void jMenuItemNewQueueActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuItemNewQueueActionPerformed
    {//GEN-HEADEREND:event_jMenuItemNewQueueActionPerformed
        frmNewQueue frm = new frmNewQueue(this);
        frm.setVisible(true);
    }//GEN-LAST:event_jMenuItemNewQueueActionPerformed

    private void jTree1ValueChanged(javax.swing.event.TreeSelectionEvent evt)//GEN-FIRST:event_jTree1ValueChanged
    {//GEN-HEADEREND:event_jTree1ValueChanged
        TreePath selectedPath = jTree1.getSelectionPath();
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) (selectedPath.getLastPathComponent());
        String selectedNodeText = selectedNode.getUserObject().toString();

        if (selectedNode == fMyQueueNode)
        {
            ShowMainView();
        }
        else
        {
            if (selectedNode.getParent() == fMyQueueNode) // myQueue server is selected.
            {
                ShowMyQueueInfoView(selectedNodeText);
            }
        }
}//GEN-LAST:event_jTree1ValueChanged

    public void ShowMainView()
    {
        try
        {
            fMainPropertiesView.setVisible(true);
            fMainPropertiesView.setMaximum(true);
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, "Failed to Show Main View!");
        }
        fMyQueueServerPropertiesView.setVisible(false);
        fMyQueueServerInfoView.setVisible(false);
    }

    public void ShowMyQueueServerPropertiesView(String serverName)
    {
        try
        {
            fMainPropertiesView.setVisible(false);

            fMyQueueServerPropertiesView.setVisible(true);
            fMyQueueServerPropertiesView.setMaximum(true);
            fMyQueueServerPropertiesView.SetMyQueueServer(serverName);

            fMyQueueServerInfoView.setVisible(false);
        }
        catch (Exception ex)
        {
        }
    }

    public void ShowMyQueueInfoView(String serverName)
    {
        try
        {
            fMainPropertiesView.setVisible(false);
            fMyQueueServerPropertiesView.setVisible(false);

            fMyQueueServerInfoView.setVisible(true);
            fMyQueueServerInfoView.setMaximum(true);
            fMyQueueServerInfoView.SetMyQueueServer(serverName);
        }
        catch (Exception ex)
        {
            System.err.println(ex.getMessage());
        }
    }

    // Selection changed.
    public DefaultMutableTreeNode AddServerTreeNode(Object serverName, boolean shouldBeVisible)
    {
        DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(serverName);
        ((DefaultTreeModel) jTree1.getModel()).insertNodeInto(childNode, fMyQueueNode, fMyQueueNode.getChildCount());

        if (shouldBeVisible)
        {
            jTree1.scrollPathToVisible(new TreePath(childNode.getPath()));
        }
        return childNode;
    }

    public void RemoveServerNode(String serverName)
    {
        DefaultTreeModel model = (DefaultTreeModel) jTree1.getModel();

        try
        {
            for (int i = 0; i < fMyQueueNode.getChildCount(); i++)
            {
                DefaultMutableTreeNode child = (DefaultMutableTreeNode) fMyQueueNode.getChildAt(i);
                if (child.getUserObject().toString().equals(serverName))
                {
                    model.removeNodeFromParent(child);
                    break;
                }
            }
        }
        catch (Exception ex)
        {
        }
        jTree1.repaint();
    }

    private void HideForm()
    {
        menuItemShowServer.setLabel("Show");
        this.setVisible(false);
    }

    public void ShowForm()
    {
        menuItemShowServer.setLabel("Hide");
        this.setVisible(true);
    }

    public void Update()
    {
        try
        {
            String name, description, location, poolSize;
            jTree1.repaint();
            // Add new myQueue server in tree.
            for (MyQueue queue : QueueManager.getQueues().values())
            {
                boolean existsInTree = false;
                for (int i = 0; i < fMyQueueNode.getChildCount(); i++)
                {
                    DefaultMutableTreeNode serverNode = (DefaultMutableTreeNode) fMyQueueNode.getChildAt(i);
                    if (serverNode.getUserObject().toString().equals(queue.getName()))
                    {
                        existsInTree = true;
                        break;
                    }
                }

                if (!existsInTree)
                {
                    AddServerTreeNode(queue.getName(), true);
                }
            }

            // Remove deleted servers.
            for (int i = fMyQueueNode.getChildCount() - 1; i >= 0; i--)
            {
                boolean serverExists = false;
                DefaultMutableTreeNode serverNode = (DefaultMutableTreeNode) fMyQueueNode.getChildAt(i);
                for (MyQueue queue : QueueManager.getQueues().values())
                {
                    if (queue.getName().equals(serverNode.getUserObject().toString()))
                    {
                        serverExists = true;
                        break;
                    }
                }
                if (!serverExists)
                {
                    RemoveServerNode(serverNode.getUserObject().toString());
                }
            }
        }
        catch (Exception ex)
        {
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItemClear;
    private javax.swing.JMenuItem jMenuItemDelete;
    private javax.swing.JMenuItem jMenuItemEdit;
    private javax.swing.JMenuItem jMenuItemNewQueue;
    private javax.swing.JMenuItem jMenuItemStart;
    private javax.swing.JMenuItem jMenuItemStop;
    public javax.swing.JPopupMenu jPopupMenuMyQueueServers;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTree jTree1;
    private java.awt.MenuItem menuItemShowServer;
    private java.awt.PopupMenu popupMenuTray;
    // End of variables declaration//GEN-END:variables
}
