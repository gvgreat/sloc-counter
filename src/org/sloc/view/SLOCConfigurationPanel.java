/**
 * @Copyrights GV
 */
package org.sloc.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import org.sloc.SLOCCounterUtil;
import org.sloc.config.FileType;
import org.sloc.config.FileTypes;
import org.sloc.img.ImageRegistry;
import org.sloc.model.SLOCSortedListModel;

/**
 * @author G. Vaidhyanathan
 */
@SuppressWarnings("serial")
public class SLOCConfigurationPanel extends JPanel implements ActionListener {

  private JPanel bottomPanel;
  private JPanel topPanel;
  private Border etchBorder = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
  private JButton actionAdd;
  private static final String ADD_CMD = "Add"; //$NON-NLS-1$
  private static final String EDIT_CMD = "Edit"; //$NON-NLS-1$
  private static final String DELETE_CMD = "Delete"; //$NON-NLS-1$

  private DefaultListModel model;
  private JList list;
  private JScrollPane pane;

  private SLOCCounterUtil util = SLOCCounterUtil.getInstance();
  private ConfigurationDataPanel configDataPanel;

  private DefaultComboBoxModel comboModel;
  private JDialog addFileDialog;

  private JMenuItem actionEdit;
  private JMenuItem actionDelete;
  private JPopupMenu popupMenu;
  private SLOCCounterView view;

  @SuppressWarnings("nls")
  public SLOCConfigurationPanel(DefaultComboBoxModel comboModel_p, SLOCCounterView view_p) {
    super();

    this.comboModel = comboModel_p;
    this.view = view_p;

    setLayout(new BorderLayout());

    createTopPanel();
    createBottomPanel();
    createPopupMenu();
    add(topPanel, BorderLayout.NORTH);
    add(bottomPanel, BorderLayout.CENTER);
  }

  @SuppressWarnings("nls")
  private void createPopupMenu() {
    actionEdit = new JMenuItem(EDIT_CMD);
    actionEdit.setIcon(ImageRegistry.getIcon(ImageRegistry.EDIT_ICON));
    actionEdit.setFont(SLOCCounterView.font);
    actionEdit.addActionListener(this);

    actionDelete = new JMenuItem(DELETE_CMD);
    actionDelete.setIcon(ImageRegistry.getIcon(ImageRegistry.DELETE_ICON));
    actionDelete.setFont(SLOCCounterView.font);
    actionDelete.addActionListener(this);

    popupMenu = new JPopupMenu();
    popupMenu.setFont(SLOCCounterView.font);
    popupMenu.add(actionEdit);
    popupMenu.addSeparator();
    popupMenu.add((actionDelete));
  }

  /**
   * 
   */
  @SuppressWarnings("nls")
  private void createTopPanel() {
    topPanel = new JPanel(new BorderLayout(5, 5));
    topPanel.setBorder(BorderFactory.createTitledBorder(etchBorder, "Configuration Data")); //$NON-NLS-1$

    JPanel actionPanel = new JPanel(new BorderLayout(5, 5));
    actionAdd = new JButton(ADD_CMD);
    actionAdd.setFont(SLOCCounterView.font);
    actionAdd.addActionListener(this);
    actionAdd.setIcon(ImageRegistry.getIcon(ImageRegistry.ADD_ICON));
    actionPanel.add(actionAdd, BorderLayout.EAST);

    configDataPanel = new ConfigurationDataPanel(this, false, false);

    topPanel.add(configDataPanel, BorderLayout.CENTER);
    topPanel.add(actionPanel, BorderLayout.SOUTH);
  }

  /**
   * 
   */
  private void createBottomPanel() {
    bottomPanel = new JPanel(new BorderLayout());
    bottomPanel.setBorder(BorderFactory.createTitledBorder(etchBorder, "Existing Types")); //$NON-NLS-1$

    model = new SLOCSortedListModel();

    addItemsToModel();
    list = new JList(model);
    list.setFont(SLOCCounterView.font);
    list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    list.setCellRenderer(new SLOCListCellRenderer());
    pane = new JScrollPane(list);
    bottomPanel.add(pane);

    list.addListSelectionListener(new SLOCListSelectionListener(configDataPanel));

    list.addMouseListener(new MouseAdapter() {

      /**
       * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
       */
      @SuppressWarnings("synthetic-access")
      @Override
      public void mouseClicked(MouseEvent me) {
        int index = list.locationToIndex(me.getPoint());
        list.setSelectedIndex(index);
        if(me.getClickCount() == 2) {
          createAndShowAddDialog(true);
          return;
        }
        if (me.getButton() == MouseEvent.BUTTON3) {
          popupMenu.show(list, me.getX(), me.getY());
        }
      }

    });
  }

