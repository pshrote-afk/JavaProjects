package com.thinking.machines.hr.dl.dao;

import com.thinking.machines.enums.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import java.io.*; // for RandomAccessFile class
import java.math.*; // for BigDecimal class
import java.text.*; // for SimpleDateFormat class
import java.util.*; // for Set collection, and Date class

public class StudentDAO implements StudentDAOInterface {
  private static final String FILE_NAME = "student.data";
  private static SimpleDateFormat simpleDateFormat =
      new SimpleDateFormat("dd/MM/yyyy"); // initialized in a static initializer block

  public void add(StudentDTOInterface studentDTO) throws DAOException {
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
    // extract data from incoming DTO object
    String name = studentDTO.getName();
    if (name == null) throw new DAOException("Name is null");
    name = name.trim();
    if (name.length() == 0) throw new DAOException("Length of name cannot be zero");
    int courseCode = studentDTO.getCourseCode();
    if (!(new CourseDAO().codeExists(courseCode)))
      throw new DAOException("Invalid course code: " + courseCode);
    Date dateOfBirth = studentDTO.getDateOfBirth();
    if (dateOfBirth == null) throw new DAOException("Date of birth is null");
    char gender = studentDTO.getGender();
    if (gender == ' ') throw new DAOException("Invalid gender. Only M/F allowed");
    boolean isIndian = studentDTO.getIsIndian();
    BigDecimal fees = studentDTO.getFees();
    if (fees == null) throw new DAOException("Fess is null");
    String enrollmentNumber = studentDTO.getEnrollmentNumber();
    if (enrollmentNumber == null) throw new DAOException("Enrollment number is null");
    String aadharCardNumber = studentDTO.getAadharCardNumber();
    if (aadharCardNumber == null) throw new DAOException("Aadhar card number is null");
    // validated all fields. Now add to file. Then update header.
    try {
      File file = new File(FILE_NAME);
      RandomAccessFile randomAccessFile;
      randomAccessFile = new RandomAccessFile(file, "rw");
      int lastGeneratedCode = 10000000;
      int recordCount = 0;
      if (randomAccessFile.length() == 0) // means newly opened file. Therefore initialize header
      {
        randomAccessFile.writeBytes(String.format("%-10d", lastGeneratedCode) + "\n");
        randomAccessFile.writeBytes(String.format("%-10d", recordCount) + "\n");
      } else // else file already exists, so read header
      {
        lastGeneratedCode = Integer.parseInt(randomAccessFile.readLine().trim());
        recordCount = Integer.parseInt(randomAccessFile.readLine().trim());
      }
      // crossed header. Now ensure enrollment number and aadhar card number is unique - else throw
      // exception
      int x;
      String fRollNo;
      String fEnrollmentNumber;
      String fAadharCardNumber;
      boolean enrollmentNumberExists = false;
      boolean aadharCardNumberExists = false;
      String enrollmentNumberAgainstRollNo = "";
      String aadharCardNumberAgainstRollNo = "";
      while (randomAccessFile.getFilePointer() < randomAccessFile.length()) {
        fRollNo = randomAccessFile.readLine();
        for (x = 2; x <= 7; x++) randomAccessFile.readLine();
        fEnrollmentNumber = randomAccessFile.readLine();
        fAadharCardNumber = randomAccessFile.readLine();
        if (enrollmentNumberExists == false
            && enrollmentNumber.equalsIgnoreCase(fEnrollmentNumber)) {
          enrollmentNumberExists = true;
          enrollmentNumberAgainstRollNo = fRollNo;
        }
        if (aadharCardNumberExists == false
            && aadharCardNumber.equalsIgnoreCase(fAadharCardNumber)) {
          aadharCardNumberExists = true;
          aadharCardNumberAgainstRollNo = fRollNo;
        }
        if (enrollmentNumberExists && aadharCardNumberExists) break;
      }
      if (enrollmentNumberExists && aadharCardNumberExists) {
        throw new DAOException(
            "Enrollment number ("
                + enrollmentNumber
                + ") and aadhar card number ("
                + aadharCardNumber
                + ") already exist against roll no ("
                + enrollmentNumberAgainstRollNo
                + ") and ("
                + aadharCardNumberAgainstRollNo
                + ") respectively");
      }
      if (enrollmentNumberExists) {
        throw new DAOException(
            "Enrollment number ("
                + enrollmentNumber
                + ") already exists against roll no ("
                + enrollmentNumberAgainstRollNo
                + ")");
      }
      if (aadharCardNumberExists) {
        throw new DAOException(
            "Aadhar card number ("
                + aadharCardNumber
                + ") already exists against roll no ("
                + aadharCardNumberAgainstRollNo
                + ")");
      }
      // control reached here means enrollment and aadhar are unique. thus, write in file.
      lastGeneratedCode++;
      recordCount++;
      String newGeneratedRollNo = "R" + lastGeneratedCode;
      studentDTO.setRollNo(
          newGeneratedRollNo); // setting roll no in parameter variable so that from where it was
                               // called we can display what roll no has been generated
      randomAccessFile.writeBytes(studentDTO.getRollNo() + "\n");
      randomAccessFile.writeBytes(name + "\n");
      randomAccessFile.writeBytes(courseCode + "\n");
      randomAccessFile.writeBytes(simpleDateFormat.format(dateOfBirth) + "\n");
      randomAccessFile.writeBytes(gender + "\n");
      randomAccessFile.writeBytes(isIndian + "\n");
      randomAccessFile.writeBytes(fees.toPlainString() + "\n");
      randomAccessFile.writeBytes(enrollmentNumber + "\n");
      randomAccessFile.writeBytes(aadharCardNumber + "\n");
      // update header
      randomAccessFile.seek(0);
      randomAccessFile.writeBytes(String.format("%-10d", lastGeneratedCode) + "\n");
      randomAccessFile.writeBytes(String.format("%-10d", recordCount) + "\n");
      randomAccessFile.close();
    } catch (IOException ioException) {
      throw new DAOException(ioException.getMessage());
    }
  }

