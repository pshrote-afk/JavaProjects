package com.thinking.machines.hr.bl.interfaces.managers;

import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.pojo.*;
import java.util.*;

public interface CourseManagerInterface {
  public void addCourse(CourseInterface course) throws BLException;

  public void updateCourse(CourseInterface course) throws BLException;

  public void removeCourse(int code) throws BLException;

  public CourseInterface getCourseByCode(int code) throws BLException;

  public CourseInterface getCourseByTitle(String title) throws BLException;

  public int getCourseCount();

  public boolean courseCodeExists(int code);

  public boolean courseTitleExists(String title);

  public Set<CourseInterface> getCourses();
}