  /**
   * 
   */
  private void addItemsToModel() {
    FileTypes types = util.getFileTypes();
    for (FileType type : types.getFile()) {
      model.addElement(type);
      comboModel.addElement(type);
    }
    view.setCountPaneEnabled(!model.isEmpty());
  }

  /**
   * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
   */
  public void actionPerformed(ActionEvent e_p) {
    String cmd = e_p.getActionCommand();
    if (cmd.equals(ADD_CMD)) {
      createAndShowAddDialog(false);
    } else if (cmd.equals(EDIT_CMD)) {
      createAndShowAddDialog(true);
    } else if (cmd.equals(DELETE_CMD)) {
      doRemoveOperation();
    }
  }

  @SuppressWarnings("nls")
  public boolean doAddOperation(ConfigurationDataPanel dataPanel) {
    String fileName = dataPanel.getFileType();
    String fileExtn = dataPanel.getFileExtension();
    String singleLC = dataPanel.getSingleLineComment();
    String multiLCStart = dataPanel.getMultiLineCommentStart();
    String multiLCEnd = dataPanel.getMultiLineCommentEnd();

    boolean flag = fileName.trim().length() == 0;
    flag |= fileExtn.trim().length() == 0;
    // flag |= singleLC.trim().length() == 0;
    // flag |= multiLCStart.trim().length() == 0;
    // flag |= multiLCEnd.trim().length() == 0;

    if (flag) {
      JOptionPane.showMessageDialog(this, "FileType and Extension are required!", "SLOC Counter", JOptionPane.ERROR_MESSAGE);
      return false;
    }

    FileType currentFileType = new FileType();
    currentFileType.setFileType(fileName);
    StringTokenizer strtok = new StringTokenizer(fileExtn, ",");
    while (strtok.hasMoreTokens()) {
      currentFileType.getFileExtension().add(strtok.nextToken().trim());
    }

    currentFileType.setSinglelineComment(singleLC);
    currentFileType.setMultilineCommentStart(multiLCStart);
    currentFileType.setMultilineCommentEnd(multiLCEnd);
    if (!util.addFileType(currentFileType)) {
      JOptionPane.showMessageDialog(this, "File Type already exists!", "SLOC Counter", JOptionPane.ERROR_MESSAGE);
      return false;
    }
    model.addElement(currentFileType);
    comboModel.addElement(currentFileType);
    if (pane.getVerticalScrollBar().isVisible()) {
      Dimension dim = list.getPreferredSize();
      Point pt = new Point(dim.width, dim.height);
      pane.getViewport().setViewPosition(pt);
    }
    view.setCountPaneEnabled(!model.isEmpty());
    list.setSelectedValue(currentFileType, true);
    return true;
  }

  @SuppressWarnings("nls")
  public boolean doUpdateOperation(ConfigurationDataPanel dataPanel) {
    doRemoveOperation();
    return doAddOperation(dataPanel);
  }

  public void doRemoveOperation() {
    FileType currentFileType = (FileType) list.getSelectedValue();
    util.removeFileType(currentFileType);
    model.removeElement(currentFileType);
    comboModel.removeElement(currentFileType);
    view.setCountPaneEnabled(!model.isEmpty());
  }

  @SuppressWarnings("nls")
  private void createAndShowAddDialog(boolean isUpdateMode) {
    String title = isUpdateMode ? "Edit File Type" : "Add File Type";

    addFileDialog = new JDialog(JOptionPane.getFrameForComponent(this), title, true);
    ConfigurationDataPanel dataPanel = new ConfigurationDataPanel(this, true, isUpdateMode);
    dataPanel.setBorder(BorderFactory.createTitledBorder(etchBorder, "Configuration Data"));
    addFileDialog.setContentPane(dataPanel);
    addFileDialog.pack();
    addFileDialog.setLocationRelativeTo(this);
    if (isUpdateMode) {
      FileType type = (FileType) list.getSelectedValue();
      dataPanel.displayData(type);
    }
    addFileDialog.setVisible(true);
  }

  public void disposeDialog() {
    if (addFileDialog != null)
      addFileDialog.dispose();
  }
}
