import com.thinking.machines.enums.*;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import java.math.*; // for BigDecimal class
import java.text.*; // for SimpleDateFormat class
import java.util.*; // for List class

public class StudentManagerIsCourseAllottedTestCase {
  public static void main(String gg[]) {
    try {
      StudentManagerInterface studentManager = StudentManager.getStudentManager();

      boolean isCourseAllotted = studentManager.isCourseAllotted(Integer.parseInt(gg[0]));
      System.out.println("Course is allotted: " + isCourseAllotted);

    } catch (BLException blException) {
      if (blException.hasGenericException()) System.out.println(blException.getGenericException());
      List<String> properties = blException.getProperties();
      properties.forEach(
          (property) -> {
            System.out.println(blException.getException(property));
          });
    }
  }
}
