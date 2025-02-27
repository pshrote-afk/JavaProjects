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
public class CourseManagerGetCourseByCodeTestCase
{
public static void main(String gg[])
{
try
{
int code = Integer.parseInt(gg[0]);
CourseManagerInterface courseManager = CourseManager.getCourseManager();
CourseInterface course = courseManager.getCourseByCode(code);
System.out.println("Code: ("+course.getCode()+"), Title: ("+course.getTitle()+")");
}catch(BLException blException)
{
if(blException.hasGenericException())
{
System.out.println(blException.getGenericException());
}
}
}
}