import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import java.util.*;

public class CourseTitleExistsTestCase {
  public static void main(String gg[]) {
    try {
      String title = gg[0];
      CourseDAOInterface courseDAO = new CourseDAO();
      boolean titleExists = courseDAO.titleExists(title);
      System.out.println("Title exists: " + titleExists);
    } catch (DAOException daoException) {
      System.out.println(daoException.getMessage());
    }
  }
}
