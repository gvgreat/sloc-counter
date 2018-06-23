/**
 * @Copyrights Thalesgroup MDE
 */
package org.sloc.util;

import java.util.Enumeration;
import java.util.Iterator;

/**
 * @author t_vaidhyanathan
 */
public class IterableEnumeration<E> implements Iterable<E> {

  private final Enumeration<E> enumeration;

  public IterableEnumeration(Enumeration<E> enumeration_p) {
    this.enumeration = enumeration_p;
  }

  /**
   * @see java.lang.Iterable#iterator()
   */
  @Override
  public Iterator<E> iterator() {
    return new Iterator<E>() {

      @SuppressWarnings("synthetic-access")
      @Override
      public boolean hasNext() {
        return enumeration.hasMoreElements();
      }

      @SuppressWarnings("synthetic-access")
      @Override
      public E next() {
        return enumeration.nextElement();
      }

      @Override
      public void remove() {
        // TODO Auto-generated method stub
        
      }
      
    };
  }

}
