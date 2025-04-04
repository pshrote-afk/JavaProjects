package com.thinking.machines.hr.pl.ui;
import com.thinking.machines.hr.pl.model.*;
import javax.swing.*;
import java.awt.*;
public class CourseUI extends JFrame
{
private JTable courseTable;
private Container container;
private JLabel titleLabel;
private JLabel searchBarLabel;
private JTextField searchTextField;
private JButton searchTextFieldCancelButton;
private JLabel searchErrorLabel;
private JScrollPane scrollPane;
private CoursePanel coursePanel;
public CourseUI()
{
initComponents(); //for Object creation
setAppearance(); //for font, etc
addListeners(); //"add" Listeners 
}
private void initComponents()
{
CourseModel courseModel = new CourseModel();
courseTable = new JTable(courseModel);
container = getContentPane();
titleLabel = new JLabel("Designations");
searchBarLabel = new JLabel("Search");
searchTextField = new JTextField();
searchErrorLabel = new JLabel("Not found");
searchTextFieldCancelButton = new JButton("X");
scrollPane = new JScrollPane(courseTable,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
coursePanel = new CoursePanel();
this.setDefaultCloseOperation(EXIT_ON_CLOSE);
}
private void setAppearance()
{
int lm = 0; //left margin
int tm = 0; //top margin
this.setLayout(null);
Font titleFont = new Font("Arial",Font.BOLD,18);
Font searchFont = new Font("Arial",Font.BOLD,16);
Font dataFont = new Font("Times New Roman",Font.PLAIN,16);
Font tableHeaderFont = new Font("Times New Roman",Font.BOLD,16);
Font searchErrorFont = new Font("Arial",Font.PLAIN,12);
courseTable.setRowHeight(20);
courseTable.getColumnModel().getColumn(0).setPreferredWidth(25);
courseTable.getColumnModel().getColumn(1).setPreferredWidth(350);
courseTable.getTableHeader().setReorderingAllowed(false);
courseTable.getTableHeader().setResizingAllowed(false);
courseTable.setRowSelectionAllowed(true);
courseTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


courseTable.setFont(dataFont);
courseTable.getTableHeader().setFont(tableHeaderFont);
titleLabel.setFont(titleFont);
searchBarLabel.setFont(searchFont);
searchErrorLabel.setFont(searchErrorFont);
searchErrorLabel.setForeground(Color.red);
titleLabel.setBounds(lm+10,tm+10,200,40);
searchBarLabel.setBounds(lm+10,tm+10+40+10,100,30);
searchTextField.setBounds(lm+10+60,tm+10+40+10,330,30);
searchErrorLabel.setBounds(lm+10+60+330+10-70,tm+10+40+10-20,100,20);
searchTextFieldCancelButton.setBounds(lm+10+60+330+10,tm+10+40+10,30,30);
scrollPane.setBounds(lm+10,tm+10+40+10+35,470,300);
coursePanel.setBounds(lm+10,tm+10+40+10+35+300+10,470,150);

container.add(titleLabel);
container.add(searchBarLabel);
container.add(searchTextField);
container.add(searchErrorLabel);
container.add(searchTextFieldCancelButton);
container.add(scrollPane);
container.add(searchTextFieldCancelButton);
container.add(coursePanel);

Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
int width = 500;
int height = 600;
setSize(width,height);
setLocation(d.width/2 - width/2,d.height/2 - height/2);
}
private void addListeners()
{
}
//inner class
class CoursePanel extends JPanel //private class and constructor? >No modifier
{
CoursePanel()
{
setBorder(BorderFactory.createLineBorder(new Color(170,170,170)));
}
}


} //end of class