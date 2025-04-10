package com.thinking.machines.hr.pl.ui;
import com.thinking.machines.hr.pl.model.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
//BL
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;
public class CourseUI extends JFrame implements DocumentListener,ListSelectionListener
{
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
public CourseUI()
{
initComponents();
setAppearance();
addListeners();
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
searchTextFieldCancelButton = new JButton("X");
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
searchTextFieldCancelButton.setBounds(lm+10+75+300+5,tm+10+40+10,30,30);
searchErrorLabel.setBounds(lm+10+75+300+5-65,tm+10+40+10-25,75,30);
scrollPane.setBounds(lm+10,tm+10+40+10+30+10,450,300);
coursePanel.setBounds(lm+10,tm+10+40+10+30+10+300+10,450,140);
//add to container
container.setLayout(null);
container.add(titleLabel);
container.add(searchLabel);
container.add(searchTextField);
container.add(searchTextFieldCancelButton);
container.add(searchErrorLabel);
container.add(scrollPane);
container.add(coursePanel);
}
private void addListeners()
{
searchTextField.getDocument().addDocumentListener(this);
searchTextFieldCancelButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
searchTextField.setText("");
searchTextField.requestFocus();
}
});
courseTable.getSelectionModel().addListSelectionListener(this);
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
public void valueChanged(ListSelectionEvent ev)
{
int rowIndex = courseTable.getSelectedRow();
try
{
CourseInterface tmpCourse = courseModel.getCourseAt(rowIndex);
coursePanel.setCourse(tmpCourse);
}catch(BLException blException)
{
coursePanel.clearCourse();
}

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
private CourseInterface course;
CoursePanel()
{
this.setBorder(BorderFactory.createLineBorder(new Color(175,175,175)));
initComponents();
setAppearances();
addListeners();
}
public void initComponents()
{
titleCaptionLabel = new JLabel("Course");
titleLabel = new JLabel("temporary text");
titleTextField = new JTextField();
clearTitleTextFieldButton = new JButton("x");
addButton = new JButton("A");
saveButton = new JButton(new ImageIcon("../icons/saveButton.png"));
editButton = new JButton("B");
deleteButton = new JButton("C");
cancelButton = new JButton("D");
exportToPDFButton = new JButton("E");
buttonsPanel = new JPanel();
}
public void setAppearances()
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
addButton.setBounds(lm1+20+75,tm1+20,40,40);
saveButton.setBounds(lm1+20+75,tm1+20,40,40);
editButton.setBounds(lm1+20+30+20+75,tm1+20,40,40);
deleteButton.setBounds(lm1+20+30+20+30+20+75,tm1+20,40,40);
cancelButton.setBounds(lm1+20+30+20+30+20+30+20+75,tm1+20,40,40);
exportToPDFButton.setBounds(lm1+20+30+20+30+20+30+20+30+20+75,tm1+20,40,40);

//adding buttons to buttonsPanel
buttonsPanel.setLayout(null);
buttonsPanel.add(addButton);
buttonsPanel.add(saveButton);
saveButton.setVisible(false);  
buttonsPanel.add(editButton);
buttonsPanel.add(deleteButton);
buttonsPanel.add(cancelButton);
cancelButton.setEnabled(false);
buttonsPanel.add(exportToPDFButton);

//adding components
this.add(titleCaptionLabel);
titleTextField.setVisible(false);
this.add(titleTextField);
this.add(titleLabel);
this.add(clearTitleTextFieldButton);
this.add(buttonsPanel);
}
public void addListeners()
{
clearTitleTextFieldButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
titleTextField.setText("");
titleTextField.requestFocus();
}
});
addButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
//enable/disable buttons
CourseUI.this.searchTextFieldCancelButton.setEnabled(false);
addButton.setVisible(false); //using setVisible instead of setEnabled cause buttons are overlapping, merely enabling/disabling is giving issues like: saveButton not responding after enabling
saveButton.setVisible(true);
editButton.setEnabled(false);
deleteButton.setEnabled(false);
cancelButton.setEnabled(true);
exportToPDFButton.setEnabled(false);

//other component visibility
CourseUI.this.searchTextField.setText("");	//cleared main search
CourseUI.this.searchTextField.setVisible(false);
titleLabel.setVisible(false);
titleTextField.setVisible(true);

//table related
courseTable.setRowSelectionAllowed(false);

}
});
cancelButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
//enable/disable buttons
CourseUI.this.searchTextFieldCancelButton.setEnabled(true);
addButton.setVisible(true);
saveButton.setVisible(false);
editButton.setEnabled(true);
deleteButton.setEnabled(true);
cancelButton.setEnabled(false);
exportToPDFButton.setEnabled(true);

//other component visibility
CourseUI.this.searchTextField.setVisible(true);
titleLabel.setVisible(true);
titleTextField.setText("");
titleTextField.setVisible(false);

//table related
courseTable.setRowSelectionAllowed(true);

}
});
saveButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
String title = titleTextField.getText().trim();
if(title.length() == 0)
{
JOptionPane.showMessageDialog(CourseUI.this,"Title required","Error",JOptionPane.ERROR_MESSAGE);
return; //don't go back to "regular view" by enabling/disabling components, stay in this "addButton" view
}
try
{
//Doubt: PL should not have to make a course and pass. We should only pass the String to model. Model should make an object and pass it further to BL
CourseInterface course = new Course();
course.setTitle(title);
courseModel.add(course);
JOptionPane.showMessageDialog(CourseUI.this,"Course added successfully!","Success",JOptionPane.INFORMATION_MESSAGE);
}catch(BLException blException)
{
JOptionPane.showMessageDialog(CourseUI.this,blException.getException("title"),"Error",JOptionPane.ERROR_MESSAGE); //here the "title" in blException.getException(title); is name of property against which there is an exception
return;
}
//enable/disable buttons
CourseUI.this.searchTextFieldCancelButton.setEnabled(true);
addButton.setVisible(true);
saveButton.setVisible(false);
editButton.setEnabled(true);
deleteButton.setEnabled(true);
cancelButton.setEnabled(false);
exportToPDFButton.setEnabled(true);

//other component visibility
CourseUI.this.searchTextField.setVisible(true);
titleLabel.setVisible(true);
titleTextField.setText("");
titleTextField.setVisible(false);

//table related
courseTable.setRowSelectionAllowed(true);
}

});
}
public void setCourse(CourseInterface course)
{
titleLabel.setText(course.getTitle());
}
public void clearCourse()
{
titleLabel.setText("");

}

}//inner class end
}//end of class