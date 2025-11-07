package com.thinking.machines.hr.dl.dto;

import com.thinking.machines.enums.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import java.math.*; // for BigDecimal
import java.util.*; // for Date class

public class StudentDTO implements StudentDTOInterface {
  private String rollNo;
  private String name;
  private int courseCode;
  private Date dateOfBirth;
  private char gender;
  private boolean isIndian;
  private BigDecimal fees;
  private String enrollmentNumber;
  private String aadharCardNumber;

  public void setRollNo(String rollNo) {
    this.rollNo = rollNo;
  }

  public String getRollNo() {
    return this.rollNo;
  }

  public void setName(java.lang.String name) {
    this.name = name;
  }

  public java.lang.String getName() {
    return this.name;
  }

  public void setCourseCode(int courseCode) {
    this.courseCode = courseCode;
  }

  public int getCourseCode() {
    return this.courseCode;
  }

  public void setDateOfBirth(java.util.Date dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  public java.util.Date getDateOfBirth() {
    return this.dateOfBirth;
  }

  public void setGender(com.thinking.machines.enums.GENDER gender) {
    if (gender == GENDER.MALE) this.gender = 'M';
    else this.gender = 'F';
  }

  public char getGender() {
    return this.gender;
  }

  public void setIsIndian(boolean isIndian) {
    this.isIndian = isIndian;
  }

  public boolean getIsIndian() {
    return this.isIndian;
  }

  public void setFees(java.math.BigDecimal fees) {
    this.fees = fees;
  }

  public java.math.BigDecimal getFees() {
    return this.fees;
  }

  public void setEnrollmentNumber(java.lang.String enrollmentNumber) {
    this.enrollmentNumber = enrollmentNumber;
  }

  public java.lang.String getEnrollmentNumber() {
    return this.enrollmentNumber;
  }

  public void setAadharCardNumber(java.lang.String aadharCardNumber) {
    this.aadharCardNumber = aadharCardNumber;
  }

  public java.lang.String getAadharCardNumber() {
    return this.aadharCardNumber;
  }

  public StudentDTO() {
    this.rollNo = null;
    this.name = null;
    this.courseCode = 0;
    this.dateOfBirth = null;
    this.gender = ' ';
    this.isIndian = false;
    this.fees = null;
    this.enrollmentNumber = null;
    this.aadharCardNumber = null;
  }

  public boolean equals(Object other) {
    if (!(other instanceof StudentDTOInterface)) return false;
    StudentDTOInterface studentDTO;
    studentDTO = (StudentDTO) other;
    return this.rollNo.equalsIgnoreCase(studentDTO.getRollNo());
  }

  public int compareTo(StudentDTOInterface other) {
    return this.rollNo.compareToIgnoreCase(other.getRollNo());
  }

  public int hashCode() {
    return this.rollNo
        .toUpperCase()
        .hashCode(); // Not a recursive call. We're calling hashCode() method of String.
  }
  // 3 methods
}
