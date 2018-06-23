/**
 * @Copyrights GV
 */
package org.sloc;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.sloc.config.FileType;
import org.sloc.model.SLOCTableModel;

/**
 * @author G. Vaidhyanathan
 */
public class FileCounter {

  private SLOCCounter counter;
  private SLOCTableModel model;
  SLOCController controller;

  private FileType fileType;
  private DecimalFormat decimalFormat;
//  private SLOCFileFilter filter;

  public FileCounter(FileType fileType_p, SLOCTableModel model_p) {
    this.fileType = fileType_p;

    this.model = model_p;
    this.controller = model_p.getView().getController();
//    filter = new SLOCFileFilter(fileType);
    counter = new SLOCCounter(fileType);
    decimalFormat = new DecimalFormat();
    decimalFormat.setMaximumFractionDigits(2);
    decimalFormat.setMinimumFractionDigits(2);
  }

  /**
   * 
   */
  public FileCounter(SLOCTableModel model_p) {
    super();
    this.model = model_p;
  }

  public List<File> loadFiles(String dirName_p) {
    ArrayList<File> filesToCount = new ArrayList<File>(1);
    try {
      File directory = new File(dirName_p);
      if (directory.isDirectory()) {
        try {
          doProcess(directory, filesToCount);
        } catch (Exception exception_p) {
          exception_p.printStackTrace();
        }
        // File[] files = directory.listFiles(filter);
        // for (File file : files) {
        // if (file.isFile()) {
        // // if (checkExtension(file.getName(), fileType.getFileExtension()))
        // filesToCount.add(file);
        // } else if (file.isDirectory()) {
        // filesToCount.addAll(loadFiles(file.getAbsolutePath()));
        // }
        // }
      }
    } catch (RuntimeException exception_p) {
      exception_p.printStackTrace();
    }
    return filesToCount;
  }

  /**
   * @param directory
   * @throws IOException
   * @throws InterruptedException
   */
  @SuppressWarnings("nls")
  private void doProcess(File directory, List<File> filesToCount) throws IOException, InterruptedException {
    String extn = fileType.getFileExtension().get(0);
    String cmd = "files.bat \"" + directory.toString() + "\" *" + extn;
    FileWriter file = new FileWriter("files.txt");
    Runtime rt = Runtime.getRuntime();
    Process process = rt.exec(cmd);
    BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));

    String line = null;
    while ((line = input.readLine()) != null) {
      System.out.println(line);
      if (line.endsWith(":")) {
        continue;
      }
      if (line.contains("cd \"" + directory.toString())) {
        continue;
      }
      if (line.contains("dir /S /B")) {
        continue;
      }
      if (line.trim().length() == 0) {
        continue;
      }
      filesToCount.add(new File(line));
      file.write(line);
      file.write("\n");
    }
    file.flush();
    file.close();
    process.waitFor();
  }

  boolean checkExtension(String fileName, List<String> extensions) {
    for (String extn : extensions) {
      if (fileName.trim().endsWith(extn)) {
        return true;
      }
    }
    return false;

  }

  @SuppressWarnings( { "unchecked", "boxing", "nls" })
  public void countSLOC(String dirName_p) {
    startTask();
    final List<File> list = loadFiles(dirName_p);
    endTask();

    int sum = 0;
    int totalSum = 0;
    Vector row = null;
    startTask();
    for (File file : list) {
      String absolutePath = file.getAbsolutePath();
      int sloc = 0;
      try {
        sloc = counter.getSLOCCount(absolutePath, controller.isBlankLinesIgnored());
      } catch (Exception exception_p) {
        exception_p.printStackTrace();
      }
      sum += sloc;
      int total = counter.getMap().get(absolutePath);
      totalSum += total;
      row = new Vector(1);
      row.add(file.getName());
      row.add(sloc);
      row.add(total);
      row.add(decimalFormat.format((total - sloc) * 1d / total * 100));
      row.add(absolutePath);
      model.addRow(row);
    }
    endTask();
    row = new Vector(1);
    row.add("Total ( " + model.getRowCount() + " files)");
    row.add(sum);
    row.add(totalSum);
    row.add(decimalFormat.format((totalSum - sum) * 1d / totalSum * 100));
    model.addRow(row);
  }

  void startTask() {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        controller.showBusyDialog();
      }
    });
  }

  void endTask() {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        controller.closeBusyDialog();
      }
    });
  }

}
