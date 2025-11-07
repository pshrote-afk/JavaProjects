package com.thinking.machines.hr.dl.interfaces.dao;

import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import java.util.*; // for Set collection

public interface CourseDAOInterface {
  public void add(CourseDTOInterface courseDTO) throws DAOException;

  public void update(CourseDTOInterface courseDTO) throws DAOException;

  public void delete(int code) throws DAOException;

  public Set<CourseDTOInterface> getAll() throws DAOException;

  public CourseDTOInterface getByCode(int code) throws DAOException;

  public CourseDTOInterface getByTitle(String title) throws DAOException;

  public boolean codeExists(int code) throws DAOException;

  public boolean titleExists(String title) throws DAOException;

  public int getCount() throws DAOException;
}
