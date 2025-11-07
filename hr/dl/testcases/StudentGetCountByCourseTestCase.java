import com.thinking.machines.enums.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import java.math.*; // for BigDecimal class
import java.text.*; // for SimpleDateFormat
import java.util.*; // for TreeSet

public class StudentGetCountByCourseTestCase {
  public static void main(String gg[]) {
    int count = 0;
    int code = Integer.parseInt(gg[0]);
    try {
      StudentDAOInterface studentDAO;
      studentDAO = new StudentDAO();
      count = studentDAO.getCountByCourse(code);
      System.out.println("Number of students against course code (" + code + "): " + count);
    } catch (DAOException daoException) {
      System.out.println(daoException.getMessage());
    }
  }
}
