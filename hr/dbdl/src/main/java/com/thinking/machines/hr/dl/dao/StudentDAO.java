package com.thinking.machines.hr.dl.dao;

import com.thinking.machines.enums.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import java.io.*; // for RandomAccessFile class
import java.math.*; // for BigDecimal class
import java.sql.*;
import java.text.*; // for SimpleDateFormat class
import java.util.*; // for Set collection, and Date class

public class StudentDAO implements StudentDAOInterface {
  public void add(StudentDTOInterface studentDTO) throws DAOException {
    String enrollmentNumber = studentDTO.getEnrollmentNumber();
    String aadharCardNumber = studentDTO.getAadharCardNumber();
    try {
      Connection connection = DAOConnection.getConnection();
      PreparedStatement preparedStatement =
          connection.prepareStatement("SELECT roll_no FROM student WHERE enrollment_number=?");
      preparedStatement.setString(1, enrollmentNumber);
      ResultSet resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        resultSet.close();
        preparedStatement.close();
        connection.close();
        throw new DAOException("Enrollment number: " + enrollmentNumber + " already exists.");
      }
      resultSet.close();
      preparedStatement.close();
      preparedStatement =
          connection.prepareStatement("SELECT roll_no FROM student WHERE aadhar_card_number=?");
      preparedStatement.setString(1, aadharCardNumber);
      resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        resultSet.close();
        preparedStatement.close();
        connection.close();
        throw new DAOException("Aadhar card number: " + aadharCardNumber + " already exists.");
      }
      resultSet.close();
      preparedStatement.close();
      String name = studentDTO.getName();
      int courseCode = studentDTO.getCourseCode();
      java.util.Date dateOfBirth = studentDTO.getDateOfBirth();
      char gender = studentDTO.getGender();
      boolean isIndian = studentDTO.getIsIndian();
      BigDecimal fees = studentDTO.getFees();