  public void update(StudentDTOInterface studentDTO) throws DAOException {
    // extract data from incoming DTO object
    String rollNo = studentDTO.getRollNo();
    String name = studentDTO.getName();
    if (name == null) throw new DAOException("Name is null");
    name = name.trim();
    if (name.length() == 0) throw new DAOException("Length of name cannot be zero");
    int courseCode = studentDTO.getCourseCode();
    if (!(new CourseDAO().codeExists(courseCode)))
      throw new DAOException("Invalid course code: " + courseCode);
    Date dateOfBirth = studentDTO.getDateOfBirth();
    if (dateOfBirth == null) throw new DAOException("Date of birth is null");
    char gender = studentDTO.getGender();
    if (gender == ' ') throw new DAOException("Invalid gender. Only M/F allowed");
    boolean isIndian = studentDTO.getIsIndian();
    BigDecimal fees = studentDTO.getFees();
    if (fees == null) throw new DAOException("Fess is null");
    String enrollmentNumber = studentDTO.getEnrollmentNumber();
    if (enrollmentNumber == null) throw new DAOException("Enrollment number is null");
    String aadharCardNumber = studentDTO.getAadharCardNumber();
    if (aadharCardNumber == null) throw new DAOException("Aadhar card number is null");
    // validated all fields. Now check duplicacy of roll, enroll and aadhar in file.
    try {
      File file = new File(FILE_NAME);
      RandomAccessFile randomAccessFile;
      randomAccessFile = new RandomAccessFile(file, "rw");
      randomAccessFile.readLine();
      randomAccessFile.readLine();
      boolean rollNoFound = false;
      boolean enrollmentNumberFound = false;
      boolean aadharCardNumberFound = false;
      String enrollmentNumberFoundAgainstRollNo = "";
      String aadharCardNumberFoundAgainstRollNo = "";
      String fRollNo;
      String fEnrollmentNumber;
      String fAadharCardNumber;
      int x;
      long foundAt = 0;
      while (randomAccessFile.getFilePointer() < randomAccessFile.length()) {
        if (rollNoFound == false) {
          foundAt = randomAccessFile.getFilePointer();
        }
        fRollNo = randomAccessFile.readLine();
        for (x = 0; x <= 5; x++) randomAccessFile.readLine();
        fEnrollmentNumber = randomAccessFile.readLine();
        fAadharCardNumber = randomAccessFile.readLine();
        if (rollNoFound == false && fRollNo.equalsIgnoreCase(rollNo)) {
          rollNoFound = true;
        }
        if (enrollmentNumberFound == false
            && fEnrollmentNumber.equalsIgnoreCase(enrollmentNumber)) {
          enrollmentNumberFound = true;
          enrollmentNumberFoundAgainstRollNo = fRollNo;
        }
        if (aadharCardNumberFound == false
            && fAadharCardNumber.equalsIgnoreCase(aadharCardNumber)) {
          aadharCardNumberFound = true;
          aadharCardNumberFoundAgainstRollNo = fRollNo;
        }
        if (rollNoFound && enrollmentNumberFound && aadharCardNumberFound) break;
      }
      if (rollNoFound == false) {
        throw new DAOException("Invalid roll no: " + rollNo);
      }
      boolean enrollmentNumberExists = false;
      if (enrollmentNumberFound
          && enrollmentNumberFoundAgainstRollNo.equalsIgnoreCase(rollNo) == false) {
        enrollmentNumberExists = true;
      }
      boolean aadharCardNumberExists = false;
      if (aadharCardNumberFound
          && aadharCardNumberFoundAgainstRollNo.equalsIgnoreCase(rollNo) == false) {
        aadharCardNumberExists = true;
      }
      if (enrollmentNumberExists && aadharCardNumberExists) {
        throw new DAOException(
            "Enrollment number ("
                + enrollmentNumber
                + ") and aadhar card number ("
                + aadharCardNumber
                + ") already exist against roll no ("
                + enrollmentNumberFoundAgainstRollNo
                + ") and ("
                + aadharCardNumberFoundAgainstRollNo
                + ") respectively");
      }
      if (enrollmentNumberExists) {
        throw new DAOException(
            "Enrollment number ("
                + enrollmentNumber
                + ") already exists against roll no ("
                + enrollmentNumberFoundAgainstRollNo
                + ")");
      }
      if (aadharCardNumberExists) {
        throw new DAOException(
            "Aadhar card number ("
                + aadharCardNumber
                + ") already exists against roll no ("
                + aadharCardNumberFoundAgainstRollNo
                + ")");
      }
      File tmpFile = new File("student.tmp");
      RandomAccessFile tmpRandomAccessFile;
      tmpRandomAccessFile = new RandomAccessFile(tmpFile, "rw");
      randomAccessFile.seek(foundAt);
      for (x = 0; x <= 8; x++) randomAccessFile.readLine();
      while (randomAccessFile.getFilePointer() < randomAccessFile.length()) {
        tmpRandomAccessFile.writeBytes(randomAccessFile.readLine() + "\n");
      }
      // update
      randomAccessFile.seek(foundAt);
      randomAccessFile.writeBytes(rollNo + "\n");
      randomAccessFile.writeBytes(name + "\n");
      randomAccessFile.writeBytes(courseCode + "\n");
      randomAccessFile.writeBytes(simpleDateFormat.format(dateOfBirth) + "\n");
      randomAccessFile.writeBytes(gender + "\n");
      randomAccessFile.writeBytes(isIndian + "\n");
      randomAccessFile.writeBytes(fees.toPlainString() + "\n");
      randomAccessFile.writeBytes(enrollmentNumber + "\n");
      randomAccessFile.writeBytes(aadharCardNumber + "\n");
      tmpRandomAccessFile.seek(0);
      while (tmpRandomAccessFile.getFilePointer() < tmpRandomAccessFile.length()) {
        randomAccessFile.writeBytes(tmpRandomAccessFile.readLine() + "\n");
      }
      randomAccessFile.setLength(randomAccessFile.getFilePointer());
      tmpRandomAccessFile.setLength(0);
      tmpRandomAccessFile.close();
      randomAccessFile.close();
    } catch (IOException ioException) {
      throw new DAOException(ioException.getMessage());
    }
  }

