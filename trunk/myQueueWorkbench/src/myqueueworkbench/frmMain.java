package myqueueworkbench;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import myqueueworkbench.Connections.Connection;
import myqueueworkbench.Connections.ConnectionsManager;

/**
 *
 * @author Nikos Siatras
 */
public class frmMain extends javax.swing.JFrame
{

    private DefaultMutableTreeNode fMyQueueConnectionsParentNode = new DefaultMutableTreeNode("myQueue Connections");

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

    }

    public void UpdateConnectionsList()
    {
        // Update connections in tree
        for (Connection con : ConnectionsManager.getConnections())
        {
            fMyQueueConnectionsParentNode.add(new DefaultMutableTreeNode(con.fName));
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenuConnections = new javax.swing.JPopupMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTreeConnections = new javax.swing.JTree();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();

        jPopupMenuConnections.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jMenuItem4.setLabel("Open Connection");
        jPopupMenuConnections.add(jMenuItem4);
        jMenuItem4.getAccessibleContext().setAccessibleName("jMenuItemOpenClose");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("myQueue Workbench");

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("root");
        jTreeConnections.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jTreeConnections.setAutoscrolls(true);
        jScrollPane1.setViewportView(jTreeConnections);

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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(536, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 477, Short.MAX_VALUE)
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
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JPopupMenu jPopupMenuConnections;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTree jTreeConnections;
    // End of variables declaration//GEN-END:variables
}
