import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import java.util.*;

public class CourseManagerGetCoursesTestCase {
  public static void main(String gg[]) {
    try {
      CourseManagerInterface courseManager = CourseManager.getCourseManager();
      Set<CourseInterface> courses = courseManager.getCourses();
      for (CourseInterface course : courses) {
        System.out.println("Code: (" + course.getCode() + "), Title: (" + course.getTitle() + ")");
      }
    } catch (BLException blException) {
      List<String> properties = blException.getProperties();
      for (String property : properties) {
        String exception = blException.getException(property);
        System.out.println(exception);
      }
    }
  }
}
