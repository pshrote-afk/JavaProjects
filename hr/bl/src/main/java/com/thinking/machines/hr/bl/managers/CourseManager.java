package com.thinking.machines.hr.bl.managers;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;
import java.util.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.exceptions.*;
public class CourseManager implements CourseManagerInterface
{
private Map<Integer,CourseInterface> codeWiseCoursesMap;
private Map<String,CourseInterface> titleWiseCoursesMap;
private Set<CourseInterface> coursesSet;
private static CourseManagerInterface courseManager = null; 
private CourseManager() throws BLException
{
populateDataStructures();
}
public static CourseManagerInterface getCourseManager() throws BLException
{
if(courseManager==null) courseManager = new CourseManager();
return courseManager;
}
private void populateDataStructures() throws BLException
{
this.codeWiseCoursesMap = new TreeMap<>();
this.titleWiseCoursesMap = new TreeMap<>();
this.coursesSet = new TreeSet<>();
try
{
CourseDAOInterface courseDAO = new CourseDAO();
Set<CourseDTOInterface> dlCourses = courseDAO.getAll();
CourseInterface course;
for(CourseDTOInterface courseDTO:dlCourses)
{
course = new Course();
course.setCode(courseDTO.getCode());
course.setTitle(courseDTO.getTitle());
this.codeWiseCoursesMap.put(courseDTO.getCode(),course);
this.titleWiseCoursesMap.put(courseDTO.getTitle().toUpperCase(),course);
this.coursesSet.add(course);
}
}catch(DAOException daoException)
{
BLException blException = new BLException();
blException.setGenericException(daoException.getMessage());
throw blException;
}
}

public void addCourse(CourseInterface course) throws BLException
{
BLException blException = new BLException();
//validate
int code = course.getCode();
if(code!=0) blException.addException("code","code should be zero");
String title = course.getTitle();
if(title==null) 
{
blException.addException("title","title required");
title="";
}
else
{
title=title.trim();
if(title.length()==0) blException.addException("title","title required");
}
if(title.length()>0)
{
if(titleWiseCoursesMap.containsKey(title.toUpperCase()))
{
blException.addException("title","Designation: " + title + " already exists");
}
}
if(blException.hasExceptions())
{
throw blException;
}
try
{
CourseDAOInterface courseDAO = new CourseDAO();
CourseDTOInterface courseDTO = new CourseDTO();
courseDTO.setTitle(title);
courseDAO.add(courseDTO);
//control reaches here means successfully added in data layer
code = courseDTO.getCode();
course.setCode(code);
//we make a new object right? Yes, sir.
CourseInterface dsCourse = new Course();
dsCourse.setCode(code);
dsCourse.setTitle(title);
this.codeWiseCoursesMap.put(code,dsCourse);
this.titleWiseCoursesMap.put(title.toUpperCase(),dsCourse);
this.coursesSet.add(dsCourse);
}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
throw blException;
}
}
public void updateCourse(CourseInterface course) throws BLException
{
BLException blException = new BLException();
blException.setGenericException("not yet implemented");
throw blException;
}
public void removeCourse(int code) throws BLException
{
BLException blException = new BLException();
blException.setGenericException("not yet implemented");
throw blException;
}
public CourseInterface getCourseByCode(int code) throws BLException
{
BLException blException = new BLException();
blException.setGenericException("not yet implemented");
throw blException;
}
public CourseInterface getCourseByTitle(String title) throws BLException
{
BLException blException = new BLException();
blException.setGenericException("not yet implemented");
throw blException;
}
public int getCourseCount() throws BLException
{
BLException blException = new BLException();
blException.setGenericException("not yet implemented");
throw blException;
}
public boolean courseCodeExists(int code) throws BLException
{
BLException blException = new BLException();
blException.setGenericException("not yet implemented");
throw blException;
}
public boolean courseTitleExists(String title) throws BLException
{
BLException blException = new BLException();
blException.setGenericException("not yet implemented");
throw blException;
}
public Set<CourseInterface> getCourses() throws BLException
{
BLException blException = new BLException();
blException.setGenericException("not yet implemented");
throw blException;
}
}
