import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.exceptions.*;
import java.util.*;	//for TreeSet
import java.math.*;	//for BigDecimal class
import java.text.*;	//for SimpleDateFormat
public class EmployeeGetAllTestCase
{
public static void main(String gg[])
{
Set<EmployeeDTOInterface> treeSet1;
treeSet1 = new TreeSet<>();
Iterator<EmployeeDTOInterface> iterator = treeSet1.iterator();
try
{
EmployeeDAOInterface employeeDAO;
employeeDAO = new EmployeeDAO();
treeSet1=employeeDAO.getAll();
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
//iterate on what? EmployeeDAOInterface type object

treeSet1.forEach((employeeDTO)->{
String employeeId=employeeDTO.getEmployeeId();
String name=employeeDTO.getName();
int designationCode=employeeDTO.getDesignationCode();
Date dateOfBirth=employeeDTO.getDateOfBirth();	//convert to dateString to print
char gender=employeeDTO.getGender();
boolean isIndian=employeeDTO.getIsIndian();
BigDecimal basicSalary=employeeDTO.getBasicSalary();
String panNumber=employeeDTO.getPANNumber();
String aadharCardNumber=employeeDTO.getAadharCardNumber();
System.out.println(employeeId);
System.out.println(name);
System.out.println(String.valueOf(designationCode));

SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
String dateString = sdf.format(dateOfBirth);
System.out.println(dateString);

System.out.println(gender);
System.out.println(isIndian);
System.out.println(basicSalary.toPlainString());
System.out.println(panNumber);
System.out.println(aadharCardNumber);
});
}
}