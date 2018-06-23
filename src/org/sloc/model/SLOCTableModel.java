/**
 * @Copyrights GV
 */
package org.sloc.model;

import java.util.Arrays;

import javax.swing.table.DefaultTableModel;

import org.sloc.view.SLOCCounterView;

/**
 * @author G. Vaidhyanathan
 */
@SuppressWarnings("serial")
public class SLOCTableModel extends DefaultTableModel {

  private SLOCCounterView view;

  @SuppressWarnings( { "nls", "unchecked" })
  public SLOCTableModel() {
    super();
    columnIdentifiers.addAll(Arrays.asList(new String[] { "FileName", "SLOC", "Total LOC", "Comment Density (%)", "Absolute Path" }));
    setDataVector(dataVector, columnIdentifiers);
  }

  @Override
  public boolean isCellEditable(int row, int col) {
    return false;
  }

  /**
   * @return the view
   */
  public SLOCCounterView getView() {
    return view;
  }

  /**
   * @param view_p
   *          the view to set
   */
  public void setView(SLOCCounterView view_p) {
    view = view_p;
  }

  /**
   * @see javax.swing.table.AbstractTableModel#fireTableRowsInserted(int, int)
   */
  @Override
  public void fireTableRowsInserted(int firstRow_p, int lastRow_p) {
    super.fireTableRowsInserted(firstRow_p, lastRow_p);
    view.refreshViewPort();
  }

  /**
   * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
   */
  @Override
  public Class<?> getColumnClass(int columnIndex_p) {
    if (getRowCount() > 0) {
      return getValueAt(0, columnIndex_p).getClass();
    }
    return super.getColumnClass(columnIndex_p);
  }

}
