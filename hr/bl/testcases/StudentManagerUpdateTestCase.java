import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.enums.*;
import java.util.*; //for List class
import java.text.*; //for SimpleDateFormat class
import java.math.*; //for BigDecimal class
public class StudentManagerUpdateTestCase
{
public static void main(String gg[])
{
try
{

StudentInterface student;
student = new Student();
student.setRollNo(gg[0]);
student.setName(gg[1]);

CourseManagerInterface courseManager = CourseManager.getCourseManager();
CourseInterface course = courseManager.getCourseByCode(Integer.parseInt(gg[2]));
student.setCourse(course);

SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
Date dateOfBirth = null;
try
{
dateOfBirth = sdf.parse(gg[3]);
student.setDateOfBirth(dateOfBirth);
}catch(ParseException pe)
{
System.out.println(pe.getMessage());
}
char gender = gg[4].charAt(0);
if(gender=='M') student.setGender(GENDER.MALE);
if(gender=='F') student.setGender(GENDER.FEMALE);
student.setIsIndian(Boolean.parseBoolean(gg[5]));
student.setFees(new BigDecimal(gg[6]));
student.setEnrollmentNumber(gg[7]);
student.setAadharCardNumber(gg[8]);

StudentManagerInterface studentManager = StudentManager.getStudentManager();
studentManager.updateStudent(student);
System.out.println("Student updated with roll no: "+student.getRollNo());
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

