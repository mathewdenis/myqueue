
/*
 * frmNewListener.java
 *
 * Created on 2 Δεκ 2008, 2:05:49 μμ
 */
package myqueue.UI;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 *
 * @author Nikos Siatras
 */
public class frmNewListener extends javax.swing.JDialog
{

    private frmNewQueue fMainForm;
    private int fRowToEdit;
    private boolean fOpenedForEdit = false,  fOpenedToCreateNew = false;

    public frmNewListener(frmNewQueue frm)
    {
        fMainForm = frm;
        fOpenedToCreateNew = true;
        initComponents();
        Init();
    }

    public frmNewListener(frmNewQueue frm, int index)
    {
        fOpenedForEdit = true;
        fRowToEdit = index;
        fMainForm = frm;
        initComponents();
        Init();
    }

    private void Init()
    {
        try
        {
            Enumeration e = NetworkInterface.getNetworkInterfaces();

            while (e.hasMoreElements())
            {
                NetworkInterface netface = (NetworkInterface) e.nextElement();
                Enumeration e2 = netface.getInetAddresses();

                while (e2.hasMoreElements())
                {
                    InetAddress ip = (InetAddress) e2.nextElement();
                    jComboBoxIP.addItem(ip.toString().substring(1));
                }
            }
        }
        catch (SocketException ex)
        {
        }

        //Open form in the center of the screen.
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
        jComboBoxIP = new javax.swing.JComboBox();
        jTextFieldPort = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldMaxConnections = new javax.swing.JTextField();
        jButtonOK = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(myqueue.MyQueueApp.class).getContext().getResourceMap(frmNewListener.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jComboBoxIP.setName("jComboBoxIP"); // NOI18N

        jTextFieldPort.setText(resourceMap.getString("jTextFieldPort.text")); // NOI18N
        jTextFieldPort.setName("jTextFieldPort"); // NOI18N

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        jTextFieldMaxConnections.setText(resourceMap.getString("jTextFieldMaxConnections.text")); // NOI18N
        jTextFieldMaxConnections.setName("jTextFieldMaxConnections"); // NOI18N

        jButtonOK.setText(resourceMap.getString("jButtonOK.text")); // NOI18N
        jButtonOK.setName("jButtonOK"); // NOI18N
        jButtonOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOKActionPerformed(evt);
            }
        });

        jButton2.setText(resourceMap.getString("jButton2.text")); // NOI18N
        jButton2.setName("jButton2"); // NOI18N
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
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGap(35, 35, 35)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBoxIP, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jTextFieldMaxConnections, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextFieldPort, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jLabel3)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonOK)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)))
                .addContainerGap(67, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jComboBoxIP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextFieldPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jTextFieldMaxConnections, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonOK)
                    .addComponent(jButton2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton2ActionPerformed
    {//GEN-HEADEREND:event_jButton2ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButtonOKActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonOKActionPerformed
    {//GEN-HEADEREND:event_jButtonOKActionPerformed
        try
        {
            Integer.parseInt(jTextFieldPort.getText());
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, "Invalid port number!", "Error", JOptionPane.ERROR_MESSAGE);
        }

        try
        {
            Integer.parseInt(jTextFieldMaxConnections.getText());
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, "Invalid maximum connections number!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        fMainForm.AddListener(jComboBoxIP.getSelectedItem().toString(), Integer.parseInt(jTextFieldPort.getText()), Integer.parseInt(jTextFieldMaxConnections.getText()));
        this.dispose();
    }//GEN-LAST:event_jButtonOKActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButtonOK;
    private javax.swing.JComboBox jComboBoxIP;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField jTextFieldMaxConnections;
    private javax.swing.JTextField jTextFieldPort;
    // End of variables declaration//GEN-END:variables
}