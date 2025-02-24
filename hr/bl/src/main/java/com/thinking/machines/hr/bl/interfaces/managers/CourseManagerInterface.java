package com.thinking.machines.hr.bl.interfaces.managers;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;
import java.util.*;
public interface CourseManagerInterface
{
public void addCourse(CourseInterface course) throws BLException;
public void updateCourse(CourseInterface course) throws BLException;
public void removeCourse(int code) throws BLException;

public CourseInterface getCourseByCode(int code) throws BLException;
public CourseInterface getCourseByTitle(String title) throws BLException;
public int getCourseCount() throws BLException;

public boolean courseCodeExists(int code) throws BLException;
public boolean courseTitleExists(String title) throws BLException;
public Set<CourseInterface> getCourses() throws BLException;
}