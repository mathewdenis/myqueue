package myqueueworkbench.UI.ConnectionTabs;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
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

            jListQueues.removeAll();
            String[] tmp = userGrants.split("\n");
            DefaultListModel model = new DefaultListModel();
            for (String str : tmp)
            {
                model.addElement(str);
            }
            jListQueues.setModel(model);
            con.close();
        }
        catch (Exception ex)
        {
           
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jListQueues = new javax.swing.JList();
        jButtonRefreshQueues = new javax.swing.JButton();
        jButtonCreateQueue = new javax.swing.JButton();
        jButtonDropQueue = new javax.swing.JButton();

        jScrollPane2.setViewportView(jListQueues);

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonRefreshQueues, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonCreateQueue, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonDropQueue, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(337, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonRefreshQueues)
                    .addComponent(jButtonCreateQueue)
                    .addComponent(jButtonDropQueue))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonRefreshQueuesActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonRefreshQueuesActionPerformed
    {//GEN-HEADEREND:event_jButtonRefreshQueuesActionPerformed
        try
        {
            UpdateQueues();
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
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
            String selectedQueue = jListQueues.getSelectedValue().toString().split(" ")[0];

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
    public javax.swing.JList jListQueues;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
