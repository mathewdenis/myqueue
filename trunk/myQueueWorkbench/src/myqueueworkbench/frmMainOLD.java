package myqueueworkbench;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.UnknownHostException;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import myQueueConnector.myQueueConnection;
import myqueueworkbench.Connections.*;
import myqueueworkbench.UI.*;

/**
 *
 * @author Nikos Siatras
 */
public class frmMainOLD extends javax.swing.JFrame
{

    private DefaultMutableTreeNode fMyQueueConnectionsParentNode = new DefaultMutableTreeNode("myQueue Connections");
    private JPopupMenu fConnectionsPopUpMenu = null;
    public Connection fSelectedConnection;
    private Thread fUpdateUIForSelectedConnectionThread;
    private String fSelectedUser;

    public frmMainOLD()
    {
        initComponents();

        jPanelMainPanel.setVisible(false);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int w = this.getSize().width;
        int h = this.getSize().height;
        int x = (dim.width - w) / 2;
        int y = (dim.height - h) / 2;
        this.setLocation(x, y);

        try
        {
            ConnectionsManager.Initialize();
        }
        catch (IOException | ClassNotFoundException ex)
        {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        jTreeConnections.setModel(new DefaultTreeModel(fMyQueueConnectionsParentNode));
        ((DefaultTreeModel) jTreeConnections.getModel()).reload();

        UpdateConnectionsList();
        jTreeConnections.expandRow(0);

        //fUpdateUIForSelectedConnectionThread = new UpdateUIForSelectedConnectionThread(this);
        //fUpdateUIForSelectedConnectionThread.start();
    }

    public void UpdateConnectionsList()
    {
    }

    /**
     * This method should be called every time the user clicks to a connection
     * inside the jTreeConnections
     */
    public void SelectedConnectionChanged() throws UnknownHostException, Exception
    {
        jPanelMainPanel.setVisible(true);
        if (fSelectedConnection.fConnected)
        {
            UpdateQueuesList();
            UpdateRemoteMachineStatus();
            UpdateUsersList();
        }
        else
        {
            // Clear jListQueues
            DefaultListModel model = new DefaultListModel();
            jListQueues.setModel(model);

            // Clear machine status
            jLabelFreeMemory.setText("N/A");
            jLabelTotalMemory.setText("N/A");
        }
    }

    public void UpdateQueuesList() throws UnknownHostException, Exception
    {
        myQueueConnection con = fSelectedConnection.getQueueConnection();
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

    private void UpdateUsersList()
    {
        try
        {
            try (myQueueConnection con = fSelectedConnection.getQueueConnection())
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
            //JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void UpdateRemoteMachineStatus() throws UnknownHostException, Exception
    {
        myQueueConnection con = fSelectedConnection.getQueueConnection();
        con.Open();
        byte[] bytes = con.SendToServer("SHOW MACHINE STATUS").getBytes();
        String str = new String(bytes);
        con.close();

        if (!str.isEmpty())
        {
            try
            {
                String[] data = str.split("\n");

                String loadStr = data[2].replace("CPU LOAD", "");
                int load = Integer.parseInt(loadStr.trim());

                jProgressBarCPULoad.setValue(load);
                jLabelFreeMemory.setText(data[0].replace("FREE MEMORY", ""));
                jLabelTotalMemory.setText(data[1].replace("TOTAL MEMORY", ""));
            }
            catch (Exception ex)
            {
                System.err.println(ex.getMessage());
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTreeConnections = new javax.swing.JTree();
        jPanelMainPanel = new javax.swing.JPanel();
        jLabelSelectedConnectionName = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jListQueues = new javax.swing.JList();
        jButtonRefreshQueues = new javax.swing.JButton();
        jButtonCreateQueue = new javax.swing.JButton();
        jButtonDropQueue = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
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
        jLabel4 = new javax.swing.JLabel();
        jTextFieldNewUserUsername = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextFieldNewUserPassword = new javax.swing.JTextField();
        jButtonCreateUser = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabelFreeMemory = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabelTotalMemory = new javax.swing.JLabel();
        jProgressBarCPULoad = new javax.swing.JProgressBar();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("myQueue Workbench");

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("root");
        jTreeConnections.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jTreeConnections.setAutoscrolls(true);
        jTreeConnections.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                jTreeConnectionsValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(jTreeConnections);

        jLabelSelectedConnectionName.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelSelectedConnectionName.setText("....");

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButtonRefreshQueues, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonCreateQueue, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonDropQueue, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(387, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonRefreshQueues)
                    .addComponent(jButtonCreateQueue)
                    .addComponent(jButtonDropQueue))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Queues", jPanel1);

        jListUsers.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jListUsersMouseReleased(evt);
            }
        });
        jScrollPane3.setViewportView(jListUsers);

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

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Create User"));
        jPanel4.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel4.setText("Username:");

        jLabel5.setText("Password:");

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
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextFieldNewUserPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
                            .addComponent(jTextFieldNewUserUsername)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jButtonCreateUser, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextFieldNewUserUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldNewUserPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addComponent(jButtonCreateUser)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(19, 19, 19)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabelUserLabel)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jCheckBoxCreateUsers)
                                            .addComponent(jCheckBoxAll)
                                            .addComponent(jCheckBoxDropUsers)
                                            .addComponent(jCheckBoxCreateQueues)
                                            .addComponent(jCheckBoxDropQueues)))))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jButtonRefreshUsers, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButtonDropUser, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(339, Short.MAX_VALUE))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButtonRefreshUsers)
                            .addComponent(jButtonDropUser))
                        .addGap(13, 13, 13)))
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Users", jPanel5);

        jLabel1.setText("CPU Load:");

        jLabel2.setText("Free Memory:");

        jLabelFreeMemory.setText("0");

        jLabel3.setText("Total Memory:");

        jLabelTotalMemory.setText("0");

        jProgressBarCPULoad.setStringPainted(true);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jProgressBarCPULoad, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelFreeMemory, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelTotalMemory, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(401, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jProgressBarCPULoad, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel6Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jLabel1)))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabelFreeMemory))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabelTotalMemory))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Server Status", jPanel6);

        javax.swing.GroupLayout jPanelMainPanelLayout = new javax.swing.GroupLayout(jPanelMainPanel);
        jPanelMainPanel.setLayout(jPanelMainPanelLayout);
        jPanelMainPanelLayout.setHorizontalGroup(
            jPanelMainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMainPanelLayout.createSequentialGroup()
                .addComponent(jLabelSelectedConnectionName)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanelMainPanelLayout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 649, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanelMainPanelLayout.setVerticalGroup(
            jPanelMainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMainPanelLayout.createSequentialGroup()
                .addComponent(jLabelSelectedConnectionName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 491, Short.MAX_VALUE))
        );

        jMenu1.setText("File");

        jMenuItem1.setText("New Connection");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("Exit");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanelMainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jPanelMainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Exit
    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuItem2ActionPerformed
    {//GEN-HEADEREND:event_jMenuItem2ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    // New connection
    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuItem1ActionPerformed
    {//GEN-HEADEREND:event_jMenuItem1ActionPerformed
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        /*
         * frmNewConnection frm = new frmNewConnection(this);
         *
         * int w = frm.getSize().width; int h = frm.getSize().height; int x =
         * (dim.width - w) / 2; int y = (dim.height - h) / 2;
         *
         * frm.setLocation(x, y);
        frm.setVisible(true);
         */
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    // JTree Connections selection changed
    private void jTreeConnectionsValueChanged(javax.swing.event.TreeSelectionEvent evt)//GEN-FIRST:event_jTreeConnectionsValueChanged
    {//GEN-HEADEREND:event_jTreeConnectionsValueChanged
        TreePath selectedPath = jTreeConnections.getSelectionPath();
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) selectedPath.getLastPathComponent();
        RebuildConnectionsPopUp(selectedNode);
        try
        {
            SelectedConnectionChanged();
        }
        catch (Exception ex)
        {
        }
    }//GEN-LAST:event_jTreeConnectionsValueChanged

    private void jButtonRefreshQueuesActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonRefreshQueuesActionPerformed
    {//GEN-HEADEREND:event_jButtonRefreshQueuesActionPerformed
        try
        {
            UpdateQueuesList();
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
                myQueueConnection con = fSelectedConnection.getQueueConnection();
                con.Open();

                String reply = new String(con.SendToServer("CREATE QUEUE " + queueName).getBytes());
                if (reply.startsWith("ERROR"))
                {
                    con.close();
                    throw new Exception(reply);
                }
                con.close();
                UpdateQueuesList();
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

            myQueueConnection con = fSelectedConnection.getQueueConnection();
            con.Open();

            String reply = new String(con.SendToServer("DROP QUEUE " + selectedQueue).getBytes());
            if (reply.startsWith("ERROR"))
            {
                con.close();
                throw new Exception(reply);
            }

            con.close();
            UpdateQueuesList();
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButtonDropQueueActionPerformed

    private void jListUsersMouseReleased(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jListUsersMouseReleased
    {//GEN-HEADEREND:event_jListUsersMouseReleased

        fSelectedUser = jListUsers.getSelectedValue().toString();
        UpdateUserPermissions(fSelectedUser);
    }//GEN-LAST:event_jListUsersMouseReleased

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

            try (myQueueConnection con = fSelectedConnection.getQueueConnection())
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
            UpdateUserPermissions(fSelectedUser);
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButtonDropUserActionPerformed

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

    private void jButtonCreateUserActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonCreateUserActionPerformed
    {//GEN-HEADEREND:event_jButtonCreateUserActionPerformed
        try
        {
            try (myQueueConnection con = fSelectedConnection.getQueueConnection())
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

    public void RebuildConnectionsPopUp(DefaultMutableTreeNode selectedNode)
    {
    }

    private void UpdateUIForSelectedConnection()
    {
        if (fSelectedConnection != null)
        {
            jLabelSelectedConnectionName.setText(fSelectedConnection.fName);
            if (fSelectedConnection.fConnected)
            {
                jLabelSelectedConnectionName.setText(jLabelSelectedConnectionName.getText() + " (Connected)");
            }
            else
            {
                jLabelSelectedConnectionName.setText(jLabelSelectedConnectionName.getText() + " (Disconnected)");
            }
        }
        else
        {
        }
    }

    private void UpdateUserPermissions(String selectedUser)
    {
        jLabelUserLabel.setText("Permissions for user: " + selectedUser);
        jCheckBoxAll.setSelected(false);
        jCheckBoxCreateUsers.setSelected(false);
        jCheckBoxDropUsers.setSelected(false);
        jCheckBoxCreateQueues.setSelected(false);
        jCheckBoxDropQueues.setSelected(false);

        try (myQueueConnection con = fSelectedConnection.getQueueConnection())
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

    private void GivePermission(String permission)
    {
        try
        {
            try (myQueueConnection con = fSelectedConnection.getQueueConnection())
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
            try (myQueueConnection con = fSelectedConnection.getQueueConnection())
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

    public static void main(String args[])
    {
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception ex)
        {
        }

        java.awt.EventQueue.invokeLater(new Runnable()
        {

            @Override
            public void run()
            {
                new frmMain().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCreateQueue;
    private javax.swing.JButton jButtonCreateUser;
    private javax.swing.JButton jButtonDropQueue;
    private javax.swing.JButton jButtonDropUser;
    private javax.swing.JButton jButtonRefreshQueues;
    private javax.swing.JButton jButtonRefreshUsers;
    private javax.swing.JCheckBox jCheckBoxAll;
    private javax.swing.JCheckBox jCheckBoxCreateQueues;
    private javax.swing.JCheckBox jCheckBoxCreateUsers;
    private javax.swing.JCheckBox jCheckBoxDropQueues;
    private javax.swing.JCheckBox jCheckBoxDropUsers;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    public javax.swing.JLabel jLabelFreeMemory;
    private javax.swing.JLabel jLabelSelectedConnectionName;
    public javax.swing.JLabel jLabelTotalMemory;
    private javax.swing.JLabel jLabelUserLabel;
    public javax.swing.JList jListQueues;
    public javax.swing.JList jListUsers;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanelMainPanel;
    public javax.swing.JProgressBar jProgressBarCPULoad;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTextFieldNewUserPassword;
    private javax.swing.JTextField jTextFieldNewUserUsername;
    private javax.swing.JTree jTreeConnections;
    // End of variables declaration//GEN-END:variables
}
