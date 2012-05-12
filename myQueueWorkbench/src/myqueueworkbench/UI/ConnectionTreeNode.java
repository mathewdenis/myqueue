package myqueueworkbench.UI;

import javax.swing.tree.DefaultMutableTreeNode;
import myqueueworkbench.Connections.Connection;

/**
 *
 * @author Nikos Siatras
 */
public class ConnectionTreeNode extends DefaultMutableTreeNode
{

    private String fTitle;
    private Connection fConnection;

    public ConnectionTreeNode(String title, Connection connection)
    {
        super(title);
        fTitle = title;
        fConnection = connection;
    }
    
    public Connection getConnection()
    {
        return fConnection;
    }
}
