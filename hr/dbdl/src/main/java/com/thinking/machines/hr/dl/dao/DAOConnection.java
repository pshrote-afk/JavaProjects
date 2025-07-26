package com.thinking.machines.hr.dl.dao;
import com.thinking.machines.hr.dl.exceptions.*;
import java.sql.*;
public class DAOConnection
{
private DAOConnection() //no need to make object
{
}
public static Connection getConnection() throws DAOException
{
Connection connection=null;
try
{
Class.forName("com.mysql.cj.jdbc.Driver");
connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hrdb","hr","hr");
return connection;
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
}
}