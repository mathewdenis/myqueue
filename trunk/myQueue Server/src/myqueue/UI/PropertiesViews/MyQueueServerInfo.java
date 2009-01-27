package myqueue.UI.PropertiesViews;

import Extasys.Network.TCP.Server.Listener.TCPClientConnection;
import Extasys.Network.TCP.Server.Listener.TCPListener;
import java.util.Calendar;
import java.util.Enumeration;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import myqueue.Core.MyQueue;
import myqueue.Core.QueueManager;
import myqueue.UI.frmMain;

/**
 *
 * @author Nikos Siatras
 */
public class MyQueueServerInfo extends javax.swing.JInternalFrame
{

    private frmMain fMain;
    private boolean fUpdateThreadRun = false;
    private String fQueue = "";

    public MyQueueServerInfo(frmMain frm)
    {
        try
        {
            initComponents();
            javax.swing.plaf.InternalFrameUI myUI = this.getUI();
            ((javax.swing.plaf.basic.BasicInternalFrameUI) myUI).setNorthPane(null);
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, "MyQueueServerInfo error");
        }
    }

    public void SetMyQueueServer(String serverName)
    {
        fQueue = serverName;
        jLabelName.setText(serverName);
        Update();
    }

    public void Dispose()
    {
        fUpdateThreadRun = false;
    }

    private void Update()
    {
        try
        {
            if (fQueue.equals(""))
            {
                return;
            }
            if (QueueManager.getQueues().containsKey(fQueue))
            {
                MyQueue queue = QueueManager.getQueues().get(fQueue);

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

                    // Add new clients.
                    for (Enumeration e = listener.getConnectedClients().keys(); e.hasMoreElements();)
                    {
                        try
                        {
                            String ip = e.nextElement().toString();
                            TCPClientConnection client = (TCPClientConnection) listener.getConnectedClients().get(ip);
                            int bytesIn = client.getBytesIn();
                            int bytesOut = client.getBytesOut();

                            Calendar cal = Calendar.getInstance();
                            long milliseconds = cal.getTimeInMillis() - client.getConnectionStartUpDateTime().getTime();

                            long time = milliseconds / 1000;
                            String seconds = Integer.toString((int) (time % 60));
                            String minutes = Integer.toString((int) ((time % 3600) / 60));
                            String hours = Integer.toString((int) (time / 3600));

                            if (seconds.length() < 2)
                            {
                                seconds = "0" + seconds;
                            }
                            if (minutes.length() < 2)
                            {
                                minutes = "0" + minutes;
                            }
                            if (hours.length() < 2)
                            {
                                hours = "0" + hours;
                            }

                            String bytesInOut = String.valueOf(bytesIn) + "/" + String.valueOf(bytesOut);
                            String timeConnected = String.valueOf(hours) + "h " + String.valueOf(minutes) + "m " + String.valueOf(seconds) + "s";

                            boolean clientExists = false;
                            for (int x = 0; x < jTableClients.getRowCount(); x++)
                            {
                                // Update existing client.
                                if (jTableClients.getValueAt(x, 0).toString().equals(ip.substring(1)))
                                {
                                    clientExists = true;
                                    jTableClients.setValueAt(bytesInOut, x, 1);
                                    jTableClients.setValueAt(timeConnected, x, 2);
                                    break;
                                }
                            }

                            // Add new client.
                            if (!clientExists)
                            {
                                Object[] item = new Object[3];
                                item[0] = ip.substring(1);
                                item[1] = bytesInOut;
                                item[2] = timeConnected;
                                model.addRow(item);
                            }
                        }
                        catch (Exception ex)
                        {
                        }
                    }

                    try
                    {
                        // Remove disconnected clients.
                        for (int x = jTableClients.getRowCount() - 1; x >= 0; x--)
                        {
                            try
                            {
                                String ip = "/" + jTableClients.getValueAt(x, 0).toString();

                                if (!listener.getConnectedClients().containsKey(ip))
                                {
                                    model.removeRow(x);
                                }
                            }
                            catch (Exception ex)
                            {
                            }
                        }
                    }
                    catch (Exception ex)
                    {
                    }
                }
            }
            else
            {
                fUpdateThreadRun = false;
                this.dispose();
            }
        }
        catch (Exception ex)
        {
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane4 = new javax.swing.JScrollPane();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableClients = new javax.swing.JTable();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabelHighMessages = new javax.swing.JLabel();
        jLabelAboveNormalMessages = new javax.swing.JLabel();
        jLabelMessagesCount = new javax.swing.JLabel();
        jLabelBytesIn = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabelBytesOut = new javax.swing.JLabel();
        jLabelNormalMessages = new javax.swing.JLabel();
        jLabelConnectedClients = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabelName = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();

        jPanel3.setPreferredSize(new java.awt.Dimension(600, 385));

        jTableClients.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "IP", "Bytes In/Out", "Time Connected"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableClients.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTableClients);

        jLabel16.setText("Normal:");

        jLabel17.setText("Above Normal:");

        jLabel18.setText("High:");

        jLabel19.setText("Bytes In/Out:");

        jLabelHighMessages.setText("0");

        jLabelAboveNormalMessages.setText("0");

        jLabelMessagesCount.setText("0");

        jLabelBytesIn.setText("0");

        jLabel20.setText("/");

        jLabelBytesOut.setText("0");

        jLabelNormalMessages.setText("0");

        jLabelConnectedClients.setText("0");

        jLabel21.setText("Connected Clients:");

        jLabelName.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelName.setText("myQueue");

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Button-Refresh-32x32.png"))); // NOI18N
        jButton1.setText("Refresh");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/mail-32x32.png"))); // NOI18N
        jLabel1.setText("Messages:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelName)
                .addGap(643, 643, 643))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(78, 78, 78)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17)
                    .addComponent(jLabel16)
                    .addComponent(jLabel18))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabelHighMessages, javax.swing.GroupLayout.DEFAULT_SIZE, 18, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelNormalMessages)
                            .addComponent(jLabelAboveNormalMessages, javax.swing.GroupLayout.DEFAULT_SIZE, 18, Short.MAX_VALUE)
                            .addComponent(jLabelMessagesCount))))
                .addGap(534, 534, 534))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 699, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel21)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 477, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jButton1)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                    .addGap(118, 118, 118)
                                    .addComponent(jLabelConnectedClients, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 568, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel19)
                                .addGap(52, 52, 52)
                                .addComponent(jLabelBytesIn, javax.swing.GroupLayout.PREFERRED_SIZE, 7, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel20)
                                .addGap(10, 10, 10)
                                .addComponent(jLabelBytesOut))
                            .addComponent(jLabel1))
                        .addGap(541, 541, 541))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelName)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabelMessagesCount))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jLabelNormalMessages))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(jLabelAboveNormalMessages))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(jLabelHighMessages))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelBytesIn)
                    .addComponent(jLabel20)
                    .addComponent(jLabelBytesOut)
                    .addComponent(jLabel19))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(jLabelConnectedClients))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addContainerGap(187, Short.MAX_VALUE))
        );

        jScrollPane4.setViewportView(jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 607, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 631, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton1ActionPerformed
    {//GEN-HEADEREND:event_jButton1ActionPerformed
        Update();
    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabelAboveNormalMessages;
    private javax.swing.JLabel jLabelBytesIn;
    private javax.swing.JLabel jLabelBytesOut;
    private javax.swing.JLabel jLabelConnectedClients;
    private javax.swing.JLabel jLabelHighMessages;
    private javax.swing.JLabel jLabelMessagesCount;
    private javax.swing.JLabel jLabelName;
    private javax.swing.JLabel jLabelNormalMessages;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTableClients;
    // End of variables declaration//GEN-END:variables
}
