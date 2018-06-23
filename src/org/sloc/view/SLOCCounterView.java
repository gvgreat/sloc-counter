package org.sloc.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

import org.sloc.SLOCController;
import org.sloc.config.FileType;
import org.sloc.img.ImageRegistry;
import org.sloc.model.SLOCSortedComboBoxModel;
import org.sloc.model.SLOCTableModel;

@SuppressWarnings("serial")
public class SLOCCounterView extends JPanel implements ActionListener {

  private SLOCTableModel model;
  private SLOCController controller;
  private JPanel tablePanel = null;
  private JPanel topPanel = null;
  private JTextField txtDirName;
  private JButton actionCount;
  private JTable table = new JTable();
  private JScrollPane pane;
  public static final Font font = new Font("Tahoma", Font.PLAIN, 11); //$NON-NLS-1$
  private JComboBox cboType;
  private JPanel bottomPanel;
  private JButton actionExport;
  private JPopupMenu popupMenu;
  private JMenuItem actionOpen;
  private JMenuItem menuCount;
  private JMenuItem menuConfig;
  private JMenuItem menuAbout;
  private JMenuItem menuIgnoreBlankLines;

  private static final Border etchBorder = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
  private JTabbedPane tabPane;
  private JMenuBar menuBar;
  private Icon configIcon;
  private Icon countIcon;

  public static final String BROWSE = "Browse"; //$NON-NLS-1$
  public static final String COUNT = "Count SLOC"; //$NON-NLS-1$
  public static final String EXPORT = "Export to Excel"; //$NON-NLS-1$
  public static final String OPEN_FILE_CMD = "Open File"; //$NON-NLS-1$
  public static final String SHOW_COUNT_TAB = "Show Count Tab"; //$NON-NLS-1$
  public static final String SHOW_CONFIG_CMD = "Show Config Tab"; //$NON-NLS-1$
  public static final String SHOW_ABOUT_CMD = "Show About Dialog"; //$NON-NLS-1$
  public static final String IGNORE_BLANKLINES_CMD = "Ignore Blank Lines"; //$NON-NLS-1$

  public SLOCCounterView() {
    super();
    model = new SLOCTableModel();
  }

  /**
   *
   */
  @SuppressWarnings("nls")
  public void initComponents() {
    setLayout(new BorderLayout(5, 5));
    configIcon = ImageRegistry.getIcon(ImageRegistry.CONFIG_ICON);
    countIcon = ImageRegistry.getIcon(ImageRegistry.COUNT_ICON);
    createTopPanel();
    createPopupMenu();
    createTablePanel();
    createBottomPanel();
    createMenuBar();
    JPanel panel = new JPanel(new BorderLayout(10, 10));

    panel.add(topPanel, BorderLayout.NORTH);
    panel.add(tablePanel, BorderLayout.CENTER);
    panel.add(bottomPanel, BorderLayout.SOUTH);

    tabPane = new JTabbedPane(SwingConstants.TOP);
    tabPane.addTab("Counter", panel); //$NON-NLS-1$
    tabPane.addTab("Configuration", new SLOCConfigurationPanel((DefaultComboBoxModel) cboType.getModel(), this)); //$NON-NLS-1$
    tabPane.setFont(font);

    tabPane.setIconAt(0, countIcon);
    tabPane.setIconAt(1, configIcon);

    tabPane.setToolTipTextAt(0, "SLOC Counter Tab");
    tabPane.setToolTipTextAt(1, "SLOC Configuration Tab");

    add(tabPane, BorderLayout.CENTER);
    if (tabPane.isEnabledAt(0))
      tabPane.setSelectedIndex(0);
    else
      tabPane.setSelectedIndex(1);
  }

