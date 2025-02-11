import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.exceptions.*;
import java.text.*;	//for SimpleDateFormat class
public class EmployeeGetByEmployeeIdTestCase
{
public static EmployeeDTOInterface getByEmployeeId(String employeeId) throws DAOException
{
EmployeeDAOInterface employeeDAO;
employeeDAO = new EmployeeDAO();
return employeeDAO.getByEmployeeId(employeeId);
}

//to show that execution within a class starts from the method labelled "main" which is public, static, has return type void, and accepts String array as parameter
public static void main(String gg[])
{
String employeeId=(gg[0]);
try
{
EmployeeDTOInterface employeeDTO;
employeeDTO = getByEmployeeId(employeeId);

System.out.println(employeeDTO.getEmployeeId());
System.out.println(employeeDTO.getName());
System.out.println(employeeDTO.getDesignationCode());

SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
System.out.println(sdf.format(employeeDTO.getDateOfBirth()));
System.out.println(employeeDTO.getGender());
System.out.println(employeeDTO.getIsIndian());
System.out.println(employeeDTO.getBasicSalary());
System.out.println(employeeDTO.getPANNumber());
System.out.println(employeeDTO.getAadharCardNumber());
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}