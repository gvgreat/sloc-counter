/**
 * @Copyrights GV
 */
package org.sloc.view;

import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.sloc.config.FileType;

/**
 * @author G. Vaidhyanathan
 *
 */
public class SLOCListSelectionListener implements ListSelectionListener {
  
  private ConfigurationDataPanel configDataPanel;


  public SLOCListSelectionListener(ConfigurationDataPanel panel) {
    this.configDataPanel = panel;
  }
  

  /**
   * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
   */
  public void valueChanged(ListSelectionEvent e_p) {
    JList list = (JList) e_p.getSource();
    FileType type = (FileType) list.getSelectedValue();
    configDataPanel.displayData(type);
  }

}
 