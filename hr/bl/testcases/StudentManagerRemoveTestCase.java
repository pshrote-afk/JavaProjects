import com.thinking.machines.enums.*;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import java.math.*; // for BigDecimal class
import java.text.*; // for SimpleDateFormat class
import java.util.*; // for List class

public class StudentManagerRemoveTestCase {
  public static void main(String gg[]) {
    try {
      String rollNo = gg[0];
      StudentManagerInterface studentManager = StudentManager.getStudentManager();
      studentManager.removeStudent(rollNo);
      System.out.println("Student removed with roll no: " + rollNo);
    } catch (BLException blException) {
      if (blException.hasGenericException()) System.out.println(blException.getGenericException());
      List<String> properties = blException.getProperties();
      properties.forEach(
          (property) -> { // the data type of parameter of forEach is determined by the type of data
                          // type of collection
            System.out.println(blException.getException(property));
          });
    }
  }
}
