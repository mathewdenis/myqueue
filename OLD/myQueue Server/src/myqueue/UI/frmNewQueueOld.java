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

import Extasys.Network.TCP.Server.Listener.TCPListener;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.net.InetAddress;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import myqueue.Core.MyQueue;
import myqueue.Core.QueueManager;

/**
 *
 * @author Nikos Siatras
 */
public class frmNewQueueOld extends javax.swing.JFrame
{

    private frmMain fMainForm;
    private boolean fOpenedForEdit;
    private String fQueueToEdit;
    private boolean fQueueToEditIsActive = false;

    // Open form to create new queue.
    public frmNewQueueOld(frmMain mainForm)
    {
        fMainForm = mainForm;
        initComponents();

        // Open form in the center of the screen.
        Dimension us = this.getSize(), them = Toolkit.getDefaultToolkit().getScreenSize();
        int newX = (them.width - us.width) / 2;
        int newY = (them.height - us.height) / 2;
        this.setLocation(newX, newY);
    }

    public frmNewQueueOld(frmMain mainForm, String queue)
    {
        fQueueToEdit = queue;
        fOpenedForEdit = true;
        fMainForm = mainForm;

        initComponents();

        // Open form in the center of the screen.
        Dimension us = this.getSize(), them = Toolkit.getDefaultToolkit().getScreenSize();
        int newX = (them.width - us.width) / 2;
        int newY = (them.height - us.height) / 2;
        this.setLocation(newX, newY);

        // Load queue data.
        MyQueue tmp = QueueManager.getQueues().get(queue);
        fQueueToEditIsActive = tmp.isRunning();

        jTextFieldName.setText(tmp.getName());
        jTextFieldDescription.setText(tmp.getDescription());
        jTextFieldLocation.setText(tmp.getEngine().getLocation());
        jTextFieldCorePoolSize.setText(String.valueOf(tmp.getCorePoolSize()));
        jTextFieldMaxPoolSize.setText(String.valueOf(tmp.getMaxPoolSize()));
        jCheckBoxJournalRecording.setSelected(tmp.isJournalRecording());

        // Listeners.
        DefaultTableModel model = (DefaultTableModel) jTableListeners.getModel();
        for (int i = 0; i < tmp.getListeners().size(); i++)
        {
            TCPListener listener = (TCPListener) tmp.getListeners().get(i);
            AddListener(listener.getIPAddress().toString().substring(1), listener.getPort(), listener.getMaxConnections());
        }

        jTextFieldTimeOut.setText(String.valueOf(tmp.getConnectionsTimeOut()));

        this.setTitle("Edit " + queue);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTextFieldDescription = new javax.swing.JTextField();
        jTextFieldName = new javax.swing.JTextField();
        jTextFieldLocation = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextFieldMaxPoolSize = new javax.swing.JTextField();
        jTextFieldCorePoolSize = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableListeners = new javax.swing.JTable();
        jButtonAddListener = new javax.swing.JButton();
        jButtonRemoveListener = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButtonOK = new javax.swing.JButton();
        jButtonCancel = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jTextFieldTimeOut = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jCheckBoxJournalRecording = new javax.swing.JCheckBox();
        jLabel15 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("New Queue");
        setResizable(false);

        jLabel3.setText("Description:");

        jLabel1.setText("Name:");

        jLabel6.setText("Location:");

        jTextFieldLocation.setEditable(false);

        jLabel4.setText("Core pool size:");

        jLabel5.setText("Max pool size:");

        jTextFieldMaxPoolSize.setText("60");

        jTextFieldCorePoolSize.setText("20");

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

        jButton1.setText("...");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButtonOK.setText("OK");
        jButtonOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOKActionPerformed(evt);
            }
        });

        jButtonCancel.setText("Cancel");
        jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 10));
        jLabel2.setText("The number of threads to keep in the pool, even if they are idle.");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 10));
        jLabel7.setText("The maximum number of threads to allow in the pool.");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 10));
        jLabel10.setText("The path at which the queue saves the messages.");

        jLabel11.setText("Listeners:");

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 10));
        jLabel12.setText("Add one or more listeners to this queue.");
        jLabel12.setAutoscrolls(true);

        jLabel13.setText("Connections time out (ms):");

        jTextFieldTimeOut.setText("30000");

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 10));
        jLabel14.setText("The time in milliseconds a client can be inactive.");

        jCheckBoxJournalRecording.setSelected(true);
        jCheckBoxJournalRecording.setText("Journal recording ");

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 10));
        jLabel15.setText("Journal allow you to save copies of messages as they are processed.");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 562, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextFieldMaxPoolSize)
                            .addComponent(jTextFieldCorePoolSize, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel2))
                        .addContainerGap(125, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 454, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jButtonAddListener, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButtonRemoveListener))))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel6)
                            .addComponent(jLabel1))
                        .addGap(32, 32, 32)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jTextFieldLocation, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jTextFieldName, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jTextFieldDescription, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel10)
                        .addContainerGap(17, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonOK)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonCancel)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextFieldTimeOut, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel14)
                        .addGap(55, 55, 55))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jCheckBoxJournalRecording)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel15)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextFieldName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextFieldDescription, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldLocation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jButton1)
                    .addComponent(jLabel10))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextFieldCorePoolSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextFieldMaxPoolSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(28, 28, 28)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonAddListener)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonRemoveListener))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jTextFieldTimeOut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBoxJournalRecording)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonOK)
                    .addComponent(jButtonCancel))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void AddListener(String ipAddress, int port, int maxConnections)
    {
        Object[] item = new Object[3];
        item[0] = ipAddress;
        item[1] = String.valueOf(port);
        item[2] = String.valueOf(maxConnections);
        ((DefaultTableModel) this.jTableListeners.getModel()).addRow(item);
    }

    private void jButtonAddListenerActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonAddListenerActionPerformed
    {//GEN-HEADEREND:event_jButtonAddListenerActionPerformed
       
}//GEN-LAST:event_jButtonAddListenerActionPerformed

    private void jButtonRemoveListenerActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonRemoveListenerActionPerformed
    {//GEN-HEADEREND:event_jButtonRemoveListenerActionPerformed
        try
        {
            ((DefaultTableModel) this.jTableListeners.getModel()).removeRow(jTableListeners.getSelectedRow());
        }
        catch (Exception ex)
        {
        }
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
}//GEN-LAST:event_jButton1ActionPerformed

    private void jButtonOKActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonOKActionPerformed
    {//GEN-HEADEREND:event_jButtonOKActionPerformed
        if (fOpenedForEdit) // Opened for edit.
        {
            try
            {
                int answer = JOptionPane.YES_OPTION;

                if (fQueueToEditIsActive)
                {
                    answer = JOptionPane.showConfirmDialog(null, "This queue will be restarted.\n Do you agree with that?", "Restart", JOptionPane.YES_NO_OPTION);
                }

                if (answer == JOptionPane.YES_OPTION)
                {
                }
                else
                {
                    return;
                }
            }
            catch (Exception ex)
            {
            }
        }

        String name = jTextFieldName.getText();

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
            try
            {
                QueueManager.DeleteQueue(fQueueToEdit);
                fMainForm.Update();
            }
            catch (Exception ex)
            {
            }

            QueueManager.CreateNewQueue(jTextFieldName.getText(), jTextFieldDescription.getText(), jTextFieldLocation.getText(), corePoolSize, maxPoolSize, listeners, timeOut, jCheckBoxJournalRecording.isSelected());

            if (fQueueToEditIsActive)
            {
                QueueManager.StartQueue(name);
            }

            fMainForm.Update();
            this.dispose();
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
}//GEN-LAST:event_jButtonOKActionPerformed

    private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonCancelActionPerformed
    {//GEN-HEADEREND:event_jButtonCancelActionPerformed
        this.dispose();
}//GEN-LAST:event_jButtonCancelActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButtonAddListener;
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonOK;
    private javax.swing.JButton jButtonRemoveListener;
    private javax.swing.JCheckBox jCheckBoxJournalRecording;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableListeners;
    private javax.swing.JTextField jTextFieldCorePoolSize;
    private javax.swing.JTextField jTextFieldDescription;
    private javax.swing.JTextField jTextFieldLocation;
    private javax.swing.JTextField jTextFieldMaxPoolSize;
    private javax.swing.JTextField jTextFieldName;
    private javax.swing.JTextField jTextFieldTimeOut;
    // End of variables declaration//GEN-END:variables
}
