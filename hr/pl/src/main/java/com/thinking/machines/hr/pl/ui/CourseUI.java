package com.thinking.machines.hr.pl.ui;

import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.pl.model.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*; // for File
// for URL of ImageIcon
// BL
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.filechooser.*; // for JFileChooser class

public class CourseUI extends JFrame implements DocumentListener, ListSelectionListener {
  private Container container;
  private JTable courseTable;
  private CourseModel courseModel;
  private JScrollPane scrollPane;
  private JLabel titleLabel;
  private JLabel searchLabel;
  private JLabel searchErrorLabel;
  private JButton searchTextFieldCancelButton;
  private JTextField searchTextField;
  private CoursePanel coursePanel;

  private enum MODE {
    VIEW,
    ADD,
    EDIT,
    DELETE,
    EXPORT_TO_PDF
  };

  private MODE mode;
  private ImageIcon logoIcon;
  private ImageIcon addIcon;
  private ImageIcon editIcon;
  private ImageIcon deleteIcon;
  private ImageIcon cancelIcon;
  private ImageIcon pdfIcon;
  private ImageIcon saveIcon;
  private ImageIcon updateIcon;

  public CourseUI() {
    initComponents();
    setAppearance();
    addListeners();
    setViewMode();
    coursePanel.setViewMode();
  }

