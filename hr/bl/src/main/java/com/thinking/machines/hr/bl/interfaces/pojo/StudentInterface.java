package com.thinking.machines.hr.bl.interfaces.pojo;
import java.math.*; //for BigDecimal
import java.util.*; //for Date class
import com.thinking.machines.enums.*;
public interface StudentInterface extends java.io.Serializable,Comparable<StudentInterface>
{
public void setRollNo(String rollNo);
public String getRollNo();
public void setName(String Name);
public String getName();
public void setCourseCode(int courseCode);
public int getCourseCode();
public void setDateOfBirth(Date dateOfBirth);
public Date getDateOfBirth();
public void setGender(GENDER gender); //GENDER is an enumerated class
public char getGender();
public void setIsIndian(boolean isIndian);
public boolean getIsIndian();
public void setFees(BigDecimal fees);
public BigDecimal getFees();
public void setEnrollmentNumber(String enrollmentNumber);
public String getEnrollmentNumber();
public void setAadharCardNumber(String aadharCardNumber);
public String getAadharCardNumber();
}