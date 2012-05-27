package myqueueworkbench;

import myqueueworkbench.UI.frmNewConnection;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import myQueueConnector.myQueueConnection;
import myqueueworkbench.Connections.*;
import myqueueworkbench.UI.frmConnection;

/**
 *
 * @author Nikos Siatras
 */
public class frmMain extends javax.swing.JFrame
{

    private ArrayList<frmConnection> fCurrentConnectionForms = new ArrayList<>();

    public frmMain()
    {
        initComponents();

        // Resize event
        this.addComponentListener(new java.awt.event.ComponentAdapter()
        {

            @Override
            public void componentResized(ComponentEvent e)
            {
                try
                {
                    for (frmConnection frm : fCurrentConnectionForms)
                    {
                        frm.ResizeTabs();
                    }
                }
                catch (Exception ex)
                {
                }
            }
        });

        jListConnections.setComponentPopupMenu(jPopupMenuConnections);

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
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        UpdateConnectionsList();
    }

    public void UpdateConnectionsList()
    {
        jListConnections.removeAll();
        DefaultListModel model = new DefaultListModel();

        for (Connection con : ConnectionsManager.getConnections())
        {
            model.addElement(con.fName);
        }
        jListConnections.setModel(model);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenuConnections = new javax.swing.JPopupMenu();
        jMenuItemConnect = new javax.swing.JMenuItem();
        jMenuItemEditConnection = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItemDeleteServerInstance = new javax.swing.JMenuItem();
        jTabbedPaneMain = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jListConnections = new javax.swing.JList();
        jLabelNewInstance = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        jMenuItemConnect.setText("Connect");
        jMenuItemConnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemConnectActionPerformed(evt);
            }
        });
        jPopupMenuConnections.add(jMenuItemConnect);

        jMenuItemEditConnection.setText("Edit ");
        jMenuItemEditConnection.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemEditConnectionActionPerformed(evt);
            }
        });
        jPopupMenuConnections.add(jMenuItemEditConnection);
        jPopupMenuConnections.add(jSeparator1);

        jMenuItemDeleteServerInstance.setText("Delete Instance");
        jMenuItemDeleteServerInstance.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemDeleteServerInstanceActionPerformed(evt);
            }
        });
        jPopupMenuConnections.add(jMenuItemDeleteServerInstance);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("MyQueue Workbench");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/myqueueworkbench/UI/Images/connection-32x32.png"))); // NOI18N
        jLabel1.setText("Server Administration");

        jScrollPane1.setViewportView(jListConnections);

        jLabelNewInstance.setIcon(new javax.swing.ImageIcon(getClass().getResource("/myqueueworkbench/UI/Images/add-16x16.png"))); // NOI18N
        jLabelNewInstance.setText("New Server Instance");
        jLabelNewInstance.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabelNewInstance.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabelNewInstanceMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabelNewInstanceMouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabelNewInstanceMouseReleased(evt);
            }
        });

        jLabel3.setForeground(new java.awt.Color(153, 153, 153));
        jLabel3.setText("Register a new server instance to manage");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabelNewInstance, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)))
                    .addComponent(jLabel1))
                .addContainerGap(93, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabelNewInstance)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 306, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPaneMain.addTab("Home", jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPaneMain)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPaneMain)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabelNewInstanceMouseEntered(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jLabelNewInstanceMouseEntered
    {//GEN-HEADEREND:event_jLabelNewInstanceMouseEntered
        jLabelNewInstance.setForeground(Color.BLUE);
    }//GEN-LAST:event_jLabelNewInstanceMouseEntered

    private void jLabelNewInstanceMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jLabelNewInstanceMouseExited
    {//GEN-HEADEREND:event_jLabelNewInstanceMouseExited
        jLabelNewInstance.setForeground(Color.BLACK);
    }//GEN-LAST:event_jLabelNewInstanceMouseExited

    private void jLabelNewInstanceMouseReleased(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jLabelNewInstanceMouseReleased
    {//GEN-HEADEREND:event_jLabelNewInstanceMouseReleased
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frmNewConnection frm = new frmNewConnection(this);

        int w = frm.getSize().width;
        int h = frm.getSize().height;
        int x = (dim.width - w) / 2;
        int y = (dim.height - h) / 2;

        frm.setLocation(x, y);
        frm.setVisible(true);
    }//GEN-LAST:event_jLabelNewInstanceMouseReleased

    private void jMenuItemEditConnectionActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuItemEditConnectionActionPerformed
    {//GEN-HEADEREND:event_jMenuItemEditConnectionActionPerformed
        try
        {
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            String selectedConnection = jListConnections.getSelectedValue().toString();
            Connection con = ConnectionsManager.getConnection(selectedConnection);
            frmNewConnection frm = new frmNewConnection(this, con);

            int w = frm.getSize().width;
            int h = frm.getSize().height;
            int x = (dim.width - w) / 2;
            int y = (dim.height - h) / 2;

            frm.setLocation(x, y);
            frm.setVisible(true);
        }
        catch (Exception ex)
        {
        }
    }//GEN-LAST:event_jMenuItemEditConnectionActionPerformed

    private void jMenuItemDeleteServerInstanceActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuItemDeleteServerInstanceActionPerformed
    {//GEN-HEADEREND:event_jMenuItemDeleteServerInstanceActionPerformed
        try
        {
            String selectedConnection = jListConnections.getSelectedValue().toString();

            int answer = JOptionPane.showConfirmDialog(null, "Do you want to delete  '" + selectedConnection + "' ?", "Delete Server Instance", JOptionPane.WARNING_MESSAGE);
            if (answer != JOptionPane.YES_OPTION)
            {
                return;
            }

            ConnectionsManager.DeleteConnection(selectedConnection);
            UpdateConnectionsList();
        }
        catch (Exception ex)
        {
        }
    }//GEN-LAST:event_jMenuItemDeleteServerInstanceActionPerformed

    private void jMenuItemConnectActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuItemConnectActionPerformed
    {//GEN-HEADEREND:event_jMenuItemConnectActionPerformed
        try
        {
            String selectedConnection = jListConnections.getSelectedValue().toString();

            // Try connection and then open a new Connection tab
            Connection con = ConnectionsManager.getConnection(selectedConnection);
            myQueueConnection queueConnection = con.getQueueConnection();
            queueConnection.Open();
            queueConnection.close();

            frmConnection frm = new frmConnection(con);
            this.jTabbedPaneMain.add(con.fName, frm);
            fCurrentConnectionForms.add(frm);
            frm.setVisible(true);
            jTabbedPaneMain.setSelectedComponent(frm);
            frm.ResizeTabs();
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jMenuItemConnectActionPerformed

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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabelNewInstance;
    private javax.swing.JList jListConnections;
    private javax.swing.JMenuItem jMenuItemConnect;
    private javax.swing.JMenuItem jMenuItemDeleteServerInstance;
    private javax.swing.JMenuItem jMenuItemEditConnection;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu jPopupMenuConnections;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPaneMain;
    // End of variables declaration//GEN-END:variables
}