  private void initComponents() {
    // container, table, scroll pane
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    container = getContentPane();
    courseModel = new CourseModel();
    courseTable = new JTable(courseModel);
    scrollPane =
        new JScrollPane(
            this.courseTable,
            ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    // label, button, text field
    titleLabel = new JLabel("Courses");
    searchLabel = new JLabel("Search");
    searchErrorLabel = new JLabel();
    searchTextFieldCancelButton = new JButton();
    searchTextField =
        new JTextField(); // don't set width now, cause we're going to set it using setBounds();
    coursePanel = new CoursePanel();
  }

  private void setAppearance() {
    // frame dimensions
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    int width = 480;
    int height = 600;
    setSize(width, height);
    setLocation((d.width / 2 - width / 2), (d.height / 2 - height / 2));
    // fonts
    Font titleLabelFont = new Font("Verdana", Font.BOLD, 18);
    Font searchLabelFont = new Font("Verdana", Font.BOLD, 16);
    Font searchErrorLabelFont = new Font("Verdana", Font.PLAIN, 12);
    Font columnHeaderFont = new Font("Times New Roman", Font.BOLD, 14);
    Font dataFont = new Font("Times New Roman", Font.PLAIN, 14);
    int lm = 0; // left margin
    int tm = 0; // top margin
    // table settings
    courseTable.setRowHeight(25);
    courseTable.getColumnModel().getColumn(0).setPreferredWidth(25);
    courseTable.getColumnModel().getColumn(1).setPreferredWidth(350);
    courseTable.setRowSelectionAllowed(true);
    courseTable.getTableHeader().setReorderingAllowed(false);
    courseTable.getTableHeader().setResizingAllowed(false);
    courseTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    // setting fonts
    titleLabel.setFont(titleLabelFont);
    searchLabel.setFont(searchLabelFont);
    searchErrorLabel.setFont(searchErrorLabelFont);
    searchErrorLabel.setForeground(Color.red);
    courseTable.getTableHeader().setFont(columnHeaderFont);
    courseTable.setFont(dataFont);
    // setBounds(left-margin,top-margin,x-size,y-size)
    titleLabel.setBounds(lm + 10, tm + 10, 200, 40);
    searchLabel.setBounds(lm + 10, tm + 10 + 40 + 10, 75, 30);
    searchTextField.setBounds(lm + 10 + 75, tm + 10 + 40 + 10, 300, 30);
    searchTextFieldCancelButton.setBounds(lm + 10 + 75 + 300 + 5, tm + 10 + 40 + 10, 30, 30);
    searchErrorLabel.setBounds(lm + 10 + 75 + 300 + 5 - 65, tm + 10 + 40 + 10 - 25, 75, 30);
    scrollPane.setBounds(lm + 10, tm + 10 + 40 + 10 + 30 + 10, 450, 300);
    coursePanel.setBounds(lm + 10, tm + 10 + 40 + 10 + 30 + 10 + 300 + 10, 450, 140);
    // icons
    logoIcon =
        new ImageIcon(
            getClass().getResource("/icons/logo.png")); // getResource() accepts only / frontslash
    setIconImage(logoIcon.getImage());
    // for button icons
    addIcon = new ImageIcon(getClass().getResource("/icons/add_icon.png"));
    editIcon = new ImageIcon(getClass().getResource("/icons/edit_icon.png"));
    deleteIcon = new ImageIcon(getClass().getResource("/icons/delete_icon.png"));
    cancelIcon = new ImageIcon(getClass().getResource("/icons/cancel_icon.png"));
    pdfIcon = new ImageIcon(getClass().getResource("/icons/pdf_icon.png"));
    saveIcon = new ImageIcon(getClass().getResource("/icons/save_icon.png"));
    updateIcon = new ImageIcon(getClass().getResource("/icons/update_icon.png"));
    // add to container
    container.setLayout(null);
    container.add(titleLabel);
    container.add(searchLabel);
    container.add(searchTextField);
    container.add(searchTextFieldCancelButton);
    container.add(searchErrorLabel);
    container.add(scrollPane);
    container.add(coursePanel);
  }

  private void addListeners() {
    searchTextField.getDocument().addDocumentListener(this);
    searchTextFieldCancelButton.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent ev) {
            searchTextField.setText("");
            searchTextField.requestFocus();
          }
        });
    courseTable.getSelectionModel().addListSelectionListener(this);
  }

  private void searchCourse() {
    searchErrorLabel.setText("");
    String title = searchTextField.getText().trim();
    if (title.length() == 0)
      return; // very important line. If sth is searched and found. Then if text field is made
              // empty, the first row should not get highlighted. The row which was last highlighted
              // should stay highlighted.
    int rowIndex = 0;
    try {
      rowIndex = courseModel.indexOfTitle(title, true);
    } catch (BLException blException) {
      searchErrorLabel.setText("Not found");
      return;
    }
    courseTable.setRowSelectionInterval(rowIndex, rowIndex);
    Rectangle rectangle = courseTable.getCellRect(rowIndex, 0, true);
    courseTable.scrollRectToVisible(rectangle);
  }

  public void changedUpdate(DocumentEvent ev) {
    searchCourse();
  }

  public void removeUpdate(DocumentEvent ev) {
    searchCourse();
  }

  public void insertUpdate(DocumentEvent ev) {
    searchCourse();
  }

  public void valueChanged(ListSelectionEvent ev) {
    int rowIndex = courseTable.getSelectedRow();
    try {
      CourseInterface tmpCourse = courseModel.getCourseAt(rowIndex);
      coursePanel.setCourse(tmpCourse);
    } catch (BLException blException) {
      coursePanel.clearCourse();
    }
  }

  // methods for changing between modes
  private void setViewMode() {
    // important
    this.mode = MODE.VIEW;
    if (courseModel.getRowCount()
        == 0) // if no records, why should these three be enabled? so disable them
    {
      // note: we request courseModel, and not courseTable, for count
      searchTextField.setEnabled(false);
      searchTextFieldCancelButton.setEnabled(false);
      courseTable.setEnabled(false);
    } else {
      searchTextField.setVisible(true);
      searchTextFieldCancelButton.setEnabled(true);
      courseTable.setEnabled(true);
    }
  }

  private void
      setAddMode() // same story for all other modes. But still we make new functions in case
                   // tomorrow something changes.
      {
    this.mode = MODE.ADD;
    searchTextField.setVisible(false);
    searchTextFieldCancelButton.setEnabled(false);
    courseTable.setEnabled(false);
  }

  private void setEditMode() {
    this.mode = MODE.EDIT;
    searchTextField.setVisible(false);
    searchTextFieldCancelButton.setEnabled(false);
    courseTable.setEnabled(false);
  }

  private void setDeleteMode() {
    this.mode = MODE.DELETE;
    searchTextField.setVisible(false);
    searchTextFieldCancelButton.setEnabled(false);
    courseTable.setEnabled(false);
  }

  private void setExportToPDFMode() {
    this.mode = MODE.EXPORT_TO_PDF;
    searchTextField.setVisible(false);
    searchTextFieldCancelButton.setEnabled(false);
    courseTable.setEnabled(false);
  }

  // inner class
  class CoursePanel extends JPanel {
    private JLabel titleCaptionLabel;
    private JLabel titleLabel;
    private JTextField titleTextField;
    private JButton clearTitleTextFieldButton;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton cancelButton;
    private JButton exportToPDFButton;
    private JPanel buttonsPanel;
    private CourseInterface course;

    CoursePanel() {
      this.setBorder(BorderFactory.createLineBorder(new Color(175, 175, 175)));
      initComponents();
      setAppearances();
      addListeners();
    }

    private void initComponents() {
      titleCaptionLabel = new JLabel("Course");
      titleLabel = new JLabel();
      titleTextField = new JTextField();
      clearTitleTextFieldButton = new JButton();

      addButton = new JButton(addIcon);
      editButton = new JButton(editIcon);
      deleteButton = new JButton(deleteIcon);
      cancelButton = new JButton(cancelIcon);
      exportToPDFButton = new JButton(pdfIcon);
      // test: setting icons, instead of just adding them to constructor
      editButton.setIcon(editIcon);
      deleteButton.setIcon(deleteIcon);
      cancelButton.setIcon(cancelIcon);
      exportToPDFButton.setIcon(pdfIcon);

      clearTitleTextFieldButton.setMargin(new Insets(0, 0, 0, 0));
      clearTitleTextFieldButton.setFont(new Font("Arial", Font.PLAIN, 12)); // or even 10
      CourseUI.this.searchTextFieldCancelButton.setMargin(new Insets(0, 0, 0, 0));
      CourseUI.this.searchTextFieldCancelButton.setFont(
          new Font("Arial", Font.PLAIN, 12)); // or even 10
      clearTitleTextFieldButton.setText("X");
      CourseUI.this.searchTextFieldCancelButton.setText("X");

      buttonsPanel = new JPanel();
    }

    private void setAppearances() {
      this.setLayout(null);
      int lm = 0; // left margin for CoursePanel
      int tm = 0; // right margin for CoursePanel
      Font titleCaptionFont = new Font("Verdana", Font.BOLD, 16);
      Font titleLabelFont = new Font("Verdana", Font.BOLD, 14);
      // setFont() for coursePanel
      titleCaptionLabel.setFont(titleCaptionFont);
      titleLabel.setFont(titleLabelFont);
      // setBounds() for coursePanel
      titleCaptionLabel.setBounds(lm + 10, tm, 75, 40);
      titleTextField.setBounds(lm + 10 + 75, tm + 10, 300, 30); // titleTextField and
      titleLabel.setBounds(
          lm + 10 + 75 + 20,
          tm + 5,
          300,
          30); // titleLabel will be in the same position. At a time only one will be visible.
      clearTitleTextFieldButton.setBounds(lm + 10 + 75 + 300 + 10, tm + 10, 30, 30);
      buttonsPanel.setBounds(lm + 10, tm + 10 + 40 + 10, 430, 70);
      buttonsPanel.setBorder(BorderFactory.createLineBorder(new Color(175, 175, 175)));

      // setting up buttonsPanel
      int lm1 = 0;
      int tm1 = 0;
      addButton.setBounds(lm1 + 20 + 75, tm1 + 20, 32, 32);
      editButton.setBounds(lm1 + 20 + 30 + 20 + 75, tm1 + 20, 32, 32);
      deleteButton.setBounds(lm1 + 20 + 30 + 20 + 30 + 20 + 75, tm1 + 20, 32, 32);
      cancelButton.setBounds(lm1 + 20 + 30 + 20 + 30 + 20 + 30 + 20 + 75, tm1 + 20, 32, 32);
      exportToPDFButton.setBounds(
          lm1 + 20 + 30 + 20 + 30 + 20 + 30 + 20 + 30 + 20 + 75, tm1 + 20, 32, 32);

      // adding buttons to buttonsPanel
      buttonsPanel.setLayout(null);
      buttonsPanel.add(addButton);
      buttonsPanel.add(editButton);
      buttonsPanel.add(deleteButton);
      buttonsPanel.add(cancelButton);
      cancelButton.setEnabled(false);
      buttonsPanel.add(exportToPDFButton);

      // adding components
      this.add(titleCaptionLabel);
      titleTextField.setVisible(false);
      this.add(titleTextField);
      this.add(titleLabel);
      this.add(clearTitleTextFieldButton);
      this.add(buttonsPanel);
    }

    private void addListeners() {
      clearTitleTextFieldButton.addActionListener(
          new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
              titleTextField.setText("");
              titleTextField.requestFocus();
            }
          });

      this.addButton.addActionListener(
          new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
              // first figure which mode, then make changes accordingly
              if (mode == MODE.VIEW) {
                coursePanel.setAddMode();
              } else {
                // logic to add and get back to view mode
                if (addCourse()) {
                  coursePanel.setViewMode();
                }
              }
            }
          });
      this.editButton.addActionListener(
          new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
              if (mode == MODE.VIEW) {
                coursePanel.setEditMode();
              } else {
                if (updateCourse()) {
                  coursePanel.setViewMode();
                }
              }
            }
          });

      this.deleteButton.addActionListener(
          new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
              coursePanel.setDeleteMode();
            }
          });

      this.cancelButton.addActionListener(
          new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
              mode = MODE.VIEW;
              coursePanel.setViewMode();
            }
          });
      this.exportToPDFButton.addActionListener(
          new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
              coursePanel.setExportToPDFMode();
              JFileChooser jFileChooser = new JFileChooser();
              jFileChooser.setCurrentDirectory(
                  new File(".")); // by default - current working directory
              jFileChooser.addChoosableFileFilter(
                  new javax.swing.filechooser.FileFilter() {
                    public boolean accept(File file) {
                      if (file.isDirectory()) return true;
                      if (file.getName().endsWith(".pdf")) return true;
                      return false;
                    }

                    public String getDescription() {
                      return ".pdf files";
                    }
                  });
              int selectedOption = jFileChooser.showSaveDialog(CourseUI.this);
              if (selectedOption == JFileChooser.APPROVE_OPTION) {
                File file = jFileChooser.getSelectedFile();
                try {
                  courseModel.exportToPDF(file);
                } catch (BLException blException) {
                  if (blException.hasGenericException()) {
                    JOptionPane.showMessageDialog(coursePanel, blException.getGenericException());
                  }
                  coursePanel.setViewMode();
                  return;
                }
                // control reached here means no exception and PDF created;
                JOptionPane.showMessageDialog(
                    CourseUI.this,
                    "PDF file successfully created",
                    "PDF created",
                    JOptionPane.INFORMATION_MESSAGE);
              }
              coursePanel.setViewMode();
            }
          });
    } // end of CourseUI addListeners()

    // email Doubt: PL should not have to make a course and pass. We should only pass the String to
    // model. Model should make an object and pass it further to BL
    private boolean addCourse() {
      String title = titleTextField.getText().trim();
      if (title.length() == 0) {
        // if we simply return, control will go back to where it was called from and enter
        // setViewMode(). Hence return a boolean.
        JOptionPane.showMessageDialog(coursePanel, "Title required");
        return false;
      }
      CourseInterface tmpCourse;
      tmpCourse = new Course();
      tmpCourse.setTitle(title);
      try {
        courseModel.add(tmpCourse);
        // either course is added, or exception is thrown
        int rowIndex = courseModel.indexOfCourse(tmpCourse);
        courseTable.setRowSelectionInterval(rowIndex, rowIndex);
        Rectangle rectangle = courseTable.getCellRect(rowIndex, 0, true);
        courseTable.scrollRectToVisible(rectangle);
        return true;
      } catch (BLException blException) {
        if (blException.hasGenericException()) {
          JOptionPane.showMessageDialog(coursePanel, blException.getGenericException());
        } else {
          JOptionPane.showMessageDialog(coursePanel, blException.getException("title"));
        }
        return false;
      }
    }

    private boolean updateCourse() {
      String title = titleTextField.getText().trim();
      if (title.length() == 0) {
        JOptionPane.showMessageDialog(this, "Title required");
        return false;
      }
      CourseInterface tmpCourse;
      tmpCourse = new Course();
      tmpCourse.setTitle(title);
      tmpCourse.setCode(course.getCode());
      try {
        courseModel.update(tmpCourse);
        // either course is updated, or exception is thrown
        int rowIndex = courseModel.indexOfCourse(tmpCourse);
        courseTable.setRowSelectionInterval(rowIndex, rowIndex);
        Rectangle rectangle = courseTable.getCellRect(rowIndex, 0, true);
        courseTable.scrollRectToVisible(rectangle);
        return true;
      } catch (BLException blException) {
        if (blException.hasGenericException()) {
          JOptionPane.showMessageDialog(coursePanel, blException.getGenericException());
        } else {
          JOptionPane.showMessageDialog(coursePanel, blException.getException("title"));
        }
        return false;
      }
    }

    private void deleteCourse() {
      String title = course.getTitle();
      int code = course.getCode();
      int selectedOption =
          JOptionPane.showConfirmDialog(
              this, "Delete " + title + "?", "Confirmation", JOptionPane.YES_NO_OPTION);
      if (selectedOption == JOptionPane.NO_OPTION || selectedOption == JOptionPane.CLOSED_OPTION) {
        return;
      }
      try {
        courseModel.remove(code);
      } catch (BLException blException) {
        if (blException.hasGenericException()) {
          JOptionPane.showMessageDialog(
              this, "Course not deleted. " + blException.getGenericException());
        } else {
          JOptionPane.showMessageDialog(
              this, "Course not deleted. " + blException.getException("code"));
        }
        return;
      }
      coursePanel.clearCourse();
      JOptionPane.showMessageDialog(this, "Course " + title + " deleted");
    }

    public void setCourse(CourseInterface course) {
      this.course = course;
      titleLabel.setText(course.getTitle());
    }

    public void clearCourse() {
      this.course = null;
      titleLabel.setText("");
    }

    void setViewMode() {
      CourseUI.this.setViewMode();
      titleLabel.setVisible(true);
      titleTextField.setText("");
      titleTextField.setVisible(false);
      clearTitleTextFieldButton.setEnabled(false);
      this.addButton.setIcon(addIcon);
      this.editButton.setIcon(editIcon);
      this.deleteButton.setIcon(deleteIcon);
      this.cancelButton.setIcon(cancelIcon);
      this.exportToPDFButton.setIcon(pdfIcon);
      addButton.setEnabled(true);
      cancelButton.setEnabled(false);
      if (courseTable.getRowCount() > 0) // table hablo entries
      {
        editButton.setEnabled(true);
        deleteButton.setEnabled(true);
        exportToPDFButton.setEnabled(true);
      } else {
        editButton.setEnabled(true);
        deleteButton.setEnabled(true);
        exportToPDFButton.setEnabled(true);
      }
    }

    void setAddMode() {
      CourseUI.this.setAddMode();
      //
      this.addButton.setIcon(saveIcon);
      this.addButton.setEnabled(true);
      this.editButton.setEnabled(false);
      this.deleteButton.setEnabled(false);
      this.cancelButton.setEnabled(true);
      this.exportToPDFButton.setEnabled(false);
      titleLabel.setVisible(false);
      titleTextField.setVisible(true);
      clearTitleTextFieldButton.setEnabled(true);
      titleTextField.requestFocus();
    }

    void setEditMode() {
      if (courseTable.getSelectedRow() < 0
          || courseTable.getSelectedRow() >= courseModel.getRowCount()) {
        JOptionPane.showMessageDialog(this, "Select course to edit");
        return; // won't go in edit mode, will stay in view mode
      }
      CourseUI.this.setEditMode();
      //
      this.addButton.setEnabled(false);
      this.editButton.setIcon(updateIcon);
      this.editButton.setEnabled(true);
      this.deleteButton.setEnabled(false);
      this.cancelButton.setEnabled(true);
      this.exportToPDFButton.setEnabled(false);
      titleLabel.setVisible(false);
      titleTextField.setText(course.getTitle());
      titleTextField.setVisible(true);
      clearTitleTextFieldButton.setEnabled(true);
      titleTextField.requestFocus();
    }

    void setDeleteMode() {
      if (courseTable.getSelectedRow() < 0
          || courseTable.getSelectedRow() >= courseModel.getRowCount()) {
        JOptionPane.showMessageDialog(this, "Select course to delete");
        return;
      }
      CourseUI.this.setDeleteMode();
      //
      this.addButton.setEnabled(false);
      this.editButton.setEnabled(false);
      this.deleteButton.setEnabled(
          false); // delete will also stay disabled. two clicks not allowed.
      this.cancelButton.setEnabled(false);
      this.exportToPDFButton.setEnabled(false);
      // delete is a special case. calling deleteCourse function from within
      // coursePanel.setDeleteMode(), because doing this in deleteButton listener complicates logic
      deleteCourse();
      this.setViewMode();
    }

    void setExportToPDFMode() {
      CourseUI.this.setExportToPDFMode();
      //
      this.addButton.setEnabled(false);
      this.editButton.setEnabled(false);
      this.deleteButton.setEnabled(false);
      this.cancelButton.setEnabled(false);
      this.exportToPDFButton.setEnabled(false);
    }
  } // inner class end
} // end of class
