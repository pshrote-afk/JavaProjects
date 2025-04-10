package com.thinking.machines.hr.pl.ui;
import com.thinking.machines.hr.pl.model.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
//BL
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.pojo.*;
public class CourseUI extends JFrame implements DocumentListener
{
private Container container;
private JTable courseTable;
private CourseModel courseModel;
private JScrollPane scrollPane;
private JLabel titleLabel;
private JLabel searchLabel;
private JLabel searchErrorLabel;
private JButton searchCancelButton;
private JTextField searchTextField;
private CoursePanel coursePanel;
private enum MODE{VIEW,ADD,EDIT,DELETE,EXPORT_TO_PDF};
private MODE mode;
public CourseUI()
{
initComponents();
setAppearance();
addListeners();
setViewMode();
coursePanel.setViewMode();
}
private void initComponents()
{
//container, table, scroll pane
setDefaultCloseOperation(EXIT_ON_CLOSE);
container = getContentPane();
courseModel = new CourseModel();
courseTable = new JTable(courseModel);
scrollPane = new JScrollPane(this.courseTable,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//label, button, text field
titleLabel = new JLabel("Courses");
searchLabel = new JLabel("Search");
searchErrorLabel = new JLabel();
searchCancelButton = new JButton("X");
searchTextField = new JTextField(); //don't set width now, cause we're going to set it using setBounds();
coursePanel = new CoursePanel();
}
private void setAppearance()
{
//frame dimensions
Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
int width = 480;
int height = 600;
setSize(width,height);
setLocation((d.width/2 - width/2),(d.height/2 - height/2));
//fonts
Font titleLabelFont = new Font("Verdana",Font.BOLD,18);
Font searchLabelFont = new Font("Verdana",Font.BOLD,16);
Font searchErrorLabelFont = new Font("Verdana",Font.PLAIN,12);
Font columnHeaderFont = new Font("Times New Roman",Font.BOLD,14);
Font dataFont = new Font("Times New Roman",Font.PLAIN,14);
int lm = 0; //left margin
int tm = 0; //top margin
//table settings
courseTable.setRowHeight(25);
courseTable.getColumnModel().getColumn(0).setPreferredWidth(25);
courseTable.getColumnModel().getColumn(1).setPreferredWidth(350);
courseTable.setRowSelectionAllowed(true);
courseTable.getTableHeader().setReorderingAllowed(false);
courseTable.getTableHeader().setResizingAllowed(false);
courseTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//setting fonts
titleLabel.setFont(titleLabelFont);
searchLabel.setFont(searchLabelFont);
searchErrorLabel.setFont(searchErrorLabelFont);
searchErrorLabel.setForeground(Color.red);
courseTable.getTableHeader().setFont(columnHeaderFont);
courseTable.setFont(dataFont);
//setBounds(left-margin,top-margin,x-size,y-size)
titleLabel.setBounds(lm+10,tm+10,200,40);
searchLabel.setBounds(lm+10,tm+10+40+10,75,30);
searchTextField.setBounds(lm+10+75,tm+10+40+10,300,30);
searchCancelButton.setBounds(lm+10+75+300+5,tm+10+40+10,30,30);
searchErrorLabel.setBounds(lm+10+75+300+5-65,tm+10+40+10-25,75,30);
scrollPane.setBounds(lm+10,tm+10+40+10+30+10,450,300);
coursePanel.setBounds(lm+10,tm+10+40+10+30+10+300+10,450,140);
//add to container
container.setLayout(null);
container.add(titleLabel);
container.add(searchLabel);
container.add(searchTextField);
container.add(searchCancelButton);
container.add(searchErrorLabel);
container.add(scrollPane);
container.add(coursePanel);
}
private void addListeners()
{
searchTextField.getDocument().addDocumentListener(this);
searchCancelButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
searchTextField.setText("");
searchTextField.requestFocus();
}
});
}
private void searchCourse()
{
searchErrorLabel.setText("");
String title = searchTextField.getText().trim();
if(title.length()==0) return;	//very important line. If sth is searched and found. Then if text field is made empty, the first row should not get highlighted. The row which was last highlighted should stay highlighted.
int rowIndex=0;
try
{
rowIndex = courseModel.indexOfTitle(title,true);
}catch(BLException blException)
{
searchErrorLabel.setText("Not found");
return;
}
courseTable.setRowSelectionInterval(rowIndex,rowIndex);
Rectangle rectangle = courseTable.getCellRect(rowIndex,0,true);
courseTable.scrollRectToVisible(rectangle);
}
public void changedUpdate(DocumentEvent ev)
{
searchCourse();
}
public void removeUpdate(DocumentEvent ev)
{
searchCourse();
}
public void insertUpdate(DocumentEvent ev)
{
searchCourse();
}
private void setViewMode()
{
//important
this.mode = MODE.VIEW;
if(courseModel.getRowCount() == 0) //if no records, why should these three be enabled?
{
//note: we request courseModel, and not courseTable, for count
searchTextField.setEnabled(false);
searchCancelButton.setEnabled(false);
courseTable.setEnabled(false);
}
else
{
searchTextField.setEnabled(true);
searchCancelButton.setEnabled(true);
courseTable.setEnabled(true);
}
}
private void setAddMode() //same story for all other modes. But still we make new functions in case tomorrow something changes.
{
this.mode = MODE.ADD;
searchTextField.setEnabled(false);
searchCancelButton.setEnabled(false);
courseTable.setEnabled(false);
}
private void setEditMode()
{
this.mode = MODE.EDIT;
searchTextField.setEnabled(false);
searchCancelButton.setEnabled(false);
courseTable.setEnabled(false);
}
private void setDeleteMode()
{
this.mode = MODE.DELETE;	
searchTextField.setEnabled(false);
searchCancelButton.setEnabled(false);
courseTable.setEnabled(false);
}
private void setExportToPDFMode()
{ 
this.mode = MODE.EXPORT_TO_PDF;
searchTextField.setEnabled(false);
searchCancelButton.setEnabled(false);
courseTable.setEnabled(false);
}
//inner class
class CoursePanel extends JPanel
{
private JLabel titleCaptionLabel;
private JLabel titleLabel;
private JTextField titleTextField;
private JButton clearTitleTextFieldButton;
private JButton addButton;
private JButton saveButton;
private JButton editButton;
private JButton deleteButton;
private JButton cancelButton;
private JButton exportToPDFButton;
private JPanel buttonsPanel;	
CoursePanel()
{
this.setBorder(BorderFactory.createLineBorder(new Color(175,175,175)));
initComponents();
setAppearances();
addListeners();
}
private void initComponents()
{
titleCaptionLabel = new JLabel("Course");
titleLabel = new JLabel("temporary text");
titleTextField = new JTextField();
clearTitleTextFieldButton = new JButton();
addButton = new JButton("A");
saveButton = new JButton("Save");
editButton = new JButton("B");
deleteButton = new JButton("C");
cancelButton = new JButton("D");
exportToPDFButton = new JButton("E");
buttonsPanel = new JPanel();
}
private void setAppearances()
{
this.setLayout(null);
int lm = 0; //left margin for CoursePanel
int tm = 0; //right margin for CoursePanel
Font titleCaptionFont = new Font("Verdana",Font.BOLD,16);
Font titleLabelFont = new Font("Verdana",Font.BOLD,14);
//setFont() for coursePanel
titleCaptionLabel.setFont(titleCaptionFont);
titleLabel.setFont(titleLabelFont);
//setBounds() for coursePanel
titleCaptionLabel.setBounds(lm+10,tm,75,40);
titleTextField.setBounds(lm+10+75,tm+10,300,30); //titleTextField and
titleLabel.setBounds(lm+10+75+20,tm+5,300,30); //titleLabel will be in the same position. At a time only one will be visible.
clearTitleTextFieldButton.setBounds(lm+10+75+300+10,tm+10,30,30);
buttonsPanel.setBounds(lm+10,tm+10+40+10,430,70);
buttonsPanel.setBorder(BorderFactory.createLineBorder(new Color(175,175,175)));

//setting up buttonsPanel
int lm1 = 0;
int tm1 = 0;
addButton.setBounds(lm1+20+75,tm1+20,30,30);
saveButton.setBounds(lm1+20+75,tm1+20,30,30);
editButton.setBounds(lm1+20+30+20+75,tm1+20,30,30);
deleteButton.setBounds(lm1+20+30+20+30+20+75,tm1+20,30,30);
cancelButton.setBounds(lm1+20+30+20+30+20+30+20+75,tm1+20,30,30);
exportToPDFButton.setBounds(lm1+20+30+20+30+20+30+20+30+20+75,tm1+20,30,30);

//adding buttons to buttonsPanel
buttonsPanel.setLayout(null);
buttonsPanel.add(addButton);
buttonsPanel.add(saveButton);
buttonsPanel.add(editButton);
buttonsPanel.add(deleteButton);
buttonsPanel.add(cancelButton);
buttonsPanel.add(exportToPDFButton); 

//adding components
this.add(titleCaptionLabel);
this.add(titleTextField);
this.add(titleLabel);
this.add(clearTitleTextFieldButton);
this.add(buttonsPanel);
}
private void addCourse()
{
String title = titleTextField.getText().trim();
if(title.length()==0)
{
//if we simply return, control will go back to where it was called from and enter setViewMode()
// ??? assignment
}
CourseInterface tmpCourse = new Course();
tmpCourse.setTitle(title);
try
{
courseModel.add(tmpCourse);
int rowIndex = 0;
try
{
rowIndex = courseModel.indexOfCourse(tmpCourse);
}catch(BLException blException)
{
// do nothing
}
courseTable.setRowSelectionInterval(rowIndex,rowIndex);
Rectangle rectangle = courseTable.getCellRect(rowIndex,0,true);
courseTable.scrollRectToVisible(rectangle);
}catch(BLException blException)
{
// ??? 
}
}
private void updateCourse()
{


}
private void addListeners()
{
this.addButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
//first figure which mode, then make changes accordingly
if(mode == MODE.VIEW)
{
setAddMode();
}
else
{
//logic to save and get back to view mode
addCourse();
setViewMode();
}
}
});
this.editButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
if(mode == MODE.VIEW)
{
setEditMode();
}
else
{
//logic to save and get back to view mode
updateCourse();
setViewMode();
}
}
});
this.cancelButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
setViewMode();
}
});
}
void setViewMode()
{//addButton always visible
CourseUI.this.setViewMode();
this.addButton.setText("A");
this.editButton.setText("E");
this.titleTextField.setVisible(false);
this.clearTitleTextFieldButton.setVisible(false);
this.titleLabel.setVisible(true);
this.addButton.setEnabled(true);
this.cancelButton.setEnabled(false);
if(courseModel.getRowCount()>0)
{
this.editButton.setEnabled(true);
this.deleteButton.setEnabled(true);
this.exportToPDFButton.setEnabled(true);
}
else
{
this.editButton.setEnabled(false);
this.deleteButton.setEnabled(false);
this.exportToPDFButton.setEnabled(false);
}
}
void setAddMode()
{
CourseUI.this.setAddMode();
//above function disables all Components from outer class first
this.titleTextField.setText("");
this.titleLabel.setVisible(false);
this.clearTitleTextFieldButton.setVisible(true);
this.titleTextField.setVisible(true);
addButton.setText("S");
editButton.setEnabled(false);
cancelButton.setEnabled(true);
deleteButton.setEnabled(false);
exportToPDFButton.setEnabled(false);
}
void setEditMode()
{
//nothing is selected
if(courseTable.getSelectedRow() < 0 || courseTable.getSelectedRow()>=courseModel.getRowCount())
{
JOptionPane.showMessageDialog(this,"Select course to edit");
return; //won't go in edit mode, will stay in view mode
}
CourseUI.this.setEditMode();
//
this.titleTextField.setText(course.getTitle());
this.titleLabel.setVisible(false);
this.titleTextField.setVisible(true);
this.clearTitleTextFieldButton.setVisible(true);
addButton.setEnabled(false);
cancelButton.setEnabled(true);
deleteButton.setEnabled(false);
exportToPDFButton.setEnabled(false);
editButton.setText("U");
}
void setDeleteMode()
{
if(courseTable.getSelectedRow() < 0 || courseTable.getSelectedRow()>=courseModel.getRowCount())
{
JOptionPane.showMessageDialog(this,"Select course to delete");
return; 
}
CourseUI.this.setDeleteMode();
//
addButton.setEnabled(false);
editButton.setEnabled(false);
cancelButton.setEnabled(true);
deleteButton.setEnabled(false); //delete will also stay disabled. two clicks not allowed.
exportToPDFButton.setEnabled(false);

}

void setExportToPDFMode()
{
CourseUI.this.setExportToPDFMode();
//
addButton.setEnabled(false);
editButton.setEnabled(false);
cancelButton.setEnabled(true);
deleteButton.setEnabled(false); 
exportToPDFButton.setEnabled(false);

}

}//end of inner class
}//end of class