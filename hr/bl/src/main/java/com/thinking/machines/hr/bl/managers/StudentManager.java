package com.thinking.machines.hr.bl.managers;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;
import java.util.*;
public class StudentManager implements StudentManagerInterface
{ 
public void addStudent(StudentInterface student) throws BLException
{
BLException blException = new BLException();
blException.setGenericException("not yet implemented");
throw blException;
}
public void updateStudent(StudentInterface student) throws BLException
{
BLException blException = new BLException();
blException.setGenericException("not yet implemented");
throw blException;
}
public void removeStudent(int code) throws BLException
{
BLException blException = new BLException();
blException.setGenericException("not yet implemented");
throw blException;
}

public Set<StudentInterface> getStudentByCourseCode() throws BLException
{
BLException blException = new BLException();
blException.setGenericException("not yet implemented");
throw blException;
}
public Set<StudentInterface> getStudents() throws BLException
{
BLException blException = new BLException();
blException.setGenericException("not yet implemented");
throw blException;
}

public StudentInterface getStudentByRollNo(String rollNo) throws BLException
{
BLException blException = new BLException();
blException.setGenericException("not yet implemented");
throw blException;
}
public StudentInterface getStudentByEnrollmentNumber(String enrollmentNumber) throws BLException
{
BLException blException = new BLException();
blException.setGenericException("not yet implemented");
throw blException;
}
public StudentInterface getStudentByAadharCardNumber(String aadharCardNumber) throws BLException
{
BLException blException = new BLException();
blException.setGenericException("not yet implemented");
throw blException;
}

public boolean isCourseAllottedToStudent(int code) throws BLException
{
BLException blException = new BLException();
blException.setGenericException("not yet implemented");
throw blException;
}
public boolean studentRollNoExists(String rollNo) throws BLException
{
BLException blException = new BLException();
blException.setGenericException("not yet implemented");
throw blException;
}
public boolean studentEnrollmentNumberExists(String enrollmentNumber) throws BLException
{
BLException blException = new BLException();
blException.setGenericException("not yet implemented");
throw blException;
}
public boolean studentAadharCardNumberExists(String aadharCardNumber) throws BLException
{
BLException blException = new BLException();
blException.setGenericException("not yet implemented");
throw blException;
}

public int getStudentCount() throws BLException
{
BLException blException = new BLException();
blException.setGenericException("not yet implemented");
throw blException;
}
public int getCountByCourseForStudent(int code) throws BLException
{
BLException blException = new BLException();
blException.setGenericException("not yet implemented");
throw blException;
}
}