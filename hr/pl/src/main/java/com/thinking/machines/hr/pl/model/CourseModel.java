package com.thinking.machines.hr.pl.model;
import javax.swing.table.*; //for AbstractTableModel
import java.util.*; //for List
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
        columnTitle[0] = "Sr. No.";
        columnTitle[1] = "Course";
        try 
        {
         courseManager = CourseManager.getCourseManager();
        }catch(BLException blException) 
        {
            // ????? do what
        }
        Set<CourseInterface> blCourses = courseManager.getCourses();   
	plCoursesList = new LinkedList<>();        
	for(CourseInterface course:blCourses) 
        {
            plCoursesList.add(course); //getCourses() already returns duplicates. No need to duplicate and add in PL-level DS therefore
        }
	//now that PL-level DS is populated, let us sort it. //Doubt: how will it be sorted when we add a new entry?
	
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
if(columnIndex==0) return Integer.class;
if(columnIndex==1) return String.class;
return null;
}
public boolean isCellEditable(int rowIndex,int columnIndex)
{
return false;
}
public Object getValueAt(int rowIndex,int columnIndex)
{
if(columnIndex==0) return rowIndex+1;
else return plCoursesList.get(rowIndex).getTitle();
}
// Application Specific Methods
public void add(CourseInterface course) throws BLException
{
courseManager.addCourse(course);
this.plCoursesList.add(course);
Collections.sort(this.plCoursesList,new Comparator<CourseInterface>(){
public int compare(CourseInterface left,CourseInterface right)
{
return left.getTitle().toUpperCase().compareTo(right.getTitle().toUpperCase()); //compareTo() method of String
}
});
fireTableDataChanged();
}
public void update(CourseInterface course) throws BLException
{
courseManager.updateCourse(course);
this.plCoursesList.remove(indexOfCourse(course)); //removing on the basis of course.getCode(). hence, yes, we can use the new updated object to help remove. Cause w/ current BL,DL functionalities we cannot change course code. We can only update title
this.plCoursesList.add(course); 
Collections.sort(this.plCoursesList,new Comparator<CourseInterface>(){
public int compare(CourseInterface left,CourseInterface right)
{
return left.getTitle().toUpperCase().compareTo(right.getTitle().toUpperCase());
}
});
fireTableDataChanged();
}
public void remove(int code) throws BLException
{
courseManager.removeCourse(code);
Iterator<CourseInterface> iterator = this.plCoursesList.iterator();
int index = 0;
while(iterator.hasNext())
{
if(iterator.next().getCode()==code) break;
index++;
}
if(index==this.plCoursesList.size())
{
BLException blException = new BLException();
blException.setGenericException("Invalid course code: "+code);
throw blException;
}
this.plCoursesList.remove(index); //removing on the basis of course.getCode(). hence, yes, we can use the new updated object to help remove. Cause w/ current BL,DL functionalities we cannot change course code. We can only update title
fireTableDataChanged();
}
public int indexOfCourse(CourseInterface course) throws BLException
{
Iterator<CourseInterface> iterator = this.plCoursesList.iterator();
int index = 0;
CourseInterface tmpCourse;
while(iterator.hasNext())
{
tmpCourse = iterator.next();
if(course.equals(tmpCourse))
{
return index;
}
index++;
}
BLException blException = new BLException();
blException.setGenericException("Invalid course: "+course.getTitle());
throw blException;
}
public int indexOfTitle(String title,boolean partialLeftSearch) throws BLException
{
Iterator<CourseInterface> iterator = this.plCoursesList.iterator();
int index = 0;
CourseInterface tmpCourse;
while(iterator.hasNext())
{
tmpCourse = iterator.next();
if(partialLeftSearch)
{
if(tmpCourse.getTitle().toUpperCase().startsWith(title.toUpperCase()))
{
return index;
}
}
else
{
if(tmpCourse.getTitle().equalsIgnoreCase(title)) 
{
return index;
}
}
index++;
}
BLException blException = new BLException();
blException.setGenericException("Invalid title: "+title);
throw blException;
}

} //end of class

