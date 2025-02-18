import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.enums.*;
import java.math.*;
import java.util.*;
import java.text.*;
public class StudentUpdateTestCase
{
public static void main(String gg[])
{
SimpleDateFormat sdf;
sdf = new SimpleDateFormat("dd/MM/yyyy");
try
{
String rollNo = gg[0];
String name = gg[1];
int courseCode = Integer.parseInt(gg[2]);
Date dateOfBirth=null;
try
{
dateOfBirth = sdf.parse(gg[3]);
}catch(ParseException pe)
{
System.out.println(pe.getMessage());
}
char gender = gg[4].charAt(0);
boolean isIndian = Boolean.parseBoolean(gg[5]);
BigDecimal fees = new BigDecimal(gg[6]);
String enrollmentNumber = gg[7];
String aadharCardNumber = gg[8];

StudentDTOInterface studentDTO;
studentDTO = new StudentDTO();
studentDTO.setRollNo(rollNo);
studentDTO.setName(name);
studentDTO.setCourseCode(courseCode);
studentDTO.setDateOfBirth(dateOfBirth);
if(gender=='M') studentDTO.setGender(GENDER.MALE);
if(gender=='F') studentDTO.setGender(GENDER.FEMALE);
studentDTO.setIsIndian(isIndian);
studentDTO.setFees(fees);
studentDTO.setEnrollmentNumber(enrollmentNumber);
studentDTO.setAadharCardNumber(aadharCardNumber);

StudentDAOInterface studentDAO;
studentDAO = new StudentDAO();
studentDAO.update(studentDTO);
System.out.println("Record updated with roll no ("+studentDTO.getRollNo()+")");
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}