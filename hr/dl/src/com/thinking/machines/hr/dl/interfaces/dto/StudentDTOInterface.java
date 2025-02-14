package com.thinking.machines.hr.dl.interfaces.dto;
import java.util.*; //for Date class
import java.math.*; //for BigDecimal class
import com.thinking.machines.enums.*;
public interface StudentDTOInterface extends Comparable<StudentDTOInterface>,java.io.Serializable
{
public void setRollNo(String rollNo);
public String getRollNo();
public void setName(String name);
public String getName();
public void setDateOfBirth(Date dateOfBirth);
public Date getDateOfBirth();
public void setGender(GENDER gender);
public char getGender();
public void setIsIndian(boolean isIndian);
public boolean getIsIndian();
public void setFees(BigDecimal fees);
public BigDecimal getFees();
public void setEnrollmentNumber(String enrollmenNumber);
public String getEnrollmentNumber();
}
/*
private String rollNo;
private String name;
private Date dateOfBirth;
private GENDER gender;
private boolean isIndian;
private BigDecimal fees;
private String enrollmentNumber;
*/