  public void delete(String rollNo) throws DAOException {
    if (rollNo == null) throw new DAOException("Roll no is null");
    rollNo = rollNo.trim();
    if (rollNo.length() == 0) throw new DAOException("Length of roll no cannot be zero");
    // Now check duplicacy of roll, enroll and aadhar in file.
    try {
      File file = new File(FILE_NAME);
      RandomAccessFile randomAccessFile;
      randomAccessFile = new RandomAccessFile(file, "rw");
      randomAccessFile.readLine();
      randomAccessFile.readLine();
      boolean rollNoFound = false;
      String fRollNo;
      int x;
      long foundAt = 0;
      while (randomAccessFile.getFilePointer() < randomAccessFile.length()) {
        if (rollNoFound == false) {
          foundAt = randomAccessFile.getFilePointer();
        }
        fRollNo = randomAccessFile.readLine();
        for (x = 0; x <= 7; x++) randomAccessFile.readLine();
        if (fRollNo.equalsIgnoreCase(rollNo)) {
          rollNoFound = true;
        }
        if (rollNoFound) break;
      }
      if (rollNoFound == false) {
        throw new DAOException("Invalid roll no: " + rollNo);
      }
      File tmpFile = new File("student.tmp");
      RandomAccessFile tmpRandomAccessFile;
      tmpRandomAccessFile = new RandomAccessFile(tmpFile, "rw");
      while (randomAccessFile.getFilePointer() < randomAccessFile.length()) {
        tmpRandomAccessFile.writeBytes(randomAccessFile.readLine() + "\n");
      }
      // delete
      randomAccessFile.seek(foundAt);
      tmpRandomAccessFile.seek(0);
      while (tmpRandomAccessFile.getFilePointer() < tmpRandomAccessFile.length()) {
        randomAccessFile.writeBytes(tmpRandomAccessFile.readLine() + "\n");
      }
      randomAccessFile.setLength(randomAccessFile.getFilePointer());
      // update header
      String recordCountString;
      randomAccessFile.seek(0);
      randomAccessFile.readLine();
      foundAt = randomAccessFile.getFilePointer();
      recordCountString = randomAccessFile.readLine().trim();
      int recordCount = Integer.parseInt(recordCountString);
      recordCount--;
      randomAccessFile.seek(foundAt);
      randomAccessFile.writeBytes(String.format("%-10d", recordCount) + "\n");
      tmpRandomAccessFile.setLength(0);
      tmpRandomAccessFile.close();
      randomAccessFile.close();
    } catch (IOException ioException) {
      throw new DAOException(ioException.getMessage());
    }
  }

