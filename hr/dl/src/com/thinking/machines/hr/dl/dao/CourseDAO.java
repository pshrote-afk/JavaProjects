package com.thinking.machines.hr.dl.dao;

import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import java.io.*;
import java.util.*;

public class CourseDAO implements CourseDAOInterface {
  private static final String FILE_NAME = "course.data";

  public void add(CourseDTOInterface courseDTO) throws DAOException {
    String title = courseDTO.getTitle();
    if (title == null) throw new DAOException("Title is null");
    title = title.trim();
    if (title.length() == 0) throw new DAOException("Length of title is zero");
    int lastGeneratedCode = 0;
    int recordCount = 0;
    String lastGeneratedCodeString = "";
    String recordCountString = "";
    try {
      File file = new File(FILE_NAME);
      RandomAccessFile randomAccessFile;
      randomAccessFile = new RandomAccessFile(file, "rw");
      // if file doesnt already exist, initialize file
      if (randomAccessFile.length() == 0) {
        // control enters means new file created
        randomAccessFile.writeBytes(String.format("%-10d", lastGeneratedCode) + "\n");
        randomAccessFile.writeBytes(String.format("%-10d", recordCount) + "\n");
      } else {
        lastGeneratedCodeString = randomAccessFile.readLine().trim();
        lastGeneratedCode = Integer.parseInt(lastGeneratedCodeString);
        recordCountString = randomAccessFile.readLine().trim();
        recordCount = Integer.parseInt(recordCountString);
      }
      int fCode;
      String fTitle;
      while (randomAccessFile.getFilePointer()
          < randomAccessFile.length()) // loop to find if title already exists
      {
        fCode = Integer.parseInt(randomAccessFile.readLine());
        fTitle = randomAccessFile.readLine();
        if (title.equalsIgnoreCase(fTitle)) {
          throw new DAOException("Title already exists against code: " + fCode);
        }
      }
      // control reaches here means title is unique
      lastGeneratedCode++;
      recordCount++;
      randomAccessFile.writeBytes(lastGeneratedCode + "\n");
      randomAccessFile.writeBytes(title + "\n");
      courseDTO.setCode(lastGeneratedCode);
      // now update header
      randomAccessFile.seek(0);
      randomAccessFile.writeBytes(String.format("%-10d", lastGeneratedCode) + "\n");
      randomAccessFile.writeBytes(String.format("%-10d", recordCount) + "\n");
      randomAccessFile.close();
    } catch (IOException ioException) {
      throw new DAOException(ioException.getMessage());
    }
  }

  public void update(CourseDTOInterface courseDTO) throws DAOException {
    // validate incoming object
    int code = courseDTO.getCode();
    if (code <= 0) throw new DAOException("Invalid code: " + code);
    String title = courseDTO.getTitle();
    if (title == null) throw new DAOException("Title is null");
    title = title.trim();
    if (title.length() == 0) throw new DAOException("Length of title is zero");
    try {
      File file = new File(FILE_NAME);
      RandomAccessFile randomAccessFile;
      randomAccessFile = new RandomAccessFile(file, "rw");
      randomAccessFile.readLine();
      randomAccessFile.readLine();
      int fCode;
      String fTitle;
      boolean found = false;
      long foundAt = 0;
      while (randomAccessFile.getFilePointer() < randomAccessFile.length()) {
        foundAt = randomAccessFile.getFilePointer();
        fCode = Integer.parseInt(randomAccessFile.readLine());
        fTitle = randomAccessFile.readLine();
        if (fCode == code) {
          found = true;
          break;
        }
      }
      if (found == false) {
        throw new DAOException("Invalid code: " + code);
      }
      // copy data in tmp file. then go to foundAt position, and update. then copy tmp to original
      File tmpFile = new File("course.tmp");
      if (tmpFile.exists()) tmpFile.delete();
      RandomAccessFile tmpRandomAccessFile;
      tmpRandomAccessFile = new RandomAccessFile(tmpFile, "rw");
      while (randomAccessFile.getFilePointer() < randomAccessFile.length()) {
        tmpRandomAccessFile.writeBytes(randomAccessFile.readLine() + "\n");
      }
      randomAccessFile.seek(foundAt);
      randomAccessFile.writeBytes(code + "\n");
      randomAccessFile.writeBytes(title + "\n");

      tmpRandomAccessFile.seek(0);
      while (tmpRandomAccessFile.getFilePointer() < tmpRandomAccessFile.length()) {
        randomAccessFile.writeBytes(tmpRandomAccessFile.readLine() + "\n");
      }
      randomAccessFile.setLength(randomAccessFile.getFilePointer());
      randomAccessFile.close();
      tmpRandomAccessFile.close();
    } catch (IOException ioException) {
      throw new DAOException(ioException.getMessage());
    }
  }

