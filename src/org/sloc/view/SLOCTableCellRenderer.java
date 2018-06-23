/**
 * @Copyrights GV
 */
package org.sloc.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * @author t_vaidhyanathan
 */
@SuppressWarnings("serial")
public class SLOCTableCellRenderer extends DefaultTableCellRenderer {
  Font font = SLOCCounterView.font.deriveFont(Font.BOLD, 12);
  /**
   * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
   */
  @SuppressWarnings("nls")
  @Override
  public Component getTableCellRendererComponent(JTable table_p, Object value_p, boolean isSelected_p, boolean hasFocus_p, int row_p, int column_p) {
    JLabel label = (JLabel) super.getTableCellRendererComponent(table_p, value_p, isSelected_p, hasFocus_p, row_p, column_p);
    label.setFont(SLOCCounterView.font);
    if (isSelected_p) {
      label.setBackground(Color.YELLOW);
      label.setForeground(Color.BLUE);
    } else {
      
      int mod = row_p % 2;
      switch (mod) {
        case 0:
          label.setBackground(new Color(171, 214, 214));
          label.setForeground(new Color(0, 0, 106));
        break;
        case 1:
          label.setBackground(Color.WHITE);
          label.setForeground(Color.BLACK);
        break;
        default:
        break;
      }
    }
    if(column_p == 0) {
      label.setHorizontalAlignment(SwingConstants.LEFT);
    } else if(column_p == 1 || row_p == -1) {
      label.setHorizontalAlignment(SwingConstants.CENTER);
    }
    if(row_p == table_p.getRowCount()-1) {
      label.setBackground(new Color(0, 64, 64));
      label.setForeground(Color.WHITE);
      label.setFont(font);
      label.setHorizontalAlignment(SwingConstants.CENTER);
    }
    return label;
  }
  /**
   * @see javax.swing.table.DefaultTableCellRenderer#isOpaque()
   */
  @Override
  public boolean isOpaque() {
    // TODO Auto-generated method stub
    return true;
  }
  
  

}
