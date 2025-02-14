package com.thinking.machines.hr.dl.interfaces.dao;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import java.util.*; //for Set collection
public interface StudentDAOInterface implements Comparable<StudentDAOInterface>,java.io.Serializable
{
public void add(StudentDTOInterface studentDTO) throws DAOException;
public void update(StudentDTOInterface studentDTO) throws DAOException;
public void delete(String rollNo) throws DAOException;

public StudentDTOInterface getByRollNo(String rollNo) throws DAOException;
public StudentDTOInterface getByAadharCardNumber(String aadharCardNumber) throws DAOException;


}