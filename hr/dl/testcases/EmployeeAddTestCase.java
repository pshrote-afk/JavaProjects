import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.exceptions.*;
import java.util.*; //for Date class
import java.text.*; //for SimpleDateFormat class
import java.math.*; //for BigDecimal class
public class EmployeeAddTestCase
{
public static void main(String gg[])
{
SimpleDateFormat sdf;
sdf = new SimpleDateFormat("dd/MM/yyyy");

try
{
String name = gg[0];
int designationCode = Integer.parseInt(gg[1]);
Date dateOfBirth;
try
{
dateOfBirth = sdf.parse(gg[2]);
}catch(ParseException pe)
{
System.out.println(pe.getMessage());
return;
}
char gender = gg[3].charAt(0);
boolean isIndian = Boolean.parseBoolean(gg[4]);
String bigDecimal = gg[5];
String panNumber = gg[6];
String aadharCardNumber = gg[7];

EmployeeDTOInterface employeeDTO;
employeeDTO = new EmployeeDTO();
employeeDTO.setName(name);
employeeDTO.setDesignationCode(designationCode);
employeeDTO.setDateOfBirth(dateOfBirth);
employeeDTO.setGender(gender);
employeeDTO.setIsIndian(isIndian);
employeeDTO.setBasicSalary(new BigDecimal(bigDecimal));
employeeDTO.setPANNumber(panNumber);
employeeDTO.setAadharCardNumber(aadharCardNumber);

EmployeeDAOInterface employeeDAO;
employeeDAO = new EmployeeDAO();
employeeDAO.add(employeeDTO);
System.out.println("Employee added w/ employee ID: ("+employeeDTO.getEmployeeId()+") .");
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}