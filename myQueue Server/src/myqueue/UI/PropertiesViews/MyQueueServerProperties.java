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
package myqueue.UI.PropertiesViews;

import Extasys.Network.TCP.Server.Listener.TCPListener;
import java.io.File;
import java.net.InetAddress;
import java.util.ArrayList;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import myqueue.Core.MyQueue;
import myqueue.Core.QueueManager;
import myqueue.UI.frmMain;
import myqueue.UI.frmNewListener;

/**
 *
 * @author Nikos Siatras
 */
public class MyQueueServerProperties extends javax.swing.JInternalFrame
{

    private frmMain fMain;
    public String fMyQueueServerName = "";
    private Icon fStartIcon = new ImageIcon(getClass().getResource("/Images/Button-play-16x16.png"));
    private Icon fStopIcon = new ImageIcon(getClass().getResource("/Images/Button-stop-16x16.png"));
    private boolean fChangesMadeToListeners = false;

    public MyQueueServerProperties(frmMain frm)
    {
        fMain = frm;
        initComponents();
        javax.swing.plaf.InternalFrameUI myUI = this.getUI();
        ((javax.swing.plaf.basic.BasicInternalFrameUI) myUI).setNorthPane(null);
    }

    public void SetMyQueueServer(String name)
    {
        fMyQueueServerName = name;
        Update();
        fChangesMadeToListeners = false;
        CheckForChanges();
    }

    public boolean CheckForChanges()
    {
        boolean changesFound = false;
        System.out.println("Checking for changes...");
        MyQueue queue = QueueManager.getQueues().get(fMyQueueServerName);

        if (!jTextFieldLocation.getText().equals(queue.getEngine().getLocation()))
        {
            changesFound = true;
        }
        if (!jTextFieldCorePoolSize.getText().equals(String.valueOf(queue.getMyThreadPool().getCorePoolSize())))
        {
            changesFound = true;
        }
        if (!jTextFieldMaxPoolSize.getText().equals(String.valueOf(queue.getMyThreadPool().getMaximumPoolSize())))
        {
            changesFound = true;
        }

        // check for changes in listeners.
        if (fChangesMadeToListeners)
        {
            changesFound = true;
        }

        if (!jTextFieldTimeOut.getText().equals(String.valueOf(queue.getConnectionsTimeOut())))
        {
            changesFound = true;
        }
        if (jCheckBoxJournalRecording.isSelected() != queue.isJournalRecording())
        {
            changesFound = true;
        }

        if (queue.isRunning())
        {
            jButtonApply.setEnabled(false);
            jButtonCancel.setEnabled(false);
        }
        else
        {
            jButtonApply.setEnabled(changesFound);
            jButtonCancel.setEnabled(changesFound);
        }
        fChangesMadeToListeners = false;
        return changesFound;
    }

    public void Update()
    {
        MyQueue queue = QueueManager.getQueues().get(fMyQueueServerName);

        if (queue.isRunning())
        {
            jLabelName.setText(queue.getName());
            jButtonStartStop.setText("Stop");
            jButtonStartStop.setIcon(fStopIcon);
        }
        else
        {
            jLabelName.setText(queue.getName() + " (stoped)");
            jButtonStartStop.setText("Start");
            jButtonStartStop.setIcon(fStartIcon);
        }

        jTextFieldLocation.setText(queue.getEngine().getLocation());
        jTextFieldCorePoolSize.setText(String.valueOf(queue.getMyThreadPool().getCorePoolSize()));
        jTextFieldMaxPoolSize.setText(String.valueOf(queue.getMyThreadPool().getMaximumPoolSize()));

        // Load listeners.
        DefaultTableModel model = (DefaultTableModel) jTableListeners.getModel();
        for (int i = 0; i < model.getRowCount(); i++)
        {
            model.removeRow(i);
        }
        for (int i = 0; i < queue.getListeners().size(); i++)
        {
            TCPListener listener = (TCPListener) queue.getListeners().get(i);
            AddListener(listener.getIPAddress().toString().substring(1), listener.getPort(), listener.getMaxConnections());
        }
        jCheckBoxJournalRecording.setSelected(queue.isJournalRecording());

        // Set enabled-disabled fields
        jLabel1.setVisible(queue.isRunning());

        jLabel6.setEnabled(!queue.isRunning());
        jTextFieldLocation.setEnabled(!queue.isRunning());
        jButton1.setEnabled(!queue.isRunning());
        jLabel10.setEnabled(!queue.isRunning());

        jLabel4.setEnabled(!queue.isRunning());
        jTextFieldCorePoolSize.setEnabled(!queue.isRunning());
        jLabel2.setEnabled(!queue.isRunning());

        jLabel5.setEnabled(!queue.isRunning());
        jTextFieldMaxPoolSize.setEnabled(!queue.isRunning());
        jLabel7.setEnabled(!queue.isRunning());

        jLabel11.setEnabled(!queue.isRunning());
        jLabel12.setEnabled(!queue.isRunning());
        jTableListeners.setEnabled(!queue.isRunning());
        jButtonAddListener.setEnabled(!queue.isRunning());
        jButtonRemoveListener.setEnabled(!queue.isRunning());

        jLabel13.setEnabled(!queue.isRunning());
        jLabel14.setEnabled(!queue.isRunning());
        jTextFieldTimeOut.setEnabled(!queue.isRunning());

        jCheckBoxJournalRecording.setEnabled(!queue.isRunning());
        jLabel15.setEnabled(!queue.isRunning());
    }

