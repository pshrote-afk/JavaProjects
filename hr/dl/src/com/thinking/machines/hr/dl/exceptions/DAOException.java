package com.thinking.machines.hr.dl.exceptions;
public class DAOException extends Exception //therefore it is now a checked exception
{
public DAOException(String message)
{
super(message);
}
}