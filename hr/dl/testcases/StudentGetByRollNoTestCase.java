import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.enums.*;
import java.util.*;	//for TreeSet
import java.math.*;	//for BigDecimal class
import java.text.*;	//for SimpleDateFormat
public class StudentGetByRollNoTestCase
{
public static void main(String gg[])
{
try
{
StudentDTOInterface studentDTO;
StudentDAOInterface studentDAO;
studentDAO = new StudentDAO();
studentDTO=studentDAO.getByRollNo(gg[0]);
String rollNo=studentDTO.getRollNo();
String name=studentDTO.getName();
int courseCode=studentDTO.getCourseCode();
Date dateOfBirth=studentDTO.getDateOfBirth();	//convert to dateString to print
char gender=studentDTO.getGender();
boolean isIndian=studentDTO.getIsIndian();
BigDecimal fees=studentDTO.getFees();
String enrollmentNumber=studentDTO.getEnrollmentNumber();
String aadharCardNumber=studentDTO.getAadharCardNumber();
System.out.println(rollNo);
System.out.println(name);
System.out.println(String.valueOf(courseCode));
SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
String dateString = sdf.format(dateOfBirth);
System.out.println(dateString);
System.out.println(gender);
System.out.println(isIndian);
System.out.println(fees.toPlainString());
System.out.println(enrollmentNumber);
System.out.println(aadharCardNumber);
System.out.println("****************************************");
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}