  public Set<StudentDTOInterface> getByCourseCode(int code) throws DAOException {
    Set<StudentDTOInterface> treeSet1 = new TreeSet<>();
    try {
      File file = new File(FILE_NAME);
      RandomAccessFile randomAccessFile;
      randomAccessFile = new RandomAccessFile(file, "rw");
      randomAccessFile.readLine();
      randomAccessFile.readLine();
      StudentDTOInterface studentDTO;
      String fRollNo;
      String fName;
      int fCourseCode;
      char fGender;
      int x;
      while (randomAccessFile.getFilePointer() < randomAccessFile.length()) {
        fRollNo = randomAccessFile.readLine();
        fName = randomAccessFile.readLine();
        fCourseCode = Integer.parseInt(randomAccessFile.readLine());
        if (code == fCourseCode) {
          studentDTO = new StudentDTO();
          studentDTO.setRollNo(fRollNo);
          studentDTO.setName(fName);
          studentDTO.setCourseCode(fCourseCode);
          try {
            studentDTO.setDateOfBirth(simpleDateFormat.parse(randomAccessFile.readLine()));
          } catch (ParseException parseException) {
            throw new DAOException(parseException.getMessage());
          }
          fGender = randomAccessFile.readLine().charAt(0);
          studentDTO.setGender((fGender == 'M') ? GENDER.MALE : GENDER.FEMALE);
          studentDTO.setIsIndian(Boolean.parseBoolean(randomAccessFile.readLine()));
          studentDTO.setFees(new BigDecimal(randomAccessFile.readLine()));
          studentDTO.setEnrollmentNumber(randomAccessFile.readLine());
          studentDTO.setAadharCardNumber(randomAccessFile.readLine());
          treeSet1.add(studentDTO);
        } else {
          for (x = 0; x <= 5; x++) randomAccessFile.readLine();
        }
      }
      randomAccessFile.close();
      return treeSet1;
    } catch (IOException ioException) {
      throw new DAOException(ioException.getMessage());
    }
  }

