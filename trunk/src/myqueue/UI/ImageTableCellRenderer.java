package myqueue.UI;

import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Nikos Siatras
 */
public class ImageTableCellRenderer extends JLabel implements TableCellRenderer
{

    public ImageTableCellRenderer()
    {

    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
        setIcon((ImageIcon) value);
        return this;
    }
}
