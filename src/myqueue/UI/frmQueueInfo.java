package myqueue.UI;

import myqueue.Core.MyQueue;
import myqueue.Core.QueueManager;

/**
 *
 * @author Nikos Siatras
 */
public class frmQueueInfo extends javax.swing.JFrame
{

    private String fQueue;
    private boolean fUpdateThreadRun = false;

    public frmQueueInfo(String queue)
    {
        initComponents();
        this.setTitle(queue);
        fQueue = queue;

        Thread updateThread = new Thread(new Runnable()
        {

            @Override
            public void run()
            {
                while (fUpdateThreadRun)
                {
                    try
                    {
                        Update();
                        Thread.sleep(1000);
                    }
                    catch (Exception ex)
                    {
                    }
                }

            }
        });
        fUpdateThreadRun = true;
        updateThread.start();
    }

    private void Update()
    {
        MyQueue queue;
        if (QueueManager.getQueues().containsKey(fQueue))
        {
            queue = QueueManager.getQueues().get(fQueue);

            jLabelBytesIn.setText(String.valueOf(queue.getBytesIn()));
            jLabelBytesOut.setText(String.valueOf(queue.getBytesOut()));
            jLabelConnectedClients.setText(String.valueOf(queue.getCurrentConnectionsNumber()));
        }
        else
        {
            fUpdateThreadRun = false;
            this.dispose();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabelBytesIn = new javax.swing.JLabel();
        jLabelBytesOut = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabelConnectedClients = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jLabel1.setText("Bytes In:");

        jLabel2.setText("Bytes Out:");

        jLabelBytesIn.setText("0");

        jLabelBytesOut.setText("0");

        jLabel3.setText("Connected Clients:");

        jLabelConnectedClients.setText("0");

        jButton1.setText("Close");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2))
                                .addGap(25, 25, 25)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabelBytesOut, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabelBytesIn, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addComponent(jLabelConnectedClients, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(161, 161, 161)
                        .addComponent(jButton1)))
                .addContainerGap(161, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabelBytesIn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabelBytesOut))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabelConnectedClients))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton1ActionPerformed
    {//GEN-HEADEREND:event_jButton1ActionPerformed
        fUpdateThreadRun = false;
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabelBytesIn;
    private javax.swing.JLabel jLabelBytesOut;
    private javax.swing.JLabel jLabelConnectedClients;
    // End of variables declaration//GEN-END:variables
}