  /**
   *
   */
  @SuppressWarnings("nls")
  private void createMenuBar() {
    menuBar = new JMenuBar();
    JMenu fileMenu = new JMenu("File");
    fileMenu.setMnemonic(KeyStroke.getKeyStroke("F").getKeyCode());
    menuBar.add(fileMenu);
    menuCount = new JMenuItem();
    menuCount.setIcon(countIcon);
    menuCount.setText("Count");
    menuCount.setActionCommand(SHOW_COUNT_TAB);
    menuCount.addActionListener(this);
    fileMenu.add(menuCount);

    menuConfig = new JMenuItem();
    menuConfig.setText("Configure");
    menuConfig.setIcon(configIcon);
    menuConfig.setActionCommand(SHOW_CONFIG_CMD);
    menuConfig.addActionListener(this);
    fileMenu.add(menuConfig);

    JMenu helpMenu = new JMenu("Help");
    helpMenu.setMnemonic(KeyStroke.getKeyStroke("H").getKeyCode());
    menuBar.add(helpMenu);

    menuAbout = new JMenuItem();
    menuAbout.setText("About");
    menuAbout.setActionCommand(SHOW_ABOUT_CMD);
    menuAbout.addActionListener(this);
    menuAbout.setIcon(ImageRegistry.getIcon(ImageRegistry.ABOUT_ICON));
    helpMenu.add(menuAbout);

    JMenu optionsMenu = new JMenu("Options");
    optionsMenu.setMnemonic(KeyStroke.getKeyStroke("O").getKeyCode());
    menuBar.add(optionsMenu);
    
    menuIgnoreBlankLines = new JCheckBoxMenuItem();
    menuIgnoreBlankLines.setText("Ignore Blank Lines");
    menuIgnoreBlankLines.setActionCommand(IGNORE_BLANKLINES_CMD);
    menuIgnoreBlankLines.addActionListener(this);
    optionsMenu.add(menuIgnoreBlankLines);
  }

  /**
   *
   */
  @SuppressWarnings("nls")
  private void createPopupMenu() {
    popupMenu = new JPopupMenu("Open File");
    actionOpen = new JMenuItem(OPEN_FILE_CMD);
    actionOpen.setIcon(ImageRegistry.getIcon(ImageRegistry.OPEN_ICON));
    actionOpen.addActionListener(this);
    popupMenu.add(actionOpen);
  }

  /**
   *
   */
  private void createBottomPanel() {
    bottomPanel = new JPanel(new BorderLayout(10, 10));
    actionExport = new JButton(EXPORT);
    actionExport.addActionListener(this);
    bottomPanel.add(actionExport, BorderLayout.EAST);
    bottomPanel.setBorder(BorderFactory.createTitledBorder(etchBorder, "Export")); //$NON-NLS-1$
    actionExport.setEnabled(false);
    actionExport.setFont(font);
  }

  /**
   *
   */
  @SuppressWarnings( { "nls", "unchecked" })
  private void createTablePanel() {
    table.setFont(font);

    JTableHeader header = table.getTableHeader();
    header.setFont(font.deriveFont(Font.BOLD, 12));
    header.setBackground(new Color(0, 64, 64));
    header.setForeground(Color.WHITE);
    header.setToolTipText("Click for sorting");

    table.setModel(model);
    table.setAutoCreateColumnsFromModel(true);

    SLOCTableCellRenderer renderer = new SLOCTableCellRenderer();
    table.setDefaultRenderer(Integer.class, renderer);
    table.setDefaultRenderer(Object.class, renderer);
    table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    table.setRowSelectionAllowed(true);
    TableRowSorter sorter = new SLOCTableSorter(model);
    table.setRowSorter(sorter);
    sorter.setSortsOnUpdates(true);

    TableColumn absolutePathColumn = table.getColumn("Absolute Path");
    table.getColumnModel().removeColumn(absolutePathColumn);
    table.setToolTipText("Right/Double click to open the file");
    table.addMouseListener(new MouseAdapter() {

      /**
       * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
       */
      @SuppressWarnings("synthetic-access")
      @Override
      public void mouseClicked(MouseEvent me) {
        int index = table.rowAtPoint(me.getPoint());
        table.setRowSelectionInterval(index, index);

		if ((me.getButton() == MouseEvent.BUTTON3)) {
          popupMenu.show(table, me.getX(), me.getY());
        }
        if ((me.getClickCount() == 2)) {
          doOpenFile();
        }
      }
    });

    tablePanel = new JPanel(new BorderLayout(10, 10));
    pane = new JScrollPane();
    tablePanel.add(pane, BorderLayout.CENTER);
    pane.getViewport().setView(table);

    tablePanel.setBorder(BorderFactory.createTitledBorder(etchBorder, "SLOC")); //$NON-NLS-1$
  }

