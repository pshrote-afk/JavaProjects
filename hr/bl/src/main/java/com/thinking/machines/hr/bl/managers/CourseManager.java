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
private static CourseManager courseManager = null; 
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
this.codeWiseCoursesMap = new HashMap<>();
this.titleWiseCoursesMap = new HashMap<>();
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
//lect 62
CourseInterface getDSCourseByCode(int code)
{
CourseInterface course;
course = this.codeWiseCoursesMap.get(code);
return course;
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
blException.addException("title","Course: " + title + " already exists");
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
course.setCode(code); //setting code in parameter variable to ensure that from where it was called we can display what code has been generated
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
if(course==null)
{
blException.setGenericException("Course required");
throw blException;
}
int code = course.getCode();
String title = course.getTitle();
if(code<=0) blException.addException("code","Invalid code: "+code);
if(code>0)
{
if(this.codeWiseCoursesMap.containsKey(code)==false)
{
blException.addException("code","Invalid code: "+code);
throw blException;
}
}
if(title==null) 
{
blException.addException("title","title required");
title="";
}
else
{
title=title.trim();
if(title.length()==0) 
{
blException.addException("title","title required");
}
}
if(title.length()>0)
{
CourseInterface tmpCourse;
tmpCourse = titleWiseCoursesMap.get(title.toUpperCase());
if(tmpCourse!=null && tmpCourse.getCode()!=code)
{
blException.addException("title","Course: "+title+" already exists");
}
}
if(blException.hasExceptions())
{
throw blException;
}
try
{
CourseInterface dsCourse = codeWiseCoursesMap.get(code);
CourseDTOInterface courseDTO = new CourseDTO();
courseDTO.setCode(code);
courseDTO.setTitle(title);
new CourseDAO().update(courseDTO);
//control reaches here means successfully updated. hence, remove old DS entries
codeWiseCoursesMap.remove(code);
titleWiseCoursesMap.remove(dsCourse.getTitle().toUpperCase());
coursesSet.remove(dsCourse);
//update DS object
dsCourse.setTitle(title);
//update DS
codeWiseCoursesMap.put(code,dsCourse);
titleWiseCoursesMap.put(title.toUpperCase(),dsCourse);
coursesSet.add(dsCourse);
}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
}
}
public void removeCourse(int code) throws BLException
{
BLException blException = new BLException();
if(code<=0) 
{
blException.addException("code","Invalid code: "+code);
}
if(code>0)
{
if(this.codeWiseCoursesMap.containsKey(code)==false)
{
blException.addException("code","Invalid code: "+code);
throw blException;
}
}
if(blException.hasExceptions())
{
throw blException;
}
CourseInterface tmpCourse = codeWiseCoursesMap.get(code);
try
{
new CourseDAO().delete(code);
//control reaches here means successfully deleted
this.codeWiseCoursesMap.remove(code);
this.titleWiseCoursesMap.remove(tmpCourse.getTitle().toUpperCase());	//replace cant be used here
this.coursesSet.remove(tmpCourse);
}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
}
}
public CourseInterface getCourseByCode(int code) throws BLException
{
CourseInterface course;
course = codeWiseCoursesMap.get(code);
if(course==null)
{
BLException blException = new BLException();
blException.addException("code","Invalid code: "+code);
throw blException;
}
return course;
}
public CourseInterface getCourseByTitle(String title) throws BLException
{
CourseInterface course;
course = titleWiseCoursesMap.get(title.toUpperCase());
if(course==null)
{
BLException blException = new BLException();
blException.addException("title","Invalid title: "+title);
throw blException;
}
return course;
}
public int getCourseCount()
{
return this.coursesSet.size();
}
public boolean courseCodeExists(int code)
{
return this.codeWiseCoursesMap.containsKey(code);
}
public boolean courseTitleExists(String title)
{
return this.titleWiseCoursesMap.containsKey(title.toUpperCase());
}
public Set<CourseInterface> getCourses()
{
//do not return original data structure
//return clone of our data structure
Set<CourseInterface> courses;
courses = new TreeSet<>();

this.coursesSet.forEach((CourseInterface course)->{
CourseInterface tmpCourse = new Course();
tmpCourse.setCode(course.getCode());
tmpCourse.setTitle(course.getTitle());
courses.add(course);
});
return courses;
}
}//end of class
