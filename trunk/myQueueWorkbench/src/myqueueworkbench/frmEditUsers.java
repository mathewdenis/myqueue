package myqueueworkbench;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import myQueueConnector.myQueueConnection;
import myqueueworkbench.Connections.Connection;

/**
 *
 * @author Nikos Siatras
 */
public class frmEditUsers extends javax.swing.JFrame
{

    private Connection fConnection;
    private String fSelectedUser;

    public frmEditUsers(Connection connection)
    {
        fConnection = connection;
        initComponents();
        this.setTitle("Edit Users " + connection.fName);

        UpdateUsersList();
    }

    private void UpdateUserPermissions(String selectedUser)
    {
        jLabelUserLabel.setText("Permissions for user: " + selectedUser);
        jCheckBoxAll.setSelected(false);
        jCheckBoxCreateUsers.setSelected(false);
        jCheckBoxDropUsers.setSelected(false);
        jCheckBoxCreateQueues.setSelected(false);
        jCheckBoxDropQueues.setSelected(false);

        try (myQueueConnection con = fConnection.getQueueConnection())
        {
            con.Open();
            String reply = new String(con.SendToServer("SHOW PERMISSIONS FOR " + selectedUser).getBytes());

            if (reply.startsWith("ERROR"))
            {
                con.close();
                throw new Exception(reply.substring(5));
            }


            String[] permissions = reply.split("\\|");

            for (String permission : permissions)
            {
                switch (permission.toUpperCase())
                {
                    case "ALL":
                        jCheckBoxAll.setSelected(true);
                        break;

                    case "CREATEUSERS":
                        jCheckBoxCreateUsers.setSelected(true);
                        break;

                    case "DROPUSERS":
                        jCheckBoxDropUsers.setSelected(true);
                        break;

                    case "CREATEQUEUES":
                        jCheckBoxCreateQueues.setSelected(true);
                        break;

                    case "DROPQUEUES":
                        jCheckBoxDropQueues.setSelected(true);
                        break;
                }
            }

        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void UpdateUsersList()
    {
        try
        {
            try (myQueueConnection con = fConnection.getQueueConnection())
            {
                con.Open();
                String reply = new String(con.SendToServer("SHOW USERS").getBytes());

                if (reply.startsWith("ERROR"))
                {
                    throw new Exception(reply);
                }

                jListUsers.removeAll();
                DefaultListModel model = new DefaultListModel();
                String[] users = reply.split("\n");
                for (String user : users)
                {
                    model.addElement(user);
                }
                jListUsers.setModel(model);
            }
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void GivePermission(String permission)
    {
        try
        {
            try (myQueueConnection con = fConnection.getQueueConnection())
            {
                con.Open();

                String reply = new String(con.SendToServer("GIVE PERMISSION " + permission + " TO " + fSelectedUser).getBytes());
                if (reply.startsWith("ERROR"))
                {
                    throw new Exception(reply.substring(5));
                }

                UpdateUserPermissions(fSelectedUser);
            }
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void TakePermission(String permission)
    {
        try
        {
            try (myQueueConnection con = fConnection.getQueueConnection())
            {
                con.Open();

                String reply = new String(con.SendToServer("TAKE PERMISSION " + permission + " FROM " + fSelectedUser).getBytes());
                if (reply.startsWith("ERROR"))
                {
                    throw new Exception(reply.substring(5));
                }

                UpdateUserPermissions(fSelectedUser);
            }
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jListUsers = new javax.swing.JList();
        jButtonRefreshUsers = new javax.swing.JButton();
        jButtonDropUser = new javax.swing.JButton();
        jLabelUserLabel = new javax.swing.JLabel();
        jCheckBoxAll = new javax.swing.JCheckBox();
        jCheckBoxCreateUsers = new javax.swing.JCheckBox();
        jCheckBoxDropUsers = new javax.swing.JCheckBox();
        jCheckBoxCreateQueues = new javax.swing.JCheckBox();
        jCheckBoxDropQueues = new javax.swing.JCheckBox();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextFieldNewUserUsername = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldNewUserPassword = new javax.swing.JTextField();
        jButtonCreateUser = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Edit Users");
        setResizable(false);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Users & Permissions"));
        jPanel3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jListUsers.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jListUsersMouseReleased(evt);
            }
        });
        jScrollPane2.setViewportView(jListUsers);

        jButtonRefreshUsers.setText("Refresh");
        jButtonRefreshUsers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRefreshUsersActionPerformed(evt);
            }
        });

        jButtonDropUser.setText("Drop");
        jButtonDropUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDropUserActionPerformed(evt);
            }
        });

        jLabelUserLabel.setText("Permissions for user: ");

        jCheckBoxAll.setText("All");
        jCheckBoxAll.setEnabled(false);

        jCheckBoxCreateUsers.setText("Create Users");
        jCheckBoxCreateUsers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxCreateUsersActionPerformed(evt);
            }
        });

        jCheckBoxDropUsers.setText("Drop Users");
        jCheckBoxDropUsers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxDropUsersActionPerformed(evt);
            }
        });

        jCheckBoxCreateQueues.setText("Create Queues");
        jCheckBoxCreateQueues.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxCreateQueuesActionPerformed(evt);
            }
        });

        jCheckBoxDropQueues.setText("Drop Queues");
        jCheckBoxDropQueues.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxDropQueuesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jButtonRefreshUsers, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonDropUser, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelUserLabel)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCheckBoxCreateUsers)
                            .addComponent(jCheckBoxAll)
                            .addComponent(jCheckBoxDropUsers)
                            .addComponent(jCheckBoxCreateQueues)
                            .addComponent(jCheckBoxDropQueues))))
                .addContainerGap(55, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabelUserLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jCheckBoxAll)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jCheckBoxCreateUsers)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jCheckBoxDropUsers)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBoxCreateQueues)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jCheckBoxDropQueues)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButtonRefreshUsers)
                            .addComponent(jButtonDropUser))))
                .addContainerGap())
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Create User"));
        jPanel4.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel1.setText("Username:");

        jLabel2.setText("Password:");

        jButtonCreateUser.setText("Create");
        jButtonCreateUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCreateUserActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldNewUserUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldNewUserPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jButtonCreateUser, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jTextFieldNewUserPassword, jTextFieldNewUserUsername});

        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextFieldNewUserUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldNewUserPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addComponent(jButtonCreateUser)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonRefreshUsersActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonRefreshUsersActionPerformed
    {//GEN-HEADEREND:event_jButtonRefreshUsersActionPerformed
        try
        {
            UpdateUsersList();
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButtonRefreshUsersActionPerformed

    private void jButtonCreateUserActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonCreateUserActionPerformed
    {//GEN-HEADEREND:event_jButtonCreateUserActionPerformed
        try
        {
            try (myQueueConnection con = fConnection.getQueueConnection())
            {
                con.Open();

                String username = jTextFieldNewUserUsername.getText().trim().replace(" ", "");
                String password = jTextFieldNewUserPassword.getText();

                String reply = new String(con.SendToServer("CREATE USER " + username + " " + password).getBytes());
                if (reply.startsWith("ERROR"))
                {
                    throw new Exception(reply.substring(5));
                }
                else
                {
                    jTextFieldNewUserUsername.setText("");
                    jTextFieldNewUserPassword.setText("");
                    JOptionPane.showMessageDialog(null, "User created!", "New user", JOptionPane.INFORMATION_MESSAGE);
                }

                UpdateUsersList();
            }
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButtonCreateUserActionPerformed

    private void jButtonDropUserActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonDropUserActionPerformed
    {//GEN-HEADEREND:event_jButtonDropUserActionPerformed
        try
        {
            String selectedUser = jListUsers.getSelectedValue().toString().split(" ")[0];

            int answer = JOptionPane.showConfirmDialog(null, "Do you want to drop user '" + selectedUser + "' ?", "Drop User", JOptionPane.WARNING_MESSAGE);
            if (answer != JOptionPane.YES_OPTION)
            {
                return;
            }

            try (myQueueConnection con = fConnection.getQueueConnection())
            {
                con.Open();

                String reply = new String(con.SendToServer("DROP USER " + selectedUser).getBytes());
                if (reply.startsWith("ERROR"))
                {
                    con.close();
                    throw new Exception(reply.substring(5));
                }
            }

            UpdateUsersList();
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButtonDropUserActionPerformed

    private void jListUsersMouseReleased(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jListUsersMouseReleased
    {//GEN-HEADEREND:event_jListUsersMouseReleased

        fSelectedUser = jListUsers.getSelectedValue().toString();
        UpdateUserPermissions(fSelectedUser);

    }//GEN-LAST:event_jListUsersMouseReleased

    private void jCheckBoxCreateUsersActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jCheckBoxCreateUsersActionPerformed
    {//GEN-HEADEREND:event_jCheckBoxCreateUsersActionPerformed
        if (jCheckBoxCreateUsers.isSelected())
        {
            GivePermission("CREATEUSERS");
        }
        else
        {
            TakePermission("CREATEUSERS");
        }
    }//GEN-LAST:event_jCheckBoxCreateUsersActionPerformed

    private void jCheckBoxDropUsersActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jCheckBoxDropUsersActionPerformed
    {//GEN-HEADEREND:event_jCheckBoxDropUsersActionPerformed
        if (jCheckBoxDropUsers.isSelected())
        {
            GivePermission("DROPUSERS");
        }
        else
        {
            TakePermission("DROPUSERS");
        }
    }//GEN-LAST:event_jCheckBoxDropUsersActionPerformed

    private void jCheckBoxCreateQueuesActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jCheckBoxCreateQueuesActionPerformed
    {//GEN-HEADEREND:event_jCheckBoxCreateQueuesActionPerformed
        if (jCheckBoxCreateQueues.isSelected())
        {
            GivePermission("CREATEQUEUES");
        }
        else
        {
            TakePermission("CREATEQUEUES");
        }
    }//GEN-LAST:event_jCheckBoxCreateQueuesActionPerformed

    private void jCheckBoxDropQueuesActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jCheckBoxDropQueuesActionPerformed
    {//GEN-HEADEREND:event_jCheckBoxDropQueuesActionPerformed
        if (jCheckBoxDropQueues.isSelected())
        {
            GivePermission("DROPQUEUES");
        }
        else
        {
            TakePermission("DROPQUEUES");
        }
    }//GEN-LAST:event_jCheckBoxDropQueuesActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCreateUser;
    private javax.swing.JButton jButtonDropUser;
    private javax.swing.JButton jButtonRefreshUsers;
    private javax.swing.JCheckBox jCheckBoxAll;
    private javax.swing.JCheckBox jCheckBoxCreateQueues;
    private javax.swing.JCheckBox jCheckBoxCreateUsers;
    private javax.swing.JCheckBox jCheckBoxDropQueues;
    private javax.swing.JCheckBox jCheckBoxDropUsers;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabelUserLabel;
    public javax.swing.JList jListUsers;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextFieldNewUserPassword;
    private javax.swing.JTextField jTextFieldNewUserUsername;
    // End of variables declaration//GEN-END:variables
}
