package com.thinking.machines.hr.dl.dao;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.exceptions.*;

import java.util.*;
import java.io.*;
import java.sql.*;
public class CourseDAO implements CourseDAOInterface
{
private static final String FILE_NAME = "course.data";
public void add(CourseDTOInterface courseDTO) throws DAOException
{
if(courseDTO==null) throw new DAOException("Course is null");
String title = courseDTO.getTitle();
if(title==null) throw new DAOException("Title is null");
title=title.trim();
if(title.length()==0) throw new DAOException("Length of title is zero");
Connection connection = DAOConnection.getConnection();
try
{
PreparedStatement prepareStatement = connection.prepareStatement("SELECT * FROM course WHERE title=?");
prepareStatement.setString(1,title);
ResultSet resultSet;
resultSet = prepareStatement.executeQuery();
if(resultSet.next())
{
resultSet.close();
prepareStatement.close();
connection.close();
throw new DAOException("Course title already exists");
}
resultSet.close();
prepareStatement.close();
prepareStatement = connection.prepareStatement("INSERT INTO course (title) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
prepareStatement.setString(1,title);
prepareStatement.executeUpdate();
resultSet = prepareStatement.getGeneratedKeys();
resultSet.next();
int code = resultSet.getInt(1);
resultSet.close();
prepareStatement.close();
connection.close();
courseDTO.setCode(code);
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
public void update(CourseDTOInterface courseDTO) throws DAOException
{
//validate incoming object
int code = courseDTO.getCode();
if(code<=0) throw new DAOException("Invalid code: "+code);
String title = courseDTO.getTitle();
if(title==null) throw new DAOException("Title is null");
title=title.trim();
if(title.length()==0) throw new DAOException("Length of title is zero");
try
{
File file = new File(FILE_NAME);
RandomAccessFile randomAccessFile;
randomAccessFile = new RandomAccessFile(file,"rw");
randomAccessFile.readLine();
randomAccessFile.readLine();
int fCode;
String fTitle;
boolean found = false;
long foundAt=0;
while(randomAccessFile.getFilePointer() < randomAccessFile.length())
{
foundAt = randomAccessFile.getFilePointer();
fCode = Integer.parseInt(randomAccessFile.readLine());
fTitle = randomAccessFile.readLine();
if(fCode==code)
{
found=true;
break;
}
}
if(found==false)
{
throw new DAOException("Invalid code: "+code);
}
//copy data in tmp file. then go to foundAt position, and update. then copy tmp to original
File tmpFile = new File("course.tmp");
if(tmpFile.exists()) tmpFile.delete();
RandomAccessFile tmpRandomAccessFile;
tmpRandomAccessFile = new RandomAccessFile(tmpFile,"rw");
while(randomAccessFile.getFilePointer() < randomAccessFile.length())
{
tmpRandomAccessFile.writeBytes(randomAccessFile.readLine() + "\n");
}
randomAccessFile.seek(foundAt);
randomAccessFile.writeBytes(code + "\n");
randomAccessFile.writeBytes(title + "\n");

tmpRandomAccessFile.seek(0);
while(tmpRandomAccessFile.getFilePointer() < tmpRandomAccessFile.length())
{
randomAccessFile.writeBytes(tmpRandomAccessFile.readLine() + "\n");
}
randomAccessFile.setLength(randomAccessFile.getFilePointer());
randomAccessFile.close();
tmpRandomAccessFile.close();
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public void delete(int code) throws DAOException
{
//validate incoming code
if(code<=0) throw new DAOException("Invalid code: "+code);
try
{
File file = new File(FILE_NAME);
RandomAccessFile randomAccessFile;
randomAccessFile = new RandomAccessFile(file,"rw");
randomAccessFile.readLine();
randomAccessFile.readLine();
int fCode;
String fTitle;
boolean found = false;
long foundAt=0;
while(randomAccessFile.getFilePointer() < randomAccessFile.length())
{
foundAt = randomAccessFile.getFilePointer();
fCode = Integer.parseInt(randomAccessFile.readLine());
fTitle = randomAccessFile.readLine();
if(fCode==code)
{
found=true;
break;
}
}
if(found==false)
{
throw new DAOException("Invalid code: "+code);
}
//copy data in tmp file. then go to foundAt position, and delete. then copy tmp to original
File tmpFile = new File("course.tmp");
if(tmpFile.exists()) tmpFile.delete();
RandomAccessFile tmpRandomAccessFile;
tmpRandomAccessFile = new RandomAccessFile(tmpFile,"rw");
while(randomAccessFile.getFilePointer() < randomAccessFile.length())
{
tmpRandomAccessFile.writeBytes(randomAccessFile.readLine() + "\n");
}
randomAccessFile.seek(foundAt);
tmpRandomAccessFile.seek(0);
while(tmpRandomAccessFile.getFilePointer() < tmpRandomAccessFile.length())
{
randomAccessFile.writeBytes(tmpRandomAccessFile.readLine() + "\n");
}
randomAccessFile.setLength(randomAccessFile.getFilePointer());
//update header after deletion
randomAccessFile.seek(0);
randomAccessFile.readLine();
foundAt=randomAccessFile.getFilePointer();
int recordCount = Integer.parseInt(randomAccessFile.readLine().trim());
recordCount--;
randomAccessFile.seek(foundAt);
randomAccessFile.writeBytes(String.format("%-10d",recordCount) + "\n");
randomAccessFile.close();
tmpRandomAccessFile.close();
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public Set<CourseDTOInterface> getAll() throws DAOException
{
TreeSet<CourseDTOInterface> treeSet1 = new TreeSet<>();
try
{
Connection connection = DAOConnection.getConnection();
PreparedStatement prepareStatement = connection.prepareStatement("SELECT * FROM course");
ResultSet resultSet = prepareStatement.executeQuery();
CourseDTOInterface courseDTO;
while(resultSet.next())
{
courseDTO = new CourseDTO();
courseDTO.setCode(resultSet.getInt("code"));
courseDTO.setTitle(resultSet.getString("title").trim());
treeSet1.add(courseDTO);
}
connection.close();
prepareStatement.close();
resultSet.close();
return treeSet1;
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
public CourseDTOInterface getByCode(int code) throws DAOException
{
if(code<=0) throw new DAOException("Invalid code: "+code);
try
{
File file = new File(FILE_NAME);
if(!(file.exists())) throw new DAOException("Invalid code: "+code);
RandomAccessFile randomAccessFile;
randomAccessFile = new RandomAccessFile(file,"rw");
randomAccessFile.readLine();
randomAccessFile.readLine();
int fCode;
String fTitle;
while(randomAccessFile.getFilePointer() < randomAccessFile.length()) //loop to find code
{
fCode = Integer.parseInt(randomAccessFile.readLine());
fTitle = randomAccessFile.readLine();
if(fCode==code)
{
//wrap
CourseDTOInterface courseDTO;
courseDTO = new CourseDTO();
courseDTO.setCode(fCode);
courseDTO.setTitle(fTitle);
randomAccessFile.close();
return courseDTO;
}
}
randomAccessFile.close();
throw new DAOException("Invalid code: "+code);
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public CourseDTOInterface getByTitle(String title) throws DAOException
{
if(title==null) throw new DAOException("Title is null");
title=title.trim();
if(title.length()==0) throw new DAOException("Length of title is zero");
try
{
File file = new File(FILE_NAME);
if(!(file.exists())) throw new DAOException("Invalid title: "+title);
RandomAccessFile randomAccessFile;
randomAccessFile = new RandomAccessFile(file,"rw");
randomAccessFile.readLine();
randomAccessFile.readLine();
int fCode;
String fTitle;
while(randomAccessFile.getFilePointer() < randomAccessFile.length()) //loop to find code
{
fCode = Integer.parseInt(randomAccessFile.readLine());
fTitle = randomAccessFile.readLine();
if(title.equalsIgnoreCase(fTitle))
{
//wrap
CourseDTOInterface courseDTO;
courseDTO = new CourseDTO();
courseDTO.setCode(fCode);
courseDTO.setTitle(fTitle);
randomAccessFile.close();
return courseDTO;
}
}
randomAccessFile.close();
throw new DAOException("Invalid title: "+title);
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public boolean codeExists(int code) throws DAOException
{
if(code<=0) return false;
try
{
File file = new File(FILE_NAME);
if(!(file.exists())) return false;
RandomAccessFile randomAccessFile;
randomAccessFile = new RandomAccessFile(file,"rw");
randomAccessFile.readLine();
randomAccessFile.readLine();
int fCode;
String fTitle;
while(randomAccessFile.getFilePointer() < randomAccessFile.length()) //loop to find code
{
fCode = Integer.parseInt(randomAccessFile.readLine());
fTitle = randomAccessFile.readLine();
if(fCode==code)
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
public boolean titleExists(String title) throws DAOException
{
if(title==null) return false;
title=title.trim();
if(title.length()==0) return false;
try
{
File file = new File(FILE_NAME);
if(!(file.exists())) return false;
RandomAccessFile randomAccessFile;
randomAccessFile = new RandomAccessFile(file,"rw");
randomAccessFile.readLine();
randomAccessFile.readLine();
int fCode;
String fTitle;
while(randomAccessFile.getFilePointer() < randomAccessFile.length()) //loop to find title
{
fCode = Integer.parseInt(randomAccessFile.readLine());
fTitle = randomAccessFile.readLine();
if(title.equalsIgnoreCase(fTitle))
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
if(!(file.exists())) return 0;
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
}