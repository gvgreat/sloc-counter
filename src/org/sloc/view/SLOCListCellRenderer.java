/**
 * @Copyrights GV
 */
package org.sloc.view;

import java.awt.Color;
import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.SwingConstants;

import org.sloc.config.FileType;

/**
 * @author G. Vaidhyanathan
 *
 */
@SuppressWarnings("serial")
public class SLOCListCellRenderer extends DefaultListCellRenderer {

  /**
   * @see javax.swing.DefaultListCellRenderer#getListCellRendererComponent(javax.swing.JList, java.lang.Object, int, boolean, boolean)
   */
  @Override
  public Component getListCellRendererComponent(JList list_p, Object value_p, int index_p, boolean isSelected_p, boolean cellHasFocus_p) {
    JLabel label = (JLabel) super.getListCellRendererComponent(list_p, value_p, index_p, isSelected_p, cellHasFocus_p);
    label.setHorizontalAlignment(SwingConstants.CENTER);
    if (isSelected_p) {
      label.setBackground(Color.YELLOW);
      FileType type = (FileType) value_p;
      label.setText(type.getFullString());
      label.setFont(SLOCCounterView.font);
      label.setForeground(Color.BLUE);
    } else {
      int mod = index_p % 2;
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
    return label;
  }
}
