package myqueue.UI;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import myqueue.Core.QueueManager;

/**
 *
 * @author Niksos Siatras
 */
public class frmNewQueue extends javax.swing.JFrame
{

    private frmMain fMain;

    public frmNewQueue(frmMain frm)
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
        fMain = frm;
        initComponents();

        // Open form in the center of the screen.
        Dimension us = this.getSize(), them = Toolkit.getDefaultToolkit().getScreenSize();
        int newX = (them.width - us.width) / 2;
        int newY = (them.height - us.height) / 2;
        this.setLocation(newX, newY);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("New Queue");
        setResizable(false);

        jLabel1.setText("Write the name for the new myQueue server");

        jLabel2.setText("Name:");

        jButton1.setText("OK");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Cancel");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 307, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton1ActionPerformed
    {//GEN-HEADEREND:event_jButton1ActionPerformed
        try
        {
            if (!QueueManager.getQueues().containsKey(jTextField1.getText()))
            {
                QueueManager.CreateNewQueue(jTextField1.getText(), "", null, 20, 40, null, 30000, false);
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Please choose an other name for this server.", "Name exists", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }
        catch (Exception ex)
        {
            System.err.println(ex.getMessage());
        }

        fMain.Update();
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton2ActionPerformed
    {//GEN-HEADEREND:event_jButton2ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
