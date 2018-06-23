/**
 * @Copyrights GV
 */
package org.sloc.img;

import java.io.InputStream;
import java.util.Hashtable;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * @author G. Vaidhyanathan
 *
 */
@SuppressWarnings("nls")
public class ImageRegistry {
  
  public static final String ABOUT_ICON = "About Icon";
  public static final String ADD_ICON = "Add Icon";
  public static final String CONFIG_ICON = "Config Icon";
  public static final String COUNT_ICON = "Count Icon";
  public static final String DELETE_ICON = "Delete Icon";
  public static final String EDIT_ICON = "Edit Icon";
  public static final String LOADING_ICON = "Loading Icon";
  public static final String OPEN_ICON = "Open Icon";
  public static final String BROWSE_ICON = "Browse Icon";
  public static final String THUMBS_ICON = "Thumbs Icon";
  
  private static Map<String, Icon> imageTable = new Hashtable<String, Icon>(1);
  
  static {
    loadIcons();
  }

  private static void loadIcons() {
    imageTable.put(ABOUT_ICON, new ImageIcon(ImageRegistry.class.getResource("about.PNG")));
    imageTable.put(ADD_ICON, new ImageIcon(ImageRegistry.class.getResource("add.PNG")));
    imageTable.put(BROWSE_ICON, new ImageIcon(ImageRegistry.class.getResource("browse.png")));
    imageTable.put(CONFIG_ICON, new ImageIcon(ImageRegistry.class.getResource("config.PNG")));
    imageTable.put(COUNT_ICON, new ImageIcon(ImageRegistry.class.getResource("count.PNG")));
    imageTable.put(EDIT_ICON, new ImageIcon(ImageRegistry.class.getResource("edit.PNG")));
    imageTable.put(DELETE_ICON, new ImageIcon(ImageRegistry.class.getResource("delete.PNG")));
    imageTable.put(OPEN_ICON, new ImageIcon(ImageRegistry.class.getResource("open.PNG")));
    imageTable.put(LOADING_ICON, new ImageIcon(ImageRegistry.class.getResource("loading.gif")));
    imageTable.put(THUMBS_ICON, new ImageIcon(ImageRegistry.class.getResource("thumbs.PNG")));
  }
  
  public static Icon getIcon(String iconKey) {
    return imageTable.get(iconKey);
  }
}
