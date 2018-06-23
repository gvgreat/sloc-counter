/**
 * @Copyrights GV
 */
package org.sloc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.sloc.config.FileType;
import org.sloc.config.FileTypes;
import org.sloc.config.ObjectFactory;

/**
 * @author G. Vaidhyanathan
 */
public class SLOCCounterUtil {
  private static final SLOCCounterUtil instance = new SLOCCounterUtil();
  private FileTypes fileTypes;

  public static SLOCCounterUtil getInstance() {
    return instance;
  }

  static {
    getInstance().loadXML();
  }

  @SuppressWarnings("nls")
  public void saveXML() {
    try {
      String fileName = "file-types.xml";
      if (fileTypes == null) {
        ObjectFactory factory = new ObjectFactory();
        fileTypes = factory.createFileTypes();
      }

      ClassLoader theClassLoader = getClass().getClassLoader();

      JAXBContext jc = JAXBContext.newInstance("org.sloc.config", theClassLoader);

      Marshaller marshaller = jc.createMarshaller();
      marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
      marshaller.marshal(fileTypes, new FileOutputStream(fileName));
    } catch (JAXBException ex) {
      ex.printStackTrace();
    } catch (FileNotFoundException ex) {
      ex.printStackTrace();
    }
  }

  @SuppressWarnings("nls")
  public void loadXML() {
    try {
      String fileName = "file-types.xml";

      File file = new File(fileName);
      if (!file.exists()) {
        file.createNewFile();
        writeToFile(file);
      }

      ClassLoader theClassLoader = getClass().getClassLoader();

      JAXBContext jc = JAXBContext.newInstance("org.sloc.config", theClassLoader);

      Unmarshaller unmarshaller = jc.createUnmarshaller();
      fileTypes = (FileTypes) unmarshaller.unmarshal(new FileInputStream(file));

    } catch (JAXBException ex) {
      ex.printStackTrace();
    } catch (FileNotFoundException ex) {
      ex.printStackTrace();
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  /**
   * @param file_p
   * @throws IOException 
   */
  @SuppressWarnings("nls")
  private void writeToFile(File file_p) throws IOException {
    FileWriter writer = new FileWriter(file_p);
    writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n<fileTypes/>");
    writer.flush();
    writer.close();   
  }

  public boolean addFileType(FileType fileType) {
    List<FileType> list = fileTypes.getFile();
    if (!list.contains(fileType)) {
      list.add(fileType);
      return true;
    }
    return false;
  }
  
  public void removeFileType(FileType fileType) {
    List<FileType> list = fileTypes.getFile();
    list.remove(fileType);
  }

  /**
   * @return the fileTypes
   */
  public FileTypes getFileTypes() {
    return fileTypes;
  }
}
