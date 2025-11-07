package com.thinking.machines.hr.bl.pojo;

import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;

public class Course implements CourseInterface {
  private int code;
  private String title;

  public void setCode(int code) {
    this.code = code;
  }

  public int getCode() {
    return this.code;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getTitle() {
    return this.title;
  }

  public int hashCode() {
    return this.code;
  }

  public boolean equals(Object other) {
    if (!(other instanceof CourseInterface)) return false;
    CourseInterface course = (CourseInterface) other;
    return this.code == course.getCode();
  }

  public int compareTo(CourseInterface other) {
    return this.code - other.getCode();
  }
}
