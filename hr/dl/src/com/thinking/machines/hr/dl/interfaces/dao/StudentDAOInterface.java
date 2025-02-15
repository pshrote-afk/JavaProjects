package com.thinking.machines.hr.dl.interfaces.dao;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import java.util.*; //for Set collection
public interface StudentDAOInterface 
{
public void add(StudentDTOInterface studentDTO) throws DAOException;
public void update(StudentDTOInterface studentDTO) throws DAOException;
public void delete(String rollNo) throws DAOException;
public Set<StudentDTOInterface> getByCourseCode(int code) throws DAOException;
public Set<StudentDTOInterface> getAll() throws DAOException;
public boolean isCourseCodeAllotted(int courseCode) throws DAOException;

public StudentDTOInterface getByRollNo(String rollNo) throws DAOException;
public StudentDTOInterface getByEnrollmentNumber(String enrollmentNumber) throws DAOException;
public StudentDTOInterface getByAadharCardNumber(String aadharCardNumber) throws DAOException;

public boolean rollNoExists(String rollNo) throws DAOException;
public boolean enrollmentNumberExists(String enrollmentNumber) throws DAOException;
public boolean aadharCardNumberExists(String aadharCardNumber) throws DAOException;

public int getCount() throws DAOException;
public int getCountByCourse(int code) throws DAOException;
}