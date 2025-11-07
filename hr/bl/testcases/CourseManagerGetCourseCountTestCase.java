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

public class CourseManagerGetCourseCountTestCase {
  public static void main(String gg[]) {
    try {
      CourseManagerInterface courseManager = CourseManager.getCourseManager();
      int count = courseManager.getCourseCount();
      System.out.println("Count: " + count);
    } catch (BLException blException) {
      if (blException.hasGenericException()) {
        System.out.println(blException.getGenericException());
      }
    }
  }
}
