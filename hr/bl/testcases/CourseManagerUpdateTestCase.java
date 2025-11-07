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

public class CourseManagerUpdateTestCase {
  public static void main(String gg[]) {
    try {
      CourseInterface course = new Course();
      course.setCode(Integer.parseInt(gg[0]));
      course.setTitle(gg[1]);

      CourseManagerInterface courseManager = CourseManager.getCourseManager();
      courseManager.updateCourse(course);
      System.out.println("Record updated");
    } catch (BLException blException) {
      if (blException.hasGenericException()) System.out.println(blException.getGenericException());

      List<String> properties = blException.getProperties();
      for (String property : properties) {
        String exception = blException.getException(property);
        System.out.println(exception);
      }
    }
  }
}
