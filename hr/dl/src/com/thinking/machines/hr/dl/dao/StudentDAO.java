package com.thinking.machines.hr.dl.dao;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import java.util.*; //for Set collection, and Date class
import java.math.*; //for BigDecimal class
import java.text.*; //for SimpleDateFormat class
import java.io.*; //for RandomAccessFile class
public class StudentDAO implements StudentDAOInterface
{
private static final String FILE_NAME="student.data";
private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy"); //initialized in a static initializer block

public void add(StudentDTOInterface studentDTO) throws DAOException
{
/*
private String rollNo;//to be generated
private String name;
private int courseCode;
private Date dateOfBirth;
private char gender;
private boolean isIndian;
private BigDecimal fees;
private String enrollmentNumber;
private String aadharCardNumber;
*/
//extract data from incoming DTO object
String name = studentDTO.getName();
if(name==null) throw new DAOException("Name is null");
name=name.trim();
if(name.length()==0) throw new DAOException("Length of name cannot be zero"); 
int courseCode = studentDTO.getCourseCode();
if(!(new CourseDAO().codeExists(courseCode))) throw new DAOException("Invalid course code: "+courseCode);
Date dateOfBirth = studentDTO.getDateOfBirth();
if(dateOfBirth==null) throw new DAOException("Date of birth is null");
char gender = studentDTO.getGender();
if(gender==' ') throw new DAOException("Invalid gender. Only M/F allowed");
boolean isIndian = studentDTO.getIsIndian();
BigDecimal fees = studentDTO.getFees();
if(fees==null) throw new DAOException("Fess is null");
String enrollmentNumber = studentDTO.getEnrollmentNumber();
if(enrollmentNumber==null) throw new DAOException("Enrollment number is null");
String aadharCardNumber = studentDTO.getAadharCardNumber();
if(aadharCardNumber==null) throw new DAOException("Aadhar card number is null");
//validated all fields. Now add to file. Then update header.
try
{
File file = new File(FILE_NAME);
RandomAccessFile randomAccessFile;
randomAccessFile = new RandomAccessFile(file,"rw");
int lastGeneratedCode=10000000;
int recordCount=0;
if(randomAccessFile.length()==0) //means newly opened file. Therefore initialize header
{
randomAccessFile.writeBytes(String.format("%-10d",lastGeneratedCode) + "\n");
randomAccessFile.writeBytes(String.format("%-10d",recordCount) + "\n");
}
else //else file already exists, so read header
{
lastGeneratedCode = Integer.parseInt(randomAccessFile.readLine().trim());
recordCount = Integer.parseInt(randomAccessFile.readLine().trim());
}
//crossed header. Now ensure enrollment number and aadhar card number is unique - else throw exception
int x;
String fRollNo;
String fEnrollmentNumber;
String fAadharCardNumber;
boolean enrollmentNumberExists=false;
boolean aadharCardNumberExists=false;
String enrollmentNumberAgainstRollNo="";
String aadharCardNumberAgainstRollNo="";
while(randomAccessFile.getFilePointer() < randomAccessFile.length())
{
fRollNo = randomAccessFile.readLine();
for(x=2;x<=7;x++) randomAccessFile.readLine();
fEnrollmentNumber = randomAccessFile.readLine();
fAadharCardNumber = randomAccessFile.readLine();
if(enrollmentNumberExists==false && enrollmentNumber.equalsIgnoreCase(fEnrollmentNumber)) 
{
enrollmentNumberExists = true;
enrollmentNumberAgainstRollNo = fRollNo;
}
if(aadharCardNumberExists==false && aadharCardNumber.equalsIgnoreCase(fAadharCardNumber)) 
{
aadharCardNumberExists = true;
aadharCardNumberAgainstRollNo = fRollNo;
}
if(enrollmentNumberExists && aadharCardNumberExists) break;
}
if(enrollmentNumberExists && aadharCardNumberExists)
{
throw new DAOException("Enrollment number ("+enrollmentNumber+") and aadhar card number ("+aadharCardNumber+") already exist against roll no ("+enrollmentNumberAgainstRollNo+") and ("+aadharCardNumberAgainstRollNo+") respectively");
}
if(enrollmentNumberExists)
{
throw new DAOException("Enrollment number ("+enrollmentNumber+") already exists against roll no ("+enrollmentNumberAgainstRollNo+")");
}
if(aadharCardNumberExists)
{
throw new DAOException("Aadhar card number ("+aadharCardNumber+") already exists against roll no ("+aadharCardNumberAgainstRollNo+")");
}
//control reached here means enrollment and aadhar are unique. thus, write in file.
lastGeneratedCode++;
recordCount++;
String newGeneratedRollNo = "R" + lastGeneratedCode;
studentDTO.setRollNo(newGeneratedRollNo);
randomAccessFile.writeBytes(studentDTO.getRollNo() + "\n");
randomAccessFile.writeBytes(name + "\n");
randomAccessFile.writeBytes(courseCode + "\n"); 
randomAccessFile.writeBytes(simpleDateFormat.format(dateOfBirth) + "\n");
randomAccessFile.writeBytes(gender + "\n");
randomAccessFile.writeBytes(isIndian + "\n");
randomAccessFile.writeBytes(fees.toPlainString() + "\n");
randomAccessFile.writeBytes(enrollmentNumber + "\n");
randomAccessFile.writeBytes(aadharCardNumber + "\n");
//update header
randomAccessFile.seek(0);
randomAccessFile.writeBytes(String.format("%-10d",lastGeneratedCode) + "\n");
randomAccessFile.writeBytes(String.format("%-10d",recordCount) + "\n");
randomAccessFile.close();
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public void update(StudentDTOInterface studentDTO) throws DAOException
{
throw new DAOException("Not yet implemented");
}
public void delete(String rollNo) throws DAOException
{
throw new DAOException("Not yet implemented");
}
public Set<StudentDTOInterface> getByCourseCode(int code) throws DAOException
{
throw new DAOException("Not yet implemented");
}
public Set<StudentDTOInterface> getAll() throws DAOException
{
throw new DAOException("Not yet implemented");
}
public boolean isCourseCodeAllotted(int courseCode) throws DAOException
{
throw new DAOException("Not yet implemented");
}
public StudentDTOInterface getByRollNo(String rollNo) throws DAOException
{
throw new DAOException("Not yet implemented");
}
public StudentDTOInterface getByEnrollmentNumber(String enrollmentNumber) throws DAOException
{
throw new DAOException("Not yet implemented");
}
public StudentDTOInterface getByAadharCardNumber(String aadharCardNumber) throws DAOException
{
throw new DAOException("Not yet implemented");
}
public boolean rollNoExists(String rollNo) throws DAOException
{
throw new DAOException("Not yet implemented");
}
public boolean enrollmentNumberExists(String enrollmentNumber) throws DAOException
{
throw new DAOException("Not yet implemented");
}
public boolean aadharCardNumberExists(String aadharCardNumber) throws DAOException
{
throw new DAOException("Not yet implemented");
}
public int getCount() throws DAOException
{
throw new DAOException("Not yet implemented");
}
public int getCountByCourse(int code) throws DAOException
{
throw new DAOException("Not yet implemented");
}
}