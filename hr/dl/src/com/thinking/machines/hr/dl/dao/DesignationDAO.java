package com.thinking.machines.hr.dl.dao;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.exceptions.*;
import java.util.*;	//for TreeSet
import java.io.*;	//for File, RandomAccessFile class
public class DesignationDAO implements DesignationDAOInterface
{
private final static String FILE_NAME = "designation.data";
 
public void add(DesignationDTOInterface designationDTO) throws DAOException
{//check beginning validations 
String title = designationDTO.getTitle();
title = title.trim();
if(title.length()==0)
{
throw new DAOException("Title cannot have length zero.");
}
try
{
File file = new File(FILE_NAME);
RandomAccessFile randomAccessFile;
randomAccessFile = new RandomAccessFile(file,"rw");

int lastGeneratedCode=0;
int recordCount=0;
String lastGeneratedCodeString="";
String recordCountString="";

if(randomAccessFile.length()==0)	//this means file is new/empty. Therefore we initialize "header" accordingly
{
lastGeneratedCodeString="0";
while(lastGeneratedCodeString.length() < 10)
{
lastGeneratedCodeString+=" ";
}
recordCountString="0";
while(recordCountString.length() < 10)
{
recordCountString+=" ";
}
randomAccessFile.writeBytes(lastGeneratedCodeString);
randomAccessFile.writeBytes("\n");
randomAccessFile.writeBytes(recordCountString);
randomAccessFile.writeBytes("\n");
}
else	//else we read the current header
{
lastGeneratedCodeString = randomAccessFile.readLine().trim();
recordCountString = randomAccessFile.readLine().trim();
lastGeneratedCode = Integer.parseInt(lastGeneratedCodeString);
recordCount = Integer.parseInt(recordCountString);
}

int fCode;
String fTitle;
while(randomAccessFile.getFilePointer() < randomAccessFile.length())	//traverse file
{
fCode = Integer.parseInt(randomAccessFile.readLine());
fTitle = randomAccessFile.readLine();
if(fTitle.equalsIgnoreCase(title))
{
throw new DAOException("Designation \""+title+"\" already exists with code "+fCode+".");
}
}
//if control reaches here, means passed title is unique. thus, write it into file
int code = lastGeneratedCode+1;
designationDTO.setCode(code);

randomAccessFile.writeBytes(String.valueOf(designationDTO.getCode()));
randomAccessFile.writeBytes("\n");
randomAccessFile.writeBytes(designationDTO.getTitle());
randomAccessFile.writeBytes("\n");

lastGeneratedCode++;
recordCount++;

//ready masala to update header
lastGeneratedCodeString = String.valueOf(lastGeneratedCode);
recordCountString = String.valueOf(recordCount);
while(lastGeneratedCodeString.length() < 10)
{
lastGeneratedCodeString+=" ";
}
while(recordCountString.length() < 10)
{
recordCountString+=" ";
}

randomAccessFile.seek(0);
randomAccessFile.writeBytes(lastGeneratedCodeString);
randomAccessFile.writeBytes("\n");
randomAccessFile.writeBytes(recordCountString);
randomAccessFile.writeBytes("\n");
randomAccessFile.close();
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public void update(DesignationDTOInterface designationDTO) throws DAOException
{
if(designationDTO==null) throw new DAOException("Designation is null"); 
int code = designationDTO.getCode();
if(code<=0) throw new DAOException("Invalid code: "+code);
String title = designationDTO.getTitle().trim();
if(title==null) throw new DAOException("Designation is null"); 
if(title.length()==0) throw new DAOException("Length of designation cannot be zero.");
//check if code exists
//also check if received title already exists against some code
try
{
File file = new File(FILE_NAME);
RandomAccessFile randomAccessFile;
randomAccessFile = new RandomAccessFile(file,"rw");
int fCode;
String fTitle;
randomAccessFile.readLine();
randomAccessFile.readLine();
boolean found = false;
while(randomAccessFile.getFilePointer() < randomAccessFile.length())
{
fCode = Integer.parseInt(randomAccessFile.readLine());
if(fCode == code)
{
found = true;
break;
}
randomAccessFile.readLine();
}
if(found==false) 
{
randomAccessFile.close();
throw new DAOException("Invalid code: "+code); 
}
randomAccessFile.seek(0);
randomAccessFile.readLine();
randomAccessFile.readLine();

while(randomAccessFile.getFilePointer() < randomAccessFile.length())
{
fCode = Integer.parseInt(randomAccessFile.readLine());
fTitle = randomAccessFile.readLine();
if(fCode!=code &&  title.equalsIgnoreCase(fTitle))
{
randomAccessFile.close();
throw new DAOException("Title: " +fTitle+ " already exists with code: "+fCode);
}
}

File tmpFile = new File("designation.tmp");
if(tmpFile.exists()) tmpFile.delete();
RandomAccessFile tmpRandomAccessFile;
tmpRandomAccessFile = new RandomAccessFile(tmpFile,"rw");

randomAccessFile.seek(0);

tmpRandomAccessFile.writeBytes(randomAccessFile.readLine());
tmpRandomAccessFile.writeBytes("\n");
tmpRandomAccessFile.writeBytes(randomAccessFile.readLine());
tmpRandomAccessFile.writeBytes("\n");

//copy main file to tmp file and update wherever necessary
while(randomAccessFile.getFilePointer() < randomAccessFile.length())
{
fCode = Integer.parseInt(randomAccessFile.readLine());
fTitle = randomAccessFile.readLine();
if(code!=fCode)	//update
{
tmpRandomAccessFile.writeBytes(String.valueOf(fCode));
tmpRandomAccessFile.writeBytes("\n");
tmpRandomAccessFile.writeBytes(fTitle);
tmpRandomAccessFile.writeBytes("\n");
}
else
{
tmpRandomAccessFile.writeBytes(String.valueOf(code));
tmpRandomAccessFile.writeBytes("\n");
tmpRandomAccessFile.writeBytes(title);	//updated
tmpRandomAccessFile.writeBytes("\n");
}
}
randomAccessFile.seek(0);
tmpRandomAccessFile.seek(0);
while(tmpRandomAccessFile.getFilePointer() < tmpRandomAccessFile.length())
{
randomAccessFile.writeBytes(tmpRandomAccessFile.readLine());
randomAccessFile.writeBytes("\n");
}
randomAccessFile.setLength(tmpRandomAccessFile.length());
tmpRandomAccessFile.setLength(0);
tmpRandomAccessFile.close();
randomAccessFile.close();
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public void delete(int code) throws DAOException
{
if(code<=0) throw new DAOException("Invalid code: "+code);
//check if code exists
try
{
File file = new File(FILE_NAME);
RandomAccessFile randomAccessFile;
randomAccessFile = new RandomAccessFile(file,"rw");
int fCode;
String fTitle;
randomAccessFile.readLine();
randomAccessFile.readLine();
boolean found = false;
while(randomAccessFile.getFilePointer() < randomAccessFile.length())
{
fCode = Integer.parseInt(randomAccessFile.readLine());
if(fCode == code)
{
found = true;
break;
}
randomAccessFile.readLine();
}
if(found==false) 
{
randomAccessFile.close();
throw new DAOException("Invalid code: "+code); 
}
randomAccessFile.seek(0);
randomAccessFile.readLine();
randomAccessFile.readLine();

File tmpFile = new File("designation.tmp");
if(tmpFile.exists()) tmpFile.delete();
RandomAccessFile tmpRandomAccessFile;
tmpRandomAccessFile = new RandomAccessFile(tmpFile,"rw");

randomAccessFile.seek(0);

tmpRandomAccessFile.writeBytes(randomAccessFile.readLine());
tmpRandomAccessFile.writeBytes("\n");
tmpRandomAccessFile.writeBytes(randomAccessFile.readLine());
tmpRandomAccessFile.writeBytes("\n");

//copy main file to tmp file and delete wherever necessary
while(randomAccessFile.getFilePointer() < randomAccessFile.length())
{
fCode = Integer.parseInt(randomAccessFile.readLine());
fTitle = randomAccessFile.readLine();
if(code!=fCode)	
{
tmpRandomAccessFile.writeBytes(String.valueOf(fCode));
tmpRandomAccessFile.writeBytes("\n");
tmpRandomAccessFile.writeBytes(fTitle);
tmpRandomAccessFile.writeBytes("\n");
}
}
randomAccessFile.seek(0);
tmpRandomAccessFile.seek(0);

String lastGeneratedCode = tmpRandomAccessFile.readLine();
int recordCount = Integer.parseInt(tmpRandomAccessFile.readLine().trim());
recordCount--;
String recordCountString = String.valueOf(recordCount);
while(recordCountString.length() < 10)
{
recordCountString+=" ";
}
randomAccessFile.writeBytes(lastGeneratedCode);
randomAccessFile.writeBytes("\n");
randomAccessFile.writeBytes(recordCountString);
randomAccessFile.writeBytes("\n");

while(tmpRandomAccessFile.getFilePointer() < tmpRandomAccessFile.length())
{
randomAccessFile.writeBytes(tmpRandomAccessFile.readLine());
randomAccessFile.writeBytes("\n");
}
randomAccessFile.setLength(tmpRandomAccessFile.length());
tmpRandomAccessFile.setLength(0);
tmpRandomAccessFile.close();
randomAccessFile.close();
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}

}
public TreeSet<DesignationDTOInterface> getAll() throws DAOException
{
try
{
File file = new File(FILE_NAME);
if(file.exists()==false) throw new DAOException("File does not exist.");
RandomAccessFile randomAccessFile;
randomAccessFile = new RandomAccessFile(file,"rw");

randomAccessFile.readLine();
randomAccessFile.readLine();

TreeSet<DesignationDTOInterface> getAllSet = new TreeSet<>();

int code;
String title;
DesignationDTOInterface designationDTO; 
while(randomAccessFile.getFilePointer() < randomAccessFile.length())
{
code = Integer.parseInt(randomAccessFile.readLine());
title = randomAccessFile.readLine();

designationDTO = new DesignationDTO();
designationDTO.setCode(code);
designationDTO.setTitle(title);
getAllSet.add(designationDTO);
}
randomAccessFile.close();
return getAllSet;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public boolean codeExists(int code) throws DAOException
{
if(code<=0) throw new DAOException("Invalid code: "+code);
try
{
File file = new File(FILE_NAME);
if(!file.exists()) throw new DAOException("File does not exist.");
RandomAccessFile randomAccessFile;
randomAccessFile  = new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("Invalid code: "+code);
}
//lastGeneratedCode, and recordCount
randomAccessFile.readLine();
int recordCount = Integer.parseInt(randomAccessFile.readLine().trim());
if(recordCount == 0)
{
randomAccessFile.close();
throw new DAOException("Invalid code: "+code);
}
int fCode;
while(randomAccessFile.getFilePointer() < randomAccessFile.length())
{
fCode = Integer.parseInt(randomAccessFile.readLine());
randomAccessFile.readLine();
if(fCode == code) //found
{
randomAccessFile.close();
return true;
}
}
randomAccessFile.close();
return false;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}

}
public boolean titleExists(String title) throws DAOException
{
title = title.trim();
if(title==null || title.length()==0) throw new DAOException("Invalid title: "+title);
try
{
File file = new File(FILE_NAME);
if(!file.exists()) throw new DAOException("File does not exist.");
RandomAccessFile randomAccessFile;
randomAccessFile  = new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("Invalid title: "+title);
}
//lastGeneratedCode, and recordCount
randomAccessFile.readLine();
int recordCount = Integer.parseInt(randomAccessFile.readLine().trim());
if(recordCount == 0)
{
randomAccessFile.close();
throw new DAOException("Invalid title: "+title);
}
String fTitle;
while(randomAccessFile.getFilePointer() < randomAccessFile.length())
{
randomAccessFile.readLine();
fTitle = randomAccessFile.readLine();
if(fTitle.equalsIgnoreCase(title)) //found
{
return true;
}
}
randomAccessFile.close();
return false;
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
RandomAccessFile randomAccessFile;
randomAccessFile = new RandomAccessFile(file,"rw");

randomAccessFile.readLine();
int recordCount = Integer.parseInt(randomAccessFile.readLine().trim());
return recordCount;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}

public DesignationDTOInterface getByCode(int code) throws DAOException
{
if(code<=0) throw new DAOException("Invalid code: "+code);
try
{
File file = new File(FILE_NAME);
if(!file.exists()) throw new DAOException("File does not exist.");
RandomAccessFile randomAccessFile;
randomAccessFile  = new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("Invalid code: "+code);
}
//lastGeneratedCode, and recordCount
randomAccessFile.readLine();
int recordCount = Integer.parseInt(randomAccessFile.readLine().trim());
if(recordCount == 0)
{
randomAccessFile.close();
throw new DAOException("Invalid code: "+code);
}

int fCode;
String fTitle;
while(randomAccessFile.getFilePointer() < randomAccessFile.length())
{
fCode = Integer.parseInt(randomAccessFile.readLine());
fTitle = randomAccessFile.readLine();
if(fCode == code) //found
{
randomAccessFile.close();
DesignationDTOInterface designationDTO;
designationDTO = new DesignationDTO();
designationDTO.setCode(fCode);
designationDTO.setTitle(fTitle);
return designationDTO;
}
}
randomAccessFile.close();
throw new DAOException("Invalid code: "+code);
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}

}
public DesignationDTOInterface getByTitle(String title) throws DAOException
{
title = title.trim();
if(title==null || title.length()==0) throw new DAOException("Invalid title: "+title);
try
{
File file = new File(FILE_NAME);
if(!file.exists()) throw new DAOException("File does not exist.");
RandomAccessFile randomAccessFile;
randomAccessFile  = new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("Invalid title: "+title);
}
//lastGeneratedCode, and recordCount
randomAccessFile.readLine();
int recordCount = Integer.parseInt(randomAccessFile.readLine().trim());
if(recordCount == 0)
{
randomAccessFile.close();
throw new DAOException("Invalid title: "+title);
}
int fCode;
String fTitle;
while(randomAccessFile.getFilePointer() < randomAccessFile.length())
{
fCode = Integer.parseInt(randomAccessFile.readLine());
fTitle = randomAccessFile.readLine();
if(fTitle.equalsIgnoreCase(title)) //found
{
randomAccessFile.close();
DesignationDTOInterface designationDTO;
designationDTO = new DesignationDTO();
designationDTO.setCode(fCode);
designationDTO.setTitle(fTitle);
return designationDTO;
}
}
randomAccessFile.close();
throw new DAOException("Invalid title: "+title);
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}

}//end of DesignationDAO class