  public void delete(int code) throws DAOException {
    // validate incoming code
    if (code <= 0) throw new DAOException("Invalid code: " + code);
    try {
      File file = new File(FILE_NAME);
      RandomAccessFile randomAccessFile;
      randomAccessFile = new RandomAccessFile(file, "rw");
      randomAccessFile.readLine();
      randomAccessFile.readLine();
      int fCode;
      String fTitle;
      boolean found = false;
      long foundAt = 0;
      while (randomAccessFile.getFilePointer() < randomAccessFile.length()) {
        foundAt = randomAccessFile.getFilePointer();
        fCode = Integer.parseInt(randomAccessFile.readLine());
        fTitle = randomAccessFile.readLine();
        if (fCode == code) {
          found = true;
          break;
        }
      }
      if (found == false) {
        throw new DAOException("Invalid code: " + code);
      }
      // copy data in tmp file. then go to foundAt position, and delete. then copy tmp to original
      File tmpFile = new File("course.tmp");
      if (tmpFile.exists()) tmpFile.delete();
      RandomAccessFile tmpRandomAccessFile;
      tmpRandomAccessFile = new RandomAccessFile(tmpFile, "rw");
      while (randomAccessFile.getFilePointer() < randomAccessFile.length()) {
        tmpRandomAccessFile.writeBytes(randomAccessFile.readLine() + "\n");
      }
      randomAccessFile.seek(foundAt);
      tmpRandomAccessFile.seek(0);
      while (tmpRandomAccessFile.getFilePointer() < tmpRandomAccessFile.length()) {
        randomAccessFile.writeBytes(tmpRandomAccessFile.readLine() + "\n");
      }
      randomAccessFile.setLength(randomAccessFile.getFilePointer());
      // update header after deletion
      randomAccessFile.seek(0);
      randomAccessFile.readLine();
      foundAt = randomAccessFile.getFilePointer();
      int recordCount = Integer.parseInt(randomAccessFile.readLine().trim());
      recordCount--;
      randomAccessFile.seek(foundAt);
      randomAccessFile.writeBytes(String.format("%-10d", recordCount) + "\n");
      randomAccessFile.close();
      tmpRandomAccessFile.close();
    } catch (IOException ioException) {
      throw new DAOException(ioException.getMessage());
    }
  }

  public Set<CourseDTOInterface> getAll() throws DAOException {
    TreeSet<CourseDTOInterface> treeSet1 = new TreeSet<>();
    try {
      File file = new File(FILE_NAME);
      if (file.exists() == false) return treeSet1;
      RandomAccessFile randomAccessFile;
      randomAccessFile = new RandomAccessFile(file, "rw");
      if (file.length() == 0) {
        randomAccessFile.close();
        return treeSet1;
      }
      randomAccessFile.readLine(); // read header
      randomAccessFile.readLine();
      int fCode;
      String fTitle;
      CourseDTOInterface courseDTO;
      while (randomAccessFile.getFilePointer() < randomAccessFile.length()) {
        fCode = Integer.parseInt(randomAccessFile.readLine());
        fTitle = randomAccessFile.readLine();
        courseDTO = new CourseDTO();
        courseDTO.setCode(fCode);
        courseDTO.setTitle(fTitle);
        treeSet1.add(courseDTO);
      }
      randomAccessFile.close();
      return treeSet1;
    } catch (IOException ioException) {
      throw new DAOException(ioException.getMessage());
    }
  }

