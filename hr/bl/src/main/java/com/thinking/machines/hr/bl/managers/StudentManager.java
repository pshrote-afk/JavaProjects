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
if(rollNo!=null) blException.addException("rollNo","Roll no should be null, or should not be passed"); //doubt: what error message?
String name = student.getName();
if(name==null) 
{
blException.addException("name","Name is null");
name=""; //so that it doesnt get stuck in next if()
}
if(name.length()>0)
{
name=name.trim();
if(name.length()==0) //doubt: name validations are enough?
{
blException.addException("name","Length of name is zero");
}
}

CourseManagerInterface courseManager = CourseManager.getCourseManager(); //for checking if course code exists in Courses
CourseInterface course;
course = student.getCourse();
int courseCode = course.getCode(); //doubt: no need to verify code, cause we have pulled it out of BL data structure
if(courseCode<=0) 
{
blException.addException("courseCode","Course code should be zero");
}
else if(courseManager.courseCodeExists(courseCode)==false) //doubt: else block is correct?
{
blException.addException("courseCode","Invalid course code: "+courseCode);
}
Date dateOfBirth = student.getDateOfBirth();
if(dateOfBirth==null) 
{
blException.addException("dateOfBirth","Date of birth is null");
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
blException.addException("fees","Fees is null");
}
String enrollmentNumber = student.getEnrollmentNumber();
if(enrollmentNumber==null) 
{
blException.addException("enrollmentNumber","Enrollment number is null");
enrollmentNumber=""; //so that it doesnt get stuck in next if()
}
if(enrollmentNumber.length()>0)
{
enrollmentNumber=enrollmentNumber.trim();
if(enrollmentNumber.length()==0) //doubt: name validations are enough?
{
blException.addException("enrollmentNumber","Length of enrollment number is zero");
}
else if(enrollmentNumberWiseStudentsMap.containsKey(enrollmentNumber)) //check if enrollment and aadhar are unique
{
StudentInterface tmpStudent = enrollmentNumberWiseStudentsMap.get(enrollmentNumber);
blException.addException("enrollmentNumber","Enrollment number already exists against roll no: "+tmpStudent.getRollNo());
}
}
String aadharCardNumber = student.getAadharCardNumber();
if(aadharCardNumber==null) 
{
blException.addException("aadharCardNumber","Aadhar card number is null");
aadharCardNumber=""; //so that it doesnt get stuck in next if()
}
if(aadharCardNumber.length()>0)
{
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0) //doubt: name validations are enough?
{
blException.addException("aadharCardNumber","Length of aadhar card number is zero");
}
else if(aadharCardNumberWiseStudentsMap.containsKey(aadharCardNumber)) //check if enrollment and aadhar are unique
{
StudentInterface tmpStudent = aadharCardNumberWiseStudentsMap.get(aadharCardNumber);
blException.addException("aadharCardNumber","Aadhar card number already exists against roll no: "+tmpStudent.getRollNo());
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
studentDTO.setFees(fees); 		//doubt
studentDTO.setEnrollmentNumber(enrollmentNumber);
studentDTO.setAadharCardNumber(aadharCardNumber);
new StudentDAO().add(studentDTO);
student.setRollNo(studentDTO.getRollNo()); //setting roll no into parameter object so from where it was called, we can display that such and such a roll no has been set
//creating a fresh new object to add into our DS
StudentInterface blStudent = new Student();
blStudent.setRollNo(studentDTO.getRollNo());
blStudent.setName(name);
blStudent.setCourse(course);
blStudent.setDateOfBirth(dateOfBirth);
blStudent.setGender((gender=='M')?GENDER.MALE:GENDER.FEMALE); //if condition is true, first value will be assigned, else second value will be assigned 
blStudent.setIsIndian(isIndian);
blStudent.setFees(fees); 		//doubt
blStudent.setEnrollmentNumber(enrollmentNumber);
blStudent.setAadharCardNumber(aadharCardNumber);
//add to DS
this.rollNoWiseStudentsMap.put(blStudent.getRollNo(),blStudent);
this.enrollmentNumberWiseStudentsMap.put(enrollmentNumber,blStudent);
this.aadharCardNumberWiseStudentsMap.put(aadharCardNumber,blStudent);
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
blException.addException("rollNo","Roll no is null");
rollNo= ""; //doubt
}
if(rollNo.length()>0)
{
rollNo = rollNo.trim();
if(rollNo.length()==0) 
{
blException.addException("rollNo","Length of roll no is zero");
}
else if(rollNoWiseStudentsMap.containsKey(rollNo)==false)
{
blException.addException("rollNo","Roll no does not exists");
}
}
String name = student.getName();
if(name==null) 
{
blException.addException("name","Name is null");
name=""; //so that it doesnt get stuck in next if()
}
if(name.length()>0)
{
name=name.trim();
if(name.length()==0) //doubt: name validations are enough?
{
blException.addException("name","Length of name is zero");
}
}

