package com.thinking.machines.hr.pl.model;
import java.util.*; //for List
import javax.swing.*;
import javax.swing.table.*; //for AbstractTableModel
//BL
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;
public class CourseModel extends AbstractTableModel
{
private String columnTitle[];
private List<CourseInterface> plCoursesList;
private CourseManagerInterface courseManager;
public CourseModel()
{
populateDataStructure();
}
private void populateDataStructure() 
{
columnTitle = new String[2];
columnTitle[0] = "Sr.No.";
columnTitle[1] = "Course";
plCoursesList = new LinkedList<>();
try
{
courseManager = CourseManager.getCourseManager();
}catch(BLException blException)
{
//????? do what
}
Set<CourseInterface> courses = courseManager.getCourses();
Iterator<CourseInterface> iterator = courses.iterator();
CourseInterface tmpCourse;
while(iterator.hasNext())
{
tmpCourse = iterator.next();
plCoursesList.add(tmpCourse);
}
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
if(columnIndex==0) return columnTitle[0];
if(columnIndex==1) return columnTitle[1];
return null;
}
public Class getColumnClass(int columnIndex)
{
if(columnIndex==0) return Integer.class;
if(columnIndex==1) return String.class;
return null;
}
public Object getValueAt(int rowIndex,int columnIndex)
{
if(columnIndex==0) return rowIndex+1;
else return plCoursesList.get(rowIndex).getTitle();
}
public boolean isCellEditable(int rowIndex,int columnIndex)
{
return false;
}
//application specific methods
public void add(CourseInterface course) throws BLException
{
courseManager.addCourse(course);
this.plCoursesList.add(course);
Collections.sort(this.plCoursesList,new Comparator<CourseInterface>(){
public int compare(CourseInterface left,CourseInterface right)
{
return left.getTitle().toUpperCase().compareTo(right.getTitle().toUpperCase());
}
});
}
public void update(CourseInterface course) throws BLException
{
courseManager.updateCourse(course);
plCoursesList.remove(indexOfCourse(course));
plCoursesList.add(course);
Collections.sort(this.plCoursesList,new Comparator<CourseInterface>(){
public int compare(CourseInterface left,CourseInterface right)
{
return left.getTitle().toUpperCase().compareTo(right.getTitle().toUpperCase());
}
});
}
public void remove(int code) throws BLException
{
courseManager.removeCourse(code);
Iterator<CourseInterface> iterator = this.plCoursesList.iterator();
int index = 0;
CourseInterface tmpCourse;
while(iterator.hasNext())
{
tmpCourse = iterator.next();
if(tmpCourse.getCode() == code)
{
this.plCoursesList.remove(index);
}
index++;
}
}
public int indexOfCourse(CourseInterface course) throws BLException
{
Iterator<CourseInterface> iterator = this.plCoursesList.iterator();
int index = 0;
CourseInterface tmpCourse;
while(iterator.hasNext())
{
tmpCourse = iterator.next();
if(tmpCourse.equals(course)) //deep comparison. CourseInterface implements Comparable which means we have written "equals()" method which compares on the basis of "course code" in its implementation
{
break;
}
index++;
}
if(index == plCoursesList.size()) //control will never reach here, cause if course doesnt exist then BL will indicate even before control enters this function
{
BLException blException = new BLException();
blException.setGenericException("Invalid course: "+course.getTitle());
throw blException;
}
return index;
}
public int indexOfTitle(String title,boolean leftPartialSearch) throws BLException
{
int index = 0;
CourseInterface tmpCourse;
Iterator<CourseInterface> iterator = plCoursesList.iterator();
while(iterator.hasNext())
{
tmpCourse = iterator.next();
if(leftPartialSearch)
{
if(tmpCourse.getTitle().toUpperCase().startsWith(title.toUpperCase())) //rethink //done done
{
return index;
}
}
else
{
if(title.equalsIgnoreCase(tmpCourse.getTitle()))
{
break;
}
}
index++;
}
if(index == plCoursesList.size())
{
BLException blException = new BLException();
blException.setGenericException("Invalid title: "+title);
throw blException;
}
return index;
}

} //end of class