  public Set<StudentDTOInterface> getAll() throws DAOException {
    Set<StudentDTOInterface> treeSet1 = new TreeSet<>();
    try {
      File file = new File(FILE_NAME);
      RandomAccessFile randomAccessFile;
      randomAccessFile = new RandomAccessFile(file, "rw");
      randomAccessFile.readLine();
      randomAccessFile.readLine();
      StudentDTOInterface studentDTO;
      char fGender;
      while (randomAccessFile.getFilePointer() < randomAccessFile.length()) {
        studentDTO = new StudentDTO();
        studentDTO.setRollNo(randomAccessFile.readLine());
        studentDTO.setName(randomAccessFile.readLine());
        studentDTO.setCourseCode(Integer.parseInt(randomAccessFile.readLine()));
        try {
          studentDTO.setDateOfBirth(simpleDateFormat.parse(randomAccessFile.readLine()));
        } catch (ParseException parseException) {
          throw new DAOException(parseException.getMessage());
        }
        fGender = randomAccessFile.readLine().charAt(0);
        studentDTO.setGender((fGender == 'M') ? GENDER.MALE : GENDER.FEMALE);
        studentDTO.setIsIndian(Boolean.parseBoolean(randomAccessFile.readLine()));
        studentDTO.setFees(new BigDecimal(randomAccessFile.readLine()));
        studentDTO.setEnrollmentNumber(randomAccessFile.readLine());
        studentDTO.setAadharCardNumber(randomAccessFile.readLine());
        treeSet1.add(studentDTO);
      }
      randomAccessFile.close();
      return treeSet1;
    } catch (IOException ioException) {
      throw new DAOException(ioException.getMessage());
    }
  }

  public boolean isCourseAllotted(int code) throws DAOException {
    if (code <= 0) return false;
    try {
      File file = new File(FILE_NAME);
      RandomAccessFile randomAccessFile;
      randomAccessFile = new RandomAccessFile(file, "rw");
      randomAccessFile.readLine();
      randomAccessFile.readLine();
      boolean courseFound = false;
      int fCode;
      int x;
      while (randomAccessFile.getFilePointer() < randomAccessFile.length()) {
        randomAccessFile.readLine();
        randomAccessFile.readLine();
        fCode = Integer.parseInt(randomAccessFile.readLine());
        if (fCode == code) {
          courseFound = true;
          break;
        }
        randomAccessFile.readLine();
        randomAccessFile.readLine();
        randomAccessFile.readLine();
        randomAccessFile.readLine();
        randomAccessFile.readLine();
        randomAccessFile.readLine();
      }
      return courseFound;
    } catch (IOException ioException) {
      throw new DAOException(ioException.getMessage());
    }
  }

