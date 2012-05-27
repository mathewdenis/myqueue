package myqueueworkbench.UI;

import java.awt.Color;
import java.util.Hashtable;
import javax.swing.JLabel;
import javax.swing.JPanel;
import myqueueworkbench.Connections.Connection;
import myqueueworkbench.UI.ConnectionTabs.frmQueues;
import myqueueworkbench.UI.ConnectionTabs.frmServerStatus;

/**
 *
 * @author Nikos Siatras
 */
public class frmConnection extends javax.swing.JPanel
{

    public Connection fMyConnection;
    private Hashtable<Integer, JLabel> fTabLabels = new Hashtable<>();
    private Hashtable<Integer, JPanel> fTabForms = new Hashtable<>();

    public frmConnection(Connection connection)
    {
        initComponents();
        fMyConnection = connection;

        // Server Status
        fTabLabels.put(0, jLabelServerStatus);
        frmServerStatus newFrmServerStatus = new frmServerStatus(this);
        fTabForms.put(0, newFrmServerStatus);
        newFrmServerStatus.setBounds(0, 0, 800, 600);
        jPanelMainView.add(newFrmServerStatus);

        // Queues
        fTabLabels.put(1, jLabelQueues);
        frmQueues newFrmQueues = new frmQueues(this);
        fTabForms.put(1, newFrmQueues);
        newFrmQueues.setBounds(0, 0, 800, 600);
        jPanelMainView.add(newFrmQueues);


        fTabLabels.put(2, jLabelConfiguration);
        fTabLabels.put(3, jLabelUsersAndPriviliges);

        // Select default Server Status Tab
        SelectTab(0);

        ResizeTabs();
    }

    public void ResizeTabs()
    {
        for (Integer key : fTabForms.keySet())
        {
            fTabForms.get(key).setSize(jPanelMainView.getWidth(), jPanelMainView.getHeight());
        }
    }

    private void SelectTab(int tab)
    {
        fTabLabels.get(tab).setForeground(Color.BLUE);
        if (fTabForms.containsKey(tab))
        {
            fTabForms.get(tab).setVisible(true);
        }

        for (Integer key : fTabLabels.keySet())
        {
            if (key != tab)
            {
                fTabLabels.get(key).setForeground(Color.BLACK);
                if (fTabForms.containsKey(key))
                {
                    fTabForms.get(key).setVisible(false);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        jLabelUsersAndPriviliges = new javax.swing.JLabel();
        jLabelConfiguration = new javax.swing.JLabel();
        jLabelQueues = new javax.swing.JLabel();
        jLabelServerStatus = new javax.swing.JLabel();
        jPanelMainView = new javax.swing.JPanel();

        jLabelUsersAndPriviliges.setIcon(new javax.swing.ImageIcon(getClass().getResource("/myqueueworkbench/UI/Images/user-16x16.png"))); // NOI18N
        jLabelUsersAndPriviliges.setText("Users and Privileges");
        jLabelUsersAndPriviliges.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabelUsersAndPriviliges.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabelUsersAndPriviligesMouseReleased(evt);
            }
        });

        jLabelConfiguration.setIcon(new javax.swing.ImageIcon(getClass().getResource("/myqueueworkbench/UI/Images/configuration-16x16.png"))); // NOI18N
        jLabelConfiguration.setText("Configuration");
        jLabelConfiguration.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabelConfiguration.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabelConfigurationMouseReleased(evt);
            }
        });

        jLabelQueues.setIcon(new javax.swing.ImageIcon(getClass().getResource("/myqueueworkbench/UI/Images/queue-16x16.png"))); // NOI18N
        jLabelQueues.setText("Queues");
        jLabelQueues.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabelQueues.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabelQueuesMouseReleased(evt);
            }
        });

        jLabelServerStatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/myqueueworkbench/UI/Images/start-16x16.png"))); // NOI18N
        jLabelServerStatus.setText("Server Status");
        jLabelServerStatus.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabelServerStatus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabelServerStatusMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelServerStatus)
                    .addComponent(jLabelConfiguration)
                    .addComponent(jLabelQueues)
                    .addComponent(jLabelUsersAndPriviliges))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelServerStatus)
                .addGap(9, 9, 9)
                .addComponent(jLabelQueues)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabelConfiguration)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabelUsersAndPriviliges)
                .addContainerGap(267, Short.MAX_VALUE))
        );

        jSplitPane1.setLeftComponent(jPanel1);

        javax.swing.GroupLayout jPanelMainViewLayout = new javax.swing.GroupLayout(jPanelMainView);
        jPanelMainView.setLayout(jPanelMainViewLayout);
        jPanelMainViewLayout.setHorizontalGroup(
            jPanelMainViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 422, Short.MAX_VALUE)
        );
        jPanelMainViewLayout.setVerticalGroup(
            jPanelMainViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 373, Short.MAX_VALUE)
        );

        jSplitPane1.setRightComponent(jPanelMainView);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 565, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jLabelServerStatusMouseReleased(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jLabelServerStatusMouseReleased
    {//GEN-HEADEREND:event_jLabelServerStatusMouseReleased
        SelectTab(0);
    }//GEN-LAST:event_jLabelServerStatusMouseReleased

    private void jLabelQueuesMouseReleased(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jLabelQueuesMouseReleased
    {//GEN-HEADEREND:event_jLabelQueuesMouseReleased
        SelectTab(1);
    }//GEN-LAST:event_jLabelQueuesMouseReleased

    private void jLabelConfigurationMouseReleased(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jLabelConfigurationMouseReleased
    {//GEN-HEADEREND:event_jLabelConfigurationMouseReleased
        SelectTab(2);
    }//GEN-LAST:event_jLabelConfigurationMouseReleased

    private void jLabelUsersAndPriviligesMouseReleased(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jLabelUsersAndPriviligesMouseReleased
    {//GEN-HEADEREND:event_jLabelUsersAndPriviligesMouseReleased
        SelectTab(3);
    }//GEN-LAST:event_jLabelUsersAndPriviligesMouseReleased
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabelConfiguration;
    private javax.swing.JLabel jLabelQueues;
    private javax.swing.JLabel jLabelServerStatus;
    private javax.swing.JLabel jLabelUsersAndPriviliges;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelMainView;
    private javax.swing.JSplitPane jSplitPane1;
    // End of variables declaration//GEN-END:variables
}
