/**
 * @Copyrights GV
 */
package org.sloc.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.sloc.config.FileType;
import org.sloc.img.ImageRegistry;

/**
 * @author G. Vaidhyanathan
 */
@SuppressWarnings("serial")
public class ConfigurationDataPanel extends JPanel implements ActionListener {
  
  private JTextField txtFileType;
  private JTextField txtFileExtension;
  private JTextField txtSingleLineComment;
  private JTextField txtMultiLineCommentStart;
  private JTextField txtMultiLineCommentEnd;

  private JLabel lblName;
  private JLabel lblType;
  private JLabel mlcLblStart;
  private JLabel mlcLblEnd;
  private JLabel slcLbl;

  private JButton actionAdd;

  private boolean isCreationMode;
  private boolean isUpdateMode;

  private static final String ADD_CMD = "Add File"; //$NON-NLS-1$
  private static final String UPDATE_CMD = "Update"; //$NON-NLS-1$
  private SLOCConfigurationPanel parent;

  public ConfigurationDataPanel(SLOCConfigurationPanel parent_p, boolean isCreationMode_p, boolean isUpdateMode_p) {
    super(new BorderLayout(5, 5));
    this.isCreationMode = isCreationMode_p;
    this.isUpdateMode = isUpdateMode_p;
    this.parent = parent_p;
    initComponents();
  }
  
  public ConfigurationDataPanel(boolean isCreationMode_p, boolean isUpdateMode_p) {
    super(new BorderLayout(5, 5));
    this.isCreationMode = isCreationMode_p;
    this.isUpdateMode = isUpdateMode_p;
    initComponents();
  }

  /**
   * 
   */
  @SuppressWarnings("nls")
  private void initComponents() {
    JPanel nameTypePanel = createNameTypePanel();
    JPanel commentPanel = createCommentPanel();

    setSizesForLabels();

    add(nameTypePanel, BorderLayout.NORTH);
    add(commentPanel, BorderLayout.CENTER);

    JPanel actionPanel = new JPanel(new BorderLayout(5, 5));
    actionAdd = new JButton();
    if (isUpdateMode) {
      actionAdd.setText(UPDATE_CMD);
      actionAdd.setIcon(ImageRegistry.getIcon(ImageRegistry.EDIT_ICON));
      actionAdd.setActionCommand(UPDATE_CMD);
    } else {
      actionAdd.setText(ADD_CMD);
      actionAdd.setIcon(ImageRegistry.getIcon(ImageRegistry.ADD_ICON));
      actionAdd.setActionCommand(ADD_CMD);
    }
    actionAdd.addActionListener(this);
    actionPanel.add(actionAdd, BorderLayout.EAST);

    if (isCreationMode) {
      add(actionPanel, BorderLayout.SOUTH);
    }

    setComponentsReadOnly(isCreationMode);
  }

  /**
   * @param readOnly_p
   */
  @SuppressWarnings("nls")
  private void setComponentsReadOnly(boolean readOnly_p) {
    txtFileType.setEditable(readOnly_p);
    txtFileExtension.setEditable(readOnly_p);
    txtSingleLineComment.setEditable(readOnly_p);
    txtMultiLineCommentStart.setEditable(readOnly_p);
    txtMultiLineCommentEnd.setEditable(readOnly_p);

    txtFileType.setBackground(Color.WHITE);
    txtFileExtension.setBackground(Color.WHITE);
    txtSingleLineComment.setBackground(Color.WHITE);
    txtMultiLineCommentStart.setBackground(Color.WHITE);
    txtMultiLineCommentEnd.setBackground(Color.WHITE);
    
    txtFileType.setToolTipText("File type e.g. Java, C, C++");
    txtFileExtension.setToolTipText("File Extension e.g. .java, .c, .c++");
    txtSingleLineComment.setToolTipText("Single Line Comment e.g. for java \"//\"");
    txtMultiLineCommentStart.setToolTipText("Multi Line Comment Start e.g. for java \"/*\"");
    txtMultiLineCommentEnd.setToolTipText("Multi Line Comment End e.g. for java \"*/\"");
  }

  /**
   * 
   */
  private void setSizesForLabels() {
    Dimension dim = mlcLblStart.getPreferredSize();
    lblName.setPreferredSize(dim);
    lblType.setPreferredSize(dim);
    mlcLblStart.setPreferredSize(dim);
    slcLbl.setPreferredSize(dim);
    mlcLblEnd.setPreferredSize(dim);
  }

