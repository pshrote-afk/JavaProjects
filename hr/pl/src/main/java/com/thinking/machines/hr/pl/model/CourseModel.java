package com.thinking.machines.hr.pl.model;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;
//swing
import javax.swing.*;
import java.awt.*;
import javax.swing.table.*;
import java.util.*; //for Set and List
public class CourseModel extends AbstractTableModel
{
private String columnTitle[];
private CourseManagerInterface courseManager;
private java.util.List<CourseInterface> plCoursesList;
public CourseModel()
{
this.populateDataStructure();
}

private void populateDataStructure()
{
columnTitle = new String[2];
columnTitle[0] = "Sr. No.";
columnTitle[1] = "Course";
Set<CourseInterface> blCourses;
try
{
courseManager = CourseManager.getCourseManager();
}catch(BLException blException)
{
//????? what to do 
}
blCourses = courseManager.getCourses(); //these are already duplicated and returned. No need to duplicate them again to add in PL-level data structure
plCoursesList = new LinkedList<>();
for(CourseInterface tmpCourse:blCourses)
{
plCoursesList.add(tmpCourse);
}
//calling a function to sort after entire DS has been loaded
Collections.sort(this.plCoursesList,new Comparator<CourseInterface>(){
public int compare(CourseInterface left,CourseInterface right)
{
return left.getTitle().toUpperCase().compareTo(right.getTitle().toUpperCase());
}
}); 
}
public int getRowCount()
{
return plCoursesList.size();
}
public int getColumnCount()
{
return columnTitle.length;
}
public String getColumnName(int columnIndex)
{
return columnTitle[columnIndex];
}
public Class getColumnClass(int columnIndex)
{
if(columnIndex==0) return Integer.class; //special treatment. As good as writing: Class.forName("java.lang.Integer");
if(columnIndex==1) return String.class;
return null;
}
public boolean isCellEditable(int rowIndex,int columnIndex)
{
return false;
}
public Object getValueAt(int rowIndex,int columnIndex) //imp
{
if(columnIndex==0) return rowIndex+1;
else return plCoursesList.get(rowIndex).getTitle(); //LinkedList can be given index. (which will be considered index of node). .getTitle() very important
}


}



