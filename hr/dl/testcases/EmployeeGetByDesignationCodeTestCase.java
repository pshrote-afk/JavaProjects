import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.exceptions.*;
import java.text.*;	//for SimpleDateFormat class
import java.util.*; 	//for Set,TreeSet class
public class EmployeeGetByDesignationCodeTestCase
{
public static void main(String gg[])
{
try
{
int designationCode = Integer.parseInt(gg[0]);

Set<EmployeeDTOInterface> treeSet1 = new TreeSet<>();
Iterator<EmployeeDTOInterface> iterator1 = treeSet1.iterator();

EmployeeDAOInterface employeeDAO;
employeeDAO = new EmployeeDAO();
treeSet1 = employeeDAO.getByDesignationCode(designationCode);

treeSet1.forEach((employeeDTO)->{
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
});

}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}