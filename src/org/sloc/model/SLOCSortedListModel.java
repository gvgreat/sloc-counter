/**
 * @Copyrights GV
 */
package org.sloc.model;

import javax.swing.DefaultListModel;

/**
 * @author G. Vaidhyanathan
 *
 */
@SuppressWarnings("serial")
public class SLOCSortedListModel extends DefaultListModel {

  public SLOCSortedListModel() {
    super();
  }

  /**
   * @see javax.swing.DefaultListModel#add(int, java.lang.Object)
   */
  @Override
  public void add(int index_p, Object element_p) {
    insertElementAt(element_p, index_p);
  }

  /**
   * @see javax.swing.DefaultListModel#addElement(java.lang.Object)
   */
  @Override
  public void addElement(Object obj_p) {
    insertElementAt(obj_p, 0);
  }

  /**
   * @see javax.swing.DefaultListModel#insertElementAt(java.lang.Object, int)
   */
  @SuppressWarnings("unchecked")
  @Override
  public void insertElementAt(Object element_p, int index_p) {
    int size = getSize();

    // Determine where to insert element to keep model in sorted order
    int ind = 0;
    for (; ind < size; ind++) {
      Comparable comparable = (Comparable) getElementAt(ind);
      if (comparable.compareTo(element_p) > 0)
        break;
    }
    super.insertElementAt(element_p, ind);
  }
}