  public StudentDTOInterface getByRollNo(String rollNo) throws DAOException {
    if (rollNo == null) throw new DAOException("Roll no is null");
    rollNo = rollNo.trim();
    if (rollNo.length() == 0) throw new DAOException("Length of roll no cannot be zero");
    try {
      File file = new File(FILE_NAME);
      RandomAccessFile randomAccessFile;
      randomAccessFile = new RandomAccessFile(file, "rw");
      randomAccessFile.readLine();
      randomAccessFile.readLine();
      String fRollNo;
      String fName;
      int fCourseCode;
      Date fDateOfBirth;
      char fGender;
      boolean fIsIndian;
      BigDecimal fFees;
      String fEnrollmentNumber;
      String fAadharCardNumber;
      while (randomAccessFile.getFilePointer() < randomAccessFile.length()) {
        fRollNo = randomAccessFile.readLine();
        if (fRollNo.equalsIgnoreCase(rollNo)) {
          fName = randomAccessFile.readLine();
          fCourseCode = Integer.parseInt(randomAccessFile.readLine());
          try {
            fDateOfBirth = simpleDateFormat.parse(randomAccessFile.readLine());
          } catch (ParseException parseException) {
            throw new DAOException(parseException.getMessage());
          }
          fGender = randomAccessFile.readLine().charAt(0);
          fIsIndian = Boolean.parseBoolean(randomAccessFile.readLine());
          fFees = new BigDecimal(randomAccessFile.readLine());
          fEnrollmentNumber = randomAccessFile.readLine();
          fAadharCardNumber = randomAccessFile.readLine();
          StudentDTOInterface studentDTO = new StudentDTO();
          studentDTO.setRollNo(fRollNo);
          studentDTO.setName(fName);
          studentDTO.setCourseCode(fCourseCode);
          studentDTO.setDateOfBirth(fDateOfBirth);
          studentDTO.setGender((fGender == 'M') ? GENDER.MALE : GENDER.FEMALE);
          studentDTO.setIsIndian(fIsIndian);
          studentDTO.setFees(fFees);
          studentDTO.setEnrollmentNumber(fEnrollmentNumber);
          studentDTO.setAadharCardNumber(fAadharCardNumber);
          return studentDTO;
        }
        for (int x = 0; x < 8; x++) {
          randomAccessFile.readLine();
        }
      }
      randomAccessFile.close();
      throw new DAOException("Invalid roll no: " + rollNo);
    } catch (IOException ioException) {
      throw new DAOException(ioException.getMessage());
    }
  }

  public StudentDTOInterface getByEnrollmentNumber(String enrollmentNumber) throws DAOException {
    if (enrollmentNumber == null) throw new DAOException("Enrollment number is null");
    enrollmentNumber = enrollmentNumber.trim();
    if (enrollmentNumber.length() == 0)
      throw new DAOException("Length of enrollment number cannot be zero");
    try {
      File file = new File(FILE_NAME);
      RandomAccessFile randomAccessFile;
      randomAccessFile = new RandomAccessFile(file, "rw");
      randomAccessFile.readLine();
      randomAccessFile.readLine();
      String fRollNo;
      String fName;
      int fCourseCode;
      Date fDateOfBirth;
      char fGender;
      boolean fIsIndian;
      BigDecimal fFees;
      String fEnrollmentNumber;
      String fAadharCardNumber;
      long foundAt = 0;
      while (randomAccessFile.getFilePointer() < randomAccessFile.length()) {
        foundAt = randomAccessFile.getFilePointer();
        for (int x = 0; x < 7; x++) randomAccessFile.readLine();
        fEnrollmentNumber = randomAccessFile.readLine();
        randomAccessFile.readLine(); // aadharCard
        if (fEnrollmentNumber.equalsIgnoreCase(enrollmentNumber)) {
          randomAccessFile.seek(foundAt);
          fRollNo = randomAccessFile.readLine();
          fName = randomAccessFile.readLine();
          fCourseCode = Integer.parseInt(randomAccessFile.readLine());
          try {
            fDateOfBirth = simpleDateFormat.parse(randomAccessFile.readLine());
          } catch (ParseException parseException) {
            throw new DAOException(parseException.getMessage());
          }
          fGender = randomAccessFile.readLine().charAt(0);
          fIsIndian = Boolean.parseBoolean(randomAccessFile.readLine());
          fFees = new BigDecimal(randomAccessFile.readLine());
          fEnrollmentNumber = randomAccessFile.readLine(); // re-reading just for clarity
          fAadharCardNumber = randomAccessFile.readLine();
          StudentDTOInterface studentDTO = new StudentDTO();
          studentDTO.setRollNo(fRollNo);
          studentDTO.setName(fName);
          studentDTO.setCourseCode(fCourseCode);
          studentDTO.setDateOfBirth(fDateOfBirth);
          studentDTO.setGender((fGender == 'M') ? GENDER.MALE : GENDER.FEMALE);
          studentDTO.setIsIndian(fIsIndian);
          studentDTO.setFees(fFees);
          studentDTO.setEnrollmentNumber(fEnrollmentNumber);
          studentDTO.setAadharCardNumber(fAadharCardNumber);
          return studentDTO;
        }
      }
      randomAccessFile.close();
      throw new DAOException("Invalid enrollment number: " + enrollmentNumber);
    } catch (IOException ioException) {
      throw new DAOException(ioException.getMessage());
    }
  }

