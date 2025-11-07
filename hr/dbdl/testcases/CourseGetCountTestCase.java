import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import java.util.*;

public class CourseGetCountTestCase {
  public static void main(String gg[]) {
    try {
      CourseDAOInterface courseDAO = new CourseDAO();
      System.out.println("Count of records: " + courseDAO.getCount());
    } catch (DAOException daoException) {
      System.out.println(daoException.getMessage());
    }
  }
}
