/* myQueue
 * Copyright (C) 2008 Nikos Siatras
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
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import myqueue.Core.MyQueue;
import myqueue.Core.QueueManager;

/**
 *
 * @author Nikos Siatras
 */
public class frmMain extends javax.swing.JFrame
{

    private TrayIcon fTrayIcon;

    public frmMain()
    {
        try
        {
            ImageIcon titleImage = new ImageIcon(getClass().getResource("/Images/data-server-16x16.png"));
            this.setIconImage(titleImage.getImage());
        }
        catch (Exception ex)
        {
            System.err.println(ex.getMessage());
        }
        initComponents();

        // Load myQueue.properties.
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
    }

    @Override
    public void setVisible(boolean b)
    {
        super.setVisible(b);
        QueueManager.Load();
        jTableQueues.getColumnModel().getColumn(0).setCellRenderer(new ImageTableCellRenderer());
        Update();

        try
        {
            if (SystemTray.isSupported())
            {
                SystemTray tray = SystemTray.getSystemTray();

                ImageIcon trayImage = new ImageIcon(getClass().getResource("/Images/data-server-16x16.png"));
                fTrayIcon = new TrayIcon(trayImage.getImage());

                fTrayIcon.setToolTip("myQueue");
                fTrayIcon.setPopupMenu(popupMenuTray);
                tray.add(fTrayIcon);
            }
        }
        catch (Exception ex)
        {
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItemStart = new javax.swing.JMenuItem();
        jMenuItemStop = new javax.swing.JMenuItem();
        jMenuItemEdit = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JSeparator();
        jMenuItemInfo = new javax.swing.JMenuItem();
        jMenuItemDelete = new javax.swing.JMenuItem();
        jMenuItemClear = new javax.swing.JMenuItem();
        popupMenuTray = new java.awt.PopupMenu();
        menuItemShowServer = new java.awt.MenuItem();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableQueues = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();

        jPopupMenu1.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
                jPopupMenu1PopupMenuWillBecomeVisible(evt);
            }
        });

        jMenuItemStart.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Button-Play-16x16.png"))); // NOI18N
        jMenuItemStart.setText("Start");
        jMenuItemStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemStartActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItemStart);

        jMenuItemStop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Button-Stop-16x16.png"))); // NOI18N
        jMenuItemStop.setText("Stop");
        jMenuItemStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemStopActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItemStop);

        jMenuItemEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/notes-edit-16x16.png"))); // NOI18N
        jMenuItemEdit.setText("Edit");
        jMenuItemEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemEditActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItemEdit);
        jPopupMenu1.add(jSeparator1);

        jMenuItemInfo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Button-Info-16x16.png"))); // NOI18N
        jMenuItemInfo.setText("Info");
        jMenuItemInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemInfoActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItemInfo);

        jMenuItemDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Button-Delete-16x16.png"))); // NOI18N
        jMenuItemDelete.setText("Delete");
        jMenuItemDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemDeleteActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItemDelete);

        jMenuItemClear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/trash-full-16x16.png"))); // NOI18N
        jMenuItemClear.setText("Clear");
        jMenuItemClear.setToolTipText("Clear all messages stored by this queue.");
        jMenuItemClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemClearActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItemClear);

        menuItemShowServer.setLabel("Show");
        menuItemShowServer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemShowServerActionPerformed(evt);
            }
        });
        popupMenuTray.add(menuItemShowServer);

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("myQueue");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        addWindowStateListener(new java.awt.event.WindowStateListener() {
            public void windowStateChanged(java.awt.event.WindowEvent evt) {
                formWindowStateChanged(evt);
            }
        });

        jTableQueues.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "Name", "Description", "Location", "Pool Size (Core/Max)"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableQueues.setComponentPopupMenu(jPopupMenu1);
        jTableQueues.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTableQueues);
        jTableQueues.getColumnModel().getColumn(0).setMinWidth(20);
        jTableQueues.getColumnModel().getColumn(0).setMaxWidth(20);

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

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 857, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE)
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
            System.exit(0);
        }
    }

    private void jPopupMenu1PopupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt)//GEN-FIRST:event_jPopupMenu1PopupMenuWillBecomeVisible
    {//GEN-HEADEREND:event_jPopupMenu1PopupMenuWillBecomeVisible
        try
        {
            String selectedServer = "";
            if (jTableQueues.getSelectedRow() >= 0)
            {
                selectedServer = jTableQueues.getValueAt(jTableQueues.getSelectedRow(), 1).toString();

                MyQueue queue = QueueManager.getQueues().get(selectedServer);

                if (queue.isRunning())
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
        }
        catch (Exception ex)
        {
        }
    }//GEN-LAST:event_jPopupMenu1PopupMenuWillBecomeVisible

    // Start queue.
    private void jMenuItemStartActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuItemStartActionPerformed
    {//GEN-HEADEREND:event_jMenuItemStartActionPerformed
        try
        {
            String selectedServer = "";
            if (jTableQueues.getSelectedRow() >= 0)
            {
                selectedServer = jTableQueues.getValueAt(jTableQueues.getSelectedRow(), 1).toString();
                QueueManager.StartQueue(selectedServer);
                Update();
            }
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR);
        }

    }//GEN-LAST:event_jMenuItemStartActionPerformed

    // Stop queue.
    private void jMenuItemStopActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuItemStopActionPerformed
    {//GEN-HEADEREND:event_jMenuItemStopActionPerformed
        try
        {
            String selectedServer = "";
            if (jTableQueues.getSelectedRow() >= 0)
            {
                selectedServer = jTableQueues.getValueAt(jTableQueues.getSelectedRow(), 1).toString();
                QueueManager.StopQueue(selectedServer);
                Update();
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
            String selectedServer = "";
            if (jTableQueues.getSelectedRow() >= 0)
            {
                selectedServer = jTableQueues.getValueAt(jTableQueues.getSelectedRow(), 1).toString();
                frmNewQueue frm = new frmNewQueue(this, selectedServer);
                frm.setVisible(true);
            }
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
            String selectedServer = "";
            if (jTableQueues.getSelectedRow() >= 0)
            {
                selectedServer = jTableQueues.getValueAt(jTableQueues.getSelectedRow(), 1).toString();
                int answer = JOptionPane.showConfirmDialog(null, "Delete " + selectedServer + "?", "Delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (answer == JOptionPane.YES_OPTION)
                {
                    QueueManager.DeleteQueue(selectedServer);
                    Update();
                }
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
        try
        {
            String selectedServer = "";
            if (jTableQueues.getSelectedRow() >= 0)
            {
                selectedServer = jTableQueues.getValueAt(jTableQueues.getSelectedRow(), 1).toString();
                int answer = JOptionPane.showConfirmDialog(null, "Clear " + selectedServer + " messages?", "Clear Queue", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (answer == JOptionPane.YES_OPTION)
                {
                    QueueManager.ClearQueue(selectedServer);
                    Update();
                }
            }
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR);
        }
    }//GEN-LAST:event_jMenuItemClearActionPerformed

    private void jMenuItemInfoActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuItemInfoActionPerformed
    {//GEN-HEADEREND:event_jMenuItemInfoActionPerformed
        try
        {
            String selectedServer = "";
            if (jTableQueues.getSelectedRow() >= 0)
            {
                selectedServer = jTableQueues.getValueAt(jTableQueues.getSelectedRow(), 1).toString();
                frmQueueInfo frm = new frmQueueInfo(selectedServer);
                frm.setVisible(true);
            }
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR);
        }
    }//GEN-LAST:event_jMenuItemInfoActionPerformed

    private void formWindowStateChanged(java.awt.event.WindowEvent evt)//GEN-FIRST:event_formWindowStateChanged
    {//GEN-HEADEREND:event_formWindowStateChanged
        if (this.getState() == JFrame.ICONIFIED)
        {
            this.setVisible(false);
        }
    }//GEN-LAST:event_formWindowStateChanged

    private void menuItemShowServerActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_menuItemShowServerActionPerformed
    {//GEN-HEADEREND:event_menuItemShowServerActionPerformed
        this.setVisible(true);
        this.setState(JFrame.NORMAL);
    }//GEN-LAST:event_menuItemShowServerActionPerformed

    // Closing form.
    private void formWindowClosing(java.awt.event.WindowEvent evt)//GEN-FIRST:event_formWindowClosing
    {//GEN-HEADEREND:event_formWindowClosing
        Exit();
    }//GEN-LAST:event_formWindowClosing

    public void Update()
    {
        System.out.println("Updated...");
        String name, description, location, poolSize;

        DefaultTableModel model = (DefaultTableModel) jTableQueues.getModel();

        for (MyQueue queue : QueueManager.getQueues().values())
        {
            name = queue.getName();

            boolean existsInTable = false;
            for (int i = 0; i < model.getRowCount(); i++)
            {
                if (jTableQueues.getValueAt(i, 1).equals(name))
                {
                    existsInTable = true;
                    // Update row.
                    ImageIcon img;
                    if (queue.isRunning())
                    {
                        img = new ImageIcon(getClass().getResource("/Images/enable-server-16x16.png"));
                        jTableQueues.setValueAt(img, i, 0);
                    }
                    else
                    {
                        img = new ImageIcon(getClass().getResource("/Images/desable-server-16x16.png"));
                        jTableQueues.setValueAt(img, i, 0);
                    }
                    jTableQueues.setValueAt(queue.getDescription(), i, 2);
                    jTableQueues.setValueAt(queue.getEngine().getLocation(), i, 3);
                    jTableQueues.setValueAt(String.valueOf(queue.getCorePoolSize()) + "/" + String.valueOf(queue.getMaxPoolSize()), i, 4);
                }
            }

            if (!existsInTable) // Add new row.
            {
                description = queue.getDescription();
                location = queue.getEngine().getLocation();
                poolSize = String.valueOf(queue.getCorePoolSize()) + "/" + String.valueOf(queue.getMaxPoolSize());

                Vector tmp = new Vector();

                try
                {
                    ImageIcon img;
                    if (queue.isRunning())
                    {
                        img = new ImageIcon(getClass().getResource("/Images/enable-server-16x16.png"));
                    }
                    else
                    {
                        img = new ImageIcon(getClass().getResource("/Images/desable-server-16x16.png"));
                    }
                    tmp.addElement(img);
                }
                catch (Exception ex)
                {
                }
                tmp.addElement(name);
                tmp.addElement(description);
                tmp.addElement(location);
                tmp.addElement(poolSize);

                model.addRow(tmp);
            }
        }

        // Remove deleted queues.
        for (int i = model.getRowCount() - 1; i >= 0; i--)
        {
            if (!QueueManager.getQueues().containsKey(jTableQueues.getValueAt(i, 1).toString()))
            {
                model.removeRow(i);
            }
        }
    }

    public static void main(String args[])
    {
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception ex)
        {
        }

        java.awt.EventQueue.invokeLater(new Runnable()
        {

            @Override
            public void run()
            {
                new frmMain().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItemClear;
    private javax.swing.JMenuItem jMenuItemDelete;
    private javax.swing.JMenuItem jMenuItemEdit;
    private javax.swing.JMenuItem jMenuItemInfo;
    private javax.swing.JMenuItem jMenuItemStart;
    private javax.swing.JMenuItem jMenuItemStop;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTableQueues;
    private java.awt.MenuItem menuItemShowServer;
    private java.awt.PopupMenu popupMenuTray;
    // End of variables declaration//GEN-END:variables
}
