
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;
import java.util.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.exceptions.*;
public class CourseManagerAddTestCase
{
public static void main(String gg[])
{
try
{
CourseInterface course = new Course();
course.setTitle(gg[0]);

CourseManagerInterface courseManager = CourseManager.getCourseManager();
courseManager.addCourse(course);
System.out.println("Record added with course code: "+course.getCode());
}catch(BLException blException)
{
List<String> properties = blException.getProperties();
for(String property:properties)
{
String exception = blException.getException(property);
System.out.println(exception);
}

}

}
}