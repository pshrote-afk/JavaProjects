package com.thinking.machines.hr.dl.dao;

import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import java.io.*;
import java.sql.*;
import java.util.*;

public class CourseDAO implements CourseDAOInterface {
  private static final String FILE_NAME = "course.data";

  public void add(CourseDTOInterface courseDTO) throws DAOException {
    if (courseDTO == null) throw new DAOException("Course is null");
    String title = courseDTO.getTitle();
    if (title == null) throw new DAOException("Title is null");
    title = title.trim();
    if (title.length() == 0) throw new DAOException("Length of title is zero");
    Connection connection = DAOConnection.getConnection();
    try {
      PreparedStatement preparedStatement =
          connection.prepareStatement("SELECT code FROM course WHERE title=?");
      preparedStatement.setString(1, title);
      ResultSet resultSet;
      resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        resultSet.close();
        preparedStatement.close();
        connection.close();
        throw new DAOException("Course title already exists");
      }
      resultSet.close();
      preparedStatement.close();
      preparedStatement =
          connection.prepareStatement(
              "INSERT INTO course (title) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
      preparedStatement.setString(1, title);
      preparedStatement.executeUpdate();
      resultSet = preparedStatement.getGeneratedKeys();
      resultSet.next();
      int code = resultSet.getInt(1);
      resultSet.close();
      preparedStatement.close();
      connection.close();
      courseDTO.setCode(code);
    } catch (SQLException sqlException) {
      throw new DAOException(sqlException.getMessage());
    }
  }

  public void update(CourseDTOInterface courseDTO) throws DAOException {
    int code = courseDTO.getCode();
    String title = courseDTO.getTitle();
    try {
      Connection connection = DAOConnection.getConnection();
      PreparedStatement preparedStatement =
          connection.prepareStatement("SELECT code FROM course WHERE code=?");
      preparedStatement.setInt(1, code);
      ResultSet resultSet = preparedStatement.executeQuery();
      if (resultSet.next() == false) {
        connection.close();
        preparedStatement.close();
        resultSet.close();
        throw new DAOException("Invalid course code: " + code);
      }
      preparedStatement =
          connection.prepareStatement("SELECT code FROM course WHERE title=? AND CODE!=?");
      preparedStatement.setString(1, title);
      preparedStatement.setInt(2, code);
      resultSet = preparedStatement.executeQuery();
      if (resultSet.next() == true) {
        connection.close();
        preparedStatement.close();
        resultSet.close();
        throw new DAOException("Course already exists: " + title);
      }
      resultSet.close();
      preparedStatement.close();
      preparedStatement = connection.prepareStatement("UPDATE course SET title=? WHERE code=?");
      preparedStatement.setString(1, title);
      preparedStatement.setInt(2, code);
      preparedStatement.executeUpdate();
      preparedStatement.close();
      connection.close();
    } catch (Exception exception) {
      throw new DAOException(exception.getMessage());
    }
  }

  public void delete(int code) throws DAOException {
    try {
      Connection connection = DAOConnection.getConnection();
      PreparedStatement preparedStatement =
          connection.prepareStatement("SELECT code FROM course WHERE code=?");
      preparedStatement.setInt(1, code);
      ResultSet resultSet = preparedStatement.executeQuery();
      if (resultSet.next() == false) {
        connection.close();
        preparedStatement.close();
        resultSet.close();
        throw new DAOException("Invalid course code: " + code);
      }

      resultSet.close();
      preparedStatement.close();
      preparedStatement = connection.prepareStatement("DELETE FROM course WHERE code=?");
      preparedStatement.setInt(1, code);
      preparedStatement.executeUpdate();
      preparedStatement.close();
      connection.close();
    } catch (Exception exception) {
      throw new DAOException(exception.getMessage());
    }
  }

