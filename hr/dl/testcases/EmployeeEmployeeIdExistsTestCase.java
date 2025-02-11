import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.exceptions.*;
public class EmployeeEmployeeIdExistsTestCase
{
public static void main(String gg[])
{
String employeeId = gg[0];
try
{
EmployeeDAOInterface employeeDAO;
employeeDAO = new EmployeeDAO();
boolean isEmployeeIdValid = employeeDAO.employeeIdExists(employeeId);
if(isEmployeeIdValid)
{
System.out.println("Employee ID ("+employeeId+") exists");
}
else
{
System.out.println("Invalid employee ID: "+employeeId);
}
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}