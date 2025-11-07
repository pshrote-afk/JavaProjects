import com.thinking.machines.enums.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import java.math.*; // for BigDecimal class
import java.text.*; // for SimpleDateFormat
import java.util.*; // for TreeSet

public class StudentGetByCourseCodeTestCase {
  public static void main(String gg[]) {
    Set<StudentDTOInterface> treeSet1;
    treeSet1 = new TreeSet<>();
    Iterator<StudentDTOInterface> iterator = treeSet1.iterator();
    int code = Integer.parseInt(gg[0]);
    try {
      StudentDAOInterface studentDAO;
      studentDAO = new StudentDAO();
      treeSet1 = studentDAO.getByCourseCode(code);
    } catch (DAOException daoException) {
      System.out.println(daoException.getMessage());
    }
    // iterate on what? StudentDAOInterface type object
    treeSet1.forEach(
        (studentDTO) -> {
          String rollNo = studentDTO.getRollNo();
          String name = studentDTO.getName();
          int courseCode = studentDTO.getCourseCode();
          Date dateOfBirth = studentDTO.getDateOfBirth(); // convert to dateString to print
          char gender = studentDTO.getGender();
          boolean isIndian = studentDTO.getIsIndian();
          BigDecimal fees = studentDTO.getFees();
          String enrollmentNumber = studentDTO.getEnrollmentNumber();
          String aadharCardNumber = studentDTO.getAadharCardNumber();
          System.out.println(rollNo);
          System.out.println(name);
          System.out.println(String.valueOf(courseCode));
          SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
          String dateString = sdf.format(dateOfBirth);
          System.out.println(dateString);
          System.out.println(gender);
          System.out.println(isIndian);
          System.out.println(fees.toPlainString());
          System.out.println(enrollmentNumber);
          System.out.println(aadharCardNumber);
          System.out.println("****************************************");
        });
  }
}
