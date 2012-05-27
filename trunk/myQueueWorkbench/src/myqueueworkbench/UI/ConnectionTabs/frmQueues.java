package myqueueworkbench.UI.ConnectionTabs;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import myQueueConnector.myQueueConnection;
import myqueueworkbench.UI.frmConnection;

/**
 *
 * @author Nikos Siatras
 */
public class frmQueues extends javax.swing.JPanel
{

    private frmConnection fMyFrmConnection;

    public frmQueues(frmConnection frm)
    {
        fMyFrmConnection = frm;
        initComponents();
        UpdateQueues();
    }

    public void UpdateQueues()
    {
        try (myQueueConnection con = fMyFrmConnection.fMyConnection.getQueueConnection())
        {
            con.Open();
            String userGrants = new String(con.SendToServer("SHOW GRANTS FOR CURRENT_USER").getBytes());

            String[] tmp = userGrants.split("\n");

            DefaultTableModel model = (DefaultTableModel) jTableQueues.getModel();
            while (model.getRowCount() > 0)
            {
                model.removeRow(0);
            }

            if (!userGrants.trim().equals(""))
            {
                for (String str : tmp)
                {
                    String[] queueNameAndPermissions = str.split(" ");
                    model.addRow(queueNameAndPermissions);
                }
            }

            jTableQueues.setModel(model);
            jTableQueues.updateUI();
            con.close();
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButtonRefreshQueues = new javax.swing.JButton();
        jButtonCreateQueue = new javax.swing.JButton();
        jButtonDropQueue = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableQueues = new javax.swing.JTable();

        jButtonRefreshQueues.setText("Refresh");
        jButtonRefreshQueues.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRefreshQueuesActionPerformed(evt);
            }
        });

        jButtonCreateQueue.setText("Create");
        jButtonCreateQueue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCreateQueueActionPerformed(evt);
            }
        });

        jButtonDropQueue.setText("Drop");
        jButtonDropQueue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDropQueueActionPerformed(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/myqueueworkbench/UI/Images/queue-48x48.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("Queues");

        jTableQueues.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Permissions"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableQueues.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTableQueues);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonRefreshQueues, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonCreateQueue, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonDropQueue, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 389, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(326, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jLabel2)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 298, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonRefreshQueues)
                    .addComponent(jButtonCreateQueue)
                    .addComponent(jButtonDropQueue))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonRefreshQueuesActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonRefreshQueuesActionPerformed
    {//GEN-HEADEREND:event_jButtonRefreshQueuesActionPerformed
        UpdateQueues();
    }//GEN-LAST:event_jButtonRefreshQueuesActionPerformed

    private void jButtonCreateQueueActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonCreateQueueActionPerformed
    {//GEN-HEADEREND:event_jButtonCreateQueueActionPerformed
        String queueName = JOptionPane.showInputDialog(null, "Name for the new queue:").trim();
        if (!queueName.equals(""))
        {
            try
            {
                queueName = queueName.trim();
                queueName = queueName.replace(" ", "_");
                myQueueConnection con = fMyFrmConnection.fMyConnection.getQueueConnection();
                con.Open();

                String reply = new String(con.SendToServer("CREATE QUEUE " + queueName).getBytes());
                if (reply.startsWith("ERROR"))
                {
                    con.close();
                    throw new Exception(reply);
                }
                con.close();
                UpdateQueues();
            }
            catch (Exception ex)
            {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jButtonCreateQueueActionPerformed

    private void jButtonDropQueueActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonDropQueueActionPerformed
    {//GEN-HEADEREND:event_jButtonDropQueueActionPerformed
        try
        {
            String selectedQueue = jTableQueues.getValueAt(jTableQueues.getSelectedRow(), 0).toString().split(" ")[0];

            int answer = JOptionPane.showConfirmDialog(null, "Do you want to drop queue '" + selectedQueue + "' ?", "Drop Queue", JOptionPane.WARNING_MESSAGE);
            if (answer != JOptionPane.YES_OPTION)
            {
                return;
            }

            myQueueConnection con = fMyFrmConnection.fMyConnection.getQueueConnection();
            con.Open();

            String reply = new String(con.SendToServer("DROP QUEUE " + selectedQueue).getBytes());
            if (reply.startsWith("ERROR"))
            {
                con.close();
                throw new Exception(reply);
            }

            con.close();
            UpdateQueues();
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButtonDropQueueActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCreateQueue;
    private javax.swing.JButton jButtonDropQueue;
    private javax.swing.JButton jButtonRefreshQueues;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableQueues;
    // End of variables declaration//GEN-END:variables
}
