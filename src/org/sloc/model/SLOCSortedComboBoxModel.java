/**
 * @Copyrights GV
 */
package org.sloc.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;

/**
 * @author G. Vaidhyanathan
 */
@SuppressWarnings("serial")
public class SLOCSortedComboBoxModel extends DefaultComboBoxModel {

  public SLOCSortedComboBoxModel() {
    super();
  }

  public SLOCSortedComboBoxModel(Object[] items) {
    Arrays.sort(items);
    int size = items.length;

    for (int i = 0; i < size; i++) {
      super.addElement(items[i]);
    }

    setSelectedItem(items[0]);
  }

  @SuppressWarnings("unchecked")
  public SLOCSortedComboBoxModel(Vector items) {
    Collections.sort(items);
    int size = items.size();

    for (int i = 0; i < size; i++) {
      super.addElement(items.elementAt(i));
    }

    setSelectedItem(items.elementAt(0));
  }

  @Override
  public void addElement(Object element) {
    insertElementAt(element, 0);
  }

  @SuppressWarnings("unchecked")
  @Override
  public void insertElementAt(Object element, int index) {
    int size = getSize();

    // Determine where to insert element to keep model in sorted order
    int ind = 0;
    for (; ind < size; ind++) {
      Comparable comparable = (Comparable) getElementAt(ind);
      if (comparable.compareTo(element) > 0)
        break;
    }

    super.insertElementAt(element, ind);
  }
}
