package com.thinking.machines.hr.dl.dto;
import com.thinking.machines.hr.dl.interfaces.dto.*;
public class CourseDTO implements CourseDTOInterface
{
private int code;
private String title;
public void setCode(int code)
{
this.code=code;
}
public int getCode()
{
return this.code;
}
public void setTitle(java.lang.String title)
{
this.title=title;
}
public java.lang.String getTitle()
{
return this.title;
}
public CourseDTO()
{
this.code=0;
this.title=null;
}
//3 methods
public boolean equals(Object other)
{
if(!(other instanceof CourseDTOInterface)) return false;
CourseDTOInterface courseDTO;
courseDTO = (CourseDTOInterface)other;
return this.code==courseDTO.getCode();
}

public int compareTo(CourseDTOInterface courseDTO)
{
return this.code - courseDTO.getCode();
}

public int hashCode()
{
return code;
}

}