  /**
   *
   */
  @SuppressWarnings("nls")
  private void createTopPanel() {
    topPanel = new JPanel(new BorderLayout(10, 10));
    actionCount = new JButton(COUNT);
    actionCount.setFont(font);
    topPanel.add(actionCount, BorderLayout.EAST);
    actionCount.setEnabled(false);
    actionCount.addActionListener(this);

    JPanel browsePanel = new JPanel(new BorderLayout(2, 10));
    txtDirName = new JTextField();
    txtDirName.setFont(font);
    txtDirName.setEditable(false);
    txtDirName.setBackground(Color.WHITE);
    browsePanel.add(txtDirName, BorderLayout.CENTER);

    JButton actionBrowse = new JButton();
    actionBrowse.setActionCommand(BROWSE);
    actionBrowse.setIcon(ImageRegistry.getIcon(ImageRegistry.BROWSE_ICON));
    actionBrowse.addActionListener(this);
    actionBrowse.setFont(font);
    actionBrowse.setToolTipText("Browse...");
    browsePanel.add(actionBrowse, BorderLayout.EAST);

    topPanel.add(browsePanel, BorderLayout.CENTER);

    JLabel typeLabel = new JLabel("Select File type: ");
    typeLabel.setFont(font);
    cboType = new JComboBox(new SLOCSortedComboBoxModel());
    cboType.setFont(font);

    JPanel typePanel = new JPanel(new BorderLayout(5, 5));
    typePanel.add(typeLabel, BorderLayout.WEST);
    typePanel.add(cboType, BorderLayout.CENTER);

    topPanel.add(typePanel, BorderLayout.WEST);

    topPanel.setBorder(BorderFactory.createTitledBorder(etchBorder, "Folder Selection")); //$NON-NLS-1$
  }

  /**
   * @return the controller
   */
  public SLOCController getController() {
    return controller;
  }

  /**
   * @param controller_p
   *          the controller to set
   */
  public void setController(SLOCController controller_p) {
    controller = controller_p;
  }

  /**
   * @return the model
   */
  public SLOCTableModel getModel() {
    return model;
  }

  /**
   * @param model_p
   *          the model to set
   */
  public void setModel(SLOCTableModel model_p) {
    model = model_p;
  }

  /**
   * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
   */
  @SuppressWarnings("nls")
  public void actionPerformed(ActionEvent event_p) {
    String cmd = event_p.getActionCommand();
    if (cmd.equals(BROWSE)) {
      doBrowseOperation();
      actionExport.setEnabled(false);
    } else if (cmd.equals(COUNT)) {
      if (cboType.getSelectedItem() == null) {
        JOptionPane.showMessageDialog(this, "Select a File Type!", "SLOC Counter", JOptionPane.ERROR_MESSAGE);
        cboType.grabFocus();
        return;
      }
      FileType type = (FileType) cboType.getSelectedItem();
      controller.countSLOC(type, txtDirName.getText());
      actionCount.setEnabled(false);
      actionExport.setEnabled(true);
//      txtDirName.setText("");
//      cboType.setSelectedItem(null);
    } else if (cmd.equals(EXPORT)) {
      doExportOperation();
    } else if (cmd.equals(OPEN_FILE_CMD)) {
      doOpenFile();
    } else if (cmd.equals(SHOW_CONFIG_CMD)) {
      tabPane.setSelectedIndex(1);
    } else if (cmd.equals(SHOW_COUNT_TAB)) {
      if (tabPane.isEnabledAt(0)) {
        tabPane.setSelectedIndex(0);
      }
    } else if (cmd.equals(SHOW_ABOUT_CMD)) {
      showAboutDialog();
    }else if (cmd.equals(IGNORE_BLANKLINES_CMD)) {
      controller.setBlankLinesIgnored(menuIgnoreBlankLines.isSelected());
    }
  }

