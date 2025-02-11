package com.thinking.machines.hr.dl.dto;
import com.thinking.machines.hr.dl.interfaces.dto.*;
public class DesignationDTO implements DesignationDTOInterface
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
public DesignationDTO()
{
this.code=0;
this.title=null;
}

//3 methods: equals, compareTo, hashCode
public boolean equals(Object other)
{
if(!(other instanceof DesignationDTOInterface)) return false;
DesignationDTOInterface designationDTO;
designationDTO=(DesignationDTOInterface)other;
return this.code==designationDTO.getCode();
}

public int compareTo(DesignationDTOInterface designationDTO)
{
return this.code-designationDTO.getCode();
}

public int hashCode()
{
return code;
}

}




