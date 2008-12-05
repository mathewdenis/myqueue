/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * frmQueueManager.java
 *
 * Created on 3 Δεκ 2008, 1:30:56 μμ
 */
package myqueue.UI;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Label;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.util.Vector;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import myqueue.Core.MyQueue;
import myqueue.Core.QueueManager;

/**
 *
 * @author Administrator
 */
public class frmQueueManager extends javax.swing.JInternalFrame
{

    private Thread fUpdateThread;
    private boolean fKeepUpdating = false;

    /** Creates new form frmQueueManager */
    public frmQueueManager()
    {
        initComponents();
        fKeepUpdating = true;
        fUpdateThread = new Thread(new Runnable()
        {

            @Override
            public void run()
            {
                while (fKeepUpdating)
                {
                    try
                    {
                        Update();
                        Thread.sleep(5000);
                    }
                    catch (Exception ex)
                    {
                    }
                }
            }
        });
        // Fix JTable rendered for icons.
        jTableQueues.getColumnModel().getColumn(0).setCellRenderer(new ImageTableCellRenderer());
        fUpdateThread.start();
    }

    private void Update()
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTableQueues = new javax.swing.JTable();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(myqueue.MyQueueApp.class).getContext().getResourceMap(frmQueueManager.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N
        try {
            setSelected(true);
        } catch (java.beans.PropertyVetoException e1) {
            e1.printStackTrace();
        }
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosing(evt);
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });

        jScrollPane1.setName("jScrollPane1"); // NOI18N

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
        jTableQueues.setName("jTableQueues"); // NOI18N
        jTableQueues.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTableQueues);
        jTableQueues.getColumnModel().getColumn(0).setPreferredWidth(16);
        jTableQueues.getColumnModel().getColumn(0).setHeaderValue(resourceMap.getString("jTableQueues.columnModel.title4")); // NOI18N
        jTableQueues.getColumnModel().getColumn(1).setPreferredWidth(130);
        jTableQueues.getColumnModel().getColumn(1).setHeaderValue(resourceMap.getString("jTableQueues.columnModel.title0")); // NOI18N
        jTableQueues.getColumnModel().getColumn(2).setPreferredWidth(220);
        jTableQueues.getColumnModel().getColumn(2).setHeaderValue(resourceMap.getString("jTableQueues.columnModel.title1")); // NOI18N
        jTableQueues.getColumnModel().getColumn(3).setPreferredWidth(120);
        jTableQueues.getColumnModel().getColumn(3).setHeaderValue(resourceMap.getString("jTableQueues.columnModel.title2")); // NOI18N
        jTableQueues.getColumnModel().getColumn(4).setPreferredWidth(60);
        jTableQueues.getColumnModel().getColumn(4).setHeaderValue(resourceMap.getString("jTableQueues.columnModel.title3")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 619, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt)//GEN-FIRST:event_formInternalFrameClosing
    {//GEN-HEADEREND:event_formInternalFrameClosing
        fKeepUpdating = false;
        fUpdateThread.interrupt();
    }//GEN-LAST:event_formInternalFrameClosing

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableQueues;
    // End of variables declaration//GEN-END:variables
}