  public CourseDTOInterface getByCode(int code) throws DAOException {
    if (code <= 0) throw new DAOException("Invalid code: " + code);
    try {
      File file = new File(FILE_NAME);
      if (!(file.exists())) throw new DAOException("Invalid code: " + code);
      RandomAccessFile randomAccessFile;
      randomAccessFile = new RandomAccessFile(file, "rw");
      randomAccessFile.readLine();
      randomAccessFile.readLine();
      int fCode;
      String fTitle;
      while (randomAccessFile.getFilePointer() < randomAccessFile.length()) // loop to find code
      {
        fCode = Integer.parseInt(randomAccessFile.readLine());
        fTitle = randomAccessFile.readLine();
        if (fCode == code) {
          // wrap
          CourseDTOInterface courseDTO;
          courseDTO = new CourseDTO();
          courseDTO.setCode(fCode);
          courseDTO.setTitle(fTitle);
          randomAccessFile.close();
          return courseDTO;
        }
      }
      randomAccessFile.close();
      throw new DAOException("Invalid code: " + code);
    } catch (IOException ioException) {
      throw new DAOException(ioException.getMessage());
    }
  }

  public CourseDTOInterface getByTitle(String title) throws DAOException {
    if (title == null) throw new DAOException("Title is null");
    title = title.trim();
    if (title.length() == 0) throw new DAOException("Length of title is zero");
    try {
      File file = new File(FILE_NAME);
      if (!(file.exists())) throw new DAOException("Invalid title: " + title);
      RandomAccessFile randomAccessFile;
      randomAccessFile = new RandomAccessFile(file, "rw");
      randomAccessFile.readLine();
      randomAccessFile.readLine();
      int fCode;
      String fTitle;
      while (randomAccessFile.getFilePointer() < randomAccessFile.length()) // loop to find code
      {
        fCode = Integer.parseInt(randomAccessFile.readLine());
        fTitle = randomAccessFile.readLine();
        if (title.equalsIgnoreCase(fTitle)) {
          // wrap
          CourseDTOInterface courseDTO;
          courseDTO = new CourseDTO();
          courseDTO.setCode(fCode);
          courseDTO.setTitle(fTitle);
          randomAccessFile.close();
          return courseDTO;
        }
      }
      randomAccessFile.close();
      throw new DAOException("Invalid title: " + title);
    } catch (IOException ioException) {
      throw new DAOException(ioException.getMessage());
    }
  }

  public boolean codeExists(int code) throws DAOException {
    if (code <= 0) return false;
    try {
      File file = new File(FILE_NAME);
      if (!(file.exists())) return false;
      RandomAccessFile randomAccessFile;
      randomAccessFile = new RandomAccessFile(file, "rw");
      randomAccessFile.readLine();
      randomAccessFile.readLine();
      int fCode;
      String fTitle;
      while (randomAccessFile.getFilePointer() < randomAccessFile.length()) // loop to find code
      {
        fCode = Integer.parseInt(randomAccessFile.readLine());
        fTitle = randomAccessFile.readLine();
        if (fCode == code) {
          return true;
        }
      }
      randomAccessFile.close();
      return false;
    } catch (IOException ioException) {
      throw new DAOException(ioException.getMessage());
    }
  }

  public boolean titleExists(String title) throws DAOException {
    if (title == null) return false;
    title = title.trim();
    if (title.length() == 0) return false;
    try {
      File file = new File(FILE_NAME);
      if (!(file.exists())) return false;
      RandomAccessFile randomAccessFile;
      randomAccessFile = new RandomAccessFile(file, "rw");
      randomAccessFile.readLine();
      randomAccessFile.readLine();
      int fCode;
      String fTitle;
      while (randomAccessFile.getFilePointer() < randomAccessFile.length()) // loop to find title
      {
        fCode = Integer.parseInt(randomAccessFile.readLine());
        fTitle = randomAccessFile.readLine();
        if (title.equalsIgnoreCase(fTitle)) {
          return true;
        }
      }
      randomAccessFile.close();
      return false;
    } catch (IOException ioException) {
      throw new DAOException(ioException.getMessage());
    }
  }

  public int getCount() throws DAOException {
    try {
      File file = new File(FILE_NAME);
      if (!(file.exists())) return 0;
      RandomAccessFile randomAccessFile;
      randomAccessFile = new RandomAccessFile(file, "rw");
      randomAccessFile.readLine();
      int recordCount = Integer.parseInt(randomAccessFile.readLine().trim());
      return recordCount;
    } catch (IOException ioException) {
      throw new DAOException(ioException.getMessage());
    }
  }
}
