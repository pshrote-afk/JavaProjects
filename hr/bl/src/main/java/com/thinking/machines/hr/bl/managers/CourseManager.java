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
Map<Integer,CourseInterface> codeWiseCoursesMap = new TreeMap<>();
titleWiseCoursesMap = new TreeMap<>();
coursesSet = new TreeSet<>();
try
{
CourseDAOInterface courseDAO = new CourseDAO();
Set<CourseDTOInterface> courses = courseDAO.getAll();
CourseInterface dsCourse;
for(CourseDTOInterface courseDTO:courses)
{
dsCourse = new Course();
dsCourse.setCode(courseDTO.getCode());
dsCourse.setTitle(courseDTO.getTitle());
codeWiseCoursesMap.put(courseDTO.getCode(),dsCourse);
titleWiseCoursesMap.put(courseDTO.getTitle(),dsCourse);
coursesSet.add(dsCourse);
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
blException.setGenericException("not yet implemented");
throw blException;
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
