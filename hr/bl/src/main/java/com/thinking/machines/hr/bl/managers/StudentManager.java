package com.thinking.machines.hr.bl.managers;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;
import java.util.*; //for collection classes, and Date class
import java.math.*; //for BigDecimal class
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.enums.*;
public class StudentManager implements StudentManagerInterface
{
private Map<String,StudentInterface> rollNoWiseStudentsMap;
private Map<String,StudentInterface> enrollmentNumberWiseStudentsMap;
private Map<String,StudentInterface> aadharCardNumberWiseStudentsMap;
private Set<StudentInterface> studentsSet;
private static StudentManager studentManager = null;

private StudentManager() throws BLException
{
populateDataStructures();
}
public static StudentManagerInterface getStudentManager() throws BLException
{
if(studentManager==null) studentManager = new StudentManager();
return studentManager;
}
private void populateDataStructures() throws BLException
{
this.rollNoWiseStudentsMap = new HashMap<>();
this.enrollmentNumberWiseStudentsMap = new HashMap<>();
this.aadharCardNumberWiseStudentsMap = new HashMap<>();
this.studentsSet = new TreeSet<>();

try
{
Set<StudentDTOInterface> dlStudents;
dlStudents = new StudentDAO().getAll();

//temporary placeholders:
StudentInterface student;
CourseManagerInterface courseManager;
courseManager = CourseManager.getCourseManager();
CourseInterface course;
for(StudentDTOInterface dlStudent:dlStudents)
{
student = new Student();
student.setRollNo(dlStudent.getRollNo());
student.setName(dlStudent.getName());

course = courseManager.getCourseByCode(dlStudent.getCourseCode());
student.setCourse(course);

student.setDateOfBirth(dlStudent.getDateOfBirth());
student.setGender((dlStudent.getGender()=='M')?GENDER.MALE:GENDER.FEMALE); //if condition is true, first value will be assigned, else second value will be assigned 
student.setIsIndian(dlStudent.getIsIndian());
student.setFees(dlStudent.getFees()); 
student.setEnrollmentNumber(dlStudent.getEnrollmentNumber());
student.setAadharCardNumber(dlStudent.getAadharCardNumber());
//now add to DS
this.rollNoWiseStudentsMap.put(student.getRollNo().toUpperCase(),student); //no need for toUpperCase(), since roll no is actually numbers stored as a String
this.enrollmentNumberWiseStudentsMap.put(student.getEnrollmentNumber().toUpperCase(),student);
this.aadharCardNumberWiseStudentsMap.put(student.getAadharCardNumber().toUpperCase(),student);
this.studentsSet.add(student);
}
}catch(DAOException daoException)
{
BLException blException = new BLException();
blException.setGenericException(daoException.getMessage());
}
}
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

public void addStudent(StudentInterface student) throws BLException
{
BLException blException = new BLException();
String rollNo = student.getRollNo();
if(rollNo!=null) 
{
rollNo=rollNo.trim();
if(rollNo.length()>0)
{
blException.addException("rollNo","Roll no should be null, or should not be passed"); 
}
}
String name = student.getName();
if(name==null) 
{
blException.addException("name","Name required");
}
else
{
name=name.trim();
if(name.length()==0) //doubt: name validations are enough? Yes. they're enough
{
blException.addException("name","Name required");
}
}

CourseManagerInterface courseManager = CourseManager.getCourseManager(); //for checking if course code exists in Courses
CourseInterface course;
int courseCode=0;
course = student.getCourse();
if(course==null)
{
blException.addException("course","Course required");
}
else 
{
courseCode=course.getCode();
if(courseManager.courseCodeExists(courseCode)==false) //doubt: else block is correct? Yes, correct.
{
blException.addException("courseCode","Invalid course code: "+courseCode);
}
}
Date dateOfBirth = student.getDateOfBirth();
if(dateOfBirth==null) 
{
blException.addException("dateOfBirth","Date of birth required");
}
char gender = student.getGender();
if(!(gender=='M' || gender=='F'))
{
blException.addException("gender","Invalid gender: "+gender+". Only M/F allowed");
}
boolean isIndian = student.getIsIndian();
BigDecimal fees = student.getFees();
if(fees==null)
{
blException.addException("fees","Fees required");
}
else
{
if(fees.signum()==-1)
{
blException.addException("fees","Fees cannot be negative");
}
}
String enrollmentNumber = student.getEnrollmentNumber();
if(enrollmentNumber==null) 
{
blException.addException("enrollmentNumber","Enrollment number is null");
}
else
{
enrollmentNumber=enrollmentNumber.trim();
if(enrollmentNumber.length()==0) 
{
blException.addException("enrollmentNumber","Enrollment number required");
}
else if(enrollmentNumberWiseStudentsMap.containsKey(enrollmentNumber.toUpperCase())) //check if enrollment and aadhar are unique
{
blException.addException("enrollmentNumber","Enrollment number "+enrollmentNumber+" already exists");
}
}
String aadharCardNumber = student.getAadharCardNumber();
if(aadharCardNumber==null) 
{
blException.addException("aadharCardNumber","Aadhar card number required");
}
else
{
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0) 
{
blException.addException("aadharCardNumber","Aadhar card number required");
}
else if(aadharCardNumberWiseStudentsMap.containsKey(aadharCardNumber.toUpperCase())) //check if enrollment and aadhar are unique
{
blException.addException("aadharCardNumber","Aadhar card number "+aadharCardNumber+" already exists");
}
}
if(blException.hasExceptions())
{
throw blException;
}
try
{
StudentDTOInterface studentDTO = new StudentDTO();
//studentDTO.setRollNo(rollNo); //no need to set. value null by default
studentDTO.setName(name);
studentDTO.setCourseCode(courseCode);
studentDTO.setDateOfBirth(dateOfBirth);
studentDTO.setGender((gender=='M')?GENDER.MALE:GENDER.FEMALE); 
studentDTO.setIsIndian(isIndian);
studentDTO.setFees(fees); 		//doubt. It's correct. Fees is of BigDecimal type, which is also the type that needs to be passed
studentDTO.setEnrollmentNumber(enrollmentNumber);
studentDTO.setAadharCardNumber(aadharCardNumber);
new StudentDAO().add(studentDTO);
student.setRollNo(studentDTO.getRollNo()); //setting roll no into parameter object so from where it was called, we can display that such and such a roll no has been set
//creating a fresh new object to add into our DS
StudentInterface blStudent = new Student();
blStudent.setRollNo(studentDTO.getRollNo());
blStudent.setName(name);

//getting original Course object for setting into Student. For that we'll temporarily typecast because the "interface" pointer does not know this method which is only in its implementation
CourseInterface originalCourse;
originalCourse = ((CourseManager)courseManager).getDSCourseByCode(course.getCode());
blStudent.setCourse(originalCourse);

blStudent.setDateOfBirth(dateOfBirth);
blStudent.setGender((gender=='M')?GENDER.MALE:GENDER.FEMALE); //if condition is true, first value will be assigned, else second value will be assigned 
blStudent.setIsIndian(isIndian);
blStudent.setFees(fees); 		//doubt
blStudent.setEnrollmentNumber(enrollmentNumber);
blStudent.setAadharCardNumber(aadharCardNumber);
//add to DS
this.rollNoWiseStudentsMap.put(blStudent.getRollNo().toUpperCase(),blStudent);
this.enrollmentNumberWiseStudentsMap.put(enrollmentNumber.toUpperCase(),blStudent);
this.aadharCardNumberWiseStudentsMap.put(aadharCardNumber.toUpperCase(),blStudent);
this.studentsSet.add(blStudent); 
}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
throw blException;
}
}
public void updateStudent(StudentInterface student) throws BLException
{
BLException blException = new BLException();
String rollNo = student.getRollNo();
if(rollNo==null) 
{
blException.addException("rollNo","Roll no required");
throw blException;
}
else
{
rollNo = rollNo.trim();
if(rollNo.length()==0) 
{
blException.addException("rollNo","Roll no required");
throw blException;
}
else if(rollNoWiseStudentsMap.containsKey(rollNo.toUpperCase())==false)
{
blException.addException("rollNo","Invalid roll no: "+rollNo);
throw blException;
}
}
String name = student.getName();
if(name==null) 
{
blException.addException("name","Name required");
}
else
{
name=name.trim();
if(name.length()==0) 
{
blException.addException("name","Name required");
}
}

CourseManagerInterface courseManager = CourseManager.getCourseManager(); //for checking if course code exists in Courses
CourseInterface course;
int courseCode=0;
course = student.getCourse();
if(course==null)
{
blException.addException("course","Course required");
}
else 
{
courseCode=course.getCode();
if(courseManager.courseCodeExists(courseCode)==false) //doubt: else block is correct? Yes, correct.
{
blException.addException("courseCode","Invalid course code: "+courseCode);
}
}
Date dateOfBirth = student.getDateOfBirth();
if(dateOfBirth==null) 
{
blException.addException("dateOfBirth","Date of birth required");
}
char gender = student.getGender();
if(!(gender=='M' || gender=='F'))
{
blException.addException("gender","Invalid gender: "+gender+". Only M/F allowed");
}
boolean isIndian = student.getIsIndian();
BigDecimal fees = student.getFees();
if(fees==null)
{
blException.addException("fees","Fees required");
}
else
{
if(fees.signum()==-1)
{
blException.addException("fees","Fees cannot be negative");
}
}
String enrollmentNumber = student.getEnrollmentNumber();
if(enrollmentNumber==null) 
{
blException.addException("enrollmentNumber","Enrollment number required");
}
else
{
enrollmentNumber=enrollmentNumber.trim();
if(enrollmentNumber.length()==0) //doubt: name validations are enough?
{
blException.addException("enrollmentNumber","Enrollment number required");
}
else
{
StudentInterface tmpStudent = enrollmentNumberWiseStudentsMap.get(enrollmentNumber.toUpperCase());
if(tmpStudent!=null && tmpStudent.getRollNo().equalsIgnoreCase(rollNo)==false) //check if enrollment and aadhar are unique
{
blException.addException("enrollmentNumber","Enrollment number "+enrollmentNumber+" already exists");
}
}
}
String aadharCardNumber = student.getAadharCardNumber();
if(aadharCardNumber==null) 
{
blException.addException("aadharCardNumber","Aadhar card number required");
}
else
{
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0) //doubt: name validations are enough?
{
blException.addException("aadharCardNumber","Aadhar card number required");
}
else
{
StudentInterface tmpStudent = aadharCardNumberWiseStudentsMap.get(aadharCardNumber.toUpperCase()); //Map.get() returns null if it finds nothing
if(tmpStudent!=null && tmpStudent.getRollNo().equalsIgnoreCase(rollNo)==false) //check if enrollment and aadhar are unique
{
blException.addException("aadharCardNumber","Aadhar card number "+aadharCardNumber+" already exists");
}
}
}
if(blException.hasExceptions())
{
throw blException;
}
try
{
//for remove purposes
StudentInterface dsStudent;
dsStudent = this.rollNoWiseStudentsMap.get(rollNo.toUpperCase());
String oldEnrollmentNumber = dsStudent.getEnrollmentNumber();
String oldAadharCardNumber = dsStudent.getAadharCardNumber();

StudentDTOInterface studentDTO = new StudentDTO();
studentDTO.setRollNo(dsStudent.getRollNo()); //doubt: error. we should not set the rollno being passed. we should set the rollno that exists in ds
studentDTO.setName(name);
studentDTO.setCourseCode(courseCode);
studentDTO.setDateOfBirth(dateOfBirth);
studentDTO.setGender((gender=='M')?GENDER.MALE:GENDER.FEMALE); 
studentDTO.setIsIndian(isIndian);
studentDTO.setFees(fees); 		//doubt
studentDTO.setEnrollmentNumber(enrollmentNumber);
studentDTO.setAadharCardNumber(aadharCardNumber);
new StudentDAO().update(studentDTO);
//creating a fresh new object to add into our DS
StudentInterface blStudent = new Student();
blStudent.setRollNo(studentDTO.getRollNo());
blStudent.setName(name);
blStudent.setCourse(course);
blStudent.setDateOfBirth(dateOfBirth);
blStudent.setGender((gender=='M')?GENDER.MALE:GENDER.FEMALE); //if condition is true, first value will be assigned, else second value will be assigned 
blStudent.setIsIndian(isIndian);
blStudent.setFees(fees); 
blStudent.setEnrollmentNumber(enrollmentNumber);
blStudent.setAadharCardNumber(aadharCardNumber);
//remove from DS
rollNoWiseStudentsMap.remove(dsStudent.getRollNo());
enrollmentNumberWiseStudentsMap.remove(oldEnrollmentNumber);
aadharCardNumberWiseStudentsMap.remove(oldAadharCardNumber);
studentsSet.remove(dsStudent); 
//add to DS
this.rollNoWiseStudentsMap.put(blStudent.getRollNo().toUpperCase(),blStudent); //uppercasing as a precaution
this.enrollmentNumberWiseStudentsMap.put(blStudent.getEnrollmentNumber().toUpperCase(),blStudent); //enter into DS according to new enrollment/aadhar field
this.aadharCardNumberWiseStudentsMap.put(blStudent.getAadharCardNumber().toUpperCase(),blStudent);
this.studentsSet.add(blStudent);
}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
throw blException;
}
}
public void removeStudent(String rollNo) throws BLException
{
if(rollNo==null) 
{
BLException blException = new BLException();
blException.addException("rollNo","Roll no required");
throw blException;
}
else
{
rollNo = rollNo.trim();
if(rollNo.length()==0) 
{
BLException blException = new BLException();
blException.addException("rollNo","Roll no required");
throw blException;
}
else if(rollNoWiseStudentsMap.containsKey(rollNo.toUpperCase())==false)
{
BLException blException = new BLException();
blException.addException("rollNo","Invalid roll no: "+rollNo);
throw blException;
}
}
try
{
//for remove purposes
StudentInterface dsStudent;
dsStudent = this.rollNoWiseStudentsMap.get(rollNo.toUpperCase());
String oldEnrollmentNumber = dsStudent.getEnrollmentNumber();
String oldAadharCardNumber = dsStudent.getAadharCardNumber();

new StudentDAO().delete(dsStudent.getRollNo());
//remove from DS
rollNoWiseStudentsMap.remove(dsStudent.getRollNo());
enrollmentNumberWiseStudentsMap.remove(oldEnrollmentNumber);
aadharCardNumberWiseStudentsMap.remove(oldAadharCardNumber);
studentsSet.remove(dsStudent); 
}catch(DAOException daoException)
{
BLException blException = new BLException();
blException.setGenericException(daoException.getMessage());
throw blException;
}
}

