package myqueue.UI;

import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import myqueue.Core.MyQueue;
import myqueue.Core.QueueManager;

/**
 *
 * @author Nikos Siatras
 */
public class frmMain extends javax.swing.JFrame
{

    public frmMain()
    {
        initComponents();
    }

    @Override
    public void setVisible(boolean b)
    {
        super.setVisible(b);
        QueueManager.Load();
        jTableQueues.getColumnModel().getColumn(0).setCellRenderer(new ImageTableCellRenderer());
        Update();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItemStart = new javax.swing.JMenuItem();
        jMenuItemStop = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JSeparator();
        jMenuItemEdit = new javax.swing.JMenuItem();
        jMenuItemDelete = new javax.swing.JMenuItem();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableQueues = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();

        jPopupMenu1.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
                jPopupMenu1PopupMenuWillBecomeVisible(evt);
            }
        });

        jMenuItemStart.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Button-Play-16x16.png"))); // NOI18N
        jMenuItemStart.setText("Start");
        jMenuItemStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemStartActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItemStart);

        jMenuItemStop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Button-Stop-16x16.png"))); // NOI18N
        jMenuItemStop.setText("Stop");
        jMenuItemStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemStopActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItemStop);
        jPopupMenu1.add(jSeparator1);

        jMenuItemEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/notes-edit-16x16.png"))); // NOI18N
        jMenuItemEdit.setText("Edit");
        jMenuItemEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemEditActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItemEdit);

        jMenuItemDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Button-Delete-16x16.png"))); // NOI18N
        jMenuItemDelete.setText("Delete");
        jPopupMenu1.add(jMenuItemDelete);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("myQueue");

        jTableQueues.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "Name", "Description", "Location", "Pool Size (Core/Max)"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableQueues.setComponentPopupMenu(jPopupMenu1);
        jTableQueues.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTableQueues);
        jTableQueues.getColumnModel().getColumn(0).setMinWidth(20);
        jTableQueues.getColumnModel().getColumn(0).setMaxWidth(20);

        jMenu1.setText("File");

        jMenuItem1.setText("New Queue");
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

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 851, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuItem1ActionPerformed
    {//GEN-HEADEREND:event_jMenuItem1ActionPerformed
        frmNewQueue frm = new frmNewQueue(this);
        frm.setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    // Exit.
    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuItem2ActionPerformed
    {//GEN-HEADEREND:event_jMenuItem2ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jPopupMenu1PopupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt)//GEN-FIRST:event_jPopupMenu1PopupMenuWillBecomeVisible
    {//GEN-HEADEREND:event_jPopupMenu1PopupMenuWillBecomeVisible
        try
        {
            String selectedServer = "";
            if (jTableQueues.getSelectedRow() >= 0)
            {
                selectedServer = jTableQueues.getValueAt(jTableQueues.getSelectedRow(), 1).toString();

                MyQueue queue = QueueManager.getQueues().get(selectedServer);

                if (queue.isRunning())
                {
                    jMenuItemStart.setVisible(false);
                    jMenuItemStop.setVisible(true);
                }
                else
                {
                    jMenuItemStart.setVisible(true);
                    jMenuItemStop.setVisible(false);
                }
            }
        }
        catch (Exception ex)
        {
        }
    }//GEN-LAST:event_jPopupMenu1PopupMenuWillBecomeVisible

    // Start queue.
    private void jMenuItemStartActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuItemStartActionPerformed
    {//GEN-HEADEREND:event_jMenuItemStartActionPerformed
        try
        {
            String selectedServer = "";
            if (jTableQueues.getSelectedRow() >= 0)
            {
                selectedServer = jTableQueues.getValueAt(jTableQueues.getSelectedRow(), 1).toString();
                QueueManager.StartQueue(selectedServer);
                Update();
            }
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR);
        }

    }//GEN-LAST:event_jMenuItemStartActionPerformed

    // Stop queue.
    private void jMenuItemStopActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuItemStopActionPerformed
    {//GEN-HEADEREND:event_jMenuItemStopActionPerformed
        try
        {
            String selectedServer = "";
            if (jTableQueues.getSelectedRow() >= 0)
            {
                selectedServer = jTableQueues.getValueAt(jTableQueues.getSelectedRow(), 1).toString();
                QueueManager.StopQueue(selectedServer);
                Update();
            }
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR);
        }
    }//GEN-LAST:event_jMenuItemStopActionPerformed

    // Edit queue.
    private void jMenuItemEditActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuItemEditActionPerformed
    {//GEN-HEADEREND:event_jMenuItemEditActionPerformed
        try
        {
            String selectedServer = "";
            if (jTableQueues.getSelectedRow() >= 0)
            {
                selectedServer = jTableQueues.getValueAt(jTableQueues.getSelectedRow(), 1).toString();

            }
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR);
        }
    }//GEN-LAST:event_jMenuItemEditActionPerformed

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

            public void run()
            {
                new frmMain().setVisible(true);
            }
        });
    }

    public void Update()
    {
        System.out.println("Updated...");
        String name, description, location, poolSize;

        DefaultTableModel model = (DefaultTableModel) jTableQueues.getModel();

        for (MyQueue queue : QueueManager.getQueues().values())
        {
            name = queue.getName();

            boolean existsInTable = false;
            for (int i = 0; i < model.getRowCount(); i++)
            {
                if (jTableQueues.getValueAt(i, 1).equals(name))
                {
                    existsInTable = true;
                    // Update row.
                    ImageIcon img;
                    if (queue.isRunning())
                    {
                        img = new ImageIcon(getClass().getResource("/Images/enable-server-16x16.png"));
                        jTableQueues.setValueAt(img, i, 0);
                    }
                    else
                    {
                        img = new ImageIcon(getClass().getResource("/Images/desable-server-16x16.png"));
                        jTableQueues.setValueAt(img, i, 0);
                    }
                    jTableQueues.setValueAt(queue.getDescription(), i, 2);
                    jTableQueues.setValueAt(queue.getEngine().getLocation(), i, 3);
                    jTableQueues.setValueAt(String.valueOf(queue.getCorePoolSize()) + "/" + String.valueOf(queue.getMaxPoolSize()), i, 4);
                }
            }

            if (!existsInTable) // Add new row.
            {
                description = queue.getDescription();
                location = queue.getEngine().getLocation();
                poolSize = String.valueOf(queue.getCorePoolSize()) + "/" + String.valueOf(queue.getMaxPoolSize());

                Vector tmp = new Vector();

                try
                {
                    ImageIcon img;
                    if (queue.isRunning())
                    {
                        img = new ImageIcon(getClass().getResource("/Images/enable-server-16x16.png"));
                    }
                    else
                    {
                        img = new ImageIcon(getClass().getResource("/Images/desable-server-16x16.png"));
                    }
                    tmp.addElement(img);
                }
                catch (Exception ex)
                {
                }
                tmp.addElement(name);
                tmp.addElement(description);
                tmp.addElement(location);
                tmp.addElement(poolSize);

                model.addRow(tmp);
            }

        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItemDelete;
    private javax.swing.JMenuItem jMenuItemEdit;
    private javax.swing.JMenuItem jMenuItemStart;
    private javax.swing.JMenuItem jMenuItemStop;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTableQueues;
    // End of variables declaration//GEN-END:variables
}