CourseManagerInterface courseManager = CourseManager.getCourseManager(); //for checking if course code exists in Courses
CourseInterface course;
course = student.getCourse();
int courseCode = course.getCode();
if(courseManager.courseCodeExists(courseCode)==false)
{
blException.addException("courseCode","Invalid course code: "+courseCode);
}
Date dateOfBirth = student.getDateOfBirth();
if(dateOfBirth==null) 
{
blException.addException("dateOfBirth","Date of birth is null");
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
blException.addException("fees","Fees is null");
}
String enrollmentNumber = student.getEnrollmentNumber();
if(enrollmentNumber==null) 
{
blException.addException("enrollmentNumber","Enrollment number is null");
enrollmentNumber=""; //so that it doesnt get stuck in next if()
}
if(enrollmentNumber.length()>0)
{
enrollmentNumber=enrollmentNumber.trim();
if(enrollmentNumber.length()==0) //doubt: name validations are enough?
{
blException.addException("enrollmentNumber","Length of enrollment number is zero");
}
StudentInterface tmpStudent = enrollmentNumberWiseStudentsMap.get(enrollmentNumber);
if(tmpStudent!=null && tmpStudent.getRollNo().equalsIgnoreCase(rollNo)==false) //check if enrollment and aadhar are unique
{
blException.addException("enrollmentNumber","Enrollment number already exists against roll no: "+tmpStudent.getRollNo());
}
}
String aadharCardNumber = student.getAadharCardNumber();
if(aadharCardNumber==null) 
{
blException.addException("aadharCardNumber","Aadhar card number is null");
aadharCardNumber=""; //so that it doesnt get stuck in next if()
}
if(aadharCardNumber.length()>0)
{
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0) //doubt: name validations are enough?
{
blException.addException("aadharCardNumber","Length of aadhar card number is zero");
}
StudentInterface tmpStudent = aadharCardNumberWiseStudentsMap.get(aadharCardNumber); //Map.get() returns null if it finds nothing
if(tmpStudent!=null && tmpStudent.getRollNo().equalsIgnoreCase(rollNo)==false) //check if enrollment and aadhar are unique
{
blException.addException("aadharCardNumber","Aadhar card number already exists against roll no: "+tmpStudent.getRollNo());
}
}
if(blException.hasExceptions())
{
throw blException;
}
try
{
StudentDTOInterface studentDTO = new StudentDTO();
studentDTO.setRollNo(rollNo); 
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
blStudent.setRollNo(rollNo);
blStudent.setName(name);
blStudent.setCourse(course);
blStudent.setDateOfBirth(dateOfBirth);
blStudent.setGender((gender=='M')?GENDER.MALE:GENDER.FEMALE); //if condition is true, first value will be assigned, else second value will be assigned 
blStudent.setIsIndian(isIndian);
blStudent.setFees(fees); 		//doubt
blStudent.setEnrollmentNumber(enrollmentNumber);
blStudent.setAadharCardNumber(aadharCardNumber);
//remove from DS
StudentInterface tmpStudent;
tmpStudent = rollNoWiseStudentsMap.get(rollNo);
rollNoWiseStudentsMap.remove(rollNo);
enrollmentNumberWiseStudentsMap.remove(enrollmentNumber);
aadharCardNumberWiseStudentsMap.remove(aadharCardNumber);
studentsSet.remove(tmpStudent); 
//add to DS
this.rollNoWiseStudentsMap.put(blStudent.getRollNo(),blStudent);
this.enrollmentNumberWiseStudentsMap.put(blStudent.getEnrollmentNumber(),blStudent); //enter into DS according to new enrollment/aadhar field
this.aadharCardNumberWiseStudentsMap.put(blStudent.getAadharCardNumber(),blStudent);
this.studentsSet.add(blStudent);
}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
throw blException;
}
}
public void removeStudent(String rollNo) throws BLException
{
BLException blException = new BLException();
String rollNo = student.getRollNo();
if(rollNo==null) 
{
blException.addException("rollNo","Roll no is null");
rollNo= ""; //doubt
}
if(rollNo.length()>0)
{
rollNo = rollNo.trim();
if(rollNo.length()==0) 
{
blException.addException("rollNo","Length of roll no is zero");
}
else if(rollNoWiseStudentsMap.containsKey(rollNo)==false)
{
blException.addException("rollNo","Roll no does not exists");
}
}
String name = student.getName();
if(name==null) 
{
blException.addException("name","Name is null");
name=""; //so that it doesnt get stuck in next if()
}
if(name.length()>0)
{
name=name.trim();
if(name.length()==0) //doubt: name validations are enough?
{
blException.addException("name","Length of name is zero");
}
}

CourseManagerInterface courseManager = CourseManager.getCourseManager(); //for checking if course code exists in Courses
CourseInterface course;
course = student.getCourse();
int courseCode = course.getCode();
if(courseManager.courseCodeExists(courseCode)==false)
{
blException.addException("courseCode","Invalid course code: "+courseCode);
}
Date dateOfBirth = student.getDateOfBirth();
if(dateOfBirth==null) 
{
blException.addException("dateOfBirth","Date of birth is null");
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
blException.addException("fees","Fees is null");
}
String enrollmentNumber = student.getEnrollmentNumber();
if(enrollmentNumber==null) 
{
blException.addException("enrollmentNumber","Enrollment number is null");
enrollmentNumber=""; //so that it doesnt get stuck in next if()
}
if(enrollmentNumber.length()>0)
{
enrollmentNumber=enrollmentNumber.trim();
if(enrollmentNumber.length()==0) //doubt: name validations are enough?
{
blException.addException("enrollmentNumber","Length of enrollment number is zero");
}
StudentInterface tmpStudent = enrollmentNumberWiseStudentsMap.get(enrollmentNumber);
if(tmpStudent!=null && tmpStudent.getRollNo().equalsIgnoreCase(rollNo)==false) //check if enrollment and aadhar are unique
{
blException.addException("enrollmentNumber","Enrollment number already exists against roll no: "+tmpStudent.getRollNo());
}
}
String aadharCardNumber = student.getAadharCardNumber();
if(aadharCardNumber==null) 
{
blException.addException("aadharCardNumber","Aadhar card number is null");
aadharCardNumber=""; //so that it doesnt get stuck in next if()
}
if(aadharCardNumber.length()>0)
{
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0) //doubt: name validations are enough?
{
blException.addException("aadharCardNumber","Length of aadhar card number is zero");
}
StudentInterface tmpStudent = aadharCardNumberWiseStudentsMap.get(aadharCardNumber); //Map.get() returns null if it finds nothing
if(tmpStudent!=null && tmpStudent.getRollNo().equalsIgnoreCase(rollNo)==false) //check if enrollment and aadhar are unique
{
blException.addException("aadharCardNumber","Aadhar card number already exists against roll no: "+tmpStudent.getRollNo());
}
}
if(blException.hasExceptions())
{
throw blException;
}
try
{
StudentDTOInterface studentDTO = new StudentDTO();
studentDTO.setRollNo(rollNo); 
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
blStudent.setRollNo(rollNo);
blStudent.setName(name);
blStudent.setCourse(course);
blStudent.setDateOfBirth(dateOfBirth);
blStudent.setGender((gender=='M')?GENDER.MALE:GENDER.FEMALE); //if condition is true, first value will be assigned, else second value will be assigned 
blStudent.setIsIndian(isIndian);
blStudent.setFees(fees); 		//doubt
blStudent.setEnrollmentNumber(enrollmentNumber);
blStudent.setAadharCardNumber(aadharCardNumber);
//remove from DS
StudentInterface tmpStudent;
tmpStudent = rollNoWiseStudentsMap.get(rollNo);
rollNoWiseStudentsMap.remove(rollNo);
enrollmentNumberWiseStudentsMap.remove(enrollmentNumber);
aadharCardNumberWiseStudentsMap.remove(aadharCardNumber);
studentsSet.remove(tmpStudent); 
//add to DS
this.rollNoWiseStudentsMap.put(blStudent.getRollNo(),blStudent);
this.enrollmentNumberWiseStudentsMap.put(blStudent.getEnrollmentNumber(),blStudent); //enter into DS according to new enrollment/aadhar field
this.aadharCardNumberWiseStudentsMap.put(blStudent.getAadharCardNumber(),blStudent);
this.studentsSet.add(blStudent);
}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
throw blException;
}
}

public Set<StudentInterface> getStudentByCourseCode(int courseCode) throws BLException
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

public boolean isCourseAllotted(int courseCode) throws BLException
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
public int getStudentCountByCourse(int courseCode) throws BLException
{
BLException blException = new BLException();
blException.setGenericException("not yet implemented");
throw blException;
}
}