  /**
   * @return
   */
  @SuppressWarnings("nls")
  private JPanel createCommentPanel() {
    JPanel commentPanel = new JPanel(new BorderLayout(5, 5));
    JPanel panel31 = new JPanel(new BorderLayout(5, 5));
    JPanel panel32 = new JPanel(new BorderLayout(5, 5));
    slcLbl = new JLabel("Single Line Comment Pattern: ");
    slcLbl.setFont(SLOCCounterView.font);
    txtSingleLineComment = new JTextField(10);
    txtSingleLineComment.setFont(SLOCCounterView.font);
    panel31.add(slcLbl, BorderLayout.WEST);
    panel31.add(txtSingleLineComment, BorderLayout.CENTER);

    mlcLblStart = new JLabel("Multi Line Comment Pattern (Start): ");
    mlcLblStart.setFont(SLOCCounterView.font);
    txtMultiLineCommentStart = new JTextField(10);
    txtMultiLineCommentStart.setFont(SLOCCounterView.font);
    panel32.add(mlcLblStart, BorderLayout.WEST);
    panel32.add(txtMultiLineCommentStart, BorderLayout.CENTER);

    mlcLblEnd = new JLabel("Multi Line Comment Pattern (End): ");
    mlcLblEnd.setFont(SLOCCounterView.font);
    txtMultiLineCommentEnd = new JTextField(10);
    txtMultiLineCommentEnd.setFont(SLOCCounterView.font);

    JPanel panel33 = new JPanel(new BorderLayout(5, 5));
    panel33.add(mlcLblEnd, BorderLayout.WEST);
    panel33.add(txtMultiLineCommentEnd, BorderLayout.CENTER);

    commentPanel.add(panel31, BorderLayout.NORTH);
    commentPanel.add(panel32, BorderLayout.CENTER);
    commentPanel.add(panel33, BorderLayout.SOUTH);
    return commentPanel;
  }

  /**
   * 
   */
  private JPanel createNameTypePanel() {
    JPanel nameTypePanel = new JPanel(new BorderLayout(5, 5));

    JPanel panel1 = new JPanel(new BorderLayout(5, 5));
    lblName = new JLabel("File Type*: "); //$NON-NLS-1$
    lblName.setFont(SLOCCounterView.font);
    txtFileType = new JTextField(10);
    txtFileType.setFont(SLOCCounterView.font);
//    PresentationModel model = new PresentationModel(fileType);
//    Bindings.bind(txtFileType, model.getModel("fileType"));
    panel1.add(lblName, BorderLayout.WEST);
    panel1.add(txtFileType, BorderLayout.CENTER);

    JPanel panel2 = new JPanel(new BorderLayout(5, 5));
    lblType = new JLabel("File Extension*: "); //$NON-NLS-1$
    lblType.setFont(SLOCCounterView.font);
    txtFileExtension = new JTextField(10);
    txtFileExtension.setFont(SLOCCounterView.font);

    panel2.add(lblType, BorderLayout.WEST);
    panel2.add(txtFileExtension, BorderLayout.CENTER);

    nameTypePanel.add(panel1, BorderLayout.NORTH);
    nameTypePanel.add(panel2, BorderLayout.CENTER);

    return nameTypePanel;
  }

  public String getFileType() {
    return txtFileType.getText().trim();
  }

  public String getFileExtension() {
    return txtFileExtension.getText().trim();
  }

  public String getSingleLineComment() {
    return txtSingleLineComment.getText().trim();
  }

  public String getMultiLineCommentStart() {
    return txtMultiLineCommentStart.getText().trim();
  }

  public String getMultiLineCommentEnd() {
    return txtMultiLineCommentEnd.getText().trim();
  }

  public void setFileType(String fileType_p) {
    txtFileType.setText(fileType_p);
  }

  public void setFileExtension(String fileExtn_p) {
    txtFileExtension.setText(fileExtn_p);
  }

  public void setSingleLineComment(String slc) {
    txtSingleLineComment.setText(slc);
  }

  public void setMultiLineCommentStart(String mlcStart) {
    txtMultiLineCommentStart.setText(mlcStart);
  }

  public void setMultiLineCommentEnd(String mlcEnd) {
    txtMultiLineCommentEnd.setText(mlcEnd);
  }

  /**
   * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
   */
  public void actionPerformed(ActionEvent e_p) {
    String cmd = e_p.getActionCommand();
    if (cmd.equals(ADD_CMD)) {
      if (parent.doAddOperation(this))
        parent.disposeDialog();
    } else if (cmd.equals(UPDATE_CMD)) {
      if (parent.doUpdateOperation(this))
        parent.disposeDialog();
    }
  }

  public void displayData(FileType type) {
    if (type == null) {
      setEmptyValues();
      return;
    }
    this.setFileType(type.getFileType());
    String fileExtn = type.getFileExtension().toString();
    this.setFileExtension(fileExtn.substring(1, fileExtn.length() - 1));
    this.setSingleLineComment(type.getSinglelineComment());
    this.setMultiLineCommentStart(type.getMultilineCommentStart());
    this.setMultiLineCommentEnd(type.getMultilineCommentEnd());
  }
  
  @SuppressWarnings("nls")
  public void setEmptyValues() {
    this.setFileType("");
    this.setFileExtension("");
    this.setSingleLineComment("");
    this.setMultiLineCommentStart("");
    this.setMultiLineCommentEnd("");
  }
  
  public static void main(String args[]) {
    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setContentPane(new ConfigurationDataPanel(true, false));
    frame.pack();
    frame.setVisible(true);
  }

}
