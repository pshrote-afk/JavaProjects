package com.thinking.machines.hr.dl.dao;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.exceptions.*;
import java.io.*;	//for File and RandomAccessFile classes
import java.util.*; 	//for TreeSet

import java.math.*; //for SimpleDateFormat
import java.text.*; //for SimpleDateFormat

public class EmployeeDAO implements EmployeeDAOInterface
{
private final static String FILE_NAME = "employee.data";
//private final static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

public void add(EmployeeDTOInterface employeeDTO) throws DAOException
{
if(employeeDTO==null) throw new DAOException("Employee is null");
//first extract data DTO
String employeeId;
String name = employeeDTO.getName(); //2
if(name==null) throw new DAOException("Name cannot be null");
name=name.trim();
if(name.length()==0) throw new DAOException("Length of name cannot be null");
int designationCode = employeeDTO.getDesignationCode(); //3
if(designationCode<=0) throw new DAOException("Invalid designation code: " + designationCode);
DesignationDAOInterface designationDAO = new DesignationDAO();
boolean isDesignationCodeValid = designationDAO.codeExists(designationCode);
if(isDesignationCodeValid==false)
{
throw new DAOException("Invalid designation code: " + designationCode);
}
Date dateOfBirth = employeeDTO.getDateOfBirth(); //4
char gender = employeeDTO.getGender(); //5 
boolean isIndian = employeeDTO.getIsIndian(); //6
BigDecimal basicSalary = employeeDTO.getBasicSalary(); //7
if(basicSalary==null) throw new DAOException("Salary cannot be null");
if(basicSalary.signum()==-1) throw new DAOException("Salary cannot be negative");
String panNumber = employeeDTO.getPANNumber(); //8
if(panNumber==null) throw new DAOException("PAN number cannot be null");
panNumber=panNumber.trim();
if(panNumber.length()==0) throw new DAOException("Length of PAN number cannot be zero");
String aadharCardNumber = employeeDTO.getAadharCardNumber(); //9
if(aadharCardNumber==null) throw new DAOException("adhar card number cannot be null");
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0) throw new DAOException("Length of Aadhar card number cannot be zero");
//now open file and verify that pan and aadhar are unique
try
{
File file = new File(FILE_NAME);
RandomAccessFile randomAccessFile;
randomAccessFile  = new RandomAccessFile(file,"rw");

int lastGeneratedEmployeeId=10000000;
String lastGeneratedEmployeeIdString = "";
int recordCount=0;
String recordCountString = "";
if(file.length()==0) //means new file
{
lastGeneratedEmployeeIdString = String.format("%-10d",lastGeneratedEmployeeId);
randomAccessFile.writeBytes(String.format(lastGeneratedEmployeeIdString) + "\n");
recordCountString = String.format("%-10d",recordCount);
randomAccessFile.writeBytes(recordCountString + "\n");
}
else
{
lastGeneratedEmployeeId = Integer.parseInt(randomAccessFile.readLine().trim());
recordCount = Integer.parseInt(randomAccessFile.readLine().trim());
}
boolean panNumberExists=false;
boolean aadharCardNumberExists=false;

String fPANNumber;
String fAadharCardNumber;
while(randomAccessFile.getFilePointer() < randomAccessFile.length())
{
for(int k=1;k<=7;k++) randomAccessFile.readLine();
fPANNumber = randomAccessFile.readLine();
fAadharCardNumber = randomAccessFile.readLine();
if(panNumberExists==false && panNumber.equalsIgnoreCase(fPANNumber))
{
panNumberExists=true;
}
if(aadharCardNumberExists==false && aadharCardNumber.equalsIgnoreCase(fAadharCardNumber))
{
aadharCardNumberExists=true;
}
if(panNumberExists==true && aadharCardNumberExists==true) break;
}
if(panNumberExists==true && aadharCardNumberExists==true)
{
randomAccessFile.close();
throw new DAOException("PAN number ("+panNumber+") and Aadhar card number ("+aadharCardNumber+") already exists");
}
if(panNumberExists)
{
randomAccessFile.close();
throw new DAOException("PAN number ("+panNumber+") already exists");
}
if(aadharCardNumberExists)
{
randomAccessFile.close();
throw new DAOException("Aadhar card number ("+aadharCardNumber+") already exists");
}
//we reach here means we have searched the entire file, and pan and aadhar are unique
//therefore generate employee id and write in file
lastGeneratedEmployeeId++;
recordCount++;

employeeId = "A" + String.format("%-10d",lastGeneratedEmployeeId);
employeeDTO.setEmployeeId(employeeId);
randomAccessFile.writeBytes(employeeId + "\n");
randomAccessFile.writeBytes(name + "\n");
randomAccessFile.writeBytes(designationCode + "\n");

SimpleDateFormat simpleDateFormat;
simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
randomAccessFile.writeBytes(simpleDateFormat.format(dateOfBirth) + "\n");
randomAccessFile.writeBytes(gender + "\n");
randomAccessFile.writeBytes(isIndian + "\n");
randomAccessFile.writeBytes(basicSalary.toPlainString() + "\n");
randomAccessFile.writeBytes(panNumber + "\n");
randomAccessFile.writeBytes(aadharCardNumber + "\n");

//update header
lastGeneratedEmployeeIdString = String.format("%-10d",lastGeneratedEmployeeId);
recordCountString = String.format("%-10d",recordCount);
randomAccessFile.seek(0);
randomAccessFile.writeBytes(lastGeneratedEmployeeIdString + "\n");
randomAccessFile.writeBytes(recordCountString + "\n");
randomAccessFile.close();
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}

}
public void update(EmployeeDTOInterface employeeDTO) throws DAOException
{
throw new DAOException("Not yet implemented");
}
public void delete(String employeeId) throws DAOException
{
throw new DAOException("Not yet implemented");
}
public TreeSet<EmployeeDTOInterface> getAll() throws DAOException
{
DesignationDAOInterface designationDAO;
designationDAO = new DesignationDAO();
TreeSet<EmployeeDTOInterface> treeSet1 = new TreeSet<>();
//search logic, and wrap logic
try
{
File file = new File(FILE_NAME);
if(file.exists()==false) throw new DAOException("File does not exist");
RandomAccessFile randomAccessFile;
randomAccessFile = new RandomAccessFile(file,"rw");
//read header
randomAccessFile.readLine();
randomAccessFile.readLine();
String fEmployeeId;
String fName;
String fDesignationCode;
String fDateString;
String fGender;
String fIsIndianString;
String fBasicSalaryString;
String fPANNumber;
String fAadharCardNumber;
EmployeeDTOInterface employeeDTO;
while(randomAccessFile.getFilePointer() < randomAccessFile.length())
{
fEmployeeId=randomAccessFile.readLine();
fName=randomAccessFile.readLine();
fDesignationCode=randomAccessFile.readLine();
fDateString=randomAccessFile.readLine();
fGender=randomAccessFile.readLine();
fIsIndianString=randomAccessFile.readLine();
fBasicSalaryString=randomAccessFile.readLine();
fPANNumber=randomAccessFile.readLine();
fAadharCardNumber=randomAccessFile.readLine();

//wrap it in object and add to TreeSet
employeeDTO = new EmployeeDTO();
employeeDTO.setEmployeeId(fEmployeeId);
employeeDTO.setName(fName);
employeeDTO.setDesignationCode(Integer.parseInt(fDesignationCode));
SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
Date dateOfBirth=new Date();
try
{
dateOfBirth=simpleDateFormat.parse(fDateString);
}catch(ParseException parseException)
{
throw new DAOException(parseException.getMessage());
}
employeeDTO.setDateOfBirth(dateOfBirth);
employeeDTO.setGender(fGender.charAt(0));
employeeDTO.setIsIndian(Boolean.parseBoolean(fGender));
employeeDTO.setBasicSalary(new BigDecimal(fBasicSalaryString));
employeeDTO.setPANNumber(fPANNumber);
employeeDTO.setAadharCardNumber(fAadharCardNumber);
treeSet1.add(employeeDTO);
}
randomAccessFile.close();
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
if(treeSet1.isEmpty())
{
throw new DAOException("No employees added");
}
return treeSet1;
}
public TreeSet<EmployeeDTOInterface> getByDesignationCode(int designationCode) throws DAOException
{
DesignationDAOInterface designationDAO;
designationDAO = new DesignationDAO();
boolean isDesignationCodeValid = designationDAO.codeExists(designationCode);
if(isDesignationCodeValid==false)
{
throw new DAOException("Invalid designation code: "+designationCode);
}

TreeSet<EmployeeDTOInterface> treeSet1 = new TreeSet<>();
//search logic, and wrap logic
try
{
File file = new File(FILE_NAME);
if(file.exists()==false) throw new DAOException("File does not exist");
RandomAccessFile randomAccessFile;
randomAccessFile = new RandomAccessFile(file,"rw");
//read header
randomAccessFile.readLine();
randomAccessFile.readLine();
String fEmployeeId;
String fName;
int fDesignationCode;
String fDateString;
String fGender;
String fIsIndianString;
String fBasicSalaryString;
String fPANNumber;
String fAadharCardNumber;
EmployeeDTOInterface employeeDTO;
while(randomAccessFile.getFilePointer() < randomAccessFile.length())
{
fEmployeeId=randomAccessFile.readLine();
fName=randomAccessFile.readLine();
fDesignationCode=Integer.parseInt(randomAccessFile.readLine());	//important
fDateString=randomAccessFile.readLine();
fGender=randomAccessFile.readLine();
fIsIndianString=randomAccessFile.readLine();
fBasicSalaryString=randomAccessFile.readLine();
fPANNumber=randomAccessFile.readLine();
fAadharCardNumber=randomAccessFile.readLine();

if(designationCode == fDesignationCode)
{//found another employee who has same designation code
//wrap it in object and add to TreeSet
employeeDTO = new EmployeeDTO();
employeeDTO.setEmployeeId(fEmployeeId);
employeeDTO.setName(fName);
employeeDTO.setDesignationCode(fDesignationCode);
SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
Date dateOfBirth=new Date();
try
{
dateOfBirth=simpleDateFormat.parse(fDateString);
}catch(ParseException parseException)
{
throw new DAOException(parseException.getMessage());
}
employeeDTO.setDateOfBirth(dateOfBirth);
employeeDTO.setGender(fGender.charAt(0));
employeeDTO.setIsIndian(Boolean.parseBoolean(fGender));
employeeDTO.setBasicSalary(new BigDecimal(fBasicSalaryString));
employeeDTO.setPANNumber(fPANNumber);
employeeDTO.setAadharCardNumber(fAadharCardNumber);
treeSet1.add(employeeDTO);
}
}
randomAccessFile.close();
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
if(treeSet1.isEmpty())
{
throw new DAOException("Valid designation code provided. But no employees under designation code.");
}
return treeSet1;
}
public boolean isDesignationAllotted(int designationCode) throws DAOException
{
throw new DAOException("Not yet implemented");
}
public EmployeeDTOInterface getByEmployeeId(String employeeId) throws DAOException
{
if(employeeId==null) throw new DAOException("Invalid employee ID: "+employeeId);
employeeId=employeeId.trim();
if(employeeId.length()==0) throw new DAOException("Invalid employee ID: "+employeeId);
try
{
File file = new File(FILE_NAME);
if(file.exists()==false) throw new DAOException("File does not exist");
RandomAccessFile randomAccessFile;
randomAccessFile = new RandomAccessFile(file,"rw");
//read header
randomAccessFile.readLine();
randomAccessFile.readLine();
String fEmployeeId;
while(randomAccessFile.getFilePointer() < randomAccessFile.length())
{
fEmployeeId=randomAccessFile.readLine();
if(employeeId.equalsIgnoreCase(fEmployeeId))
{//found required employee
//wrap it in object and return
EmployeeDTOInterface employeeDTO;
employeeDTO = new EmployeeDTO();
employeeDTO.setEmployeeId(fEmployeeId);
employeeDTO.setName(randomAccessFile.readLine());
employeeDTO.setDesignationCode(Integer.parseInt(randomAccessFile.readLine()));

SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
Date dateOfBirth=new Date();
try
{
dateOfBirth=simpleDateFormat.parse(randomAccessFile.readLine());
}catch(ParseException parseException)
{
throw new DAOException(parseException.getMessage());
}
employeeDTO.setDateOfBirth(dateOfBirth);
employeeDTO.setGender(randomAccessFile.readLine().charAt(0));  //using String to return a char
employeeDTO.setIsIndian(Boolean.parseBoolean(randomAccessFile.readLine()));
employeeDTO.setBasicSalary(new BigDecimal(randomAccessFile.readLine()));
employeeDTO.setPANNumber(randomAccessFile.readLine());
employeeDTO.setAadharCardNumber(randomAccessFile.readLine());
randomAccessFile.close();
return employeeDTO;
}
for(int x=2;x<=9;x++) randomAccessFile.readLine();
}
randomAccessFile.close();
throw new DAOException("Invalid employee ID: "+employeeId);
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public EmployeeDTOInterface getByPANNumber(String panNumber) throws DAOException
{
if(panNumber==null) throw new DAOException("Invalid PAN number: "+panNumber);
panNumber=panNumber.trim();
if(panNumber.length()==0) throw new DAOException("Invalid PAN number: "+panNumber);
try
{
File file = new File(FILE_NAME);
if(file.exists()==false) throw new DAOException("File does not exist");
RandomAccessFile randomAccessFile;
randomAccessFile = new RandomAccessFile(file,"rw");
//read header
randomAccessFile.readLine();
randomAccessFile.readLine();
String fEmployeeId;
String fName;
String fDesignationCodeString;
String fDateString;
String fGender;
String fIsIndianString;
String fBasicSalaryString;
String fPANNumber;
String fAadharCardNumber;
while(randomAccessFile.getFilePointer() < randomAccessFile.length())
{
fEmployeeId=randomAccessFile.readLine();
fName=randomAccessFile.readLine();
fDesignationCodeString=randomAccessFile.readLine();
fDateString=randomAccessFile.readLine();
fGender=randomAccessFile.readLine();
fIsIndianString=randomAccessFile.readLine();
fBasicSalaryString=randomAccessFile.readLine();
fPANNumber=randomAccessFile.readLine();
fAadharCardNumber=randomAccessFile.readLine();

if(panNumber.equalsIgnoreCase(fPANNumber))
{//found required employee using pan number
//wrap it in object and return
EmployeeDTOInterface employeeDTO;
employeeDTO = new EmployeeDTO();
employeeDTO.setEmployeeId(fEmployeeId);
employeeDTO.setName(fName);
employeeDTO.setDesignationCode(Integer.parseInt(fDesignationCodeString));

SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
Date dateOfBirth=new Date();
try
{
dateOfBirth=simpleDateFormat.parse(fDateString);
}catch(ParseException parseException)
{
throw new DAOException(parseException.getMessage());
}
employeeDTO.setDateOfBirth(dateOfBirth);
employeeDTO.setGender(fGender.charAt(0));
employeeDTO.setIsIndian(Boolean.parseBoolean(fGender));
employeeDTO.setBasicSalary(new BigDecimal(fBasicSalaryString));
employeeDTO.setPANNumber(fPANNumber);
employeeDTO.setAadharCardNumber(fAadharCardNumber);
randomAccessFile.close();
return employeeDTO;
}

}
randomAccessFile.close();
throw new DAOException("Invalid PAN number: "+panNumber); //control reached here means file ahs been searched, and matching pan number was not found
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}

}
public EmployeeDTOInterface getByAadharCardNumber(String aadharCardNumber) throws DAOException
{
if(aadharCardNumber==null) throw new DAOException("Invalid Aadhar card number: "+aadharCardNumber);
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0) throw new DAOException("Invalid Aadhar card number: "+aadharCardNumber);
try
{
File file = new File(FILE_NAME);
if(file.exists()==false) throw new DAOException("File does not exist");
RandomAccessFile randomAccessFile;
randomAccessFile = new RandomAccessFile(file,"rw");
//read header
randomAccessFile.readLine();
randomAccessFile.readLine();
String fEmployeeId;
String fName;
String fDesignationCodeString;
String fDateString;
String fGender;
String fIsIndianString;
String fBasicSalaryString;
String fPANNumber;
String fAadharCardNumber;
while(randomAccessFile.getFilePointer() < randomAccessFile.length())
{
fEmployeeId=randomAccessFile.readLine();
fName=randomAccessFile.readLine();
fDesignationCodeString=randomAccessFile.readLine();
fDateString=randomAccessFile.readLine();
fGender=randomAccessFile.readLine();
fIsIndianString=randomAccessFile.readLine();
fBasicSalaryString=randomAccessFile.readLine();
fPANNumber=randomAccessFile.readLine();
fAadharCardNumber=randomAccessFile.readLine();

if(aadharCardNumber.equalsIgnoreCase(fAadharCardNumber))
{//found required employee using aadhar card number
//wrap it in object and return
EmployeeDTOInterface employeeDTO;
employeeDTO = new EmployeeDTO();
employeeDTO.setEmployeeId(fEmployeeId);
employeeDTO.setName(fName);
employeeDTO.setDesignationCode(Integer.parseInt(fDesignationCodeString));
SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
Date dateOfBirth=new Date();
try
{
dateOfBirth=simpleDateFormat.parse(fDateString);
}catch(ParseException parseException)
{
throw new DAOException(parseException.getMessage());
}
employeeDTO.setDateOfBirth(dateOfBirth);
employeeDTO.setGender(fGender.charAt(0));
employeeDTO.setIsIndian(Boolean.parseBoolean(fGender));
employeeDTO.setBasicSalary(new BigDecimal(fBasicSalaryString));
employeeDTO.setPANNumber(fPANNumber);
employeeDTO.setAadharCardNumber(fAadharCardNumber);
randomAccessFile.close();
return employeeDTO;
}
}
randomAccessFile.close();
throw new DAOException("Invalid Aadhar card number: "+aadharCardNumber); //control reached here means file ahs been searched, and matching aadhar card number was not found
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public boolean employeeIdExists(String employeeId) throws DAOException
{
if(employeeId==null) throw new DAOException("Invalid employee ID: "+employeeId);
employeeId=employeeId.trim();
if(employeeId.length()==0) throw new DAOException("Invalid employee ID: "+employeeId);
try
{
File file = new File(FILE_NAME);
if(file.exists()==false) throw new DAOException("File does not exist");
RandomAccessFile randomAccessFile;
randomAccessFile = new RandomAccessFile(file,"rw");
//read header
randomAccessFile.readLine();
randomAccessFile.readLine();
String fEmployeeId;
while(randomAccessFile.getFilePointer() < randomAccessFile.length())
{
fEmployeeId=randomAccessFile.readLine().trim();
if(employeeId.equalsIgnoreCase(fEmployeeId))
{//found required employee
//return true
randomAccessFile.close();
return true;
}
for(int x=2;x<=9;x++) randomAccessFile.readLine();
}
randomAccessFile.close();
return false;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public boolean panNumberExists(String panNumber) throws DAOException
{
if(panNumber==null) throw new DAOException("Invalid PAN number: "+panNumber);
panNumber=panNumber.trim();
if(panNumber.length()==0) throw new DAOException("Invalid PAN number: "+panNumber);
try
{
File file = new File(FILE_NAME);
if(file.exists()==false) throw new DAOException("File does not exist");
RandomAccessFile randomAccessFile;
randomAccessFile = new RandomAccessFile(file,"rw");
//read header
randomAccessFile.readLine();
randomAccessFile.readLine();
String fPANNumber;
while(randomAccessFile.getFilePointer() < randomAccessFile.length())
{
randomAccessFile.readLine();
randomAccessFile.readLine();
randomAccessFile.readLine();
randomAccessFile.readLine();
randomAccessFile.readLine();
randomAccessFile.readLine();
randomAccessFile.readLine();
fPANNumber=randomAccessFile.readLine();
randomAccessFile.readLine();
if(panNumber.equalsIgnoreCase(fPANNumber))
{//found required employee using pan number
//return true
randomAccessFile.close();
return true;
}
}
randomAccessFile.close();
return false; //control reached here means pan number does not exist in file
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}

}
public boolean aadharCardNumberExists(String aadharCardNumber) throws DAOException
{
if(aadharCardNumber==null) throw new DAOException("Invalid Aadhar card number: "+aadharCardNumber);
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0) throw new DAOException("Invalid Aadhar card number: "+aadharCardNumber);
try
{
File file = new File(FILE_NAME);
if(file.exists()==false) throw new DAOException("File does not exist");
RandomAccessFile randomAccessFile;
randomAccessFile = new RandomAccessFile(file,"rw");
//read header
randomAccessFile.readLine();
randomAccessFile.readLine();
String fAadharCardNumber;
while(randomAccessFile.getFilePointer() < randomAccessFile.length())
{
randomAccessFile.readLine();
randomAccessFile.readLine();
randomAccessFile.readLine();
randomAccessFile.readLine();
randomAccessFile.readLine();
randomAccessFile.readLine();
randomAccessFile.readLine();
randomAccessFile.readLine();
fAadharCardNumber=randomAccessFile.readLine();
if(aadharCardNumber.equalsIgnoreCase(fAadharCardNumber))
{//found required employee using aadhar card number
//return true
randomAccessFile.close();
return true;
}
}
randomAccessFile.close();
return false; //control reached here means aadhar card number does not exist in file
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public int getCount() throws DAOException
{
try
{
File file = new File(FILE_NAME);
if(file.exists()==false) throw new DAOException("File does not exist");
RandomAccessFile randomAccessFile;
randomAccessFile = new RandomAccessFile(file,"rw");
randomAccessFile.readLine();
int recordCount = Integer.parseInt(randomAccessFile.readLine().trim());
randomAccessFile.close();
return recordCount;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}


}
public int getCountByDesignation(int designationCode) throws DAOException
{
DesignationDAOInterface designationDAO;
designationDAO = new DesignationDAO();
boolean isDesignationCodeValid = designationDAO.codeExists(designationCode);
if(isDesignationCodeValid==false)
{
throw new DAOException("Invalid designation code: "+designationCode);
}
int countByDesignation=0;
try
{
File file = new File(FILE_NAME);
if(file.exists()==false) throw new DAOException("File does not exist");
RandomAccessFile randomAccessFile;
randomAccessFile = new RandomAccessFile(file,"rw");
//read header
randomAccessFile.readLine();
randomAccessFile.readLine();
int fDesignationCode;
while(randomAccessFile.getFilePointer() < randomAccessFile.length())
{
randomAccessFile.readLine();
randomAccessFile.readLine();
fDesignationCode=Integer.parseInt(randomAccessFile.readLine());	//important
randomAccessFile.readLine();
randomAccessFile.readLine();
randomAccessFile.readLine();
randomAccessFile.readLine();
randomAccessFile.readLine();
randomAccessFile.readLine();
if(designationCode == fDesignationCode)
{
countByDesignation++;
}
}
randomAccessFile.close();
return countByDesignation;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}

}//end of EmployeeDAO class