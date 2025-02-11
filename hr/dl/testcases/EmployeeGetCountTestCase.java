import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.exceptions.*;
public class EmployeeGetCountTestCase
{
public static void main(String gg[])
{
try
{
EmployeeDAOInterface employeeDAO;
employeeDAO = new EmployeeDAO();
int count = employeeDAO.getCount();
System.out.println("Number of employees: "+count);
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}