import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import java.util.*;

public class StudentManagerGetStudentCountTestCase {
  public static void main(String gg[]) {
    try {
      StudentManagerInterface studentManager = StudentManager.getStudentManager();
      int count = studentManager.getStudentCount();
      System.out.println("Total number of students: " + count);
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