  public StudentDTOInterface getByAadharCardNumber(String aadharCardNumber) throws DAOException {
    if (aadharCardNumber == null) throw new DAOException("Aadhar card number is null");
    aadharCardNumber = aadharCardNumber.trim();
    if (aadharCardNumber.length() == 0)
      throw new DAOException("Length of aadhar card number cannot be zero");
    try {
      File file = new File(FILE_NAME);
      RandomAccessFile randomAccessFile;
      randomAccessFile = new RandomAccessFile(file, "rw");
      randomAccessFile.readLine();
      randomAccessFile.readLine();
      String fRollNo;
      String fName;
      int fCourseCode;
      Date fDateOfBirth;
      char fGender;
      boolean fIsIndian;
      BigDecimal fFees;
      String fEnrollmentNumber;
      String fAadharCardNumber;
      long foundAt = 0;
      while (randomAccessFile.getFilePointer() < randomAccessFile.length()) {
        foundAt = randomAccessFile.getFilePointer();
        for (int x = 0; x < 8; x++) randomAccessFile.readLine();
        fAadharCardNumber = randomAccessFile.readLine();
        if (fAadharCardNumber.equalsIgnoreCase(aadharCardNumber)) {
          randomAccessFile.seek(foundAt);
          fRollNo = randomAccessFile.readLine();
          fName = randomAccessFile.readLine();
          fCourseCode = Integer.parseInt(randomAccessFile.readLine());
          try {
            fDateOfBirth = simpleDateFormat.parse(randomAccessFile.readLine());
          } catch (ParseException parseException) {
            throw new DAOException(parseException.getMessage());
          }
          fGender = randomAccessFile.readLine().charAt(0);
          fIsIndian = Boolean.parseBoolean(randomAccessFile.readLine());
          fFees = new BigDecimal(randomAccessFile.readLine());
          fEnrollmentNumber = randomAccessFile.readLine();
          fAadharCardNumber = randomAccessFile.readLine(); // re-reading just for clarity
          StudentDTOInterface studentDTO = new StudentDTO();
          studentDTO.setRollNo(fRollNo);
          studentDTO.setName(fName);
          studentDTO.setCourseCode(fCourseCode);
          studentDTO.setDateOfBirth(fDateOfBirth);
          studentDTO.setGender((fGender == 'M') ? GENDER.MALE : GENDER.FEMALE);
          studentDTO.setIsIndian(fIsIndian);
          studentDTO.setFees(fFees);
          studentDTO.setEnrollmentNumber(fEnrollmentNumber);
          studentDTO.setAadharCardNumber(fAadharCardNumber);
          return studentDTO;
        }
      }
      randomAccessFile.close();
      throw new DAOException("Invalid aadhar card number: " + aadharCardNumber);
    } catch (IOException ioException) {
      throw new DAOException(ioException.getMessage());
    }
  }

