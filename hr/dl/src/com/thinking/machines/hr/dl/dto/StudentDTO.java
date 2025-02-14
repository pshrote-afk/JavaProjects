package com.thinking.machines.hr.dl.dto;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.enums.*;
import java.math.*; //for BigDecimal
import java.util.*; //for Date class
public class StudentDTO implements StudentDTOInterface
{
private int rollNo;
private String name;
private Date dateOfBirth;
private char gender;
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
if(gender==GENDER.MALE) this.gender = 'M';
else this.gender = 'F';
}
public char getGender()
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
this.gender=' ';
this.isIndian=false;
this.fees=null;
this.enrollmentNumber=null;
}

public boolean equals(Object other)
{
if(!(other instanceof StudentDTOInterface)) return false;
StudentDTOInterface studentDTO;
studentDTO = (StudentDTO)other;
return this.rollNo==studentDTO.getRollNo();
}
public int compareTo(StudentDTOInterface studentDTO)
{
return this.rollNo - studentDTO.getRollNo();
}
public int hashCode()
{
return rollNo;
}
//3 methods
}