      preparedStatement =
          connection.prepareStatement(
              "INSERT INTO student"
                  + " (name,course_code,date_of_birth,gender,is_indian,fees,enrollment_number,aadhar_card_number)"
                  + " VALUES (?,?,?,?,?,?,?,?)",
              Statement.RETURN_GENERATED_KEYS);
      preparedStatement.setString(1, name);
      preparedStatement.setInt(2, courseCode);
      java.sql.Date sqlDateOfBirth = new java.sql.Date(dateOfBirth.getTime());
      preparedStatement.setDate(
          3, sqlDateOfBirth); // won't accept java.util.Date. It wants java.sql.Date.
      preparedStatement.setString(4, String.valueOf(gender));
      preparedStatement.setBoolean(5, isIndian);
      preparedStatement.setBigDecimal(6, fees);
      preparedStatement.setString(7, enrollmentNumber);
      preparedStatement.setString(8, aadharCardNumber);
      preparedStatement.executeUpdate();
      resultSet = preparedStatement.getGeneratedKeys();
      resultSet.next();
      int rollNo = resultSet.getInt(1);
      studentDTO.setRollNo("A" + rollNo);
      resultSet.close();
      preparedStatement.close();
      connection.close();
    } catch (SQLException sqlException) {
      throw new DAOException(sqlException.getMessage());
    }
  }

  public void update(StudentDTOInterface studentDTO) throws DAOException {
    String rollNoString = studentDTO.getRollNo();
    if (rollNoString.charAt(0) == 'A' || rollNoString.charAt(0) == 'a')
      rollNoString = rollNoString.substring(1);
    int rollNo = Integer.parseInt(rollNoString);
    String enrollmentNumber = studentDTO.getEnrollmentNumber();
    String aadharCardNumber = studentDTO.getAadharCardNumber();
    try {
      Connection connection = DAOConnection.getConnection();
      PreparedStatement preparedStatement =
          connection.prepareStatement(
              "SELECT roll_no FROM student WHERE enrollment_number=? AND roll_no!=?");
      preparedStatement.setString(1, enrollmentNumber);
      preparedStatement.setInt(2, rollNo);
      ResultSet resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        resultSet.close();
        preparedStatement.close();
        connection.close();
        throw new DAOException("Enrollment number: " + enrollmentNumber + " already exists.");
      }
      resultSet.close();
      preparedStatement.close();
      preparedStatement =
          connection.prepareStatement(
              "SELECT roll_no FROM student WHERE aadhar_card_number=? AND roll_no!=?");
      preparedStatement.setString(1, aadharCardNumber);
      preparedStatement.setInt(2, rollNo);
      resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        resultSet.close();
        preparedStatement.close();
        connection.close();
        throw new DAOException("Aadhar card number: " + aadharCardNumber + " already exists.");
      }
      resultSet.close();
      preparedStatement.close();
      String name = studentDTO.getName();
      int courseCode = studentDTO.getCourseCode();
      java.util.Date dateOfBirth = studentDTO.getDateOfBirth();
      char gender = studentDTO.getGender();
      boolean isIndian = studentDTO.getIsIndian();
      BigDecimal fees = studentDTO.getFees();

      preparedStatement =
          connection.prepareStatement(
              "UPDATE student SET"
                  + " name=?,course_code=?,date_of_birth=?,gender=?,is_indian=?,fees=?,enrollment_number=?,aadhar_card_number=?"
                  + " WHERE roll_no=?");
      preparedStatement.setString(1, name);
      preparedStatement.setInt(2, courseCode);
      java.sql.Date sqlDateOfBirth = new java.sql.Date(dateOfBirth.getTime());
      preparedStatement.setDate(3, sqlDateOfBirth);
      preparedStatement.setString(4, String.valueOf(gender));
      preparedStatement.setBoolean(5, isIndian);
      preparedStatement.setBigDecimal(6, fees);
      preparedStatement.setString(7, enrollmentNumber);
      preparedStatement.setString(8, aadharCardNumber);
      preparedStatement.setInt(9, rollNo);

      preparedStatement.executeUpdate();
      preparedStatement.close();
      connection.close();
    } catch (Exception exception) {
      throw new DAOException(exception.getMessage());
    }
  }

  public void delete(String rollNo) throws DAOException {
    String rollNoString = rollNo;
    if (rollNoString.charAt(0) == 'A' || rollNoString.charAt(0) == 'a')
      rollNoString = rollNoString.substring(1);
    int intRollNo = Integer.parseInt(rollNoString);
    try {
      Connection connection = DAOConnection.getConnection();
      PreparedStatement preparedStatement =
          connection.prepareStatement("SELECT roll_no FROM student WHERE roll_no=?");
      preparedStatement.setInt(1, intRollNo);
      ResultSet resultSet = preparedStatement.executeQuery();
      if (resultSet.next() == false) {
        resultSet.close();
        preparedStatement.close();
        connection.close();
        throw new DAOException("Invalid roll no " + rollNo);
      }
      resultSet.close();
      preparedStatement.close();
      preparedStatement = connection.prepareStatement("DELETE FROM student WHERE roll_no=?");
      preparedStatement.setInt(1, intRollNo);
      preparedStatement.executeUpdate();
      preparedStatement.close();
      connection.close();
    } catch (Exception exception) {
      throw new DAOException(exception.getMessage());
    }
  }

  public Set<StudentDTOInterface> getByCourseCode(int code) throws DAOException {
    Set<StudentDTOInterface> students;
    students = new TreeSet<>(); // because we want to maintain order of insertion
    try {
      Connection connection = DAOConnection.getConnection();
      PreparedStatement preparedStatement =
          connection.prepareStatement(
              "SELECT"
                  + " student.roll_no,student.name,student.course_code,course.title,student.date_of_birth,student.gender,student.is_indian,student.fees,student.enrollment_number,student.aadhar_card_number"
                  + " FROM student INNER JOIN course ON student.course_code=course.code WHERE"
                  + " student.course_code=? ORDER BY student.roll_no");
      preparedStatement.setInt(1, code);
      ResultSet resultSet;
      resultSet = preparedStatement.executeQuery();

      int rollNo;
      String name;
      int courseCode;
      String title;
      java.sql.Date dateOfBirth; // cause we're receiving Date object from an sql database
      String gender;
      boolean isIndian;
      BigDecimal fees;
      String enrollmentNumber;
      String aadharCardNumber;
      StudentDTO studentDTO;
      while (resultSet.next()) {
        rollNo = resultSet.getInt("roll_no");
        name = resultSet.getString("name").trim();
        courseCode = resultSet.getInt("course_code");
        title = resultSet.getString("title").trim();
        dateOfBirth = resultSet.getDate("date_of_birth");
        gender = resultSet.getString("gender");
        isIndian = resultSet.getBoolean("is_indian");
        fees = resultSet.getBigDecimal("fees");
        enrollmentNumber = resultSet.getString("enrollment_number").trim();
        aadharCardNumber = resultSet.getString("aadhar_card_number").trim();
        studentDTO = new StudentDTO();
        studentDTO.setRollNo("A" + rollNo);
        studentDTO.setName(name);
        studentDTO.setCourseCode(courseCode);
        studentDTO.setTitle(title);
        studentDTO.setDateOfBirth(dateOfBirth);
        studentDTO.setGender(gender.charAt(0) == 'M' ? GENDER.MALE : GENDER.FEMALE);
        studentDTO.setIsIndian(isIndian);
        studentDTO.setFees(fees);
        studentDTO.setEnrollmentNumber(enrollmentNumber);
        studentDTO.setAadharCardNumber(aadharCardNumber);
        students.add(studentDTO);
      }
      resultSet.close();
      preparedStatement.close();
      connection.close();
      return students;
    } catch (Exception e) {
      throw new DAOException(e.getMessage());
    }
  }

  public Set<StudentDTOInterface> getAll() throws DAOException {
    Set<StudentDTOInterface> students;
    students = new TreeSet<>(); // because we want to maintain order of insertion
    try {
      Connection connection = DAOConnection.getConnection();
      PreparedStatement preparedStatement =
          connection.prepareStatement(
              "SELECT"
                  + " student.roll_no,student.name,student.course_code,course.title,student.date_of_birth,student.gender,student.is_indian,student.fees,student.enrollment_number,student.aadhar_card_number"
                  + " FROM student INNER JOIN course ON student.course_code=course.code ORDER BY"
                  + " student.roll_no");
      ResultSet resultSet;
      resultSet = preparedStatement.executeQuery();

      int rollNo;
      String name;
      int courseCode;
      String title;
      java.sql.Date dateOfBirth; // cause we're receiving Date object from an sql database
      String gender;
      boolean isIndian;
      BigDecimal fees;
      String enrollmentNumber;
      String aadharCardNumber;
      StudentDTO studentDTO;
      while (resultSet.next()) {
        rollNo = resultSet.getInt("roll_no");
        name = resultSet.getString("name").trim();
        courseCode = resultSet.getInt("course_code");
        title = resultSet.getString("title").trim();
        dateOfBirth = resultSet.getDate("date_of_birth");
        gender = resultSet.getString("gender");
        isIndian = resultSet.getBoolean("is_indian");
        fees = resultSet.getBigDecimal("fees");
        enrollmentNumber = resultSet.getString("enrollment_number").trim();
        aadharCardNumber = resultSet.getString("aadhar_card_number").trim();
        studentDTO = new StudentDTO();
        studentDTO.setRollNo("A" + rollNo);
        studentDTO.setName(name);
        studentDTO.setCourseCode(courseCode);
        studentDTO.setTitle(title);
        studentDTO.setDateOfBirth(dateOfBirth);
        studentDTO.setGender(gender.charAt(0) == 'M' ? GENDER.MALE : GENDER.FEMALE);
        studentDTO.setIsIndian(isIndian);
        studentDTO.setFees(fees);
        studentDTO.setEnrollmentNumber(enrollmentNumber);
        studentDTO.setAadharCardNumber(aadharCardNumber);
        students.add(studentDTO);
      }
      resultSet.close();
      preparedStatement.close();
      connection.close();
      return students;
    } catch (Exception e) {
      throw new DAOException(e.getMessage());
    }
  }

  public StudentDTOInterface getByRollNo(String rollNo) throws DAOException {
    if (rollNo.charAt(0) == 'A' || rollNo.charAt(0) == 'a') rollNo = rollNo.substring(1);
    int intRollNo = Integer.parseInt(rollNo);
    try {
      Connection connection = DAOConnection.getConnection();
      PreparedStatement preparedStatement =
          connection.prepareStatement(
              "SELECT"
                  + " student.roll_no,student.name,student.course_code,course.title,student.date_of_birth,student.gender,student.is_indian,student.fees,student.enrollment_number,student.aadhar_card_number"
                  + " FROM student INNER JOIN course ON student.course_code=course.code WHERE"
                  + " student.roll_no=?");
      preparedStatement.setInt(1, intRollNo);
      ResultSet resultSet;
      resultSet = preparedStatement.executeQuery();
      if (resultSet.next() == false) {
        resultSet.close();
        preparedStatement.close();
        connection.close();
        throw new DAOException("Invalid roll no " + rollNo);
      }

      String name;
      int courseCode;
      String title;
      java.sql.Date dateOfBirth; // cause we're receiving Date object from an sql database
      String gender;
      boolean isIndian;
      BigDecimal fees;
      String enrollmentNumber;
      String aadharCardNumber;
      StudentDTO studentDTO;

      intRollNo = resultSet.getInt("roll_no");
      name = resultSet.getString("name").trim();
      courseCode = resultSet.getInt("course_code");
      title = resultSet.getString("title").trim();
      dateOfBirth = resultSet.getDate("date_of_birth");
      gender = resultSet.getString("gender");
      isIndian = resultSet.getBoolean("is_indian");
      fees = resultSet.getBigDecimal("fees");
      enrollmentNumber = resultSet.getString("enrollment_number").trim();
      aadharCardNumber = resultSet.getString("aadhar_card_number").trim();
      studentDTO = new StudentDTO();
      studentDTO.setRollNo("A" + intRollNo);
      studentDTO.setName(name);
      studentDTO.setCourseCode(courseCode);
      studentDTO.setTitle(title);
      studentDTO.setDateOfBirth(dateOfBirth);
      studentDTO.setGender(gender.charAt(0) == 'M' ? GENDER.MALE : GENDER.FEMALE);
      studentDTO.setIsIndian(isIndian);
      studentDTO.setFees(fees);
      studentDTO.setEnrollmentNumber(enrollmentNumber);
      studentDTO.setAadharCardNumber(aadharCardNumber);
      resultSet.close();
      preparedStatement.close();
      connection.close();
      return studentDTO;
    } catch (Exception e) {
      throw new DAOException(e.getMessage());
    }
  }

  public StudentDTOInterface getByEnrollmentNumber(String enrollmentNumber) throws DAOException {

    try {
      Connection connection = DAOConnection.getConnection();
      PreparedStatement preparedStatement =
          connection.prepareStatement(
              "SELECT"
                  + " student.roll_no,student.name,student.course_code,course.title,student.date_of_birth,student.gender,student.is_indian,student.fees,student.enrollment_number,student.aadhar_card_number"
                  + " FROM student INNER JOIN course ON student.course_code=course.code WHERE"
                  + " student.enrollment_number=?");
      preparedStatement.setString(1, enrollmentNumber);
      ResultSet resultSet;
      resultSet = preparedStatement.executeQuery();
      if (resultSet.next() == false) {
        resultSet.close();
        preparedStatement.close();
        connection.close();
        throw new DAOException("Invalid enrollment number " + enrollmentNumber);
      }

      int rollNo;
      String name;
      int courseCode;
      String title;
      java.sql.Date dateOfBirth; // cause we're receiving Date object from an sql database
      String gender;
      boolean isIndian;
      BigDecimal fees;
      // String enrollmentNumber;
      String aadharCardNumber;
      StudentDTO studentDTO;

      rollNo = resultSet.getInt("roll_no");
      name = resultSet.getString("name").trim();
      courseCode = resultSet.getInt("course_code");
      title = resultSet.getString("title").trim();
      dateOfBirth = resultSet.getDate("date_of_birth");
      gender = resultSet.getString("gender");
      isIndian = resultSet.getBoolean("is_indian");
      fees = resultSet.getBigDecimal("fees");
      enrollmentNumber = resultSet.getString("enrollment_number").trim();
      aadharCardNumber = resultSet.getString("aadhar_card_number").trim();
      studentDTO = new StudentDTO();
      studentDTO.setRollNo("A" + rollNo);
      studentDTO.setName(name);
      studentDTO.setCourseCode(courseCode);
      studentDTO.setTitle(title);
      studentDTO.setDateOfBirth(dateOfBirth);
      studentDTO.setGender(gender.charAt(0) == 'M' ? GENDER.MALE : GENDER.FEMALE);
      studentDTO.setIsIndian(isIndian);
      studentDTO.setFees(fees);
      studentDTO.setEnrollmentNumber(enrollmentNumber);
      studentDTO.setAadharCardNumber(aadharCardNumber);
      resultSet.close();
      preparedStatement.close();
      connection.close();
      return studentDTO;
    } catch (Exception e) {
      throw new DAOException(e.getMessage());
    }
  }

  public StudentDTOInterface getByAadharCardNumber(String aadharCardNumber) throws DAOException {
    try {
      Connection connection = DAOConnection.getConnection();
      PreparedStatement preparedStatement =
          connection.prepareStatement(
              "SELECT"
                  + " student.roll_no,student.name,student.course_code,course.title,student.date_of_birth,student.gender,student.is_indian,student.fees,student.enrollment_number,student.aadhar_card_number"
                  + " FROM student INNER JOIN course ON student.course_code=course.code WHERE"
                  + " student.aadhar_card_number=?");
      preparedStatement.setString(1, aadharCardNumber);
      ResultSet resultSet;
      resultSet = preparedStatement.executeQuery();
      if (resultSet.next() == false) {
        resultSet.close();
        preparedStatement.close();
        connection.close();
        throw new DAOException("Invalid enrollment number " + aadharCardNumber);
      }

      int rollNo;
      String name;
      int courseCode;
      String title;
      java.sql.Date dateOfBirth; // cause we're receiving Date object from an sql database
      String gender;
      boolean isIndian;
      BigDecimal fees;
      String enrollmentNumber;
      // String aadharCardNumber;
      StudentDTO studentDTO;

      rollNo = resultSet.getInt("roll_no");
      name = resultSet.getString("name").trim();
      courseCode = resultSet.getInt("course_code");
      title = resultSet.getString("title").trim();
      dateOfBirth = resultSet.getDate("date_of_birth");
      gender = resultSet.getString("gender");
      isIndian = resultSet.getBoolean("is_indian");
      fees = resultSet.getBigDecimal("fees");
      enrollmentNumber = resultSet.getString("enrollment_number").trim();
      aadharCardNumber = resultSet.getString("aadhar_card_number").trim();
      studentDTO = new StudentDTO();
      studentDTO.setRollNo("A" + rollNo);
      studentDTO.setName(name);
      studentDTO.setCourseCode(courseCode);
      studentDTO.setTitle(title);
      studentDTO.setDateOfBirth(dateOfBirth);
      studentDTO.setGender(gender.charAt(0) == 'M' ? GENDER.MALE : GENDER.FEMALE);
      studentDTO.setIsIndian(isIndian);
      studentDTO.setFees(fees);
      studentDTO.setEnrollmentNumber(enrollmentNumber);
      studentDTO.setAadharCardNumber(aadharCardNumber);
      resultSet.close();
      preparedStatement.close();
      connection.close();
      return studentDTO;
    } catch (Exception e) {
      throw new DAOException(e.getMessage());
    }
  }

  public boolean isCourseAllotted(int code) throws DAOException {
    try {
      Connection connection = DAOConnection.getConnection();
      PreparedStatement preparedStatement =
          connection.prepareStatement("SELECT course_code FROM student WHERE course_code=?");
      preparedStatement.setInt(1, code);
      ResultSet resultSet;
      resultSet = preparedStatement.executeQuery();

      boolean isCourseAllotted = resultSet.next();

      resultSet.close();
      preparedStatement.close();
      connection.close();
      return isCourseAllotted;
    } catch (Exception e) {
      throw new DAOException(e.getMessage());
    }
  }

  public boolean rollNoExists(String rollNo) throws DAOException {
    if (rollNo.charAt(0) == 'A' || rollNo.charAt(0) == 'a') rollNo = rollNo.substring(1);
    int intRollNo = Integer.parseInt(rollNo);
    try {
      Connection connection = DAOConnection.getConnection();
      PreparedStatement preparedStatement =
          connection.prepareStatement("SELECT roll_no FROM student WHERE roll_no=?");
      preparedStatement.setInt(1, intRollNo);
      ResultSet resultSet;
      resultSet = preparedStatement.executeQuery();

      boolean rollNoExists = resultSet.next();

      resultSet.close();
      preparedStatement.close();
      connection.close();
      return rollNoExists;
    } catch (Exception e) {
      throw new DAOException(e.getMessage());
    }
  }

  public boolean enrollmentNumberExists(String enrollmentNumber) throws DAOException {
    try {
      Connection connection = DAOConnection.getConnection();
      PreparedStatement preparedStatement =
          connection.prepareStatement(
              "SELECT enrollment_number FROM student WHERE enrollment_number=?");
      preparedStatement.setString(1, enrollmentNumber);
      ResultSet resultSet;
      resultSet = preparedStatement.executeQuery();

      boolean enrollmentNumberExists = resultSet.next();

      resultSet.close();
      preparedStatement.close();
      connection.close();
      return enrollmentNumberExists;
    } catch (Exception e) {
      throw new DAOException(e.getMessage());
    }
  }

  public boolean aadharCardNumberExists(String aadharCardNumber) throws DAOException {
    try {
      Connection connection = DAOConnection.getConnection();
      PreparedStatement preparedStatement =
          connection.prepareStatement(
              "SELECT aadhar_card_number FROM student WHERE aadhar_card_number=?");
      preparedStatement.setString(1, aadharCardNumber);
      ResultSet resultSet;
      resultSet = preparedStatement.executeQuery();

      boolean aadharCardNumberExists = resultSet.next();

      resultSet.close();
      preparedStatement.close();
      connection.close();
      return aadharCardNumberExists;
    } catch (Exception e) {
      throw new DAOException(e.getMessage());
    }
  }

  public int getCount() throws DAOException {
    int count = 0;
    try {
      Connection connection = DAOConnection.getConnection();
      PreparedStatement preparedStatement =
          connection.prepareStatement("SELECT COUNT(roll_no) FROM student;");
      ResultSet resultSet;
      resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        count = resultSet.getInt(1);
      }
      resultSet.close();
      preparedStatement.close();
      connection.close();
      return count;
    } catch (Exception e) {
      throw new DAOException(e.getMessage());
    }
  }

  public int getCountByCourse(int code) throws DAOException {
    int count = 0;
    try {
      Connection connection = DAOConnection.getConnection();
      PreparedStatement preparedStatement =
          connection.prepareStatement("SELECT COUNT(roll_no) FROM student WHERE course_code=?;");
      preparedStatement.setInt(1, code);
      ResultSet resultSet;
      resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        count = resultSet.getInt(1);
      }
      resultSet.close();
      preparedStatement.close();
      connection.close();
      return count;
    } catch (Exception e) {
      throw new DAOException(e.getMessage());
    }
  }
}