  private void doOpenFile() {
    int index = table.getSelectedRow();
    int modelIndex = table.convertRowIndexToModel(index);
    String fileName = model.getValueAt(modelIndex, 4).toString();
    controller.openFile(fileName);
  }

  /**
   *
   */
  @SuppressWarnings("nls")
  private void showAboutDialog() {
    String txt =
                 "<html>" + "<h2><font color=\"#004080\" face=\"Times New Roman\">" + "<b><i>SLOC Counter</i></b><br></h2></font>"
                     + "This is used to count the Source Lines Of Code from a given folder<br>" + "Also enables configuring file types<br>"
                     + "<h3><i><font color=\"Green\" face=\"Times New Roman\">" + ">>>>>>>>>>>>>>Done by G. Vaidhyanathan</i></h3>" + "</font>" + "</html>";
    JLabel lbl = new JLabel(txt);
    JOptionPane.showMessageDialog(this, lbl, "SLOC Counter", JOptionPane.INFORMATION_MESSAGE, ImageRegistry.getIcon(ImageRegistry.THUMBS_ICON));
  }

  @SuppressWarnings("nls")
  private void doExportOperation() {
    JFileChooser fileChooser = new JFileChooser();

    FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel Document", "xls");
    fileChooser.addChoosableFileFilter(filter);
    fileChooser.setAcceptAllFileFilterUsed(false);
    int operation = fileChooser.showSaveDialog(this);
    switch (operation) {
      case JFileChooser.APPROVE_OPTION:
        String fileName = fileChooser.getSelectedFile().getAbsolutePath();
        File file = new File(fileName);
        boolean flag = false;
        if (file.exists()) {
          int option =
                       JOptionPane.showConfirmDialog(this, "File already exists.\nReplace existing file?", "SLOC Counter", JOptionPane.WARNING_MESSAGE,
                                                     JOptionPane.OK_CANCEL_OPTION);
          flag = (option == JOptionPane.OK_OPTION);
        } else {
          fileName += "." + filter.getExtensions()[0];
          flag = true;
        }
        if (flag) {
          try {
            controller.exportToExcel(table, fileName);
          } catch (Exception exception_p) {
            exception_p.printStackTrace();
          }
        }
      break;
      case JFileChooser.CANCEL_OPTION:
      break;

      default:
      break;
    }
  }

  /**
   * Does the browse operation. Simply opens a file chooser
   */
  private void doBrowseOperation() {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    if(txtDirName.getText().trim().length() != 0) {
      fileChooser.setCurrentDirectory(new File(txtDirName.getText().trim()));
    }
    int operation = fileChooser.showOpenDialog(this);
    switch (operation) {
      case JFileChooser.APPROVE_OPTION:
        String fileName = fileChooser.getSelectedFile().getAbsolutePath();
        txtDirName.setText(fileName);
        actionCount.setEnabled(true);
      break;
      case JFileChooser.CANCEL_OPTION:
        txtDirName.setText(""); //$NON-NLS-1$
        actionCount.setEnabled(false);
      break;

      default:
      break;
    }
  }

  public void refreshViewPort() {
    EventQueue.invokeLater(new Runnable() {
      @SuppressWarnings("synthetic-access")
      public void run() {
        if (pane.getVerticalScrollBar().isVisible()) {
          Point pt = new Point(table.getWidth(), table.getHeight() + table.getRowHeight());
          pane.getViewport().setViewPosition(pt);
        }
      }
    });
  }

  public void setCountPaneEnabled(boolean flag) {
    tabPane.setEnabledAt(0, flag);
  }

  /**
   * @return the menuBar
   */
  public JMenuBar getMenuBar() {
    return menuBar;
  }
}
