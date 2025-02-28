import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.enums.*;
import java.util.*; //for List class
import java.text.*; //for SimpleDateFormat class
import java.math.*; //for BigDecimal class
public class StudentManagerAddTestCase
{
public static void main(String gg[])
{
/*
private String rollNo;//to be generated
private String name;
private int courseCode;
private Date dateOfBirth;
private char gender;
private boolean isIndian;
private BigDecimal fees;
private String enrollmentNumber;
private String aadharCardNumber;
*/
StudentInterface student;
student = new Student();
student.setName(gg[0]);
student.setCourseCode(Integer.parseInt(gg[1]));
SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
Date dateOfBirth = null;
try
{
dateOfBirth = sdf.parse(gg[2]);
student.setDateOfBirth(dateOfBirth);
}catch(ParseException pe)
{
System.out.println(pe.getMessage());
}
char gender = gg[3].charAt(0);
if(gender=='M') student.setGender(GENDER.MALE);
if(gender=='F') student.setGender(GENDER.FEMALE);
student.setIsIndian(Boolean.parseBoolean(gg[4]));
student.setFees(new BigDecimal(gg[5]));
student.setEnrollmentNumber(gg[6]);
student.setAadharCardNumber(gg[7]);
try
{
StudentManagerInterface studentManager = StudentManager.getStudentManager();
studentManager.addStudent(student);
System.out.println("Student added with roll no: "+student.getRollNo());
}catch(BLException blException)
{
if(blException.hasGenericException()) System.out.println(blException.getGenericException());
List<String> properties = blException.getProperties();
properties.forEach((property)->{
System.out.println(blException.getException(property));
});
}
}
}