public Set<StudentInterface> getStudentsByCourseCode(int courseCode) throws BLException
{
Set<StudentInterface> treeSet = new TreeSet<>();
for(StudentInterface student:this.studentsSet)
{
if(student.getCourse().getCode()==courseCode)
{
treeSet.add(student);
}
}
return treeSet;
}
public Set<StudentInterface> getStudents()
{
Set<StudentInterface> treeSet = new TreeSet<>();
for(StudentInterface student:this.studentsSet)
{
treeSet.add(student);
}
return treeSet;
//doubt: this much precaution necessary? is IOException the correct one to catch?
}

public StudentInterface getStudentByRollNo(String rollNo) throws BLException
{
BLException blException = new BLException();
StudentInterface student;
student = this.rollNoWiseStudentsMap.get(rollNo.toUpperCase());
if(student==null) //that is if it finds nothing
{
blException.setGenericException("Invalid rollNo: "+rollNo);
throw blException;
}
else
{
StudentInterface tmpStudent = new Student();
tmpStudent.setRollNo(student.getRollNo());
tmpStudent.setName(student.getName());
tmpStudent.setCourse(student.getCourse()); //error: set duplicate of Course
tmpStudent.setDateOfBirth(student.getDateOfBirth());
char gender = student.getGender();
tmpStudent.setGender((gender=='M')?GENDER.MALE:GENDER.FEMALE);
tmpStudent.setIsIndian(student.getIsIndian());
tmpStudent.setFees(student.getFees());
tmpStudent.setEnrollmentNumber(student.getEnrollmentNumber());
tmpStudent.setAadharCardNumber(student.getAadharCardNumber());
return tmpStudent;
}
}
public StudentInterface getStudentByEnrollmentNumber(String enrollmentNumber) throws BLException
{
BLException blException = new BLException();
StudentInterface student;
student = this.enrollmentNumberWiseStudentsMap.get(enrollmentNumber.toUpperCase());
if(student==null) //that is if it finds nothing
{
blException.setGenericException("Invalid enrollment number: "+enrollmentNumber);
throw blException;
}
else
{
StudentInterface tmpStudent = new Student();
tmpStudent.setRollNo(student.getRollNo());
tmpStudent.setName(student.getName());
tmpStudent.setCourse(student.getCourse());
tmpStudent.setDateOfBirth(student.getDateOfBirth());
char gender = student.getGender();
tmpStudent.setGender((gender=='M')?GENDER.MALE:GENDER.FEMALE);
tmpStudent.setIsIndian(student.getIsIndian());
tmpStudent.setFees(student.getFees());
tmpStudent.setEnrollmentNumber(student.getEnrollmentNumber());
tmpStudent.setAadharCardNumber(student.getAadharCardNumber());
return tmpStudent;
}
}
public StudentInterface getStudentByAadharCardNumber(String aadharCardNumber) throws BLException
{
BLException blException = new BLException();
StudentInterface student;
student = this.aadharCardNumberWiseStudentsMap.get(aadharCardNumber.toUpperCase());
if(student==null) //that is if it finds nothing
{
blException.setGenericException("Invalid aadhar card number: "+aadharCardNumber);
throw blException;
}
else
{
StudentInterface tmpStudent = new Student();
tmpStudent.setRollNo(student.getRollNo());
tmpStudent.setName(student.getName());
tmpStudent.setCourse(student.getCourse());
tmpStudent.setDateOfBirth(student.getDateOfBirth());
char gender = student.getGender();
tmpStudent.setGender((gender=='M')?GENDER.MALE:GENDER.FEMALE);
tmpStudent.setIsIndian(student.getIsIndian());
tmpStudent.setFees(student.getFees());
tmpStudent.setEnrollmentNumber(student.getEnrollmentNumber());
tmpStudent.setAadharCardNumber(student.getAadharCardNumber());
return tmpStudent;
}
}

public boolean isCourseAllotted(int courseCode) throws BLException
{
BLException blException = new BLException();
boolean isCourseAllotted;
CourseManagerInterface courseManager = CourseManager.getCourseManager();
isCourseAllotted = courseManager.courseCodeExists(courseCode);
return isCourseAllotted;

//doubt: no catch DAOException?
}
public boolean studentRollNoExists(String rollNo)
{
return this.rollNoWiseStudentsMap.containsKey(rollNo.toUpperCase());
}
public boolean studentEnrollmentNumberExists(String enrollmentNumber)
{
return this.enrollmentNumberWiseStudentsMap.containsKey(enrollmentNumber.toUpperCase());
}
public boolean studentAadharCardNumberExists(String aadharCardNumber)
{
return this.aadharCardNumberWiseStudentsMap.containsKey(aadharCardNumber.toUpperCase());
}

public int getStudentCount()
{
return this.studentsSet.size();
}
public int getStudentCountByCourse(int courseCode) throws BLException
{
int count = 0;
for(StudentInterface student:this.studentsSet)
{
if(student.getCourse().getCode()==courseCode)
{
count++;
}
}
return count;
}

}//end of StudentManager.java class