  public boolean rollNoExists(String rollNo) throws DAOException {
    if (rollNo == null) return false;
    rollNo = rollNo.trim();
    if (rollNo.length() == 0) return false;
    try {
      File file = new File(FILE_NAME);
      RandomAccessFile randomAccessFile;
      randomAccessFile = new RandomAccessFile(file, "rw");
      randomAccessFile.readLine();
      randomAccessFile.readLine();
      String fRollNo;
      while (randomAccessFile.getFilePointer() < randomAccessFile.length()) {
        fRollNo = randomAccessFile.readLine();
        if (fRollNo.equalsIgnoreCase(rollNo)) {
          return true;
        }
        for (int x = 0; x < 8; x++) randomAccessFile.readLine();
      }
      randomAccessFile.close();
      return false;
    } catch (IOException ioException) {
      throw new DAOException(ioException.getMessage());
    }
  }

  public boolean enrollmentNumberExists(String enrollmentNumber) throws DAOException {
    if (enrollmentNumber == null) return false;
    enrollmentNumber = enrollmentNumber.trim();
    if (enrollmentNumber.length() == 0) return false;
    try {
      File file = new File(FILE_NAME);
      RandomAccessFile randomAccessFile;
      randomAccessFile = new RandomAccessFile(file, "rw");
      randomAccessFile.readLine();
      randomAccessFile.readLine();
      String fEnrollmentNumber;
      while (randomAccessFile.getFilePointer() < randomAccessFile.length()) {
        for (int x = 0; x < 7; x++) randomAccessFile.readLine();
        fEnrollmentNumber = randomAccessFile.readLine();
        randomAccessFile.readLine(); // aadharCard
        if (fEnrollmentNumber.equalsIgnoreCase(enrollmentNumber)) {
          return true;
        }
      }
      randomAccessFile.close();
      return false;
    } catch (IOException ioException) {
      throw new DAOException(ioException.getMessage());
    }
  }

  public boolean aadharCardNumberExists(String aadharCardNumber) throws DAOException {
    if (aadharCardNumber == null) return false;
    aadharCardNumber = aadharCardNumber.trim();
    if (aadharCardNumber.length() == 0) return false;
    try {
      File file = new File(FILE_NAME);
      RandomAccessFile randomAccessFile;
      randomAccessFile = new RandomAccessFile(file, "rw");
      randomAccessFile.readLine();
      randomAccessFile.readLine();
      String fAadharCardNumber;
      while (randomAccessFile.getFilePointer() < randomAccessFile.length()) {
        for (int x = 0; x < 8; x++) randomAccessFile.readLine();
        fAadharCardNumber = randomAccessFile.readLine();
        if (fAadharCardNumber.equalsIgnoreCase(aadharCardNumber)) {
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
      RandomAccessFile randomAccessFile;
      randomAccessFile = new RandomAccessFile(file, "rw");
      randomAccessFile.readLine();
      int count = Integer.parseInt(randomAccessFile.readLine().trim());
      randomAccessFile.close();
      return count;
    } catch (IOException ioException) {
      throw new DAOException(ioException.getMessage());
    }
  }

  public int getCountByCourse(int code) throws DAOException {
    if (code <= 0) throw new DAOException("Invalid code: " + code);
    if (!(new CourseDAO().codeExists(code))) throw new DAOException("Invalid code: " + code);
    int count = 0;
    try {
      File file = new File(FILE_NAME);
      RandomAccessFile randomAccessFile;
      randomAccessFile = new RandomAccessFile(file, "rw");
      randomAccessFile.readLine();
      randomAccessFile.readLine();
      StudentDTOInterface studentDTO;
      int fCourseCode;
      int x;
      while (randomAccessFile.getFilePointer() < randomAccessFile.length()) {
        randomAccessFile.readLine();
        randomAccessFile.readLine();
        fCourseCode = Integer.parseInt(randomAccessFile.readLine());
        if (code == fCourseCode) {
          count++;
        }
        for (x = 0; x <= 5; x++) randomAccessFile.readLine();
      }
      randomAccessFile.close();
      return count;
    } catch (IOException ioException) {
      throw new DAOException(ioException.getMessage());
    }
  }
}
