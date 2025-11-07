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

public class CourseManagerRemoveTestCase {
  public static void main(String gg[]) {
    try {
      int code = Integer.parseInt(gg[0]);
      CourseManagerInterface courseManager = CourseManager.getCourseManager();
      courseManager.removeCourse(code);
      System.out.println("Record removed");
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
