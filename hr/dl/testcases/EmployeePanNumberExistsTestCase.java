import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.exceptions.*;
public class EmployeePanNumberExistsTestCase
{
public static void main(String gg[])
{
String panNumber = gg[0];
try
{
EmployeeDAOInterface employeeDAO;
employeeDAO = new EmployeeDAO();
boolean isPANNumberValid = employeeDAO.panNumberExists(panNumber);
if(isPANNumberValid)
{
System.out.println("PAN number ("+panNumber+") exists");
}
else
{
System.out.println("Invalid PAN number: "+panNumber);
}
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}