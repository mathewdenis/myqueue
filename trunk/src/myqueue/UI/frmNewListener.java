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

import Extasys.Network.TCP.Client.ExtasysTCPClient;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Nikos Siatras
 */
public class frmNewListener extends JFrame
{

    private frmNewQueue fMainForm;

    public frmNewListener(frmNewQueue frm)
    {
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
        jComboBoxIP = new javax.swing.JComboBox();
        jTextFieldMaxConnections = new javax.swing.JTextField();
        jTextFieldPort = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jButtonOK = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("New Listener");
        setResizable(false);

        jLabel1.setText("IP:");

        jLabel2.setText("Port:");

        jTextFieldMaxConnections.setText("100");

        jLabel3.setText("Max. connections:");

        jButtonOK.setText("OK");
        jButtonOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOKActionPerformed(evt);
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
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonOK, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(jLabel3))
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGap(14, 14, 14)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBoxIP, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jTextFieldPort, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jTextFieldMaxConnections, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)))))
                .addContainerGap(18, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(jComboBoxIP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jTextFieldPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextFieldMaxConnections, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonOK)
                    .addComponent(jButton2))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonOKActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonOKActionPerformed
    {//GEN-HEADEREND:event_jButtonOKActionPerformed
        try
        {
            Integer.parseInt(jTextFieldPort.getText());
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, "Invalid port number!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try
        {
            Integer.parseInt(jTextFieldMaxConnections.getText());
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, "Invalid maximum connections number!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Check if TCP port is in use by an other application.
        boolean portIsInUse = false;
        ExtasysTCPClient client = new ExtasysTCPClient("", "", 1, 2);

        try
        {
            client.AddConnector("", InetAddress.getByName(jComboBoxIP.getSelectedItem().toString()), Integer.parseInt(jTextFieldPort.getText()), 10240);
            client.Start();
            portIsInUse = true;
            client.Stop();
        }
        catch (Exception ex)
        {
            // Port is free.
        }

        if (portIsInUse)
        {
            JOptionPane.showMessageDialog(null, "The selected TCP port is in use by an other proccess.\nPlease use an other TCP port for this listener.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        fMainForm.AddListener(jComboBoxIP.getSelectedItem().toString(), Integer.parseInt(jTextFieldPort.getText()), Integer.parseInt(jTextFieldMaxConnections.getText()));
        this.dispose();
}//GEN-LAST:event_jButtonOKActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton2ActionPerformed
    {//GEN-HEADEREND:event_jButton2ActionPerformed
        this.dispose();
}//GEN-LAST:event_jButton2ActionPerformed
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
