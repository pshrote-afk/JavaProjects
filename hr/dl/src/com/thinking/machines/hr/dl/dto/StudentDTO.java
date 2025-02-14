package com.thinking.machines.hr.dl.dto;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.enums.*;
import math.*;
public class StudentDTO implements StudentDTOInterface
{
private int rollNo;
private String name;
private Date dateOfBirth;
private GENDER gender;
private boolean isIndian;
private BigDecimal fees;
private String enrollmentNumber;
public void setRollNo(int rollNo)
{
this.rollNo=rollNo;
}
public int getRollNo()
{
return this.rollNo;
}
public void setName(java.lang.String name)
{
this.name=name;
}
public java.lang.String getName()
{
return this.name;
}
public void setDateOfBirth(java.util.Date dateOfBirth)
{
this.dateOfBirth=dateOfBirth;
}
public java.util.Date getDateOfBirth()
{
return this.dateOfBirth;
}
public void setGender(com.thinking.machines.enums.GENDER gender)
{
this.gender=gender;
}
public com.thinking.machines.enums.GENDER getGender()
{
return this.gender;
}
public void setIsIndian(boolean isIndian)
{
this.isIndian=isIndian;
}
public boolean getIsIndian()
{
return this.isIndian;
}
public void setFees(java.math.BigDecimal fees)
{
this.fees=fees;
}
public java.math.BigDecimal getFees()
{
return this.fees;
}
public void setEnrollmentNumber(java.lang.String enrollmentNumber)
{
this.enrollmentNumber=enrollmentNumber;
}
public java.lang.String getEnrollmentNumber()
{
return this.enrollmentNumber;
}
public StudentDTO()
{
this.rollNo=0;
this.name=null;
this.dateOfBirth=null;
this.gender=null;
this.isIndian=false;
this.fees=null;
this.enrollmentNumber=null;
}

public boolean equals(Object other)
{
if(!(other instanceof StudentDTOInterface)) return 0;
StudentDTOInterface studentDTO;
student DTO = (StudentDTO)other;
return this.code==studentDTO.getCode();
}
public int compareTo(StudentDTOInterface studentDTO)
{
return this.code - studentDTO.getCode();
}
public int hashCode()
{
return code;
}
//3 methods
}