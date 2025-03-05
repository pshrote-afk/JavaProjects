import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;
import java.util.*;
public class StudentManagerGetStudentCountByCourseTestCase
{
public static void main(String gg[])
{
int courseCode = Integer.parseInt(gg[0]);
try
{
StudentManagerInterface studentManager = StudentManager.getStudentManager();
int count = studentManager.getStudentCountByCourse(courseCode);

CourseManagerInterface courseManager = CourseManager.getCourseManager();
String courseTitle = courseManager.getCourseByCode(courseCode).getTitle();

System.out.println("Total number of students for course ("+courseTitle+"): "+count);
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

