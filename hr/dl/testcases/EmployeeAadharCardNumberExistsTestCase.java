import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.exceptions.*;
public class EmployeeAadharCardNumberExistsTestCase
{
public static void main(String gg[])
{
String aadharCardNumber = gg[0];
try
{
EmployeeDAOInterface employeeDAO;
employeeDAO = new EmployeeDAO();
boolean isAadharCardNumberValid = employeeDAO.aadharCardNumberExists(aadharCardNumber);
if(isAadharCardNumberValid)
{
System.out.println("Aadhar card number ("+aadharCardNumber+") exists");
}
else
{
System.out.println("Invalid Aadhar card number: "+aadharCardNumber);
}
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}