package com.thinking.machines.hr.bl.interfaces.managers;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;
import java.util.*;
public interface StudentManagerInterface
{ //rewrite methods as per StudentDAOInterface methods
public void addStudent(StudentInterface student) throws BLException;
public void updateStudent(StudentInterface student) throws BLException;
public void removeStudent(int code) throws BLException;

public Set<StudentInterface> getStudentByCourseCode() throws BLException;
public Set<StudentInterface> getStudents() throws BLException;

public StudentInterface getStudentByRollNo(String rollNo) throws BLException;
public StudentInterface getStudentByEnrollmentNumber(String enrollmentNumber) throws BLException;
public StudentInterface getStudentByAadharCardNumber(String aadharCardNumber) throws BLException;

public boolean isCourseAllottedToStudent(int code) throws BLException;
public boolean studentRollNoExists(String rollNo) throws BLException;
public boolean studentEnrollmentNumberExists(String enrollmentNumber) throws BLException;
public boolean studentAadharCardNumberExists(String aadharCardNumber) throws BLException;

public int getStudentCount() throws BLException;
public int getCountByCourseForStudent(int code) throws BLException;
}