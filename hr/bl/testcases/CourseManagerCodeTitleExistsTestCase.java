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
public class CourseManagerCodeTitleExistsTestCase
{
public static void main(String gg[])
{
try
{
int code = 13;
String title = "ceo";
CourseManagerInterface courseManager = CourseManager.getCourseManager();
boolean exists;
exists = courseManager.courseCodeExists(code);
System.out.println("Code exists: "+exists);
exists = courseManager.courseTitleExists(title);
System.out.println("Title exists: "+exists);
}catch(BLException blException)
{
if(blException.hasGenericException())
{
System.out.println(blException.getGenericException());
}
}
}
}