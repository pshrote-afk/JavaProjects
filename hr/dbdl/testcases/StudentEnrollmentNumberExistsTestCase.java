import com.thinking.machines.enums.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import java.math.*; // for BigDecimal class
import java.text.*; // for SimpleDateFormat
import java.util.*; // for TreeSet

public class StudentEnrollmentNumberExistsTestCase {
  public static void main(String gg[]) {
    try {
      StudentDTOInterface studentDTO;
      StudentDAOInterface studentDAO;
      studentDAO = new StudentDAO();
      boolean enrollmentNumberExists = studentDAO.enrollmentNumberExists(gg[0]);
      System.out.println(enrollmentNumberExists);
    } catch (DAOException daoException) {
      System.out.println(daoException.getMessage());
    }
  }
}
