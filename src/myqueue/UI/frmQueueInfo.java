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

import Extasys.Network.TCP.Server.Listener.TCPClientConnection;
import Extasys.Network.TCP.Server.Listener.TCPListener;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Calendar;
import java.util.Enumeration;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;
import myqueue.Core.MyQueue;
import myqueue.Core.QueueManager;

/**
 *
 * @author Nikos Siatras
 */
public class frmQueueInfo extends javax.swing.JFrame
{

    private String fQueue;
    private boolean fUpdateThreadRun = false;

    public frmQueueInfo(String queue)
    {
        ImageIcon titleImage = new ImageIcon(getClass().getResource("/Images/Button-Info-16x16.png"));
        this.setIconImage(titleImage.getImage());

        initComponents();
        this.setTitle(queue);
        fQueue = queue;

        Thread updateThread = new Thread(new Runnable()
        {

            @Override
            public void run()
            {
                while (fUpdateThreadRun)
                {
                    try
                    {
                        Update();
                        Thread.sleep(1000);
                    }
                    catch (Exception ex)
                    {
                    }
                }
            }
        });
        fUpdateThreadRun = true;
        updateThread.start();

        // Open form in the center of the screen.
        Dimension us = this.getSize(), them = Toolkit.getDefaultToolkit().getScreenSize();
        int newX = (them.width - us.width) / 2;
        int newY = (them.height - us.height) / 2;
        this.setLocation(newX, newY);
    }

    private void Update()
    {
        MyQueue queue;
        if (QueueManager.getQueues().containsKey(fQueue))
        {
            queue = QueueManager.getQueues().get(fQueue);

            jLabelMessagesCount.setText(String.valueOf(queue.getMessageCount()));
            jLabelNormalMessages.setText(String.valueOf(queue.getNormalPriorityMessageCount()));
            jLabelAboveNormalMessages.setText(String.valueOf(queue.getAboveNormalPriorityMessageCount()));
            jLabelHighMessages.setText(String.valueOf(queue.getHighPriorityMessageCount()));

            jLabelBytesIn.setText(String.valueOf(queue.getBytesIn()));
            jLabelBytesOut.setText(String.valueOf(queue.getBytesOut()));
            jLabelConnectedClients.setText(String.valueOf(queue.getCurrentConnectionsNumber()));

            // Add connected clients.
            DefaultTableModel model = (DefaultTableModel) jTableClients.getModel();
            for (int i = 0; i < queue.getListeners().size(); i++)
            {
                TCPListener listener = (TCPListener) queue.getListeners().get(i);
                for (Enumeration e = listener.getConnectedClients().keys(); e.hasMoreElements();)
                {
                    String ip = e.nextElement().toString();
                    TCPClientConnection client = (TCPClientConnection) listener.getConnectedClients().get(ip);
                    int bytesIn = client.getBytesIn();
                    int bytesOut = client.getBytesOut();

                    Calendar cal = Calendar.getInstance();
                    long milliseconds = cal.getTimeInMillis() - client.getConnectionStartUpDateTime().getTime();

                    Object[] item = new Object[3];
                    item[0] = ip;
                    item[1] = String.valueOf(bytesIn) + "/" + String.valueOf(bytesOut);
                    item[2] = String.valueOf(milliseconds);

                    model.addRow(item);
                }
            }
        }
        else
        {
            fUpdateThreadRun = false;
            this.dispose();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabelBytesIn = new javax.swing.JLabel();
        jLabelBytesOut = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabelConnectedClients = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabelMessagesCount = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabelNormalMessages = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabelAboveNormalMessages = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabelHighMessages = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableClients = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jLabel1.setText("Bytes In/Out:");

        jLabelBytesIn.setText("0");

        jLabelBytesOut.setText("0");

        jLabel3.setText("Connected Clients:");

        jLabelConnectedClients.setText("0");

        jButton1.setText("Close");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel4.setText("Messages count:");

        jLabelMessagesCount.setText("0");

        jLabel5.setText("Normal:");

        jLabelNormalMessages.setText("0");

        jLabel7.setText("Above Normal:");

        jLabelAboveNormalMessages.setText("0");

        jLabel9.setText("High:");

        jLabelHighMessages.setText("0");

        jLabel11.setText("/");

        jTableClients.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "IP", "Bytes In/Out", "Time Connected"
            }
        ));
        jScrollPane1.setViewportView(jTableClients);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 478, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel5)
                                            .addComponent(jLabel7)
                                            .addComponent(jLabel9)))
                                    .addComponent(jLabel1))
                                .addGap(37, 37, 37)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabelHighMessages, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabelAboveNormalMessages, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabelMessagesCount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabelBytesIn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 7, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel11)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabelBytesOut))
                                    .addComponent(jLabelNormalMessages))
                                .addGap(77, 77, 77))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGap(118, 118, 118)
                                .addComponent(jLabelConnectedClients, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 133, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(264, 264, 264))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addContainerGap(427, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabelMessagesCount))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabelNormalMessages))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabelAboveNormalMessages))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabelHighMessages))
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabelBytesIn)
                    .addComponent(jLabel11)
                    .addComponent(jLabelBytesOut))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabelConnectedClients))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt)//GEN-FIRST:event_formWindowClosing
    {//GEN-HEADEREND:event_formWindowClosing
        fUpdateThreadRun = false;
        this.dispose();
    }//GEN-LAST:event_formWindowClosing

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton1ActionPerformed
    {//GEN-HEADEREND:event_jButton1ActionPerformed
        fUpdateThreadRun = false;
        this.dispose();
}//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelAboveNormalMessages;
    private javax.swing.JLabel jLabelBytesIn;
    private javax.swing.JLabel jLabelBytesOut;
    private javax.swing.JLabel jLabelConnectedClients;
    private javax.swing.JLabel jLabelHighMessages;
    private javax.swing.JLabel jLabelMessagesCount;
    private javax.swing.JLabel jLabelNormalMessages;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableClients;
    // End of variables declaration//GEN-END:variables
}
