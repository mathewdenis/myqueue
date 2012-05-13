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
import myqueueworkbench.Connections.Connection;
import myqueueworkbench.Connections.ConnectionsManager;
import myqueueworkbench.UI.ConnectionTreeNode;
import myqueueworkbench.UI.ConnectionsPopUpActionListeners.*;
import myqueueworkbench.UI.PopUpHandler;
import myqueueworkbench.UI.UpdateUIForSelectedConnectionThread;

/**
 *
 * @author Nikos Siatras
 */
public class frmMain extends javax.swing.JFrame
{

    private DefaultMutableTreeNode fMyQueueConnectionsParentNode = new DefaultMutableTreeNode("myQueue Connections");
    private JPopupMenu fConnectionsPopUpMenu = null;
    public Connection fSelectedConnection;
    private Thread fUpdateUIForSelectedConnectionThread;

    public frmMain()
    {
        initComponents();

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


        fUpdateUIForSelectedConnectionThread = new UpdateUIForSelectedConnectionThread(this);
        fUpdateUIForSelectedConnectionThread.start();
    }

    public void UpdateConnectionsList()
    {
        // Update connections in tree
        for (Connection con : ConnectionsManager.getConnections())
        {
            ConnectionTreeNode node = new ConnectionTreeNode(con.fName, con);
            fMyQueueConnectionsParentNode.add(node);
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
        con.Close();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTreeConnections = new javax.swing.JTree();
        jPanel1 = new javax.swing.JPanel();
        jLabelSelectedConnectionName = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabelCPUUsage = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabelFreeMemory = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabelTotalMemory = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jListQueues = new javax.swing.JList();
        jButtonRefreshQueues = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
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

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Machine Status"));
        jPanel2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel1.setText("CPU Load:");

        jLabelCPUUsage.setText("0");

        jLabel2.setText("Free Memory:");

        jLabelFreeMemory.setText("0");

        jLabel3.setText("Total Memory:");

        jLabelTotalMemory.setText("0");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabelCPUUsage, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelFreeMemory, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
                    .addComponent(jLabelTotalMemory, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabelCPUUsage))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabelFreeMemory))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabelTotalMemory))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Queues"));
        jPanel3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jScrollPane2.setViewportView(jListQueues);

        jButtonRefreshQueues.setText("Refresh");
        jButtonRefreshQueues.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRefreshQueuesActionPerformed(evt);
            }
        });

        jButton2.setText("Create");

        jButton3.setText("Drop");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jButtonRefreshQueues, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonRefreshQueues)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabelSelectedConnectionName)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 433, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelSelectedConnectionName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 487, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
        frmNewConnection frm = new frmNewConnection(this);

        int w = frm.getSize().width;
        int h = frm.getSize().height;
        int x = (dim.width - w) / 2;
        int y = (dim.height - h) / 2;

        frm.setLocation(x, y);
        frm.setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    // JTree Connections selecteion changed
    private void jTreeConnectionsValueChanged(javax.swing.event.TreeSelectionEvent evt)//GEN-FIRST:event_jTreeConnectionsValueChanged
    {//GEN-HEADEREND:event_jTreeConnectionsValueChanged
        TreePath selectedPath = jTreeConnections.getSelectionPath();
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) selectedPath.getLastPathComponent();
        RebuildConnectionsPopUp(selectedNode);
    }//GEN-LAST:event_jTreeConnectionsValueChanged

    private void jButtonRefreshQueuesActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonRefreshQueuesActionPerformed
    {//GEN-HEADEREND:event_jButtonRefreshQueuesActionPerformed
        UpdateQueuesList();
    }//GEN-LAST:event_jButtonRefreshQueuesActionPerformed

    public void RebuildConnectionsPopUp(DefaultMutableTreeNode selectedNode)
    {
        if (selectedNode instanceof ConnectionTreeNode)
        {
            // Get connection
            final ConnectionTreeNode selectedConnectionNode = (ConnectionTreeNode) selectedNode;
            fSelectedConnection = selectedConnectionNode.getConnection();

            // Update the JPopUpMenu items
            fConnectionsPopUpMenu = new JPopupMenu();
            fConnectionsPopUpMenu.removeAll();

            fConnectionsPopUpMenu.setInvoker(jTreeConnections);
            PopUpHandler handler = new PopUpHandler(jTreeConnections, fConnectionsPopUpMenu); // Set up a handler for the PopUp menu

            // Open Connection
            JMenuItem menuItemOpen = new JMenuItem("Open Connection");
            menuItemOpen.addActionListener(new OpenConnectionActionListener(this, selectedConnectionNode));
            fConnectionsPopUpMenu.add(menuItemOpen);

            // Close Connection
            JMenuItem menuItemClose = new JMenuItem("Close Connection");
            menuItemClose.addActionListener(new CloseConnectionActionListener(this, selectedConnectionNode));
            fConnectionsPopUpMenu.add(menuItemClose);

            // Separator
            fConnectionsPopUpMenu.addSeparator();

            // Edit
            JMenuItem menuItemEdit = new JMenuItem("Edit Connection");
            menuItemEdit.addActionListener(new EditConnectionActionListener(this, selectedConnectionNode));
            fConnectionsPopUpMenu.add(menuItemEdit);


            switch (selectedConnectionNode.getConnection().fConnected ? 1 : 0)
            {
                case 1:
                    menuItemClose.setEnabled(true);
                    menuItemOpen.setEnabled(false);
                    break;
                case 0:
                    menuItemClose.setEnabled(false);
                    menuItemOpen.setEnabled(true);
                    break;
            }

        }
        else
        {
            fConnectionsPopUpMenu.removeAll();
            fConnectionsPopUpMenu.setInvoker(null);
            fConnectionsPopUpMenu = null;
        }

        UpdateUIForSelectedConnection();
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
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButtonRefreshQueues;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    public javax.swing.JLabel jLabelCPUUsage;
    public javax.swing.JLabel jLabelFreeMemory;
    private javax.swing.JLabel jLabelSelectedConnectionName;
    public javax.swing.JLabel jLabelTotalMemory;
    public javax.swing.JList jListQueues;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTree jTreeConnections;
    // End of variables declaration//GEN-END:variables
}
