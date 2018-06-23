/**
 * @Copyrights GV
 */
package org.sloc.model;

import java.io.File;
import java.io.FileFilter;

import org.sloc.config.FileType;

/**
 * @author t_vaidhyanathan
 */
public class SLOCFileFilter implements FileFilter {
  private final FileType fileType;

  public SLOCFileFilter(FileType fileType_p) {
    this.fileType = fileType_p;
  }

  /**
   * @see java.io.FileFilter#accept(java.io.File)
   */
  @Override
  public boolean accept(File file) {
    if(file.isDirectory()) {
      return true;
    }
    for (String extn : fileType.getFileExtension()) {
      if (file.getName().trim().endsWith(extn)) {
        return true;
      }
    }
    return false;
  }

}
