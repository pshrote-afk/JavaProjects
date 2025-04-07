package com.thinking.machines.hr.pl.ui;
import com.thinking.machines.hr.pl.model.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
//BL
import com.thinking.machines.hr.bl.exceptions.*;
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
public void initComponents()
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
//this.add(titleTextField);
this.add(titleLabel);
this.add(clearTitleTextFieldButton);
this.add(buttonsPanel);
}
public void addListeners()
{
}
}
}//end of class