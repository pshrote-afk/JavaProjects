import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.exceptions.*;
public class EmployeeGetCountByDesignationTestCase
{
public static void main(String gg[])
{
try
{
int designationCode = Integer.parseInt(gg[0]);
EmployeeDAOInterface employeeDAO;
employeeDAO = new EmployeeDAO();
int countByDesignation = employeeDAO.getCountByDesignation(designationCode);
System.out.println("Number of employees for code ("+designationCode+"): "+countByDesignation);
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}