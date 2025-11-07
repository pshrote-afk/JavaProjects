import com.thinking.machines.enums.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import java.math.*; // for BigDecimal class
import java.text.*; // for SimpleDateFormat
import java.util.*; // for TreeSet

public class StudentAadharCardNumberExistsTestCase {
  public static void main(String gg[]) {
    try {
      StudentDTOInterface studentDTO;
      StudentDAOInterface studentDAO;
      studentDAO = new StudentDAO();
      boolean aadharCardNumberExists = studentDAO.aadharCardNumberExists(gg[0]);
      System.out.println(aadharCardNumberExists);
    } catch (DAOException daoException) {
      System.out.println(daoException.getMessage());
    }
  }
}
