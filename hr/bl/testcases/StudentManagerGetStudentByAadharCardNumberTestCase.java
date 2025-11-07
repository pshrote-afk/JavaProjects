import com.thinking.machines.enums.*;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import java.math.*; // for BigDecimal class
import java.text.*; // for SimpleDateFormat class
import java.util.*; // for List class

public class StudentManagerGetStudentByAadharCardNumberTestCase {
  public static void main(String gg[]) {
    try {
      StudentManagerInterface studentManager = StudentManager.getStudentManager();
      StudentInterface student;

      student = studentManager.getStudentByAadharCardNumber(gg[0]);
      String rollNo = student.getRollNo();
      String name = student.getName();
      int courseCode = student.getCourse().getCode();
      Date dateOfBirth = student.getDateOfBirth(); // convert to dateString to print
      char gender = student.getGender();
      boolean isIndian = student.getIsIndian();
      BigDecimal fees = student.getFees();
      String enrollmentNumber = student.getEnrollmentNumber();
      String aadharCardNumber = student.getAadharCardNumber();
      System.out.println("Roll no: " + rollNo);
      System.out.println("Name: " + name);
      System.out.println("Course code: " + courseCode);
      SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
      String dateString = sdf.format(dateOfBirth);
      System.out.println("DOB: " + dateString);
      System.out.println("Gender: " + gender);
      System.out.println("Is Indian: " + isIndian);
      System.out.println("Fees: " + fees.toPlainString());
      System.out.println("Enrollment number: " + enrollmentNumber);
      System.out.println("Aadhar card number: " + aadharCardNumber);
      System.out.println("******************getByAadharCardNumber**********************");
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