  public Set<CourseDTOInterface> getAll() throws DAOException {
    Set<CourseDTOInterface> courses = new TreeSet<>();
    CourseDTOInterface courseDTO;
    int code;
    String title;
    try {
      Connection connection = DAOConnection.getConnection();
      PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM course");
      ResultSet resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        code = resultSet.getInt("code");
        title = resultSet.getString("title");
        courseDTO = new CourseDTO();
        courseDTO.setCode(code);
        courseDTO.setTitle(title);
        courses.add(courseDTO);
      }
      resultSet.close();
      preparedStatement.close();
      connection.close();
      return courses;
    } catch (Exception exception) {
      throw new DAOException(exception.getMessage());
    }
  }

  public CourseDTOInterface getByCode(int code) throws DAOException {
    CourseDTOInterface courseDTO;
    String title;
    try {
      Connection connection = DAOConnection.getConnection();
      PreparedStatement preparedStatement =
          connection.prepareStatement("SELECT * FROM course WHERE code=?");
      preparedStatement.setInt(1, code);
      ResultSet resultSet = preparedStatement.executeQuery();

      if (resultSet.next() == false) {
        connection.close();
        preparedStatement.close();
        resultSet.close();
        throw new DAOException("Invalid course code: " + code);
      }
      code = resultSet.getInt("code");
      title = resultSet.getString("title");
      courseDTO = new CourseDTO();
      courseDTO.setCode(code);
      courseDTO.setTitle(title);
      resultSet.close();
      preparedStatement.close();
      connection.close();
      return courseDTO;
    } catch (Exception exception) {
      throw new DAOException(exception.getMessage());
    }
  }

  public CourseDTOInterface getByTitle(String title) throws DAOException {
    CourseDTOInterface courseDTO;
    int code;
    try {
      Connection connection = DAOConnection.getConnection();
      PreparedStatement preparedStatement =
          connection.prepareStatement("SELECT * FROM course WHERE title=?");
      preparedStatement.setString(1, title);
      ResultSet resultSet = preparedStatement.executeQuery();

      if (resultSet.next() == false) {
        connection.close();
        preparedStatement.close();
        resultSet.close();
        throw new DAOException("Invalid course title: " + title);
      }
      code = resultSet.getInt("code");
      title = resultSet.getString("title");
      courseDTO = new CourseDTO();
      courseDTO.setCode(code);
      courseDTO.setTitle(title);
      resultSet.close();
      preparedStatement.close();
      connection.close();
      return courseDTO;
    } catch (Exception exception) {
      throw new DAOException(exception.getMessage());
    }
  }

  public boolean codeExists(int code) throws DAOException {
    try {
      Connection connection = DAOConnection.getConnection();
      PreparedStatement preparedStatement =
          connection.prepareStatement("SELECT code FROM course WHERE code=?");
      preparedStatement.setInt(1, code);
      ResultSet resultSet = preparedStatement.executeQuery();

      boolean codeExists = resultSet.next();

      resultSet.close();
      preparedStatement.close();
      connection.close();
      return codeExists;
    } catch (Exception exception) {
      throw new DAOException(exception.getMessage());
    }
  }

  public boolean titleExists(String title) throws DAOException {
    try {
      Connection connection = DAOConnection.getConnection();
      PreparedStatement preparedStatement =
          connection.prepareStatement("SELECT code FROM course WHERE title=?");
      preparedStatement.setString(1, title);
      ResultSet resultSet = preparedStatement.executeQuery();

      boolean titleExists = resultSet.next();

      resultSet.close();
      preparedStatement.close();
      connection.close();
      return titleExists;
    } catch (Exception exception) {
      throw new DAOException(exception.getMessage());
    }
  }

  public int getCount() throws DAOException {
    int count = 0;
    try {
      Connection connection = DAOConnection.getConnection();
      PreparedStatement preparedStatement =
          connection.prepareStatement("SELECT COUNT(code) FROM course");
      ResultSet resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        count = resultSet.getInt(1);
      }
      resultSet.close();
      preparedStatement.close();
      connection.close();
      return count;
    } catch (Exception exception) {
      throw new DAOException(exception.getMessage());
    }
  }
} // end of class
