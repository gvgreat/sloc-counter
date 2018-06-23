package org.sloc;

import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;

import org.sloc.config.FileType;

public class SLOCCounter {

  private FileType fileType;
  private boolean hasSingleLineComment;
  private boolean hasMultiLineComment;
  private String singleLineComment;
  private String multiLineCommentStart;
  private String multiLineCommentEnd;
  private Map<String, Integer> map = new HashMap<String, Integer>(1);

  public SLOCCounter(FileType type_p) {
    this.fileType = type_p;
    hasSingleLineComment = fileType.getSinglelineComment().trim().length() > 0;
    hasMultiLineComment = (fileType.getMultilineCommentStart().trim().length() > 0) && (fileType.getMultilineCommentEnd().trim().length() > 0);

    singleLineComment = fileType.getSinglelineComment().trim();
    multiLineCommentStart = fileType.getMultilineCommentStart().trim();
    multiLineCommentEnd = fileType.getMultilineCommentEnd().trim();
  }

  public int getSLOCCount(String fileName) throws Exception {
    return getSLOCCount(fileName, false);
  }

  @SuppressWarnings("boxing")
  public int getSLOCCount(String fileName, boolean ignoreBlankLines) throws Exception {
    RandomAccessFile raf = new RandomAccessFile(fileName, "r"); //$NON-NLS-1$

    int lineCount = 0;
    int totalLineCount = 0;
    String line = null;
    boolean commentStarted = false;
    while ((line = raf.readLine()) != null) {
      totalLineCount++; // counting the total lines...
      boolean commentEnded = false;
      String trimmedLine = line.trim();
      if (trimmedLine.length() == 0) {
        if (ignoreBlankLines) {
          totalLineCount--;
        }
        continue;
      }

      if (hasSingleLineComment) {
        if (trimmedLine.startsWith(singleLineComment) || isQTPExtension(trimmedLine)) {
          continue;
        }

      }
      if (hasMultiLineComment) {
        if (trimmedLine.startsWith(multiLineCommentStart)) {
          commentStarted = true;
          commentEnded = false;
        }
        if (trimmedLine.endsWith(multiLineCommentEnd)) {
          commentEnded = true;

        }
      }
      if (commentStarted) {
        if (commentEnded) {
          commentStarted = false;
        } else {
          continue;
        }
      } else {
        lineCount++;
      }
    }
    map.clear();
    map.put(fileName, totalLineCount);
    raf.close();
    return lineCount;
  }

  /**
   * @param trimmedLine_p
   * @return
   */
  private boolean isQTPExtension(String trimmedLine) {
    if (fileType.getFileExtension().contains(".mts")) { //$NON-NLS-1$
      if (trimmedLine.substring(3).startsWith(singleLineComment)) {
        String qtpStart = trimmedLine.substring(0, 3);
        char[] splChars = qtpStart.toCharArray();
        if (splChars[0] == 0XEF && splChars[1] == 0XBB && splChars[2] == 0XBF) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * @return the map
   */
  public Map<String, Integer> getMap() {
    return map;
  }

  /**
   * @param map_p
   *          the map to set
   */
  public void setMap(Map<String, Integer> map_p) {
    map = map_p;
  }
}