    public void AddListener(String ipAddress, int port, int maxConnections)
    {
        Object[] item = new Object[3];
        item[0] = ipAddress;
        item[1] = String.valueOf(port);
        item[2] = String.valueOf(maxConnections);
        ((DefaultTableModel) this.jTableListeners.getModel()).addRow(item);
        fChangesMadeToListeners = true;
        CheckForChanges();
    }

    public void Dispose()
    {
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextFieldMaxPoolSize = new javax.swing.JTextField();
        jTextFieldCorePoolSize = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableListeners = new javax.swing.JTable();
        jButtonAddListener = new javax.swing.JButton();
        jButtonRemoveListener = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jTextFieldLocation = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jTextFieldTimeOut = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jCheckBoxJournalRecording = new javax.swing.JCheckBox();
        jLabel15 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabelName = new javax.swing.JLabel();
        jButtonStartStop = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jButtonApply = new javax.swing.JButton();
        jButtonCancel = new javax.swing.JButton();

        jPanel1.setPreferredSize(new java.awt.Dimension(600, 500));

        jLabel12.setText("Add one or more listeners to this queue.");
        jLabel12.setAutoscrolls(true);

        jLabel4.setText("Core pool size:");

        jLabel5.setText("Max pool size:");

        jTextFieldMaxPoolSize.setText("60");
        jTextFieldMaxPoolSize.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldMaxPoolSizeKeyReleased(evt);
            }
        });

        jTextFieldCorePoolSize.setText("20");
        jTextFieldCorePoolSize.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldCorePoolSizeKeyReleased(evt);
            }
        });

        jLabel7.setText("The maximum number of threads to allow in the pool.");

        jLabel2.setText("The number of threads to keep in the pool, even if they are idle.");

        jLabel11.setText("Listeners:");

        jTableListeners.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "IP Address", "Port", "Max Connections"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableListeners.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTableListeners);

        jButtonAddListener.setText("Add");
        jButtonAddListener.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddListenerActionPerformed(evt);
            }
        });

        jButtonRemoveListener.setText("Remove");
        jButtonRemoveListener.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRemoveListenerActionPerformed(evt);
            }
        });

        jLabel6.setText("Location:");

        jTextFieldLocation.setEditable(false);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/tiny_open.png"))); // NOI18N
        jButton1.setText("...");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel10.setText("The path at which the queue saves the messages.");

        jLabel13.setText("Connections time out (ms):");

        jTextFieldTimeOut.setText("30000");
        jTextFieldTimeOut.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldTimeOutKeyReleased(evt);
            }
        });

        jLabel14.setText("The time in milliseconds a client can be inactive.");

        jCheckBoxJournalRecording.setSelected(true);
        jCheckBoxJournalRecording.setText("Journal recording ");
        jCheckBoxJournalRecording.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxJournalRecordingActionPerformed(evt);
            }
        });

        jLabel15.setText("Journal allow you to save copies of messages as they are processed.");

        jLabelName.setFont(new java.awt.Font("Tahoma", 1, 14));
        jLabelName.setText("myQueue");

        jButtonStartStop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Button-Play-16x16.png"))); // NOI18N
        jButtonStartStop.setText("Start");
        jButtonStartStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonStartStopActionPerformed(evt);
            }
        });

        jLabel1.setText("To edit the settings bellow stop the server.");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabelName)
                .addGap(18, 18, 18)
                .addComponent(jButtonStartStop)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addContainerGap(236, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelName)
                    .addComponent(jButtonStartStop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1))
                .addGap(11, 11, 11))
        );

        jButtonApply.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/check32.png"))); // NOI18N
        jButtonApply.setText("Apply");
        jButtonApply.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonApplyActionPerformed(evt);
            }
        });

        jButtonCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/remove-32x32.png"))); // NOI18N
        jButtonCancel.setText("Cancel");
        jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(33, 33, 33)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jTextFieldLocation, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 232, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextFieldMaxPoolSize)
                            .addComponent(jTextFieldCorePoolSize, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jButton1)
                                .addComponent(jLabel2)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 154, Short.MAX_VALUE))
                    .addComponent(jLabel11)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 454, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButtonAddListener, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButtonRemoveListener)))
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextFieldTimeOut, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel14))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButtonApply, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jCheckBoxJournalRecording, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButtonCancel)
                            .addComponent(jLabel15))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jTextFieldLocation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addGap(34, 34, 34)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextFieldCorePoolSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextFieldMaxPoolSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(31, 31, 31)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButtonAddListener)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonRemoveListener))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jTextFieldTimeOut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBoxJournalRecording)
                    .addComponent(jLabel15))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonApply, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(112, 112, 112))
        );

        jScrollPane2.setViewportView(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 630, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 537, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonRemoveListenerActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonRemoveListenerActionPerformed
    {//GEN-HEADEREND:event_jButtonRemoveListenerActionPerformed
        try
        {
            fChangesMadeToListeners = true;
            ((DefaultTableModel) this.jTableListeners.getModel()).removeRow(jTableListeners.getSelectedRow());
        }
        catch (Exception ex)
        {
        }
        CheckForChanges();
}//GEN-LAST:event_jButtonRemoveListenerActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton1ActionPerformed
    {//GEN-HEADEREND:event_jButton1ActionPerformed
        JFileChooser fc = new JFileChooser();
        JList list = new JList();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (JFileChooser.APPROVE_OPTION == fc.showOpenDialog(list))
        {
            File file = fc.getSelectedFile();
            jTextFieldLocation.setText(file.getPath());
        }
        CheckForChanges();
}//GEN-LAST:event_jButton1ActionPerformed
    // Start - Stop server.
    private void jButtonStartStopActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonStartStopActionPerformed
    {//GEN-HEADEREND:event_jButtonStartStopActionPerformed
        MyQueue queue = QueueManager.getQueues().get(fMyQueueServerName);
        try
        {
            if (queue.isRunning())
            {
                QueueManager.StopQueue(fMyQueueServerName);
            }
            else
            {
                if (CheckForChanges())
                {
                    JOptionPane.showMessageDialog(null, "Apply changes first and then start the server!", "Apply changes", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                QueueManager.StartQueue(fMyQueueServerName);
            }
            fMain.Update();
            Update();
            fChangesMadeToListeners = false;
            CheckForChanges();
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR);
        }
    }//GEN-LAST:event_jButtonStartStopActionPerformed

    private void jButtonApplyActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonApplyActionPerformed
    {//GEN-HEADEREND:event_jButtonApplyActionPerformed
        ApplyChanges();
        CheckForChanges();
    }//GEN-LAST:event_jButtonApplyActionPerformed

    // Add listener.
    private void jButtonAddListenerActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonAddListenerActionPerformed
    {//GEN-HEADEREND:event_jButtonAddListenerActionPerformed
        new frmNewListener(this).setVisible(true);
    }//GEN-LAST:event_jButtonAddListenerActionPerformed

    private void jTextFieldCorePoolSizeKeyReleased(java.awt.event.KeyEvent evt)//GEN-FIRST:event_jTextFieldCorePoolSizeKeyReleased
    {//GEN-HEADEREND:event_jTextFieldCorePoolSizeKeyReleased
        CheckForChanges();
    }//GEN-LAST:event_jTextFieldCorePoolSizeKeyReleased

    private void jTextFieldMaxPoolSizeKeyReleased(java.awt.event.KeyEvent evt)//GEN-FIRST:event_jTextFieldMaxPoolSizeKeyReleased
    {//GEN-HEADEREND:event_jTextFieldMaxPoolSizeKeyReleased
        CheckForChanges();
    }//GEN-LAST:event_jTextFieldMaxPoolSizeKeyReleased

    private void jTextFieldTimeOutKeyReleased(java.awt.event.KeyEvent evt)//GEN-FIRST:event_jTextFieldTimeOutKeyReleased
    {//GEN-HEADEREND:event_jTextFieldTimeOutKeyReleased
        CheckForChanges();
    }//GEN-LAST:event_jTextFieldTimeOutKeyReleased

    private void jCheckBoxJournalRecordingActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jCheckBoxJournalRecordingActionPerformed
    {//GEN-HEADEREND:event_jCheckBoxJournalRecordingActionPerformed
        CheckForChanges();
    }//GEN-LAST:event_jCheckBoxJournalRecordingActionPerformed

    private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonCancelActionPerformed
    {//GEN-HEADEREND:event_jButtonCancelActionPerformed
        SetMyQueueServer(fMyQueueServerName);
        CheckForChanges();
    }//GEN-LAST:event_jButtonCancelActionPerformed

    private void ApplyChanges()
    {
        MyQueue queue = QueueManager.getQueues().get(fMyQueueServerName);

        try
        {
            QueueManager.DeleteQueue(fMyQueueServerName);
        }
        catch (Exception ex)
        {
        }
        int corePoolSize = 0;
        try
        {
            corePoolSize = Integer.parseInt(jTextFieldCorePoolSize.getText());
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, "Invalid core pool size!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int maxPoolSize = 0;
        try
        {
            maxPoolSize = Integer.parseInt(jTextFieldMaxPoolSize.getText());
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, "Invalid max pool size!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int timeOut = 0;
        try
        {
            timeOut = Integer.parseInt(jTextFieldTimeOut.getText());
            if (timeOut < 30000)
            {
                JOptionPane.showMessageDialog(null, "Time-out must be 30000 and above milliseconds.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, "Invalid time-out!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        ArrayList listeners = new ArrayList();

        for (int i = 0; i < jTableListeners.getRowCount(); i++)
        {
            try
            {
                InetAddress ip = InetAddress.getByName(jTableListeners.getValueAt(i, 0).toString());
                int port = Integer.parseInt(jTableListeners.getValueAt(i, 1).toString());
                int maxConnections = Integer.parseInt(jTableListeners.getValueAt(i, 2).toString());

                TCPListener listener = new TCPListener("Listener " + String.valueOf(i), ip, port, maxConnections, 65535, timeOut, 100, String.valueOf(((char) 3)));
                listeners.add(listener);
            }
            catch (Exception ex)
            {
            }
        }

        try
        {
            QueueManager.CreateNewQueue(fMyQueueServerName, "", jTextFieldLocation.getText(), corePoolSize, maxPoolSize, listeners, timeOut, jCheckBoxJournalRecording.isSelected());
        //JOptionPane.showMessageDialog(null, "Changes saved!", "Saved", JOptionPane.INFORMATION_MESSAGE);
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButtonAddListener;
    private javax.swing.JButton jButtonApply;
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonRemoveListener;
    private javax.swing.JButton jButtonStartStop;
    private javax.swing.JCheckBox jCheckBoxJournalRecording;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabelName;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTableListeners;
    private javax.swing.JTextField jTextFieldCorePoolSize;
    private javax.swing.JTextField jTextFieldLocation;
    private javax.swing.JTextField jTextFieldMaxPoolSize;
    private javax.swing.JTextField jTextFieldTimeOut;
    // End of variables declaration//GEN-END:variables
}
