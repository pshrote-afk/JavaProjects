package com.thinking.machines.hr.dl.dao;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.exceptions.*;
import java.util.*;
import java.io.*;
public class CourseDAO implements CourseDAOInterface
{
private static final String FILE_NAME = "course.data";
public void add(CourseDTOInterface courseDTO) throws DAOException
{
String title = courseDTO.getTitle();
if(title==null) throw new DAOException("Title is null");
title=title.trim();
if(title.length()==0) throw new DAOException("Length of title is zero");
int lastGeneratedCode=0;
int recordCount=0;
String lastGeneratedCodeString="";
String recordCountString="";
try
{
File file = new File(FILE_NAME);
RandomAccessFile randomAccessFile;
randomAccessFile = new RandomAccessFile(file,"rw");
//if file doesnt already exist, initialize file
if(file.length()==0)
{
//control enters means new file created
randomAccessFile.writeBytes(String.format("%-10d",lastGeneratedCode) + "\n");
randomAccessFile.writeBytes(String.format("%-10d",recordCount) + "\n");
}
else
{
lastGeneratedCodeString = randomAccessFile.readLine().trim();
lastGeneratedCode = Integer.parseInt(lastGeneratedCodeString);
recordCountString = randomAccessFile.readLine().trim();
recordCount = Integer.parseInt(recordCountString);
}
int fCode;
String fTitle;
while(randomAccessFile.getFilePointer() < randomAccessFile.length()) //loop to find if title already exists
{
fCode = Integer.parseInt(randomAccessFile.readLine());
fTitle = randomAccessFile.readLine();
if(title.equalsIgnoreCase(fTitle))
{
throw new DAOException("Title already exists against code: "+fCode);
}
}
//control reaches here means title is unique
lastGeneratedCode++;
recordCount++;
randomAccessFile.writeBytes(lastGeneratedCode + "\n");
randomAccessFile.writeBytes(title + "\n");
//now update header
randomAccessFile.seek(0);
randomAccessFile.writeBytes(String.format("%-10d",lastGeneratedCode) + "\n");
randomAccessFile.writeBytes(String.format("%-10d",recordCount) + "\n");
randomAccessFile.close();
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public void update(int code) throws DAOException
{
throw new DAOException("not yet implemented");
}
public void delete(int code) throws DAOException
{
throw new DAOException("not yet implemented");
}
public Set<CourseDTOInterface> getAll() throws DAOException
{
TreeSet<CourseDTOInterface> treeSet1 = new TreeSet<>();
try
{
File file = new File(FILE_NAME);
RandomAccessFile randomAccessFile;
randomAccessFile = new RandomAccessFile(file,"rw");
if(file.length()==0)
{
throw new DAOException("File is empty");
}
randomAccessFile.readLine(); //read header
randomAccessFile.readLine();
int fCode;
String fTitle;
CourseDTOInterface courseDTO;
while(randomAccessFile.getFilePointer() < randomAccessFile.length())
{
fCode = Integer.parseInt(randomAccessFile.readLine());
fTitle = randomAccessFile.readLine();
courseDTO = new CourseDTO();
courseDTO.setCode(fCode);
courseDTO.setTitle(fTitle);
treeSet1.add(courseDTO);
}
randomAccessFile.close();
return treeSet1;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public CourseDTOInterface getByCourseCode(int code) throws DAOException
{
throw new DAOException("not yet implemented");
}
public CourseDTOInterface getByCourseTitle(String title) throws DAOException
{
throw new DAOException("not yet implemented");
}
public boolean codeExists(int code) throws DAOException
{
throw new DAOException("not yet implemented");
}
public boolean titleExists(String title) throws DAOException
{
throw new DAOException("not yet implemented");
}
public int getCount() throws DAOException
{
throw new DAOException("not yet implemented");
}
}